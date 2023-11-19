package icecat.test.aka.my_mod.screen.gui;

import icecat.test.aka.my_mod.register.Items;
import icecat.test.aka.my_mod.register.NetWork;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ExampleGuiDescription extends LightweightGuiDescription {
    public static final Identifier GIVE_PLAYER_TEST_FOOD = new Identifier("my_mod", "command.get.testfood");

    public ExampleGuiDescription() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        setFullscreen(true);

        root.setSize(100, 100);
        root.setInsets(Insets.ROOT_PANEL);

        WSprite icon = new WSprite(new Identifier("my_mod", "textures/item/test_food.png"));
        root.add(icon, 0, 2, 1, 1);

        WButton button = new WButton(Text.translatable("gui.my_mod.get_test_food")).setOnClick(() -> {
            NetWork.Client.sendToServer(GIVE_PLAYER_TEST_FOOD, new PacketByteBuf(Unpooled.buffer()));
            if (MinecraftClient.getInstance().currentScreen != null) {
                MinecraftClient.getInstance().currentScreen.close();
            }
        });
        root.addPainters();
        root.add(button, 1, 4, 4, 1);

        WLabel label = new WLabel(Text.literal("Test"), 0xFFFFFF);
        root.add(label, 1, 2, 2, 1);

        root.validate(this);
    }

    public static void listen() {
        ServerPlayNetworking.registerGlobalReceiver(GIVE_PLAYER_TEST_FOOD,
                (server, player, handler, attachedData, responseSender) -> {
                    player.giveItemStack(new ItemStack(Items.TestFood.item, 1));
                    PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                    passedData.writeBoolean(true);
                    NetWork.Server.sendToClient(player, GIVE_PLAYER_TEST_FOOD, passedData);
                });
    }

    public static void clientListen() {
        ClientPlayNetworking.registerReceiver(ExampleGuiDescription.GIVE_PLAYER_TEST_FOOD,
                (client, handler, passedData, responseSender) -> {
                    if (passedData.getBoolean(0)) {
                        if (client.player != null) {
                            client.player.sendMessage(Text.translatable("text.my_mod.server_give_test_food"), false);
                        }
                    }
                }
        );
    }
}
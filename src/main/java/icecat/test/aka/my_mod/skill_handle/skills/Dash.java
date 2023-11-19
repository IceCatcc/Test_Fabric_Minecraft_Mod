package icecat.test.aka.my_mod.skill_handle.skills;

import icecat.test.aka.my_mod.Client;
import icecat.test.aka.my_mod.register.NetWork;
import icecat.test.aka.my_mod.skill_handle.base.SkillBase;
import icecat.test.aka.my_mod.skill_handle.base.SkillCost;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class Dash extends SkillBase {
    public static final Identifier PLAYER_DASH = new Identifier("my_mod", "command.player.dash");

    public KeyBinding Dash = Client.keybind.bind(GLFW.GLFW_KEY_G, "key.my_mod.dash");
    private Boolean isKeyDown = false;

    public Dash() {
        super(new SkillCost(SkillCost.CostValueFrom.MP, 10), 5);
    }

    @Override
    protected void execute(ServerPlayerEntity player) {
        if (player != null) {
            Vec3d lookVec = player.getRotationVec(1.0f);
            Vec3d velocity = new Vec3d(lookVec.x * 2.0f, 0.05f, lookVec.z * 2.0f);
            player.setVelocity(velocity);
            PacketByteBuf passedData = PacketByteBufs.create();
            passedData.writeVector3f(velocity.toVector3f());
            NetWork.Server.sendToClient(player, PLAYER_DASH, passedData);
        }
    }

    @Override
    public void serverListen() {
        ServerPlayNetworking.registerGlobalReceiver(PLAYER_DASH, (server, player, handler, passedData, responseSender) -> use(player));
    }

    @Override
    public void clientListen() {
        super.clientListen();
        ClientPlayNetworking.registerReceiver(PLAYER_DASH, (client, handler, passedData, responseSender) -> {
            Vec3d velocity = new Vec3d(passedData.readVector3f());
            if (client.player != null) {
                client.player.setVelocity(velocity);
            }
        });
    }

    @Override
    public void onTrigger(MinecraftClient client) {
        if (Dash.isPressed()) {
            if (!isKeyDown) {
                isKeyDown = true;
                if (client.player != null) {
                    NetWork.Client.sendToServer(PLAYER_DASH, PacketByteBufs.empty());
                }
            }
        } else {
            isKeyDown = false;
        }
    }
}

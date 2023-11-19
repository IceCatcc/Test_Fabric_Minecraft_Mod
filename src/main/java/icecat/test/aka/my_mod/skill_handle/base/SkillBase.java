package icecat.test.aka.my_mod.skill_handle.base;

import icecat.test.aka.my_mod.register.NetWork;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class SkillBase {
    public static final Identifier OnCoolingDown = new Identifier("my_mod", "tip.skill.on_cooling_down");
    private long lastUseTime;
    private final int coolDown;
    private SkillCost cost;

    public SkillBase(SkillCost cost, int coolDown) {
        this.coolDown = coolDown;
        this.cost = cost;
    }

    public void use(ServerPlayerEntity player) {
        long timeDifference = System.currentTimeMillis() - lastUseTime;
        long coolDownL = coolDown * 1000L;
        if (timeDifference > coolDownL) {
            lastUseTime = System.currentTimeMillis();
            execute(player);
        } else {
            double coolingTime = (coolDownL - timeDifference) / 1000f;
            PacketByteBuf passedData = PacketByteBufs.create();
            passedData.writeDouble(coolingTime);
            NetWork.Server.sendToClient(player, OnCoolingDown, passedData);
        }
    }

    protected abstract void execute(ServerPlayerEntity player);

    public abstract void serverListen();

    public void clientListen() {
        ClientPlayNetworking.registerReceiver(OnCoolingDown, (client, handler, passedData, responseSender) -> {
            double coolingTime = passedData.getDouble(0);
            if (client.player != null) {
                client.player.sendMessage(Text.translatable("tip.my_mod.cooling_down", String.format("%.2f", coolingTime)), false);
            }
        });
    }

    ;

    public abstract void onTrigger(MinecraftClient client);
}


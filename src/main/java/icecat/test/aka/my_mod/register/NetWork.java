package icecat.test.aka.my_mod.register;

import icecat.test.aka.my_mod.screen.gui.ExampleGuiDescription;
import icecat.test.aka.my_mod.skill_handle.SkillsHandle;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class NetWork {


    public static class Server {

        /**
         * 开始服务端监听
         */
        public static void listen() {
            ExampleGuiDescription.listen();
            SkillsHandle.dash.serverListen();
        }

        /**
         * 发送数据包到客户端
         *
         * @param player     玩家实体
         * @param id         标识符
         * @param passedData 数据包
         */
        public static void sendToClient(PlayerEntity player, Identifier id, PacketByteBuf passedData) {
            ServerPlayNetworking.send((ServerPlayerEntity) player, id, passedData);
        }
    }

    public static class Client {
        /**
         * 开始监听
         */
        public static void listen() {
            ExampleGuiDescription.clientListen();
            SkillsHandle.dash.clientListen();
        }

        /**
         * 发送数据包到服务端
         *
         * @param id         标识符
         * @param passedData 数据包
         */
        public static void sendToServer(Identifier id, PacketByteBuf passedData) {
            ClientPlayNetworking.send(id, passedData);
        }
    }

}

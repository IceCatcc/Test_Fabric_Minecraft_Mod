package icecat.test.aka.my_mod;

import icecat.test.aka.my_mod.register.KeyBind;
import icecat.test.aka.my_mod.register.NetWork;
import icecat.test.aka.my_mod.screen.ExampleBlockScreen;
import icecat.test.aka.my_mod.screen.gui.ExampleGuiDescription;
import icecat.test.aka.my_mod.skill_handle.SkillsHandle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;


public class Client implements ClientModInitializer {

    public static KeyBind keybind = new KeyBind();

    @Override
    public void onInitializeClient() {


        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (keybind.ShowGui.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new ExampleBlockScreen(new ExampleGuiDescription()));
                }
                keybind.ShowGui.setPressed(false);
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(SkillsHandle.dash::onTrigger);

        ClientTickEvents.START_WORLD_TICK.register(world -> {

        });

        ClientTickEvents.END_WORLD_TICK.register(world -> {
            NetWork.Client.listen();
        });

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            System.out.print("JOIN");
        });
        ClientPlayConnectionEvents.DISCONNECT.register((id, listener) -> {
            System.out.print("DISCONNECT");
        });
    }


}

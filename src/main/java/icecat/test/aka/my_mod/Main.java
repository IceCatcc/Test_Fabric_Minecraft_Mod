package icecat.test.aka.my_mod;

import icecat.test.aka.my_mod.register.Items;
import icecat.test.aka.my_mod.register.NetWork;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
    @Override
    public void onInitialize() {
        Items.registerAll();
        NetWork.Server.listen();
    }
}
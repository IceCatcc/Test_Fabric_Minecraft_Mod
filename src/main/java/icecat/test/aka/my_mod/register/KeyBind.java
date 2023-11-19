package icecat.test.aka.my_mod.register;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBind {
    public KeyBinding ShowGui = bind(GLFW.GLFW_KEY_LEFT_ALT, "key.my_mod.show_gui");


    public KeyBinding bind(int keyCode, String name) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(name, InputUtil.Type.KEYSYM, keyCode, "category.my_mod"));
    }
}

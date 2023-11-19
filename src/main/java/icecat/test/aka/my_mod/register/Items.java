package icecat.test.aka.my_mod.register;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static icecat.test.aka.my_mod.items.ModFood.ModFood;

public class Items {
    public static void registerAll() {
        TestFood.registerItem();
        TestFood.addToItemGroups();
    }

    public static class TestFood {
        public static final Item item = new Item(
                new FabricItemSettings()
                        .maxCount(128)
                        .food(ModFood));

        private static void registerItem() {
            //注册自定义物品
            Registry.register(
                    Registries.ITEM,
                    new Identifier("my_mod", "test_food")
                    , item);
        }

        private static void addToItemGroups() {
            //注册物品到创造物品栏
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
                content.add(item);
            });
        }
    }
}

package icecat.test.aka.my_mod.items;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFood {
    public static final FoodComponent ModFood =
            (new FoodComponent.Builder())
                    .hunger(4)
                    .saturationModifier(2.0F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0), 1.0F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0), 1.0F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 9), 1.0F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0), 1.0F)
                    .alwaysEdible()
                    .build();
}

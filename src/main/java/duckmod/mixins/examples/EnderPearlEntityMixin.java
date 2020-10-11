/* (CC BY-SA 4.0) 2020 */
package duckmod.mixins.examples;

import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EnderPearlEntity.class)
public class EnderPearlEntityMixin {

    @ModifyConstant(method = "onCollision", constant = @Constant(floatValue = 0.05F))
    private static float endermiteChance(float original) {
        System.out.println("hello");

        return 1f;
    }
}

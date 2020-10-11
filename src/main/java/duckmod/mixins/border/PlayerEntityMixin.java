/* (CC BY-SA 4.0) 2020 */
package duckmod.mixins.border;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = -2.9999999E7D))
    private double modifyMinCord(double original) {
        return Integer.MIN_VALUE;
    }

    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = 2.9999999E7D))
    private double modifyMaxCord(double original) {
        return Integer.MAX_VALUE;
    }
}

/* (CC BY-SA 4.0) 2020 */
package duckmod.mixins.border;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Entity.class)
public class EntityMixin {
    @ModifyConstant(method = "method_30634", constant = @Constant(doubleValue = 3.0E7D))
    private double fixMaxPos(double original) {
        return Integer.MAX_VALUE;
    }

    @ModifyConstant(method = "method_30634", constant = @Constant(doubleValue = -3.0E7D))
    private double fixMinPos(double original) {
        return Integer.MIN_VALUE;
    }
}

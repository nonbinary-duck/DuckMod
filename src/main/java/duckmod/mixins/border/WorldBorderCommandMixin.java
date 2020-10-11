/* (CC BY-SA 4.0) 2020 */
package duckmod.mixins.border;

import net.minecraft.server.command.WorldBorderCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(WorldBorderCommand.class)
public class WorldBorderCommandMixin {
    @ModifyConstant(method = "executeSet", constant = @Constant(doubleValue = 6.0E7D))
    private static double maxWorldBorderSizeExecute(double original) {
        return (double) (Integer.MAX_VALUE) * 2D;
    }

    @ModifyConstant(method = "register", constant = @Constant(floatValue = 6.0E7F))
    private static float maxWorldBorderSizeArguments(float original) {
        return (float) (Integer.MAX_VALUE) * 2F;
    }

    @ModifyConstant(method = "register", constant = @Constant(floatValue = -6.0E7F))
    private static float minWorldBorderSizeArguments(float original) {
        return (float) (Integer.MIN_VALUE) * 2F;
    }
}

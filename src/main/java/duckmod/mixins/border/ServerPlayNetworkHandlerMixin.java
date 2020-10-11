/* (CC BY-SA 4.0) 2020 */
package duckmod.mixins.border;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @ModifyConstant(method = "validatePlayerMove", constant = @Constant(doubleValue = 3.0E7D))
    private static double allowPlayerMovementPackage(double original) {
        return Integer.MAX_VALUE;
    }
}

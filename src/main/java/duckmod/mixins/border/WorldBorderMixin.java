/* (CC BY-SA 4.0) 2020 */
package duckmod.mixins.border;

import net.minecraft.world.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldBorder.class)
public abstract class WorldBorderMixin {
    @Shadow
    public abstract void setMaxWorldBorderRadius(int radius);

    @Shadow private int maxWorldBorderRadius = 29999984;

    private final int DEFAULT_MAX_BORDER_RADIUS = 29999984;

    @Inject(method = "setSize", at = @At(value = "HEAD"))
    public void increaseMaxSize(double size, CallbackInfo ci) {
        double radius = size / 2;

        System.out.println(
                "Current Max Size: " + maxWorldBorderRadius + "\nNew Border Radius: " + radius);

        if (radius > DEFAULT_MAX_BORDER_RADIUS && radius <= Integer.MAX_VALUE) {
            setMaxWorldBorderRadius((int) radius);
        } else if (maxWorldBorderRadius != DEFAULT_MAX_BORDER_RADIUS) {
            setMaxWorldBorderRadius(DEFAULT_MAX_BORDER_RADIUS);
        }
    }
}

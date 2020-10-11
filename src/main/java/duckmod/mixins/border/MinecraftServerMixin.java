/* (CC BY-SA 4.0) 2020 */
package duckmod.mixins.border;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Overwrite
    public int getMaxWorldBorderRadius() {
        return Integer.MAX_VALUE;
    }
}

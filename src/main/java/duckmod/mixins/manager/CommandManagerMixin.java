/* (CC BY-SA 4.0) 2020 */
package duckmod.mixins.manager;

import com.mojang.brigadier.CommandDispatcher;
import duckmod.managers.RegisterCommands;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public class CommandManagerMixin {

    @Shadow @Final private CommandDispatcher<ServerCommandSource> dispatcher;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onRegister(CommandManager.RegistrationEnvironment arg, CallbackInfo ci) {
        RegisterCommands.registerCommands(this.dispatcher);
    }
}

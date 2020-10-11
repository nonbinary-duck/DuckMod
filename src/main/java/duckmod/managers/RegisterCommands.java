/* (CC BY-SA 4.0) 2020 */
package duckmod.managers;

import com.mojang.brigadier.CommandDispatcher;
import duckmod.commands.ConfigCommand;
import net.minecraft.server.command.ServerCommandSource;

public class RegisterCommands {
    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        ConfigCommand.register(dispatcher);
    }
}

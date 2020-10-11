/* (CC BY-SA 4.0) 2020 */
package duckmod;

import duckmod.managers.settings.DuckSettings;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModInit implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "villanelle-duckmod";
    public static final String MOD_NAME = "Duckmod";

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");

        log(Level.INFO, "Loading settings");
        DuckSettings.initSettings();
        log(Level.INFO, "Settings loaded");
    }

    public static void logError(String message) {
        log(Level.ERROR, message);
    }

    public static void logWarn(String message) {
        log(Level.WARN, message);
    }

    public static void log(String message) {
        log(Level.INFO, message);
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }
}

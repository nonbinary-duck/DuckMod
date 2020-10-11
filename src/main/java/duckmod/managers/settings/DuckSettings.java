/* (CC BY-SA 4.0) 2020 */
package duckmod.managers.settings;

import duckmod.ModInit;
import duckmod.managers.settings.rules.duckmod.ConfigCommandGetLevelSetting;
import duckmod.managers.settings.rules.duckmod.ConfigCommandSetLevelSetting;
import duckmod.managers.settings.rules.fun.ExtraDamageCritsEnabledSetting;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import org.jetbrains.annotations.Nullable;

/**
 * Stores values for all of the in-game configurable settings.
 *
 * <p>Settings can be accessed from both the static constants such as EXTRA_DAMAGE_CRITS_ENABLED and
 * also via the HashMap which uses the GetName() of the DuckSetting as a key for the object.
 *
 * <p>It's unlikely that I'll ever change the name of an established field in this method.
 *
 * <p>Pull requests that don't include a ? extends DuckSetting<T> to at the very least toggle your
 * mod will probably be declined unless I've got some spare time and your fork is really that cool!
 */
public final class DuckSettings {
    public static final String SETTINGS_PATH = "duckSettings.txt";
    public static LinkedHashMap<String, DuckSetting> settings;

    public static ExtraDamageCritsEnabledSetting EXTRA_DAMAGE_CRITS_ENABLED =
            new ExtraDamageCritsEnabledSetting();

    public static ConfigCommandSetLevelSetting CONFIG_COMMAND_SET_OP_LEVEL =
            new ConfigCommandSetLevelSetting();

    public static ConfigCommandGetLevelSetting CONFIG_COMMAND_GET_OP_LEVEL =
            new ConfigCommandGetLevelSetting();

    /** Returns every registered setting in an array */
    public static DuckSetting[] getSettings() {
        return settings.values().toArray(new DuckSetting[0]);
    }

    /** Returns the name of every registered setting */
    public static String[] getSettingsNames() {
        return settings.keySet().toArray(new String[0]);
    }

    // #region Loading and saving settings

    /** Init all settings from the config file */
    public static void initSettings() {
        settings = new LinkedHashMap<String, DuckSetting>();

        settings.put(EXTRA_DAMAGE_CRITS_ENABLED.getName(), EXTRA_DAMAGE_CRITS_ENABLED);
        settings.put(CONFIG_COMMAND_GET_OP_LEVEL.getName(), CONFIG_COMMAND_GET_OP_LEVEL);
        settings.put(CONFIG_COMMAND_SET_OP_LEVEL.getName(), CONFIG_COMMAND_SET_OP_LEVEL);

        // Contributor's settings go after this please!

        setSettingsFromHashMap(loadSettings());
    }

    protected static void setSettingsFromHashMap(HashMap<String, String> settingsFile) {
        if (settingsFile == null) return;

        Iterator<String> keys = settingsFile.keySet().iterator();

        while (keys.hasNext()) {
            String key = keys.next();

            if (settings.containsKey(key)) {
                DuckSetting setting = settings.get(key);

                setting.setValue(setting.fromString(settingsFile.get(key)));

                continue;
            }

            ModInit.logWarn(
                    String.format(
                            "The key '%s' with value '%s' taken from %s does not exist withing the current settings configuration",
                            key, settingsFile.get(key), SETTINGS_PATH));
        }
    }

    /**
     * Loads all of the settings from the config file. The config file uses
     * §<settingName>:<settingValue> syntax with no comment support IMPORTANT: There must be a new
     * line for every setting, though one setting can have multiple lines
     *
     * @return A HashMap with names of settings as the key and value of the setting as the string
     */
    @Nullable
    public static HashMap<String, String> loadSettings() {
        LinkedHashMap<String, String> settingsMap = new LinkedHashMap<String, String>();
        File file = new File(SETTINGS_PATH);

        if (!file.exists()) {
            saveSettings();
            return null;
        }

        StringBuilder sb = new StringBuilder();
        try {
            // Probably should have used a FileReader but I don't think there is much difference
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNext()) {
                sb.append(fileReader.nextLine());
                if (fileReader.hasNext()) sb.append("\n");
            }

            fileReader.close();
        } catch (Exception e) {
            // This can never be called. Java is dumb.
        }

        String[] settingsFile = sb.toString().split("\n§");

        if (settingsFile[0].startsWith("§")) {
            settingsFile[0] = settingsFile[0].substring(1);
        }

        for (String line : settingsFile) {
            int pos = line.indexOf(":");
            if (pos < 0) continue;

            // I don't need to assign these values, but I do anyway.
            String key = line.substring(0, pos);
            // The +1 is to avoid including the ':'
            String value = line.substring(pos + 1);

            // Use this to debug the settings thingi
            // System.out.printf("{Key: \"%s\", Value: \"%s\"}\n", key, value);

            settingsMap.put(key, value);
        }

        return settingsMap;
    }

    /**
     * Saves all settings to a file
     *
     * @return Success state
     */
    public static boolean saveSettings() {
        File file = new File(SETTINGS_PATH);

        if (!file.exists()) {
            try {
                file.getAbsoluteFile().getParentFile().mkdirs();
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        StringBuilder sb = new StringBuilder();
        Iterator<DuckSetting> currentSettings = settings.values().iterator();

        while (currentSettings.hasNext()) {
            DuckSetting currSetting = currentSettings.next();

            sb.append(String.format("§%s:%s\n", currSetting.getName(), currSetting.toString()));
        }

        try {
            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(sb.toString());
            fileWriter.close();
        } catch (Exception e) {
            // Another great example of how annoying java is
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // #endregion

    public static boolean setPermissionFeedback(Object sauce) {
        if (!(sauce instanceof ServerCommandSource)) return false;

        ServerCommandSource source = (ServerCommandSource)sauce;
        boolean canSet = source.hasPermissionLevel(CONFIG_COMMAND_SET_OP_LEVEL.getValue());

        if (!canSet) source.sendFeedback(new TranslatableText("command.villanelle-duckmod.setting.no_permission_to_set"), false);

        return canSet;
    }
    
    public static int sendSettingFeedback(String settingName, ServerCommandSource source) {
        if (settings.containsKey(settingName)) {
            DuckSetting setting = settings.get(settingName);

            source.sendFeedback(
                    new LiteralText(
                            String.format(
                                    "The setting \"%s\" has a value of \"%s\"",
                                    setting.getName(), setting.toString())),
                    false);
            return 1;
        }

        return 0;
    }
}

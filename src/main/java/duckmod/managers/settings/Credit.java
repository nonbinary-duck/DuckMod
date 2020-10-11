/* (CC BY-SA 4.0) 2020 */
package duckmod.managers.settings;

/** Provides a clean class for forkers to attribute themselves to a setting */
public class Credit {
    /**
     * Give yourself credit! I will only accept pull requests with trusted URLs like GitHub, Patreon
     * etc.
     *
     * @param name Your name or nickname!
     * @param tag A message to show your users when they hover over your name! Optionally a url for
     *     users to be taken to when clicked, must start with "http" for that. Only trusted URLs are
     *     accepted.
     */
    public Credit(String name, String tag) {
        this.NAME = name;
        this.TAG = tag;
        this.TAG_IS_URL = tag.toLowerCase().startsWith("http");
    }

    public final String NAME;
    public final String TAG;
    public final boolean TAG_IS_URL;
}


import java.io.File;


/**
 * Plugin.java - Extend this to create your own plugins.
 * 
 * @author James
 */
public abstract class Plugin {
    private String name = "";
    private boolean enabled = true;
    private boolean usesListeners;

    /**
     * Enables the plugin
     */
    public abstract void enable();

    /**
     * Disables the plugin
     */
    public abstract void disable();

    /**
     * Returns true if this plugin is enabled
     * 
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Toggles whether or not this plugin is enabled
     * 
     * @return
     */
    public boolean toggleEnabled() {
        enabled = !enabled;
        return enabled;
    }

    /**
     * Sets the name of this plugin
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of this plugin
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Plugin is loaded and may now register hooks
     */
    public void initialize() {}

    /**
     * Returns the default PropertiesFile for this plugin.
     * @return plugins/NAME/NAME.properties
     */
    public final PropertiesFile getPropertiesFile() {
        File dir = new File("plugins/" + name);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new PropertiesFile("plugins/" + name + "/" + name + ".properties");
    }

    /**
     * Returns a PropertiesFile with the specified name for this plugin.
     * @param filename The filename without the extension ".properties". 
     * @return plugins/NAME/FILENAME.properties
     */
    public final PropertiesFile getPropertiesFile(String filename) {
        File dir = new File("plugins/" + name);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new PropertiesFile("plugins/" + name + "/" + filename + ".properties");
    }

    /**
     * Returns the default txt file for this plugin. The necessary directorys are created.
     * @return plugins/NAME/NAME.txt
     */
    public final File getTxtFile() {
        File dir = new File("plugins/" + name);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, name + ".txt");
    }

    /**
     * Returns a txt file with the specified name for this plugin. The necessary directorys are created.
     * @param filename The filename without the extension ".txt". 
     * @return plugins/NAME/FILENAME.txt
     */
    public final File getTxtFile(String filename) {
        File dir = new File("plugins/" + name);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, name + ".txt");
    }

    /**
     * Returns the default file with the specified type for this plugin. The necessary directorys are created.
     * @param filetype The filetype without the leading ".".
     * @return plugins/NAME/NAME.FILETYPE
     */
    public final File getFile(String filetype) {
        File dir = new File("plugins/" + name);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, name + "." + filetype);
    }

    /**
     * Returns a file with the specified name and type for this plugin. The necessary directorys are created.
     * @param filename The filename without the extension.
     * @param filetype The filetype without the leading ".".
     * @return plugins/NAME/FILENAME.FILETYPE
     */
    public final File getFile(String filename, String filetype) {
        File dir = new File("plugins/" + name);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, filename + "." + filetype);
    }

    /**
     * Returns the default directory for this plugin. If it doesn't exist, it's created.
     * @return plugins/NAME/
     */
    public final File getDirectory() {
        File dir = new File("plugins/" + name);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

}

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Motd - Class for handling of the MOTD
 * extracted from etc
 * 
 * @author Talmor
 */
public class Motd {
    private static final Logger           log = Logger.getLogger("Minecraft");
    
    public static String getMotd(MessageReceiver caller) {
        try {
            FileInputStream fis = new FileInputStream(etc.getInstance().getConfigFolder() + "motd.txt");
            Scanner scanner = new Scanner(fis, "UTF-8");

            scanner.useDelimiter("\r\n");
            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (line.startsWith("#")) {
                    continue;
                }
                String msg = replace(line, ((Player) caller));

                ((Player) caller).getEntity().a.b(new OPacket3Chat(msg));
            }
            scanner.close();
            fis.close();
        } catch (FileNotFoundException e) {
            log.log(Level.WARNING, "motd.txt does not exist.");
            caller.notify("Cannot load motd");
        } catch (IOException e) {
            log.log(Level.WARNING, "Couldn't load motd.txt");
            caller.notify("Cannot load motd.txt");
        }
        return null;
    }
    
    public static void makeMotd() {
        new File(etc.getInstance().getConfigFolder()).mkdirs();
        File motdfile = new File(etc.getInstance().getConfigFolder() + "motd.txt");

        if (!motdfile.exists()) {
            FileWriter writer = null;

            try {
                writer = new FileWriter(motdfile);
                writer.write("#For a list of colors, go here: http://wiki.canarymod.net/Colors\r\n");
                writer.write("#To use linebreaks, just press enter or return. For color, use &\r\n");
                writer.write("Welcome to my server! Please type /help for commands.");
            } catch (Exception e) {
                log.log(Level.SEVERE, "Exception while creating motd.txt");
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e1) {
                    log.log(Level.SEVERE, "Exception while closing writer for motd.txt", e1);
                }
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    log.log(Level.SEVERE, "Exception while closing writer for motd.txt", e);
                }
            }
        }
    }
    
    public static String replace(String text, Player p) {
        if (text.contains("&")) {
            text = text.replace("&", Colors.Marker);
        }
        if (text.contains("&&")) {
            text = text.replaceAll("&&", "&");
        }
        if (text.contains("%playerlist%")) {
            text = text.replaceAll("%playerlist%", etc.getServer().getPlayerNames());
        }
        if (text.contains("%player%")) {
            text = text.replaceAll("%player%", p.getName());
        }
        if (text.contains("%group%")) {
            text = text.replaceAll("%group%", (p.getGroups().length > 0 ? p.getGroups()[0] : etc.getDataSource().getDefaultGroup().Name));
        }
        if (text.contains("%onlineplayers%")) {
            text = text.replaceAll("%onlineplayers%", Integer.toString(etc.getServer().getPlayerList().size()));
        }
        if (text.contains("%maxplayers%")) {
            text = text.replaceAll("%maxplayers%", Integer.toString(etc.getInstance().getPlayerLimit()));
        }
        if (text.contains("%level%")) {
            text = text.replaceAll("%level%", Integer.toString(p.getLevel()));
        }
        if (text.contains("%exp%")) {
            text = text.replaceAll("%exp%", Integer.toString(p.getXP()));
        }
        return text;
    }
}

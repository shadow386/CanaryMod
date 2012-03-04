
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * FlatFileSource.java - Accessing users, groups and such from flat files.
 *
 * @author James
 */
public class FlatFileSource extends DataSource {

    private static final String LINE_SEP = System.getProperty("line.separator");

    @Override
    public void initialize() {
        loadGroups();
        loadKits();
        loadHomes();
        loadWarps();
        loadItems();
        loadEnderBlocks();
        loadAntiXRayBlocks();
        loadMutedPlayers();
        loadBanList();

        String location = etc.getInstance().getUsersLocation();

        if (!new File(location).exists()) {
            FileWriter writer = null;

            try {
                writer = new FileWriter(location);
                writer.write("#Add your users here (When adding your entry DO NOT include #!)" + LINE_SEP);
                writer.write("#The format is:" + LINE_SEP);
                writer.write("#NAME:GROUPS:ADMIN/UNRESTRICTED:COLOR:COMMANDS:IPs" + LINE_SEP);
                writer.write("#For administrative powers set admin/unrestricted to 2." + LINE_SEP);
                writer.write("#For no restrictions and ability to give out items set it to 1." + LINE_SEP);
                writer.write("#If you don't want the person to be able to build set it to -1." + LINE_SEP);
                writer.write("#Admin/unrestricted, color and commands are optional." + LINE_SEP);
                writer.write("#Examples:" + LINE_SEP);
                writer.write("#Adminfoo:admins" + LINE_SEP);
                writer.write("#Moderator39:mods:1:0:/unban" + LINE_SEP);
                writer.write("#BobTheBuilder:vip:0:d" + LINE_SEP);
            } catch (Exception e) {
                log.log(Level.SEVERE, String.format("Exception while creating %s", location), e);
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    log.log(Level.SEVERE, String.format("Exception while closing writer for %s", location), e);
                }
            }
        }
        location = etc.getInstance().getWhitelistLocation();
        if (!new File(location).exists()) {
            FileWriter writer = null;

            try {
                writer = new FileWriter(location);
                writer.write("#Whitelist. Add your users here" + LINE_SEP);
            } catch (Exception e) {
                log.log(Level.SEVERE, String.format("Exception while creating %s", location), e);
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    log.log(Level.SEVERE, String.format("Exception while closing writer for %s", location), e);
                }
            }
        }
    }

    @Override
    public void loadGroups() {
        String location = etc.getInstance().getGroupLocation();

        if (!new File(location).exists()) {
            FileWriter writer = null;

            try {
                writer = new FileWriter(location);
                writer.write("#Add your groups here (When adding your entry DO NOT include #!)" + LINE_SEP);
                writer.write("#The format is:" + LINE_SEP);
                writer.write("#NAME:COLOR:COMMANDS:INHERITEDGROUPS:ADMIN/UNRESTRICTED" + LINE_SEP);
                writer.write("#For administrative powers set admin/unrestricted to 2." + LINE_SEP);
                writer.write("#For no restrictions and ability to give out items set it to 1." + LINE_SEP);
                writer.write("#If you don't want the group to be able to build set it to -1." + LINE_SEP);
                writer.write("#Inherited groups and admin/unrestricted are optional." + LINE_SEP);
                writer.write("#Examples:" + LINE_SEP);
                writer.write("admins:c:*:mods:2" + LINE_SEP);
                writer.write("mods:b:/ban,/kick,/item,/tp,/tphere,/s,/i,/give:vip:1" + LINE_SEP);
                writer.write("vip:a::default" + LINE_SEP);
                writer.write("default:f:/help,/sethome,/home,/spawn,/me,/msg,/kit,/playerlist,/warp,/motd,/compass,/tell,/m,/who:default" + LINE_SEP);
            } catch (Exception e) {
                log.log(Level.SEVERE, String.format("Exception while creating %s", location), e);
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    log.log(Level.SEVERE, String.format("Exception while closing writer for %s", location), e);
                }
            }
        }

        synchronized (groupLock) {
            groups = new ArrayList<Group>();
            try {
                Scanner scanner = new Scanner(new File(location));
                int linenum = 0;

                while (scanner.hasNextLine()) {
                    linenum++;
                    String line = scanner.nextLine();

                    if (line.startsWith("#") || line.equals("") || line.startsWith("﻿")) {
                        continue;
                    }

                    String[] split = line.split(":");

                    if (split.length < 3) {
                        log.log(Level.SEVERE, String.format("Problem while reading %s (Line %d violates the syntax)", location, linenum));
                        continue;
                    }
                    Group group = new Group();

                    group.Name = split[0];
                    group.Prefix = split[1];
                    group.Commands = split[2].split(",");
                    if (split.length >= 4) {
                        group.InheritedGroups = split[3].split(",");
                    }
                    if (split.length >= 5) {
                        if (split[4].equals("1")) {
                            group.IgnoreRestrictions = true;
                        } else if (split[4].equals("2")) {
                            group.Administrator = true;
                        } else if (split[4].equals("-1")) {
                            group.CanModifyWorld = false;
                        }
                    }

                    // kind of a shitty way, but whatever.
                    if (group.InheritedGroups != null) {
                        if (group.InheritedGroups[0].equalsIgnoreCase(group.Name)) {
                            group.InheritedGroups = new String[]{""};
                            group.DefaultGroup = true;
                        }
                    }

                    groups.add(group);
                }
                scanner.close();
            } catch (Exception e) {
                log.log(Level.SEVERE, String.format("Exception while reading %s (Are you sure you formatted it correctly?)", location), e);
            }
        }
    }

    @Override
    public void loadKits() {
        String location = etc.getInstance().getKitsLocation();

        if (!new File(location).exists()) {
            FileWriter writer = null;

            try {
                writer = new FileWriter(location);
                writer.write("#Add your kits here. Example entry below (When adding your entry DO NOT include #!)" + LINE_SEP);
                writer.write("#miningbasics:1,2,3,4:6000" + LINE_SEP);
                writer.write(String.format("#The formats are (Find out more about groups in %s:" + LINE_SEP, etc.getInstance().getUsersLocation()));
                writer.write("#NAME:IDs:DELAY" + LINE_SEP);
                writer.write("#NAME:IDs:DELAY:GROUP" + LINE_SEP);
                writer.write("#6000 for delay is roughly 5 minutes." + LINE_SEP);
            } catch (Exception e) {
                log.log(Level.SEVERE, String.format("Exception while creating %s", location), e);
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                }
            }
        }

        synchronized (kitLock) {
            kits = new ArrayList<Kit>();
            try {
                Scanner scanner = new Scanner(new File(location));
                int linenum = 0;

                while (scanner.hasNextLine()) {
                    linenum++;
                    String line = scanner.nextLine();

                    if (line.startsWith("#") || line.equals("")) {
                        continue;
                    }
                    String[] split = line.split(":");

                    if (split.length < 4) {
                        log.log(Level.SEVERE, String.format("Problem while reading %s (Line %d violates the syntax)", location, linenum));
                        continue;
                    }
                    String name = split[0];
                    String[] ids = split[1].split(",");
                    int delay;

                    try {
                        delay = Integer.parseInt(split[2]);
                    } catch (Exception exc) {
                        log.log(Level.SEVERE, String.format("Problem while reading %s (Line %d violates the syntax)", location, linenum));
                        continue;
                    }
                    String group = "";

                    if (split.length == 4) {
                        group = split[3];
                    }
                    Kit kit = new Kit();

                    kit.Name = name;
                    kit.IDs = new HashMap<String, Integer>();
                    for (String str : ids) {
                        String id = "";
                        int amount = 1;

                        if (str.contains(" ")) {
                            id = str.split(" ")[0];
                            try {
                                amount = Integer.parseInt(str.split(" ")[1]);
                            } catch (Exception exc) {
                                log.log(Level.SEVERE, String.format("Problem while reading %s (Line %d violates the syntax)", location, linenum));
                                continue;
                            }
                        } else {
                            id = str;
                        }
                        kit.IDs.put(id, amount);
                    }
                    kit.Delay = delay;
                    kit.Group = group;
                    kits.add(kit);
                }
                scanner.close();
            } catch (Exception e) {
                log.log(Level.SEVERE, String.format("Exception while reading %s", location), e);
            }
        }
    }

    @Override
    public void loadHomes() {
        synchronized (homeLock) {
            homes = new ArrayList<Warp>();
            if (!etc.getInstance().canSaveHomes()) {
                return;
            }

            String location = etc.getInstance().getHomeLocation();

            if (new File(location).exists()) {
                try {
                    Scanner scanner = new Scanner(new File(location));

                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();

                        if (line.startsWith("#") || line.equals("")) {
                            continue;
                        }
                        String[] split = line.split(":");

                        if (split.length < 4) {
                            continue;
                        }
                        Location loc = new Location();

                        loc.x = Double.parseDouble(split[1]);
                        loc.y = Double.parseDouble(split[2]);
                        loc.z = Double.parseDouble(split[3]);
                        if (split.length >= 6) {
                            loc.rotX = Float.parseFloat(split[4]);
                            loc.rotY = Float.parseFloat(split[5]);
                        }
                        Warp home = new Warp();

                        home.Name = split[0];
                        home.Location = loc;
                        if (split.length >= 7) {
                            home.Group = split[6];
                        } else {
                            home.Group = "";
                        }
                        homes.add(home);
                    }
                    scanner.close();
                } catch (Exception e) {
                    log.log(Level.SEVERE, String.format("Exception while reading %s", location), e);
                }
            }
        }
    }

    @Override
    public void loadWarps() {
        synchronized (warpLock) {
            warps = new ArrayList<Warp>();
            String location = etc.getInstance().getWarpLocation();

            if (new File(location).exists()) {
                try {
                    Scanner scanner = new Scanner(new File(location));

                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();

                        if (line.startsWith("#") || line.equals("")) {
                            continue;
                        }
                        String[] split = line.split("\\:");

                        if (split.length < 4) {
                            continue;
                        }

                        Location loc = new Location();

                        loc.x = Double.parseDouble(split[1]);
                        loc.y = Double.parseDouble(split[2]);
                        loc.z = Double.parseDouble(split[3]);
                        if (split.length >= 6) {
                            loc.rotX = Float.parseFloat(split[4]);
                            loc.rotY = Float.parseFloat(split[5]);
                        }

                        int posShift = 0;

                        if (split.length >= 7 && split[6].matches("0|-1")) {
                            loc.dimension = Integer.parseInt(split[6]);
                            posShift++;
                        }

                        Warp warp = new Warp();

                        warp.Name = split[0].replace("\\:", ":");
                        warp.Location = loc;

                        if (split.length >= 7 + posShift) {
                            warp.Group = split[6 + posShift];
                        } else {
                            warp.Group = "";
                        }

                        warps.add(warp);
                    }
                    scanner.close();
                } catch (Exception e) {
                    log.log(Level.SEVERE, String.format("Exception while reading %s", location), e);
                }
            }
        }
    }

    @Override
    public void loadItems() {
        String location = etc.getInstance().getItemLocation();

        if (!(new File(location).exists())) {
            FileWriter writer = null;

            try {
                writer = new FileWriter(location);
                writer.write("air:0" + LINE_SEP);
                writer.write("rock:1" + LINE_SEP);
                writer.write("stone:1" + LINE_SEP);
                writer.write("grass:2" + LINE_SEP);
                writer.write("dirt:3" + LINE_SEP);
                writer.write("cobblestone:4" + LINE_SEP);
                writer.write("cobble:4" + LINE_SEP);
                writer.write("wood:5" + LINE_SEP);
                writer.write("sapling:6" + LINE_SEP);
                writer.write("adminium:7" + LINE_SEP);
                writer.write("bedrock:7" + LINE_SEP);
                writer.write("water:8" + LINE_SEP);
                writer.write("stillwater:9" + LINE_SEP);
                writer.write("swater:9" + LINE_SEP);
                writer.write("lava:10" + LINE_SEP);
                writer.write("stilllava:11" + LINE_SEP);
                writer.write("slava:11" + LINE_SEP);
                writer.write("sand:12" + LINE_SEP);
                writer.write("gravel:13" + LINE_SEP);
                writer.write("goldore:14" + LINE_SEP);
                writer.write("ironore:15" + LINE_SEP);
                writer.write("coalore:16" + LINE_SEP);
                writer.write("tree:17" + LINE_SEP);
                writer.write("log:17" + LINE_SEP);
                writer.write("leaves:18" + LINE_SEP);
                writer.write("sponge:19" + LINE_SEP);
                writer.write("glass:20" + LINE_SEP);
                writer.write("lapislazuliore:21" + LINE_SEP);
                writer.write("lapislazuliblock:22" + LINE_SEP);
                writer.write("dispenser:23" + LINE_SEP);
                writer.write("sandstone:24" + LINE_SEP);
                writer.write("noteblock:25" + LINE_SEP);
                writer.write("poweredrail:27" + LINE_SEP);
                writer.write("boosterrail:27" + LINE_SEP);
                writer.write("detectorrail:28" + LINE_SEP);
                writer.write("stickypiston:29" + LINE_SEP);
                writer.write("cobweb:30" + LINE_SEP);
                writer.write("tallgrass:31" + LINE_SEP);
                writer.write("deadshrub:32" + LINE_SEP);
                writer.write("piston:33" + LINE_SEP);
                writer.write("cloth:35" + LINE_SEP);
                writer.write("flower:37" + LINE_SEP);
                writer.write("rose:38" + LINE_SEP);
                writer.write("brownmushroom:39" + LINE_SEP);
                writer.write("redmushroom:40" + LINE_SEP);
                writer.write("gold:41" + LINE_SEP);
                writer.write("goldblock:41" + LINE_SEP);
                writer.write("iron:42" + LINE_SEP);
                writer.write("ironblock:42" + LINE_SEP);
                writer.write("doublestair:43" + LINE_SEP);
                writer.write("stair:44" + LINE_SEP);
                writer.write("step:44" + LINE_SEP);
                writer.write("brickblock:45" + LINE_SEP);
                writer.write("brickwall:45" + LINE_SEP);
                writer.write("tnt:46" + LINE_SEP);
                writer.write("bookshelf:47" + LINE_SEP);
                writer.write("bookcase:47" + LINE_SEP);
                writer.write("mossycobblestone:48" + LINE_SEP);
                writer.write("mossy:48" + LINE_SEP);
                writer.write("obsidian:49" + LINE_SEP);
                writer.write("torch:50" + LINE_SEP);
                writer.write("fire:51" + LINE_SEP);
                writer.write("mobspawner:52" + LINE_SEP);
                writer.write("woodstairs:53" + LINE_SEP);
                writer.write("chest:54" + LINE_SEP);
                writer.write("redstonedust:55" + LINE_SEP);
                writer.write("redstonewire:55" + LINE_SEP);
                writer.write("diamondore:56" + LINE_SEP);
                writer.write("diamondblock:57" + LINE_SEP);
                writer.write("workbench:58" + LINE_SEP);
                writer.write("crop:59" + LINE_SEP);
                writer.write("crops:59" + LINE_SEP);
                writer.write("soil:60" + LINE_SEP);
                writer.write("furnace:61" + LINE_SEP);
                writer.write("litfurnace:62" + LINE_SEP);
                writer.write("signblock:63" + LINE_SEP);
                writer.write("wooddoorblock:64" + LINE_SEP);
                writer.write("ladder:65" + LINE_SEP);
                writer.write("rails:66" + LINE_SEP);
                writer.write("rail:66" + LINE_SEP);
                writer.write("track:66" + LINE_SEP);
                writer.write("tracks:66" + LINE_SEP);
                writer.write("cobblestonestairs:67" + LINE_SEP);
                writer.write("stairs:67" + LINE_SEP);
                writer.write("signblocktop:68" + LINE_SEP);
                writer.write("wallsign:68" + LINE_SEP);
                writer.write("lever:69" + LINE_SEP);
                writer.write("rockplate:70" + LINE_SEP);
                writer.write("stoneplate:70" + LINE_SEP);
                writer.write("irondoorblock:71" + LINE_SEP);
                writer.write("woodplate:72" + LINE_SEP);
                writer.write("redstoneore:73" + LINE_SEP);
                writer.write("redstoneorealt:74" + LINE_SEP);
                writer.write("redstonetorchoff:75" + LINE_SEP);
                writer.write("redstonetorchon:76" + LINE_SEP);
                writer.write("button:77" + LINE_SEP);
                writer.write("snow:78" + LINE_SEP);
                writer.write("ice:79" + LINE_SEP);
                writer.write("snowblock:80" + LINE_SEP);
                writer.write("cactus:81" + LINE_SEP);
                writer.write("clayblock:82" + LINE_SEP);
                writer.write("reedblock:83" + LINE_SEP);
                writer.write("jukebox:84" + LINE_SEP);
                writer.write("fence:85" + LINE_SEP);
                writer.write("pumpkin:86" + LINE_SEP);
                writer.write("netherstone:87" + LINE_SEP);
                writer.write("slowsand:88" + LINE_SEP);
                writer.write("lightstone:89" + LINE_SEP);
                writer.write("portal:90" + LINE_SEP);
                writer.write("jackolantern:91" + LINE_SEP);
                writer.write("jacko:91" + LINE_SEP);
                writer.write("lockedchest:95" + LINE_SEP);
                writer.write("trapdoor:96" + LINE_SEP);
                writer.write("silverblock:97" + LINE_SEP);
                writer.write("stonebrick:98" + LINE_SEP);
                writer.write("hugebrownmushroom:99" + LINE_SEP);
                writer.write("hugeredmushroom:100" + LINE_SEP);
                writer.write("ironbars:101" + LINE_SEP);
                writer.write("glasspane:102" + LINE_SEP);
                writer.write("melonblock:103" + LINE_SEP);
                writer.write("pumpkinstem:104" + LINE_SEP);
                writer.write("melonstem:105" + LINE_SEP);
                writer.write("vine:106" + LINE_SEP);
                writer.write("fencegate:107" + LINE_SEP);
                writer.write("brickstair:108" + LINE_SEP);
                writer.write("stonebrickstair:109" + LINE_SEP);
                writer.write("mycelium:110" + LINE_SEP);
                writer.write("lilypad:111" + LINE_SEP);
                writer.write("netherbrick:112" + LINE_SEP);
                writer.write("netherbrickfence:113" + LINE_SEP);
                writer.write("netherbrickstairs:114" + LINE_SEP);
                writer.write("netherwart:115" + LINE_SEP);
                writer.write("enchantmenttable:116" + LINE_SEP);
                writer.write("brewingstand:117" + LINE_SEP);
                writer.write("cauldron:118" + LINE_SEP);
                writer.write("endportal:119" + LINE_SEP);
                writer.write("endportalframe:120" + LINE_SEP);
                writer.write("endstone:121" + LINE_SEP);
                writer.write("dragonegg:122" + LINE_SEP);
                writer.write("redstonelampoff:123" + LINE_SEP);
                writer.write("redstonelampon:123" + LINE_SEP);
                writer.write("ironshovel:256" + LINE_SEP);
                writer.write("ironspade:256" + LINE_SEP);
                writer.write("ironpickaxe:257" + LINE_SEP);
                writer.write("ironpick:257" + LINE_SEP);
                writer.write("ironaxe:258" + LINE_SEP);
                writer.write("flintandsteel:259" + LINE_SEP);
                writer.write("lighter:259" + LINE_SEP);
                writer.write("apple:260" + LINE_SEP);
                writer.write("bow:261" + LINE_SEP);
                writer.write("arrow:262" + LINE_SEP);
                writer.write("coal:263" + LINE_SEP);
                writer.write("diamond:264" + LINE_SEP);
                writer.write("ironbar:265" + LINE_SEP);
                writer.write("goldbar:266" + LINE_SEP);
                writer.write("ironsword:267" + LINE_SEP);
                writer.write("woodsword:268" + LINE_SEP);
                writer.write("woodshovel:269" + LINE_SEP);
                writer.write("woodspade:269" + LINE_SEP);
                writer.write("woodpickaxe:270" + LINE_SEP);
                writer.write("woodpick:270" + LINE_SEP);
                writer.write("woodaxe:271" + LINE_SEP);
                writer.write("stonesword:272" + LINE_SEP);
                writer.write("stoneshovel:273" + LINE_SEP);
                writer.write("stonespade:273" + LINE_SEP);
                writer.write("stonepickaxe:274" + LINE_SEP);
                writer.write("stonepick:274" + LINE_SEP);
                writer.write("stoneaxe:275" + LINE_SEP);
                writer.write("diamondsword:276" + LINE_SEP);
                writer.write("diamondshovel:277" + LINE_SEP);
                writer.write("diamondspade:277" + LINE_SEP);
                writer.write("diamondpickaxe:278" + LINE_SEP);
                writer.write("diamondpick:278" + LINE_SEP);
                writer.write("diamondaxe:279" + LINE_SEP);
                writer.write("stick:280" + LINE_SEP);
                writer.write("bowl:281" + LINE_SEP);
                writer.write("bowlwithsoup:282" + LINE_SEP);
                writer.write("soupbowl:282" + LINE_SEP);
                writer.write("soup:282" + LINE_SEP);
                writer.write("goldsword:283" + LINE_SEP);
                writer.write("goldshovel:284" + LINE_SEP);
                writer.write("goldspade:284" + LINE_SEP);
                writer.write("goldpickaxe:285" + LINE_SEP);
                writer.write("goldpick:285" + LINE_SEP);
                writer.write("goldaxe:286" + LINE_SEP);
                writer.write("string:287" + LINE_SEP);
                writer.write("feather:288" + LINE_SEP);
                writer.write("gunpowder:289" + LINE_SEP);
                writer.write("woodhoe:290" + LINE_SEP);
                writer.write("stonehoe:291" + LINE_SEP);
                writer.write("ironhoe:292" + LINE_SEP);
                writer.write("diamondhoe:293" + LINE_SEP);
                writer.write("goldhoe:294" + LINE_SEP);
                writer.write("seeds:295" + LINE_SEP);
                writer.write("wheat:296" + LINE_SEP);
                writer.write("bread:297" + LINE_SEP);
                writer.write("leatherhelmet:298" + LINE_SEP);
                writer.write("leatherchestplate:299" + LINE_SEP);
                writer.write("leatherpants:300" + LINE_SEP);
                writer.write("leatherboots:301" + LINE_SEP);
                writer.write("chainmailhelmet:302" + LINE_SEP);
                writer.write("chainmailchestplate:303" + LINE_SEP);
                writer.write("chainmailpants:304" + LINE_SEP);
                writer.write("chainmailboots:305" + LINE_SEP);
                writer.write("ironhelmet:306" + LINE_SEP);
                writer.write("ironchestplate:307" + LINE_SEP);
                writer.write("ironpants:308" + LINE_SEP);
                writer.write("ironboots:309" + LINE_SEP);
                writer.write("diamondhelmet:310" + LINE_SEP);
                writer.write("diamondchestplate:311" + LINE_SEP);
                writer.write("diamondpants:312" + LINE_SEP);
                writer.write("diamondboots:313" + LINE_SEP);
                writer.write("goldhelmet:314" + LINE_SEP);
                writer.write("goldchestplate:315" + LINE_SEP);
                writer.write("goldpants:316" + LINE_SEP);
                writer.write("goldboots:317" + LINE_SEP);
                writer.write("flint:318" + LINE_SEP);
                writer.write("meat:319" + LINE_SEP);
                writer.write("pork:319" + LINE_SEP);
                writer.write("cookedmeat:320" + LINE_SEP);
                writer.write("cookedpork:320" + LINE_SEP);
                writer.write("painting:321" + LINE_SEP);
                writer.write("paintings:321" + LINE_SEP);
                writer.write("goldenapple:322" + LINE_SEP);
                writer.write("sign:323" + LINE_SEP);
                writer.write("wooddoor:324" + LINE_SEP);
                writer.write("bucket:325" + LINE_SEP);
                writer.write("waterbucket:326" + LINE_SEP);
                writer.write("lavabucket:327" + LINE_SEP);
                writer.write("minecart:328" + LINE_SEP);
                writer.write("saddle:329" + LINE_SEP);
                writer.write("irondoor:330" + LINE_SEP);
                writer.write("redstonedust:331" + LINE_SEP);
                writer.write("snowball:332" + LINE_SEP);
                writer.write("boat:333" + LINE_SEP);
                writer.write("leather:334" + LINE_SEP);
                writer.write("milkbucket:335" + LINE_SEP);
                writer.write("brick:336" + LINE_SEP);
                writer.write("clay:337" + LINE_SEP);
                writer.write("reed:338" + LINE_SEP);
                writer.write("paper:339" + LINE_SEP);
                writer.write("book:340" + LINE_SEP);
                writer.write("slimeorb:341" + LINE_SEP);
                writer.write("storageminecart:342" + LINE_SEP);
                writer.write("poweredminecart:343" + LINE_SEP);
                writer.write("egg:344" + LINE_SEP);
                writer.write("compass:345" + LINE_SEP);
                writer.write("fishingrod:346" + LINE_SEP);
                writer.write("watch:347" + LINE_SEP);
                writer.write("lightstonedust:348" + LINE_SEP);
                writer.write("lightdust:348" + LINE_SEP);
                writer.write("rawfish:349" + LINE_SEP);
                writer.write("fish:349" + LINE_SEP);
                writer.write("cookedfish:350" + LINE_SEP);
                writer.write("dye:351" + LINE_SEP);
                writer.write("bone:352" + LINE_SEP);
                writer.write("sugar:353" + LINE_SEP);
                writer.write("cake:354" + LINE_SEP);
                writer.write("bed:355" + LINE_SEP);
                writer.write("repeater:356" + LINE_SEP);
                writer.write("cookie:357" + LINE_SEP);
                writer.write("map:358" + LINE_SEP);
                writer.write("shears:359" + LINE_SEP);
                writer.write("melonslice:360" + LINE_SEP);
                writer.write("pumpkinseeds:361" + LINE_SEP);
                writer.write("melonseeds:362" + LINE_SEP);
                writer.write("rawbeef:363" + LINE_SEP);
                writer.write("steak:364" + LINE_SEP);
                writer.write("rawchicken:365" + LINE_SEP);
                writer.write("cookedchicken:366" + LINE_SEP);
                writer.write("rottenflesh:367" + LINE_SEP);
                writer.write("enderpearl:368" + LINE_SEP);
                writer.write("blazerod:369" + LINE_SEP);
                writer.write("ghasttear:370" + LINE_SEP);
                writer.write("goltnugget:371" + LINE_SEP);
                writer.write("netherwart:372" + LINE_SEP);
                writer.write("potion:373" + LINE_SEP);
                writer.write("glassbottle:374" + LINE_SEP);
                writer.write("spidereye:375" + LINE_SEP);
                writer.write("fermentedspidereye:376" + LINE_SEP);
                writer.write("blazepowder:377" + LINE_SEP);
                writer.write("magmacream:378" + LINE_SEP);
                writer.write("brewingstand:379" + LINE_SEP);
                writer.write("eyeofender:381" + LINE_SEP);
                writer.write("glisteringmelon:382" + LINE_SEP);
                writer.write("spawnegg:383" + LINE_SEP);
                writer.write("monsterplacer:383" + LINE_SEP);
                writer.write("bottleoenchanting:384" + LINE_SEP);
                writer.write("firecharge:385" + LINE_SEP);
                writer.write("goldrecord:2256" + LINE_SEP);
                writer.write("greenrecord:2257" + LINE_SEP);
                writer.write("blocksrecord:2258" + LINE_SEP);
                writer.write("chirprecord:2259" + LINE_SEP);
                writer.write("farrecord:2260" + LINE_SEP);
                writer.write("mallrecord:2261" + LINE_SEP);
                writer.write("mellohirecord:2262" + LINE_SEP);
                writer.write("stalrecord:2263" + LINE_SEP);
                writer.write("stradrecord:2264" + LINE_SEP);
                writer.write("wardrecord:2265" + LINE_SEP);
                writer.write("11record:2266" + LINE_SEP);
            } catch (Exception e) {
                log.log(Level.SEVERE, String.format("Exception while creating %s", location), e);
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        log.log(Level.SEVERE, String.format("Exception while closing writer for %s", location), e);
                    }
                }
            }
        }

        // This, for sure, now exists.
        synchronized (itemLock) {
            items = new HashMap<String, Integer>();
            try {
                Scanner scanner = new Scanner(new File(location));
                int linenum = 0;

                while (scanner.hasNextLine()) {
                    linenum++;
                    String line = scanner.nextLine();

                    if (line.startsWith("#")) {
                        continue;
                    }
                    if (line.equals("")) {
                        continue;
                    }
                    String[] split = line.split(":");

                    if (split.length < 2) {
                        log.log(Level.SEVERE, String.format("Problem while reading %s (Line %d violates the syntax)", location, linenum));
                        continue;
                    }
                    String name = split[0];
                    int id;

                    try {
                        id = Integer.parseInt(split[1]);
                    } catch (Exception exc) {
                        log.log(Level.SEVERE, String.format("Problem while reading %s (Line %d violates the syntax)", location, linenum));
                        continue;
                    }
                    items.put(name, id);
                }
                scanner.close();
            } catch (Exception e) {
                log.log(Level.SEVERE, String.format("Exception while reading %s (Are you sure you formatted it correctly?)", location), e);
            }
        }
    }

    @Override
    public void loadBanList() {
        String location = etc.getInstance().getBanListLoc();
        
        if (!new File(location).exists()) {
            FileWriter writer = null;

            try {
                writer = new FileWriter(location);
                writer.write("# Add your bans here (When adding your entry DO NOT include #!)" + LINE_SEP);
                writer.write("# The format is:" + LINE_SEP);
                writer.write("# NAME/IP:REASON:TIMESTAMP" + LINE_SEP);
                writer.write("# Timestamp is a UNIX timestamp, i.e. seconds since 01-01-1970 00:00" + LINE_SEP);
                writer.write("# Reason and timestamp are optional." + LINE_SEP);
                writer.write("# Examples:" + LINE_SEP);
                writer.write("#N0tch:Impersonating Notch" + LINE_SEP);
                writer.write("#UnwantedUser1337" + LINE_SEP);
                writer.write("#BobTheBuilder:No building until April Fools.:1333238400" + LINE_SEP);
            } catch (Exception e) {
                log.log(Level.SEVERE, String.format("Exception while creating %s", location), e);
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    log.log(Level.SEVERE, String.format("Exception while closing writer for %s", location), e);
                }
            }
        }
        synchronized (banLock) {
            bans = new ArrayList<Ban>();

            try {
                Scanner scanner = new Scanner(new File(location));

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    if (line.startsWith("#") || line.equals("")) {
                        continue;
                    }
                    String[] split = line.split(":");
                    Ban ban = new Ban();


                    if (split[0].contains("."))
                        ban.setIp(split[0]);
                    else
                        ban.setName(split[0]);
                    if (split.length >= 2) {
                        ban.setReason(split[1]);
                        if (split.length >= 3)
                            ban.setTimestamp(Integer.parseInt(split[2]));
                    } else
                        ban.setReason(etc.getInstance().getDefaultBanMessage());
                    
                    bans.add(ban);
                }
                scanner.close();
            } catch (Exception e) {
                log.log(Level.SEVERE, String.format("Exception while reading %s", location), e);
            }
        }
    }

    @Override
    public void loadEnderBlocks() {
        String location = etc.getInstance().getEnderBlocksLocation();

        if (!new File(location).exists()) {
            FileWriter writer = null;

            try {
                writer = new FileWriter(location);
                writer.write("#Add block IDs the endermen can pick up." + LINE_SEP);
                writer.write("1" + LINE_SEP);
                writer.write("2" + LINE_SEP);
                writer.write("3" + LINE_SEP);
                writer.write("4" + LINE_SEP);
                writer.write("5" + LINE_SEP);
                writer.write("12" + LINE_SEP);
                writer.write("13" + LINE_SEP);
                writer.write("14" + LINE_SEP);
                writer.write("15" + LINE_SEP);
                writer.write("16" + LINE_SEP);
                writer.write("17" + LINE_SEP);
                writer.write("18" + LINE_SEP);
                writer.write("19" + LINE_SEP);
                writer.write("20" + LINE_SEP);
                writer.write("21" + LINE_SEP);
                writer.write("22" + LINE_SEP);
                writer.write("24" + LINE_SEP);
                writer.write("35" + LINE_SEP);
                writer.write("37" + LINE_SEP);
                writer.write("38" + LINE_SEP);
                writer.write("39" + LINE_SEP);
                writer.write("40" + LINE_SEP);
                writer.write("41" + LINE_SEP);
                writer.write("42" + LINE_SEP);
                writer.write("45" + LINE_SEP);
                writer.write("46" + LINE_SEP);
                writer.write("47" + LINE_SEP);
                writer.write("48" + LINE_SEP);
                writer.write("56" + LINE_SEP);
                writer.write("57" + LINE_SEP);
                writer.write("58" + LINE_SEP);
                writer.write("73" + LINE_SEP);
                writer.write("74" + LINE_SEP);
                writer.write("79" + LINE_SEP);
                writer.write("81" + LINE_SEP);
                writer.write("82" + LINE_SEP);
                writer.write("86" + LINE_SEP);
                writer.write("87" + LINE_SEP);
                writer.write("88" + LINE_SEP);
                writer.write("89" + LINE_SEP);
                writer.write("91" + LINE_SEP);
                writer.write("98" + LINE_SEP);
                writer.write("99" + LINE_SEP);
                writer.write("100" + LINE_SEP);
                writer.write("103" + LINE_SEP);
            } catch (Exception e) {
                log.log(Level.SEVERE, String.format("Exception while creating %s", location), e);
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                }
            }
        }

        synchronized (enderBlocksLock) {
            enderBlocks = new ArrayList<Integer>();
            try {
                Scanner scanner = new Scanner(new File(location));
                int linenum = 0;

                while (scanner.hasNextLine()) {
                    linenum++;
                    String line = scanner.nextLine();

                    if (line.startsWith("#") || line.equals("")) {
                        continue;
                    }

                    int id;

                    try {
                        id = Integer.parseInt(line);
                    } catch (Exception exc) {
                        log.log(Level.SEVERE, String.format("Problem while reading %s (Line %d violates the syntax)", location, linenum));
                        continue;
                    }
                    enderBlocks.add(id);
                }
                scanner.close();
            } catch (Exception e) {
                log.log(Level.SEVERE, String.format("Exception while reading %s", location), e);
            }
            for (int i = 0; i < 256; i += 1) {
                OEntityEnderman.setHoldable(i, false);
            }
            for (Integer id : enderBlocks) {
                OEntityEnderman.setHoldable(id, true);
            }
        }
    }

    @Override
    public void loadAntiXRayBlocks() {
        String location = etc.getInstance().getAntiXRayBlocksLocation();

        if (!new File(location).exists()) {
            FileWriter writer = null;

            try {
                writer = new FileWriter(location);
                writer.write("#Add block IDs the anti xray will hide." + LINE_SEP);
                writer.write("14" + LINE_SEP);
                writer.write("15" + LINE_SEP);
                writer.write("16" + LINE_SEP);
                writer.write("21" + LINE_SEP);
                writer.write("56" + LINE_SEP);
                writer.write("73" + LINE_SEP);
            } catch (Exception e) {
                log.log(Level.SEVERE, String.format("Exception while creating %s", location), e);
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                }
            }
        }

        synchronized (antiXRayBlocksLock) {
            antiXRayBlocks = new ArrayList<Integer>();
            try {
                Scanner scanner = new Scanner(new File(location));
                int linenum = 0;

                while (scanner.hasNextLine()) {
                    linenum++;
                    String line = scanner.nextLine();

                    if (line.startsWith("#") || line.equals("")) {
                        continue;
                    }

                    int id;

                    try {
                        id = Integer.parseInt(line);
                    } catch (Exception exc) {
                        log.log(Level.SEVERE, String.format("Problem while reading %s (Line %d violates the syntax)", location, linenum));
                        continue;
                    }
                    antiXRayBlocks.add(id);
                }
                scanner.close();
            } catch (Exception e) {
                log.log(Level.SEVERE, String.format("Exception while reading %s", location), e);
            }
        }
    }

    // Users
    @Override
    public void addPlayer(Player player) {
        String loc = etc.getInstance().getUsersLocation();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(loc, true));
            StringBuilder builder = new StringBuilder();

            // #NAME:GROUPS:ADMIN/UNRESTRICTED:COLOR:COMMANDS
            builder.append(player.getName());
            builder.append(":");
            builder.append(etc.combineSplit(0, player.getGroups(), ","));
            builder.append(":");
            if (player.getAdmin()) {
                builder.append("2");
            } else if (player.ignoreRestrictions()) {
                builder.append("1");
            } else if (!player.canModifyWorld()) {
                builder.append("-1");
            } else {
                builder.append("0");
            }
            builder.append(":");
            builder.append(player.getPrefix());
            builder.append(":");
            builder.append(etc.combineSplit(0, player.getCommands(), ","));
            bw.append(builder.toString());
            bw.newLine();
            bw.close();
        } catch (Exception ex) {
            log.log(Level.SEVERE, String.format("Exception while writing new user to %s", loc), ex);
        }
    }

    @Override
    public void modifyPlayer(Player player) {
        String loc = etc.getInstance().getUsersLocation();

        try {
            // Now to save...
            BufferedReader reader = new BufferedReader(new FileReader(new File(loc)));
            StringBuilder toWrite = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                if (!line.split(":")[0].equalsIgnoreCase(player.getOfflineName())) {
                    toWrite.append(line).append(LINE_SEP);
                } else {
                    StringBuilder builder = new StringBuilder();

                    builder.append(line.split(":")[0]);
                    builder.append(":");
                    builder.append(etc.combineSplit(0, player.getGroups(), ","));
                    builder.append(":");
                    if (player.getAdmin()) {
                        builder.append("2");
                    } else if (player.ignoreRestrictions()) {
                        builder.append("1");
                    } else if (!player.canModifyWorld()) {
                        builder.append("-1");
                    } else {
                        builder.append("0");
                    }
                    builder.append(":");
                    builder.append(player.getPrefix());
                    builder.append(":");
                    builder.append(etc.combineSplit(0, player.getCommands(), ","));
                    toWrite.append(builder.toString()).append(LINE_SEP);
                }
            }
            reader.close();

            FileWriter writer = new FileWriter(loc);

            writer.write(toWrite.toString());
            writer.close();
        } catch (Exception ex) {
            log.log(Level.SEVERE, String.format("Exception while editing user in %s", loc), ex);
        }
    }

    @Override
    public boolean doesPlayerExist(String player) {
        String location = etc.getInstance().getUsersLocation();

        try {
            Scanner scanner = new Scanner(new File(location));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.startsWith("#") || line.equals("") || line.startsWith("﻿")) {
                    continue;
                }
                String[] split = line.split(":");

                if (!split[0].equalsIgnoreCase(player)) {
                    continue;
                }
                return true;
            }
            scanner.close();
        } catch (Exception e) {
            log.log(Level.SEVERE, String.format("Exception while reading %s (Are you sure you formatted it correctly?)", location), e);
        }
        return false;
    }

    @Override
    public Player getPlayer(String name) {
        Player player = new Player();
        String location = etc.getInstance().getUsersLocation();

        try {
            Scanner scanner = new Scanner(new File(location));
            int linenum = 0;

            while (scanner.hasNextLine()) {
                linenum++;
                String line = scanner.nextLine();

                if (line.startsWith("#") || line.equals("") || line.startsWith("﻿")) {
                    continue;
                }
                String[] split = line.split(":");

                if (!split[0].equalsIgnoreCase(name)) {
                    continue;
                }

                if (split.length < 2) {
                    log.log(Level.SEVERE, String.format("Problem while reading %s (Line %d violates the syntax)", location, linenum));
                    continue;
                }
                player.setGroups(split[1].split(","));

                if (split.length >= 3) {
                    if (split[2].equals("1")) {
                        player.setIgnoreRestrictions(true);
                    } else if (split[2].equals("2")) {
                        player.setAdmin(true);
                    } else if (split[2].equals("-1")) {
                        player.setCanModifyWorld(false);
                    }
                }

                if (split.length >= 4) {
                    player.setPrefix(split[3]);
                }
                if (split.length >= 5) {
                    player.setCommands(split[4].split(","));
                }
                if (split.length >= 6) {
                    player.setIps(split[5].split(","));
                }
            }
            scanner.close();
        } catch (Exception e) {
            log.log(Level.SEVERE, String.format("Exception while reading %s (Are you sure you formatted it correctly?)", location), e);
        }
        return player;
    }

    // Groups
    @Override
    public void addGroup(Group group) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void modifyGroup(Group group) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // Kits
    @Override
    public void addKit(Kit kit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void modifyKit(Kit kit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // Homes
    @Override
    public void addHome(Warp home) {
        String homeLoc = etc.getInstance().getHomeLocation();

        try {
            if (etc.getInstance().canSaveHomes()) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(homeLoc, true));
                StringBuilder builder = new StringBuilder();

                builder.append(home.Name);
                builder.append(":");
                builder.append(home.Location.x);
                builder.append(":");
                builder.append(home.Location.y);
                builder.append(":");
                builder.append(home.Location.z);
                builder.append(":");
                builder.append(home.Location.rotX);
                builder.append(":");
                builder.append(home.Location.rotY);
                builder.append(":");
                builder.append(home.Group);
                bw.append(builder.toString());
                bw.newLine();
                bw.close();
            }
            synchronized (homeLock) {
                homes.add(home);
            }
        } catch (Exception e2) {
            log.log(Level.SEVERE, String.format("Exception while writing new user home to %s", homeLoc), e2);
        }
    }

    @Override
    public void changeHome(Warp home) {
        synchronized (homeLock) {
            Warp toRem = null;

            for (Warp h : homes) {
                if (h.Name.equalsIgnoreCase(home.Name)) {
                    toRem = h;
                }
            }
            if (toRem != null) {
                homes.remove(toRem);
            }
            homes.add(home);
        }
        FileWriter writer = null;
        String homeLoc = etc.getInstance().getHomeLocation();

        try {
            // Now to save...
            if (etc.getInstance().canSaveHomes()) {
                BufferedReader reader = new BufferedReader(new FileReader(new File(homeLoc)));
                StringBuilder toWrite = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    if (!line.split(":")[0].equalsIgnoreCase(home.Name)) {
                        toWrite.append(line).append(LINE_SEP);
                    } else {
                        StringBuilder builder = new StringBuilder();

                        builder.append(home.Name);
                        builder.append(":");
                        builder.append(home.Location.x);
                        builder.append(":");
                        builder.append(home.Location.y);
                        builder.append(":");
                        builder.append(home.Location.z);
                        builder.append(":");
                        builder.append(home.Location.rotX);
                        builder.append(":");
                        builder.append(home.Location.rotY);
                        builder.append(":");
                        builder.append(home.Group);
                        toWrite.append(builder.toString()).append(LINE_SEP);
                    }
                }
                reader.close();

                writer = new FileWriter(homeLoc);
                writer.write(toWrite.toString());
                writer.close();
            }
        } catch (Exception e1) {
            log.log(Level.SEVERE, String.format("Exception while editing user home in %s", homeLoc), e1);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    // Warps
    @Override
    public void addWarp(Warp warp) {
        String warpLoc = etc.getInstance().getWarpLocation();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(warpLoc, true));
            StringBuilder builder = new StringBuilder();

            builder.append(warp.Name.replace(":", "\\:"));
            builder.append(":");
            builder.append(warp.Location.x);
            builder.append(":");
            builder.append(warp.Location.y);
            builder.append(":");
            builder.append(warp.Location.z);
            builder.append(":");
            builder.append(warp.Location.rotX);
            builder.append(":");
            builder.append(warp.Location.rotY);
            builder.append(":");
            builder.append(warp.Location.dimension);
            builder.append(":");
            builder.append(warp.Group);
            bw.append(builder.toString());
            bw.newLine();
            bw.close();
            synchronized (warpLock) {
                warps.add(warp);
            }
        } catch (Exception e2) {
            log.log(Level.SEVERE, String.format("Exception while writing new warp to %s", warpLoc), e2);
        }
    }

    @Override
    public void changeWarp(Warp warp) {
        synchronized (warpLock) {
            Warp toRem = null;

            for (Warp h : warps) {
                if (h.Name.equalsIgnoreCase(warp.Name)) {
                    toRem = h;
                }
            }
            if (toRem != null) {
                warps.remove(toRem);
            }
            warps.add(warp);
        }
        FileWriter writer = null;
        String warpLoc = etc.getInstance().getWarpLocation();

        try {
            // Now to save...
            BufferedReader reader = new BufferedReader(new FileReader(new File(warpLoc)));
            StringBuilder toWrite = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                if (!line.split("[^\\\\]:")[0].equalsIgnoreCase(warp.Name)) {
                    toWrite.append(line).append(LINE_SEP);
                } else {
                    StringBuilder builder = new StringBuilder();

                    builder.append(warp.Name.replace(":", "\\:"));
                    builder.append(":");
                    builder.append(warp.Location.x);
                    builder.append(":");
                    builder.append(warp.Location.y);
                    builder.append(":");
                    builder.append(warp.Location.z);
                    builder.append(":");
                    builder.append(warp.Location.rotX);
                    builder.append(":");
                    builder.append(warp.Location.rotY);
                    builder.append(":");
                    builder.append(warp.Location.dimension);
                    builder.append(":");
                    builder.append(warp.Group);
                    toWrite.append(builder.toString()).append(LINE_SEP);
                }
            }
            reader.close();

            writer = new FileWriter(warpLoc);
            writer.write(toWrite.toString());
            writer.close();
        } catch (Exception e1) {
            log.log(Level.SEVERE, String.format("Exception while editing warp in %s", warpLoc), e1);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    @Override
    public void removeWarp(Warp warp) {
        FileWriter writer = null;
        String warpLoc = etc.getInstance().getWarpLocation();

        try {
            // Now to save...
            BufferedReader reader = new BufferedReader(new FileReader(new File(warpLoc)));
            StringBuilder toWrite = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                if (!line.split(":")[0].equalsIgnoreCase(warp.Name)) {
                    toWrite.append(line).append(LINE_SEP);
                }
            }
            reader.close();

            writer = new FileWriter(warpLoc);
            writer.write(toWrite.toString());
            writer.close();
        } catch (Exception e1) {
            log.log(Level.SEVERE, String.format("Exception while deleting warp from %s", warpLoc), e1);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
            }
        }

        synchronized (warpLock) {
            warps.remove(warp);
        }
    }

    // Whitelist
    @Override
    public void addToWhitelist(String name) {
        if (isUserOnWhitelist(name)) {
            return;
        }

        BufferedWriter bw = null;
        String location = etc.getInstance().getWhitelistLocation();

        try {
            bw = new BufferedWriter(new FileWriter(location, true));
            bw.newLine();
            bw.append(name);
        } catch (Exception e2) {
            log.log(Level.SEVERE, String.format("Exception while writing new user to %s", location), e2);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    @Override
    public void removeFromWhitelist(String name) {
        if (!isUserOnWhitelist(name)) {
            return;
        }

        FileWriter writer = null;
        String location = etc.getInstance().getWhitelistLocation();

        try {
            // Now to save...
            BufferedReader reader = new BufferedReader(new FileReader(new File(location)));
            String line = "";
            StringBuilder toSave = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (!line.equalsIgnoreCase(name.toLowerCase())) {
                    toSave.append(line).append(LINE_SEP);
                }
            }
            reader.close();

            writer = new FileWriter(location);
            writer.write(toSave.toString());
        } catch (Exception e1) {
            log.log(Level.SEVERE, String.format("Exception while removing player '%s' from %s", name, location), e1);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    @Override
    public boolean isUserOnWhitelist(String user) {
        String location = etc.getInstance().getWhitelistLocation();
        Player player = getPlayer(user);

        try {
            Scanner scanner = new Scanner(new File(location));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.startsWith("#") || line.equals("") || line.startsWith("﻿")) {
                    continue;
                }
                if (line.startsWith("@") && player.isInGroup(line.substring(1))) {
                    return true;
                }
                if (line.equalsIgnoreCase(user)) {
                    return true;
                }
            }
            scanner.close();
        } catch (Exception e) {
            log.log(Level.SEVERE, String.format("Exception while reading %s", location), e);
        }
        return false;
    }

    @Override
    public void addBan(Ban ban) {
        String loc = etc.getInstance().getBanListLoc();
        boolean byIp = !ban.getIp().isEmpty();
        boolean found = false;

        try {
            // Now to save...
            BufferedReader reader = new BufferedReader(new FileReader(new File(loc)));
            StringBuilder toWrite = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                toWrite.append(line).append(LINE_SEP);
            }
            
            reader.close();

            toWrite.append(byIp ? ban.getIp() : ban.getName())
                   .append(":").append(ban.getReason()).append(":")
                   .append(ban.getTimestamp()).append(LINE_SEP);

            FileWriter writer = new FileWriter(loc);

            writer.write(toWrite.toString());
            writer.close();
        } catch (Exception ex) {
            log.log(Level.SEVERE, String.format("Exception while adding ban in %s", loc), ex);
        }
        synchronized (banLock) {
            bans.add(ban);
        }
    }

    // Reservelist
    @Override
    public boolean isUserOnReserveList(String user) {
        String location = etc.getInstance().getReservelistLocation();
        Player player = getPlayer(user);

        try {
            Scanner scanner = new Scanner(new File(location));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.startsWith("#") || line.equals("") || line.startsWith("﻿")) {
                    continue;
                }
                if (line.startsWith("@") && player.isInGroup(line.substring(1))) {
                    return true;
                }
                if (line.equalsIgnoreCase(user)) {
                    return true;
                }
            }
            scanner.close();
        } catch (Exception e) {
            log.log(Level.SEVERE, String.format("Exception while reading %s", location), e);
        }
        return false;
    }

    @Override
    public void addToReserveList(String name) {
        if (isUserOnReserveList(name)) {
            return;
        }
        BufferedWriter bw = null;
        String location = etc.getInstance().getReservelistLocation();

        try {
            bw = new BufferedWriter(new FileWriter(location, true));
            bw.newLine();
            bw.append(name);
        } catch (Exception e2) {
            log.log(Level.SEVERE, String.format("Exception while writing new user to %s", location), e2);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    @Override
    public void removeFromReserveList(String name) {
        if (!isUserOnReserveList(name)) {
            return;
        }

        FileWriter writer = null;
        String location = etc.getInstance().getReservelistLocation();

        try {
            // Now to save...
            BufferedReader reader = new BufferedReader(new FileReader(new File(location)));
            String line = "";
            StringBuilder toSave = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (!line.equalsIgnoreCase(name.toLowerCase())) {
                    toSave.append(line).append(LINE_SEP);
                }
            }
            reader.close();

            writer = new FileWriter(location);
            writer.write(toSave.toString());
        } catch (Exception e1) {
            log.log(Level.SEVERE, String.format("Exception while removing player '%s' from %s", name, location), e1);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    //Grouplist
    @Override
    public List getGroupList() {
        return this.groups;
    }

    @Override
    public void loadMutedPlayers() {
        try {
            String location = etc.getInstance().getMuteListLocation();
            if (!new File(location).exists()) {
                FileWriter writer = null;

                try {
                    writer = new FileWriter(location);
                    writer.write("# Add muted users to this file. One user each line" + LINE_SEP);
                } catch (Exception e) {
                    log.log(Level.SEVERE, String.format("Exception while creating %s", location), e);
                } finally {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                    } catch (IOException e) {
                        log.log(Level.SEVERE, String.format("Exception while closing writer for %s", location), e);
                    }
                }
            }
            String line = "";
            BufferedReader reader = new BufferedReader(new FileReader(new File(location)));
            while ((line = reader.readLine()) != null) {
                this.mutedPlayers.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setPlayerToMuteList(String name) {
        this.mutedPlayers.add(name);
        String location = etc.getInstance().getMuteListLocation();
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(location));
            for (String key : this.mutedPlayers) {
                out.write(key);
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
            log.warning("Unable to write to " + location);
        }

    }

    @Override
    public void removePlayerFromMuteList(String name) {
        this.mutedPlayers.remove(name);
        String location = etc.getInstance().getMuteListLocation();
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(location));
            for (String key : this.mutedPlayers) {
                out.write(key);
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
            log.warning("Unable to write to " + location);
        }

    }

    @Override
    public void expireBan(Ban ban) {
        int now = (int) (System.currentTimeMillis() / 1000);
        synchronized (banLock) {
            for (Ban b: bans)
                if (b.equals(ban)) {
                    ban = b;
                    ban.setTimestamp(now);
                }
        }
        String loc = etc.getInstance().getBanListLoc();
        
        try {
            // Now to save...
            BufferedReader reader = new BufferedReader(new FileReader(new File(loc)));
            StringBuilder toWrite = new StringBuilder();
            String line;
            String user = ban.getIp().isEmpty() ? ban.getName() : ban.getIp();
        
            while ((line = reader.readLine()) != null) {
                if (!line.split(":")[0].equalsIgnoreCase(user)) {
                    toWrite.append(line).append(LINE_SEP);
                } else {

                    toWrite.append(line.split(":")[0])
                           .append(":")
                           .append(ban.getReason())
                           .append(":")
                           .append(now)
                           .append(LINE_SEP);
                }
            }
            reader.close();

            FileWriter writer = new FileWriter(loc);

            writer.write(toWrite.toString());
            writer.close();
        } catch (Exception ex) {
            log.log(Level.SEVERE, String.format("Exception while editing ban in %s", loc), ex);
        }
    }
}

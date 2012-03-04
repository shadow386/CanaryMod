import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;


public class OConsoleCommandHandler {

    private static Logger a = Logger.getLogger("Minecraft");
    private MinecraftServer b;

    public OConsoleCommandHandler(MinecraftServer var1) {
        super();
        this.b = var1;
    }

    public synchronized void a(OServerCommand var1) {
        String var2 = var1.a;
        OICommandListener var3 = var1.b;
        String var4 = var3.d();
        OServerConfigurationManager var5 = this.b.h;

        if (!var2.toLowerCase().startsWith("help") && !var2.toLowerCase().startsWith("?")) {
            if (var2.toLowerCase().startsWith("list")) {
                var3.b("Connected players: " + var5.c());
            } else if (var2.toLowerCase().startsWith("stop")) {
                this.a(var4, "Stopping the server..");
                this.b.a();
            } else {
                int var6;
                OWorldServer var7;

                if (var2.toLowerCase().startsWith("save-all")) {
                    this.a(var4, "Forcing save..");
                    if (var5 != null) {
                        var5.g();
                    }

                    for (var6 = 0; var6 < this.b.e.length; ++var6) {
                        var7 = this.b.e[var6];
                        var7.a(true, (OIProgressUpdate) null);
                    }

                    this.a(var4, "Save complete.");
                } else if (var2.toLowerCase().startsWith("save-off")) {
                    this.a(var4, "Disabling level saving..");

                    for (var6 = 0; var6 < this.b.e.length; ++var6) {
                        var7 = this.b.e[var6];
                        var7.I = true;
                    }
                } else if (var2.toLowerCase().startsWith("save-on")) {
                    this.a(var4, "Enabling level saving..");

                    for (var6 = 0; var6 < this.b.e.length; ++var6) {
                        var7 = this.b.e[var6];
                        var7.I = false;
                    }
                } else {
                    String var16;

                    if (var2.toLowerCase().startsWith("op ")) {
                        var16 = var2.substring(var2.indexOf(" ")).trim();
                        var5.e(var16);
                        this.a(var4, "Opping " + var16);
                        var5.a(var16, "\u00a7eYou are now op!");
                    } else if (var2.toLowerCase().startsWith("deop ")) {
                        var16 = var2.substring(var2.indexOf(" ")).trim();
                        var5.f(var16);
                        var5.a(var16, "\u00a7eYou are no longer op!");
                        this.a(var4, "De-opping " + var16);
                    } else if (var2.toLowerCase().startsWith("ban-ip ")) {
                        var16 = var2.substring(var2.indexOf(" ")).trim();
                        var5.c(var16);
                        this.a(var4, "Banning ip " + var16);
                    } else if (var2.toLowerCase().startsWith("pardon-ip ")) {
                        var16 = var2.substring(var2.indexOf(" ")).trim();
                        var5.d(var16);
                        this.a(var4, "Pardoning ip " + var16);
                    } else {
                        OEntityPlayerMP var17;

                        if (var2.toLowerCase().startsWith("ban ")) {
                            var16 = var2.substring(var2.indexOf(" ")).trim();
                            var5.a(var16);
                            this.a(var4, "Banning " + var16);
                            var17 = var5.i(var16);
                            if (var17 != null) {
                                var17.a.a("Banned by admin");
                            }
                        } else if (var2.toLowerCase().startsWith("pardon ")) {
                            var16 = var2.substring(var2.indexOf(" ")).trim();
                            var5.b(var16);
                            this.a(var4, "Pardoning " + var16);
                        } else {
                            int var8;

                            if (var2.toLowerCase().startsWith("kick ")) {
                                var16 = var2.substring(var2.indexOf(" ")).trim();
                                var17 = null;

                                for (var8 = 0; var8 < var5.b.size(); ++var8) {
                                    OEntityPlayerMP var9 = (OEntityPlayerMP) var5.b.get(var8);

                                    if (var9.v.equalsIgnoreCase(var16)) {
                                        var17 = var9;
                                    }
                                }

                                if (var17 != null) {
                                    var17.a.a("Kicked by admin");
                                    this.a(var4, "Kicking " + var17.v);
                                } else {
                                    var3.b("Can\'t find user " + var16 + ". No kick.");
                                }
                            } else {
                                OEntityPlayerMP var18;
                                String[] var21;

                                if (var2.toLowerCase().startsWith("tp ")) {
                                    var21 = var2.split(" ");
                                    if (var21.length == 3) {
                                        var17 = var5.i(var21[1]);
                                        var18 = var5.i(var21[2]);
                                        if (var17 == null) {
                                            var3.b("Can\'t find user " + var21[1] + ". No tp.");
                                        } else if (var18 == null) {
                                            var3.b("Can\'t find user " + var21[2] + ". No tp.");
                                        } else if (var17.w != var18.w) {
                                            var3.b("User " + var21[1] + " and " + var21[2] + " are in different dimensions. No tp.");
                                        } else {
                                            var17.a.a(var18.bm, var18.bn, var18.bo, var18.bs, var18.bt);
                                            this.a(var4, "Teleporting " + var21[1] + " to " + var21[2] + ".");
                                        }
                                    } else {
                                        var3.b("Syntax error, please provice a source and a target.");
                                    }
                                } else {
                                    int var19;
                                    String var20;

                                    if (var2.toLowerCase().startsWith("give ")) {
                                        var21 = var2.split(" ");
                                        if (var21.length != 3 && var21.length != 4 && var21.length != 5) {
                                            return;
                                        }

                                        var20 = var21[1];
                                        var18 = var5.i(var20);
                                        if (var18 != null) {
                                            try {
                                                var19 = Integer.parseInt(var21[2]);
                                                if (OItem.d[var19] != null) {
                                                    this.a(var4, "Giving " + var18.v + " some " + var19);
                                                    int var10 = 1;
                                                    int var11 = 0;

                                                    if (var21.length > 3) {
                                                        var10 = this.a(var21[3], 1);
                                                    }

                                                    if (var21.length > 4) {
                                                        var11 = this.a(var21[4], 1);
                                                    }

                                                    if (var10 < 1) {
                                                        var10 = 1;
                                                    }

                                                    if (var10 > 64) {
                                                        var10 = 64;
                                                    }

                                                    var18.b(new OItemStack(var19, var10, var11));
                                                } else {
                                                    var3.b("There\'s no item with id " + var19);
                                                }
                                            } catch (NumberFormatException var14) {
                                                var3.b("There\'s no item with id " + var21[2]);
                                            }
                                        } else {
                                            var3.b("Can\'t find user " + var20);
                                        }
                                    } else if (var2.toLowerCase().startsWith("xp")) {
                                        var21 = var2.split(" ");
                                        if (var21.length != 3) {
                                            return;
                                        }

                                        var20 = var21[1];
                                        var18 = var5.i(var20);
                                        if (var18 != null) {
                                            try {
                                                var19 = Integer.parseInt(var21[2]);
                                                var19 = var19 > 5000 ? 5000 : var19;
                                                this.a(var4, "Giving " + var19 + " orbs to " + var18.v);
                                                var18.g(var19);
                                            } catch (NumberFormatException var13) {
                                                var3.b("Invalid orb count: " + var21[2]);
                                            }
                                        } else {
                                            var3.b("Can\'t find user " + var20);
                                        }
                                    } else if (var2.toLowerCase().startsWith("gamemode ")) {
                                        var21 = var2.split(" ");
                                        if (var21.length != 3) {
                                            return;
                                        }

                                        var20 = var21[1];
                                        var18 = var5.i(var20);
                                        if (var18 != null) {
                                            try {
                                                var19 = Integer.parseInt(var21[2]);
                                                var19 = OWorldSettings.a(var19);
                                                if (var18.c.a() != var19) {
                                                    this.a(var4, "Setting " + var18.v + " to game mode " + var19);
                                                    var18.c.a(var19);
                                                    var18.a.b((OPacket) (new OPacket70Bed(3, var19)));
                                                } else {
                                                    this.a(var4, var18.v + " already has game mode " + var19);
                                                }
                                            } catch (NumberFormatException var12) {
                                                var3.b("There\'s no game mode with id " + var21[2]);
                                            }
                                        } else {
                                            var3.b("Can\'t find user " + var20);
                                        }
                                    } else if (var2.toLowerCase().startsWith("time ")) {
                                        var21 = var2.split(" ");
                                        if (var21.length != 3) {
                                            return;
                                        }

                                        var20 = var21[1];

                                        try {
                                            var8 = Integer.parseInt(var21[2]);
                                            OWorldServer var22;

                                            if ("add".equalsIgnoreCase(var20)) {
                                                for (var19 = 0; var19 < this.b.e.length; ++var19) {
                                                    var22 = this.b.e[var19];
                                                    var22.b(var22.o() + (long) var8);
                                                }

                                                this.a(var4, "Added " + var8 + " to time");
                                            } else if ("set".equalsIgnoreCase(var20)) {
                                                for (var19 = 0; var19 < this.b.e.length; ++var19) {
                                                    var22 = this.b.e[var19];
                                                    var22.b((long) var8);
                                                }

                                                this.a(var4, "Set time to " + var8);
                                            } else {
                                                var3.b("Unknown method, use either \"add\" or \"set\"");
                                            }
                                        } catch (NumberFormatException var15) {
                                            var3.b("Unable to convert time value, " + var21[2]);
                                        }
                                    } else if (var2.toLowerCase().startsWith("say ")) {
                                        var2 = var2.substring(var2.indexOf(" ")).trim();
                                        a.info("[" + var4 + "] " + var2);
                                        var5.a((OPacket) (new OPacket3Chat("\u00a7d[Server] " + var2)));
                                    } else if (var2.toLowerCase().startsWith("tell ")) {
                                        var21 = var2.split(" ");
                                        if (var21.length >= 3) {
                                            var2 = var2.substring(var2.indexOf(" ")).trim();
                                            var2 = var2.substring(var2.indexOf(" ")).trim();
                                            a.info("[" + var4 + "->" + var21[1] + "] " + var2);
                                            var2 = "\u00a77" + var4 + " whispers " + var2;
                                            a.info(var2);
                                            if (!var5.a(var21[1], (OPacket) (new OPacket3Chat(var2)))) {
                                                var3.b("There\'s no player by that name online.");
                                            }
                                        }
                                    } else if (var2.toLowerCase().startsWith("whitelist ")) {
                                        this.a(var4, var2, var3);
                                    } else if (var2.toLowerCase().startsWith("toggledownfall")) {
                                        this.b.e[0].j();
                                        var3.b("Toggling rain and snow, hold on...");
                                    } else if (var2.toLowerCase().startsWith("banlist")) {
                                        var21 = var2.split(" ");
                                        if (var21.length == 2) {
                                            if (var21[1].equals("ips")) {
                                                var3.b("IP Ban list:" + this.a(this.b.q(), ", "));
                                            }
                                        } else {
                                            var3.b("Ban list:" + this.a(this.b.r(), ", "));
                                        }
                                    } else {
                                        a.info("Unknown console command. Type \"help\" for help.");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            this.a(var3);
        }

    }

    private void a(String var1, String var2, OICommandListener var3) {
        String[] var4 = var2.split(" ");

        if (var4.length >= 2) {
            String var5 = var4[1].toLowerCase();

            if ("OStringTranslate".equals(var5)) {
                this.a(var1, "Turned on white-listing");
                this.b.d.b("white-list", true);
            } else if ("off".equals(var5)) {
                this.a(var1, "Turned off white-listing");
                this.b.d.b("white-list", false);
            } else if ("list".equals(var5)) {
                Set var6 = this.b.h.h();
                String var7 = "";

                String var9;

                for (Iterator var8 = var6.iterator(); var8.hasNext(); var7 = var7 + var9 + " ") {
                    var9 = (String) var8.next();
                }

                var3.b("White-listed players: " + var7);
            } else {
                String var10;

                if ("add".equals(var5) && var4.length == 3) {
                    var10 = var4[2].toLowerCase();
                    this.b.h.k(var10);
                    this.a(var1, "Added " + var10 + " to white-list");
                } else if ("remove".equals(var5) && var4.length == 3) {
                    var10 = var4[2].toLowerCase();
                    this.b.h.l(var10);
                    this.a(var1, "Removed " + var10 + " from white-list");
                } else if ("reload".equals(var5)) {
                    this.b.h.i();
                    this.a(var1, "Reloaded white-list from file");
                }
            }

        }
    }

    private void a(OICommandListener var1) {
        var1.b("To run the server without a gui, start it like this:");
        var1.b("   java -Xmx1024M -Xms1024M -jar minecraft_server.jar nogui");
        var1.b("Console commands:");
        var1.b("   help  or  ?               shows this message");
        var1.b("   kick <player>             removes a player from the server");
        var1.b("   ban <player>              bans a player from the server");
        var1.b("   pardon <player>           pardons a banned player so that they can connect again");
        var1.b("   ban-ip <ip>               bans an IP address from the server");
        var1.b("   pardon-ip <ip>            pardons a banned IP address so that they can connect again");
        var1.b("   op <player>               turns a player into an op");
        var1.b("   deop <player>             removes op status from a player");
        var1.b("   tp <player1> <player2>    moves one player to the same location as another player");
        var1.b("   give <player> <id> [num]  gives a player a resource");
        var1.b("   tell <player> <message>   sends a private message to a player");
        var1.b("   stop                      gracefully stops the server");
        var1.b("   save-all                  forces a server-wide level save");
        var1.b("   save-off                  disables terrain saving (useful for backup scripts)");
        var1.b("   save-on                   re-enables terrain saving");
        var1.b("   list                      lists all currently connected players");
        var1.b("   say <message>             broadcasts a message to all players");
        var1.b("   time <add|set> <amount>   adds to or sets the world time (0-24000)");
        var1.b("   gamemode <player> <mode>  sets player\'s game mode (0 or 1)");
    }

    private void a(String var1, String var2) {
        String var3 = var1 + ": " + var2;

        this.b.h.j("\u00a77(" + var3 + ")");
        a.info(var3);
    }

    private int a(String var1, int var2) {
        try {
            return Integer.parseInt(var1);
        } catch (NumberFormatException var4) {
            return var2;
        }
    }

    private String a(String[] var1, String var2) {
        int var3 = var1.length;

        if (0 == var3) {
            return "";
        } else {
            StringBuilder var4 = new StringBuilder();

            var4.append(var1[0]);

            for (int var5 = 1; var5 < var3; ++var5) {
                var4.append(var2).append(var1[var5]);
            }

            return var4.toString();
        }
    }

}

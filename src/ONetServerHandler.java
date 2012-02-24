import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;


public class ONetServerHandler extends ONetHandler implements OICommandListener {

    public static Logger a = Logger.getLogger("Minecraft");
    public ONetworkManager b;
    public boolean c = false;
    private MinecraftServer d;
    private OEntityPlayerMP e;
    private int f;
    private int g;
    private boolean h;
    private int i;
    private long j;
    private static Random k = new Random();
    private long l;
    // private int m = 0; CanaryMod - disable native spam protection
    private double n;
    private double o;
    private double p;
    private boolean q = true;
    private OIntHashMap r = new OIntHashMap();

    public ONetServerHandler(MinecraftServer var1, ONetworkManager var2, OEntityPlayerMP var3) {
        super();
        this.d = var1;
        this.b = var2;
        var2.a((ONetHandler) this);
        this.e = var3;
        var3.a = this;
    }

    public void a() {
        this.h = false;
        ++this.f;
        this.b.b();
        if ((long) this.f - this.l > 20L) {
            this.l = (long) this.f;
            this.j = System.nanoTime() / 1000000L;
            this.i = k.nextInt();
            this.b((OPacket) (new OPacket0KeepAlive(this.i)));
        }

        /* CanaryMod - disable native spam protection
        if (this.m > 0) {
            --this.m;
        }
        */

    }

    public void a(String var1) {
        if (!this.c) {
            this.e.F();
            this.b((OPacket) (new OPacket255KickDisconnect(var1)));
            this.b.d();

            // CanaryMod - onPlayerDisconnect Hook
            HookParametersDisconnect hookResult = new HookParametersDisconnect(String.format(Colors.Yellow + "%s left the game.", this.e.v));

            hookResult = (HookParametersDisconnect) etc.getLoader().callHook(PluginLoader.Hook.PLAYER_DISCONNECT, this.e.getPlayer(), hookResult);
            if (!hookResult.isHidden()) { 
                    this.d.h.a((OPacket) (new OPacket3Chat(hookResult.getLeaveMessage())));
            }

            this.d.h.e(this.e);
            this.c = true;
        }
    }

    public void a(OPacket10Flying var1) {
        OWorldServer var2 = this.d.a(this.e.w);
        
        this.h = true;
        if (!this.e.j) {
            double var3;

            if (!this.q) {
                var3 = var1.b - this.o;
                if (var1.a == this.n && var3 * var3 < 0.01D && var1.c == this.p) {
                    this.q = true;
                }
            }
         
            // CanaryMod: Notice player movement
            Player player = getPlayer();

            if (etc.floor(n) != etc.floor(player.getX()) || etc.floor(o) != etc.floor(player.getY()) || etc.floor(p) != etc.floor(player.getZ())) {
                Location from = new Location();

                from.x = etc.floor(n);
                from.y = etc.floor(o);
                from.z = etc.floor(p);
                from.rotX = player.getRotation();
                from.rotY = player.getPitch();

                Location to = new Location();

                to.x = etc.floor(e.bm);
                to.y = etc.floor(e.bn);
                to.z = etc.floor(e.bo);
                to.rotX = player.getRotation();
                to.rotY = player.getPitch();

                OEntity.manager.callHook(PluginLoader.Hook.PLAYER_MOVE, player, from, to);
            }

            if (this.q) {
                double var7;
                double var9;
                double var11;
                double var15;

                if (this.e.bh != null) {
                    float var5 = this.e.bs;
                    float var6 = this.e.bt;

                    this.e.bh.i();
                    var7 = this.e.bm;
                    var9 = this.e.bn;
                    var11 = this.e.bo;
                    double var13 = 0.0D;

                    var15 = 0.0D;
                    if (var1.i) {
                        var5 = var1.e;
                        var6 = var1.f;
                    }

                    if (var1.h && var1.b == -999.0D && var1.d == -999.0D) {
                        var13 = var1.a;
                        var15 = var1.c;
                    }

                    this.e.bx = var1.g;
                    this.e.a(true);
                    this.e.a(var13, 0.0D, var15);
                    this.e.b(var7, var9, var11, var5, var6);
                    this.e.bp = var13;
                    this.e.br = var15;
                    if (this.e.bh != null) {
                        var2.b(this.e.bh, true);
                    }

                    if (this.e.bh != null) {
                        this.e.bh.i();
                    }

                    this.d.h.d(this.e);
                    this.n = this.e.bm;
                    this.o = this.e.bn;
                    this.p = this.e.bo;
                    var2.g(this.e);
                    return;
                }

                if (this.e.V()) {
                    this.e.a(true);
                    this.e.b(this.n, this.o, this.p, this.e.bs, this.e.bt);
                    var2.g(this.e);
                    return;
                }

                var3 = this.e.bn;
                this.n = this.e.bm;
                this.o = this.e.bn;
                this.p = this.e.bo;
                var7 = this.e.bm;
                var9 = this.e.bn;
                var11 = this.e.bo;
                float var17 = this.e.bs;
                float var18 = this.e.bt;

                if (var1.h && var1.b == -999.0D && var1.d == -999.0D) {
                    var1.h = false;
                }

                if (var1.h) {
                    var7 = var1.a;
                    var9 = var1.b;
                    var11 = var1.c;
                    var15 = var1.d - var1.b;
                    if (!this.e.V() && (var15 > 1.65D || var15 < 0.1D)) {
                        this.a("Illegal stance");
                        a.warning(this.e.v + " had an illegal stance: " + var15);
                        return;
                    }

                    if (Math.abs(var1.a) > 3.2E7D || Math.abs(var1.c) > 3.2E7D) {
                        this.a("Illegal position");
                        return;
                    }
                }

                if (var1.i) {
                    var17 = var1.e;
                    var18 = var1.f;
                }

                this.e.a(true);
                this.e.bO = 0.0F;
                this.e.b(this.n, this.o, this.p, var17, var18);
                if (!this.q) {
                    return;
                }

                var15 = var7 - this.e.bm;
                double var19 = var9 - this.e.bn;
                double var21 = var11 - this.e.bo;
                double var23 = var15 * var15 + var19 * var19 + var21 * var21;

                if (var23 > 100.0D) {
                    a.warning(this.e.v + " moved too quickly!");
                    this.a("You moved too quickly :( (Hacking?)");
                    return;
                }

                float var25 = 0.0625F;
                boolean var26 = var2.a(this.e, this.e.bw.b().e((double) var25, (double) var25, (double) var25)).size() == 0;

                if (this.e.bx && !var1.g && var19 > 0.0D) {
                    this.e.c(0.2F);
                }

                this.e.a(var15, var19, var21);
                this.e.bx = var1.g;
                this.e.b(var15, var19, var21);
                double var27 = var19;

                var15 = var7 - this.e.bm;
                var19 = var9 - this.e.bn;
                if (var19 > -0.5D || var19 < 0.5D) {
                    var19 = 0.0D;
                }

                var21 = var11 - this.e.bo;
                var23 = var15 * var15 + var19 * var19 + var21 * var21;
                boolean var29 = false;

                if (var23 > 0.0625D && !this.e.V() && !this.e.c.b()) {
                    var29 = true;
                    a.warning(this.e.v + " moved wrongly!");
                    System.out.println("Got position " + var7 + ", " + var9 + ", " + var11);
                    System.out.println("Expected " + this.e.bm + ", " + this.e.bn + ", " + this.e.bo);
                }

                this.e.b(var7, var9, var11, var17, var18);
                boolean var30 = var2.a(this.e, this.e.bw.b().e((double) var25, (double) var25, (double) var25)).size() == 0;

                if (var26 && (var29 || !var30) && !this.e.V()) {
                    this.a(this.n, this.o, this.p, var17, var18);
                    return;
                }

                OAxisAlignedBB var31 = this.e.bw.b().b((double) var25, (double) var25, (double) var25).a(0.0D, -0.55D, 0.0D);

                // CanaryMod: Allow ops and people with ignoreRestrictions to fly
                if (!this.d.r && !this.e.c.b() && !var2.b(var31) && !getPlayer().canIgnoreRestrictions()) {
                    if (var27 >= -0.03125D) {
                        ++this.g;
                        if (this.g > 80) {
                            a.warning(this.e.v + " was kicked for floating too long!");
                            this.a("Flying is not enabled on this server");
                            return;
                        }
                    }
                } else {
                    this.g = 0;
                }

                this.e.bx = var1.g;
                this.d.h.d(this.e);
                this.e.b(this.e.bn - var3, var1.g);
            }

        }
    }

    public void a(double var1, double var3, double var5, float var7, float var8) {
        // CanaryMod: Teleportation hook
        Location from = new Location();

        from.x = var1;
        from.y = var3;
        from.z = var5;
        from.rotX = var7;
        from.rotY = var8;
        Player player = getPlayer();

        if ((Boolean) OEntity.manager.callHook(PluginLoader.Hook.TELEPORT, player, player.getLocation(), from)) {
            return;
        }

        this.q = false;
        this.n = var1;
        this.o = var3;
        this.p = var5;
        this.e.b(var1, var3, var5, var7, var8);
        this.e.a.b((OPacket) (new OPacket13PlayerLookMove(var1, var3 + 1.6200000047683716D, var3, var5, var7, var8, false)));
        player.refreshCreativeMode();
    }
   
    // CanaryMod: Store x/y/z
    int x, y, z, type;

    public void a(OPacket14BlockDig var1) {
        OWorldServer var2 = this.d.a(this.e.w);

        if (var1.e == 4) {
            this.e.O();
        } else if (var1.e == 5) {
            this.e.J();
        } else {
            // CanaryMod: We allow admins and ops to dig!
            boolean var3 = var2.K = var2.y.h != 0 || this.d.h.h(this.e.v) || getPlayer().isAdmin();
            boolean var4 = false;

            if (var1.e == 0) {
                var4 = true;
            }

            if (var1.e == 2) {
                var4 = true;
            }

            int var5 = var1.a;
            int var6 = var1.b;
            int var7 = var1.c;

            if (var4) {
                double var8 = this.e.bm - ((double) var5 + 0.5D);
                double var10 = this.e.bn - ((double) var6 + 0.5D) + 1.5D;
                double var12 = this.e.bo - ((double) var7 + 0.5D);
                double var14 = var8 * var8 + var10 * var10 + var12 * var12;

                if (var14 > 36.0D) {
                    return;
                }
            }

            OChunkCoordinates var16 = var2.o();
            int var17 = OMathHelper.a(var5 - var16.a);
            int var18 = OMathHelper.a(var7 - var16.c);

            if (var17 > var18) {
                var18 = var17;
            }
         
            // CanaryMod: the player
            Player player = getPlayer();

            if (var1.e == 0) {
                // CanaryMod: Start digging
                // No buildrights
                if (!getPlayer().canBuild()) {
                    return;
                }
                // CanaryMod: Custom spawn prot size
                if (var18 <= etc.getInstance().getSpawnProtectionSize() && !var3) {
                    this.e.a.b((OPacket) (new OPacket53BlockChange(var5, var6, var7, var2)));
                } else {
                    // CanaryMod: Dig hooks
                    Block block = var2.world.getBlockAt(var5, var6, var7);

                    block.setStatus(0); // Started digging
                    x = block.getX();
                    y = block.getY();
                    z = block.getZ();
                    type = block.getType();
                    if (!(Boolean) OEntity.manager.callHook(PluginLoader.Hook.BLOCK_DESTROYED, player, block)) {
                        this.e.c.a(var5, var6, var7, var1.d);
                    } else {
                        this.e.a.b((OPacket) (new OPacket53BlockChange(var5, var6, var7, var2)));
                    }
                }
            } else if (var1.e == 2) {
                // CanaryMod: Break block
                Block block = var2.world.getBlockAt(var5, var6, var7);

                block.setStatus(2); // Block broken
                OEntity.manager.callHook(PluginLoader.Hook.BLOCK_DESTROYED, player, block);

                this.e.c.a(var5, var6, var7);
                if (var2.a(var5, var6, var7) != 0) {
                    this.e.a.b((OPacket) (new OPacket53BlockChange(var5, var6, var7, var2)));
                }
            } else if (var1.e == 3) {
                // CanaryMod: Send block update
                Block block = new Block(var2.world, type, x, y, z);

                block.setStatus(3); // Send update for block
                OEntity.manager.callHook(PluginLoader.Hook.BLOCK_DESTROYED, player, block);

                double var19 = this.e.bm - ((double) var5 + 0.5D);
                double var21 = this.e.bn - ((double) var6 + 0.5D);
                double var23 = this.e.bo - ((double) var7 + 0.5D);
                double var25 = var19 * var19 + var21 * var21 + var23 * var23;

                if (var25 < 256.0D) {
                    this.e.a.b((OPacket) (new OPacket53BlockChange(var5, var6, var7, var2)));
                }
            }

            var2.K = false;
        }
    }
   
    // CanaryMod: Store the blocks between blockPlaced packets
    Block lastRightClicked;

    public void a(OPacket15Place var1) {
        OWorldServer var2 = this.d.a(this.e.w);
        OItemStack var3 = this.e.k.d();
      
        // CanaryMod: Store block data to call hooks
        // CanaryMod START
        Block blockClicked;
        Block blockPlaced = null;

        // We allow admins and ops to build!
        boolean var4 = var2.K = var2.y.h != 0 || this.d.h.h(this.e.v) || getPlayer().isAdmin();
      
        if (var1.d == 255) {
            // ITEM_USE -- if we have a lastRightClicked then it could be a
            // usable location
            blockClicked = lastRightClicked;
            lastRightClicked = null;
        } else {
            // RIGHTCLICK or BLOCK_PLACE .. or nothing
            blockClicked = new Block(var2.world, var2.world.getBlockIdAt(var1.a, var1.b, var1.c), var1.a, var1.b, var1.c);
            blockClicked.setFaceClicked(Block.Face.fromId(var1.d));
            
            lastRightClicked = blockClicked;
        }

        // If we clicked on something then we also have a location to place the
        // block
        if (blockClicked != null && var3 != null) {
            blockPlaced = new Block(var2.world, var3.c, blockClicked.getX(), blockClicked.getY(), blockClicked.getZ());
            switch (var1.d) {
                case 0:
                    blockPlaced.setY(blockPlaced.getY() - 1);
                    break;

                case 1:
                    blockPlaced.setY(blockPlaced.getY() + 1);
                    break;

                case 2:
                    blockPlaced.setZ(blockPlaced.getZ() - 1);
                    break;

                case 3:
                    blockPlaced.setZ(blockPlaced.getZ() + 1);
                    break;

                case 4:
                    blockPlaced.setX(blockPlaced.getX() - 1);
                    break;

                case 5:
                    blockPlaced.setX(blockPlaced.getX() + 1);
                    break;
            }
        }

        // CanaryMod: END
      
        if (var1.d == 255) {
            // CanaryMod: call our version with extra blockClicked/blockPlaced
            if (blockPlaced != null) {
                // Set the type of block to what it currently is
                blockPlaced.setType(var2.world.getBlockIdAt(blockPlaced.getX(), blockPlaced.getY(), blockPlaced.getZ()));
            }

            if (var3 == null) {
                return;
            }

            this.e.c.itemUsed(this.e, var2, var3, blockPlaced, blockClicked);
        } else {
            int var5 = var1.a;
            int var6 = var1.b;
            int var7 = var1.c;
            int var8 = var1.d;
            OChunkCoordinates var9 = var2.o();
            // CanaryMod : Fix stupid buggy spawn protection. (2 times)
            int var10 = (int) OMathHelper.e((var8 == 4 ? var5 - 1 : (var8 == 5 ? (var5 + 1) : var5)) - var9.a);
            int var11 = (int) OMathHelper.e((var8 == 2 ? var7 - 1 : (var8 == 3 ? (var7 + 1) : var7)) - var9.c);

            if (var10 > var11) {
                var11 = var10;
            }
            
            // CanaryMod: call BLOCK_RIGHTCLICKED
            Item item = (var3 != null) ? new Item(var3) : new Item(Item.Type.Air);
            Player player = getPlayer();
            boolean cancelled = (Boolean) OEntity.manager.callHook(PluginLoader.Hook.BLOCK_RIGHTCLICKED, player, blockClicked, item);
         
            // CanaryMod: call original BLOCK_CREATED
            OEntity.manager.callHook(PluginLoader.Hook.BLOCK_CREATED, player, blockPlaced, blockClicked, item.getItemId());
            // CanaryMod: If we were building inside spawn, bail! (unless ops/admin)

            if (this.q && this.e.e((double) var5 + 0.5D, (double) var6 + 0.5D, (double) var7 + 0.5D) < 64.0D && (var11 > etc.getInstance().getSpawnProtectionSize() || var4) && player.canBuild() && !cancelled) {
                this.e.c.a(this.e, var2, var3, var5, var6, var7, var8);
            } else {
                // CanaryMod: No point sending the client to update the blocks, you weren't allowed to place!
                this.e.a.b((OPacket) (new OPacket53BlockChange(var5, var6, var7, var2)));
                var2.D = false;
                return;
            }

            this.e.a.b((OPacket) (new OPacket53BlockChange(var5, var6, var7, var2)));
            if (var8 == 0) {
                --var6;
            }

            if (var8 == 1) {
                ++var6;
            }

            if (var8 == 2) {
                --var7;
            }

            if (var8 == 3) {
                ++var7;
            }

            if (var8 == 4) {
                --var5;
            }

            if (var8 == 5) {
                ++var5;
            }

            this.e.a.b((OPacket) (new OPacket53BlockChange(var5, var6, var7, var2)));
        }

        var3 = this.e.k.d();
        if (var3 != null && var3.a == 0) {
            this.e.k.a[this.e.k.c] = null;
            var3 = null;
        }

        if (var3 == null || var3.l() == 0) {
            this.e.h = true;
            this.e.k.a[this.e.k.c] = OItemStack.b(this.e.k.a[this.e.k.c]);
            OSlot var12 = this.e.m.a((OIInventory) this.e.k, this.e.k.c);

            this.e.m.a();
            this.e.h = false;
            if (!OItemStack.b(this.e.k.d(), var1.e)) {
                this.b((OPacket) (new OPacket103SetSlot(this.e.m.f, var12.c, this.e.k.d())));
            }
        }

        var2.K = false;
    }
   
    public void a(String var1, Object[] var2) {
        // CanaryMod: disconnect!
        OEntity.manager.callHook(PluginLoader.Hook.DISCONNECT, getPlayer());
        a.info(this.e.v + " lost connection: " + var1);

        // CanaryMod - onPlayerDisconnect Hook
        HookParametersDisconnect hookResult = new HookParametersDisconnect(String.format(Colors.Yellow + "%s left the server.", this.e.v));

        hookResult = (HookParametersDisconnect) etc.getLoader().callHook(PluginLoader.Hook.PLAYER_DISCONNECT, this.e.getPlayer(), hookResult);
        if (!hookResult.isHidden()) { 
        	this.d.h.a((OPacket) (new OPacket3Chat(hookResult.getLeaveMessage())));
        }

        this.d.h.e(this.e);
        this.c = true;
    }

    public void a(OPacket var1) {
        a.warning(this.getClass() + " wasn\'t prepared to deal with a " + var1.getClass());
        this.a("Protocol error, unexpected packet");
    }

    public void b(OPacket var1) {
        this.b.a(var1);
    }

    public void a(OPacket16BlockItemSwitch var1) {
        if (var1.a >= 0 && var1.a < OInventoryPlayer.h()) {
            this.e.k.c = var1.a;
        } else {
            a.warning(this.e.v + " tried to set an invalid carried item");
        }
    }

    public void a(OPacket3Chat var1) {
        String var2 = var1.a;
        
        // CanaryMod - disable native spam protection
            
        // CanaryMod: redirect chathandling to player class
        getPlayer().chat(var2);
    }

    private void c(String var1) {// Handled by PlayerCommands class
    }

    public void a(OPacket18Animation var1) {
        if (var1.b == 1) {
            // CanaryMod: Swing the arm!
            OEntity.manager.callHook(PluginLoader.Hook.ARM_SWING, getPlayer());
            this.e.s_();
        }

    }

    public void a(OPacket19EntityAction var1) {
        if (var1.b == 1) {
            this.e.f(true);
        } else if (var1.b == 2) {
            this.e.f(false);
        } else if (var1.b == 4) {
            this.e.g(true);
        } else if (var1.b == 5) {
            this.e.g(false);
        } else if (var1.b == 3) {
            this.e.a(false, true, true);
            this.q = false;
        }

    }

    public void a(OPacket255KickDisconnect var1) {
        this.b.a("disconnect.quitting", new Object[0]);
    }

    public int b() {
        return this.b.e();
    }

    public void b(String var1) {
        this.b((OPacket) (new OPacket3Chat("\u00a77" + var1)));
    }

    public String d() {
        return this.e.v;
    }

    public void a(OPacket7UseEntity var1) {
        OWorldServer var2 = this.d.a(this.e.w);
        OEntity var3 = var2.a(var1.b);

        if (var3 != null && this.e.g(var3) && this.e.i(var3) < 36.0D) {
            if (var1.c == 0) {
                this.e.e(var3);
            } else if (var1.c == 1) {
                this.e.f(var3);
            }
        }

    }

    public void a(OPacket9Respawn var1) {
        // CanaryMod: onPlayerRespawn
        OChunkCoordinates defaultSpawnCoords = e.X();
        if (defaultSpawnCoords == null) {
            defaultSpawnCoords = etc.getServer().getWorld(0).getWorld().o();
        }
        Location respawnLocation = new Location(etc.getServer().getWorld(0), defaultSpawnCoords.a, defaultSpawnCoords.b, defaultSpawnCoords.c, 0, 0);
        if (this.e.j) {
            etc.getLoader().callHook(PluginLoader.Hook.PLAYER_RESPAWN, e.getPlayer(), respawnLocation);
            this.e = this.d.h.a(this.e, respawnLocation.dimension, true, respawnLocation);
        } else {
            if (this.e.ap() > 0) {
                return;
            }
            etc.getLoader().callHook(PluginLoader.Hook.PLAYER_RESPAWN, e.getPlayer(), respawnLocation);
            this.e = this.d.h.a(this.e, respawnLocation.dimension, false, respawnLocation);
        }
    }

    public void a(OPacket101CloseWindow var1) {
        this.e.E();
    }

    public void a(OPacket102WindowClick var1) {
        if (this.e.m.f == var1.a && this.e.m.c(this.e)) {
            OItemStack var2 = this.e.m.a(var1.b, var1.c, var1.f, this.e);

            if (OItemStack.b(var1.e, var2)) {
                this.e.a.b((OPacket) (new OPacket106Transaction(var1.a, var1.d, true)));
                this.e.h = true;
                this.e.m.a();
                this.e.D();
                this.e.h = false;
            } else {
                this.r.a(this.e.m.f, Short.valueOf(var1.d));
                this.e.a.b((OPacket) (new OPacket106Transaction(var1.a, var1.d, false)));
                this.e.m.a(this.e, false);
                ArrayList var3 = new ArrayList();

                for (int var4 = 0; var4 < this.e.m.e.size(); ++var4) {
                    var3.add(((OSlot) this.e.m.e.get(var4)).b());
                }

                this.e.a(this.e.m, var3);
            }
            // if we shiftclicked on slot 0 -> resend all inventory to player
            if (var1.b == 0 && var1.f) {
                this.e.a(this.e.m);
            }
        }

    }

    public void a(OPacket108EnchantItem var1) {
        if (this.e.m.f == var1.a && this.e.m.c(this.e)) {
            this.e.m.a((OEntityPlayer) this.e, var1.b);
            this.e.m.a();
        }

    }

    public void a(OPacket107CreativeSetSlot var1) {
        if (this.e.c.b()) {
            boolean var2 = var1.a < 0;
            OItemStack var3 = var1.b;
            boolean var4 = var1.a >= 36 && var1.a < 36 + OInventoryPlayer.h();
            boolean var5 = var3 == null || var3.c < OItem.d.length && var3.c >= 0 && OItem.d[var3.c] != null;
            boolean var6 = var3 == null || var3.h() >= 0 && var3.h() >= 0 && var3.a <= 64 && var3.a > 0;

            if (var4 && var5 && var6) {
                if (var3 == null) {
                    this.e.l.a(var1.a, (OItemStack) null);
                } else {
                    this.e.l.a(var1.a, var3);
                }

                this.e.l.a(this.e, true);
            } else if (var2 && var5 && var6) {
                this.e.b(var3);
            }
        }

    }

    public void a(OPacket106Transaction var1) {
        Short var2 = (Short) this.r.a(this.e.m.f);

        if (var2 != null && var1.b == var2.shortValue() && this.e.m.f == var1.a && !this.e.m.c(this.e)) {
            this.e.m.a(this.e, true);
        }

    }

    public void a(OPacket130UpdateSign var1) {
        OWorldServer var2 = this.d.a(this.e.w);

        if (var2.g(var1.a, var1.b, var1.c)) {
            OTileEntity var3 = var2.b(var1.a, var1.b, var1.c);

            if (var3 instanceof OTileEntitySign) {
                OTileEntitySign var4 = (OTileEntitySign) var3;

                if (!var4.c()) {
                    this.d.c("Player " + this.e.v + " just tried to change non-editable sign");
                    return;
                }
            }

            int var6;
            int var9;

            for (var9 = 0; var9 < 4; ++var9) {
                boolean var5 = true;

                // CanaryMod: Remove the char limit, for plugins.
                // if(var1.d[var9].length() > 15) {
                // var5 = false;
                // } else {
                for (var6 = 0; var6 < var1.d[var9].length(); ++var6) {
                    if (OChatAllowedCharacters.a.indexOf(var1.d[var9].charAt(var6)) < 0) {
                        var5 = false;
                    }
                }
                // }

                if (!var5) {
                    var1.d[var9] = "!?";
                }
            }

            if (var3 instanceof OTileEntitySign) {
                var9 = var1.a;
                int var10 = var1.b;

                var6 = var1.c;
                OTileEntitySign var7 = (OTileEntitySign) var3;
            
                // CanaryMod: Copy the old line text
                String[] old = Arrays.copyOf(var7.a, var7.a.length);

                for (int var8 = 0; var8 < 4; ++var8) {
                    var7.a[var8] = var1.d[var8];
                }
            
                // CanaryMod: Check if we can change it
                Sign sign = new Sign(var7);

                if ((Boolean) OEntity.manager.callHook(PluginLoader.Hook.SIGN_CHANGE, getPlayer(), sign)) {
                    var7.a = Arrays.copyOf(old, old.length);
                }

                var7.z_();
                var2.h(var9, var10, var6);
            }
        }

    }

    public void a(OPacket0KeepAlive var1) {
        if (var1.a == this.i) {
            int var2 = (int) (System.nanoTime() / 1000000L - this.j);

            this.e.i = (this.e.i * 3 + var2) / 4;
        }
    }

    public boolean c() {
        return true;
    }
   
    /**
     * Returns the item in player's hand
     * 
     * @return item
     */
    public int getItemInHand() {
        if (e.k.d() != null) {
            return e.k.d().c;
        }
        return -1;
    }

    /**
     * Returns the player
     * 
     * @return player
     */
    public Player getPlayer() {
        return e.getPlayer();
    }

    /**
     * Sends a message to the player
     * 
     * @param msg
     */
    public void msg(String msg) {
        if (msg.length() >= 119) {
            String cutMsg = msg.substring(0, 118);
            int finalCut = cutMsg.lastIndexOf(" ");
            String subCut = cutMsg.substring(0, finalCut);
            String newMsg = msg.substring(finalCut + 1);

            b(new OPacket3Chat(subCut));
            msg(newMsg);
        } else {
            b(new OPacket3Chat(msg));
        }
    }

}
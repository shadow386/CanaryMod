import java.util.ArrayList;


public class OItemInWorldManager {

    public OWorld a;
    public OEntityPlayer b;
    private int c = -1;
    private float d = 0.0F;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private boolean j;
    private int k;
    private int l;
    private int m;
    private int n;

    public OItemInWorldManager(OWorld var1) {
        super();
        this.a = var1;
    }

    public void a(int var1) {
        this.c = var1;
        if (var1 == 0) {
            this.b.L.c = false;
            this.b.L.b = false;
            this.b.L.d = false;
            this.b.L.a = false;
        } else {
            this.b.L.c = true;
            this.b.L.d = true;
            this.b.L.a = true;
        }

    }

    public int a() {
        return this.c;
    }

    public boolean b() {
        return this.c == 1;
    }

    public void b(int var1) {
        if (this.c == -1) {
            this.c = var1;
        }

        this.a(this.c);
    }

    public void c() {
        this.i = (int) (System.currentTimeMillis() / 50); // CanaryMod - block lag fix
        if (this.j) {
            int var1 = this.i - this.n;
            int var2 = this.a.a(this.k, this.l, this.m);

            if (var2 != 0) {
                OBlock var3 = OBlock.m[var2];
                float var4 = var3.a(this.b) * (float) (var1 + 1);

                if (var4 >= 1.0F) {
                    this.j = false;
                    this.c(this.k, this.l, this.m);
                }
            } else {
                this.j = false;
            }
        }

    }

    public void a(int var1, int var2, int var3, int var4) {
        if (this.b()) {
            if (!this.a.a((OEntityPlayer) null, var1, var2, var3, var4)) {
                this.c(var1, var2, var3);
            }

        } else {
            this.a.a((OEntityPlayer) null, var1, var2, var3, var4);
            this.e = this.i;
            int var5 = this.a.a(var1, var2, var3);

            if (var5 > 0) {
                OBlock.m[var5].b(this.a, var1, var2, var3, this.b);
            }

            if (var5 > 0 && OBlock.m[var5].a(this.b) >= 1.0F) {
                this.c(var1, var2, var3);
            } else {
                this.f = var1;
                this.g = var2;
                this.h = var3;
            }

        }
    }

    public void a(int var1, int var2, int var3) {
        if (var1 == this.f && var2 == this.g && var3 == this.h) {
            int var4 = this.i - this.e;
            int var5 = this.a.a(var1, var2, var3);

            if (var5 != 0) {
                OBlock var6 = OBlock.m[var5];
                float var7 = var6.a(this.b) * (float) (var4 + 1);

                if (var7 >= 0.7F) {
                    this.c(var1, var2, var3);
                } else if (!this.j) {
                    this.j = true;
                    this.k = var1;
                    this.l = var2;
                    this.m = var3;
                    this.n = this.e;
                }
            }
        }

        this.d = 0.0F;
    }

    public boolean b(int var1, int var2, int var3) {
        OBlock var4 = OBlock.m[this.a.a(var1, var2, var3)];
        int var5 = this.a.c(var1, var2, var3);
        boolean var6 = this.a.e(var1, var2, var3, 0);

        if (var4 != null && var6) {
            var4.c(this.a, var1, var2, var3, var5);
        }

        return var6;
    }

    public boolean c(int var1, int var2, int var3) {
    	// CanaryMod start - portal destroy
        Block block = ((OEntityPlayerMP) b).getPlayer().getWorld().getBlockAt(var1, var2, var3); //

        if (block.getType() == Block.Type.Obsidian.getType()) {
            boolean removeAll = true;
            ArrayList<Player> updatedPlayers = new ArrayList<Player>();

            for (Player player : etc.getServer().getPlayerList()) {
                if (player.getWorld().equals(block.getWorld())) {
                    updatedPlayers.add(player);
                }
            }
            int[][] blockOffsets = new int[][] { new int[] { 0, 1, 0}, new int[] { 1, 0, 0}, new int[] { -1, 0, 0}, new int[] { 0, 0, 1}, new int[] { 0, 0, -1}, new int[] { 0, -1, 0}};

            for (int i = 0; i < blockOffsets.length; i += 1) {
                Block[][] blocks = getPortalBlocks(block.getWorld(), var1 + blockOffsets[i][0], var2 + blockOffsets[i][1], var3 + blockOffsets[i][2]);

                if (blocks != null) {
                    // CanaryMod hook onPortalDestroy
                    if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.PORTAL_DESTROY, (Object) blocks)) {
                        removeAll = false;
                    } else {
                        for (int j = 0; j < 3; j += 1) {
                            for (int k = 0; k < 2; k += 1) {
                                block.getWorld().getWorld().c(blocks[j][k].getX() >> 4, blocks[j][k].getZ() >> 4).a(blocks[j][k].getX() & 15, blocks[j][k].getY(), blocks[j][k].getZ() & 15, 0, 0, false);
                                for (Player player : updatedPlayers) {
                                    player.getUser().a.b(new OPacket53BlockChange(blocks[j][k].getX(), blocks[j][k].getY(), blocks[j][k].getZ(), block.getWorld().getWorld()));
                                }
                            }
                        }
                    }
                }
            }
            if (removeAll == false) {
                return true;
            }
        }
        // CanaryMod hook - onBlockBreak
        if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.BLOCK_BROKEN, ((OEntityPlayerMP) b).getPlayer(), block)) {
            return true;
        }
        // CanaryMod end
    	
        int var4 = this.a.a(var1, var2, var3);
        int var5 = this.a.c(var1, var2, var3);

        this.a.a(this.b, 2001, var1, var2, var3, var4 + (this.a.c(var1, var2, var3) << 12));
        boolean var6 = this.b(var1, var2, var3);

        if (this.b()) {
            ((OEntityPlayerMP) this.b).a.b((OPacket) (new OPacket53BlockChange(var1, var2, var3, this.a)));
        } else {
            OItemStack var7 = this.b.T();
            boolean var8 = this.b.b(OBlock.m[var4]);

            if (var7 != null) {
                var7.a(var4, var1, var2, var3, this.b);
                if (var7.a == 0) {
                    var7.a(this.b);
                    this.b.U();
                }
            }

            if (var6 && var8) {
                OBlock.m[var4].a(this.a, this.b, var1, var2, var3, var5);
            }
        }

        return var6;
    }

    public boolean a(OEntityPlayer var1, OWorld var2, OItemStack var3) {		
        int var4 = var3.a;
        int var5 = var3.h();
        OItemStack var6 = var3.a(var2, var1);

        if (var6 == var3 && (var6 == null || var6.a == var4) && (var6 == null || var6.l() <= 0)) {
            return false;
        } else {
            var1.k.a[var1.k.c] = var6;
            if (this.b()) {
                var6.a = var4;
                var6.b(var5);
            }

            if (var6.a == 0) {
                var1.k.a[var1.k.c] = null;
            }

            return true;
        }
    }
	
	/**
     * Called when a player right-click air with an item in hand. We intercept
     * it.
     * 
     * @param player
     * @param world
     * @param item
     * @param blockToPlace
     * @param blockClicked
     * @return
     */
    public boolean itemUsed(OEntityPlayer player, OWorld world, OItemStack item, Block blockToPlace, Block blockClicked) {
        // CanaryMod: only call this hook if we're not using buckets/signs
        if (item != null) {
            if (item.a > 0 && item.c != Item.Type.Sign.getId() && item.c != Item.Type.Bucket.getId() && item.c != Item.Type.WaterBucket.getId() && item.c != Item.Type.LavaBucket.getId() && item.c != Item.Type.MilkBucket.getId()) {
                if (player instanceof OEntityPlayerMP && (Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE, ((OEntityPlayerMP) player).getPlayer(), blockToPlace, blockClicked, new Item(item))) {
                    return false;
                }
            }
        }
        return this.a(player, world, item);
    }

    public boolean a(OEntityPlayer var1, OWorld var2, OItemStack var3, int var4, int var5, int var6, int var7) {
        int var8 = var2.a(var4, var5, var6);

        if (var8 > 0 && OBlock.m[var8].a(var2, var4, var5, var6, var1)) {
            return true;
        } else if (var3 == null) {
            return false;
        } else if (this.b()) {
            int var9 = var3.h();
            int var10 = var3.a;
            boolean var11 = var3.a(var1, var2, var4, var5, var6, var7);

            var3.b(var9);
            var3.a = var10;
            return var11;
        } else {
            return var3.a(var1, var2, var4, var5, var6, var7);
        }
    }

    public void a(OWorldServer var1) {
        this.a = var1;
    }
	
	// CanaryMod start - getPortalBlocks
    private Block[][] getPortalBlocks(World world, int x, int y, int z) {
        int portalId = Block.Type.Portal.getType();

        if (world.getBlockIdAt(x, y, z) == portalId) {
            // These will be equal 1 if the portal is defined on their axis
            // and 0 if not.
            int portalXOffset = (world.getBlockIdAt(x - 1, y, z) == portalId || world.getBlockIdAt(x + 1, y, z) == portalId) ? 1 : 0;
            int portalZOffset = (world.getBlockIdAt(x, y, z - 1) == portalId || world.getBlockIdAt(x, y, z + 1) == portalId) ? 1 : 0;

            // If the portal is either x aligned or z aligned but not both
            // (has neighbor portal in x or z plane but not both)
            if (portalXOffset != portalZOffset) {
                // Get the edge of the portal.
                int portalX = x - ((world.getBlockIdAt(x - 1, y, z) == portalId) ? 1 : 0);
                int portalZ = z - ((world.getBlockIdAt(x, y, z - 1) == portalId) ? 1 : 0);
                int portalY = y;

                while (world.getBlockIdAt(portalX, ++portalY, portalZ) == portalId) {
                    ;
                }
                portalY -= 1;
                // Scan the portal and see if its still all there (2x3
                // formation)
                boolean completePortal = true;
                Block[][] portalBlocks = new Block[3][2];

                for (int i = 0; i < 3 && completePortal == true; i += 1) {
                    for (int j = 0; j < 2 && completePortal == true; j += 1) {
                        portalBlocks[i][j] = world.getBlockAt(portalX + j * portalXOffset, portalY - i, portalZ + j * portalZOffset);
                        if (portalBlocks[i][j].getType() != portalId) {
                            completePortal = false;
                        }
                    }
                }
                if (completePortal == true) {
                    return portalBlocks;
                }
            }
        }
        return null;
    }
    // CanaryMod end
}

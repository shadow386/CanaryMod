import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;


public class OChunk {

    public static boolean a;
    public byte[] b;
    public int[] c;
    public boolean[] d;
    public boolean e;
    public OWorld f;
    public ONibbleArray g;
    public ONibbleArray h;
    public ONibbleArray i;
    public byte[] j;
    public int k;
    public final int l;
    public final int m;
    private boolean v;
    public Map n;
    public List[] o;
    public boolean p;
    public boolean q;
    public boolean r;
    public boolean s;
    public long t;
    boolean u;

    // CanaryMod
    public final Chunk chunk = new Chunk(this);
    // antiXRayBlocks holds integers that represent the local coordinates of hidden blocks in the chunk.
    // The index of each block is represented like so: (x << 11 | z << 7 || y).
    // X and Z can be between 0-15: 4 bits each. Y can be from 0 to 127: 7 bits. Byte looks like so UXXXXYYYYZZZZZZZ (with U being Unused at the moment)
    public final HashMap<Integer, Integer> antiXRayBlocks = new HashMap<Integer, Integer>();
    public boolean needsScanning = true;
    public final Object antiXRayBlocksLock = new Object();

    public OChunk(OWorld var1, int var2, int var3) {
        super();
        this.c = new int[256];
        this.d = new boolean[256];
        this.v = false;
        this.n = new HashMap();
        this.p = false;
        this.q = false;
        this.s = false;
        this.t = 0L;
        this.u = false;
        this.o = new List[var1.c / 16];
        this.f = var1;
        this.l = var2;
        this.m = var3;
        this.j = new byte[256];

        for (int var4 = 0; var4 < this.o.length; ++var4) {
            this.o[var4] = new ArrayList();
        }

        Arrays.fill(this.c, -999);
    }

    public OChunk(OWorld var1, byte[] var2, int var3, int var4) {
        this(var1, var3, var4);
        this.b = var2;
        this.g = new ONibbleArray(var2.length, var1.a);
        this.h = new ONibbleArray(var2.length, var1.a);
        this.i = new ONibbleArray(var2.length, var1.a);
    }

    public boolean a(int var1, int var2) {
        return var1 == this.l && var2 == this.m;
    }

    public int b(int var1, int var2) {
        return this.j[var2 << 4 | var1] & 255;
    }

    public void a() {}

    public void b() {
        int var1 = this.f.c - 1;

        int var2;
        int var3;

        for (var2 = 0; var2 < 16; ++var2) {
            for (var3 = 0; var3 < 16; ++var3) {
                int var4 = this.f.c - 1;

                int var5;

                for (var5 = var2 << this.f.b | var3 << this.f.a; var4 > 0 && OBlock.q[this.b[var5 + var4 - 1] & 255] == 0; --var4) {
                    ;
                }

                this.j[var3 << 4 | var2] = (byte) var4;
                if (var4 < var1) {
                    var1 = var4;
                }

                if (!this.f.y.f) {
                    int var6 = 15;
                    int var7 = this.f.c - 1;

                    do {
                        var6 -= OBlock.q[this.b[var5 + var7] & 255];
                        if (var6 > 0) {
                            this.h.a(var2, var7, var3, var6);
                        }

                        --var7;
                    } while (var7 > 0 && var6 > 0);
                }
            }
        }

        this.k = var1;

        for (var2 = 0; var2 < 16; ++var2) {
            for (var3 = 0; var3 < 16; ++var3) {
                this.d(var2, var3);
            }
        }

        this.q = true;
    }

    public void c() {}

    private void d(int var1, int var2) {
        this.d[var1 + var2 * 16] = true;
        this.v = true;
    }

    private void k() {
        if (this.f.a(this.l * 16 + 8, this.f.c / 2, this.m * 16 + 8, 16)) {
            for (int var1 = 0; var1 < 16; ++var1) {
                for (int var2 = 0; var2 < 16; ++var2) {
                    if (this.d[var1 + var2 * 16]) {
                        this.d[var1 + var2 * 16] = false;
                        int var3 = this.b(var1, var2);
                        int var4 = this.l * 16 + var1;
                        int var5 = this.m * 16 + var2;
                        int var6 = this.f.d(var4 - 1, var5);
                        int var7 = this.f.d(var4 + 1, var5);
                        int var8 = this.f.d(var4, var5 - 1);
                        int var9 = this.f.d(var4, var5 + 1);

                        if (var7 < var6) {
                            var6 = var7;
                        }

                        if (var8 < var6) {
                            var6 = var8;
                        }

                        if (var9 < var6) {
                            var6 = var9;
                        }

                        this.f(var4, var5, var6);
                        this.f(var4 - 1, var5, var3);
                        this.f(var4 + 1, var5, var3);
                        this.f(var4, var5 - 1, var3);
                        this.f(var4, var5 + 1, var3);
                        this.v = false;
                    }
                }
            }
        }

    }

    private void f(int var1, int var2, int var3) {
        int var4 = this.f.d(var1, var2);

        if (var4 > var3) {
            this.d(var1, var2, var3, var4 + 1);
        } else if (var4 < var3) {
            this.d(var1, var2, var4, var3 + 1);
        }

    }

    private void d(int var1, int var2, int var3, int var4) {
        if (var4 > var3 && this.f.a(var1, this.f.c / 2, var2, 16)) {
            for (int var5 = var3; var5 < var4; ++var5) {
                this.f.b(OEnumSkyBlock.a, var1, var5, var2);
            }

            this.q = true;
        }

    }

    private void g(int var1, int var2, int var3) {
        int var4 = this.j[var3 << 4 | var1] & 255;
        int var5 = var4;

        if (var2 > var4) {
            var5 = var2;
        }

        for (int var6 = var1 << this.f.b | var3 << this.f.a; var5 > 0 && OBlock.q[this.b[var6 + var5 - 1] & 255] == 0; --var5) {
            ;
        }

        if (var5 != var4) {
            this.f.g(var1, var3, var5, var4);
            this.j[var3 << 4 | var1] = (byte) var5;
            int var7;
            int var8;
            int var9;

            if (var5 < this.k) {
                this.k = var5;
            } else {
                var7 = this.f.c - 1;

                for (var8 = 0; var8 < 16; ++var8) {
                    for (var9 = 0; var9 < 16; ++var9) {
                        if ((this.j[var9 << 4 | var8] & 255) < var7) {
                            var7 = this.j[var9 << 4 | var8] & 255;
                        }
                    }
                }

                this.k = var7;
            }

            var7 = this.l * 16 + var1;
            var8 = this.m * 16 + var3;
            int var10;

            if (!this.f.y.f) {
                if (var5 < var4) {
                    for (var9 = var5; var9 < var4; ++var9) {
                        this.h.a(var1, var9, var3, 15);
                    }
                } else {
                    for (var9 = var4; var9 < var5; ++var9) {
                        this.h.a(var1, var9, var3, 0);
                    }
                }

                for (var9 = 15; var5 > 0 && var9 > 0; this.h.a(var1, var5, var3, var9)) {
                    --var5;
                    var10 = OBlock.q[this.a(var1, var5, var3)];
                    if (var10 == 0) {
                        var10 = 1;
                    }

                    var9 -= var10;
                    if (var9 < 0) {
                        var9 = 0;
                    }
                }
            }

            byte var13 = this.j[var3 << 4 | var1];

            var10 = var4;
            int var11 = var13;

            if (var13 < var4) {
                var10 = var13;
                var11 = var4;
            }

            if (!this.f.y.f) {
                this.d(var7 - 1, var8, var10, var11);
                this.d(var7 + 1, var8, var10, var11);
                this.d(var7, var8 - 1, var10, var11);
                this.d(var7, var8 + 1, var10, var11);
                this.d(var7, var8, var10, var11);
            }

            this.q = true;
        }
    }

    public int a(int var1, int var2, int var3) {
        return this.b[var1 << this.f.b | var3 << this.f.a | var2] & 255;
    }

    public boolean a(int var1, int var2, int var3, int var4, int var5) {
        return a(var1, var2, var3, var4, var5, true);
    }
   
    public boolean a(int var1, int var2, int var3, int var4, int var5, boolean checkPortal) {
        byte var6 = (byte) var4;
        int var7 = var3 << 4 | var1;

        if (var2 >= this.c[var7] - 1) {
            this.c[var7] = -999;
        }

        int var8 = this.j[var3 << 4 | var1] & 255;
        int var9 = this.b[var1 << this.f.b | var3 << this.f.a | var2] & 255;

        if (var9 == var4 && this.g.a(var1, var2, var3) == var5) {
            return false;
        } else {
            int var10 = this.l * 16 + var1;
            int var11 = this.m * 16 + var3;

            if (checkPortal == true) {
                int portalPointX = var10;
                int portalPointY = var2;
                int portalPointZ = var11;
                // CanaryMod check if removed block is portal block
                int portalId = Block.Type.Portal.getType();

                if (chunk.getWorld().getBlockIdAt(portalPointX, portalPointY, portalPointZ) == portalId) {
                    // These will be equal 1 if the portal is defined on their axis and 0 if not.
                    int portalXOffset = (chunk.getWorld().getBlockIdAt(portalPointX - 1, portalPointY, portalPointZ) == portalId || chunk.getWorld().getBlockIdAt(portalPointX + 1, portalPointY, portalPointZ) == portalId) ? 1 : 0;
                    int portalZOffset = (chunk.getWorld().getBlockIdAt(portalPointX, portalPointY, portalPointZ - 1) == portalId || chunk.getWorld().getBlockIdAt(portalPointX, portalPointY, portalPointZ + 1) == portalId) ? 1 : 0;

                    // If the portal is either x aligned or z aligned but not both (has neighbor portal in x or z plane but not both)
                    if (portalXOffset != portalZOffset) {
                        // Get the edge of the portal.
                        int portalX = portalPointX - ((chunk.getWorld().getBlockIdAt(portalPointX - 1, portalPointY, portalPointZ) == portalId) ? 1 : 0);
                        int portalZ = portalPointZ - ((chunk.getWorld().getBlockIdAt(portalPointX, portalPointY, portalPointZ - 1) == portalId) ? 1 : 0);
                        int portalY = var2;

                        while (chunk.getWorld().getBlockIdAt(portalX, ++portalY, portalZ) == portalId) {
                            ;
                        }
                        portalY -= 1;
                        // Scan the portal and see if its still all there (2x3 formation)
                        boolean completePortal = true;
                        Block[][] portalBlocks = new Block[3][2];

                        for (int i1 = 0; i1 < 3 && completePortal == true; i1 += 1) {
                            for (int j1 = 0; j1 < 2 && completePortal == true; j1 += 1) {
                                portalBlocks[i1][j1] = chunk.getWorld().getBlockAt(portalX + j1 * portalXOffset, portalY - i1, portalZ + j1 * portalZOffset);
                                if (portalBlocks[i1][j1].getType() != portalId) {
                                    completePortal = false;
                                }
                            }
                        }
                        if (completePortal == true) {
                            // CanaryMod hook onPortalDestroy
                            if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.PORTAL_DESTROY, (Object) portalBlocks)) {
                                // Hook returned true = don't destroy the portal.
                                // in that case we need to reconstruct the portal's frame to make the portal valid.
                                // Problem is we don't want to reconstruct it right away because more blocks might be deleted (for example on explosion).
                                // In order to avoid spamming the hook for each destroyed block, I'm queuing the reconstruction of the portal instead.
                                etc.getServer().addToServerQueue(new PortalReconstructJob(chunk.getWorld(), portalX, portalY, portalZ, (portalXOffset == 1)));
                            }
                        }
                    }
                }
            }
            Integer index = new Integer(var1 << 11 | var3 << 7 | var2);
            int oldBlockID = this.b[index];

            this.b[index] = (byte) (var6 & 255);
            // CanaryMod: If the anti xray mechanism is enabled
            if (etc.getInstance().isAntiXRayEnabled()) {
                // If setting an anti xray blocks to something else.
                if (etc.getDataSource().getAntiXRayBlocks().contains(new Integer(oldBlockID))) {
                    synchronized (this.antiXRayBlocksLock) {
                        if (this.antiXRayBlocks.containsKey(index)) {
                            this.antiXRayBlocks.remove(index);
                        }
                    }
                }
                // If setting the block to one of the anti xray blocks
                if (etc.getDataSource().getAntiXRayBlocks().contains(new Integer(var4))) {
                    // Add it to the anti xray blocks if it doesn't already exist.
                    synchronized (this.antiXRayBlocksLock) {
                        if (!this.antiXRayBlocks.containsKey(index)) {
                            this.antiXRayBlocks.put(index, new Integer(var4));
                        }
                    }
                }
                updateNeighborAntiXRayBlocks(var1, var2, var3);
            }
            
            if (var9 != 0) {
                if (!this.f.I) {
                    OBlock.m[var9].d(this.f, var10, var2, var11);
                } else if (OBlock.m[var9] instanceof OBlockContainer && var9 != var4) {
                    this.f.n(var10, var2, var11);
                }
            }

            this.g.a(var1, var2, var3, var5);
            if (!this.f.y.f) {
                if (OBlock.q[var6 & 255] != 0) {
                    if (var2 >= var8) {
                        this.g(var1, var2 + 1, var3);
                    }
                } else if (var2 == var8 - 1) {
                    this.g(var1, var2, var3);
                }

                this.f.a(OEnumSkyBlock.a, var10, var2, var11, var10, var2, var11);
            }

            this.f.a(OEnumSkyBlock.b, var10, var2, var11, var10, var2, var11);
            this.d(var1, var3);
            this.g.a(var1, var2, var3, var5);
            OTileEntity var12;

            if (var4 != 0) {
                if (!this.f.I) {
                    OBlock.m[var4].a(this.f, var10, var2, var11);
                }

                if (OBlock.m[var4] instanceof OBlockContainer) {
                    var12 = this.d(var1, var2, var3);
                    if (var12 == null) {
                        var12 = ((OBlockContainer) OBlock.m[var4]).a_();
                        this.f.a(var10, var2, var11, var12);
                    }

                    if (var12 != null) {
                        var12.d();
                    }
                }
            } else if (var9 > 0 && OBlock.m[var9] instanceof OBlockContainer) {
                var12 = this.d(var1, var2, var3);
                if (var12 != null) {
                    var12.d();
                }
            }

            this.q = true;
            return true;
        }
    }

    public boolean a(int var1, int var2, int var3, int var4) {
        return a(var1, var2, var3, var4, true);
    }
   
    public boolean a(int var1, int var2, int var3, int var4, boolean checkPortal) {
        byte var5 = (byte) var4;
        int var6 = var3 << 4 | var1;

        if (var2 >= this.c[var6] - 1) {
            this.c[var6] = -999;
        }

        int var7 = this.j[var6] & 255;
        int var8 = this.b[var1 << this.f.b | var3 << this.f.a | var2] & 255;

        if (var8 == var4) {
            return false;
        } else {
            int var9 = this.l * 16 + var1;
            int var10 = this.m * 16 + var3;

            if (checkPortal == true) {
                int portalPointX = var9;
                int portalPointY = var2;
                int portalPointZ = var10;
                // CanaryMod check if removed block is portal block
                int portalId = Block.Type.Portal.getType();

                if (chunk.getWorld().getBlockIdAt(portalPointX, portalPointY, portalPointZ) == portalId) {
                    // These will be equal 1 if the portal is defined on theirn axis and 0 if not.
                    int portalXOffset = (chunk.getWorld().getBlockIdAt(portalPointX - 1, portalPointY, portalPointZ) == portalId || chunk.getWorld().getBlockIdAt(portalPointX + 1, portalPointY, portalPointZ) == portalId) ? 1 : 0;
                    int portalZOffset = (chunk.getWorld().getBlockIdAt(portalPointX, portalPointY, portalPointZ - 1) == portalId || chunk.getWorld().getBlockIdAt(portalPointX, portalPointY, portalPointZ + 1) == portalId) ? 1 : 0;

                    // If the portal is either x aligned or z aligned but not both (has neighbor portal in x or z plane but not both)
                    if (portalXOffset != portalZOffset) {
                        // Get the edge of the portal.
                        int portalX = portalPointX - ((chunk.getWorld().getBlockIdAt(portalPointX - 1, portalPointY, portalPointZ) == portalId) ? 1 : 0);
                        int portalZ = portalPointZ - ((chunk.getWorld().getBlockIdAt(portalPointX, portalPointY, portalPointZ - 1) == portalId) ? 1 : 0);
                        int portalY = var2;

                        while (chunk.getWorld().getBlockIdAt(portalX, ++portalY, portalZ) == portalId) {
                            ;
                        }
                        portalY -= 1;
                        // Scan the portal and see if its still all there (2x3 formation)
                        boolean completePortal = true;
                        Block[][] portalBlocks = new Block[3][2];

                        for (int i1 = 0; i1 < 3 && completePortal == true; i1 += 1) {
                            for (int j1 = 0; j1 < 2 && completePortal == true; j1 += 1) {
                                portalBlocks[i1][j1] = chunk.getWorld().getBlockAt(portalX + j1 * portalXOffset, portalY - i1, portalZ + j1 * portalZOffset);
                                if (portalBlocks[i1][j1].getType() != portalId) {
                                    completePortal = false;
                                }
                            }
                        }
                        if (completePortal == true) {
                            // CanaryMod hook onPortalDestroy
                            if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.PORTAL_DESTROY, (Object) portalBlocks)) {
                                // Hook returned true = don't destroy the portal.
                                // in that case we need to reconstruct the portal's frame to make the portal valid.
                                // Problem is we don't want to reconstruct it right away because more blocks might be deleted (for example on explosion).
                                // In order to avoid spamming the hook for each destroyed block, I'm queuing the reconstruction of the portal instead.
                                etc.getServer().addToServerQueue(new PortalReconstructJob(chunk.getWorld(), portalX, portalY, portalZ, (portalXOffset == 1)));
                            }
                        }
                    }
                }
            }
            Integer index = new Integer(var1 << 11 | var3 << 7 | var2);
            int oldBlockID = this.b[index];

            this.b[index] = (byte) (var5 & 255);
            // CanaryMod: If the anti xray mechanism is enabled
            if (etc.getInstance().isAntiXRayEnabled()) {
                // If setting an anti xray blocks to something else.
                if (etc.getDataSource().getAntiXRayBlocks().contains(new Integer(oldBlockID))) {
                    synchronized (this.antiXRayBlocksLock) {
                        if (this.antiXRayBlocks.containsKey(index)) {
                            this.antiXRayBlocks.remove(index);
                        }
                    }
                }
                // If setting the block to one of the anti xray blocks
                if (etc.getDataSource().getAntiXRayBlocks().contains(new Integer(var4))) {
                    // Add it to the anti xray blocks if it doesn't already exist.
                    synchronized (this.antiXRayBlocksLock) {
                        if (!this.antiXRayBlocks.containsKey(index)) {
                            this.antiXRayBlocks.put(index, new Integer(var4));
                        }
                    }
                }
                updateNeighborAntiXRayBlocks(var1, var2, var3);
            }
            
            if (var8 != 0) {
                OBlock.m[var8].d(this.f, var9, var2, var10);
            }

            this.g.a(var1, var2, var3, 0);
            if (OBlock.q[var5 & 255] != 0) {
                if (var2 >= var7) {
                    this.g(var1, var2 + 1, var3);
                }
            } else if (var2 == var7 - 1) {
                this.g(var1, var2, var3);
            }

            this.f.a(OEnumSkyBlock.a, var9, var2, var10, var9, var2, var10);
            this.f.a(OEnumSkyBlock.b, var9, var2, var10, var9, var2, var10);
            this.d(var1, var3);
            OTileEntity var11;

            if (var4 != 0) {
                if (!this.f.I) {
                    OBlock.m[var4].a(this.f, var9, var2, var10);
                }

                if (var4 > 0 && OBlock.m[var4] instanceof OBlockContainer) {
                    var11 = this.d(var1, var2, var3);
                    if (var11 == null) {
                        var11 = ((OBlockContainer) OBlock.m[var4]).a_();
                        this.f.a(var9, var2, var10, var11);
                    }

                    if (var11 != null) {
                        var11.d();
                    }
                }
            } else if (var8 > 0 && OBlock.m[var8] instanceof OBlockContainer) {
                var11 = this.d(var1, var2, var3);
                if (var11 != null) {
                    var11.d();
                }
            }

            this.q = true;
            return true;
        }
    }

    public int b(int var1, int var2, int var3) {
        return this.g.a(var1, var2, var3);
    }

    public boolean b(int var1, int var2, int var3, int var4) {
        this.q = true;
        int var5 = this.g.a(var1, var2, var3);

        if (var5 == var4) {
            return false;
        } else {
            this.g.a(var1, var2, var3, var4);
            int var6 = this.a(var1, var2, var3);

            if (var6 > 0 && OBlock.m[var6] instanceof OBlockContainer) {
                OTileEntity var7 = this.d(var1, var2, var3);

                if (var7 != null) {
                    var7.d();
                    var7.p = var4;
                }
            }

            return true;
        }
    }

    public int a(OEnumSkyBlock var1, int var2, int var3, int var4) {
        return var1 == OEnumSkyBlock.a ? this.h.a(var2, var3, var4) : (var1 == OEnumSkyBlock.b ? this.i.a(var2, var3, var4) : 0);
    }

    public void a(OEnumSkyBlock var1, int var2, int var3, int var4, int var5) {
        this.q = true;
        if (var1 == OEnumSkyBlock.a) {
            if (!this.f.y.f) {
                this.h.a(var2, var3, var4, var5);
            }
        } else {
            if (var1 != OEnumSkyBlock.b) {
                return;
            }

            this.i.a(var2, var3, var4, var5);
        }

    }

    public int c(int var1, int var2, int var3, int var4) {
        int var5 = this.f.y.f ? 0 : this.h.a(var1, var2, var3);

        if (var5 > 0) {
            a = true;
        }

        var5 -= var4;
        int var6 = this.i.a(var1, var2, var3);

        if (var6 > var5) {
            var5 = var6;
        }

        return var5;
    }

    public void a(OEntity var1) {
        this.s = true;
        int var2 = OMathHelper.b(var1.bm / 16.0D);
        int var3 = OMathHelper.b(var1.bo / 16.0D);

        if (var2 != this.l || var3 != this.m) {
            System.out.println("Wrong location! " + var1);
            Thread.dumpStack();
        }

        int var4 = OMathHelper.b(var1.bn / 16.0D);

        if (var4 < 0) {
            var4 = 0;
        }

        if (var4 >= this.o.length) {
            var4 = this.o.length - 1;
        }

        var1.bZ = true;
        var1.ca = this.l;
        var1.cb = var4;
        var1.cc = this.m;
        this.o[var4].add(var1);
    }

    public void b(OEntity var1) {
        this.a(var1, var1.cb);
    }

    public void a(OEntity var1, int var2) {
        if (var2 < 0) {
            var2 = 0;
        }

        if (var2 >= this.o.length) {
            var2 = this.o.length - 1;
        }

        this.o[var2].remove(var1);
    }

    public boolean c(int var1, int var2, int var3) {
        return var2 >= (this.j[var3 << 4 | var1] & 255);
    }

    public OTileEntity d(int var1, int var2, int var3) {
        OChunkPosition var4 = new OChunkPosition(var1, var2, var3);
        OTileEntity var5 = (OTileEntity) this.n.get(var4);

        if (var5 == null) {
            int var6 = this.a(var1, var2, var3);

            if (!OBlock.p[var6]) {
                return null;
            }

            if (var5 == null) {
                var5 = ((OBlockContainer) OBlock.m[var6]).a_();
                this.f.a(this.l * 16 + var1, var2, this.m * 16 + var3, var5);
            }

            var5 = (OTileEntity) this.n.get(var4);
        }

        if (var5 != null && var5.l()) {
            this.n.remove(var4);
            return null;
        } else {
            return var5;
        }
    }

    public void a(OTileEntity var1) {
        int var2 = var1.l - this.l * 16;
        int var3 = var1.m;
        int var4 = var1.n - this.m * 16;

        this.a(var2, var3, var4, var1);
        if (this.e) {
            this.f.h.add(var1);
        }

    }

    public void a(int var1, int var2, int var3, OTileEntity var4) {
        OChunkPosition var5 = new OChunkPosition(var1, var2, var3);

        var4.k = this.f;
        var4.l = this.l * 16 + var1;
        var4.m = var2;
        var4.n = this.m * 16 + var3;
        if (this.a(var1, var2, var3) != 0 && OBlock.m[this.a(var1, var2, var3)] instanceof OBlockContainer) {
            var4.m();
            this.n.put(var5, var4);
        }
    }

    public void e(int var1, int var2, int var3) {
        OChunkPosition var4 = new OChunkPosition(var1, var2, var3);

        if (this.e) {
            OTileEntity var5 = (OTileEntity) this.n.remove(var4);

            if (var5 != null) {
                var5.i();
            }
        }

    }

    public void d() {
        this.e = true;
        this.f.a(this.n.values());

        for (int var1 = 0; var1 < this.o.length; ++var1) {
            this.f.a(this.o[var1]);
        }

    }

    public void e() {
        this.e = false;
        Iterator var1 = this.n.values().iterator();

        while (var1.hasNext()) {
            OTileEntity var2 = (OTileEntity) var1.next();

            this.f.a(var2);
        }

        for (int var3 = 0; var3 < this.o.length; ++var3) {
            this.f.b(this.o[var3]);
        }

    }

    public void f() {
        this.q = true;
    }

    public void a(OEntity var1, OAxisAlignedBB var2, List var3) {
        int var4 = OMathHelper.b((var2.b - 2.0D) / 16.0D);
        int var5 = OMathHelper.b((var2.e + 2.0D) / 16.0D);

        if (var4 < 0) {
            var4 = 0;
        }

        if (var5 >= this.o.length) {
            var5 = this.o.length - 1;
        }

        for (int var6 = var4; var6 <= var5; ++var6) {
            List var7 = this.o[var6];

            for (int var8 = 0; var8 < var7.size(); ++var8) {
                OEntity var9 = (OEntity) var7.get(var8);

                if (var9 != var1 && var9.bw.a(var2)) {
                    var3.add(var9);
                    OEntity[] var10 = var9.aR();

                    if (var10 != null) {
                        for (int var11 = 0; var11 < var10.length; ++var11) {
                            var9 = var10[var11];
                            if (var9 != var1 && var9.bw.a(var2)) {
                                var3.add(var9);
                            }
                        }
                    }
                }
            }
        }

    }

    public void a(Class var1, OAxisAlignedBB var2, List var3) {
        int var4 = OMathHelper.b((var2.b - 2.0D) / 16.0D);
        int var5 = OMathHelper.b((var2.e + 2.0D) / 16.0D);

        if (var4 < 0) {
            var4 = 0;
        } else if (var4 >= this.o.length) {
            var4 = this.o.length - 1;
        }

        if (var5 >= this.o.length) {
            var5 = this.o.length - 1;
        } else if (var5 < 0) {
            var5 = 0;
        }

        for (int var6 = var4; var6 <= var5; ++var6) {
            List var7 = this.o[var6];

            for (int var8 = 0; var8 < var7.size(); ++var8) {
                OEntity var9 = (OEntity) var7.get(var8);

                if (var1.isAssignableFrom(var9.getClass()) && var9.bw.a(var2)) {
                    var3.add(var9);
                }
            }
        }

    }

    public boolean a(boolean var1) {
        if (this.r) {
            return false;
        } else {
            if (var1) {
                if (this.s && this.f.n() != this.t) {
                    return true;
                }
            } else if (this.s && this.f.n() >= this.t + 600L) {
                return true;
            }

            return this.q;
        }
    }

    // CanaryMod function alias: By default when returning the chunk data, it will be returned without the anti xray mechanism interfering with it.
    // When the anti xray is enabled, it'll generate the chunk data without the hidden blocks.
    public int a(byte[] chunkDataToFill, int relativeStartX, int relativeStartY, int relativeStartZ, int relativeEndX, int relativeEndY, int relativeEndZ, int bufferOffset) {
        return a(chunkDataToFill, relativeStartX, relativeStartY, relativeStartZ, relativeEndX, relativeEndY, relativeEndZ, bufferOffset, false);
    }
    
    // CanaryMod function alias: Returns the chunk's data at a given xyz to xyz based on wether anti xray is enabled.
    // The chunkDataToFill consists of 4 sections: block types, block datas, block light maps and sky light maps.
    // The block types take 1 byte per block and the datas and light maps takes 4 bits per block, So the final chunkDataToFill's size must be sizeX * sizeY * sizeZ * 2.5 in length.
    public int a(byte[] chunkDataToFill, int relativeStartX, int relativeStartY, int relativeStartZ, int relativeEndX, int relativeEndY, int relativeEndZ, int bufferOffset, boolean replaceAntiXRayBlocks) {
        int sizeX = relativeEndX - relativeStartX;
        int sizeY = relativeEndY - relativeStartY;
        int sizeZ = relativeEndZ - relativeStartZ;

        // If all the sizes match b's length (the number of blocks the chunk holds) it means we are returning the whole chunk's data.
        if (sizeX * sizeY * sizeZ == this.b.length) {
            System.arraycopy(this.b, 0, chunkDataToFill, bufferOffset, this.b.length);     
        	
            if (replaceAntiXRayBlocks) {
                // If the chunk has not been scanned yet (not fully generated)
                if (needsScanning) {
                    // Scan all the data and replace with stone.
                    List<Integer> antiXRayBlocksIDs = etc.getDataSource().getAntiXRayBlocks();

                    for (int i1 = 0; i1 < this.b.length; i1 += 1) {
                        if (antiXRayBlocksIDs.contains(new Integer(chunkDataToFill[bufferOffset + i1]))) {
                            synchronized (this.antiXRayBlocksLock) {
                                if (!this.antiXRayBlocks.containsKey(new Integer(bufferOffset + i1))) {
                                    this.antiXRayBlocks.put(new Integer(bufferOffset + i1), new Integer(chunkDataToFill[bufferOffset + i1]));
                                }
                            }
                            if (isHidden(((bufferOffset + i1) >> 11) & 0xf, (bufferOffset + i1) & 0x7f, ((bufferOffset + i1) >> 7) & 0xf)) {
                                chunkDataToFill[bufferOffset + i1] = 1;
                            }
                        }
                    }
                } else {
                    // Replacing blocks for a whole chunk is easy pie.
                    for (Integer replacedBlockIndex : this.antiXRayBlocks.keySet()) {
                        // The index we need to modify in the chunkDataToFill is represented by this load o' crap.
                        byte replacedBlockX = (byte) ((replacedBlockIndex >> 11) & 0xf);
                        byte replacedBlockY = (byte) (replacedBlockIndex & 0x7f);
                        byte replacedBlockZ = (byte) ((replacedBlockIndex >> 7) & 0xf);
                        
                        try {
                            // Replaces the hidden block with stone.
                        	
                            if (isHidden(replacedBlockX, replacedBlockY, replacedBlockZ)) {
                                chunkDataToFill[bufferOffset + replacedBlockY + (replacedBlockZ * sizeY) + (replacedBlockX * sizeY * sizeZ)] = 1;
                            }
                        } catch (Exception e) {
                            Logger.getLogger("Minecraft").severe("Error in masking full chunk: (" + this.l + ", " + this.m + ")");
                            Logger.getLogger("Minecraft").severe("Buffer offset: " + bufferOffset);
                            Logger.getLogger("Minecraft").severe("Matches AntiXRay cache index: " + replacedBlockIndex + " translates to (" + replacedBlockX + ", " + replacedBlockY + ", " + replacedBlockZ + ")");
                            Logger.getLogger("Minecraft").severe("Chunk data result index: " + (bufferOffset + replacedBlockY + (replacedBlockZ * sizeY) + (replacedBlockX * sizeY * sizeZ)));
                        }
                    }
                }
            }
            bufferOffset += this.b.length;
            System.arraycopy(this.g.a, 0, chunkDataToFill, bufferOffset, this.g.a.length);
            bufferOffset += this.g.a.length;
            System.arraycopy(this.i.a, 0, chunkDataToFill, bufferOffset, this.i.a.length);
            bufferOffset += this.i.a.length;
            System.arraycopy(this.h.a, 0, chunkDataToFill, bufferOffset, this.h.a.length);
            bufferOffset += this.h.a.length;
            return bufferOffset;
        } // The sizes don't add up to the whole chunk, so we need to do some indexing games.
        else {
            int relativeX;
            int relativeZ;
            int relevantIndex;
            int relevantSize;

            // CanaryMod: Copy the relevant blocks. Each block data takes up 1 byte (1-256 possible block IDs)
            for (relativeX = relativeStartX; relativeX < relativeEndX; ++relativeX) {
                for (relativeZ = relativeStartZ; relativeZ < relativeEndZ; ++relativeZ) {
                    relevantIndex = relativeX << this.f.b | relativeZ << this.f.a | relativeStartY;
                    relevantSize = relativeEndY - relativeStartY;
                    System.arraycopy(this.b, relevantIndex, chunkDataToFill, bufferOffset, relevantSize);
                    if (replaceAntiXRayBlocks) {
                        if (needsScanning) {
                            // Scan all the data and replace with stone.
                            List<Integer> antiXRayBlockIDs = etc.getDataSource().getAntiXRayBlocks();

                            for (int i1 = 0; i1 < relevantSize; i1 += 1) {
                                if (antiXRayBlockIDs.contains(new Integer(chunkDataToFill[bufferOffset + i1]))) {
                                    synchronized (this.antiXRayBlocksLock) {
                                        if (!this.antiXRayBlocks.containsKey(new Integer(bufferOffset + i1))) {
                                            this.antiXRayBlocks.put(new Integer(bufferOffset + i1), new Integer(chunkDataToFill[bufferOffset + i1]));
                                        }
                                    }
                                    if (isHidden(((bufferOffset + i1) >> 11) & 0xf, (bufferOffset + i1) & 0x7f, ((bufferOffset + i1) >> 7) & 0xf)) {
                                        chunkDataToFill[bufferOffset + i1] = 1;
                                    }
                                }
                            }
                        } else {
                            for (int relativeY = relativeStartY; relativeY < relativeEndY; relativeY += 1) {
                                if (this.antiXRayBlocks.containsKey(new Integer(relativeX << 11 | relativeZ << 7 | relativeY))) {
                                    if (isHidden(relativeX, relativeY, relativeZ)) {
                                        chunkDataToFill[bufferOffset + relativeY - relativeStartY] = 1;
                                    }
                                }
                            }
                        }
                    }
                    bufferOffset += relevantSize;
                }
            }

            for (relativeX = relativeStartX; relativeX < relativeEndX; ++relativeX) {
                for (relativeZ = relativeStartZ; relativeZ < relativeEndZ; ++relativeZ) {
                    relevantIndex = (relativeX << this.f.b | relativeZ << this.f.a | relativeStartY) >> 1;
                    relevantSize = (relativeEndY - relativeStartY) / 2;
                    System.arraycopy(this.g.a, relevantIndex, chunkDataToFill, bufferOffset, relevantSize);
                    bufferOffset += relevantSize;
                }
            }

            for (relativeX = relativeStartX; relativeX < relativeEndX; ++relativeX) {
                for (relativeZ = relativeStartZ; relativeZ < relativeEndZ; ++relativeZ) {
                    relevantIndex = (relativeX << this.f.b | relativeZ << this.f.a | relativeStartY) >> 1;
                    relevantSize = (relativeEndY - relativeStartY) / 2;
                    System.arraycopy(this.i.a, relevantIndex, chunkDataToFill, bufferOffset, relevantSize);
                    bufferOffset += relevantSize;
                }
            }

            for (relativeX = relativeStartX; relativeX < relativeEndX; ++relativeX) {
                for (relativeZ = relativeStartZ; relativeZ < relativeEndZ; ++relativeZ) {
                    relevantIndex = (relativeX << this.f.b | relativeZ << this.f.a | relativeStartY) >> 1;
                    relevantSize = (relativeEndY - relativeStartY) / 2;
                    System.arraycopy(this.h.a, relevantIndex, chunkDataToFill, bufferOffset, relevantSize);
                    bufferOffset += relevantSize;
                }
            }

            return bufferOffset;
        }
    }

    public Random a(long var1) {
        return new Random(this.f.m() + (long) (this.l * this.l * 4987142) + (long) (this.l * 5947611) + (long) (this.m * this.m) * 4392871L + (long) (this.m * 389711) ^ var1);
    }

    public boolean g() {
        return false;
    }
    
    // In order to construct the anti xray cache using this method, i'm editing OChunkBlockMap to accept a chunk reference.
    // That overloaded method will create the cache for meh.
    public void scan() {
        OChunkBlockMap.a(this);
        needsScanning = false;
    }

    public void h() {
        OChunkBlockMap.a(this.b);
    }

    public void a(OIChunkProvider var1, OIChunkProvider var2, int var3, int var4) {
        if (!this.p && var1.a(var3 + 1, var4 + 1) && var1.a(var3, var4 + 1) && var1.a(var3 + 1, var4)) {
            var1.a(var2, var3, var4);
        }

        if (var1.a(var3 - 1, var4) && !var1.b(var3 - 1, var4).p && var1.a(var3 - 1, var4 + 1) && var1.a(var3, var4 + 1) && var1.a(var3 - 1, var4 + 1)) {
            var1.a(var2, var3 - 1, var4);
        }

        if (var1.a(var3, var4 - 1) && !var1.b(var3, var4 - 1).p && var1.a(var3 + 1, var4 - 1) && var1.a(var3 + 1, var4 - 1) && var1.a(var3 + 1, var4)) {
            var1.a(var2, var3, var4 - 1);
        }

        if (var1.a(var3 - 1, var4 - 1) && !var1.b(var3 - 1, var4 - 1).p && var1.a(var3, var4 - 1) && var1.a(var3 - 1, var4)) {
            var1.a(var2, var3 - 1, var4 - 1);
        }

    }

    public int c(int var1, int var2) {
        int var3 = var1 | var2 << 4;
        int var4 = this.c[var3];

        if (var4 == -999) {
            int var5 = this.f.c - 1;

            var4 = -1;

            while (var5 > 0 && var4 == -1) {
                int var6 = this.a(var1, var5, var2);
                OMaterial var7 = var6 == 0 ? OMaterial.a : OBlock.m[var6].cb;

                if (!var7.c() && !var7.d()) {
                    --var5;
                } else {
                    var4 = var5 + 1;
                }
            }

            this.c[var3] = var4;
        }

        return var4;
    }

    public void i() {
        if (this.v && !this.f.y.f) {
            this.k();
        }

    }

    public OChunkCoordIntPair j() {
        return new OChunkCoordIntPair(this.l, this.m);
    }
    
    // CanaryMod function: Used to determine if a block should be hidden in the chunk.
    public boolean isHidden(int x, int y, int z) {
        // If attempting to hide a chest, we should also if its a double chest, in which case it should return true only if BOTH parts of the chest are hidden.
        return isHidden(x, y, z, this.b[x << 11 | z << 7 | y] == Block.Type.Chest.getType());
    }
    
    // CanaryMod function: Used to determine if a block should be hidden in the chunk.
    public boolean isHidden(int x, int y, int z, boolean checkNeighborChest) {
        boolean result;
        // If not all neighbor blocks are opaque we need to check for lightings.
        if (isBlockedByNeighbors(x, y, z) == false) {
            if (etc.getInstance().isAntiXRayLightingEnabled()) {
                result = !isLit(x, y, z);
            }
            else
            {
                result = false;
            }
        }
        else
        {
            result = true;
        }
        
        // If attempting to hide a chest, we need to check for double chest.
        // This code will not run if we are checking the SECOND part of the chest to prevent a chest checking its neighbor in an endless loop.
        if (result && checkNeighborChest) {
            if ((x < 15 && this.b[(x + 1) << 11 | z << 7 | y] == Block.Type.Chest.getType())) {
                return isHidden(x + 1, y, z, false);
            }
            else if (x == 15 && this.f.A.a(l + 1, m) && this.f.a(l * 16 + x + 1, y, m * 16 + z) == Block.Type.Chest.getType()) {
                return this.f.c(l + 1, m).isHidden(0, y, z, false);
            }
            
            if ((x > 0 && this.b[(x - 1) << 11 | z << 7 | y] == Block.Type.Chest.getType())) {
                return isHidden(x - 1, y, z, false);
            }
            else if (x == 0 && this.f.A.a(l - 1, m) && this.f.a(l * 16 + x - 1, y, m * 16 + z) == Block.Type.Chest.getType()) {
                return this.f.c(l - 1, m).isHidden(15, y, z, false);
            }
            
            if ((z < 15 && this.b[x << 11 | (z + 1) << 7 | y] == Block.Type.Chest.getType())) {
                return isHidden(x, y, z + 1, false);
            }
            else if (z == 15 && this.f.A.a(l, m + 1) && this.f.a(l * 16 + x, y, m * 16 + z + 1) == Block.Type.Chest.getType()) {
                return this.f.c(l, m + 1).isHidden(x, y, 0, false);
            }
            
            if ((z > 0 && this.b[x << 11 | (z - 1) << 7 | y] == Block.Type.Chest.getType())) {
                return isHidden(x, y, z - 1, false);
            }
            else if (z == 0 && this.f.A.a(l, m - 1) && this.f.a(l * 16 + x, y, m * 16 + z - 1) == Block.Type.Chest.getType()) {
                return this.f.c(l, m - 1).isHidden(x, y, 15, false);
            }
        }
        return result;
    }
    
    // CanaryMod function: Used to check for opaque blocks around a single block safely.
    public boolean isBlockedByNeighbors(int x, int y, int z) {
        // Blocks at the edges of the chunk are considered hidden by the nonexistant block.
        if (y < 127 && !etc.getInstance().isOpaqueAntiXRayBlock(this.b[x << 11 | z << 7 | y + 1])) {
            return false;
        }
        
        if (y > 0 && !etc.getInstance().isOpaqueAntiXRayBlock(this.b[x << 11 | z << 7 | y - 1])) {
            return false;
        }
        
        if ((x < 15 && !etc.getInstance().isOpaqueAntiXRayBlock(this.b[(x + 1) << 11 | z << 7 | y])) || (x == 15 && this.f.A.a(l + 1, m) && !etc.getInstance().isOpaqueAntiXRayBlock(this.f.a(l * 16 + x + 1, y, m * 16 + z)))) {
            return false;
        }
        
        if ((x > 0 && !etc.getInstance().isOpaqueAntiXRayBlock(this.b[(x - 1) << 11 | z << 7 | y])) || (x == 0 && this.f.A.a(l - 1, m) && !etc.getInstance().isOpaqueAntiXRayBlock(this.f.a(l * 16 + x - 1, y, m * 16 + z)))) {
            return false;
        }
        
        if ((z < 15 && !etc.getInstance().isOpaqueAntiXRayBlock(this.b[x << 11 | (z + 1) << 7 | y])) || (z == 15 && this.f.A.a(l, m + 1) && !etc.getInstance().isOpaqueAntiXRayBlock(this.f.a(l * 16 + x, y, m * 16 + z + 1)))) {
            return false;
        }
        
        if ((z > 0 && !etc.getInstance().isOpaqueAntiXRayBlock(this.b[x << 11 | (z - 1) << 7 | y])) || (z == 0 && this.f.A.a(l, m - 1) && !etc.getInstance().isOpaqueAntiXRayBlock(this.f.a(l * 16 + x, y, m * 16 + z - 1)))) {
            return false;
        }
        
        return true;
    }
    
    // CanaryMod function: Used to check for opaque blocks around a single block safely.
    public boolean isLit(int x, int y, int z) {
        return isLit(x, y, z, 0);
    }
    
 // CanaryMod function: Used to check for opaque blocks around a single block safely.
    public boolean isLit(int x, int y, int z, int lightValue) {
        int absoluteX = l * 16 + x;
        int absoluteZ = m * 16 + z;

        // Blocks at the edges of the chunk are considered hidden by the nonexistant block.
        if (y == 127 || (y < 127 && this.f.a(absoluteX, y + 1, absoluteZ, false) > lightValue)) {
            return true;
        }
        
        if (y == 0 || (y > 0 && this.f.a(absoluteX, y - 1, absoluteZ, false) > lightValue)) {
            return true;
        }
        
        if ((x < 15 && this.f.a(absoluteX + 1, y, absoluteZ, false) > lightValue) || (x == 15 && this.f.A.a(l + 1, m) && this.f.a(absoluteX + 1, y, absoluteZ, false) > lightValue)) {
            return true;
        }
        
        if ((x > 0 && this.f.a(absoluteX - 1, y, absoluteZ, false) > lightValue) || (x == 0 && this.f.A.a(l - 1, m) && this.f.a(absoluteX - 1, y, absoluteZ, false) > lightValue)) {
            return true;
        }
        
        if ((z < 15 && this.f.a(absoluteX, y, absoluteZ + 1, false) > lightValue) || (z == 15 && this.f.A.a(l, m + 1) && this.f.a(absoluteX, y, absoluteZ + 1, false) > lightValue)) {
            return true;
        }
        
        if ((z > 0 && this.f.a(absoluteX, y, absoluteZ - 1, false) > lightValue) || (z == 0 && this.f.A.a(l, m - 1) && this.f.a(absoluteX, y, absoluteZ - 1, false) > lightValue)) {
            return true;
        }
        return false;
    }
    
    // CanaryMod function: Used to update chunk borders when neighbor chunks are generated.
    public void updateChunkBorder(int borderX, int borderZ, boolean xAlignedBorder) {
        for (int i1 = 0; i1 < 16; i1 += 1) {
            Integer index = (xAlignedBorder == true) ? (i1 << 11 | borderZ) : (borderX << 11 | i1);

            for (int j1 = 0; j1 < 128; j1 += 1) {
                if (this.antiXRayBlocks.containsKey(index)) {
                    // Queue update for block.
                    this.f.h(this.l * 16 + ((index >> 11) & 0xf), j1, this.m * 16 + ((index >> 7) & 0xf));
                }
                index += 1;
            }
        }
    }
    
    // CanaryMod function: Used to update all the neighboring chunks.
    public void updateNeighborChunks() {
        if (this.f.A.a(l + 1, m)) {
            this.f.c(l + 1, m).updateChunkBorder(0, 0, false);
        }
        if (this.f.A.a(l - 1, m)) {
            this.f.c(l - 1, m).updateChunkBorder(15, 0, false);
        }
        if (this.f.A.a(l, m + 1)) {
            this.f.c(l, m + 1).updateChunkBorder(0, 0, true);
        }
        if (this.f.A.a(l, m - 1)) {
            this.f.c(l, m - 1).updateChunkBorder(0, 15, true);
        }
    }
    
    // CanaryMod function: Used to update neighboring anti xray blocks.
    public void updateNeighborAntiXRayBlocks(int x, int y, int z) {
        updateAntiXRayBlock(l * 16 + x, y, m * 16 + z, x + 1, y, z);
        updateAntiXRayBlock(l * 16 + x, y, m * 16 + z, x - 1, y, z);
        updateAntiXRayBlock(l * 16 + x, y, m * 16 + z, x, y + 1, z);
        updateAntiXRayBlock(l * 16 + x, y, m * 16 + z, x, y - 1, z);
        updateAntiXRayBlock(l * 16 + x, y, m * 16 + z, x, y, z + 1);
        updateAntiXRayBlock(l * 16 + x, y, m * 16 + z, x, y, z - 1);
    }
    
    // CanaryMod function: Used to update single xray blocks.
    public void updateAntiXRayBlock(int updaterX, int updaterY, int updaterZ, int x, int y, int z) {
        // Check for invalid heights
        if (y < 0 || y > 127) {
            return;
        }
        
        // If updated block is inside the chunk
        if (x >= 0 && x <= 15 && z >= 0 && z <= 15) {
            Integer index = x << 11 | z << 7 | y;

            if (this.antiXRayBlocks.containsKey(index)) {
                // Queue update for block.
                this.f.h(this.l * 16 + x, y, this.m * 16 + z);
            }
        } else {
            int chunkXOffset = 0, chunkZOffset = 0, xPos = x & 0xf, zPos = z & 0xf;

            if (x > 15) {
                chunkXOffset = x / 16;
                xPos = x & 0xf;
            } else if (x < 0) {
                chunkXOffset = x / 16 - 1;
                xPos = (16 - (-x & 0xf)) & 0xf;
            }
            if (z > 15) {
                chunkZOffset = z / 16;
                zPos = z & 0xf;
            } else if (z < 0) {
                chunkZOffset = z / 16 - 1;
                zPos = (16 - (-z & 0xf)) & 0xf;
            }
            
            // If the neighbor chunk exists
            if (this.f.A.a(l + chunkXOffset, m + chunkZOffset)) {
                // Update the anti xray block in the other chunk.
                Integer index = xPos << 11 | zPos << 7 | y;

                synchronized (this.antiXRayBlocksLock) {
                    if (this.f.c(l + chunkXOffset, m + chunkZOffset).antiXRayBlocks.containsKey(index)) {
                        // Queue update for block.
                        this.f.h((this.l + chunkXOffset) * 16 + xPos, y, (this.m + chunkZOffset) * 16 + zPos);
                    }
                }
            }
        }
    }
}

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;


public class OWorld implements OIBlockAccess {

    public boolean a = false;
    public List b = new ArrayList();
    private List G = new ArrayList();
    private TreeSet H = new TreeSet();
    private Set I = new HashSet();
    public List c = new ArrayList();
    private List J = new ArrayList();
    private List K = new ArrayList();
    public List d = new ArrayList();
    public List e = new ArrayList();
    private long L = 16777215L;
    public int f = 0;
    protected int g = (new Random()).nextInt();
    protected final int h = 1013904223;
    protected float i;
    protected float j;
    protected float k;
    protected float l;
    protected int m = 0;
    public int n = 0;
    public boolean o = false;
    private long M = System.currentTimeMillis();
    protected int p = 40;
    public int q;
    public Random r = new Random();
    public boolean s = false;
    public final OWorldProvider t;
    protected List u = new ArrayList();
    protected OIChunkProvider v;
    protected final OISaveHandler w;
    protected OWorldInfo x;
    public boolean y;
    private boolean N;
    public OMapStorage z;
    public final OVillageCollection A = new OVillageCollection(this);
    private final OVillageSiege O = new OVillageSiege(this);
    private ArrayList P = new ArrayList();
    private boolean Q;
    protected boolean B = true;
    protected boolean C = true;
    protected Set D = new HashSet();
    private int R;
    int[] E;
    private List S;
    public boolean F;
    
    // CanaryMod
    public final World world = new World((OWorldServer) this);
    boolean loadedpreload = false;

    public OBiomeGenBase a(int var1, int var2) {
        if (this.i(var1, 0, var2)) {
            OChunk var3 = this.c(var1, var2);

            if (var3 != null) {
                return var3.a(var1 & 15, var2 & 15, this.t.c);
            }
        }

        return this.t.c.a(var1, var2);
    }

    public OWorldChunkManager a() {
        return this.t.c;
    }

    public OWorld(OISaveHandler var1, String var2, OWorldSettings var3, OWorldProvider var4) {
        super();
        this.R = this.r.nextInt(12000);
        this.E = new int['\u8000'];
        this.S = new ArrayList();
        this.F = false;
        this.w = var1;
        this.z = new OMapStorage(var1);
        this.x = var1.c();
        this.s = this.x == null;
        if (var4 != null) {
            this.t = var4;
        } else if (this.x != null && this.x.g() != 0) {
            this.t = OWorldProvider.a(this.x.g());
        } else {
            this.t = OWorldProvider.a(0);
        }

        boolean var5 = false;

        if (this.x == null) {
            this.x = new OWorldInfo(var3, var2);
            var5 = true;
        } else {
            this.x.a(var2);
        }

        this.t.a(this);
        this.v = this.b();
        if (var5) {
            this.c();
        }

        this.g();
        this.B();
    }

    protected OIChunkProvider b() {
        OIChunkLoader var1 = this.w.a(this.t);

        return new OChunkProvider(this, var1, this.t.b());
    }

    protected void c() {
        // CanaryMod: load preload plugins once!
        if (!loadedpreload) {
            etc.getLoader().loadPreloadPlugins();
            loadedpreload = true;
        }
        // CanaryMod onSpawnpointCreate hook
        Location point = (Location) etc.getLoader().callHook(PluginLoader.Hook.SPAWNPOINT_CREATE, world);
    
        if (!this.t.c()) {
            this.x.a(0, this.t.f(), 0);
        } else {
            this.y = true;
            OWorldChunkManager var1 = this.t.c;
            List var2 = var1.a();
            Random var3 = new Random(this.n());
            OChunkPosition var4 = var1.a(0, 0, 256, var2, var3);
            int var5 = 0;
            int var6 = this.t.f();
            int var7 = 0;

            if (var4 != null) {
                var5 = var4.a;
                var7 = var4.c;
            } else {
                System.out.println("Unable to find spawn biome");
            }

            int var8 = 0;

            while (!this.t.a(var5, var7)) {
                var5 += var3.nextInt(64) - var3.nextInt(64);
                var7 += var3.nextInt(64) - var3.nextInt(64);
                ++var8;
                if (var8 == 1000) {
                    break;
                }
            }

            this.x.a(var5, var6, var7);
            this.y = false;
        }
    }

    public OChunkCoordinates d() {
        return this.t.e();
    }

    public int b(int var1, int var2) {
        int var3;

        for (var3 = 63; !this.g(var1, var3 + 1, var2); ++var3) {
            ;
        }

        return this.a(var1, var3, var2);
    }

    public void a(boolean var1, OIProgressUpdate var2) {
        if (this.v.b()) {
            if (var2 != null) {
                var2.a("Saving level");
            }

            this.A();
            if (var2 != null) {
                var2.b("Saving chunks");
            }

            this.v.a(var1, var2);
        }
    }

    private void A() {
        this.m();
        this.w.a(this.x, this.d);
        this.z.a();
    }

    public int a(int var1, int var2, int var3) {
        return var1 >= -30000000 && var3 >= -30000000 && var1 < 30000000 && var3 < 30000000 ? (var2 < 0 ? 0 : (var2 >= 256 ? 0 : this.d(var1 >> 4, var3 >> 4).a(var1 & 15, var2, var3 & 15))) : 0;
    }

    public int f(int var1, int var2, int var3) {
        return var1 >= -30000000 && var3 >= -30000000 && var1 < 30000000 && var3 < 30000000 ? (var2 < 0 ? 0 : (var2 >= 256 ? 0 : this.d(var1 >> 4, var3 >> 4).b(var1 & 15, var2, var3 & 15))) : 0;
    }

    public boolean g(int var1, int var2, int var3) {
        return this.a(var1, var2, var3) == 0;
    }

    public boolean h(int var1, int var2, int var3) {
        int var4 = this.a(var1, var2, var3);

        return OBlock.m[var4] != null && OBlock.m[var4].o();
    }

    public boolean i(int var1, int var2, int var3) {
        return var2 >= 0 && var2 < 256 ? this.h(var1 >> 4, var3 >> 4) : false;
    }

    public boolean a(int var1, int var2, int var3, int var4) {
        return this.a(var1 - var4, var2 - var4, var3 - var4, var1 + var4, var2 + var4, var3 + var4);
    }

    public boolean a(int var1, int var2, int var3, int var4, int var5, int var6) {
        if (var5 >= 0 && var2 < 256) {
            var1 >>= 4;
            var3 >>= 4;
            var4 >>= 4;
            var6 >>= 4;

            for (int var7 = var1; var7 <= var4; ++var7) {
                for (int var8 = var3; var8 <= var6; ++var8) {
                    if (!this.h(var7, var8)) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    private boolean h(int var1, int var2) {
        return this.v.a(var1, var2);
    }

    public OChunk c(int var1, int var2) {
        return this.d(var1 >> 4, var2 >> 4);
    }

    public OChunk d(int var1, int var2) {
        return this.v.b(var1, var2);
    }

    public boolean a(int var1, int var2, int var3, int var4, int var5) {
        // CanaryMod: Get Block Info
        Block block = this.world.getBlockAt(var1, var2, var3);
        
        if (var1 >= -30000000 && var3 >= -30000000 && var1 < 30000000 && var3 < 30000000) {
            if (var2 < 0) {
                return false;
            } else if (var2 >= 256) {
                return false;
            } else {
                boolean var7 = false;
                OChunk var6 = this.d(var1 >> 4, var3 >> 4);
                //CanaryMod ignore if new block is air
                if(var4 == 0){
                    var7 = var6.a(var1 & 15, var2, var3 & 15, var4, var5);
                } else if(!(Boolean)OEntity.manager.callHook(PluginLoader.Hook.BLOCK_UPDATE, block, var4)){
                    var7 = var6.a(var1 & 15, var2, var3 & 15, var4, var5);
                }
                OProfiler.a("checkLight");
                this.v(var1, var2, var3);
                OProfiler.a();
                return var7;
            }
        } else {
            return false;
        }
    }

    public boolean b(int var1, int var2, int var3, int var4) {
        if (var1 >= -30000000 && var3 >= -30000000 && var1 < 30000000 && var3 < 30000000) {
            if (var2 < 0) {
                return false;
            } else if (var2 >= 256) {
                return false;
            } else {
                OChunk var5 = this.d(var1 >> 4, var3 >> 4);
                boolean var6 = var5.a(var1 & 15, var2, var3 & 15, var4);

                OProfiler.a("checkLight");
                this.v(var1, var2, var3);
                OProfiler.a();
                return var6;
            }
        } else {
            return false;
        }
    }

    public OMaterial d(int var1, int var2, int var3) {
        int var4 = this.a(var1, var2, var3);

        return var4 == 0 ? OMaterial.a : OBlock.m[var4].cd;
    }

    public int c(int var1, int var2, int var3) {
        if (var1 >= -30000000 && var3 >= -30000000 && var1 < 30000000 && var3 < 30000000) {
            if (var2 < 0) {
                return 0;
            } else if (var2 >= 256) {
                return 0;
            } else {
                OChunk var4 = this.d(var1 >> 4, var3 >> 4);

                var1 &= 15;
                var3 &= 15;
                return var4.c(var1, var2, var3);
            }
        } else {
            return 0;
        }
    }

    public void c(int var1, int var2, int var3, int var4) {
        if (this.d(var1, var2, var3, var4)) {
            int var5 = this.a(var1, var2, var3);

            if (OBlock.r[var5 & 4095]) {
                this.f(var1, var2, var3, var5);
            } else {
                this.h(var1, var2, var3, var5);
            }
        }

    }

    public boolean d(int var1, int var2, int var3, int var4) {
        if (var1 >= -30000000 && var3 >= -30000000 && var1 < 30000000 && var3 < 30000000) {
            if (var2 < 0) {
                return false;
            } else if (var2 >= 256) {
                return false;
            } else {
                OChunk var5 = this.d(var1 >> 4, var3 >> 4);

                var1 &= 15;
                var3 &= 15;
                return var5.b(var1, var2, var3, var4);
            }
        } else {
            return false;
        }
    }

    public boolean e(int var1, int var2, int var3, int var4) {
        if (this.b(var1, var2, var3, var4)) {
            this.f(var1, var2, var3, var4);
            return true;
        } else {
            return false;
        }
    }

    public boolean b(int var1, int var2, int var3, int var4, int var5) {
        if (this.a(var1, var2, var3, var4, var5)) {
            this.f(var1, var2, var3, var4);
            return true;
        } else {
            return false;
        }
    }

    public void j(int var1, int var2, int var3) {
        for (int var4 = 0; var4 < this.u.size(); ++var4) {
            ((OIWorldAccess) this.u.get(var4)).a(var1, var2, var3);
        }

    }

    public void f(int var1, int var2, int var3, int var4) {
        this.j(var1, var2, var3);
        this.h(var1, var2, var3, var4);
    }

    public void g(int var1, int var2, int var3, int var4) {
        int var5;

        if (var3 > var4) {
            var5 = var4;
            var4 = var3;
            var3 = var5;
        }

        if (!this.t.e) {
            for (var5 = var3; var5 <= var4; ++var5) {
                this.b(OEnumSkyBlock.a, var1, var5, var2);
            }
        }

        this.b(var1, var3, var2, var1, var4, var2);
    }

    public void k(int var1, int var2, int var3) {
        for (int var4 = 0; var4 < this.u.size(); ++var4) {
            ((OIWorldAccess) this.u.get(var4)).a(var1, var2, var3, var1, var2, var3);
        }

    }

    public void b(int var1, int var2, int var3, int var4, int var5, int var6) {
        for (int var7 = 0; var7 < this.u.size(); ++var7) {
            ((OIWorldAccess) this.u.get(var7)).a(var1, var2, var3, var4, var5, var6);
        }

    }

    public void h(int var1, int var2, int var3, int var4) {
        this.k(var1 - 1, var2, var3, var4);
        this.k(var1 + 1, var2, var3, var4);
        this.k(var1, var2 - 1, var3, var4);
        this.k(var1, var2 + 1, var3, var4);
        this.k(var1, var2, var3 - 1, var4);
        this.k(var1, var2, var3 + 1, var4);
    }

    private void k(int var1, int var2, int var3, int var4) {
        if (!this.o && !this.F) {
            OBlock var5 = OBlock.m[this.a(var1, var2, var3)];

            if (var5 != null) {
                var5.a(this, var1, var2, var3, var4);
            }

        }
    }

    public boolean l(int var1, int var2, int var3) {
        return this.d(var1 >> 4, var3 >> 4).d(var1 & 15, var2, var3 & 15);
    }

    public int m(int var1, int var2, int var3) {
        if (var2 < 0) {
            return 0;
        } else {
            if (var2 >= 256) {
                var2 = 255;
            }

            return this.d(var1 >> 4, var3 >> 4).c(var1 & 15, var2, var3 & 15, 0);
        }
    }

    public int n(int var1, int var2, int var3) {
        return this.a(var1, var2, var3, true);
    }

    public int a(int var1, int var2, int var3, boolean var4) {
        if (var1 >= -30000000 && var3 >= -30000000 && var1 < 30000000 && var3 < 30000000) {
            if (var4) {
                int var5 = this.a(var1, var2, var3);

                if (var5 == OBlock.ak.bO || var5 == OBlock.aA.bO || var5 == OBlock.aH.bO || var5 == OBlock.at.bO) {
                    int var6 = this.a(var1, var2 + 1, var3, false);
                    int var7 = this.a(var1 + 1, var2, var3, false);
                    int var8 = this.a(var1 - 1, var2, var3, false);
                    int var9 = this.a(var1, var2, var3 + 1, false);
                    int var10 = this.a(var1, var2, var3 - 1, false);

                    if (var7 > var6) {
                        var6 = var7;
                    }

                    if (var8 > var6) {
                        var6 = var8;
                    }

                    if (var9 > var6) {
                        var6 = var9;
                    }

                    if (var10 > var6) {
                        var6 = var10;
                    }

                    return var6;
                }
            }

            if (var2 < 0) {
                return 0;
            } else {
                if (var2 >= 256) {
                    var2 = 255;
                }

                OChunk var11 = this.d(var1 >> 4, var3 >> 4);

                var1 &= 15;
                var3 &= 15;
                return var11.c(var1, var2, var3, this.f);
            }
        } else {
            return 15;
        }
    }

    public int e(int var1, int var2) {
        if (var1 >= -30000000 && var2 >= -30000000 && var1 < 30000000 && var2 < 30000000) {
            if (!this.h(var1 >> 4, var2 >> 4)) {
                return 0;
            } else {
                OChunk var3 = this.d(var1 >> 4, var2 >> 4);

                return var3.b(var1 & 15, var2 & 15);
            }
        } else {
            return 0;
        }
    }

    public int a(OEnumSkyBlock var1, int var2, int var3, int var4) {
        if (var3 < 0) {
            var3 = 0;
        }

        if (var3 >= 256) {
            var3 = 255;
        }

        if (var2 >= -30000000 && var4 >= -30000000 && var2 < 30000000 && var4 < 30000000) {
            int var5 = var2 >> 4;
            int var6 = var4 >> 4;

            if (!this.h(var5, var6)) {
                return var1.c;
            } else {
                OChunk var7 = this.d(var5, var6);

                return var7.a(var1, var2 & 15, var3, var4 & 15);
            }
        } else {
            return var1.c;
        }
    }

    public void a(OEnumSkyBlock var1, int var2, int var3, int var4, int var5) {
        if (var2 >= -30000000 && var4 >= -30000000 && var2 < 30000000 && var4 < 30000000) {
            if (var3 >= 0) {
                if (var3 < 256) {
                    if (this.h(var2 >> 4, var4 >> 4)) {
                        OChunk var6 = this.d(var2 >> 4, var4 >> 4);

                        var6.a(var1, var2 & 15, var3, var4 & 15, var5);

                        for (int var7 = 0; var7 < this.u.size(); ++var7) {
                            ((OIWorldAccess) this.u.get(var7)).b(var2, var3, var4);
                        }

                    }
                }
            }
        }
    }

    public void o(int var1, int var2, int var3) {
        for (int var4 = 0; var4 < this.u.size(); ++var4) {
            ((OIWorldAccess) this.u.get(var4)).b(var1, var2, var3);
        }

    }

    public float p(int var1, int var2, int var3) {
        return this.t.f[this.n(var1, var2, var3)];
    }

    public boolean e() {
        return this.f < 4;
    }

    public OMovingObjectPosition a(OVec3D var1, OVec3D var2) {
        return this.a(var1, var2, false, false);
    }

    public OMovingObjectPosition a(OVec3D var1, OVec3D var2, boolean var3) {
        return this.a(var1, var2, var3, false);
    }

    public OMovingObjectPosition a(OVec3D var1, OVec3D var2, boolean var3, boolean var4) {
        if (!Double.isNaN(var1.a) && !Double.isNaN(var1.b) && !Double.isNaN(var1.c)) {
            if (!Double.isNaN(var2.a) && !Double.isNaN(var2.b) && !Double.isNaN(var2.c)) {
                int var5 = OMathHelper.b(var2.a);
                int var6 = OMathHelper.b(var2.b);
                int var7 = OMathHelper.b(var2.c);
                int var8 = OMathHelper.b(var1.a);
                int var9 = OMathHelper.b(var1.b);
                int var10 = OMathHelper.b(var1.c);
                int var11 = this.a(var8, var9, var10);
                int var12 = this.c(var8, var9, var10);
                OBlock var13 = OBlock.m[var11];

                if ((!var4 || var13 == null || var13.e(this, var8, var9, var10) != null) && var11 > 0 && var13.a(var12, var3)) {
                    OMovingObjectPosition var14 = var13.a(this, var8, var9, var10, var1, var2);

                    if (var14 != null) {
                        return var14;
                    }
                }

                var11 = 200;

                while (var11-- >= 0) {
                    if (Double.isNaN(var1.a) || Double.isNaN(var1.b) || Double.isNaN(var1.c)) {
                        return null;
                    }

                    if (var8 == var5 && var9 == var6 && var10 == var7) {
                        return null;
                    }

                    boolean var39 = true;
                    boolean var40 = true;
                    boolean var41 = true;
                    double var15 = 999.0D;
                    double var17 = 999.0D;
                    double var19 = 999.0D;

                    if (var5 > var8) {
                        var15 = (double) var8 + 1.0D;
                    } else if (var5 < var8) {
                        var15 = (double) var8 + 0.0D;
                    } else {
                        var39 = false;
                    }

                    if (var6 > var9) {
                        var17 = (double) var9 + 1.0D;
                    } else if (var6 < var9) {
                        var17 = (double) var9 + 0.0D;
                    } else {
                        var40 = false;
                    }

                    if (var7 > var10) {
                        var19 = (double) var10 + 1.0D;
                    } else if (var7 < var10) {
                        var19 = (double) var10 + 0.0D;
                    } else {
                        var41 = false;
                    }

                    double var21 = 999.0D;
                    double var23 = 999.0D;
                    double var25 = 999.0D;
                    double var27 = var2.a - var1.a;
                    double var29 = var2.b - var1.b;
                    double var31 = var2.c - var1.c;

                    if (var39) {
                        var21 = (var15 - var1.a) / var27;
                    }

                    if (var40) {
                        var23 = (var17 - var1.b) / var29;
                    }

                    if (var41) {
                        var25 = (var19 - var1.c) / var31;
                    }

                    boolean var33 = false;
                    byte var42;

                    if (var21 < var23 && var21 < var25) {
                        if (var5 > var8) {
                            var42 = 4;
                        } else {
                            var42 = 5;
                        }

                        var1.a = var15;
                        var1.b += var29 * var21;
                        var1.c += var31 * var21;
                    } else if (var23 < var25) {
                        if (var6 > var9) {
                            var42 = 0;
                        } else {
                            var42 = 1;
                        }

                        var1.a += var27 * var23;
                        var1.b = var17;
                        var1.c += var31 * var23;
                    } else {
                        if (var7 > var10) {
                            var42 = 2;
                        } else {
                            var42 = 3;
                        }

                        var1.a += var27 * var25;
                        var1.b += var29 * var25;
                        var1.c = var19;
                    }

                    OVec3D var34 = OVec3D.b(var1.a, var1.b, var1.c);

                    var8 = (int) (var34.a = (double) OMathHelper.b(var1.a));
                    if (var42 == 5) {
                        --var8;
                        ++var34.a;
                    }

                    var9 = (int) (var34.b = (double) OMathHelper.b(var1.b));
                    if (var42 == 1) {
                        --var9;
                        ++var34.b;
                    }

                    var10 = (int) (var34.c = (double) OMathHelper.b(var1.c));
                    if (var42 == 3) {
                        --var10;
                        ++var34.c;
                    }

                    int var35 = this.a(var8, var9, var10);
                    int var36 = this.c(var8, var9, var10);
                    OBlock var37 = OBlock.m[var35];

                    if ((!var4 || var37 == null || var37.e(this, var8, var9, var10) != null) && var35 > 0 && var37.a(var36, var3)) {
                        OMovingObjectPosition var38 = var37.a(this, var8, var9, var10, var1, var2);

                        if (var38 != null) {
                            return var38;
                        }
                    }
                }

                return null;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void a(OEntity var1, String var2, float var3, float var4) {
        for (int var5 = 0; var5 < this.u.size(); ++var5) {
            ((OIWorldAccess) this.u.get(var5)).a(var2, var1.bm, var1.bn - (double) var1.bF, var1.bo, var3, var4);
        }

    }

    public void a(double var1, double var3, double var5, String var7, float var8, float var9) {
        for (int var10 = 0; var10 < this.u.size(); ++var10) {
            ((OIWorldAccess) this.u.get(var10)).a(var7, var1, var3, var5, var8, var9);
        }

    }

    public void a(String var1, int var2, int var3, int var4) {
        for (int var5 = 0; var5 < this.u.size(); ++var5) {
            ((OIWorldAccess) this.u.get(var5)).a(var1, var2, var3, var4);
        }

    }

    public void a(String var1, double var2, double var4, double var6, double var8, double var10, double var12) {
        for (int var14 = 0; var14 < this.u.size(); ++var14) {
            ((OIWorldAccess) this.u.get(var14)).a(var1, var2, var4, var6, var8, var10, var12);
        }

    }

    public boolean a(OEntity var1) {
        this.e.add(var1);
        return true;
    }

    public boolean b(OEntity var1) {
        // CanaryMod: mob spawn hook
        if (var1 instanceof OEntityLiving && !(var1 instanceof OEntityPlayer)) {
            if ((etc.getInstance().getMobSpawnRate() < 100 && etc.getInstance().getMobSpawnRate() > 0 && etc.getInstance().getMobSpawnRate() <= r.nextInt(101)) || etc.getInstance().getMobSpawnRate() <= 0 || (Boolean) (etc.getLoader().callHook(PluginLoader.Hook.MOB_SPAWN, new Mob((OEntityLiving) var1)))) {
                return false;
            }
        }
        
        int var2 = OMathHelper.b(var1.bm / 16.0D);
        int var3 = OMathHelper.b(var1.bo / 16.0D);
        boolean var4 = false;

        if (var1 instanceof OEntityPlayer) {
            var4 = true;
        }

        if (!var4 && !this.h(var2, var3)) {
            return false;
        } else {
            if (var1 instanceof OEntityPlayer) {
                OEntityPlayer var5 = (OEntityPlayer) var1;

                this.d.add(var5);
                this.t();
            }

            this.d(var2, var3).a(var1);
            this.b.add(var1);
            this.c(var1);
            return true;
        }
    }

    protected void c(OEntity var1) {
        for (int var2 = 0; var2 < this.u.size(); ++var2) {
            ((OIWorldAccess) this.u.get(var2)).a(var1);
        }

    }

    protected void d(OEntity var1) {
        for (int var2 = 0; var2 < this.u.size(); ++var2) {
            ((OIWorldAccess) this.u.get(var2)).b(var1);
        }

    }

    public void e(OEntity var1) {
        if (var1.bg != null) {
            var1.bg.b((OEntity) null);
        }

        if (var1.bh != null) {
            var1.b((OEntity) null);
        }

        var1.X();
        if (var1 instanceof OEntityPlayer) {
            this.d.remove((OEntityPlayer) var1);
            this.t();
        }

    }

    public void f(OEntity var1) {
        var1.X();
        if (var1 instanceof OEntityPlayer) {
            this.d.remove((OEntityPlayer) var1);
            this.t();
        }

        int var2 = var1.ca;
        int var3 = var1.cc;

        if (var1.bZ && this.h(var2, var3)) {
            this.d(var2, var3).b(var1);
        }

        this.b.remove(var1);
        this.d(var1);
    }

    public void a(OIWorldAccess var1) {
        this.u.add(var1);
    }

    public List a(OEntity var1, OAxisAlignedBB var2) {
        this.P.clear();
        int var3 = OMathHelper.b(var2.a);
        int var4 = OMathHelper.b(var2.d + 1.0D);
        int var5 = OMathHelper.b(var2.b);
        int var6 = OMathHelper.b(var2.e + 1.0D);
        int var7 = OMathHelper.b(var2.c);
        int var8 = OMathHelper.b(var2.f + 1.0D);

        for (int var9 = var3; var9 < var4; ++var9) {
            for (int var10 = var7; var10 < var8; ++var10) {
                if (this.i(var9, 64, var10)) {
                    for (int var11 = var5 - 1; var11 < var6; ++var11) {
                        OBlock var12 = OBlock.m[this.a(var9, var11, var10)];

                        if (var12 != null) {
                            var12.a(this, var9, var11, var10, var2, this.P);
                        }
                    }
                }
            }
        }

        double var13 = 0.25D;
        List var17 = this.b(var1, var2.b(var13, var13, var13));

        // CanaryMod: Implemented fix via M4411K4 VEHICLE_COLLISION hook
        Minecart minecart = null;
        if(var1 instanceof OEntityMinecart)
        {
            minecart = ((OEntityMinecart)var1).cart;
        }
        
        for (int var16 = 0; var16 < var17.size(); ++var16) {
            OEntity oentity = (OEntity)var17.get(var16);
            
            OAxisAlignedBB var15 = oentity.h();

            if (var15 != null && var15.a(var2)) {

                //this collided with a boat
                if(minecart != null && (Boolean) etc.getLoader().callHook(PluginLoader.Hook.VEHICLE_COLLISION, minecart, oentity.entity)) //Canary
                      continue; //Canary
                
                this.P.add(var15);
            }

            //appears to only be for boats and minecarts currently. Everything else returns null
            var15 = var1.b_(oentity); //originally: var15 = var1.b_((OEntity) var17.get(var16));
            if (var15 != null && var15.a(var2)) {

                //this collided with entity
                if(minecart != null && (Boolean) etc.getLoader().callHook(PluginLoader.Hook.VEHICLE_COLLISION, minecart, oentity.entity)) //Canary
                    continue; //Canary
                
                this.P.add(var15);
            }
        }
        // CanaryMod: End
        
        return this.P;
    }

    public int a(float var1) {
        float var2 = this.b(var1);
        float var3 = 1.0F - (OMathHelper.b(var2 * 3.1415927F * 2.0F) * 2.0F + 0.5F);

        if (var3 < 0.0F) {
            var3 = 0.0F;
        }

        if (var3 > 1.0F) {
            var3 = 1.0F;
        }

        var3 = 1.0F - var3;
        var3 = (float) ((double) var3 * (1.0D - (double) (this.d(var1) * 5.0F) / 16.0D));
        var3 = (float) ((double) var3 * (1.0D - (double) (this.c(var1) * 5.0F) / 16.0D));
        var3 = 1.0F - var3;
        return (int) (var3 * 11.0F);
    }

    public float b(float var1) {
        return this.t.a(this.x.f(), var1);
    }

    public int f(int var1, int var2) {
        return this.c(var1, var2).d(var1 & 15, var2 & 15);
    }

    public int g(int var1, int var2) {
        OChunk var3 = this.c(var1, var2);
        int var4 = var3.g() + 16;

        var1 &= 15;

        for (var2 &= 15; var4 > 0; --var4) {
            int var5 = var3.a(var1, var4, var2);

            if (var5 != 0 && OBlock.m[var5].cd.c() && OBlock.m[var5].cd != OMaterial.i) {
                return var4 + 1;
            }
        }

        return -1;
    }

    public void c(int var1, int var2, int var3, int var4, int var5) {
        ONextTickListEntry var6 = new ONextTickListEntry(var1, var2, var3, var4);
        byte var7 = 8;

        if (this.a) {
            if (this.a(var6.a - var7, var6.b - var7, var6.c - var7, var6.a + var7, var6.b + var7, var6.c + var7)) {
                int var8 = this.a(var6.a, var6.b, var6.c);

                if (var8 == var6.d && var8 > 0) {
                    OBlock.m[var8].a(this, var6.a, var6.b, var6.c, this.r);
                }
            }

        } else {
            if (this.a(var1 - var7, var2 - var7, var3 - var7, var1 + var7, var2 + var7, var3 + var7)) {
                if (var4 > 0) {
                    var6.a((long) var5 + this.x.f());
                }

                if (!this.I.contains(var6)) {
                    this.I.add(var6);
                    this.H.add(var6);
                }
            }

        }
    }

    public void d(int var1, int var2, int var3, int var4, int var5) {
        ONextTickListEntry var6 = new ONextTickListEntry(var1, var2, var3, var4);

        if (var4 > 0) {
            var6.a((long) var5 + this.x.f());
        }

        if (!this.I.contains(var6)) {
            this.I.add(var6);
            this.H.add(var6);
        }

    }

    public void f() {
        OProfiler.a("entities");
        OProfiler.a("global");

        int var1;
        OEntity var2;

        for (var1 = 0; var1 < this.e.size(); ++var1) {
            var2 = (OEntity) this.e.get(var1);
            var2.F_();
            if (var2.bE) {
                this.e.remove(var1--);
            }
        }

        OProfiler.b("remove");
        this.b.removeAll(this.G);

        int var3;
        int var4;

        for (var1 = 0; var1 < this.G.size(); ++var1) {
            var2 = (OEntity) this.G.get(var1);
            var3 = var2.ca;
            var4 = var2.cc;
            if (var2.bZ && this.h(var3, var4)) {
                this.d(var3, var4).b(var2);
            }
        }

        for (var1 = 0; var1 < this.G.size(); ++var1) {
            this.d((OEntity) this.G.get(var1));
        }

        this.G.clear();
        OProfiler.b("regular");

        for (var1 = 0; var1 < this.b.size(); ++var1) {
            var2 = (OEntity) this.b.get(var1);
            if (var2.bh != null) {
                if (!var2.bh.bE && var2.bh.bg == var2) {
                    continue;
                }

                var2.bh.bg = null;
                var2.bh = null;
            }

            if (!var2.bE) {
                this.g(var2);
            }

            OProfiler.a("remove");
            if (var2.bE) {
                var3 = var2.ca;
                var4 = var2.cc;
                if (var2.bZ && this.h(var3, var4)) {
                    this.d(var3, var4).b(var2);
                }

                this.b.remove(var1--);
                this.d(var2);
            }

            OProfiler.a();
        }

        OProfiler.b("tileEntities");
        this.Q = true;
        Iterator var10 = this.c.iterator();

        while (var10.hasNext()) {
            OTileEntity var5 = (OTileEntity) var10.next();

            if (!var5.l() && var5.k != null && this.i(var5.l, var5.m, var5.n)) {
                var5.q_();
            }

            if (var5.l()) {
                var10.remove();
                if (this.h(var5.l >> 4, var5.n >> 4)) {
                    OChunk var7 = this.d(var5.l >> 4, var5.n >> 4);

                    if (var7 != null) {
                        var7.f(var5.l & 15, var5.m, var5.n & 15);
                    }
                }
            }
        }

        this.Q = false;
        if (!this.K.isEmpty()) {
            this.c.removeAll(this.K);
            this.K.clear();
        }

        OProfiler.b("pendingTileEntities");
        if (!this.J.isEmpty()) {
            Iterator var6 = this.J.iterator();

            while (var6.hasNext()) {
                OTileEntity var8 = (OTileEntity) var6.next();

                if (!var8.l()) {
                    if (!this.c.contains(var8)) {
                        this.c.add(var8);
                    }

                    if (this.h(var8.l >> 4, var8.n >> 4)) {
                        OChunk var9 = this.d(var8.l >> 4, var8.n >> 4);

                        if (var9 != null) {
                            var9.a(var8.l & 15, var8.m, var8.n & 15, var8);
                        }
                    }

                    this.j(var8.l, var8.m, var8.n);
                }
            }

            this.J.clear();
        }

        OProfiler.a();
        OProfiler.a();
    }

    public void a(Collection var1) {
        if (this.Q) {
            this.J.addAll(var1);
        } else {
            this.c.addAll(var1);
        }

    }

    public void g(OEntity var1) {
        this.a(var1, true);
    }

    public void a(OEntity var1, boolean var2) {
        int var3 = OMathHelper.b(var1.bm);
        int var4 = OMathHelper.b(var1.bo);
        byte var5 = 32;

        if (!var2 || this.a(var3 - var5, 0, var4 - var5, var3 + var5, 0, var4 + var5)) {
            var1.bL = var1.bm;
            var1.bM = var1.bn;
            var1.bN = var1.bo;
            var1.bu = var1.bs;
            var1.bv = var1.bt;
            if (var2 && var1.bZ) {
                if (var1.bh != null) {
                    var1.R();
                } else {
                    var1.F_();
                }
            }

            OProfiler.a("chunkCheck");
            if (Double.isNaN(var1.bm) || Double.isInfinite(var1.bm)) {
                var1.bm = var1.bL;
            }

            if (Double.isNaN(var1.bn) || Double.isInfinite(var1.bn)) {
                var1.bn = var1.bM;
            }

            if (Double.isNaN(var1.bo) || Double.isInfinite(var1.bo)) {
                var1.bo = var1.bN;
            }

            if (Double.isNaN((double) var1.bt) || Double.isInfinite((double) var1.bt)) {
                var1.bt = var1.bv;
            }

            if (Double.isNaN((double) var1.bs) || Double.isInfinite((double) var1.bs)) {
                var1.bs = var1.bu;
            }

            int var6 = OMathHelper.b(var1.bm / 16.0D);
            int var7 = OMathHelper.b(var1.bn / 16.0D);
            int var8 = OMathHelper.b(var1.bo / 16.0D);

            if (!var1.bZ || var1.ca != var6 || var1.cb != var7 || var1.cc != var8) {
                if (var1.bZ && this.h(var1.ca, var1.cc)) {
                    this.d(var1.ca, var1.cc).a(var1, var1.cb);
                }

                if (this.h(var6, var8)) {
                    var1.bZ = true;
                    this.d(var6, var8).a(var1);
                } else {
                    var1.bZ = false;
                }
            }

            OProfiler.a();
            if (var2 && var1.bZ && var1.bg != null) {
                if (!var1.bg.bE && var1.bg.bh == var1) {
                    this.g(var1.bg);
                } else {
                    var1.bg.bh = null;
                    var1.bg = null;
                }
            }

        }
    }

    public boolean a(OAxisAlignedBB var1) {
        List var2 = this.b((OEntity) null, var1);

        for (int var3 = 0; var3 < var2.size(); ++var3) {
            OEntity var4 = (OEntity) var2.get(var3);

            if (!var4.bE && var4.bf) {
                return false;
            }
        }

        return true;
    }

    public boolean b(OAxisAlignedBB var1) {
        int var2 = OMathHelper.b(var1.a);
        int var3 = OMathHelper.b(var1.d + 1.0D);
        int var4 = OMathHelper.b(var1.b);
        int var5 = OMathHelper.b(var1.e + 1.0D);
        int var6 = OMathHelper.b(var1.c);
        int var7 = OMathHelper.b(var1.f + 1.0D);

        if (var1.a < 0.0D) {
            --var2;
        }

        if (var1.b < 0.0D) {
            --var4;
        }

        if (var1.c < 0.0D) {
            --var6;
        }

        for (int var8 = var2; var8 < var3; ++var8) {
            for (int var9 = var4; var9 < var5; ++var9) {
                for (int var10 = var6; var10 < var7; ++var10) {
                    OBlock var11 = OBlock.m[this.a(var8, var9, var10)];

                    if (var11 != null) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean c(OAxisAlignedBB var1) {
        int var2 = OMathHelper.b(var1.a);
        int var3 = OMathHelper.b(var1.d + 1.0D);
        int var4 = OMathHelper.b(var1.b);
        int var5 = OMathHelper.b(var1.e + 1.0D);
        int var6 = OMathHelper.b(var1.c);
        int var7 = OMathHelper.b(var1.f + 1.0D);

        if (var1.a < 0.0D) {
            --var2;
        }

        if (var1.b < 0.0D) {
            --var4;
        }

        if (var1.c < 0.0D) {
            --var6;
        }

        for (int var8 = var2; var8 < var3; ++var8) {
            for (int var9 = var4; var9 < var5; ++var9) {
                for (int var10 = var6; var10 < var7; ++var10) {
                    OBlock var11 = OBlock.m[this.a(var8, var9, var10)];

                    if (var11 != null && var11.cd.d()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean d(OAxisAlignedBB var1) {
        int var2 = OMathHelper.b(var1.a);
        int var3 = OMathHelper.b(var1.d + 1.0D);
        int var4 = OMathHelper.b(var1.b);
        int var5 = OMathHelper.b(var1.e + 1.0D);
        int var6 = OMathHelper.b(var1.c);
        int var7 = OMathHelper.b(var1.f + 1.0D);

        if (this.a(var2, var4, var6, var3, var5, var7)) {
            for (int var8 = var2; var8 < var3; ++var8) {
                for (int var9 = var4; var9 < var5; ++var9) {
                    for (int var10 = var6; var10 < var7; ++var10) {
                        int var11 = this.a(var8, var9, var10);

                        if (var11 == OBlock.ar.bO || var11 == OBlock.C.bO || var11 == OBlock.D.bO) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean a(OAxisAlignedBB var1, OMaterial var2, OEntity var3) {
        int var4 = OMathHelper.b(var1.a);
        int var5 = OMathHelper.b(var1.d + 1.0D);
        int var6 = OMathHelper.b(var1.b);
        int var7 = OMathHelper.b(var1.e + 1.0D);
        int var8 = OMathHelper.b(var1.c);
        int var9 = OMathHelper.b(var1.f + 1.0D);

        if (!this.a(var4, var6, var8, var5, var7, var9)) {
            return false;
        } else {
            boolean var10 = false;
            OVec3D var11 = OVec3D.b(0.0D, 0.0D, 0.0D);

            for (int var12 = var4; var12 < var5; ++var12) {
                for (int var13 = var6; var13 < var7; ++var13) {
                    for (int var14 = var8; var14 < var9; ++var14) {
                        OBlock var15 = OBlock.m[this.a(var12, var13, var14)];

                        if (var15 != null && var15.cd == var2) {
                            double var16 = (double) ((float) (var13 + 1) - OBlockFluid.d(this.c(var12, var13, var14)));

                            if ((double) var7 >= var16) {
                                var10 = true;
                                var15.a(this, var12, var13, var14, var3, var11);
                            }
                        }
                    }
                }
            }

            if (var11.c() > 0.0D) {
                var11 = var11.b();
                double var18 = 0.014D;

                var3.bp += var11.a * var18;
                var3.bq += var11.b * var18;
                var3.br += var11.c * var18;
            }

            return var10;
        }
    }

    public boolean a(OAxisAlignedBB var1, OMaterial var2) {
        int var3 = OMathHelper.b(var1.a);
        int var4 = OMathHelper.b(var1.d + 1.0D);
        int var5 = OMathHelper.b(var1.b);
        int var6 = OMathHelper.b(var1.e + 1.0D);
        int var7 = OMathHelper.b(var1.c);
        int var8 = OMathHelper.b(var1.f + 1.0D);

        for (int var9 = var3; var9 < var4; ++var9) {
            for (int var10 = var5; var10 < var6; ++var10) {
                for (int var11 = var7; var11 < var8; ++var11) {
                    OBlock var12 = OBlock.m[this.a(var9, var10, var11)];

                    if (var12 != null && var12.cd == var2) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean b(OAxisAlignedBB var1, OMaterial var2) {
        int var3 = OMathHelper.b(var1.a);
        int var4 = OMathHelper.b(var1.d + 1.0D);
        int var5 = OMathHelper.b(var1.b);
        int var6 = OMathHelper.b(var1.e + 1.0D);
        int var7 = OMathHelper.b(var1.c);
        int var8 = OMathHelper.b(var1.f + 1.0D);

        for (int var9 = var3; var9 < var4; ++var9) {
            for (int var10 = var5; var10 < var6; ++var10) {
                for (int var11 = var7; var11 < var8; ++var11) {
                    OBlock var12 = OBlock.m[this.a(var9, var10, var11)];

                    if (var12 != null && var12.cd == var2) {
                        int var13 = this.c(var9, var10, var11);
                        double var14 = (double) (var10 + 1);

                        if (var13 < 8) {
                            var14 = (double) (var10 + 1) - (double) var13 / 8.0D;
                        }

                        if (var14 >= var1.b) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public OExplosion a(OEntity var1, double var2, double var4, double var6, float var8) {
        return this.a(var1, var2, var4, var6, var8, false);
    }

    public OExplosion a(OEntity var1, double var2, double var4, double var6, float var8, boolean var9) {
        OExplosion var10 = new OExplosion(this, var1, var2, var4, var6, var8);

        var10.a = var9;
        var10.a();
        var10.a(true);
        return var10;
    }

    public float a(OVec3D var1, OAxisAlignedBB var2) {
        double var3 = 1.0D / ((var2.d - var2.a) * 2.0D + 1.0D);
        double var5 = 1.0D / ((var2.e - var2.b) * 2.0D + 1.0D);
        double var7 = 1.0D / ((var2.f - var2.c) * 2.0D + 1.0D);
        int var9 = 0;
        int var10 = 0;

        for (float var11 = 0.0F; var11 <= 1.0F; var11 = (float) ((double) var11 + var3)) {
            for (float var12 = 0.0F; var12 <= 1.0F; var12 = (float) ((double) var12 + var5)) {
                for (float var13 = 0.0F; var13 <= 1.0F; var13 = (float) ((double) var13 + var7)) {
                    double var14 = var2.a + (var2.d - var2.a) * (double) var11;
                    double var16 = var2.b + (var2.e - var2.b) * (double) var12;
                    double var18 = var2.c + (var2.f - var2.c) * (double) var13;

                    if (this.a(OVec3D.b(var14, var16, var18), var1) == null) {
                        ++var9;
                    }

                    ++var10;
                }
            }
        }

        return (float) var9 / (float) var10;
    }

    public boolean a(OEntityPlayer var1, int var2, int var3, int var4, int var5) {
        if (var5 == 0) {
            --var3;
        }

        if (var5 == 1) {
            ++var3;
        }

        if (var5 == 2) {
            --var4;
        }

        if (var5 == 3) {
            ++var4;
        }

        if (var5 == 4) {
            --var2;
        }

        if (var5 == 5) {
            ++var2;
        }

        if (this.a(var2, var3, var4) == OBlock.ar.bO) {
            this.a(var1, 1004, var2, var3, var4, 0);
            this.e(var2, var3, var4, 0);
            return true;
        } else {
            return false;
        }
    }

    public OTileEntity b(int var1, int var2, int var3) {
        if (var2 >= 256) {
            return null;
        } else {
            OChunk var4 = this.d(var1 >> 4, var3 >> 4);

            if (var4 == null) {
                return null;
            } else {
                OTileEntity var5 = var4.e(var1 & 15, var2, var3 & 15);

                if (var5 == null) {
                    Iterator var6 = this.J.iterator();

                    while (var6.hasNext()) {
                        OTileEntity var7 = (OTileEntity) var6.next();

                        if (!var7.l() && var7.l == var1 && var7.m == var2 && var7.n == var3) {
                            var5 = var7;
                            break;
                        }
                    }
                }

                return var5;
            }
        }
    }

    public void a(int var1, int var2, int var3, OTileEntity var4) {
        if (var4 != null && !var4.l()) {
            if (this.Q) {
                var4.l = var1;
                var4.m = var2;
                var4.n = var3;
                this.J.add(var4);
            } else {
                this.c.add(var4);
                OChunk var5 = this.d(var1 >> 4, var3 >> 4);

                if (var5 != null) {
                    var5.a(var1 & 15, var2, var3 & 15, var4);
                }
            }
        }

    }

    public void q(int var1, int var2, int var3) {
        OTileEntity var4 = this.b(var1, var2, var3);

        if (var4 != null && this.Q) {
            var4.j();
            this.J.remove(var4);
        } else {
            if (var4 != null) {
                this.J.remove(var4);
                this.c.remove(var4);
            }

            OChunk var5 = this.d(var1 >> 4, var3 >> 4);

            if (var5 != null) {
                var5.f(var1 & 15, var2, var3 & 15);
            }
        }

    }

    public void a(OTileEntity var1) {
        this.K.add(var1);
    }

    public boolean r(int var1, int var2, int var3) {
        OBlock var4 = OBlock.m[this.a(var1, var2, var3)];

        return var4 == null ? false : var4.a();
    }

    public boolean e(int var1, int var2, int var3) {
        return OBlock.g(this.a(var1, var2, var3));
    }

    public boolean b(int var1, int var2, int var3, boolean var4) {
        if (var1 >= -30000000 && var3 >= -30000000 && var1 < 30000000 && var3 < 30000000) {
            OChunk var5 = this.v.b(var1 >> 4, var3 >> 4);

            if (var5 != null && !var5.f()) {
                OBlock var6 = OBlock.m[this.a(var1, var2, var3)];

                return var6 == null ? false : var6.cd.j() && var6.b();
            } else {
                return var4;
            }
        } else {
            return var4;
        }
    }

    public void g() {
        int var1 = this.a(1.0F);

        if (var1 != this.f) {
            this.f = var1;
        }

    }

    public void a(boolean var1, boolean var2) {
        this.B = var1;
        this.C = var2;
    }

    public void h() {
        if (this.s().o() && this.q < 3) {
            this.q = 3;
        }

        this.t.c.b();
        this.i();
        long var2;

        if (this.v()) {
            boolean var1 = false;

            if (this.B && this.q >= 1) {
                ;
            }

            if (!var1) {
                var2 = this.x.f() + 24000L;
                this.x.a(var2 - var2 % 24000L);
                this.u();
            }
        }

        OProfiler.a("mobSpawner");
        OSpawnerAnimals.a(this, this.B, this.C && this.x.f() % 400L == 0L);
        OProfiler.b("chunkSource");
        this.v.a();
        int var4 = this.a(1.0F);

        if (var4 != this.f) {
            this.f = var4;
        }

        var2 = this.x.f() + 1L;
        if (var2 % (long) this.p == 0L) {
            OProfiler.b("save");
            this.a(false, (OIProgressUpdate) null);
        }
        
        // CanaryMod: Time hook
        if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.TIME_CHANGE, world, var2)) {
            this.x.a(var2);
        }

        OProfiler.b("tickPending");
        this.a(false);
        OProfiler.b("tickTiles");
        this.l();
        OProfiler.b("village");
        this.A.a();
        this.O.a();
        OProfiler.a();
    }

    private void B() {
        if (this.x.k()) {
            this.j = 1.0F;
            if (this.x.i()) {
                this.l = 1.0F;
            }
        }

    }

    protected void i() {
        if (!this.t.e) {
            if (this.m > 0) {
                --this.m;
            }

            int var1 = this.x.j();

            if (var1 <= 0) {
                if (this.x.i()) {
                    this.x.b(this.r.nextInt(12000) + 3600);
                } else {
                    this.x.b(this.r.nextInt(168000) + 12000);
                }
            } else {
                --var1;
                this.x.b(var1);
                if (var1 <= 0) {
                    // CanaryMod: Thunder hook
                    if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.THUNDER_CHANGE, world, !this.x.i())) {
                        this.x.a(!this.x.i());
                    }
                }
            }

            int var2 = this.x.l();

            if (var2 <= 0) {
                if (this.x.k()) {
                    this.x.c(this.r.nextInt(12000) + 12000);
                } else {
                    this.x.c(this.r.nextInt(168000) + 12000);
                }
            } else {
                --var2;
                this.x.c(var2);
                if (var2 <= 0) {
                    // CanaryMod: Thunder hook
                    if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.WEATHER_CHANGE, world, !this.x.k())) {
                        this.x.b(!this.x.k());
                    }
                }
            }

            this.i = this.j;
            if (this.x.k()) {
                this.j = (float) ((double) this.j + 0.01D);
            } else {
                this.j = (float) ((double) this.j - 0.01D);
            }

            if (this.j < 0.0F) {
                this.j = 0.0F;
            }

            if (this.j > 1.0F) {
                this.j = 1.0F;
            }

            this.k = this.l;
            if (this.x.i()) {
                this.l = (float) ((double) this.l + 0.01D);
            } else {
                this.l = (float) ((double) this.l - 0.01D);
            }

            if (this.l < 0.0F) {
                this.l = 0.0F;
            }

            if (this.l > 1.0F) {
                this.l = 1.0F;
            }

        }
    }

    private void C() {
        // CanaryMod: Weather hook
        if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.WEATHER_CHANGE, world, !this.x.k())) {
            this.x.c(0);
            this.x.b(false);
        }
        if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.THUNDER_CHANGE, world, !this.x.i())) {
            this.x.b(0);
            this.x.a(false);
        }
    }

    public void j() {
        this.x.c(1);
    }

    protected void k() {
        this.D.clear();
        OProfiler.a("buildList");

        int var1;
        OEntityPlayer var2;
        int var3;
        int var4;

        for (var1 = 0; var1 < this.d.size(); ++var1) {
            var2 = (OEntityPlayer) this.d.get(var1);
            var3 = OMathHelper.b(var2.bm / 16.0D);
            var4 = OMathHelper.b(var2.bo / 16.0D);
            byte var5 = 7;

            for (int var6 = -var5; var6 <= var5; ++var6) {
                for (int var7 = -var5; var7 <= var5; ++var7) {
                    this.D.add(new OChunkCoordIntPair(var6 + var3, var7 + var4));
                }
            }
        }

        OProfiler.a();
        if (this.R > 0) {
            --this.R;
        }

        OProfiler.a("playerCheckLight");
        if (!this.d.isEmpty()) {
            var1 = this.r.nextInt(this.d.size());
            var2 = (OEntityPlayer) this.d.get(var1);
            var3 = OMathHelper.b(var2.bm) + this.r.nextInt(11) - 5;
            var4 = OMathHelper.b(var2.bn) + this.r.nextInt(11) - 5;
            int var8 = OMathHelper.b(var2.bo) + this.r.nextInt(11) - 5;

            this.v(var3, var4, var8);
        }

        OProfiler.a();
    }

    protected void a(int var1, int var2, OChunk var3) {
        OProfiler.b("tickChunk");
        var3.j();
        OProfiler.b("moodSound");
        if (this.R == 0) {
            this.g = this.g * 3 + 1013904223;
            int var4 = this.g >> 2;
            int var5 = var4 & 15;
            int var6 = var4 >> 8 & 15;
            int var7 = var4 >> 16 & 127;
            int var8 = var3.a(var5, var7, var6);

            var5 += var1;
            var6 += var2;
            if (var8 == 0 && this.m(var5, var7, var6) <= this.r.nextInt(8) && this.a(OEnumSkyBlock.a, var5, var7, var6) <= 0) {
                OEntityPlayer var9 = this.a((double) var5 + 0.5D, (double) var7 + 0.5D, (double) var6 + 0.5D, 8.0D);

                if (var9 != null && var9.e((double) var5 + 0.5D, (double) var7 + 0.5D, (double) var6 + 0.5D) > 4.0D) {
                    this.a((double) var5 + 0.5D, (double) var7 + 0.5D, (double) var6 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.r.nextFloat() * 0.2F);
                    this.R = this.r.nextInt(12000) + 6000;
                }
            }
        }

        OProfiler.b("checkLight");
        var3.n();
    }

    protected void l() {
        this.k();
        int var1 = 0;
        int var2 = 0;
        Iterator var3 = this.D.iterator();

        while (var3.hasNext()) {
            OChunkCoordIntPair var4 = (OChunkCoordIntPair) var3.next();
            int var5 = var4.a * 16;
            int var6 = var4.b * 16;

            OProfiler.a("getChunk");
            OChunk var7 = this.d(var4.a, var4.b);

            this.a(var5, var6, var7);
            OProfiler.b("thunder");
            int var8;
            int var9;
            int var10;
            int var11;

            if (this.r.nextInt(100000) == 0 && this.x() && this.w()) {
                this.g = this.g * 3 + 1013904223;
                var8 = this.g >> 2;
                var9 = var5 + (var8 & 15);
                var10 = var6 + (var8 >> 8 & 15);
                var11 = this.f(var9, var10);
                if (this.y(var9, var11, var10)) {
                    this.a((OEntity) (new OEntityLightningBolt(this, (double) var9, (double) var11, (double) var10)));
                    this.m = 2;
                }
            }

            OProfiler.b("iceandsnow");
            if (this.r.nextInt(16) == 0) {
                this.g = this.g * 3 + 1013904223;
                var8 = this.g >> 2;
                var9 = var8 & 15;
                var10 = var8 >> 8 & 15;
                var11 = this.f(var9 + var5, var10 + var6);
                if (this.t(var9 + var5, var11 - 1, var10 + var6)) {
                    this.e(var9 + var5, var11 - 1, var10 + var6, OBlock.aT.bO);
                }

                if (this.x() && this.u(var9 + var5, var11, var10 + var6)) {
                    this.e(var9 + var5, var11, var10 + var6, OBlock.aS.bO);
                }
            }

            OProfiler.b("tickTiles");
            OExtendedBlockStorage[] var19 = var7.h();

            var9 = var19.length;

            for (var10 = 0; var10 < var9; ++var10) {
                OExtendedBlockStorage var20 = var19[var10];

                if (var20 != null && var20.b()) {
                    for (int var12 = 0; var12 < 3; ++var12) {
                        this.g = this.g * 3 + 1013904223;
                        int var13 = this.g >> 2;
                        int var14 = var13 & 15;
                        int var15 = var13 >> 8 & 15;
                        int var16 = var13 >> 16 & 15;
                        int var17 = var20.a(var14, var16, var15);

                        ++var2;
                        OBlock var18 = OBlock.m[var17];

                        if (var18 != null && var18.n()) {
                            ++var1;
                            var18.a(this, var14 + var5, var16 + var20.c(), var15 + var6, this.r);
                        }
                    }
                }
            }

            OProfiler.a();
        }

    }

    public boolean s(int var1, int var2, int var3) {
        return this.c(var1, var2, var3, false);
    }

    public boolean t(int var1, int var2, int var3) {
        return this.c(var1, var2, var3, true);
    }

    public boolean c(int var1, int var2, int var3, boolean var4) {
        OBiomeGenBase var5 = this.a(var1, var3);
        float var6 = var5.i();

        if (var6 > 0.15F) {
            return false;
        } else {
            if (var2 >= 0 && var2 < 256 && this.a(OEnumSkyBlock.b, var1, var2, var3) < 10) {
                int var7 = this.a(var1, var2, var3);

                if ((var7 == OBlock.B.bO || var7 == OBlock.A.bO) && this.c(var1, var2, var3) == 0) {
                    if (!var4) {
                        return true;
                    }

                    boolean var8 = true;

                    if (var8 && this.d(var1 - 1, var2, var3) != OMaterial.g) {
                        var8 = false;
                    }

                    if (var8 && this.d(var1 + 1, var2, var3) != OMaterial.g) {
                        var8 = false;
                    }

                    if (var8 && this.d(var1, var2, var3 - 1) != OMaterial.g) {
                        var8 = false;
                    }

                    if (var8 && this.d(var1, var2, var3 + 1) != OMaterial.g) {
                        var8 = false;
                    }

                    if (!var8) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public boolean u(int var1, int var2, int var3) {
        OBiomeGenBase var4 = this.a(var1, var3);
        float var5 = var4.i();

        if (var5 > 0.15F) {
            return false;
        } else {
            if (var2 >= 0 && var2 < 256 && this.a(OEnumSkyBlock.b, var1, var2, var3) < 10) {
                int var6 = this.a(var1, var2 - 1, var3);
                int var7 = this.a(var1, var2, var3);

                if (var7 == 0 && OBlock.aS.c(this, var1, var2, var3) && var6 != 0 && var6 != OBlock.aT.bO && OBlock.m[var6].cd.c()) {
                    return true;
                }
            }

            return false;
        }
    }

    public void v(int var1, int var2, int var3) {
        if (!this.t.e) {
            this.b(OEnumSkyBlock.a, var1, var2, var3);
        }

        this.b(OEnumSkyBlock.b, var1, var2, var3);
    }

    private int c(int var1, int var2, int var3, int var4, int var5, int var6) {
        int var7 = 0;

        if (this.l(var2, var3, var4)) {
            var7 = 15;
        } else {
            if (var6 == 0) {
                var6 = 1;
            }

            int var8 = this.a(OEnumSkyBlock.a, var2 - 1, var3, var4) - var6;
            int var9 = this.a(OEnumSkyBlock.a, var2 + 1, var3, var4) - var6;
            int var10 = this.a(OEnumSkyBlock.a, var2, var3 - 1, var4) - var6;
            int var11 = this.a(OEnumSkyBlock.a, var2, var3 + 1, var4) - var6;
            int var12 = this.a(OEnumSkyBlock.a, var2, var3, var4 - 1) - var6;
            int var13 = this.a(OEnumSkyBlock.a, var2, var3, var4 + 1) - var6;

            if (var8 > var7) {
                var7 = var8;
            }

            if (var9 > var7) {
                var7 = var9;
            }

            if (var10 > var7) {
                var7 = var10;
            }

            if (var11 > var7) {
                var7 = var11;
            }

            if (var12 > var7) {
                var7 = var12;
            }

            if (var13 > var7) {
                var7 = var13;
            }
        }

        return var7;
    }

    private int d(int var1, int var2, int var3, int var4, int var5, int var6) {
        int var7 = OBlock.q[var5];
        int var8 = this.a(OEnumSkyBlock.b, var2 - 1, var3, var4) - var6;
        int var9 = this.a(OEnumSkyBlock.b, var2 + 1, var3, var4) - var6;
        int var10 = this.a(OEnumSkyBlock.b, var2, var3 - 1, var4) - var6;
        int var11 = this.a(OEnumSkyBlock.b, var2, var3 + 1, var4) - var6;
        int var12 = this.a(OEnumSkyBlock.b, var2, var3, var4 - 1) - var6;
        int var13 = this.a(OEnumSkyBlock.b, var2, var3, var4 + 1) - var6;

        if (var8 > var7) {
            var7 = var8;
        }

        if (var9 > var7) {
            var7 = var9;
        }

        if (var10 > var7) {
            var7 = var10;
        }

        if (var11 > var7) {
            var7 = var11;
        }

        if (var12 > var7) {
            var7 = var12;
        }

        if (var13 > var7) {
            var7 = var13;
        }

        return var7;
    }

    public void b(OEnumSkyBlock var1, int var2, int var3, int var4) {
        if (this.a(var2, var3, var4, 17)) {
            int var5 = 0;
            int var6 = 0;

            OProfiler.a("getBrightness");
            int var7 = this.a(var1, var2, var3, var4);
            boolean var8 = false;
            int var10 = this.a(var2, var3, var4);
            int var11 = this.f(var2, var3, var4);

            if (var11 == 0) {
                var11 = 1;
            }

            boolean var12 = false;
            int var25;

            if (var1 == OEnumSkyBlock.a) {
                var25 = this.c(var7, var2, var3, var4, var10, var11);
            } else {
                var25 = this.d(var7, var2, var3, var4, var10, var11);
            }

            int var9;
            int var13;
            int var14;
            int var15;
            int var17;
            int var16;

            if (var25 > var7) {
                this.E[var6++] = 133152;
            } else if (var25 < var7) {
                if (var1 != OEnumSkyBlock.b) {
                    ;
                }

                this.E[var6++] = 133152 + (var7 << 18);

                while (var5 < var6) {
                    var9 = this.E[var5++];
                    var10 = (var9 & 63) - 32 + var2;
                    var11 = (var9 >> 6 & 63) - 32 + var3;
                    var25 = (var9 >> 12 & 63) - 32 + var4;
                    var13 = var9 >> 18 & 15;
                    var14 = this.a(var1, var10, var11, var25);
                    if (var14 == var13) {
                        this.a(var1, var10, var11, var25, 0);
                        if (var13 > 0) {
                            var15 = var10 - var2;
                            var16 = var11 - var3;
                            var17 = var25 - var4;
                            if (var15 < 0) {
                                var15 = -var15;
                            }

                            if (var16 < 0) {
                                var16 = -var16;
                            }

                            if (var17 < 0) {
                                var17 = -var17;
                            }

                            if (var15 + var16 + var17 < 17) {
                                for (int var18 = 0; var18 < 6; ++var18) {
                                    int var19 = var18 % 2 * 2 - 1;
                                    int var20 = var10 + var18 / 2 % 3 / 2 * var19;
                                    int var21 = var11 + (var18 / 2 + 1) % 3 / 2 * var19;
                                    int var22 = var25 + (var18 / 2 + 2) % 3 / 2 * var19;

                                    var14 = this.a(var1, var20, var21, var22);
                                    int var23 = OBlock.o[this.a(var20, var21, var22)];

                                    if (var23 == 0) {
                                        var23 = 1;
                                    }

                                    if (var14 == var13 - var23 && var6 < this.E.length) {
                                        this.E[var6++] = var20 - var2 + 32 + (var21 - var3 + 32 << 6) + (var22 - var4 + 32 << 12) + (var13 - var23 << 18);
                                    }
                                }
                            }
                        }
                    }
                }

                var5 = 0;
            }

            OProfiler.a();
            OProfiler.a("tcp < tcc");

            while (var5 < var6) {
                var7 = this.E[var5++];
                int var24 = (var7 & 63) - 32 + var2;

                var9 = (var7 >> 6 & 63) - 32 + var3;
                var10 = (var7 >> 12 & 63) - 32 + var4;
                var11 = this.a(var1, var24, var9, var10);
                var25 = this.a(var24, var9, var10);
                var13 = OBlock.o[var25];
                if (var13 == 0) {
                    var13 = 1;
                }

                boolean var26 = false;

                if (var1 == OEnumSkyBlock.a) {
                    var14 = this.c(var11, var24, var9, var10, var25, var13);
                } else {
                    var14 = this.d(var11, var24, var9, var10, var25, var13);
                }

                if (var14 != var11) {
                    this.a(var1, var24, var9, var10, var14);
                    if (var14 > var11) {
                        var15 = var24 - var2;
                        var16 = var9 - var3;
                        var17 = var10 - var4;
                        if (var15 < 0) {
                            var15 = -var15;
                        }

                        if (var16 < 0) {
                            var16 = -var16;
                        }

                        if (var17 < 0) {
                            var17 = -var17;
                        }

                        if (var15 + var16 + var17 < 17 && var6 < this.E.length - 6) {
                            if (this.a(var1, var24 - 1, var9, var10) < var14) {
                                this.E[var6++] = var24 - 1 - var2 + 32 + (var9 - var3 + 32 << 6) + (var10 - var4 + 32 << 12);
                            }

                            if (this.a(var1, var24 + 1, var9, var10) < var14) {
                                this.E[var6++] = var24 + 1 - var2 + 32 + (var9 - var3 + 32 << 6) + (var10 - var4 + 32 << 12);
                            }

                            if (this.a(var1, var24, var9 - 1, var10) < var14) {
                                this.E[var6++] = var24 - var2 + 32 + (var9 - 1 - var3 + 32 << 6) + (var10 - var4 + 32 << 12);
                            }

                            if (this.a(var1, var24, var9 + 1, var10) < var14) {
                                this.E[var6++] = var24 - var2 + 32 + (var9 + 1 - var3 + 32 << 6) + (var10 - var4 + 32 << 12);
                            }

                            if (this.a(var1, var24, var9, var10 - 1) < var14) {
                                this.E[var6++] = var24 - var2 + 32 + (var9 - var3 + 32 << 6) + (var10 - 1 - var4 + 32 << 12);
                            }

                            if (this.a(var1, var24, var9, var10 + 1) < var14) {
                                this.E[var6++] = var24 - var2 + 32 + (var9 - var3 + 32 << 6) + (var10 + 1 - var4 + 32 << 12);
                            }
                        }
                    }
                }
            }

            OProfiler.a();
        }
    }

    public boolean a(boolean var1) {
        int var2 = this.H.size();

        if (var2 != this.I.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        } else {
            if (var2 > 1000) {
                var2 = 1000;
            }

            for (int var3 = 0; var3 < var2; ++var3) {
                ONextTickListEntry var4 = (ONextTickListEntry) this.H.first();

                if (!var1 && var4.e > this.x.f()) {
                    break;
                }

                this.H.remove(var4);
                this.I.remove(var4);
                byte var5 = 8;

                if (this.a(var4.a - var5, var4.b - var5, var4.c - var5, var4.a + var5, var4.b + var5, var4.c + var5)) {
                    int var6 = this.a(var4.a, var4.b, var4.c);

                    if (var6 == var4.d && var6 > 0) {
                        OBlock.m[var6].a(this, var4.a, var4.b, var4.c, this.r);
                    }
                }
            }

            return this.H.size() != 0;
        }
    }

    public List a(OChunk var1, boolean var2) {
        ArrayList var3 = null;
        OChunkCoordIntPair var4 = var1.k();
        int var5 = var4.a << 4;
        int var6 = var5 + 16;
        int var7 = var4.b << 4;
        int var8 = var7 + 16;
        Iterator var9 = this.I.iterator();

        while (var9.hasNext()) {
            ONextTickListEntry var10 = (ONextTickListEntry) var9.next();

            if (var10.a >= var5 && var10.a < var6 && var10.c >= var7 && var10.c < var8) {
                if (var2) {
                    this.H.remove(var10);
                    var9.remove();
                }

                if (var3 == null) {
                    var3 = new ArrayList();
                }

                var3.add(var10);
            }
        }

        return var3;
    }

    public List b(OEntity var1, OAxisAlignedBB var2) {
        this.S.clear();
        int var3 = OMathHelper.b((var2.a - 2.0D) / 16.0D);
        int var4 = OMathHelper.b((var2.d + 2.0D) / 16.0D);
        int var5 = OMathHelper.b((var2.c - 2.0D) / 16.0D);
        int var6 = OMathHelper.b((var2.f + 2.0D) / 16.0D);

        for (int var7 = var3; var7 <= var4; ++var7) {
            for (int var8 = var5; var8 <= var6; ++var8) {
                if (this.h(var7, var8)) {
                    this.d(var7, var8).a(var1, var2, this.S);
                }
            }
        }

        return this.S;
    }

    public List a(Class var1, OAxisAlignedBB var2) {
        int var3 = OMathHelper.b((var2.a - 2.0D) / 16.0D);
        int var4 = OMathHelper.b((var2.d + 2.0D) / 16.0D);
        int var5 = OMathHelper.b((var2.c - 2.0D) / 16.0D);
        int var6 = OMathHelper.b((var2.f + 2.0D) / 16.0D);
        ArrayList var7 = new ArrayList();

        for (int var8 = var3; var8 <= var4; ++var8) {
            for (int var9 = var5; var9 <= var6; ++var9) {
                if (this.h(var8, var9)) {
                    this.d(var8, var9).a(var1, var2, var7);
                }
            }
        }

        return var7;
    }

    public OEntity a(Class var1, OAxisAlignedBB var2, OEntity var3) {
        List var4 = this.a(var1, var2);
        OEntity var5 = null;
        double var6 = Double.MAX_VALUE;
        Iterator var8 = var4.iterator();

        while (var8.hasNext()) {
            OEntity var9 = (OEntity) var8.next();

            if (var9 != var3) {
                double var10 = var3.j(var9);

                if (var10 <= var6) {
                    var5 = var9;
                    var6 = var10;
                }
            }
        }

        return var5;
    }

    public void b(int var1, int var2, int var3, OTileEntity var4) {
        if (this.i(var1, var2, var3)) {
            this.c(var1, var3).e();
        }

        for (int var5 = 0; var5 < this.u.size(); ++var5) {
            ((OIWorldAccess) this.u.get(var5)).a(var1, var2, var3, var4);
        }

    }

    public int a(Class var1) {
        int var2 = 0;

        for (int var3 = 0; var3 < this.b.size(); ++var3) {
            OEntity var4 = (OEntity) this.b.get(var3);

            if (var1.isAssignableFrom(var4.getClass())) {
                ++var2;
            }
        }

        return var2;
    }

    public void a(List var1) {
        this.b.addAll(var1);

        for (int var2 = 0; var2 < var1.size(); ++var2) {
            this.c((OEntity) var1.get(var2));
        }

    }

    public void b(List var1) {
        this.G.addAll(var1);
    }

    public boolean a(int var1, int var2, int var3, int var4, boolean var5, int var6) {
        int var7 = this.a(var2, var3, var4);
        OBlock var8 = OBlock.m[var7];
        OBlock var9 = OBlock.m[var1];
        OAxisAlignedBB var10 = var9.e(this, var2, var3, var4);

        if (var5) {
            var10 = null;
        }

        if (var10 != null && !this.a(var10)) {
            return false;
        } else {
            if (var8 != null && (var8 == OBlock.A || var8 == OBlock.B || var8 == OBlock.C || var8 == OBlock.D || var8 == OBlock.ar || var8.cd.i())) {
                var8 = null;
            }

            return var1 > 0 && var8 == null && var9.b(this, var2, var3, var4, var6);
        }
    }

    public OPathEntity a(OEntity var1, OEntity var2, float var3, boolean var4, boolean var5, boolean var6, boolean var7) {
        OProfiler.a("pathfind");
        int var8 = OMathHelper.b(var1.bm);
        int var9 = OMathHelper.b(var1.bn + 1.0D);
        int var10 = OMathHelper.b(var1.bo);
        int var11 = (int) (var3 + 16.0F);
        int var12 = var8 - var11;
        int var13 = var9 - var11;
        int var14 = var10 - var11;
        int var15 = var8 + var11;
        int var16 = var9 + var11;
        int var17 = var10 + var11;
        OChunkCache var18 = new OChunkCache(this, var12, var13, var14, var15, var16, var17);
        OPathEntity var19 = (new OPathFinder(var18, var4, var5, var6, var7)).a(var1, var2, var3);

        OProfiler.a();
        return var19;
    }

    public OPathEntity a(OEntity var1, int var2, int var3, int var4, float var5, boolean var6, boolean var7, boolean var8, boolean var9) {
        OProfiler.a("pathfind");
        int var10 = OMathHelper.b(var1.bm);
        int var11 = OMathHelper.b(var1.bn);
        int var12 = OMathHelper.b(var1.bo);
        int var13 = (int) (var5 + 8.0F);
        int var14 = var10 - var13;
        int var15 = var11 - var13;
        int var16 = var12 - var13;
        int var17 = var10 + var13;
        int var18 = var11 + var13;
        int var19 = var12 + var13;
        OChunkCache var20 = new OChunkCache(this, var14, var15, var16, var17, var18, var19);
        OPathEntity var21 = (new OPathFinder(var20, var6, var7, var8, var9)).a(var1, var2, var3, var4, var5);

        OProfiler.a();
        return var21;
    }

    public boolean i(int var1, int var2, int var3, int var4) {
        int var5 = this.a(var1, var2, var3);

        return var5 == 0 ? false : OBlock.m[var5].d(this, var1, var2, var3, var4);
    }

    public boolean w(int var1, int var2, int var3) {
        return this.i(var1, var2 - 1, var3, 0) ? true : (this.i(var1, var2 + 1, var3, 1) ? true : (this.i(var1, var2, var3 - 1, 2) ? true : (this.i(var1, var2, var3 + 1, 3) ? true : (this.i(var1 - 1, var2, var3, 4) ? true : this.i(var1 + 1, var2, var3, 5)))));
    }

    public boolean j(int var1, int var2, int var3, int var4) {
        if (this.e(var1, var2, var3)) {
            return this.w(var1, var2, var3);
        } else {
            int var5 = this.a(var1, var2, var3);

            return var5 == 0 ? false : OBlock.m[var5].a((OIBlockAccess) this, var1, var2, var3, var4);
        }
    }

    public boolean x(int var1, int var2, int var3) {
        return this.j(var1, var2 - 1, var3, 0) ? true : (this.j(var1, var2 + 1, var3, 1) ? true : (this.j(var1, var2, var3 - 1, 2) ? true : (this.j(var1, var2, var3 + 1, 3) ? true : (this.j(var1 - 1, var2, var3, 4) ? true : this.j(var1 + 1, var2, var3, 5)))));
    }

    public OEntityPlayer a(OEntity var1, double var2) {
        return this.a(var1.bm, var1.bn, var1.bo, var2);
    }

    public OEntityPlayer a(double var1, double var3, double var5, double var7) {
        double var9 = -1.0D;
        OEntityPlayer var11 = null;

        for (int var12 = 0; var12 < this.d.size(); ++var12) {
            OEntityPlayer var13 = (OEntityPlayer) this.d.get(var12);
            double var14 = var13.e(var1, var3, var5);

            if ((var7 < 0.0D || var14 < var7 * var7) && (var9 == -1.0D || var14 < var9)) {
                var9 = var14;
                var11 = var13;
            }
        }

        return var11;
    }

    public OEntityPlayer a(double var1, double var3, double var5) {
        double var7 = -1.0D;
        OEntityPlayer var9 = null;

        for (int var10 = 0; var10 < this.d.size(); ++var10) {
            OEntityPlayer var11 = (OEntityPlayer) this.d.get(var10);
            double var12 = var11.e(var1, var11.bn, var3);

            if ((var5 < 0.0D || var12 < var5 * var5) && (var7 == -1.0D || var12 < var7)) {
                var7 = var12;
                var9 = var11;
            }
        }

        return var9;
    }

    public OEntityPlayer b(OEntity var1, double var2) {
        return this.b(var1.bm, var1.bn, var1.bo, var2);
    }

    public OEntityPlayer b(double var1, double var3, double var5, double var7) {
        double var9 = -1.0D;
        OEntityPlayer var11 = null;

        for (int var12 = 0; var12 < this.d.size(); ++var12) {
            OEntityPlayer var13 = (OEntityPlayer) this.d.get(var12);

            if (!var13.L.a) {
                double var14 = var13.e(var1, var3, var5);

                if ((var7 < 0.0D || var14 < var7 * var7) && (var9 == -1.0D || var14 < var9)) {
                    var9 = var14;
                    var11 = var13;
                }
            }
        }

        return var11;
    }

    public OEntityPlayer a(String var1) {
        for (int var2 = 0; var2 < this.d.size(); ++var2) {
            if (var1.equals(((OEntityPlayer) this.d.get(var2)).v)) {
                return (OEntityPlayer) this.d.get(var2);
            }
        }

        return null;
    }

    public void m() {
        this.w.b();
    }

    public void a(long var1) {
        this.x.a(var1);
    }

    public void b(long var1) {
        long var3 = var1 - this.x.f();

        ONextTickListEntry var6;

        for (Iterator var5 = this.I.iterator(); var5.hasNext(); var6.e += var3) {
            var6 = (ONextTickListEntry) var5.next();
        }

        this.a(var1);
    }

    public long n() {
        return this.x.b();
    }

    public long o() {
        return this.x.f();
    }

    public OChunkCoordinates p() {
        return new OChunkCoordinates(this.x.c(), this.x.d(), this.x.e());
    }

    public boolean a(OEntityPlayer var1, int var2, int var3, int var4) {
        return true;
    }

    public void a(OEntity var1, byte var2) {}

    public OIChunkProvider q() {
        return this.v;
    }

    public void e(int var1, int var2, int var3, int var4, int var5) {
        int var6 = this.a(var1, var2, var3);

        if (var6 > 0) {
            OBlock.m[var6].a(this, var1, var2, var3, var4, var5);
        }

    }

    public OISaveHandler r() {
        return this.w;
    }

    public OWorldInfo s() {
        return this.x;
    }

    public void t() {
        this.N = !this.d.isEmpty();
        Iterator var1 = this.d.iterator();

        while (var1.hasNext()) {
            OEntityPlayer var2 = (OEntityPlayer) var1.next();

            if (!var2.Z()) {
                this.N = false;
                break;
            }
        }

    }

    protected void u() {
        this.N = false;
        Iterator var1 = this.d.iterator();

        while (var1.hasNext()) {
            OEntityPlayer var2 = (OEntityPlayer) var1.next();

            if (var2.Z()) {
                var2.a(false, false, true);
            }
        }

        this.C();
    }

    public boolean v() {
        if (this.N && !this.F) {
            Iterator var1 = this.d.iterator();

            OEntityPlayer var2;

            do {
                if (!var1.hasNext()) {
                    return true;
                }

                var2 = (OEntityPlayer) var1.next();
            } while (var2.aa());

            return false;
        } else {
            return false;
        }
    }

    public float c(float var1) {
        return (this.k + (this.l - this.k) * var1) * this.d(var1);
    }

    public float d(float var1) {
        return this.i + (this.j - this.i) * var1;
    }

    public boolean w() {
        return (double) this.c(1.0F) > 0.9D;
    }

    public boolean x() {
        return (double) this.d(1.0F) > 0.2D;
    }

    public boolean y(int var1, int var2, int var3) {
        if (!this.x()) {
            return false;
        } else if (!this.l(var1, var2, var3)) {
            return false;
        } else if (this.f(var1, var3) > var2) {
            return false;
        } else {
            OBiomeGenBase var4 = this.a(var1, var3);

            return var4.c() ? false : var4.d();
        }
    }

    public boolean z(int var1, int var2, int var3) {
        OBiomeGenBase var4 = this.a(var1, var3);

        return var4.e();
    }

    public void a(String var1, OWorldSavedData var2) {
        this.z.a(var1, var2);
    }

    public OWorldSavedData a(Class var1, String var2) {
        return this.z.a(var1, var2);
    }

    public int b(String var1) {
        return this.z.a(var1);
    }

    public void f(int var1, int var2, int var3, int var4, int var5) {
        this.a((OEntityPlayer) null, var1, var2, var3, var4, var5);
    }

    public void a(OEntityPlayer var1, int var2, int var3, int var4, int var5, int var6) {
        for (int var7 = 0; var7 < this.u.size(); ++var7) {
            ((OIWorldAccess) this.u.get(var7)).a(var1, var2, var3, var4, var5, var6);
        }

    }

    public int y() {
        return 256;
    }

    public Random A(int var1, int var2, int var3) {
        long var4 = (long) var1 * 341873128712L + (long) var2 * 132897987541L + this.s().b() + (long) var3;

        this.r.setSeed(var4);
        return this.r;
    }

    public boolean z() {
        return false;
    }

    public OSpawnListEntry a(OEnumCreatureType var1, int var2, int var3, int var4) {
        List var5 = this.q().a(var1, var2, var3, var4);

        return var5 != null && !var5.isEmpty() ? (OSpawnListEntry) OWeightedRandom.a(this.r, (Collection) var5) : null;
    }

    public OChunkPosition b(String var1, int var2, int var3, int var4) {
        return this.q().a(this, var1, var2, var3, var4);
    }
}

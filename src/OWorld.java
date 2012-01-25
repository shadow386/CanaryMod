import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;


public class OWorld implements OIBlockAccess {

    public int a = 7;
    public int b;
    public int c;
    public int d;
    public int e;
    public boolean f;
    public List g;
    private List J;
    private TreeSet K;
    private Set L;
    public List h;
    private List M;
    private List N;
    public List i;
    public List j;
    private long O;
    public int k;
    protected int l;
    protected final int m;
    protected float n;
    protected float o;
    protected float p;
    protected float q;
    protected int r;
    public int s;
    public boolean t;
    private long P;
    protected int u;
    public int v;
    public Random w;
    public boolean x;
    public final OWorldProvider y;
    protected List z;
    protected OIChunkProvider A;
    protected final OISaveHandler B;
    protected OWorldInfo C;
    public boolean D;
    private boolean Q;
    public OMapStorage E;
    private ArrayList R;
    private boolean S;
    protected boolean F;
    protected boolean G;
    private Set T;
    private int U;
    int[] H;
    private List V;
    public boolean I;

    // CanaryMod
    public final World world = new World((OWorldServer) this);
    boolean loadedpreload = false;

    public OWorldChunkManager a() {
        return this.y.c;
    }

    public OWorld(OISaveHandler var1, String var2, OWorldSettings var3, OWorldProvider var4) {
        super();
        this.b = this.a + 4;
        this.c = 1 << this.a;
        this.d = this.c - 1;
        this.e = this.c / 2 - 1;
        this.f = false;
        this.g = new ArrayList();
        this.J = new ArrayList();
        this.K = new TreeSet();
        this.L = new HashSet();
        this.h = new ArrayList();
        this.M = new ArrayList();
        this.N = new ArrayList();
        this.i = new ArrayList();
        this.j = new ArrayList();
        this.O = 16777215L;
        this.k = 0;
        this.l = (new Random()).nextInt();
        this.m = 1013904223;
        this.r = 0;
        this.s = 0;
        this.t = false;
        this.P = System.currentTimeMillis();
        this.u = 40;
        this.w = new Random();
        this.x = false;
        this.z = new ArrayList();
        this.R = new ArrayList();
        this.F = true;
        this.G = true;
        this.T = new HashSet();
        this.U = this.w.nextInt(12000);
        this.H = new int['\u8000'];
        this.V = new ArrayList();
        this.I = false;
        this.B = var1;
        this.E = new OMapStorage(var1);
        this.C = var1.c();
        this.x = this.C == null;
        if (var4 != null) {
            this.y = var4;
        } else if (this.C != null && this.C.h() != 0) {
            this.y = OWorldProvider.a(this.C.h());
        } else {
            this.y = OWorldProvider.a(0);
        }

        boolean var5 = false;

        if (this.C == null) {
            this.C = new OWorldInfo(var3, var2);
            var5 = true;
        } else {
            this.C.a(var2);
        }

        this.y.a(this);
        this.A = this.b();
        if (var5) {
            this.c();
        }

        this.g();
        this.z();
    }

    protected OIChunkProvider b() {
        OIChunkLoader var1 = this.B.a(this.y);

        return new OChunkProvider(this, var1, this.y.b());
    }

    protected void c() {
        // CanaryMod: load preload plugins once!
        if (!loadedpreload) {
            etc.getLoader().loadPreloadPlugins();
            loadedpreload = true;
        }
        // CanaryMod onSpawnpointCreate hook
        Location point = (Location) etc.getLoader().callHook(PluginLoader.Hook.SPAWNPOINT_CREATE, world);

        if (point != null) {
            this.C.a((int) point.x, (byte) point.y, (int) point.z);
        }
        if (!this.y.c()) {
            this.C.a(0, this.y.e(), 0);
        } else {
            this.D = true;
            OWorldChunkManager var1 = this.a();
            List var2 = var1.a();
            Random var3 = new Random(this.m());
            OChunkPosition var4 = var1.a(0, 0, 256, var2, var3);
            int var5 = 0;
            int var6 = this.y.e();
            int var7 = 0;

            if (var4 != null) {
                var5 = var4.a;
                var7 = var4.c;
            } else {
                System.out.println("Unable to find spawn biome");
            }
	
            int var8 = 0;
	
            while (!this.y.a(var5, var7)) {
                var5 += var3.nextInt(64) - var3.nextInt(64);
                var7 += var3.nextInt(64) - var3.nextInt(64);
                ++var8;
                if (var8 == 1000) {
                    break;
                }
            }
	
            this.C.a(var5, var6, var7);
        }
        this.D = false;
    }

    public OChunkCoordinates d() {
        return this.y.d();
    }

    public int a(int var1, int var2) {
        int var3;

        for (var3 = this.e; !this.f(var1, var3 + 1, var2); ++var3) {
            ;
        }

        return this.a(var1, var3, var2);
    }

    public void a(boolean var1, OIProgressUpdate var2) {
        if (this.A.b()) {
            if (var2 != null) {
                var2.a("Saving level");
            }

            this.y();
            if (var2 != null) {
                var2.b("Saving chunks");
            }

            this.A.a(var1, var2);
        }
    }

    private void y() {
        this.l();
        this.B.a(this.C, this.i);
        this.E.a();
    }

    public int a(int var1, int var2, int var3) {
        return var1 >= -30000000 && var3 >= -30000000 && var1 < 30000000 && var3 < 30000000 ? (var2 < 0 ? 0 : (var2 >= this.c ? 0 : this.c(var1 >> 4, var3 >> 4).a(var1 & 15, var2, var3 & 15))) : 0;
    }

    public boolean f(int var1, int var2, int var3) {
        return this.a(var1, var2, var3) == 0;
    }

    public boolean g(int var1, int var2, int var3) {
        return var2 >= 0 && var2 < this.c ? this.g(var1 >> 4, var3 >> 4) : false;
    }

    public boolean a(int var1, int var2, int var3, int var4) {
        return this.a(var1 - var4, var2 - var4, var3 - var4, var1 + var4, var2 + var4, var3 + var4);
    }

    public boolean a(int var1, int var2, int var3, int var4, int var5, int var6) {
        if (var5 >= 0 && var2 < this.c) {
            var1 >>= 4;
            var2 >>= 4;
            var3 >>= 4;
            var4 >>= 4;
            var5 >>= 4;
            var6 >>= 4;

            for (int var7 = var1; var7 <= var4; ++var7) {
                for (int var8 = var3; var8 <= var6; ++var8) {
                    if (!this.g(var7, var8)) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    private boolean g(int var1, int var2) {
        return this.A.a(var1, var2);
    }

    public OChunk b(int var1, int var2) {
        return this.c(var1 >> 4, var2 >> 4);
    }

    public OChunk c(int var1, int var2) {
        return this.A.b(var1, var2);
    }

    public boolean a(int var1, int var2, int var3, int var4, int var5) {
        if (var1 >= -30000000 && var3 >= -30000000 && var1 < 30000000 && var3 < 30000000) {
            if (var2 < 0) {
                return false;
            } else if (var2 >= this.c) {
                return false;
            } else {
                OChunk var6 = this.c(var1 >> 4, var3 >> 4);
                boolean var7 = var6.a(var1 & 15, var2, var3 & 15, var4, var5);

                this.s(var1, var2, var3);
                return var7;
            }
        } else {
            return false;
        }
    }

    public boolean b(int var1, int var2, int var3, int var4) {
        // CanaryMod: Get Block Info
        Block block = this.world.getBlockAt(var1, var2, var3);

        if (var1 >= -30000000 && var3 >= -30000000 && var1 < 30000000 && var3 < 30000000) {
            if (var2 < 0) {
                return false;
            } else if (var2 >= this.c) {
                return false;
            } else { 
                        boolean var6 = false;
	                OChunk var5 = this.c(var1 >> 4, var3 >> 4);
                        //CanaryMod ignore if new block is air
                        if(var4 == 0){
                        var6 = var5.a(var1 & 15, var2, var3 & 15, var4);   
                        }
                        //CanaryMod BLOCK_UPDATE hook
                        else if(!(Boolean)OEntity.manager.callHook(PluginLoader.Hook.BLOCK_UPDATE, block, var4)){
	                var6 = var5.a(var1 & 15, var2, var3 & 15, var4);
                        }
                        this.s(var1, var2, var3);
	                return var6;
            }
        } else {
            return false;
        }
    }

    public OMaterial d(int var1, int var2, int var3) {
        int var4 = this.a(var1, var2, var3);

        return var4 == 0 ? OMaterial.a : OBlock.m[var4].cb;
    }

    public int c(int var1, int var2, int var3) {
        if (var1 >= -30000000 && var3 >= -30000000 && var1 < 30000000 && var3 < 30000000) {
            if (var2 < 0) {
                return 0;
            } else if (var2 >= this.c) {
                return 0;
            } else {
                OChunk var4 = this.c(var1 >> 4, var3 >> 4);

                var1 &= 15;
                var3 &= 15;
                return var4.b(var1, var2, var3);
            }
        } else {
            return 0;
        }
    }

    public void c(int var1, int var2, int var3, int var4) {
        if (this.d(var1, var2, var3, var4)) {
            int var5 = this.a(var1, var2, var3);

            if (OBlock.t[var5 & 255]) {
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
            } else if (var2 >= this.c) {
                return false;
            } else {
                OChunk var5 = this.c(var1 >> 4, var3 >> 4);

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

    public void h(int var1, int var2, int var3) {
        for (int var4 = 0; var4 < this.z.size(); ++var4) {
            ((OIWorldAccess) this.z.get(var4)).a(var1, var2, var3);
        }

    }

    protected void f(int var1, int var2, int var3, int var4) {
        this.h(var1, var2, var3);
        this.h(var1, var2, var3, var4);
    }

    public void g(int var1, int var2, int var3, int var4) {
        int var5;

        if (var3 > var4) {
            var5 = var4;
            var4 = var3;
            var3 = var5;
        }

        if (!this.y.f) {
            for (var5 = var3; var5 <= var4; ++var5) {
                this.b(OEnumSkyBlock.a, var1, var5, var2);
            }
        }

        this.b(var1, var3, var2, var1, var4, var2);
    }

    public void i(int var1, int var2, int var3) {
        for (int var4 = 0; var4 < this.z.size(); ++var4) {
            ((OIWorldAccess) this.z.get(var4)).a(var1, var2, var3, var1, var2, var3);
        }

    }

    public void b(int var1, int var2, int var3, int var4, int var5, int var6) {
        for (int var7 = 0; var7 < this.z.size(); ++var7) {
            ((OIWorldAccess) this.z.get(var7)).a(var1, var2, var3, var4, var5, var6);
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
        if (!this.t && !this.I) {
            OBlock var5 = OBlock.m[this.a(var1, var2, var3)];

            if (var5 != null) {
                var5.a(this, var1, var2, var3, var4);
            }

        }
    }

    public boolean j(int var1, int var2, int var3) {
        return this.c(var1 >> 4, var3 >> 4).c(var1 & 15, var2, var3 & 15);
    }

    public int k(int var1, int var2, int var3) {
        if (var2 < 0) {
            return 0;
        } else {
            if (var2 >= this.c) {
                var2 = this.c - 1;
            }

            return this.c(var1 >> 4, var3 >> 4).c(var1 & 15, var2, var3 & 15, 0);
        }
    }

    public int l(int var1, int var2, int var3) {
        return this.a(var1, var2, var3, true);
    }

    public int a(int var1, int var2, int var3, boolean var4) {
        if (var1 >= -30000000 && var3 >= -30000000 && var1 < 30000000 && var3 < 30000000) {
            if (var4) {
                int var5 = this.a(var1, var2, var3);

                if (var5 == OBlock.am.bO || var5 == OBlock.aC.bO || var5 == OBlock.aJ.bO || var5 == OBlock.av.bO) {
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
                if (var2 >= this.c) {
                    var2 = this.c - 1;
                }

                OChunk var11 = this.c(var1 >> 4, var3 >> 4);

                var1 &= 15;
                var3 &= 15;
                return var11.c(var1, var2, var3, this.k);
            }
        } else {
            return 15;
        }
    }

    public int d(int var1, int var2) {
        if (var1 >= -30000000 && var2 >= -30000000 && var1 < 30000000 && var2 < 30000000) {
            if (!this.g(var1 >> 4, var2 >> 4)) {
                return 0;
            } else {
                OChunk var3 = this.c(var1 >> 4, var2 >> 4);

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

        if (var3 >= this.c) {
            var3 = this.c - 1;
        }

        if (var3 >= 0 && var3 < this.c && var2 >= -30000000 && var4 >= -30000000 && var2 < 30000000 && var4 < 30000000) {
            int var5 = var2 >> 4;
            int var6 = var4 >> 4;

            if (!this.g(var5, var6)) {
                return 0;
            } else {
                OChunk var7 = this.c(var5, var6);

                return var7.a(var1, var2 & 15, var3, var4 & 15);
            }
        } else {
            return var1.c;
        }
    }

    public void a(OEnumSkyBlock var1, int var2, int var3, int var4, int var5) {
        if (var2 >= -30000000 && var4 >= -30000000 && var2 < 30000000 && var4 < 30000000) {
            if (var3 >= 0) {
                if (var3 < this.c) {
                    if (this.g(var2 >> 4, var4 >> 4)) {
                        OChunk var6 = this.c(var2 >> 4, var4 >> 4);

                        var6.a(var1, var2 & 15, var3, var4 & 15, var5);

                        for (int var7 = 0; var7 < this.z.size(); ++var7) {
                            ((OIWorldAccess) this.z.get(var7)).a(var2, var3, var4);
                        }

                    }
                }
            }
        }
    }

    public float m(int var1, int var2, int var3) {
        return this.y.g[this.l(var1, var2, var3)];
    }

    public boolean e() {
        return this.k < 4;
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
        for (int var5 = 0; var5 < this.z.size(); ++var5) {
            ((OIWorldAccess) this.z.get(var5)).a(var2, var1.bm, var1.bn - (double) var1.bF, var1.bo, var3, var4);
        }

    }

    public void a(double var1, double var3, double var5, String var7, float var8, float var9) {
        for (int var10 = 0; var10 < this.z.size(); ++var10) {
            ((OIWorldAccess) this.z.get(var10)).a(var7, var1, var3, var5, var8, var9);
        }

    }

    public void a(String var1, int var2, int var3, int var4) {
        for (int var5 = 0; var5 < this.z.size(); ++var5) {
            ((OIWorldAccess) this.z.get(var5)).a(var1, var2, var3, var4);
        }

    }

    public void a(String var1, double var2, double var4, double var6, double var8, double var10, double var12) {
        for (int var14 = 0; var14 < this.z.size(); ++var14) {
            ((OIWorldAccess) this.z.get(var14)).a(var1, var2, var4, var6, var8, var10, var12);
        }

    }

    public boolean a(OEntity var1) {
        this.j.add(var1);
        return true;
    }

    public boolean b(OEntity var1) {
        // CanaryMod: mob spawn hook
        if (var1 instanceof OEntityLiving && !(var1 instanceof OEntityPlayer)) {
            if ((etc.getInstance().getMobSpawnRate() < 100 && etc.getInstance().getMobSpawnRate() > 0 && etc.getInstance().getMobSpawnRate() <= w.nextInt(101)) || etc.getInstance().getMobSpawnRate() <= 0 || (Boolean) (etc.getLoader().callHook(PluginLoader.Hook.MOB_SPAWN, new Mob((OEntityLiving) var1)))) {
                return false;
            }
        }

        int var2 = OMathHelper.b(var1.bm / 16.0D);
        int var3 = OMathHelper.b(var1.bo / 16.0D);
        boolean var4 = false;

        if (var1 instanceof OEntityPlayer) {
            var4 = true;
        }

        if (!var4 && !this.g(var2, var3)) {
            return false;
        } else {
            if (var1 instanceof OEntityPlayer) {
                OEntityPlayer var5 = (OEntityPlayer) var1;

                this.i.add(var5);
                this.s();
            }

            this.c(var2, var3).a(var1);
            this.g.add(var1);
            this.c(var1);
            return true;
        }
    }

    protected void c(OEntity var1) {
        for (int var2 = 0; var2 < this.z.size(); ++var2) {
            ((OIWorldAccess) this.z.get(var2)).a(var1);
        }

    }

    protected void d(OEntity var1) {
        for (int var2 = 0; var2 < this.z.size(); ++var2) {
            ((OIWorldAccess) this.z.get(var2)).b(var1);
        }

    }

    public void e(OEntity var1) {
        if (var1.bg != null) {
            var1.bg.b((OEntity) null);
        }

        if (var1.bh != null) {
            var1.b((OEntity) null);
        }

        var1.T();
        if (var1 instanceof OEntityPlayer) {
            this.i.remove((OEntityPlayer) var1);
            this.s();
        }

    }

    public void f(OEntity var1) {
        var1.T();
        if (var1 instanceof OEntityPlayer) {
            this.i.remove((OEntityPlayer) var1);
            this.s();
        }

        int var2 = var1.ca;
        int var3 = var1.cc;

        if (var1.bZ && this.g(var2, var3)) {
            this.c(var2, var3).b(var1);
        }

        this.g.remove(var1);
        this.d(var1);
    }

    public void a(OIWorldAccess var1) {
        this.z.add(var1);
    }

    public List a(OEntity var1, OAxisAlignedBB var2) {
        this.R.clear();
        int var3 = OMathHelper.b(var2.a);
        int var4 = OMathHelper.b(var2.d + 1.0D);
        int var5 = OMathHelper.b(var2.b);
        int var6 = OMathHelper.b(var2.e + 1.0D);
        int var7 = OMathHelper.b(var2.c);
        int var8 = OMathHelper.b(var2.f + 1.0D);

        for (int var9 = var3; var9 < var4; ++var9) {
            for (int var10 = var7; var10 < var8; ++var10) {
                if (this.g(var9, this.c / 2, var10)) {
                    for (int var11 = var5 - 1; var11 < var6; ++var11) {
                        OBlock var12 = OBlock.m[this.a(var9, var11, var10)];

                        if (var12 != null) {
                            var12.a(this, var9, var11, var10, var2, this.R);
                        }
                    }
                }
            }
        }

        double var13 = 0.25D;
        List var17 = this.b(var1, var2.b(var13, var13, var13));

        for (int var16 = 0; var16 < var17.size(); ++var16) {
            OAxisAlignedBB var15 = ((OEntity) var17.get(var16)).h_();

            if (var15 != null && var15.a(var2)) {
                this.R.add(var15);
            }

            var15 = var1.a_((OEntity) var17.get(var16));
            if (var15 != null && var15.a(var2)) {
                this.R.add(var15);
            }
        }

        return this.R;
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
        return this.y.a(this.C.f(), var1);
    }

    public int e(int var1, int var2) {
        return this.b(var1, var2).c(var1 & 15, var2 & 15);
    }

    public int f(int var1, int var2) {
        OChunk var3 = this.b(var1, var2);
        int var4 = this.c - 1;

        var1 &= 15;

        for (var2 &= 15; var4 > 0; --var4) {
            int var5 = var3.a(var1, var4, var2);

            if (var5 != 0 && OBlock.m[var5].cb.c() && OBlock.m[var5].cb != OMaterial.i) {
                return var4 + 1;
            }
        }

        return -1;
    }

    public void c(int var1, int var2, int var3, int var4, int var5) {
        ONextTickListEntry var6 = new ONextTickListEntry(var1, var2, var3, var4);
        byte var7 = 8;

        if (this.f) {
            if (this.a(var6.a - var7, var6.b - var7, var6.c - var7, var6.a + var7, var6.b + var7, var6.c + var7)) {
                int var8 = this.a(var6.a, var6.b, var6.c);

                if (var8 == var6.d && var8 > 0) {
                    OBlock.m[var8].a(this, var6.a, var6.b, var6.c, this.w);
                }
            }

        } else {
            if (this.a(var1 - var7, var2 - var7, var3 - var7, var1 + var7, var2 + var7, var3 + var7)) {
                if (var4 > 0) {
                    var6.a((long) var5 + this.C.f());
                }

                if (!this.L.contains(var6)) {
                    this.L.add(var6);
                    this.K.add(var6);
                }
            }

        }
    }

    public void d(int var1, int var2, int var3, int var4, int var5) {
        ONextTickListEntry var6 = new ONextTickListEntry(var1, var2, var3, var4);

        if (var4 > 0) {
            var6.a((long) var5 + this.C.f());
        }

        if (!this.L.contains(var6)) {
            this.L.add(var6);
            this.K.add(var6);
        }

    }

    public void f() {
        OProfiler.a("entities");
        OProfiler.a("global");

        int var1;
        OEntity var2;

        for (var1 = 0; var1 < this.j.size(); ++var1) {
            var2 = (OEntity) this.j.get(var1);
            var2.y_();
            if (var2.bE) {
                this.j.remove(var1--);
            }
        }

        OProfiler.b("remove");
        this.g.removeAll(this.J);

        int var3;
        int var4;

        for (var1 = 0; var1 < this.J.size(); ++var1) {
            var2 = (OEntity) this.J.get(var1);
            var3 = var2.ca;
            var4 = var2.cc;
            if (var2.bZ && this.g(var3, var4)) {
                this.c(var3, var4).b(var2);
            }
        }

        for (var1 = 0; var1 < this.J.size(); ++var1) {
            this.d((OEntity) this.J.get(var1));
        }

        this.J.clear();
        OProfiler.b("regular");

        for (var1 = 0; var1 < this.g.size(); ++var1) {
            var2 = (OEntity) this.g.get(var1);
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
                if (var2.bZ && this.g(var3, var4)) {
                    this.c(var3, var4).b(var2);
                }

                this.g.remove(var1--);
                this.d(var2);
            }

            OProfiler.a();
        }

        OProfiler.b("tileEntities");
        this.S = true;
        Iterator var10 = this.h.iterator();

        while (var10.hasNext()) {
            OTileEntity var5 = (OTileEntity) var10.next();

            if (!var5.l() && var5.k != null && this.g(var5.l, var5.m, var5.n)) {
                var5.l_();
            }

            if (var5.l()) {
                var10.remove();
                if (this.g(var5.l >> 4, var5.n >> 4)) {
                    OChunk var7 = this.c(var5.l >> 4, var5.n >> 4);

                    if (var7 != null) {
                        var7.e(var5.l & 15, var5.m, var5.n & 15);
                    }
                }
            }
        }

        this.S = false;
        if (!this.N.isEmpty()) {
            this.h.removeAll(this.N);
            this.N.clear();
        }

        OProfiler.b("pendingTileEntities");
        if (!this.M.isEmpty()) {
            Iterator var6 = this.M.iterator();

            while (var6.hasNext()) {
                OTileEntity var8 = (OTileEntity) var6.next();

                if (!var8.l()) {
                    if (!this.h.contains(var8)) {
                        this.h.add(var8);
                    }

                    if (this.g(var8.l >> 4, var8.n >> 4)) {
                        OChunk var9 = this.c(var8.l >> 4, var8.n >> 4);

                        if (var9 != null) {
                            var9.a(var8.l & 15, var8.m, var8.n & 15, var8);
                        }
                    }

                    this.h(var8.l, var8.m, var8.n);
                }
            }

            this.M.clear();
        }

        OProfiler.a();
        OProfiler.a();
    }

    public void a(Collection var1) {
        if (this.S) {
            this.M.addAll(var1);
        } else {
            this.h.addAll(var1);
        }

    }

    public void g(OEntity var1) {
        this.a(var1, true);
    }

    public void a(OEntity var1, boolean var2) {
        int var3 = OMathHelper.b(var1.bm);
        int var4 = OMathHelper.b(var1.bo);
        byte var5 = 32;

        if (!var2 || this.a(var3 - var5, 0, var4 - var5, var3 + var5, this.c, var4 + var5)) {
            var1.bL = var1.bm;
            var1.bM = var1.bn;
            var1.bN = var1.bo;
            var1.bu = var1.bs;
            var1.bv = var1.bt;
            if (var2 && var1.bZ) {
                if (var1.bh != null) {
                    var1.N();
                } else {
                    var1.y_();
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
                if (var1.bZ && this.g(var1.ca, var1.cc)) {
                    this.c(var1.ca, var1.cc).a(var1, var1.cb);
                }

                if (this.g(var6, var8)) {
                    var1.bZ = true;
                    this.c(var6, var8).a(var1);
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

                    if (var11 != null && var11.cb.d()) {
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

                        if (var11 == OBlock.at.bO || var11 == OBlock.E.bO || var11 == OBlock.F.bO) {
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

                        if (var15 != null && var15.cb == var2) {
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

                    if (var12 != null && var12.cb == var2) {
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

                    if (var12 != null && var12.cb == var2) {
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

    public void a(OEntityPlayer var1, int var2, int var3, int var4, int var5) {
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

        if (this.a(var2, var3, var4) == OBlock.at.bO) {
            this.a(var1, 1004, var2, var3, var4, 0);
            this.e(var2, var3, var4, 0);
        }

    }

    public OTileEntity b(int var1, int var2, int var3) {
        OChunk var4 = this.c(var1 >> 4, var3 >> 4);

        if (var4 == null) {
            return null;
        } else {
            OTileEntity var5 = var4.d(var1 & 15, var2, var3 & 15);

            if (var5 == null) {
                Iterator var6 = this.M.iterator();

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

    public void a(int var1, int var2, int var3, OTileEntity var4) {
        if (var4 != null && !var4.l()) {
            if (this.S) {
                var4.l = var1;
                var4.m = var2;
                var4.n = var3;
                this.M.add(var4);
            } else {
                this.h.add(var4);
                OChunk var5 = this.c(var1 >> 4, var3 >> 4);

                if (var5 != null) {
                    var5.a(var1 & 15, var2, var3 & 15, var4);
                }
            }
        }

    }

    public void n(int var1, int var2, int var3) {
        OTileEntity var4 = this.b(var1, var2, var3);

        if (var4 != null && this.S) {
            var4.i();
            this.M.remove(var4);
        } else {
            if (var4 != null) {
                this.M.remove(var4);
                this.h.remove(var4);
            }

            OChunk var5 = this.c(var1 >> 4, var3 >> 4);

            if (var5 != null) {
                var5.e(var1 & 15, var2, var3 & 15);
            }
        }

    }

    public void a(OTileEntity var1) {
        this.N.add(var1);
    }

    public boolean o(int var1, int var2, int var3) {
        OBlock var4 = OBlock.m[this.a(var1, var2, var3)];

        return var4 == null ? false : var4.a();
    }

    public boolean e(int var1, int var2, int var3) {
        OBlock var4 = OBlock.m[this.a(var1, var2, var3)];

        return var4 == null ? false : var4.cb.j() && var4.b();
    }

    public boolean b(int var1, int var2, int var3, boolean var4) {
        if (var1 >= -30000000 && var3 >= -30000000 && var1 < 30000000 && var3 < 30000000) {
            OChunk var5 = this.A.b(var1 >> 4, var3 >> 4);

            if (var5 != null && !var5.g()) {
                OBlock var6 = OBlock.m[this.a(var1, var2, var3)];

                return var6 == null ? false : var6.cb.j() && var6.b();
            } else {
                return var4;
            }
        } else {
            return var4;
        }
    }

    public void g() {
        int var1 = this.a(1.0F);

        if (var1 != this.k) {
            this.k = var1;
        }

    }

    public void a(boolean var1, boolean var2) {
        this.F = var1;
        this.G = var2;
    }

    public void h() {
        if (this.r().p() && this.v < 3) {
            this.v = 3;
        }

        this.a().b();
        this.i();
        long var2;

        if (this.u()) {
            boolean var1 = false;

            if (this.F && this.v >= 1) {
                ;
            }

            if (!var1) {
                var2 = this.C.f() + 24000L;
                this.C.a(var2 - var2 % 24000L);
                this.t();
            }
        }

        OProfiler.a("mobSpawner");
        OSpawnerAnimals.a(this, this.F, this.G && this.C.f() % 400L == 0L);
        OProfiler.b("chunkSource");
        this.A.a();
        int var4 = this.a(1.0F);

        if (var4 != this.k) {
            this.k = var4;
        }

        var2 = this.C.f() + 1L;
        if (var2 % (long) this.u == 0L) {
            OProfiler.b("save");
            this.a(false, (OIProgressUpdate) null);
        }
      
        // CanaryMod: Time hook
        if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.TIME_CHANGE, world, var2)) {
            this.C.a(var2);
        }

        OProfiler.b("tickPending");
        this.a(false);
        OProfiler.b("tickTiles");
        this.k();
        OProfiler.a();
    }

    private void z() {
        if (this.C.l()) {
            this.o = 1.0F;
            if (this.C.j()) {
                this.q = 1.0F;
            }
        }

    }

    protected void i() {
        if (!this.y.f) {
            if (this.r > 0) {
                --this.r;
            }

            int var1 = this.C.k();

            if (var1 <= 0) {
                if (this.C.j()) {
                    this.C.b(this.w.nextInt(12000) + 3600);
                } else {
                    this.C.b(this.w.nextInt(168000) + 12000);
                }
            } else {
                --var1;
                this.C.b(var1);
                if (var1 <= 0) {
                    // CanaryMod: Thunder hook
                    if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.THUNDER_CHANGE, world, !this.C.j())) {
                        this.C.a(!this.C.j());
                    }
                }
            }

            int var2 = this.C.m();

            if (var2 <= 0) {
                if (this.C.l()) {
                    this.C.c(this.w.nextInt(12000) + 12000);
                } else {
                    this.C.c(this.w.nextInt(168000) + 12000);
                }
            } else {
                --var2;
                this.C.c(var2);
                if (var2 <= 0) {
                    // CanaryMod: Weather hook
                    if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.WEATHER_CHANGE, world, !this.C.l())) {
                        this.C.b(!this.C.l());
                    }
                }
            }

            this.n = this.o;
            if (this.C.l()) {
                this.o = (float) ((double) this.o + 0.01D);
            } else {
                this.o = (float) ((double) this.o - 0.01D);
            }

            if (this.o < 0.0F) {
                this.o = 0.0F;
            }

            if (this.o > 1.0F) {
                this.o = 1.0F;
            }

            this.p = this.q;
            if (this.C.j()) {
                this.q = (float) ((double) this.q + 0.01D);
            } else {
                this.q = (float) ((double) this.q - 0.01D);
            }

            if (this.q < 0.0F) {
                this.q = 0.0F;
            }

            if (this.q > 1.0F) {
                this.q = 1.0F;
            }

        }
    }

    private void A() {
        // CanaryMod: Weather hook
        if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.WEATHER_CHANGE, world, !this.C.l())) {
            this.C.c(0);
            this.C.b(false);
        }
        // CanaryMod: Thunder hook
        if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.THUNDER_CHANGE, world, !this.C.j())) {
            this.C.b(0);
            this.C.a(false);
        }
    }

    public void j() {
        this.C.c(1);
    }

    protected void k() {
        this.T.clear();
        OProfiler.a("buildList");

        int var1;
        int var6;

        for (var1 = 0; var1 < this.i.size(); ++var1) {
            OEntityPlayer var2 = (OEntityPlayer) this.i.get(var1);
            int var3 = OMathHelper.b(var2.bm / 16.0D);
            int var4 = OMathHelper.b(var2.bo / 16.0D);
            byte var5 = 7;

            for (var6 = -var5; var6 <= var5; ++var6) {
                for (int var7 = -var5; var7 <= var5; ++var7) {
                    this.T.add(new OChunkCoordIntPair(var6 + var3, var7 + var4));
                }
            }
        }

        if (this.U > 0) {
            --this.U;
        }

        var1 = 0;
        int var14 = 0;

        OProfiler.a();
        Iterator var15 = this.T.iterator();

        while (var15.hasNext()) {
            OChunkCoordIntPair var16 = (OChunkCoordIntPair) var15.next();
            int var17 = var16.a * 16;

            var6 = var16.b * 16;
            OProfiler.a("getChunk");
            OChunk var18 = this.c(var16.a, var16.b);

            OProfiler.b("tickChunk");
            var18.i();
            OProfiler.b("moodSound");
            int var8;
            int var9;
            int var10;
            int var11;
            int var12;

            if (this.U == 0) {
                this.l = this.l * 3 + 1013904223;
                var8 = this.l >> 2;
                var9 = var8 & 15;
                var10 = var8 >> 8 & 15;
                var11 = var8 >> 16 & this.d;
                var12 = var18.a(var9, var11, var10);
                var9 += var17;
                var10 += var6;
                if (var12 == 0 && this.k(var9, var11, var10) <= this.w.nextInt(8) && this.a(OEnumSkyBlock.a, var9, var11, var10) <= 0) {
                    OEntityPlayer var13 = this.a((double) var9 + 0.5D, (double) var11 + 0.5D, (double) var10 + 0.5D, 8.0D);

                    if (var13 != null && var13.e((double) var9 + 0.5D, (double) var11 + 0.5D, (double) var10 + 0.5D) > 4.0D) {
                        this.a((double) var9 + 0.5D, (double) var11 + 0.5D, (double) var10 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.w.nextFloat() * 0.2F);
                        this.U = this.w.nextInt(12000) + 6000;
                    }
                }
            }

            OProfiler.b("thunder");
            if (this.w.nextInt(100000) == 0 && this.w() && this.v()) {
                this.l = this.l * 3 + 1013904223;
                var8 = this.l >> 2;
                var9 = var17 + (var8 & 15);
                var10 = var6 + (var8 >> 8 & 15);
                var11 = this.e(var9, var10);
                if (this.v(var9, var11, var10)) {
                    this.a((OEntity) (new OEntityLightningBolt(this, (double) var9, (double) var11, (double) var10)));
                    this.r = 2;
                }
            }

            OProfiler.b("iceandsnow");
            if (this.w.nextInt(16) == 0) {
                this.l = this.l * 3 + 1013904223;
                var8 = this.l >> 2;
                var9 = var8 & 15;
                var10 = var8 >> 8 & 15;
                var11 = this.e(var9 + var17, var10 + var6);
                if (this.q(var9 + var17, var11 - 1, var10 + var6)) {
                    this.e(var9 + var17, var11 - 1, var10 + var6, OBlock.aV.bO);
                }

                if (this.w() && this.r(var9 + var17, var11, var10 + var6)) {
                    this.e(var9 + var17, var11, var10 + var6, OBlock.aU.bO);
                }
            }

            OProfiler.b("checkLight");
            this.s(var17 + this.w.nextInt(16), this.w.nextInt(this.c), var6 + this.w.nextInt(16));
            OProfiler.b("tickTiles");

            for (var8 = 0; var8 < 20; ++var8) {
                this.l = this.l * 3 + 1013904223;
                var9 = this.l >> 2;
                var10 = var9 & 15;
                var11 = var9 >> 8 & 15;
                var12 = var9 >> 16 & this.d;
                int var19 = var18.b[var10 << this.b | var11 << this.a | var12] & 255;

                ++var14;
                if (OBlock.n[var19]) {
                    ++var1;
                    OBlock.m[var19].a(this, var10 + var17, var12, var11 + var6, this.w);
                }
            }

            OProfiler.a();
        }

    }

    public boolean p(int var1, int var2, int var3) {
        return this.c(var1, var2, var3, false);
    }

    public boolean q(int var1, int var2, int var3) {
        return this.c(var1, var2, var3, true);
    }

    public boolean c(int var1, int var2, int var3, boolean var4) {
        float var5 = this.a().a(var1, var2, var3);

        if (var5 > 0.15F) {
            return false;
        } else {
            if (var2 >= 0 && var2 < this.c && this.a(OEnumSkyBlock.b, var1, var2, var3) < 10) {
                int var6 = this.a(var1, var2, var3);

                if ((var6 == OBlock.D.bO || var6 == OBlock.C.bO) && this.c(var1, var2, var3) == 0) {
                    if (!var4) {
                        return true;
                    }

                    boolean var7 = true;

                    if (var7 && this.d(var1 - 1, var2, var3) != OMaterial.g) {
                        var7 = false;
                    }

                    if (var7 && this.d(var1 + 1, var2, var3) != OMaterial.g) {
                        var7 = false;
                    }

                    if (var7 && this.d(var1, var2, var3 - 1) != OMaterial.g) {
                        var7 = false;
                    }

                    if (var7 && this.d(var1, var2, var3 + 1) != OMaterial.g) {
                        var7 = false;
                    }

                    if (!var7) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public boolean r(int var1, int var2, int var3) {
        float var4 = this.a().a(var1, var2, var3);

        if (var4 > 0.15F) {
            return false;
        } else {
            if (var2 >= 0 && var2 < this.c && this.a(OEnumSkyBlock.b, var1, var2, var3) < 10) {
                int var5 = this.a(var1, var2 - 1, var3);
                int var6 = this.a(var1, var2, var3);

                if (var6 == 0 && OBlock.aU.c(this, var1, var2, var3) && var5 != 0 && var5 != OBlock.aV.bO && OBlock.m[var5].cb.c()) {
                    return true;
                }
            }

            return false;
        }
    }

    public void s(int var1, int var2, int var3) {
        if (!this.y.f) {
            this.b(OEnumSkyBlock.a, var1, var2, var3);
        }

        this.b(OEnumSkyBlock.b, var1, var2, var3);
    }

    private int d(int var1, int var2, int var3, int var4, int var5, int var6) {
        int var7 = 0;

        if (this.j(var2, var3, var4)) {
            var7 = 15;
        } else {
            if (var6 == 0) {
                var6 = 1;
            }

            for (int var8 = 0; var8 < 6; ++var8) {
                int var9 = var8 % 2 * 2 - 1;
                int var10 = var2 + var8 / 2 % 3 / 2 * var9;
                int var11 = var3 + (var8 / 2 + 1) % 3 / 2 * var9;
                int var12 = var4 + (var8 / 2 + 2) % 3 / 2 * var9;
                int var13 = this.a(OEnumSkyBlock.a, var10, var11, var12) - var6;

                if (var13 > var7) {
                    var7 = var13;
                }
            }
        }

        return var7;
    }

    private int e(int var1, int var2, int var3, int var4, int var5, int var6) {
        int var7 = OBlock.s[var5];
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
            int var7 = this.a(var1, var2, var3, var4);
            boolean var8 = false;
            int var10 = this.a(var2, var3, var4);
            int var11 = OBlock.q[var10];

            if (var11 == 0) {
                var11 = 1;
            }

            boolean var12 = false;
            int var25;

            if (var1 == OEnumSkyBlock.a) {
                var25 = this.d(var7, var2, var3, var4, var10, var11);
            } else {
                var25 = this.e(var7, var2, var3, var4, var10, var11);
            }

            int var9;
            int var13;
            int var14;
            int var15;
            int var17;
            int var16;

            if (var25 > var7) {
                this.H[var6++] = 133152;
            } else if (var25 < var7) {
                if (var1 != OEnumSkyBlock.b) {
                    ;
                }

                this.H[var6++] = 133152 + (var7 << 18);

                while (var5 < var6) {
                    var9 = this.H[var5++];
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
                                    int var23 = OBlock.q[this.a(var20, var21, var22)];

                                    if (var23 == 0) {
                                        var23 = 1;
                                    }

                                    if (var14 == var13 - var23) {
                                        this.H[var6++] = var20 - var2 + 32 + (var21 - var3 + 32 << 6) + (var22 - var4 + 32 << 12) + (var13 - var23 << 18);
                                    }
                                }
                            }
                        }
                    }
                }

                var5 = 0;
            }

            while (var5 < var6) {
                var7 = this.H[var5++];
                int var24 = (var7 & 63) - 32 + var2;

                var9 = (var7 >> 6 & 63) - 32 + var3;
                var10 = (var7 >> 12 & 63) - 32 + var4;
                var11 = this.a(var1, var24, var9, var10);
                var25 = this.a(var24, var9, var10);
                var13 = OBlock.q[var25];
                if (var13 == 0) {
                    var13 = 1;
                }

                boolean var26 = false;

                if (var1 == OEnumSkyBlock.a) {
                    var14 = this.d(var11, var24, var9, var10, var25, var13);
                } else {
                    var14 = this.e(var11, var24, var9, var10, var25, var13);
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

                        if (var15 + var16 + var17 < 17 && var6 < this.H.length - 6) {
                            if (this.a(var1, var24 - 1, var9, var10) < var14) {
                                this.H[var6++] = var24 - 1 - var2 + 32 + (var9 - var3 + 32 << 6) + (var10 - var4 + 32 << 12);
                            }

                            if (this.a(var1, var24 + 1, var9, var10) < var14) {
                                this.H[var6++] = var24 + 1 - var2 + 32 + (var9 - var3 + 32 << 6) + (var10 - var4 + 32 << 12);
                            }

                            if (this.a(var1, var24, var9 - 1, var10) < var14) {
                                this.H[var6++] = var24 - var2 + 32 + (var9 - 1 - var3 + 32 << 6) + (var10 - var4 + 32 << 12);
                            }

                            if (this.a(var1, var24, var9 + 1, var10) < var14) {
                                this.H[var6++] = var24 - var2 + 32 + (var9 + 1 - var3 + 32 << 6) + (var10 - var4 + 32 << 12);
                            }

                            if (this.a(var1, var24, var9, var10 - 1) < var14) {
                                this.H[var6++] = var24 - var2 + 32 + (var9 - var3 + 32 << 6) + (var10 - 1 - var4 + 32 << 12);
                            }

                            if (this.a(var1, var24, var9, var10 + 1) < var14) {
                                this.H[var6++] = var24 - var2 + 32 + (var9 - var3 + 32 << 6) + (var10 + 1 - var4 + 32 << 12);
                            }
                        }
                    }
                }
            }

        }
    }

    public boolean a(boolean var1) {
        int var2 = this.K.size();

        if (var2 != this.L.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        } else {
            if (var2 > 1000) {
                var2 = 1000;
            }

            for (int var3 = 0; var3 < var2; ++var3) {
                ONextTickListEntry var4 = (ONextTickListEntry) this.K.first();

                if (!var1 && var4.e > this.C.f()) {
                    break;
                }

                this.K.remove(var4);
                this.L.remove(var4);
                byte var5 = 8;

                if (this.a(var4.a - var5, var4.b - var5, var4.c - var5, var4.a + var5, var4.b + var5, var4.c + var5)) {
                    int var6 = this.a(var4.a, var4.b, var4.c);

                    if (var6 == var4.d && var6 > 0) {
                        OBlock.m[var6].a(this, var4.a, var4.b, var4.c, this.w);
                    }
                }
            }

            return this.K.size() != 0;
        }
    }

    public List a(OChunk var1, boolean var2) {
        ArrayList var3 = null;
        OChunkCoordIntPair var4 = var1.j();
        int var5 = var4.a << 4;
        int var6 = var5 + 16;
        int var7 = var4.b << 4;
        int var8 = var7 + 16;
        Iterator var9 = this.L.iterator();

        while (var9.hasNext()) {
            ONextTickListEntry var10 = (ONextTickListEntry) var9.next();

            if (var10.a >= var5 && var10.a < var6 && var10.c >= var7 && var10.c < var8) {
                if (var2) {
                    this.K.remove(var10);
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
        this.V.clear();
        int var3 = OMathHelper.b((var2.a - 2.0D) / 16.0D);
        int var4 = OMathHelper.b((var2.d + 2.0D) / 16.0D);
        int var5 = OMathHelper.b((var2.c - 2.0D) / 16.0D);
        int var6 = OMathHelper.b((var2.f + 2.0D) / 16.0D);

        for (int var7 = var3; var7 <= var4; ++var7) {
            for (int var8 = var5; var8 <= var6; ++var8) {
                if (this.g(var7, var8)) {
                    this.c(var7, var8).a(var1, var2, this.V);
                }
            }
        }

        return this.V;
    }

    public List a(Class var1, OAxisAlignedBB var2) {
        int var3 = OMathHelper.b((var2.a - 2.0D) / 16.0D);
        int var4 = OMathHelper.b((var2.d + 2.0D) / 16.0D);
        int var5 = OMathHelper.b((var2.c - 2.0D) / 16.0D);
        int var6 = OMathHelper.b((var2.f + 2.0D) / 16.0D);
        ArrayList var7 = new ArrayList();

        for (int var8 = var3; var8 <= var4; ++var8) {
            for (int var9 = var5; var9 <= var6; ++var9) {
                if (this.g(var8, var9)) {
                    this.c(var8, var9).a(var1, var2, var7);
                }
            }
        }

        return var7;
    }

    public void b(int var1, int var2, int var3, OTileEntity var4) {
        if (this.g(var1, var2, var3)) {
            this.b(var1, var3).f();
        }

        for (int var5 = 0; var5 < this.z.size(); ++var5) {
            ((OIWorldAccess) this.z.get(var5)).a(var1, var2, var3, var4);
        }

    }

    public int a(Class var1) {
        int var2 = 0;

        for (int var3 = 0; var3 < this.g.size(); ++var3) {
            OEntity var4 = (OEntity) this.g.get(var3);

            if (var1.isAssignableFrom(var4.getClass())) {
                ++var2;
            }
        }

        return var2;
    }

    public void a(List var1) {
        this.g.addAll(var1);

        for (int var2 = 0; var2 < var1.size(); ++var2) {
            this.c((OEntity) var1.get(var2));
        }

    }

    public void b(List var1) {
        this.J.addAll(var1);
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
            if (var8 == OBlock.C || var8 == OBlock.D || var8 == OBlock.E || var8 == OBlock.F || var8 == OBlock.at || var8 == OBlock.aU || var8 == OBlock.bw) {
                var8 = null;
            }

            return var1 > 0 && var8 == null && var9.b(this, var2, var3, var4, var6);
        }
    }

    public OPathEntity a(OEntity var1, OEntity var2, float var3) {
        OProfiler.a("pathfind");
        int var4 = OMathHelper.b(var1.bm);
        int var5 = OMathHelper.b(var1.bn);
        int var6 = OMathHelper.b(var1.bo);
        int var7 = (int) (var3 + 16.0F);
        int var8 = var4 - var7;
        int var9 = var5 - var7;
        int var10 = var6 - var7;
        int var11 = var4 + var7;
        int var12 = var5 + var7;
        int var13 = var6 + var7;
        OChunkCache var14 = new OChunkCache(this, var8, var9, var10, var11, var12, var13);
        OPathEntity var15 = (new OPathFinder(var14)).a(var1, var2, var3);

        OProfiler.a();
        return var15;
    }

    public OPathEntity a(OEntity var1, int var2, int var3, int var4, float var5) {
        OProfiler.a("pathfind");
        int var6 = OMathHelper.b(var1.bm);
        int var7 = OMathHelper.b(var1.bn);
        int var8 = OMathHelper.b(var1.bo);
        int var9 = (int) (var5 + 8.0F);
        int var10 = var6 - var9;
        int var11 = var7 - var9;
        int var12 = var8 - var9;
        int var13 = var6 + var9;
        int var14 = var7 + var9;
        int var15 = var8 + var9;
        OChunkCache var16 = new OChunkCache(this, var10, var11, var12, var13, var14, var15);
        OPathEntity var17 = (new OPathFinder(var16)).a(var1, var2, var3, var4, var5);

        OProfiler.a();
        return var17;
    }

    public boolean i(int var1, int var2, int var3, int var4) {
        int var5 = this.a(var1, var2, var3);

        return var5 == 0 ? false : OBlock.m[var5].d(this, var1, var2, var3, var4);
    }

    public boolean t(int var1, int var2, int var3) {
        return this.i(var1, var2 - 1, var3, 0) ? true : (this.i(var1, var2 + 1, var3, 1) ? true : (this.i(var1, var2, var3 - 1, 2) ? true : (this.i(var1, var2, var3 + 1, 3) ? true : (this.i(var1 - 1, var2, var3, 4) ? true : this.i(var1 + 1, var2, var3, 5)))));
    }

    public boolean j(int var1, int var2, int var3, int var4) {
        if (this.e(var1, var2, var3)) {
            return this.t(var1, var2, var3);
        } else {
            int var5 = this.a(var1, var2, var3);

            return var5 == 0 ? false : OBlock.m[var5].a((OIBlockAccess) this, var1, var2, var3, var4);
        }
    }

    public boolean u(int var1, int var2, int var3) {
        return this.j(var1, var2 - 1, var3, 0) ? true : (this.j(var1, var2 + 1, var3, 1) ? true : (this.j(var1, var2, var3 - 1, 2) ? true : (this.j(var1, var2, var3 + 1, 3) ? true : (this.j(var1 - 1, var2, var3, 4) ? true : this.j(var1 + 1, var2, var3, 5)))));
    }

    public OEntityPlayer a(OEntity var1, double var2) {
        return this.a(var1.bm, var1.bn, var1.bo, var2);
    }

    public OEntityPlayer a(double var1, double var3, double var5, double var7) {
        double var9 = -1.0D;
        OEntityPlayer var11 = null;

        for (int var12 = 0; var12 < this.i.size(); ++var12) {
            OEntityPlayer var13 = (OEntityPlayer) this.i.get(var12);
            double var14 = var13.e(var1, var3, var5);

            if ((var7 < 0.0D || var14 < var7 * var7) && (var9 == -1.0D || var14 < var9)) {
                var9 = var14;
                var11 = var13;
            }
        }

        return var11;
    }

    public OEntityPlayer b(OEntity var1, double var2) {
        return this.b(var1.bm, var1.bn, var1.bo, var2);
    }

    public OEntityPlayer b(double var1, double var3, double var5, double var7) {
        double var9 = -1.0D;
        OEntityPlayer var11 = null;

        for (int var12 = 0; var12 < this.i.size(); ++var12) {
            OEntityPlayer var13 = (OEntityPlayer) this.i.get(var12);

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
        for (int var2 = 0; var2 < this.i.size(); ++var2) {
            if (var1.equals(((OEntityPlayer) this.i.get(var2)).v)) {
                return (OEntityPlayer) this.i.get(var2);
            }
        }

        return null;
    }

    // CanaryMod function: Overloaded original function to enable anti xray chunk data requests
    // without changing the normal behaviour of minecraft.
    public byte[] c(int var1, int var2, int var3, int var4, int var5, int var6) {
        return c(var1, var2, var3, var4, var5, var6, false);

    }
    
    // CanaryMod function: The overloaded method now takes an extra boolean enableXRay and passes it down to the chunk class.
    public byte[] c(int globalStartX, int globalStartY, int globalStartZ, int sizeX, int sizeY, int sizeZ, boolean enableXRay) {
        byte[] chunkDataToFill = new byte[sizeX * sizeY * sizeZ * 5 / 2];
        int chunkStartX = globalStartX >> 4;
        int chunkStartZ = globalStartZ >> 4;
        int chunkEndX = globalStartX + sizeX - 1 >> 4;
        int chunkEndZ = globalStartZ + sizeZ - 1 >> 4;
        int bufferOffset = 0;
        int chunkStartY = globalStartY;
        int chunkEndY = globalStartY + sizeY;

        if (globalStartY < 0) {
            chunkStartY = 0;
        }

        if (chunkEndY > this.c) {
            chunkEndY = this.c;
        }

        for (int chunkX = chunkStartX; chunkX <= chunkEndX; ++chunkX) {
            int relativeStartX = globalStartX - chunkX * 16;
            int relativeEndX = globalStartX + sizeX - chunkX * 16;

            if (relativeStartX < 0) {
                relativeStartX = 0;
            }

            if (relativeEndX > 16) {
                relativeEndX = 16;
            }

            for (int chunkZ = chunkStartZ; chunkZ <= chunkEndZ; ++chunkZ) {
                int relativeStartZ = globalStartZ - chunkZ * 16;
                int relativeEndZ = globalStartZ + sizeZ - chunkZ * 16;

                if (relativeStartZ < 0) {
                    relativeStartZ = 0;
                }

                if (relativeEndZ > 16) {
                    relativeEndZ = 16;
                }

                bufferOffset = this.c(chunkX, chunkZ).a(chunkDataToFill, relativeStartX, chunkStartY, relativeStartZ, relativeEndX, chunkEndY, relativeEndZ, bufferOffset, enableXRay);
            }
        }

        return chunkDataToFill;
    }

    public void l() {
        this.B.b();
    }

    public void a(long var1) {
        this.C.a(var1);
    }

    public void b(long var1) {
        long var3 = var1 - this.C.f();

        ONextTickListEntry var6;

        for (Iterator var5 = this.L.iterator(); var5.hasNext(); var6.e += var3) {
            var6 = (ONextTickListEntry) var5.next();
        }

        this.a(var1);
    }

    public long m() {
        return this.C.b();
    }

    public long n() {
        return this.C.f();
    }

    public OChunkCoordinates o() {
        return new OChunkCoordinates(this.C.c(), this.C.d(), this.C.e());
    }

    public boolean a(OEntityPlayer var1, int var2, int var3, int var4) {
        return true;
    }

    public void a(OEntity var1, byte var2) {}

    public OIChunkProvider p() {
        return this.A;
    }

    public void e(int var1, int var2, int var3, int var4, int var5) {
        int var6 = this.a(var1, var2, var3);

        if (var6 > 0) {
            OBlock.m[var6].a(this, var1, var2, var3, var4, var5);
        }

    }

    public OISaveHandler q() {
        return this.B;
    }

    public OWorldInfo r() {
        return this.C;
    }

    public void s() {
        this.Q = !this.i.isEmpty();
        Iterator var1 = this.i.iterator();

        while (var1.hasNext()) {
            OEntityPlayer var2 = (OEntityPlayer) var1.next();

            if (!var2.V()) {
                this.Q = false;
                break;
            }
        }

    }

    protected void t() {
        this.Q = false;
        Iterator var1 = this.i.iterator();

        while (var1.hasNext()) {
            OEntityPlayer var2 = (OEntityPlayer) var1.next();

            if (var2.V()) {
                var2.a(false, false, true);
            }
        }

        this.A();
    }

    public boolean u() {
        if (this.Q && !this.I) {
            Iterator var1 = this.i.iterator();

            OEntityPlayer var2;

            do {
                if (!var1.hasNext()) {
                    return true;
                }

                var2 = (OEntityPlayer) var1.next();
            } while (var2.W());

            return false;
        } else {
            return false;
        }
    }

    public float c(float var1) {
        return (this.p + (this.q - this.p) * var1) * this.d(var1);
    }

    public float d(float var1) {
        return this.n + (this.o - this.n) * var1;
    }

    public boolean v() {
        return (double) this.c(1.0F) > 0.9D;
    }

    public boolean w() {
        return (double) this.d(1.0F) > 0.2D;
    }

    public boolean v(int var1, int var2, int var3) {
        if (!this.w()) {
            return false;
        } else if (!this.j(var1, var2, var3)) {
            return false;
        } else if (this.e(var1, var3) > var2) {
            return false;
        } else {
            OBiomeGenBase var4 = this.a().a(var1, var3);

            return var4.b() ? false : var4.c();
        }
    }

    public void a(String var1, OWorldSavedData var2) {
        this.E.a(var1, var2);
    }

    public OWorldSavedData a(Class var1, String var2) {
        return this.E.a(var1, var2);
    }

    public int b(String var1) {
        return this.E.a(var1);
    }

    public void f(int var1, int var2, int var3, int var4, int var5) {
        this.a((OEntityPlayer) null, var1, var2, var3, var4, var5);
    }

    public void a(OEntityPlayer var1, int var2, int var3, int var4, int var5, int var6) {
        for (int var7 = 0; var7 < this.z.size(); ++var7) {
            ((OIWorldAccess) this.z.get(var7)).a(var1, var2, var3, var4, var5, var6);
        }

    }

    public Random w(int var1, int var2, int var3) {
        long var4 = (long) var1 * 341873128712L + (long) var2 * 132897987541L + this.r().b() + (long) var3;

        this.w.setSeed(var4);
        return this.w;
    }

    public boolean x() {
        return false;
    }

    public void a(OEnumSkyBlock var1, int var2, int var3, int var4, int var5, int var6, int var7) {}

    public OSpawnListEntry a(OEnumCreatureType var1, int var2, int var3, int var4) {
        List var5 = this.p().a(var1, var2, var3, var4);

        return var5 != null && !var5.isEmpty() ? (OSpawnListEntry) OWeightedRandom.a(this.w, (Collection) var5) : null;
    }

    public OChunkPosition b(String var1, int var2, int var3, int var4) {
        return this.p().a(this, var1, var2, var3, var4);
    }
}

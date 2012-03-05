import java.util.List;


public class OEntityMinecart extends OEntity implements OIInventory, Container<OItemStack> {

    private OItemStack[] d;
    private int e;
    private boolean f;
    public int a;
    public double b;
    public double c;
    private static final int[][][] g = new int[][][] { { { 0, 0, -1}, { 0, 0, 1}}, { { -1, 0, 0}, { 1, 0, 0}}, { { -1, -1, 0}, { 1, 0, 0}}, { { -1, 0, 0}, { 1, -1, 0}}, { { 0, 0, -1}, { 0, -1, 1}}, { { 0, -1, -1}, { 0, 0, 1}}, { { 0, 0, 1}, { 1, 0, 0}}, { { 0, 0, 1}, { -1, 0, 0}}, { { 0, 0, -1}, { -1, 0, 0}}, { { 0, 0, -1}, { 1, 0, 0}}};
    private int h;
    private double i;
    private double j;
    private double k;
    private double l;
    private double m;
    // CanaryMod start
    private String name = "Minecart";
    Minecart cart = new Minecart(this);

    // CanaryMod end

    public OEntityMinecart(OWorld var1) {
        super(var1);
        this.d = new OItemStack[36];
        this.e = 0;
        this.f = false;
        this.bf = true;
        this.b(0.98F, 0.7F);
        this.bF = this.bH / 2.0F;
    }

    protected boolean g_() {
        return false;
    }

    protected void b() {
        this.bY.a(16, new Byte((byte) 0));
        this.bY.a(17, new Integer(0));
        this.bY.a(18, new Integer(1));
        this.bY.a(19, new Integer(0));
    }

    public OAxisAlignedBB b_(OEntity var1) {
        return var1.bw;
    }

    public OAxisAlignedBB h() {
        return null;
    }

    public boolean e_() {
        return true;
    }

    public OEntityMinecart(OWorld var1, double var2, double var4, double var6, int var8) {
        this(var1);
        this.c(var2, var4 + (double) this.bF, var6);
        this.bp = 0.0D;
        this.bq = 0.0D;
        this.br = 0.0D;
        this.bj = var2;
        this.bk = var4;
        this.bl = var6;
        this.a = var8;
        // CanaryMod: Creation of the cart
        manager.callHook(PluginLoader.Hook.VEHICLE_CREATE, cart);
    }

    public double x_() {
        return (double) this.bH * 0.0D - 0.30000001192092896D;
    }

    public boolean a(ODamageSource var1, int var2) {
        // CanaryMod: Attack of the cart
        BaseEntity entity = null;

        if (var1 != null && var1.a() != null) {
            entity = new BaseEntity(var1.a());
        }
        if ((Boolean) manager.callHook(PluginLoader.Hook.VEHICLE_DAMAGE, cart, entity, var2)) {
            return true;
        }

        if (!this.bi.F && !this.bE) {
            this.e(-this.n());
            this.d(10);
            this.aV();
            this.c(this.l() + var2 * 10);
            if (this.l() > 40) {
                if (this.bg != null) {
                    this.bg.b((OEntity) this);
                }

                this.W();
                this.a(OItem.ay.bP, 1, 0.0F);
                if (this.a == 1) {
                    OEntityMinecart var3 = this;

                    for (int var4 = 0; var4 < var3.c(); ++var4) {
                        OItemStack var5 = var3.g_(var4);

                        if (var5 != null) {
                            float var6 = this.bS.nextFloat() * 0.8F + 0.1F;
                            float var7 = this.bS.nextFloat() * 0.8F + 0.1F;
                            float var8 = this.bS.nextFloat() * 0.8F + 0.1F;

                            while (var5.a > 0) {
                                int var9 = this.bS.nextInt(21) + 10;

                                if (var9 > var5.a) {
                                    var9 = var5.a;
                                }

                                var5.a -= var9;
                                OEntityItem var10 = new OEntityItem(this.bi, this.bm + (double) var6, this.bn + (double) var7, this.bo + (double) var8, new OItemStack(var5.c, var9, var5.h()));
                                float var11 = 0.05F;

                                var10.bp = (double) ((float) this.bS.nextGaussian() * var11);
                                var10.bq = (double) ((float) this.bS.nextGaussian() * var11 + 0.2F);
                                var10.br = (double) ((float) this.bS.nextGaussian() * var11);
                                this.bi.b((OEntity) var10);
                            }
                        }
                    }

                    this.a(OBlock.au.bO, 1, 0.0F);
                } else if (this.a == 2) {
                    this.a(OBlock.aB.bO, 1, 0.0F);
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public boolean o_() {
        return !this.bE;
    }

    public void W() {
        // CanaryMod: Destruction of the cart
        manager.callHook(PluginLoader.Hook.VEHICLE_DESTROYED, cart);
        for (int var1 = 0; var1 < this.c(); ++var1) {
            OItemStack var2 = this.g_(var1);

            if (var2 != null) {
                float var3 = this.bS.nextFloat() * 0.8F + 0.1F;
                float var4 = this.bS.nextFloat() * 0.8F + 0.1F;
                float var5 = this.bS.nextFloat() * 0.8F + 0.1F;

                while (var2.a > 0) {
                    int var6 = this.bS.nextInt(21) + 10;

                    if (var6 > var2.a) {
                        var6 = var2.a;
                    }

                    var2.a -= var6;
                    OEntityItem var7 = new OEntityItem(this.bi, this.bm + (double) var3, this.bn + (double) var4, this.bo + (double) var5, new OItemStack(var2.c, var6, var2.h()));
                    float var8 = 0.05F;

                    var7.bp = (double) ((float) this.bS.nextGaussian() * var8);
                    var7.bq = (double) ((float) this.bS.nextGaussian() * var8 + 0.2F);
                    var7.br = (double) ((float) this.bS.nextGaussian() * var8);
                    this.bi.b((OEntity) var7);
                }
            }
        }

        super.W();
    }

    public void G_() {
        // CanaryMod: Update of the cart
        manager.callHook(PluginLoader.Hook.VEHICLE_UPDATE, cart);
        
        if (this.m() > 0) {
            this.d(this.m() - 1);
        }
        
        double prevX = bj;
        double prevY = bk;
        double prevZ = bl;

        if (this.l() > 0) {
            this.c(this.l() - 1);
        }

        if (this.k() && this.bS.nextInt(4) == 0) {
            this.bi.a("largesmoke", this.bm, this.bn + 0.8D, this.bo, 0.0D, 0.0D, 0.0D);
        }

        if (this.bi.F) {
            if (this.h > 0) {
                double var1 = this.bm + (this.i - this.bm) / (double) this.h;
                double var3 = this.bn + (this.j - this.bn) / (double) this.h;
                double var5 = this.bo + (this.k - this.bo) / (double) this.h;

                double var7;

                for (var7 = this.l - (double) this.bs; var7 < -180.0D; var7 += 360.0D) {
                    ;
                }

                while (var7 >= 180.0D) {
                    var7 -= 360.0D;
                }

                this.bs = (float) ((double) this.bs + var7 / (double) this.h);
                this.bt = (float) ((double) this.bt + (this.m - (double) this.bt) / (double) this.h);
                --this.h;
                this.c(var1, var3, var5);
                this.c(this.bs, this.bt);
            } else {
                this.c(this.bm, this.bn, this.bo);
                this.c(this.bs, this.bt);
            }

        } else {
            this.bj = this.bm;
            this.bk = this.bn;
            this.bl = this.bo;
            this.bq -= 0.03999999910593033D;
            int var9 = OMathHelper.b(this.bm);
            int var10 = OMathHelper.b(this.bn);
            int var11 = OMathHelper.b(this.bo);
            
            // CanaryMod: Change of the cart
            if ((int) var9 != (int) prevX || (int) var10 != (int) prevY || (int) var11 != (int) prevZ) {
                manager.callHook(PluginLoader.Hook.VEHICLE_POSITIONCHANGE, cart, var9, var10, var11);
            }

            if (OBlockRail.g(this.bi, var9, var10 - 1, var11)) {
                --var10;
            }

            double var12 = 0.4D;
            double var14 = 0.0078125D;
            int var16 = this.bi.a(var9, var10, var11);

            if (OBlockRail.d(var16)) {
                OVec3D var17 = this.h(this.bm, this.bn, this.bo);
                int var18 = this.bi.c(var9, var10, var11);

                this.bn = (double) var10;
                boolean var19 = false;
                boolean var20 = false;

                if (var16 == OBlock.T.bO) {
                    var19 = (var18 & 8) != 0;
                    var20 = !var19;
                }

                if (((OBlockRail) OBlock.m[var16]).h()) {
                    var18 &= 7;
                }

                if (var18 >= 2 && var18 <= 5) {
                    this.bn = (double) (var10 + 1);
                }

                if (var18 == 2) {
                    this.bp -= var14;
                }

                if (var18 == 3) {
                    this.bp += var14;
                }

                if (var18 == 4) {
                    this.br += var14;
                }

                if (var18 == 5) {
                    this.br -= var14;
                }

                int[][] var21 = g[var18];
                double var22 = (double) (var21[1][0] - var21[0][0]);
                double var24 = (double) (var21[1][2] - var21[0][2]);
                double var26 = Math.sqrt(var22 * var22 + var24 * var24);
                double var28 = this.bp * var22 + this.br * var24;

                if (var28 < 0.0D) {
                    var22 = -var22;
                    var24 = -var24;
                }

                double var30 = Math.sqrt(this.bp * this.bp + this.br * this.br);

                this.bp = var30 * var22 / var26;
                this.br = var30 * var24 / var26;
                double var32;

                if (var20) {
                    var32 = Math.sqrt(this.bp * this.bp + this.br * this.br);
                    if (var32 < 0.03D) {
                        this.bp *= 0.0D;
                        this.bq *= 0.0D;
                        this.br *= 0.0D;
                    } else {
                        this.bp *= 0.5D;
                        this.bq *= 0.0D;
                        this.br *= 0.5D;
                    }
                }

                var32 = 0.0D;
                double var34 = (double) var9 + 0.5D + (double) var21[0][0] * 0.5D;
                double var36 = (double) var11 + 0.5D + (double) var21[0][2] * 0.5D;
                double var38 = (double) var9 + 0.5D + (double) var21[1][0] * 0.5D;
                double var40 = (double) var11 + 0.5D + (double) var21[1][2] * 0.5D;

                var22 = var38 - var34;
                var24 = var40 - var36;
                double var42;
                double var46;
                double var44;

                if (var22 == 0.0D) {
                    this.bm = (double) var9 + 0.5D;
                    var32 = this.bo - (double) var11;
                } else if (var24 == 0.0D) {
                    this.bo = (double) var11 + 0.5D;
                    var32 = this.bm - (double) var9;
                } else {
                    var42 = this.bm - var34;
                    var44 = this.bo - var36;
                    var46 = (var42 * var22 + var44 * var24) * 2.0D;
                    var32 = var46;
                }

                this.bm = var34 + var22 * var32;
                this.bo = var36 + var24 * var32;
                this.c(this.bm, this.bn + (double) this.bF, this.bo);
                var42 = this.bp;
                var44 = this.br;
                if (this.bg != null) {
                    var42 *= 0.75D;
                    var44 *= 0.75D;
                }

                if (var42 < -var12) {
                    var42 = -var12;
                }

                if (var42 > var12) {
                    var42 = var12;
                }

                if (var44 < -var12) {
                    var44 = -var12;
                }

                if (var44 > var12) {
                    var44 = var12;
                }

                this.a(var42, 0.0D, var44);
                if (var21[0][1] != 0 && OMathHelper.b(this.bm) - var9 == var21[0][0] && OMathHelper.b(this.bo) - var11 == var21[0][2]) {
                    this.c(this.bm, this.bn + (double) var21[0][1], this.bo);
                } else if (var21[1][1] != 0 && OMathHelper.b(this.bm) - var9 == var21[1][0] && OMathHelper.b(this.bo) - var11 == var21[1][2]) {
                    this.c(this.bm, this.bn + (double) var21[1][1], this.bo);
                }

                if (this.bg != null) {
                    this.bp *= 0.996999979019165D;
                    this.bq *= 0.0D;
                    this.br *= 0.996999979019165D;
                } else {
                    if (this.a == 2) {
                        var46 = (double) OMathHelper.a(this.b * this.b + this.c * this.c);
                        if (var46 > 0.01D) {
                            this.b /= var46;
                            this.c /= var46;
                            double var48 = 0.04D;

                            this.bp *= 0.800000011920929D;
                            this.bq *= 0.0D;
                            this.br *= 0.800000011920929D;
                            this.bp += this.b * var48;
                            this.br += this.c * var48;
                        } else {
                            this.bp *= 0.8999999761581421D;
                            this.bq *= 0.0D;
                            this.br *= 0.8999999761581421D;
                        }
                    }

                    this.bp *= 0.9599999785423279D;
                    this.bq *= 0.0D;
                    this.br *= 0.9599999785423279D;
                }

                OVec3D var50 = this.h(this.bm, this.bn, this.bo);

                if (var50 != null && var17 != null) {
                    double var51 = (var17.b - var50.b) * 0.05D;

                    var30 = Math.sqrt(this.bp * this.bp + this.br * this.br);
                    if (var30 > 0.0D) {
                        this.bp = this.bp / var30 * (var30 + var51);
                        this.br = this.br / var30 * (var30 + var51);
                    }

                    this.c(this.bm, var50.b, this.bo);
                }

                int var53 = OMathHelper.b(this.bm);
                int var54 = OMathHelper.b(this.bo);

                if (var53 != var9 || var54 != var11) {
                    var30 = Math.sqrt(this.bp * this.bp + this.br * this.br);
                    this.bp = var30 * (double) (var53 - var9);
                    this.br = var30 * (double) (var54 - var11);
                }

                double var55;

                if (this.a == 2) {
                    var55 = (double) OMathHelper.a(this.b * this.b + this.c * this.c);
                    if (var55 > 0.01D && this.bp * this.bp + this.br * this.br > 0.001D) {
                        this.b /= var55;
                        this.c /= var55;
                        if (this.b * this.bp + this.c * this.br < 0.0D) {
                            this.b = 0.0D;
                            this.c = 0.0D;
                        } else {
                            this.b = this.bp;
                            this.c = this.br;
                        }
                    }
                }

                if (var19) {
                    var55 = Math.sqrt(this.bp * this.bp + this.br * this.br);
                    if (var55 > 0.01D) {
                        double var57 = 0.06D;

                        this.bp += this.bp / var55 * var57;
                        this.br += this.br / var55 * var57;
                    } else if (var18 == 1) {
                        if (this.bi.e(var9 - 1, var10, var11)) {
                            this.bp = 0.02D;
                        } else if (this.bi.e(var9 + 1, var10, var11)) {
                            this.bp = -0.02D;
                        }
                    } else if (var18 == 0) {
                        if (this.bi.e(var9, var10, var11 - 1)) {
                            this.br = 0.02D;
                        } else if (this.bi.e(var9, var10, var11 + 1)) {
                            this.br = -0.02D;
                        }
                    }
                }
            } else {
                if (this.bp < -var12) {
                    this.bp = -var12;
                }

                if (this.bp > var12) {
                    this.bp = var12;
                }

                if (this.br < -var12) {
                    this.br = -var12;
                }

                if (this.br > var12) {
                    this.br = var12;
                }

                if (this.bx) {
                    this.bp *= 0.5D;
                    this.bq *= 0.5D;
                    this.br *= 0.5D;
                }

                this.a(this.bp, this.bq, this.br);
                if (!this.bx) {
                    this.bp *= 0.949999988079071D;
                    this.bq *= 0.949999988079071D;
                    this.br *= 0.949999988079071D;
                }
            }

            this.bt = 0.0F;
            double var59 = this.bj - this.bm;
            double var61 = this.bl - this.bo;

            if (var59 * var59 + var61 * var61 > 0.001D) {
                this.bs = (float) (Math.atan2(var61, var59) * 180.0D / 3.141592653589793D);
                if (this.f) {
                    this.bs += 180.0F;
                }
            }

            double var63;

            for (var63 = (double) (this.bs - this.bu); var63 >= 180.0D; var63 -= 360.0D) {
                ;
            }

            while (var63 < -180.0D) {
                var63 += 360.0D;
            }

            if (var63 < -170.0D || var63 >= 170.0D) {
                this.bs += 180.0F;
                this.f = !this.f;
            }

            this.c(this.bs, this.bt);
            List var65 = this.bi.b((OEntity) this, this.bw.b(0.20000000298023224D, 0.0D, 0.20000000298023224D));

            if (var65 != null && var65.size() > 0) {
                for (int var66 = 0; var66 < var65.size(); ++var66) {
                    OEntity var67 = (OEntity) var65.get(var66);

                    if (var67 != this.bg && var67.e_() && var67 instanceof OEntityMinecart) {
                        var67.k(this);
                    }
                }
            }

            if (this.bg != null && this.bg.bE) {
                if (this.bg.bh == this) {
                    this.bg.bh = null;
                }

                this.bg = null;
            }

            if (this.e > 0) {
                --this.e;
            }

            if (this.e <= 0) {
                this.b = this.c = 0.0D;
            }

            this.a(this.e > 0);
        }
    }
    
    // CanaryMod: Store last position, avoids Hook spaming
    private int lastX = 0;
    private int lastY = 0;
    private int lastZ = 0;

    public OVec3D h(double var1, double var3, double var5) {
        int var7 = OMathHelper.b(var1);
        int var8 = OMathHelper.b(var3);
        int var9 = OMathHelper.b(var5);
        
        // CanaryMod: Change of the cart
        if ((int) var7 != (int) lastX || (int) var8 != (int) lastY || (int) var9 != (int) lastZ) {
            manager.callHook(PluginLoader.Hook.VEHICLE_POSITIONCHANGE, cart, var7, var8, var9);
            lastX = var7;
            lastY = var8;
            lastZ = var9;
        }

        if (OBlockRail.g(this.bi, var7, var8 - 1, var9)) {
            --var8;
        }

        int var10 = this.bi.a(var7, var8, var9);

        if (OBlockRail.d(var10)) {
            int var11 = this.bi.c(var7, var8, var9);

            var3 = (double) var8;
            if (((OBlockRail) OBlock.m[var10]).h()) {
                var11 &= 7;
            }

            if (var11 >= 2 && var11 <= 5) {
                var3 = (double) (var8 + 1);
            }

            int[][] var12 = g[var11];
            double var13 = 0.0D;
            double var15 = (double) var7 + 0.5D + (double) var12[0][0] * 0.5D;
            double var17 = (double) var8 + 0.5D + (double) var12[0][1] * 0.5D;
            double var19 = (double) var9 + 0.5D + (double) var12[0][2] * 0.5D;
            double var21 = (double) var7 + 0.5D + (double) var12[1][0] * 0.5D;
            double var23 = (double) var8 + 0.5D + (double) var12[1][1] * 0.5D;
            double var25 = (double) var9 + 0.5D + (double) var12[1][2] * 0.5D;
            double var27 = var21 - var15;
            double var29 = (var23 - var17) * 2.0D;
            double var31 = var25 - var19;

            if (var27 == 0.0D) {
                var1 = (double) var7 + 0.5D;
                var13 = var5 - (double) var9;
            } else if (var31 == 0.0D) {
                var5 = (double) var9 + 0.5D;
                var13 = var1 - (double) var7;
            } else {
                double var33 = var1 - var15;
                double var35 = var5 - var19;
                double var37 = (var33 * var27 + var35 * var31) * 2.0D;

                var13 = var37;
            }

            var1 = var15 + var27 * var13;
            var3 = var17 + var29 * var13;
            var5 = var19 + var31 * var13;
            if (var29 < 0.0D) {
                ++var3;
            }

            if (var29 > 0.0D) {
                var3 += 0.5D;
            }

            return OVec3D.b(var1, var3, var5);
        } else {
            return null;
        }
    }

    protected void b(ONBTTagCompound var1) {
        var1.a("Type", this.a);
        if (this.a == 2) {
            var1.a("PushX", this.b);
            var1.a("PushZ", this.c);
            var1.a("Fuel", (short) this.e);
        } else if (this.a == 1) {
            ONBTTagList var2 = new ONBTTagList();

            for (int var3 = 0; var3 < this.d.length; ++var3) {
                if (this.d[var3] != null) {
                    ONBTTagCompound var4 = new ONBTTagCompound();

                    var4.a("Slot", (byte) var3);
                    this.d[var3].b(var4);
                    var2.a((ONBTBase) var4);
                }
            }

            var1.a("Items", (ONBTBase) var2);
        }

    }

    protected void a(ONBTTagCompound var1) {
        this.a = var1.f("Type");
        if (this.a == 2) {
            this.b = var1.i("PushX");
            this.c = var1.i("PushZ");
            this.e = var1.e("Fuel");
        } else if (this.a == 1) {
            ONBTTagList var2 = var1.n("Items");

            this.d = new OItemStack[this.c()];

            for (int var3 = 0; var3 < var2.d(); ++var3) {
                ONBTTagCompound var4 = (ONBTTagCompound) var2.a(var3);
                int var5 = var4.d("Slot") & 255;

                if (var5 >= 0 && var5 < this.d.length) {
                    this.d[var5] = OItemStack.a(var4);
                }
            }
        }

    }

    public void k(OEntity var1) {
        if (!this.bi.F) {
            if (var1 != this.bg) {
                // CanaryMod: Collision of a cart
                if ((Boolean) manager.callHook(PluginLoader.Hook.VEHICLE_COLLISION, cart, var1.entity)) {
                    return;
                }
                
                if (var1 instanceof OEntityLiving && !(var1 instanceof OEntityPlayer) && !(var1 instanceof OEntityIronGolem) && this.a == 0 && this.bp * this.bp + this.br * this.br > 0.01D && this.bg == null && var1.bh == null) {
                    var1.b((OEntity) this);
                }

                double var2 = var1.bm - this.bm;
                double var4 = var1.bo - this.bo;
                double var6 = var2 * var2 + var4 * var4;

                if (var6 >= 9.999999747378752E-5D) {
                    var6 = (double) OMathHelper.a(var6);
                    var2 /= var6;
                    var4 /= var6;
                    double var8 = 1.0D / var6;

                    if (var8 > 1.0D) {
                        var8 = 1.0D;
                    }

                    var2 *= var8;
                    var4 *= var8;
                    var2 *= 0.10000000149011612D;
                    var4 *= 0.10000000149011612D;
                    var2 *= (double) (1.0F - this.bR);
                    var4 *= (double) (1.0F - this.bR);
                    var2 *= 0.5D;
                    var4 *= 0.5D;
                    if (var1 instanceof OEntityMinecart) {
                        double var10 = var1.bm - this.bm;
                        double var12 = var1.bo - this.bo;
                        OVec3D var14 = OVec3D.b(var10, 0.0D, var12).b();
                        OVec3D var15 = OVec3D.b((double) OMathHelper.b(this.bs * 3.1415927F / 180.0F), 0.0D, (double) OMathHelper.a(this.bs * 3.1415927F / 180.0F)).b();
                        double var16 = Math.abs(var14.a(var15));

                        if (var16 < 0.800000011920929D) {
                            return;
                        }

                        double var18 = var1.bp + this.bp;
                        double var20 = var1.br + this.br;

                        if (((OEntityMinecart) var1).a == 2 && this.a != 2) {
                            this.bp *= 0.20000000298023224D;
                            this.br *= 0.20000000298023224D;
                            this.b_(var1.bp - var2, 0.0D, var1.br - var4);
                            var1.bp *= 0.949999988079071D;
                            var1.br *= 0.949999988079071D;
                        } else if (((OEntityMinecart) var1).a != 2 && this.a == 2) {
                            var1.bp *= 0.20000000298023224D;
                            var1.br *= 0.20000000298023224D;
                            var1.b_(this.bp + var2, 0.0D, this.br + var4);
                            this.bp *= 0.949999988079071D;
                            this.br *= 0.949999988079071D;
                        } else {
                            var18 /= 2.0D;
                            var20 /= 2.0D;
                            this.bp *= 0.20000000298023224D;
                            this.br *= 0.20000000298023224D;
                            this.b_(var18 - var2, 0.0D, var20 - var4);
                            var1.bp *= 0.20000000298023224D;
                            var1.br *= 0.20000000298023224D;
                            var1.b_(var18 + var2, 0.0D, var20 + var4);
                        }
                    } else {
                        this.b_(-var2, 0.0D, -var4);
                        var1.b_(var2 / 4.0D, 0.0D, var4 / 4.0D);
                    }
                }

            }
        }
    }

    public int c() {
        return 27;
    }

    public OItemStack g_(int var1) {
        return this.d[var1];
    }

    public OItemStack a(int var1, int var2) {
        if (this.d[var1] != null) {
            OItemStack var3;

            if (this.d[var1].a <= var2) {
                var3 = this.d[var1];
                this.d[var1] = null;
                return var3;
            } else {
                var3 = this.d[var1].a(var2);
                if (this.d[var1].a == 0) {
                    this.d[var1] = null;
                }

                return var3;
            }
        } else {
            return null;
        }
    }

    public OItemStack b(int var1) {
        if (this.d[var1] != null) {
            OItemStack var2 = this.d[var1];

            this.d[var1] = null;
            return var2;
        } else {
            return null;
        }
    }

    public void a(int var1, OItemStack var2) {
        this.d[var1] = var2;
        if (var2 != null && var2.a > this.a()) {
            var2.a = this.a();
        }

    }

    public String e() {
        return "container.minecart";
    }

    public int a() {
        return 64;
    }

    public void H_() {}

    public boolean b(OEntityPlayer var1) {
        // CanaryMod: Entering the cart
        manager.callHook(PluginLoader.Hook.VEHICLE_ENTERED, cart, var1.entity);
        
        if (this.a == 0) {
            if (this.bg != null && this.bg instanceof OEntityPlayer && this.bg != var1) {
                return true;
            }

            if (!this.bi.F) {
                var1.b((OEntity) this);
            }
        } else if (this.a == 1) {
            if (!this.bi.F) {
                var1.a((OIInventory) this);
            }
        } else if (this.a == 2) {
            OItemStack var2 = var1.k.d();

            if (var2 != null && var2.c == OItem.l.bP) {
                if (--var2.a == 0) {
                    var1.k.a(var1.k.c, (OItemStack) null);
                }

                this.e += 3600;
            }

            this.b = this.bm - var1.bm;
            this.c = this.bo - var1.bo;
        }

        return true;
    }

    public boolean a(OEntityPlayer var1) {
        return this.bE ? false : var1.j(this) <= 64.0D;
    }

    protected boolean k() {
        return (this.bY.a(16) & 1) != 0;
    }

    protected void a(boolean var1) {
        if (var1) {
            this.bY.b(16, Byte.valueOf((byte) (this.bY.a(16) | 1)));
        } else {
            this.bY.b(16, Byte.valueOf((byte) (this.bY.a(16) & -2)));
        }

    }

    public void f() {}

    public void g() {}

    public void c(int var1) {
        this.bY.b(19, Integer.valueOf(var1));
    }

    public int l() {
        return this.bY.c(19);
    }

    public void d(int var1) {
        this.bY.b(17, Integer.valueOf(var1));
    }

    public int m() {
        return this.bY.c(17);
    }

    public void e(int var1) {
        this.bY.b(18, Integer.valueOf(var1));
    }

    public int n() {
        return this.bY.c(18);
    }
    
    public OItemStack[] getContents() {
        return d;
    }

    public void setContents(OItemStack[] values) {
        d = values;
    }

    public OItemStack getContentsAt(int index) {
        return g_(index);
    }

    public void setContentsAt(int index, OItemStack value) {
        a(index, value);
    }

    public int getContentsSize() {
        return c();
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

}

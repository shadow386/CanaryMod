
public class OEntityEnderman extends OEntityMob {

    private static boolean[] b = new boolean[256];
    public boolean a = false;
    private int g = 0;
    private int h = 0;
    // CanaryMod Start
    Enderman entity = new Enderman(this);

    // CanaryMod End

    public OEntityEnderman(OWorld var1) {
        super(var1);
        this.ae = "/mob/enderman.png";
        this.bb = 0.2F;
        this.c = 7;
        this.b(0.6F, 2.9F);
        this.bP = 1.0F;
    }

    public int c() {
        return 40;
    }

    protected void b() {
        super.b();
        this.bY.a(16, new Byte((byte) 0));
        this.bY.a(17, new Byte((byte) 0));
    }

    public void b(ONBTTagCompound var1) {
        super.b(var1);
        var1.a("carried", (short) this.B());
        var1.a("carriedData", (short) this.C());
    }

    public void a(ONBTTagCompound var1) {
        super.a(var1);
        this.b(var1.e("carried"));
        this.c(var1.e("carriedData"));
    }

    protected OEntity k() {
        OEntityPlayer var1 = this.bi.b(this, 64.0D);

        if (var1 != null) {
            if (this.c(var1)) {
                if (this.h++ == 5) {
                    this.h = 0;
                    return var1;
                }
            } else {
                this.h = 0;
            }
        }

        return null;
    }

    public float a(float var1) {
        return super.a(var1);
    }

    private boolean c(OEntityPlayer var1) {
        OItemStack var2 = var1.k.b[3];

        if (var2 != null && var2.c == OBlock.bc.bO) {
            return false;
        } else {
            OVec3D var3 = var1.e(1.0F).b();
            OVec3D var4 = OVec3D.b(this.bm - var1.bm, this.bw.b + (double) (this.bH / 2.0F) - (var1.bn + (double) var1.y()), this.bo - var1.bo);
            double var5 = var4.c();

            var4 = var4.b();
            double var7 = var3.a(var4);

            return var7 > 1.0D - 0.025D / var5 ? var1.g(this) : false;
        }
    }

    public void d() {
        if (this.aJ()) {
            this.a(ODamageSource.f, 1);
        }

        this.a = this.d != null;
        this.bb = this.d != null ? 6.5F : 0.3F;
        int var1;

        if (!this.bi.I) {
            int var2;
            int var3;
            int var4;

            if (this.B() == 0) {
                if (this.bS.nextInt(20) == 0) {
                    var1 = OMathHelper.b(this.bm - 2.0D + this.bS.nextDouble() * 4.0D);
                    var2 = OMathHelper.b(this.bn + this.bS.nextDouble() * 3.0D);
                    var3 = OMathHelper.b(this.bo - 2.0D + this.bS.nextDouble() * 4.0D);
                    var4 = this.bi.a(var1, var2, var3);
                    if (b[var4]) {
                        // CanaryMod onEndermanPickup
                        if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.ENDERMAN_PICKUP, entity, new Block(var4, var1, var2, var3, this.bi.a(var1, var2, var3)))) {
                            this.b(this.bi.a(var1, var2, var3));
                            this.c(this.bi.c(var1, var2, var3));
                            this.bi.e(var1, var2, var3, 0);
                        }
                    }
                }
            } else if (this.bS.nextInt(2000) == 0) {
                var1 = OMathHelper.b(this.bm - 1.0D + this.bS.nextDouble() * 2.0D);
                var2 = OMathHelper.b(this.bn + this.bS.nextDouble() * 2.0D);
                var3 = OMathHelper.b(this.bo - 1.0D + this.bS.nextDouble() * 2.0D);
                var4 = this.bi.a(var1, var2, var3);
                int var5 = this.bi.a(var1, var2 - 1, var3);

                if (var4 == 0 && var5 > 0 && OBlock.m[var5].b()) {
                    // CanaryMod onEndermanDrop
                    if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.ENDERMAN_DROP, entity, new Block(var5, var1, var2, var3, this.bi.a(var1, var2, var3)))) {
                        this.bi.b(var1, var2, var3, this.B(), this.C());
                        this.b(0);
                    }
                }
            }
        }

        for (var1 = 0; var1 < 2; ++var1) {
            this.bi.a("portal", this.bm + (this.bS.nextDouble() - 0.5D) * (double) this.bG, this.bn + this.bS.nextDouble() * (double) this.bH - 0.25D, this.bo + (this.bS.nextDouble() - 0.5D) * (double) this.bG, (this.bS.nextDouble() - 0.5D) * 2.0D, -this.bS.nextDouble(), (this.bS.nextDouble() - 0.5D) * 2.0D);
        }

        if (this.bi.e() && !this.bi.I) {
            float var6 = this.a(1.0F);

            if (var6 > 0.5F && this.bi.j(OMathHelper.b(this.bm), OMathHelper.b(this.bn), OMathHelper.b(this.bo)) && this.bS.nextFloat() * 30.0F < (var6 - 0.4F) * 2.0F) {
                this.d = null;
                this.w_();
            }
        }

        if (this.aJ()) {
            this.d = null;
            this.w_();
        }

        this.aZ = false;
        if (this.d != null) {
            this.a(this.d, 100.0F, 100.0F);
        }

        if (!this.bi.I && this.aq()) {
            if (this.d != null) {
                if (this.d instanceof OEntityPlayer && this.c((OEntityPlayer) this.d)) {
                    this.aW = this.aX = 0.0F;
                    this.bb = 0.0F;
                    if (this.d.i(this) < 16.0D) {
                        this.w_();
                    }

                    this.g = 0;
                } else if (this.d.i(this) > 256.0D && this.g++ >= 30 && this.f(this.d)) {
                    this.g = 0;
                }
            } else {
                this.g = 0;
            }
        }

        super.d();
    }

    protected boolean w_() {
        double var1 = this.bm + (this.bS.nextDouble() - 0.5D) * 64.0D;
        double var3 = this.bn + (double) (this.bS.nextInt(64) - 32);
        double var5 = this.bo + (this.bS.nextDouble() - 0.5D) * 64.0D;

        return this.b(var1, var3, var5);
    }

    protected boolean f(OEntity var1) {
        OVec3D var2 = OVec3D.b(this.bm - var1.bm, this.bw.b + (double) (this.bH / 2.0F) - var1.bn + (double) var1.y(), this.bo - var1.bo);

        var2 = var2.b();
        double var3 = 16.0D;
        double var5 = this.bm + (this.bS.nextDouble() - 0.5D) * 8.0D - var2.a * var3;
        double var7 = this.bn + (double) (this.bS.nextInt(16) - 8) - var2.b * var3;
        double var9 = this.bo + (this.bS.nextDouble() - 0.5D) * 8.0D - var2.c * var3;

        return this.b(var5, var7, var9);
    }

    protected boolean b(double var1, double var3, double var5) {
        double var7 = this.bm;
        double var9 = this.bn;
        double var11 = this.bo;

        this.bm = var1;
        this.bn = var3;
        this.bo = var5;
        boolean var13 = false;
        int var14 = OMathHelper.b(this.bm);
        int var15 = OMathHelper.b(this.bn);
        int var16 = OMathHelper.b(this.bo);
        int var18;

        if (this.bi.g(var14, var15, var16)) {
            boolean var17 = false;

            while (!var17 && var15 > 0) {
                var18 = this.bi.a(var14, var15 - 1, var16);
                if (var18 != 0 && OBlock.m[var18].cb.c()) {
                    var17 = true;
                } else {
                    --this.bn;
                    --var15;
                }
            }

            if (var17) {
                this.c(this.bm, this.bn, this.bo);
                if (this.bi.a((OEntity) this, this.bw).size() == 0 && !this.bi.c(this.bw)) {
                    var13 = true;
                }
            }
        }

        if (!var13) {
            this.c(var7, var9, var11);
            return false;
        } else {
            short var30 = 128;

            for (var18 = 0; var18 < var30; ++var18) {
                double var19 = (double) var18 / ((double) var30 - 1.0D);
                float var21 = (this.bS.nextFloat() - 0.5F) * 0.2F;
                float var22 = (this.bS.nextFloat() - 0.5F) * 0.2F;
                float var23 = (this.bS.nextFloat() - 0.5F) * 0.2F;
                double var24 = var7 + (this.bm - var7) * var19 + (this.bS.nextDouble() - 0.5D) * (double) this.bG * 2.0D;
                double var26 = var9 + (this.bn - var9) * var19 + this.bS.nextDouble() * (double) this.bH;
                double var28 = var11 + (this.bo - var11) * var19 + (this.bS.nextDouble() - 0.5D) * (double) this.bG * 2.0D;

                this.bi.a("portal", var24, var26, var28, (double) var21, (double) var22, (double) var23);
            }

            this.bi.a(var7, var9, var11, "mob.endermen.portal", 1.0F, 1.0F);
            this.bi.a(this, "mob.endermen.portal", 1.0F, 1.0F);
            return true;
        }
    }

    protected String c_() {
        return "mob.endermen.idle";
    }

    protected String m() {
        return "mob.endermen.hit";
    }

    protected String n() {
        return "mob.endermen.death";
    }

    protected int e() {
        return OItem.bm.bN;
    }

    protected void a(boolean var1, int var2) {
        int var3 = this.e();

        if (var3 > 0) {
            int var4 = this.bS.nextInt(2 + var2);

            for (int var5 = 0; var5 < var4; ++var5) {
                this.b(var3, 1);
            }
        }

    }

    public void b(int var1) {
        this.bY.b(16, Byte.valueOf((byte) (var1 & 255)));
    }

    public int B() {
        return this.bY.a(16);
    }

    public void c(int var1) {
        this.bY.b(17, Byte.valueOf((byte) (var1 & 255)));
    }

    public int C() {
        return this.bY.a(17);
    }

    public boolean a(ODamageSource var1, int var2) {
        if (var1 instanceof OEntityDamageSourceIndirect) {
            for (int var3 = 0; var3 < 64; ++var3) {
                if (this.w_()) {
                    return true;
                }
            }

            return false;
        } else {
            return super.a(var1, var2);
        }
    }
   
    // CanaryMod start
    public static boolean canHoldItem(int blockID) {
        return b[blockID];
    }

    public static void setHoldable(int blockID, boolean holdable) {
        b[blockID] = holdable;
    }

    public static boolean getHoldable(int blockID) {
        return b[blockID];
    }

    // CanaryMod end

    static {
        b[OBlock.w.bO] = true;
        b[OBlock.x.bO] = true;
        b[OBlock.G.bO] = true;
        b[OBlock.H.bO] = true;
        b[OBlock.af.bO] = true;
        b[OBlock.ag.bO] = true;
        b[OBlock.ah.bO] = true;
        b[OBlock.ai.bO] = true;
        b[OBlock.ao.bO] = true;
        b[OBlock.aX.bO] = true;
        b[OBlock.aY.bO] = true;
        b[OBlock.bc.bO] = true;
        b[OBlock.bt.bO] = true;
        b[OBlock.bA.bO] = true;
    }
}

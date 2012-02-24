
public class OEntityGhast extends OEntityFlying implements OIMob {

    public int a = 0;
    public double b;
    public double c;
    public double d;
    private OEntity g = null;
    private int h = 0;
    public int e = 0;
    public int f = 0;

    public OEntityGhast(OWorld var1) {
        super(var1);
        this.ae = "/mob/ghast.png";
        this.b(4.0F, 4.0F);
        this.bX = true;
        this.aA = 5;
    }

    public boolean a(ODamageSource var1, int var2) {
        if ("fireball".equals(var1.l()) && var1.a() instanceof OEntityPlayer) {
            super.a(var1, 1000);
            ((OEntityPlayer) var1.a()).a((OStatBase) OAchievementList.y);
            return true;
        } else {
            return super.a(var1, var2);
        }
    }

    protected void b() {
        super.b();
        this.bY.a(16, Byte.valueOf((byte) 0));
    }

    public int c() {
        return 10;
    }

    public void y_() {
        super.y_();
        byte var1 = this.bY.a(16);

        this.ae = var1 == 1 ? "/mob/ghast_fire.png" : "/mob/ghast.png";
    }

    protected void m_() {
        if (!this.bi.I && this.bi.v == 0) {
            this.T();
        }

        this.au();
        this.e = this.f;
        double var1 = this.b - this.bm;
        double var3 = this.c - this.bn;
        double var5 = this.d - this.bo;
        double var7 = (double) OMathHelper.a(var1 * var1 + var3 * var3 + var5 * var5);

        if (var7 < 1.0D || var7 > 60.0D) {
            this.b = this.bm + (double) ((this.bS.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.c = this.bn + (double) ((this.bS.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.d = this.bo + (double) ((this.bS.nextFloat() * 2.0F - 1.0F) * 16.0F);
        }

        if (this.a-- <= 0) {
            this.a += this.bS.nextInt(5) + 2;
            if (this.a(this.b, this.c, this.d, var7)) {
                this.bp += var1 / var7 * 0.1D;
                this.bq += var3 / var7 * 0.1D;
                this.br += var5 / var7 * 0.1D;
            } else {
                this.b = this.bm;
                this.c = this.bn;
                this.d = this.bo;
            }
        }

        if (this.g != null && this.g.bE) {
            this.g = null;
        }

        if (this.g == null || this.h-- <= 0) {
            OEntityPlayer var8 = this.bi.b(this, 100.0D);

            // CanaryMod: MOB_TARGET Hook for ghasts.
            if (var8 != null && !(Boolean) etc.getLoader().callHook(PluginLoader.Hook.MOB_TARGET, (Player) var8.entity.getPlayer(), entity)) {
                this.g = var8;
            }
            if (this.g != null) {
                this.h = 20;
            }
        }

        double var9 = 64.0D;

        if (this.g != null && this.g.i(this) < var9 * var9) {
            double var11 = this.g.bm - this.bm;
            double var13 = this.g.bw.b + (double) (this.g.bH / 2.0F) - (this.bn + (double) (this.bH / 2.0F));
            double var15 = this.g.bo - this.bo;

            this.V = this.bs = -((float) Math.atan2(var11, var15)) * 180.0F / 3.1415927F;
            if (this.g(this.g)) {
                if (this.f == 10) {
                    this.bi.a((OEntityPlayer) null, 1007, (int) this.bm, (int) this.bn, (int) this.bo, 0);
                }

                ++this.f;
                if (this.f == 20) {
                    this.bi.a((OEntityPlayer) null, 1008, (int) this.bm, (int) this.bn, (int) this.bo, 0);
                    OEntityFireball var17 = new OEntityFireball(this.bi, this, var11, var13, var15);
                    double var18 = 4.0D;
                    OVec3D var20 = this.e(1.0F);

                    var17.bm = this.bm + var20.a * var18;
                    var17.bn = this.bn + (double) (this.bH / 2.0F) + 0.5D;
                    var17.bo = this.bo + var20.c * var18;
                    this.bi.b((OEntity) var17);
                    this.f = -40;
                }
            } else if (this.f > 0) {
                --this.f;
            }
        } else {
            this.V = this.bs = -((float) Math.atan2(this.bp, this.br)) * 180.0F / 3.1415927F;
            if (this.f > 0) {
                --this.f;
            }
        }

        if (!this.bi.I) {
            byte var21 = this.bY.a(16);
            byte var22 = (byte) (this.f > 10 ? 1 : 0);

            if (var21 != var22) {
                this.bY.b(16, Byte.valueOf(var22));
            }
        }

    }

    private boolean a(double var1, double var3, double var5, double var7) {
        double var9 = (this.b - this.bm) / var7;
        double var11 = (this.c - this.bn) / var7;
        double var13 = (this.d - this.bo) / var7;
        OAxisAlignedBB var15 = this.bw.b();

        for (int var16 = 1; (double) var16 < var7; ++var16) {
            var15.d(var9, var11, var13);
            if (this.bi.a((OEntity) this, var15).size() > 0) {
                return false;
            }
        }

        return true;
    }

    protected String c_() {
        return "mob.ghast.moan";
    }

    protected String m() {
        return "mob.ghast.scream";
    }

    protected String n() {
        return "mob.ghast.death";
    }

    protected int e() {
        return OItem.L.bN;
    }

    protected void a(boolean var1, int var2) {
        int var3 = this.bS.nextInt(2) + this.bS.nextInt(1 + var2);

        int var4;

        for (var4 = 0; var4 < var3; ++var4) {
            this.b(OItem.bo.bN, 1);
        }

        var3 = this.bS.nextInt(3) + this.bS.nextInt(1 + var2);

        for (var4 = 0; var4 < var3; ++var4) {
            this.b(OItem.L.bN, 1);
        }

    }

    protected float o() {
        return 10.0F;
    }

    public boolean g() {
        return this.bS.nextInt(20) == 0 && super.g() && this.bi.v > 0;
    }

    public int p() {
        return 1;
    }
    
    public void setTarget(OEntity var1) {
        this.g = var1;
    }
    
    public OEntity getTarget() {
        return this.g;
    }
}

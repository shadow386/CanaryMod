import java.util.Iterator;
import java.util.List;


public class OEntityWolf extends OEntityAnimal {

    private boolean a = false;
    private float b;
    private float c;
    private boolean g;
    private boolean h;
    private float i;
    private float j;

    public OEntityWolf(OWorld var1) {
        super(var1);
        this.ae = "/mob/wolf.png";
        this.b(0.8F, 0.8F);
        this.bb = 1.1F;
    }

    public int c() {
        return this.C() ? 20 : 8;
    }

    protected void b() {
        super.b();
        this.bY.a(16, Byte.valueOf((byte) 0));
        this.bY.a(17, "");
        this.bY.a(18, new Integer(this.ap()));
    }

    protected boolean g_() {
        return false;
    }

    public void b(ONBTTagCompound var1) {
        super.b(var1);
        var1.a("Angry", this.B());
        var1.a("Sitting", this.v_());
        if (this.z() == null) {
            var1.a("Owner", "");
        } else {
            var1.a("Owner", this.z());
        }

    }

    public void a(ONBTTagCompound var1) {
        super.a(var1);
        this.c(var1.n("Angry"));
        this.b(var1.n("Sitting"));
        String var2 = var1.j("Owner");

        if (var2.length() > 0) {
            this.a(var2);
            this.d(true);
        }

    }

    protected boolean d_() {
        return this.B();
    }

    protected String c_() {
        return this.B() ? "mob.wolf.growl" : (this.bS.nextInt(3) == 0 ? (this.C() && this.bY.c(18) < 10 ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
    }

    protected String m() {
        return "mob.wolf.hurt";
    }

    protected String n() {
        return "mob.wolf.death";
    }

    protected float o() {
        return 0.4F;
    }

    protected int e() {
        return -1;
    }

    protected void m_() {
        super.m_();
        if (!this.e && !this.E() && this.C() && this.bh == null) {
            OEntityPlayer var3 = this.bi.a(this.z());

            if (var3 != null) {
                float var2 = var3.h(this);

                if (var2 > 5.0F) {
                    this.c(var3, var2);
                }
            } else if (!this.aK()) {
                this.b(true);
            }
        } else if (this.d == null && !this.E() && !this.C() && this.bi.w.nextInt(100) == 0) {
            List var1 = this.bi.a(OEntitySheep.class, OAxisAlignedBB.b(this.bm, this.bn, this.bo, this.bm + 1.0D, this.bn + 1.0D, this.bo + 1.0D).b(16.0D, 4.0D, 16.0D));

            if (!var1.isEmpty()) {
                this.e((OEntity) var1.get(this.bi.w.nextInt(var1.size())));
            }
        }

        if (this.aK()) {
            this.b(false);
        }

        if (!this.bi.I) {
            this.bY.b(18, Integer.valueOf(this.ap()));
        }

    }

    public void d() {
        super.d();
        this.a = false;
        if (this.aw() && !this.E() && !this.B()) {
            OEntity var1 = this.ax();

            if (var1 instanceof OEntityPlayer) {
                OEntityPlayer var2 = (OEntityPlayer) var1;
                OItemStack var3 = var2.k.d();

                if (var3 != null) {
                    if (!this.C() && var3.c == OItem.aW.bN) {
                        this.a = true;
                    } else if (this.C() && OItem.d[var3.c] instanceof OItemFood) {
                        this.a = ((OItemFood) OItem.d[var3.c]).q();
                    }
                }
            }
        }

        if (!this.bi.I && this.g && !this.h && !this.E() && this.bx) {
            this.h = true;
            this.i = 0.0F;
            this.j = 0.0F;
            this.bi.a(this, (byte) 8);
        }

    }

    public void y_() {
        super.y_();
        this.c = this.b;
        if (this.a) {
            this.b += (1.0F - this.b) * 0.4F;
        } else {
            this.b += (0.0F - this.b) * 0.4F;
        }

        if (this.a) {
            this.bc = 10;
        }

        if (this.aJ()) {
            this.g = true;
            this.h = false;
            this.i = 0.0F;
            this.j = 0.0F;
        } else if ((this.g || this.h) && this.h) {
            if (this.i == 0.0F) {
                this.bi.a(this, "mob.wolf.shake", this.o(), (this.bS.nextFloat() - this.bS.nextFloat()) * 0.2F + 1.0F);
            }

            this.j = this.i;
            this.i += 0.05F;
            if (this.j >= 2.0F) {
                this.g = false;
                this.h = false;
                this.j = 0.0F;
                this.i = 0.0F;
            }

            if (this.i > 0.4F) {
                float var1 = (float) this.bw.b;
                int var2 = (int) (OMathHelper.a((this.i - 0.4F) * 3.1415927F) * 7.0F);

                for (int var3 = 0; var3 < var2; ++var3) {
                    float var4 = (this.bS.nextFloat() * 2.0F - 1.0F) * this.bG * 0.5F;
                    float var5 = (this.bS.nextFloat() * 2.0F - 1.0F) * this.bG * 0.5F;

                    this.bi.a("splash", this.bm + (double) var4, (double) (var1 + 0.8F), this.bo + (double) var5, this.bp, this.bq, this.br);
                }
            }
        }

    }

    public float y() {
        return this.bH * 0.8F;
    }

    public int x() {
        return this.v_() ? 20 : super.x();
    }

    private void c(OEntity var1, float var2) {
        OPathEntity var3 = this.bi.a(this, var1, 16.0F);

        if (var3 == null && var2 > 12.0F) {
            int var4 = OMathHelper.b(var1.bm) - 2;
            int var5 = OMathHelper.b(var1.bo) - 2;
            int var6 = OMathHelper.b(var1.bw.b);

            for (int var7 = 0; var7 <= 4; ++var7) {
                for (int var8 = 0; var8 <= 4; ++var8) {
                    if ((var7 < 1 || var8 < 1 || var7 > 3 || var8 > 3) && this.bi.e(var4 + var7, var6 - 1, var5 + var8) && !this.bi.e(var4 + var7, var6, var5 + var8) && !this.bi.e(var4 + var7, var6 + 1, var5 + var8)) {
                        this.c((double) ((float) (var4 + var7) + 0.5F), (double) var6, (double) ((float) (var5 + var8) + 0.5F), this.bs, this.bt);
                        return;
                    }
                }
            }
        } else {
            this.a(var3);
        }

    }

    protected boolean v() {
        return this.v_() || this.h;
    }

    public boolean a(ODamageSource var1, int var2) {
        OEntity var3 = var1.a();

        this.b(false);
        if (var3 != null && !(var3 instanceof OEntityPlayer) && !(var3 instanceof OEntityArrow)) {
            var2 = (var2 + 1) / 2;
        }

        if (!super.a(var1, var2)) {
            return false;
        } else {
            if (!this.C() && !this.B()) {
                if (var3 instanceof OEntityPlayer) {
                    this.c(true);
                    this.d = var3;
                }

                if (var3 instanceof OEntityArrow && ((OEntityArrow) var3).c != null) {
                    var3 = ((OEntityArrow) var3).c;
                }

                if (var3 instanceof OEntityLiving) {
                    List var4 = this.bi.a(OEntityWolf.class, OAxisAlignedBB.b(this.bm, this.bn, this.bo, this.bm + 1.0D, this.bn + 1.0D, this.bo + 1.0D).b(16.0D, 4.0D, 16.0D));
                    Iterator var5 = var4.iterator();

                    while (var5.hasNext()) {
                        OEntity var6 = (OEntity) var5.next();
                        OEntityWolf var7 = (OEntityWolf) var6;

                        if (!var7.C() && var7.d == null) {
                            var7.d = var3;
                            if (var3 instanceof OEntityPlayer) {
                                var7.c(true);
                            }
                        }
                    }
                }
            } else if (var3 != this && var3 != null) {
                if (this.C() && var3 instanceof OEntityPlayer && ((OEntityPlayer) var3).v.equalsIgnoreCase(this.z())) {
                    return true;
                }

                this.d = var3;
            }

            return true;
        }
    }

    protected OEntity k() {
        return this.B() ? this.bi.a(this, 16.0D) : null;
    }

    protected void a(OEntity var1, float var2) {
        if (var2 > 2.0F && var2 < 6.0F && this.bS.nextInt(10) == 0) {
            if (this.bx) {
                double var3 = var1.bm - this.bm;
                double var5 = var1.bo - this.bo;
                float var7 = OMathHelper.a(var3 * var3 + var5 * var5);

                this.bp = var3 / (double) var7 * 0.5D * 0.800000011920929D + this.bp * 0.20000000298023224D;
                this.br = var5 / (double) var7 * 0.5D * 0.800000011920929D + this.br * 0.20000000298023224D;
                this.bq = 0.4000000059604645D;
            }
        } else if ((double) var2 < 1.5D && var1.bw.e > this.bw.b && var1.bw.b < this.bw.e) {
            this.aw = 20;
            byte var8 = 2;

            if (this.C()) {
                var8 = 4;
            }

            var1.a(ODamageSource.a((OEntityLiving) this), var8);
        }

    }

    public boolean b(OEntityPlayer var1) {
        OItemStack var2 = var1.k.d();

        if (!this.C()) {
            if (var2 != null && var2.c == OItem.aW.bN && !this.B()) {
                --var2.a;
                if (var2.a <= 0) {
                    var1.k.a(var1.k.c, (OItemStack) null);
                }

                if (!this.bi.I) {
                    // CanaryMod hook: onTame
                    // randomize the tame result. if its 0 - tame success.
                    int tameResult = this.bS.nextInt(3);
                    // Call hook
                    PluginLoader.HookResult res = (PluginLoader.HookResult) manager.callHook(PluginLoader.Hook.TAME, manager.getServer().getPlayer(var1.v), new Mob(this), tameResult == 0);

                    // if taming succeeded normally (tameResult == 0) or plugin hook result is allow (force taming)
                    if (tameResult == 0 && res == PluginLoader.HookResult.DEFAULT_ACTION || res == PluginLoader.HookResult.ALLOW_ACTION) {
                        this.d(true);
                        this.a((OPathEntity) null);
                        this.b(true);
                        this.i(20);
                        this.a(var1.v);
                        this.a(true);
                        this.bi.a(this, (byte) 7);
                    } else {
                        this.a(false);
                        this.bi.a(this, (byte) 6);
                    }
                }

                return true;
            }
        } else {
            if (var2 != null && OItem.d[var2.c] instanceof OItemFood) {
                OItemFood var3 = (OItemFood) OItem.d[var2.c];

                if (var3.q() && this.bY.c(18) < 20) {
                    --var2.a;
                    this.d(var3.o());
                    if (var2.a <= 0) {
                        var1.k.a(var1.k.c, (OItemStack) null);
                    }

                    return true;
                }
            }

            if (var1.v.equalsIgnoreCase(this.z())) {
                if (!this.bi.I) {
                    this.b(!this.v_());
                    this.aZ = false;
                    this.a((OPathEntity) null);
                }

                return true;
            }
        }

        return super.b(var1);
    }

    void a(boolean var1) {
        String var2 = "heart";

        if (!var1) {
            var2 = "smoke";
        }

        for (int var3 = 0; var3 < 7; ++var3) {
            double var4 = this.bS.nextGaussian() * 0.02D;
            double var6 = this.bS.nextGaussian() * 0.02D;
            double var8 = this.bS.nextGaussian() * 0.02D;

            this.bi.a(var2, this.bm + (double) (this.bS.nextFloat() * this.bG * 2.0F) - (double) this.bG, this.bn + 0.5D + (double) (this.bS.nextFloat() * this.bH), this.bo + (double) (this.bS.nextFloat() * this.bG * 2.0F) - (double) this.bG, var4, var6, var8);
        }

    }

    public int p() {
        return 8;
    }

    public String z() {
        return this.bY.d(17);
    }

    public void a(String var1) {
        this.bY.b(17, var1);
    }

    public boolean v_() {
        return (this.bY.a(16) & 1) != 0;
    }

    public void b(boolean var1) {
        byte var2 = this.bY.a(16);

        if (var1) {
            this.bY.b(16, Byte.valueOf((byte) (var2 | 1)));
        } else {
            this.bY.b(16, Byte.valueOf((byte) (var2 & -2)));
        }

    }

    public boolean B() {
        return (this.bY.a(16) & 2) != 0;
    }

    public void c(boolean var1) {
        byte var2 = this.bY.a(16);

        if (var1) {
            this.bY.b(16, Byte.valueOf((byte) (var2 | 2)));
        } else {
            this.bY.b(16, Byte.valueOf((byte) (var2 & -3)));
        }

    }

    public boolean C() {
        return (this.bY.a(16) & 4) != 0;
    }

    public void d(boolean var1) {
        byte var2 = this.bY.a(16);

        if (var1) {
            this.bY.b(16, Byte.valueOf((byte) (var2 | 4)));
        } else {
            this.bY.b(16, Byte.valueOf((byte) (var2 & -5)));
        }

    }

    protected OEntityAnimal a(OEntityAnimal var1) {
        return new OEntityWolf(this.bi);
    }
}

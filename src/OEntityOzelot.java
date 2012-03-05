
public class OEntityOzelot extends OEntityTamable {

    private OEntityAITempt b;

    public OEntityOzelot(OWorld var1) {
        super(var1);
        this.ae = "/mob/ozelot.png";
        this.b(0.6F, 0.8F);
        this.ak().a(true);
        this.aL.a(1, new OEntityAISwimming(this));
        this.aL.a(2, this.a);
        this.aL.a(3, this.b = new OEntityAITempt(this, 0.18F, OItem.aT.bP, true));
        this.aL.a(4, new OEntityAIAvoidEntity(this, OEntityPlayer.class, 16.0F, 0.23F, 0.4F));
        this.aL.a(5, new OEntityAIFollowOwner(this, 0.3F, 10.0F, 5.0F));
        this.aL.a(6, new OEntityAILeapAtTarget(this, 0.3F));
        this.aL.a(7, new OEntityAIOcelotAttack(this));
        this.aL.a(8, new OEntityAIMate(this, 0.23F));
        this.aL.a(9, new OEntityAIWander(this, 0.23F));
        this.aL.a(10, new OEntityAIWatchClosest(this, OEntityPlayer.class, 10.0F));
        this.aM.a(1, new OEntityAITargetNonTamed(this, OEntityChicken.class, 14.0F, 750, false));
    }

    protected void b() {
        super.b();
        this.bY.a(18, Byte.valueOf((byte) 0));
    }

    public void g() {
        if (!this.ai().a()) {
            this.g(false);
            this.h(false);
        } else {
            float var1 = this.ai().b();

            if (var1 == 0.18F) {
                this.g(true);
                this.h(false);
            } else if (var1 == 0.4F) {
                this.g(false);
                this.h(true);
            } else {
                this.g(false);
                this.h(false);
            }
        }

    }

    protected boolean n() {
        return !this.u_();
    }

    public boolean c_() {
        return true;
    }

    public int d() {
        return 10;
    }

    protected void a(float var1) {}

    public void b(ONBTTagCompound var1) {
        super.b(var1);
        var1.a("CatType", this.r());
    }

    public void a(ONBTTagCompound var1) {
        super.a(var1);
        this.c_(var1.f("CatType"));
    }

    protected String i() {
        return this.u_() ? (this.r_() ? "mob.cat.purr" : (this.bS.nextInt(4) == 0 ? "mob.cat.purreow" : "mob.cat.meow")) : "";
    }

    protected String j() {
        return "mob.cat.hitt";
    }

    protected String k() {
        return "mob.cat.hitt";
    }

    protected float p() {
        return 0.4F;
    }

    protected int f() {
        return OItem.aE.bP;
    }

    public boolean a(OEntity var1) {
        return var1.a(ODamageSource.a((OEntityLiving) this), 3);
    }

    public boolean a(ODamageSource var1, int var2) {
        this.a.a(false);
        return super.a(var1, var2);
    }

    protected void a(boolean var1, int var2) {}

    public boolean b(OEntityPlayer var1) {
        OItemStack var2 = var1.k.d();

        if (!this.u_()) {
            if (this.b.f() && var2 != null && var2.c == OItem.aT.bP && var1.j(this) < 9.0D) {
                --var2.a;
                if (var2.a <= 0) {
                    var1.k.a(var1.k.c, (OItemStack) null);
                }

                if (!this.bi.F) {
                    // CanaryMod hook: onTame
                    // randomize the tame result. if its 0 - tame success.
                    int tameResult = this.bS.nextInt(3);
                    // Call hook
                    PluginLoader.HookResult res = (PluginLoader.HookResult) manager.callHook(PluginLoader.Hook.TAME, manager.getServer().getPlayer(var1.v), new Mob(this), tameResult == 0);

                    // if taming succeeded normally (tameResult == 0) or plugin hook result is allow (force taming)
                    if (tameResult == 0 && res == PluginLoader.HookResult.DEFAULT_ACTION || res == PluginLoader.HookResult.ALLOW_ACTION) {
                        this.b(true);
                        this.c_(1 + this.bi.r.nextInt(3));
                        this.a(var1.v);
                        this.a(true);
                        this.a.a(true);
                        this.bi.a(this, (byte) 7);
                    } else {
                        this.a(false);
                        this.bi.a(this, (byte) 6);
                    }
                }
            }

            return true;
        } else {
            if (var1.v.equalsIgnoreCase(this.A()) && !this.bi.F && !this.a(var2)) {
                this.a.a(!this.v_());
            }

            return super.b(var1);
        }
    }

    public OEntityAnimal a(OEntityAnimal var1) {
        OEntityOzelot var2 = new OEntityOzelot(this.bi);

        if (this.u_()) {
            var2.a(this.A());
            var2.b(true);
            var2.c_(this.r());
        }

        return var2;
    }

    public boolean a(OItemStack var1) {
        return var1 != null && var1.c == OItem.aT.bP;
    }

    public boolean b(OEntityAnimal var1) {
        if (var1 == this) {
            return false;
        } else if (!this.u_()) {
            return false;
        } else if (!(var1 instanceof OEntityOzelot)) {
            return false;
        } else {
            OEntityOzelot var2 = (OEntityOzelot) var1;

            return !var2.u_() ? false : this.r_() && var2.r_();
        }
    }

    public int r() {
        return this.bY.a(18);
    }

    public void c_(int var1) {
        this.bY.b(18, Byte.valueOf((byte) var1));
    }

    public boolean l() {
        if (this.bi.r.nextInt(3) == 0) {
            return false;
        } else {
            if (this.bi.a(this.bw) && this.bi.a((OEntity) this, this.bw).size() == 0 && !this.bi.c(this.bw)) {
                int var1 = OMathHelper.b(this.bm);
                int var2 = OMathHelper.b(this.bw.b);
                int var3 = OMathHelper.b(this.bo);

                if (var2 < 63) {
                    return false;
                }

                int var4 = this.bi.a(var1, var2 - 1, var3);

                if (var4 == OBlock.u.bO || var4 == OBlock.K.bO) {
                    return true;
                }
            }

            return false;
        }
    }

    public String s() {
        return this.u_() ? "entity.Cat.name" : super.s();
    }
}

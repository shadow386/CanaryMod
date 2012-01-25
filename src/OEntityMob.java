
public abstract class OEntityMob extends OEntityCreature implements OIMob {

    protected int c = 2;
    
    // CanaryMod start
    protected LivingEntity entity = new LivingEntity(this);
    // CanaryMod end
    
    public OEntityMob(OWorld var1) {
        super(var1);
        this.aA = 5;
    }

    public void d() {
        float var1 = this.a(1.0F);

        if (var1 > 0.5F) {
            this.aV += 2;
        }

        super.d();
    }

    public void y_() {
        super.y_();
        if (!this.bi.I && this.bi.v == 0) {
            this.T();
        }

    }

    protected OEntity k() {
        OEntityPlayer var1 = this.bi.b(this, 16.0D);

        return var1 != null && this.g(var1) && !(Boolean) etc.getLoader().callHook(PluginLoader.Hook.MOB_TARGET, (Player) var1.entity.getPlayer(), entity) ? null : var1; // CanaryMod: MOB_TARGET hook
    }

    public boolean a(ODamageSource var1, int var2) {
        if (super.a(var1, var2)) {
            OEntity var3 = var1.a();

            if (this.bg != var3 && this.bh != var3) {
                if (var3 != this) {
                    // CanaryMod start - MOB_TARGET hook
                    if (var3 instanceof OEntityPlayer && !(Boolean) etc.getLoader().callHook(PluginLoader.Hook.MOB_TARGET, (Player) var3.entity.getPlayer(), entity)) {
                        this.d = var3;
                        this.aI = var3 instanceof OEntityLiving ? (OEntityLiving) var3 : null;
                    }
                    // CanaryMod end
                }

                return true;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean d(OEntity var1) {
        int var2 = this.c;

        if (this.a(OPotion.g)) {
            var2 += 3 << this.b(OPotion.g).c();
        }

        if (this.a(OPotion.t)) {
            var2 -= 2 << this.b(OPotion.t).c();
        }

        return var1.a(ODamageSource.a((OEntityLiving) this), var2);
    }

    protected void a(OEntity var1, float var2) {
        if (this.aw <= 0 && var2 < 2.0F && var1.bw.e > this.bw.b && var1.bw.b < this.bw.e) {
            this.aw = 20;
            this.d(var1);
        }

    }

    public float a(int var1, int var2, int var3) {
        return 0.5F - this.bi.m(var1, var2, var3);
    }

    public void b(ONBTTagCompound var1) {
        super.b(var1);
    }

    public void a(ONBTTagCompound var1) {
        super.a(var1);
    }

    protected boolean z() {
        int var1 = OMathHelper.b(this.bm);
        int var2 = OMathHelper.b(this.bw.b);
        int var3 = OMathHelper.b(this.bo);

        if (this.bi.a(OEnumSkyBlock.a, var1, var2, var3) > this.bS.nextInt(32)) {
            return false;
        } else {
            int var4 = this.bi.l(var1, var2, var3);

            if (this.bi.v()) {
                int var5 = this.bi.k;

                this.bi.k = 10;
                var4 = this.bi.l(var1, var2, var3);
                this.bi.k = var5;
            }

            return var4 <= this.bS.nextInt(8);
        }
    }

    public boolean g() {
        return this.z() && super.g();
    }
}

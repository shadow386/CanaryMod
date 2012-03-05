
public class OEntityItem extends OEntity {

    public OItemStack a;
    public int b = 0;
    public int c;
    private int e = 5;
    public float d = (float) (Math.random() * 3.141592653589793D * 2.0D);
    // CanaryMod Start
    ItemEntity item = new ItemEntity(this);

    // CanaryMod End

    public OEntityItem(OWorld var1, double var2, double var4, double var6, OItemStack var8) {
        super(var1);
        this.b(0.25F, 0.25F);
        this.bF = this.bH / 2.0F;
        this.c(var2, var4, var6);
        this.a = var8;
        this.bs = (float) (Math.random() * 360.0D);
        this.bp = (double) ((float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D));
        this.bq = 0.20000000298023224D;
        this.br = (double) ((float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D));
    }

    protected boolean g_() {
        return false;
    }

    public OEntityItem(OWorld var1) {
        super(var1);
        this.b(0.25F, 0.25F);
        this.bF = this.bH / 2.0F;
    }

    protected void b() {}

    public void G_() {
        super.G_();
        if (this.c > 0) {
            --this.c;
        }

        this.bj = this.bm;
        this.bk = this.bn;
        this.bl = this.bo;
        this.bq -= 0.03999999910593033D;
        if (this.bi.d(OMathHelper.b(this.bm), OMathHelper.b(this.bn), OMathHelper.b(this.bo)) == OMaterial.h) {
            this.bq = 0.20000000298023224D;
            this.bp = (double) ((this.bS.nextFloat() - this.bS.nextFloat()) * 0.2F);
            this.br = (double) ((this.bS.nextFloat() - this.bS.nextFloat()) * 0.2F);
            this.bi.a(this, "random.fizz", 0.4F, 2.0F + this.bS.nextFloat() * 0.4F);
        }

        this.g(this.bm, (this.bw.b + this.bw.e) / 2.0D, this.bo);
        this.a(this.bp, this.bq, this.br);
        float var1 = 0.98F;

        if (this.bx) {
            var1 = 0.58800006F;
            int var2 = this.bi.a(OMathHelper.b(this.bm), OMathHelper.b(this.bw.b) - 1, OMathHelper.b(this.bo));

            if (var2 > 0) {
                var1 = OBlock.m[var2].ce * 0.98F;
            }
        }

        this.bp *= (double) var1;
        this.bq *= 0.9800000190734863D;
        this.br *= (double) var1;
        if (this.bx) {
            this.bq *= -0.5D;
        }

        ++this.b;
        if (this.b >= 6000) {
            // CanaryMod onEntityDespawn
            if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.ENTITY_DESPAWN, item)) {
                this.W();
            } else {
                this.b = 0;
            }
        }

    }

    public void k() {
        this.b = 4800;
    }

    public boolean h_() {
        return this.bi.a(this.bw, OMaterial.g, this);
    }

    protected void a(int var1) {
        this.a(ODamageSource.b, var1);
    }

    public boolean a(ODamageSource var1, int var2) {
        this.aV();
        this.e -= var2;
        if (this.e <= 0) {
            this.W();
        }

        return false;
    }

    public void b(ONBTTagCompound var1) {
        var1.a("Health", (short) ((byte) this.e));
        var1.a("Age", (short) this.b);
        var1.a("Item", this.a.b(new ONBTTagCompound()));
    }

    public void a(ONBTTagCompound var1) {
        this.e = var1.e("Health") & 255;
        this.b = var1.e("Age");
        ONBTTagCompound var2 = var1.m("Item");

        this.a = OItemStack.a(var2);
        if (this.a == null) {
            this.W();
        }

    }

    public void a_(OEntityPlayer var1) {
        if (!this.bi.F) {
            int var2 = this.a.a;

            // CanaryMod: First simulate the pickup and call the hooks
            if (this.c == 0 && var1.k.canPickup(this)) {
                if (var1.k.a(this.a)) {
                    if (this.a.c == OBlock.J.bO) {
                        var1.a((OStatBase) OAchievementList.g);
                    }

                    if (this.a.c == OItem.aE.bP) {
                        var1.a((OStatBase) OAchievementList.t);
                    }

                    if (this.a.c == OItem.m.bP) {
                        var1.a((OStatBase) OAchievementList.w);
                    }

                    if (this.a.c == OItem.bn.bP) {
                        var1.a((OStatBase) OAchievementList.z);
                    }

                    this.bi.a(this, "random.pop", 0.2F, ((this.bS.nextFloat() - this.bS.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    var1.a((OEntity) this, var2);
                    if (this.a.a <= 0) {
                        this.W();
                    }
                }
            }

        }
    }

    public String s() {
        return OStatCollector.a("item." + this.a.k());
    }

    public boolean k_() {
        return false;
    }
}

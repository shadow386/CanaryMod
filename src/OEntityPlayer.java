import java.util.Iterator;
import java.util.List;


public abstract class OEntityPlayer extends OEntityLiving {

    public OInventoryPlayer k = new OInventoryPlayer(this);
    public OContainer l;
    public OContainer m;
    protected OFoodStats n = new OFoodStats(this);
    protected int o = 0;
    public byte p = 0;
    public int q = 0;
    public float r;
    public float s;
    public boolean t = false;
    public int u = 0;
    public String v;
    public int w;
    public int x = 0;
    public double y;
    public double z;
    public double A;
    public double B;
    public double C;
    public double D;
    protected boolean E;
    public OChunkCoordinates F;
    private int a;
    public float G;
    public float H;
    private OChunkCoordinates b;
    private OChunkCoordinates c;
    public int I = 20;
    protected boolean J = false;
    public float K;
    public OPlayerCapabilities L = new OPlayerCapabilities();
    public int M;
    public int N;
    public float O;
    private OItemStack d;
    private int e;
    protected float P = 0.1F;
    protected float Q = 0.02F;
    public OEntityFishHook R = null;
    // CanaryMod start
    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    HumanEntity entity = new HumanEntity(this);

    // CanaryMod end

    public OEntityPlayer(OWorld var1) {
        super(var1);
        this.l = new OContainerPlayer(this.k, !var1.F);
        this.m = this.l;
        this.bF = 1.62F;
        OChunkCoordinates var2 = var1.p();

        this.c((double) var2.a + 0.5D, (double) (var2.b + 1), (double) var2.c + 0.5D, 0.0F, 0.0F);
        this.ah = "humanoid";
        this.ag = 180.0F;
        this.bU = 20;
        this.ae = "/mob/char.png";
    }

    public int d() {
        return 20;
    }

    protected void b() {
        super.b();
        this.bY.a(16, Byte.valueOf((byte) 0));
        this.bY.a(17, Byte.valueOf((byte) 0));
    }

    public boolean L() {
        return this.d != null;
    }

    public void M() {
        if (this.d != null) {
            this.d.b(this.bi, this, this.e);
        }

        this.N();
    }

    public void N() {
        this.d = null;
        this.e = 0;
        if (!this.bi.F) {
            this.i(false);
        }

    }

    public boolean O() {
        return this.L() && OItem.d[this.d.c].d(this.d) == OEnumAction.d;
    }

    public void G_() {
        if (this.d != null) {
            OItemStack var1 = this.k.d();

            if (var1 != this.d) {
                this.N();
            } else {
                if (this.e <= 25 && this.e % 4 == 0) {
                    this.b(var1, 5);
                }

                if (--this.e == 0 && !this.bi.F) {
                    this.K();
                }
            }
        }

        if (this.x > 0) {
            --this.x;
        }

        if (this.Y()) {
            ++this.a;
            if (this.a > 100) {
                this.a = 100;
            }

            if (!this.bi.F) {
                if (!this.G()) {
                    this.a(true, true, false);
                } else if (this.bi.e()) {
                    this.a(false, true, true);
                }
            }
        } else if (this.a > 0) {
            ++this.a;
            if (this.a >= 110) {
                this.a = 0;
            }
        }

        super.G_();
        if (!this.bi.F && this.m != null && !this.m.b(this)) {
            this.F();
            this.m = this.l;
        }

        if (this.L.b) {
            for (int var10 = 0; var10 < 8; ++var10) {
                ;
            }
        }

        if (this.B_() && this.L.a) {
            this.aQ();
        }

        this.y = this.B;
        this.z = this.C;
        this.A = this.D;
        double var2 = this.bm - this.B;
        double var4 = this.bn - this.C;
        double var6 = this.bo - this.D;
        double var8 = 10.0D;

        if (var2 > var8) {
            this.y = this.B = this.bm;
        }

        if (var6 > var8) {
            this.A = this.D = this.bo;
        }

        if (var4 > var8) {
            this.z = this.C = this.bn;
        }

        if (var2 < -var8) {
            this.y = this.B = this.bm;
        }

        if (var6 < -var8) {
            this.A = this.D = this.bo;
        }

        if (var4 < -var8) {
            this.z = this.C = this.bn;
        }

        this.B += var2 * 0.25D;
        this.D += var6 * 0.25D;
        this.C += var4 * 0.25D;
        this.a(OStatList.k, 1);
        if (this.bh == null) {
            this.c = null;
        }

        if (!this.bi.F) {
            this.n.a(this);
        }

    }

    protected void b(OItemStack var1, int var2) {
        if (var1.m() == OEnumAction.c) {
            this.bi.a(this, "random.drink", 0.5F, this.bi.r.nextFloat() * 0.1F + 0.9F);
        }

        if (var1.m() == OEnumAction.b) {
            for (int var3 = 0; var3 < var2; ++var3) {
                OVec3D var4 = OVec3D.b(((double) this.bS.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);

                var4.a(-this.bt * 3.1415927F / 180.0F);
                var4.b(-this.bs * 3.1415927F / 180.0F);
                OVec3D var5 = OVec3D.b(((double) this.bS.nextFloat() - 0.5D) * 0.3D, (double) (-this.bS.nextFloat()) * 0.6D - 0.3D, 0.6D);

                var5.a(-this.bt * 3.1415927F / 180.0F);
                var5.b(-this.bs * 3.1415927F / 180.0F);
                var5 = var5.c(this.bm, this.bn + (double) this.B(), this.bo);
                this.bi.a("iconcrack_" + var1.a().bP, var5.a, var5.b, var5.c, var4.a, var4.b + 0.05D, var4.c);
            }

            this.bi.a(this, "random.eat", 0.5F + 0.5F * (float) this.bS.nextInt(2), (this.bS.nextFloat() - this.bS.nextFloat()) * 0.2F + 1.0F);
        }

    }

    protected void K() {
        if (this.d != null) {
            this.b(this.d, 16);
            int var1 = this.d.a;
            OItemStack var2 = this.d.b(this.bi, this);

            if (var2 != this.d || var2 != null && var2.a != var1) {
                this.k.a[this.k.c] = var2;
                if (var2.a == 0) {
                    this.k.a[this.k.c] = null;
                }
            }

            this.N();
        }

    }

    protected boolean P() {
        return this.aC() <= 0 || this.Y();
    }

    protected void F() {
        this.m = this.l;
    }

    public void Q() {
        double var1 = this.bm;
        double var3 = this.bn;
        double var5 = this.bo;

        super.Q();
        this.r = this.s;
        this.s = 0.0F;
        this.h(this.bm - var1, this.bn - var3, this.bo - var5);
    }

    private int E() {
        return this.a(OPotion.e) ? 6 - (1 + this.b(OPotion.e).c()) * 1 : (this.a(OPotion.f) ? 6 + (1 + this.b(OPotion.f).c()) * 2 : 6);
    }

    protected void d_() {
        int var1 = this.E();

        if (this.t) {
            ++this.u;
            if (this.u >= var1) {
                this.u = 0;
                this.t = false;
            }
        } else {
            this.u = 0;
        }

        this.ao = (float) this.u / (float) var1;
    }

    public void e() {
        if (this.o > 0) {
            --this.o;
        }
        
        // CanaryMod: adjust 'healing over time' independent of monster-spawn=true/false (nice notchup!)
        PluginLoader.HookResult autoHeal = etc.getInstance().autoHeal();

        if (this.bi.q == 0 && autoHeal == PluginLoader.HookResult.DEFAULT_ACTION || autoHeal == PluginLoader.HookResult.ALLOW_ACTION) {
            if (this.bi.q == 0 && this.aC() < this.d() && this.bT % 20 * 12 == 0) {
                this.d(1);
            }
        }

        this.k.i();
        this.r = this.s;
        super.e();
        this.al = this.P;
        this.am = this.Q;
        if (this.aY()) {
            this.al = (float) ((double) this.al + (double) this.P * 0.3D);
            this.am = (float) ((double) this.am + (double) this.Q * 0.3D);
        }

        float var1 = OMathHelper.a(this.bp * this.bp + this.br * this.br);
        float var2 = (float) Math.atan(-this.bq * 0.20000000298023224D) * 15.0F;

        if (var1 > 0.1F) {
            var1 = 0.1F;
        }

        if (!this.bx || this.aC() <= 0) {
            var1 = 0.0F;
        }

        if (this.bx || this.aC() <= 0) {
            var2 = 0.0F;
        }

        this.s += (var1 - this.s) * 0.4F;
        this.ay += (var2 - this.ay) * 0.8F;
        if (this.aC() > 0) {
            List var3 = this.bi.b((OEntity) this, this.bw.b(1.0D, 0.0D, 1.0D));

            if (var3 != null) {
                for (int var4 = 0; var4 < var3.size(); ++var4) {
                    OEntity var5 = (OEntity) var3.get(var4);

                    if (!var5.bE) {
                        this.l(var5);
                    }
                }
            }
        }

    }

    private void l(OEntity var1) {
        var1.a_(this);
    }

    public void a(ODamageSource var1) {
        super.a(var1);
        this.b(0.2F, 0.2F);
        this.c(this.bm, this.bn, this.bo);
        this.bq = 0.10000000149011612D;
        if (this.v.equals("Notch")) {
            this.a(new OItemStack(OItem.i, 1), true);
        }

        this.k.k();
        if (var1 != null) {
            this.bp = (double) (-OMathHelper.b((this.au + this.bs) * 3.1415927F / 180.0F) * 0.1F);
            this.br = (double) (-OMathHelper.a((this.au + this.bs) * 3.1415927F / 180.0F) * 0.1F);
        } else {
            this.bp = this.br = 0.0D;
        }

        this.bF = 0.1F;
        this.a(OStatList.y, 1);
    }

    public void b(OEntity var1, int var2) {
        this.q += var2;
        if (var1 instanceof OEntityPlayer) {
            this.a(OStatList.A, 1);
        } else {
            this.a(OStatList.z, 1);
        }

    }

    protected int b_(int var1) {
        int var2 = OEnchantmentHelper.a(this.k);

        return var2 > 0 && this.bS.nextInt(var2 + 1) > 0 ? var1 : super.b_(var1);
    }

    public OEntityItem R() {
        return this.a(this.k.a(this.k.c, 1), false);
    }

    public OEntityItem b(OItemStack var1) {
        return this.a(var1, false);
    }

    public OEntityItem a(OItemStack var1, boolean var2) {
        if (var1 == null) {
            return null;
        } else {
            OEntityItem var3 = new OEntityItem(this.bi, this.bm, this.bn - 0.30000001192092896D + (double) this.B(), this.bo, var1);

            var3.c = 40;
            float var4 = 0.1F;
            float var5;

            if (var2) {
                var5 = this.bS.nextFloat() * 0.5F;
                float var6 = this.bS.nextFloat() * 3.1415927F * 2.0F;

                var3.bp = (double) (-OMathHelper.a(var6) * var5);
                var3.br = (double) (OMathHelper.b(var6) * var5);
                var3.bq = 0.20000000298023224D;
            } else {
                var4 = 0.3F;
                var3.bp = (double) (-OMathHelper.a(this.bs / 180.0F * 3.1415927F) * OMathHelper.b(this.bt / 180.0F * 3.1415927F) * var4);
                var3.br = (double) (OMathHelper.b(this.bs / 180.0F * 3.1415927F) * OMathHelper.b(this.bt / 180.0F * 3.1415927F) * var4);
                var3.bq = (double) (-OMathHelper.a(this.bt / 180.0F * 3.1415927F) * var4 + 0.1F);
                var4 = 0.02F;
                var5 = this.bS.nextFloat() * 3.1415927F * 2.0F;
                var4 *= this.bS.nextFloat();
                var3.bp += Math.cos((double) var5) * (double) var4;
                var3.bq += (double) ((this.bS.nextFloat() - this.bS.nextFloat()) * 0.1F);
                var3.br += Math.sin((double) var5) * (double) var4;
            }

            if (!(Boolean) manager.callHook(PluginLoader.Hook.ITEM_DROP, ((OEntityPlayerMP) this).getPlayer(), var3.item)) {
                Item droppedItem = var3.item.getItem();

                if (droppedItem.getAmount() < 0) {
                    droppedItem.setAmount(1);
                    droppedItem.update();
                }
                this.a(var3);
                this.a(OStatList.v, 1);
                // return the item to the inventory.
            } else {
                return var3;
            }
            return null;
        }
    }

    protected void a(OEntityItem var1) {
        this.bi.b((OEntity) var1);
    }

    public float a(OBlock var1) {
        float var2 = this.k.a(var1);
        float var3 = var2;
        int var4 = OEnchantmentHelper.b(this.k);

        if (var4 > 0 && this.k.b(var1)) {
            var3 = var2 + (float) (var4 * var4 + 1);
        }

        if (this.a(OPotion.e)) {
            var3 *= 1.0F + (float) (this.b(OPotion.e).c() + 1) * 0.2F;
        }

        if (this.a(OPotion.f)) {
            var3 *= 1.0F - (float) (this.b(OPotion.f).c() + 1) * 0.2F;
        }

        if (this.a(OMaterial.g) && !OEnchantmentHelper.g(this.k)) {
            var3 /= 5.0F;
        }

        if (!this.bx) {
            var3 /= 5.0F;
        }

        return var3;
    }

    public boolean b(OBlock var1) {
        return this.k.b(var1);
    }

    public void a(ONBTTagCompound var1) {
        super.a(var1);
        ONBTTagList var2 = var1.n("Inventory");

        this.k.b(var2);
        this.w = var1.f("Dimension");
        this.E = var1.o("Sleeping");
        this.a = var1.e("SleepTimer");
        this.O = var1.h("XpP");
        this.M = var1.f("XpLevel");
        this.N = var1.f("XpTotal");
        if (this.E) {
            this.F = new OChunkCoordinates(OMathHelper.b(this.bm), OMathHelper.b(this.bn), OMathHelper.b(this.bo));
            this.a(true, true, false);
        }

        if (var1.c("SpawnX") && var1.c("SpawnY") && var1.c("SpawnZ")) {
            this.b = new OChunkCoordinates(var1.f("SpawnX"), var1.f("SpawnY"), var1.f("SpawnZ"));
        }

        this.n.a(var1);
        this.L.b(var1);
    }

    public void b(ONBTTagCompound var1) {
        super.b(var1);
        var1.a("Inventory", (ONBTBase) this.k.a(new ONBTTagList()));
        var1.a("Dimension", this.w);
        var1.a("Sleeping", this.E);
        var1.a("SleepTimer", (short) this.a);
        var1.a("XpP", this.O);
        var1.a("XpLevel", this.M);
        var1.a("XpTotal", this.N);
        if (this.b != null) {
            var1.a("SpawnX", this.b.a);
            var1.a("SpawnY", this.b.b);
            var1.a("SpawnZ", this.b.c);
        }

        this.n.b(var1);
        this.L.a(var1);
    }

    public void a(OIInventory var1) {}

    public void c(int var1, int var2, int var3) {}

    public void b(int var1, int var2, int var3) {}

    public void a(OEntity var1, int var2) {}

    public float B() {
        return 0.12F;
    }

    protected void A() {
        this.bF = 1.62F;
    }

    public boolean a(ODamageSource var1, int var2) {
        if (this.L.a && !var1.g()) {
            return false;
        } else {
            this.aV = 0;
            if (this.aC() <= 0) {
                return false;
            } else {
                if (this.Y() && !this.bi.F) {
                    this.a(true, true, false);
                }

                OEntity var3 = var1.a();

                if (var3 instanceof OEntityMob || var3 instanceof OEntityArrow) {
                    if (this.bi.q == 0) {
                        var2 = 0;
                    }

                    if (this.bi.q == 1) {
                        var2 = var2 / 2 + 1;
                    }

                    if (this.bi.q == 3) {
                        var2 = var2 * 3 / 2;
                    }
                }

                if (var2 == 0) {
                    return false;
                } else {
                    OEntity var4 = var3;

                    if (var3 instanceof OEntityArrow && ((OEntityArrow) var3).c != null) {
                        var4 = ((OEntityArrow) var3).c;
                    }

                    if (var4 instanceof OEntityLiving) {
                        this.a((OEntityLiving) var4, false);
                    }

                    this.a(OStatList.x, var2);
                    return super.a(var1, var2);
                }
            }
        }
    }

    protected int b(ODamageSource var1, int var2) {
        int var3 = super.b(var1, var2);

        if (var3 <= 0) {
            return 0;
        } else {
            int var4 = OEnchantmentHelper.a(this.k, var1);

            if (var4 > 20) {
                var4 = 20;
            }

            if (var4 > 0 && var4 <= 20) {
                int var5 = 25 - var4;
                int var6 = var3 * var5 + this.ar;

                var3 = var6 / 25;
                this.ar = var6 % 25;
            }

            return var3;
        }
    }

    protected boolean C_() {
        return false;
    }

    protected void a(OEntityLiving var1, boolean var2) {
        if (!(var1 instanceof OEntityCreeper) && !(var1 instanceof OEntityGhast)) {
            if (var1 instanceof OEntityWolf) {
                OEntityWolf var3 = (OEntityWolf) var1;

                if (var3.u_() && this.v.equals(var3.A())) {
                    return;
                }
            }

            if (!(var1 instanceof OEntityPlayer) || this.C_()) {
                List var7 = this.bi.a(OEntityWolf.class, OAxisAlignedBB.b(this.bm, this.bn, this.bo, this.bm + 1.0D, this.bn + 1.0D, this.bo + 1.0D).b(16.0D, 4.0D, 16.0D));
                Iterator var4 = var7.iterator();

                while (var4.hasNext()) {
                    OEntity var5 = (OEntity) var4.next();
                    OEntityWolf var6 = (OEntityWolf) var5;

                    if (var6.u_() && var6.H() == null && this.v.equals(var6.A()) && (!var2 || !var6.v_())) {
                        var6.c(false);
                        var6.d(var1);
                    }
                }

            }
        }
    }

    protected void f(int var1) {
        this.k.e(var1);
    }

    public int S() {
        return this.k.j();
    }

    protected void c(ODamageSource var1, int var2) {
        if (!var1.e() && this.O()) {
            var2 = 1 + var2 >> 1;
        }

        var2 = this.d(var1, var2);
        var2 = this.b(var1, var2);
        this.c(var1.f());
        this.ap -= var2;
    }

    public void a(OTileEntityFurnace var1) {}

    public void a(OTileEntityDispenser var1) {}

    public void a(OTileEntitySign var1) {}

    public void a(OTileEntityBrewingStand var1) {}

    public void e(OEntity var1) {
        if (!var1.b(this)) {
            OItemStack var2 = this.T();
            PluginLoader.HookResult res = (PluginLoader.HookResult) manager.callHook(PluginLoader.Hook.ENTITY_RIGHTCLICKED, ((OEntityPlayerMP) this).getPlayer(), var1.entity, (var2 == null) ? null : new Item(var2));

            if (res != PluginLoader.HookResult.PREVENT_ACTION) {
                // Normally when interact action is not defined on the interacted entity, false is returned, and the item stack is not used.
                // For example sheep can interact by shearing and cows by milking, and the item stack changes from this interaction if it returns true.
                // Players on the other hand won't interact normally, but if we want to update the item stack anyways, we will ALLOW the action.
                if (!var1.b(this) || res == PluginLoader.HookResult.ALLOW_ACTION) {

                    if (var2 != null && var1 instanceof OEntityLiving) {
                        var2.a((OEntityLiving) var1);
                        if (var2.a <= 0) {
                            var2.a(this);
                            this.U();
                        }
                    }
                }
            }
        }
    }

    public OItemStack T() {
        return this.k.d();
    }

    public void U() {
        this.k.a(this.k.c, (OItemStack) null);
    }

    public double V() {
        return (double) (this.bF - 0.5F);
    }

    public void D() {
        if (!this.t || this.u >= this.E() / 2 || this.u < 0) {
            this.u = -1;
            this.t = true;
        }

    }

    public void f(OEntity var1) {
        if (var1.k_()) {
            int var2 = this.k.a(var1);

            if (this.a(OPotion.g)) {
                var2 += 3 << this.b(OPotion.g).c();
            }

            if (this.a(OPotion.t)) {
                var2 -= 2 << this.b(OPotion.t).c();
            }

            int var3 = 0;
            int var4 = 0;

            if (var1 instanceof OEntityLiving) {
                var4 = OEnchantmentHelper.a(this.k, (OEntityLiving) var1);
                var3 += OEnchantmentHelper.b(this.k, (OEntityLiving) var1);
            }

            if (this.aY()) {
                ++var3;
            }

            if (var2 > 0 || var4 > 0) {
                boolean var5 = this.bK > 0.0F && !this.bx && !this.t() && !this.aT() && !this.a(OPotion.q) && this.bh == null && var1 instanceof OEntityLiving;

                if (var5) {
                    var2 += this.bS.nextInt(var2 / 2 + 2);
                }

                var2 += var4;
                boolean var6 = var1.a(ODamageSource.b(this), var2);

                if (var6) {
                    if (var3 > 0) {
                        var1.b_((double) (-OMathHelper.a(this.bs * 3.1415927F / 180.0F) * (float) var3 * 0.5F), 0.1D, (double) (OMathHelper.b(this.bs * 3.1415927F / 180.0F) * (float) var3 * 0.5F));
                        this.bp *= 0.6D;
                        this.br *= 0.6D;
                        this.h(false);
                    }

                    if (var5) {
                        this.c(var1);
                    }

                    if (var4 > 0) {
                        this.d(var1);
                    }

                    if (var2 >= 18) {
                        this.a((OStatBase) OAchievementList.E);
                    }

                    this.g(var1);
                }

                OItemStack var7 = this.T();

                if (var7 != null && var1 instanceof OEntityLiving) {
                    var7.a((OEntityLiving) var1, this);
                    if (var7.a <= 0) {
                        var7.a(this);
                        this.U();
                    }
                }

                if (var1 instanceof OEntityLiving) {
                    if (var1.aD()) {
                        this.a((OEntityLiving) var1, true);
                    }

                    this.a(OStatList.w, var2);
                    int var8 = OEnchantmentHelper.c(this.k, (OEntityLiving) var1);

                    if (var8 > 0) {
                        var1.i(var8 * 4);
                    }
                }

                this.c(0.3F);
            }

        }
    }

    public void c(OEntity var1) {}

    public void d(OEntity var1) {}

    public void a(OItemStack var1) {}

    public void W() {
        super.W();
        this.l.a(this);
        if (this.m != null) {
            this.m.a(this);
        }

    }

    public boolean X() {
        return !this.E && super.X();
    }

    public OEnumStatus a(int var1, int var2, int var3) {
        if (!this.bi.F) {
            if (this.Y() || !this.aD()) {
                return OEnumStatus.e;
            }

            if (!this.bi.t.d()) {
                return OEnumStatus.b;
            }

            if (this.bi.e()) {
                return OEnumStatus.c;
            }

            if (Math.abs(this.bm - (double) var1) > 3.0D || Math.abs(this.bn - (double) var2) > 2.0D || Math.abs(this.bo - (double) var3) > 3.0D) {
                return OEnumStatus.d;
            }

            double var4 = 8.0D;
            double var6 = 5.0D;
            List var8 = this.bi.a(OEntityMob.class, OAxisAlignedBB.b((double) var1 - var4, (double) var2 - var6, (double) var3 - var4, (double) var1 + var4, (double) var2 + var6, (double) var3 + var4));

            if (!var8.isEmpty()) {
                return OEnumStatus.f;
            }
        }

        this.b(0.2F, 0.2F);
        this.bF = 0.2F;
        if (this.bi.i(var1, var2, var3)) {
            int var9 = this.bi.c(var1, var2, var3);
            int var10 = OBlockBed.b(var9);
            float var11 = 0.5F;
            float var12 = 0.5F;

            switch (var10) {
            case 0:
                var12 = 0.9F;
                break;

            case 1:
                var11 = 0.1F;
                break;

            case 2:
                var12 = 0.1F;
                break;

            case 3:
                var11 = 0.9F;
            }

            this.c(var10);
            this.c((double) ((float) var1 + var11), (double) ((float) var2 + 0.9375F), (double) ((float) var3 + var12));
        } else {
            this.c((double) ((float) var1 + 0.5F), (double) ((float) var2 + 0.9375F), (double) ((float) var3 + 0.5F));
        }

        this.E = true;
        this.a = 0;
        this.F = new OChunkCoordinates(var1, var2, var3);
        this.bp = this.br = this.bq = 0.0D;
        if (!this.bi.F) {
            this.bi.t();
        }

        return OEnumStatus.a;
    }

    private void c(int var1) {
        this.G = 0.0F;
        this.H = 0.0F;
        switch (var1) {
        case 0:
            this.H = -1.8F;
            break;

        case 1:
            this.G = 1.8F;
            break;

        case 2:
            this.H = 1.8F;
            break;

        case 3:
            this.G = -1.8F;
        }

    }

    public void a(boolean var1, boolean var2, boolean var3) {
        this.b(0.6F, 1.8F);
        this.A();
        OChunkCoordinates var4 = this.F;
        OChunkCoordinates var5 = this.F;

        if (var4 != null && this.bi.a(var4.a, var4.b, var4.c) == OBlock.S.bO) {
            OBlockBed.a(this.bi, var4.a, var4.b, var4.c, false);
            var5 = OBlockBed.f(this.bi, var4.a, var4.b, var4.c, 0);
            if (var5 == null) {
                var5 = new OChunkCoordinates(var4.a, var4.b + 1, var4.c);
            }

            this.c((double) ((float) var5.a + 0.5F), (double) ((float) var5.b + this.bF + 0.1F), (double) ((float) var5.c + 0.5F));
        }

        this.E = false;
        if (!this.bi.F && var2) {
            this.bi.t();
        }

        if (var1) {
            this.a = 0;
        } else {
            this.a = 100;
        }

        if (var3) {
            this.a(this.F);
        }

    }

    private boolean G() {
        return this.bi.a(this.F.a, this.F.b, this.F.c) == OBlock.S.bO;
    }

    public static OChunkCoordinates a(OWorld var0, OChunkCoordinates var1) {
        OIChunkProvider var2 = var0.q();

        var2.c(var1.a - 3 >> 4, var1.c - 3 >> 4);
        var2.c(var1.a + 3 >> 4, var1.c - 3 >> 4);
        var2.c(var1.a - 3 >> 4, var1.c + 3 >> 4);
        var2.c(var1.a + 3 >> 4, var1.c + 3 >> 4);
        if (var0.a(var1.a, var1.b, var1.c) != OBlock.S.bO) {
            return null;
        } else {
            OChunkCoordinates var3 = OBlockBed.f(var0, var1.a, var1.b, var1.c, 0);

            return var3;
        }
    }

    public boolean Y() {
        return this.E;
    }

    public boolean Z() {
        return this.E && this.a >= 100;
    }

    public void a(String var1) {}

    public OChunkCoordinates aa() {
        return this.b;
    }

    public void a(OChunkCoordinates var1) {
        if (var1 != null) {
            this.b = new OChunkCoordinates(var1);
        } else {
            this.b = null;
        }

    }

    public void a(OStatBase var1) {
        this.a(var1, 1);
    }

    public void a(OStatBase var1, int var2) {}

    protected void ab() {
        super.ab();
        this.a(OStatList.u, 1);
        if (this.aY()) {
            this.c(0.8F);
        } else {
            this.c(0.2F);
        }

    }

    public void a(float var1, float var2) {
        double var3 = this.bm;
        double var5 = this.bn;
        double var7 = this.bo;

        if (this.L.b) {
            double var9 = this.bq;
            float var11 = this.am;

            this.am = 0.05F;
            super.a(var1, var2);
            this.bq = var9 * 0.6D;
            this.am = var11;
        } else {
            super.a(var1, var2);
        }

        this.b(this.bm - var3, this.bn - var5, this.bo - var7);
    }

    public void b(double var1, double var3, double var5) {
        if (this.bh == null) {
            int var7;

            if (this.a(OMaterial.g)) {
                var7 = Math.round(OMathHelper.a(var1 * var1 + var3 * var3 + var5 * var5) * 100.0F);
                if (var7 > 0) {
                    this.a(OStatList.q, var7);
                    this.c(0.015F * (float) var7 * 0.01F);
                }
            } else if (this.aT()) {
                var7 = Math.round(OMathHelper.a(var1 * var1 + var5 * var5) * 100.0F);
                if (var7 > 0) {
                    this.a(OStatList.m, var7);
                    this.c(0.015F * (float) var7 * 0.01F);
                }
            } else if (this.t()) {
                if (var3 > 0.0D) {
                    this.a(OStatList.o, (int) Math.round(var3 * 100.0D));
                }
            } else if (this.bx) {
                var7 = Math.round(OMathHelper.a(var1 * var1 + var5 * var5) * 100.0F);
                if (var7 > 0) {
                    this.a(OStatList.l, var7);
                    if (this.aY()) {
                        this.c(0.099999994F * (float) var7 * 0.01F);
                    } else {
                        this.c(0.01F * (float) var7 * 0.01F);
                    }
                }
            } else {
                var7 = Math.round(OMathHelper.a(var1 * var1 + var5 * var5) * 100.0F);
                if (var7 > 25) {
                    this.a(OStatList.p, var7);
                }
            }

        }
    }

    private void h(double var1, double var3, double var5) {
        if (this.bh != null) {
            int var7 = Math.round(OMathHelper.a(var1 * var1 + var3 * var3 + var5 * var5) * 100.0F);

            if (var7 > 0) {
                if (this.bh instanceof OEntityMinecart) {
                    this.a(OStatList.r, var7);
                    if (this.c == null) {
                        this.c = new OChunkCoordinates(OMathHelper.b(this.bm), OMathHelper.b(this.bn), OMathHelper.b(this.bo));
                    } else if (this.c.b(OMathHelper.b(this.bm), OMathHelper.b(this.bn), OMathHelper.b(this.bo)) >= 1000.0D) {
                        this.a((OStatBase) OAchievementList.q, 1);
                    }
                } else if (this.bh instanceof OEntityBoat) {
                    this.a(OStatList.s, var7);
                } else if (this.bh instanceof OEntityPig) {
                    this.a(OStatList.t, var7);
                }
            }
        }

    }

    protected void a(float var1) {
        if (!this.L.c) {
            if (var1 >= 2.0F) {
                this.a(OStatList.n, (int) Math.round((double) var1 * 100.0D));
            }

            super.a(var1);
        }
    }

    public void c(OEntityLiving var1) {
        if (var1 instanceof OEntityMob) {
            this.a((OStatBase) OAchievementList.s);
        }

    }

    public void ac() {
        if (this.I > 0) {
            this.I = 10;
        } else {
            this.J = true;
        }
    }

    public void g(int var1) {
        addXP(var1);
    }
    
    public void addXP(int var1) {
        int var2 = Integer.MAX_VALUE - this.N;
        if (var1 > var2) {
            var1 = var2;
        }
        
        this.q += var1;
        this.O += (float) var1 / (float) this.ad();
        this.N += var1;
        levelUp();
    }

    public void removeXP(int var1) {
        this.q -= var1;
        this.O -= (float) var1 / (float) this.ad();
        this.N -= var1;
        levelUp();
    }

    public void setXP(int var1) {
        this.q = var1;
        this.O = (float) var1 / (float) this.ad();
        this.N = var1;
        levelUp();
    }
    
    public void levelUp() {
        for (; this.O >= 1.0F; this.O /= (float) this.ad()) {
            this.O = (this.O - 1.0F) * (float) this.ad();
            this.H();
            
            manager.callHook(PluginLoader.Hook.LEVEL_UP, ((OEntityPlayerMP) this).getPlayer());
        }
    }

    public void e_(int var1) {
        this.M -= var1;
        if (this.M < 0) {
            this.M = 0;
        }

    }

    public int ad() {
        return 7 + (this.M * 7 >> 1);
    }

    private void H() {
        ++this.M;
    }

    public void c(float var1) {
        if (!this.L.a) {
            if (!this.bi.F) {
                this.n.a(var1);
            }

        }
    }

    public OFoodStats ae() {
        return this.n;
    }

    public boolean b(boolean var1) {
        return (var1 || this.n.b()) && !this.L.a;
    }

    public boolean af() {
        return this.aC() > 0 && this.aC() < this.d();
    }

    public void a(OItemStack var1, int var2) {
        if (var1 != this.d) {
            this.d = var1;
            this.e = var2;
            if (!this.bi.F) {
                this.i(true);
            }

        }
    }

    public boolean d(int var1, int var2, int var3) {
        return true;
    }

    protected int a(OEntityPlayer var1) {
        int var2 = this.M * 7;

        return var2 > 100 ? 100 : var2;
    }

    protected boolean ag() {
        return true;
    }

    public String s() {
        return this.v;
    }

    public void e(int var1) {}

    public void c(OEntityPlayer var1) {
        this.k.a(var1.k);
        this.ap = var1.ap;
        this.n = var1.n;
        this.M = var1.M;
        this.N = var1.N;
        this.O = var1.O;
        this.q = var1.q;
    }

    protected boolean g_() {
        return !this.L.b;
    }
}

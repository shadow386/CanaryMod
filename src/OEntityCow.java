
public class OEntityCow extends OEntityAnimal {

    public OEntityCow(OWorld var1) {
        super(var1);
        this.ae = "/mob/cow.png";
        this.b(0.9F, 1.3F);
    }

    public int c() {
        return 10;
    }

    public void b(ONBTTagCompound var1) {
        super.b(var1);
    }

    public void a(ONBTTagCompound var1) {
        super.a(var1);
    }

    protected String c_() {
        return "mob.cow";
    }

    protected String m() {
        return "mob.cowhurt";
    }

    protected String n() {
        return "mob.cowhurt";
    }

    protected float o() {
        return 0.4F;
    }

    protected int e() {
        return OItem.aE.bN;
    }

    protected void a(boolean var1, int var2) {
        int var3 = this.bS.nextInt(3) + this.bS.nextInt(1 + var2);

        int var4;

        for (var4 = 0; var4 < var3; ++var4) {
            this.b(OItem.aE.bN, 1);
        }

        var3 = this.bS.nextInt(3) + 1 + this.bS.nextInt(1 + var2);

        for (var4 = 0; var4 < var3; ++var4) {
            if (this.A()) {
                this.b(OItem.bi.bN, 1);
            } else {
                this.b(OItem.bh.bN, 1);
            }
        }

    }

    public boolean b(OEntityPlayer var1) {
        OItemStack var2 = var1.k.d();

        if (var2 != null && var2.c == OItem.av.bN) {
            // CanaryMod hook: onCowMilk
            if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.COW_MILK, ((OEntityPlayerMP) var1).getPlayer(), new Mob(this))) {
                var1.k.a(var1.k.c, new OItemStack(OItem.aF));
                return true;
            } else {
                return super.b(var1);
            }
        } else {
            return super.b(var1);
        }
    }

    protected OEntityAnimal a(OEntityAnimal var1) {
        return new OEntityCow(this.bi);
    }
}


public class OFoodStats {

    // CanaryMod: made fields public
    public int a = 20;
    public float b = 5.0F;
    public float c;
    public int d = 0;
    private int e = 20;
    // CanaryMod: enitity
    private OEntityPlayer entity;

    public OFoodStats() {}

    public OFoodStats(OEntityPlayer oEntityPlayer) {
        this.entity = oEntityPlayer;
    }
   
    public void a(int var1, float var2) {
        // CanaryMod: Calls onFoodLevelChange
        int newLevel = Math.min(var1 + this.a, 20);
        int oldLevel = this.a;

        this.a = ((Integer) etc.getLoader().callHook(PluginLoader.Hook.FOODLEVEL_CHANGE, new Object[] { ((OEntityPlayerMP) this.entity).getPlayer(), Integer.valueOf(oldLevel), Integer.valueOf(newLevel) })).intValue();

        // CanaryMod: Calls onFoodSaturationChange
        float newSLevel = Math.min(this.b + (float) var1 * var2 * 2.0F, (float) this.a);
        float oldSLevel = this.b;

        this.b = ((Float) etc.getLoader().callHook(PluginLoader.Hook.FOODSATURATION_CHANGE, new Object[] { ((OEntityPlayerMP) this.entity).getPlayer(), Float.valueOf(oldSLevel), Float.valueOf(newSLevel) })).floatValue();

        ((OEntityPlayerMP) entity).getPlayer().updateLevels(); 
    }

    public void a(OItemFood var1) {
        this.a(var1.o(), var1.p());
    }

    public void a(OEntityPlayer var1) {
        int var2 = var1.bi.v;

        this.e = this.a;
        if (this.c > 4.0F) {
            // CanaryMod: Calls onFoodExhaustionChange
            float newExLevel = this.c - 4.0F;
            float oldExLevel = this.c;

            this.c = (Float) etc.getLoader().callHook(PluginLoader.Hook.FOODEXHAUSTION_CHANGE, ((OEntityPlayerMP) var1).getPlayer(), oldExLevel, newExLevel);
            if (this.b > 0.0F) {
                // CanaryMod: Calls onFoodSaturationChange
                float newLevel = Math.max(this.b - 1.0F, 0.0F);
                float oldLevel = this.b;

                this.b = (Float) etc.getLoader().callHook(PluginLoader.Hook.FOODSATURATION_CHANGE, ((OEntityPlayerMP) var1).getPlayer(), oldLevel, newLevel);
            } else if (var2 > 0) {
                // CanaryMod: Calls onFoodLevelChange
                int newLevel = Math.max(this.a - 1, 0);
                int oldLevel = this.a;

                this.a = (Integer) etc.getLoader().callHook(PluginLoader.Hook.FOODLEVEL_CHANGE, ((OEntityPlayerMP) var1).getPlayer(), oldLevel, newLevel);
            }
        }

        if (this.a >= 18 && var1.ab()) {
            ++this.d;
            if (this.d >= 80) {
                var1.d(1);
                this.d = 0;
            }
        } else if (this.a <= 0) {
            ++this.d;
            if (this.d >= 80) {
                if (var1.ap() > 10 || var2 >= 3 || var1.ap() > 1 && var2 >= 2) {
                    // CanaryMod: DAMAGE From starvation
                    if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.DAMAGE, PluginLoader.DamageType.STARVATION, null, ((OEntityPlayerMP) var1).getPlayer(), 1)) {
                        var1.a(ODamageSource.g, 1);
                    }
                }

                this.d = 0;
            }
        } else {
            this.d = 0;
        }

    }

    public void a(ONBTTagCompound var1) {
        if (var1.c("foodLevel")) {
            this.a = var1.f("foodLevel");
            this.d = var1.f("foodTickTimer");
            this.b = var1.h("foodSaturationLevel");
            this.c = var1.h("foodExhaustionLevel");
        }

    }

    public void b(ONBTTagCompound var1) {
        var1.a("foodLevel", this.a);
        var1.a("foodTickTimer", this.d);
        var1.a("foodSaturationLevel", this.b);
        var1.a("foodExhaustionLevel", this.c);
    }

    public int a() {
        return this.a;
    }

    public boolean b() {
        return this.a < 20;
    }

    public void a(float var1) {
        this.c = Math.min(this.c + var1, 40.0F);
    }

    public float c() {
        return this.b;
    }
}

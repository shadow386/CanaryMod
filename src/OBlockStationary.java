import java.util.Random;


public class OBlockStationary extends OBlockFluid {

    protected OBlockStationary(int var1, OMaterial var2) {
        super(var1, var2);
        this.a(false);
        if (var2 == OMaterial.h) {
            this.a(true);
        }

    }

    public void a(OWorld var1, int var2, int var3, int var4, int var5) {
        super.a(var1, var2, var3, var4, var5);
        if (var1.a(var2, var3, var4) == this.bO) {
            this.i(var1, var2, var3, var4);
        }

    }

    private void i(OWorld var1, int var2, int var3, int var4) {
        int var5 = var1.c(var2, var3, var4);

        var1.t = true;
        var1.a(var2, var3, var4, this.bO - 1, var5);
        var1.b(var2, var3, var4, var2, var3, var4);
        var1.c(var2, var3, var4, this.bO - 1, this.d());
        var1.t = false;
    }

    public void a(OWorld var1, int var2, int var3, int var4, Random var5) {
        if (this.cb == OMaterial.h) {
            int var6 = var5.nextInt(3);

            // CanaryMod: prevent lava from putting something on fire.
            Block block = new Block(var1.world, var1.a(var2, var3, var4), var2, var3, var4);

            block.setStatus(1);
            if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.IGNITE, block, null)) {
                return;
            }

            for (int var7 = 0; var7 < var6; ++var7) {
                var2 += var5.nextInt(3) - 1;
                ++var3;
                var4 += var5.nextInt(3) - 1;
                int var8 = var1.a(var2, var3, var4);

                if (var8 == 0) {
                    if (this.j(var1, var2 - 1, var3, var4) || this.j(var1, var2 + 1, var3, var4) || this.j(var1, var2, var3, var4 - 1) || this.j(var1, var2, var3, var4 + 1) || this.j(var1, var2, var3 - 1, var4) || this.j(var1, var2, var3 + 1, var4)) {
                        var1.e(var2, var3, var4, OBlock.at.bO);
                        return;
                    }
                } else if (OBlock.m[var8].cb.c()) {
                    return;
                }
            }
        }

    }

    private boolean j(OWorld var1, int var2, int var3, int var4) {
        return var1.d(var2, var3, var4).g();
    }
}

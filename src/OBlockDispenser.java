
import java.util.Random;

public class OBlockDispenser extends OBlockContainer {

    private Random a = new Random();

    protected OBlockDispenser(int var1) {
        super(var1, OMaterial.e);
        this.bN = 45;
    }

    public int d() {
        return 4;
    }

    public int a(int var1, Random var2, int var3) {
        return OBlock.R.bO;
    }

    public void a(OWorld var1, int var2, int var3, int var4) {
        super.a(var1, var2, var3, var4);
        this.g(var1, var2, var3, var4);
    }

    private void g(OWorld var1, int var2, int var3, int var4) {
        if (!var1.I) {
            int var5 = var1.a(var2, var3, var4 - 1);
            int var6 = var1.a(var2, var3, var4 + 1);
            int var7 = var1.a(var2 - 1, var3, var4);
            int var8 = var1.a(var2 + 1, var3, var4);
            byte var9 = 3;
            if (OBlock.o[var5] && !OBlock.o[var6]) {
                var9 = 3;
            }

            if (OBlock.o[var6] && !OBlock.o[var5]) {
                var9 = 2;
            }

            if (OBlock.o[var7] && !OBlock.o[var8]) {
                var9 = 5;
            }

            if (OBlock.o[var8] && !OBlock.o[var7]) {
                var9 = 4;
            }

            var1.c(var2, var3, var4, var9);
        }
    }

    public int a(int var1) {
        return var1 == 1 ? this.bN + 17 : (var1 == 0 ? this.bN + 17 : (var1 == 3 ? this.bN + 1 : this.bN));
    }

    public boolean a(OWorld var1, int var2, int var3, int var4, OEntityPlayer var5) {
        if (var1.I) {
            return true;
        } else {
            OTileEntityDispenser var6 = (OTileEntityDispenser) var1.b(var2, var3, var4);
            if (var6 != null) {
                var5.a(var6);
            }

            return true;
        }
    }

    private void b(OWorld var1, int var2, int var3, int var4, Random var5) {
        int var6 = var1.c(var2, var3, var4);
        byte var7 = 0;
        byte var8 = 0;
        if (var6 == 3) {
            var8 = 1;
        } else if (var6 == 2) {
            var8 = -1;
        } else if (var6 == 5) {
            var7 = 1;
        } else {
            var7 = -1;
        }

        OTileEntityDispenser var9 = (OTileEntityDispenser) var1.b(var2, var3, var4);

        if (var9 != null) {
            OItemStack var10 = var9.k_();
            double var11 = (double) var2 + (double) var7 * 0.6D + 0.5D;
            double var13 = (double) var3 + 0.5D;
            double var15 = (double) var4 + (double) var8 * 0.6D + 0.5D;
            
            if (var10 == null) {
                if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.DISPENSE, new Dispenser(var9), null))
                    var1.f(1001, var2, var3, var4, 0);
            } else {

                if (var10.c == OItem.k.bN) {
                    OEntityArrow var17 = new OEntityArrow(var1, var11, var13, var15);
                    var17.a((double) var7, 0.10000000149011612D, (double) var8, 1.1F, 6.0F);
                    var17.a = true;
                    if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.DISPENSE, new Dispenser(var9), new BaseEntity(var17))) {
                        var1.b((OEntity) var17);
                        var1.f(1002, var2, var3, var4, 0);
                    } else
                        var17.T();
                } else if (var10.c == OItem.aO.bN) {
                    OEntityEgg var22 = new OEntityEgg(var1, var11, var13, var15);
                    var22.a((double) var7, 0.10000000149011612D, (double) var8, 1.1F, 6.0F);
                    if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.DISPENSE, new Dispenser(var9), new BaseEntity(var22))) {
                        var1.b((OEntity) var22);
                        var1.f(1002, var2, var3, var4, 0);
                    } else
                        var22.T();
                } else if (var10.c == OItem.aC.bN) {
                    OEntitySnowball var21 = new OEntitySnowball(var1, var11, var13, var15);
                    var21.a((double) var7, 0.10000000149011612D, (double) var8, 1.1F, 6.0F);
                    if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.DISPENSE, new Dispenser(var9), new BaseEntity(var21))) {
                        var1.b((OEntity) var21);
                        var1.f(1002, var2, var3, var4, 0);
                    } else
                        var21.T();
                } else if (var10.c == OItem.br.bN && OItemPotion.c(var10.h())) {
                    OEntityPotion var23 = new OEntityPotion(var1, var11, var13, var15, var10.h());
                    var23.a((double) var7, 0.10000000149011612D, (double) var8, 1.375F, 3.0F);
                    if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.DISPENSE, new Dispenser(var9), new BaseEntity(var23))) {
                        var1.b((OEntity) var23);
                        var1.f(1002, var2, var3, var4, 0);
                    } else
                        var23.T();
                } else {
                    OEntityItem var20 = new OEntityItem(var1, var11, var13 - 0.3D, var15, var10);
                    double var18 = var5.nextDouble() * 0.1D + 0.2D;
                    var20.bp = (double) var7 * var18;
                    var20.bq = 0.20000000298023224D;
                    var20.br = (double) var8 * var18;
                    var20.bp += var5.nextGaussian() * 0.007499999832361937D * 6.0D;
                    var20.bq += var5.nextGaussian() * 0.007499999832361937D * 6.0D;
                    var20.br += var5.nextGaussian() * 0.007499999832361937D * 6.0D;
                    if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.DISPENSE, new Dispenser(var9), new ItemEntity(var20))) {
                        var1.b((OEntity) var20);
                        var1.f(1002, var2, var3, var4, 0);
                    } else
                        var20.T();
                }

                var1.f(2000, var2, var3, var4, var7 + 1 + (var8 + 1) * 3);
            }
        }

    }

    public void a(OWorld var1, int var2, int var3, int var4, int var5) {
        if (var5 > 0 && OBlock.m[var5].e()) {
            boolean var6 = var1.u(var2, var3, var4) || var1.u(var2, var3 + 1, var4);
            if (var6) {
                var1.c(var2, var3, var4, this.bO, this.d());
            }
        }

    }

    public void a(OWorld var1, int var2, int var3, int var4, Random var5) {
        if (!var1.I && (var1.u(var2, var3, var4) || var1.u(var2, var3 + 1, var4))) {
            this.b(var1, var2, var3, var4, var5);
        }

    }

    public OTileEntity a_() {
        return new OTileEntityDispenser();
    }

    public void a(OWorld var1, int var2, int var3, int var4, OEntityLiving var5) {
        int var6 = OMathHelper.b((double) (var5.bs * 4.0F / 360.0F) + 0.5D) & 3;
        if (var6 == 0) {
            var1.c(var2, var3, var4, 2);
        }

        if (var6 == 1) {
            var1.c(var2, var3, var4, 5);
        }

        if (var6 == 2) {
            var1.c(var2, var3, var4, 3);
        }

        if (var6 == 3) {
            var1.c(var2, var3, var4, 4);
        }

    }

    public void d(OWorld var1, int var2, int var3, int var4) {
        OTileEntityDispenser var5 = (OTileEntityDispenser) var1.b(var2, var3, var4);
        if (var5 != null) {
            for (int var6 = 0; var6 < var5.c(); ++var6) {
                OItemStack var7 = var5.c_(var6);
                if (var7 != null) {
                    float var8 = this.a.nextFloat() * 0.8F + 0.1F;
                    float var9 = this.a.nextFloat() * 0.8F + 0.1F;
                    float var10 = this.a.nextFloat() * 0.8F + 0.1F;

                    while (var7.a > 0) {
                        int var11 = this.a.nextInt(21) + 10;
                        if (var11 > var7.a) {
                            var11 = var7.a;
                        }

                        var7.a -= var11;
                        OEntityItem var12 = new OEntityItem(var1, (double) ((float) var2 + var8), (double) ((float) var3 + var9), (double) ((float) var4 + var10), new OItemStack(var7.c, var11, var7.h()));
                        float var13 = 0.05F;
                        var12.bp = (double) ((float) this.a.nextGaussian() * var13);
                        var12.bq = (double) ((float) this.a.nextGaussian() * var13 + 0.2F);
                        var12.br = (double) ((float) this.a.nextGaussian() * var13);
                        var1.b((OEntity) var12);
                    }
                }
            }
        }

        super.d(var1, var2, var3, var4);
    }
}

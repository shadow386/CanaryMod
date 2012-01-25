import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class OContainerEnchantment extends OContainer {

    public OIInventory a = new OSlotEnchantmentTable(this, "Enchant", 1);
    private OWorld h;
    private int i;
    private int j;
    private int k;
    private Random l = new Random();
    public long b;
    public int[] c = new int[3];

    public OContainerEnchantment(OInventoryPlayer var1, OWorld var2, int var3, int var4, int var5) {
        super();
        this.h = var2;
        this.i = var3;
        this.j = var4;
        this.k = var5;
        this.a((OSlot) (new OSlotEnchantment(this, this.a, 0, 25, 47)));

        int var6;

        for (var6 = 0; var6 < 3; ++var6) {
            for (int var7 = 0; var7 < 9; ++var7) {
                this.a(new OSlot(var1, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
            }
        }

        for (var6 = 0; var6 < 9; ++var6) {
            this.a(new OSlot(var1, var6, 8 + var6 * 18, 142));
        }

    }

    public void a(OICrafting var1) {
        super.a(var1);
        var1.a(this, 0, this.c[0]);
        var1.a(this, 1, this.c[1]);
        var1.a(this, 2, this.c[2]);
    }

    public void a() {
        super.a();

        for (int var1 = 0; var1 < this.g.size(); ++var1) {
            OICrafting var2 = (OICrafting) this.g.get(var1);

            var2.a(this, 0, this.c[0]);
            var2.a(this, 1, this.c[1]);
            var2.a(this, 2, this.c[2]);
        }

    }

    public void a(OIInventory var1) {
        if (var1 == this.a) {
            OItemStack var2 = var1.c_(0);
            int var3;

            if (var2 != null && var2.q()) {
                this.b = this.l.nextLong();
                if (!this.h.I) {
                    var3 = 0;

                    int var4;

                    for (var4 = -1; var4 <= 1; ++var4) {
                        for (int var5 = -1; var5 <= 1; ++var5) {
                            if ((var4 != 0 || var5 != 0) && this.h.f(this.i + var5, this.j, this.k + var4) && this.h.f(this.i + var5, this.j + 1, this.k + var4)) {
                                if (this.h.a(this.i + var5 * 2, this.j, this.k + var4 * 2) == OBlock.ap.bO) {
                                    ++var3;
                                }

                                if (this.h.a(this.i + var5 * 2, this.j + 1, this.k + var4 * 2) == OBlock.ap.bO) {
                                    ++var3;
                                }

                                if (var5 != 0 && var4 != 0) {
                                    if (this.h.a(this.i + var5 * 2, this.j, this.k + var4) == OBlock.ap.bO) {
                                        ++var3;
                                    }

                                    if (this.h.a(this.i + var5 * 2, this.j + 1, this.k + var4) == OBlock.ap.bO) {
                                        ++var3;
                                    }

                                    if (this.h.a(this.i + var5, this.j, this.k + var4 * 2) == OBlock.ap.bO) {
                                        ++var3;
                                    }

                                    if (this.h.a(this.i + var5, this.j + 1, this.k + var4 * 2) == OBlock.ap.bO) {
                                        ++var3;
                                    }
                                }
                            }
                        }
                    }

                    for (var4 = 0; var4 < 3; ++var4) {
                        this.c[var4] = OEnchantmentHelper.a(this.l, var4, var3, var2);
                    }

                    this.a();
                }
            } else {
                for (var3 = 0; var3 < 3; ++var3) {
                    this.c[var3] = 0;
                }
            }
        }

    }

    public boolean a(OEntityPlayer var1, int var2) {
        OItemStack var3 = this.a.c_(0);

        if (this.c[var2] > 0 && var3 != null && var1.M >= this.c[var2]) {
            if (!this.h.I) {
                List var4 = OEnchantmentHelper.a(this.l, var3, this.c[var2]);

                if (var4 != null) {
                    // CanaryMod hook: onEnchant
                    HookParametersEnchant enchantParameters = (HookParametersEnchant)etc.getLoader().callHook(PluginLoader.Hook.ENCHANT, new HookParametersEnchant(((OEntityPlayerMP)var1).getPlayer(), var3.c, var4));
                    if (!enchantParameters.isCanceled() && enchantParameters.isValid(false))
                    {
                        List<Enchantment> enchantments = enchantParameters.getEnchantments();
                        var4 = new ArrayList();
                        for (Enchantment enchantment : enchantments)
                        {
                            var4.add(new OEnchantmentData(enchantment.getEnchantment(), enchantment.getLevel()));
                        }
                        
                        var1.b(this.c[var2]);
                        Iterator var5 = var4.iterator();
      
                        while (var5.hasNext()) {
                            OEnchantmentData var6 = (OEnchantmentData) var5.next();
      
                            var3.a(var6.a, var6.b);
                        }
      
                        this.a(this.a);
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public void a(OEntityPlayer var1) {
        super.a(var1);
        if (!this.h.I) {
            OItemStack var2 = this.a.c_(0);

            if (var2 != null) {
                var1.b(var2);
            }

        }
    }

    public boolean b(OEntityPlayer var1) {
        return this.h.a(this.i, this.j, this.k) != OBlock.bG.bO ? false : var1.e((double) this.i + 0.5D, (double) this.j + 0.5D, (double) this.k + 0.5D) <= 64.0D;
    }

    public OItemStack a(int var1) {
        OItemStack var2 = null;
        OSlot var3 = (OSlot) this.e.get(var1);

        if (var3 != null && var3.c()) {
            OItemStack var4 = var3.b();

            var2 = var4.j();
            if (var1 != 0) {
                return null;
            }

            if (!this.a(var4, 1, 37, true)) {
                return null;
            }

            if (var4.a == 0) {
                var3.c((OItemStack) null);
            } else {
                var3.d();
            }

            if (var4.a == var2.a) {
                return null;
            }

            var3.b(var4);
        }

        return var2;
    }
}

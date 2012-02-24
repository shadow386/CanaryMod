
public class OItemEnderEye extends OItem {

    public OItemEnderEye(int var1) {
        super(var1);
    }

    public boolean a(OItemStack var1, OEntityPlayer var2, OWorld var3, int var4, int var5, int var6, int var7) {
        int var8 = var3.a(var4, var5, var6);
        int var9 = var3.c(var4, var5, var6);
        
        if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE,
                ((OEntityPlayerMP) var2).getPlayer(),
                this.getBlockInfo(var3, var4, var5, var6, var7), null, new Item(var1)))
            return true;

        if (var2.d(var4, var5, var6) && var8 == OBlock.bK.bO && !OBlockEndPortalFrame.d(var9)) {
            if (var3.I) {
                return true;
            } else {
                var3.c(var4, var5, var6, var9 + 4);
                --var1.a;

                int var10;

                for (var10 = 0; var10 < 16; ++var10) {
                    double var11 = (double) ((float) var4 + (5.0F + c.nextFloat() * 6.0F) / 16.0F);
                    double var13 = (double) ((float) var5 + 0.8125F);
                    double var15 = (double) ((float) var6 + (5.0F + c.nextFloat() * 6.0F) / 16.0F);
                    double var17 = 0.0D;
                    double var19 = 0.0D;
                    double var21 = 0.0D;

                    var3.a("smoke", var11, var13, var15, var17, var19, var21);
                }

                var10 = var9 & 3;
                int var23 = 0;
                int var24 = 0;
                boolean var25 = false;
                boolean var26 = true;
                int var27 = ODirection.f[var10];

                int var29;
                int var28;
                int var31;
                int var30;
                int var32;

                for (var28 = -2; var28 <= 2; ++var28) {
                    var29 = var4 + ODirection.a[var27] * var28;
                    var30 = var6 + ODirection.b[var27] * var28;
                    var31 = var3.a(var29, var5, var30);
                    if (var31 == OBlock.bK.bO) {
                        var32 = var3.c(var29, var5, var30);
                        if (!OBlockEndPortalFrame.d(var32)) {
                            var26 = false;
                            break;
                        }

                        if (!var25) {
                            var23 = var28;
                            var24 = var28;
                            var25 = true;
                        } else {
                            var24 = var28;
                        }
                    }
                }

                if (var26 && var24 == var23 + 2) {
                    for (var28 = var23; var28 <= var24; ++var28) {
                        var29 = var4 + ODirection.a[var27] * var28;
                        var30 = var6 + ODirection.b[var27] * var28;
                        var29 += ODirection.a[var10] * 4;
                        var30 += ODirection.b[var10] * 4;
                        var31 = var3.a(var29, var5, var30);
                        var32 = var3.c(var29, var5, var30);
                        if (var31 != OBlock.bK.bO || !OBlockEndPortalFrame.d(var32)) {
                            var26 = false;
                            break;
                        }
                    }

                    for (var28 = var23 - 1; var28 <= var24 + 1; var28 += 4) {
                        for (var29 = 1; var29 <= 3; ++var29) {
                            var30 = var4 + ODirection.a[var27] * var28;
                            var31 = var6 + ODirection.b[var27] * var28;
                            var30 += ODirection.a[var10] * var29;
                            var31 += ODirection.b[var10] * var29;
                            var32 = var3.a(var30, var5, var31);
                            int var33 = var3.c(var30, var5, var31);

                            if (var32 != OBlock.bK.bO || !OBlockEndPortalFrame.d(var33)) {
                                var26 = false;
                                break;
                            }
                        }
                    }

                    if (var26) {
                        for (var28 = var23; var28 <= var24; ++var28) {
                            for (var29 = 1; var29 <= 3; ++var29) {
                                var30 = var4 + ODirection.a[var27] * var28;
                                var31 = var6 + ODirection.b[var27] * var28;
                                var30 += ODirection.a[var10] * var29;
                                var31 += ODirection.b[var10] * var29;
                                var3.e(var30, var5, var31, OBlock.bJ.bO);
                            }
                        }
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public OItemStack a(OItemStack var1, OWorld var2, OEntityPlayer var3) {
        OMovingObjectPosition var4 = this.a(var2, var3, false);

        if (var4 != null && var4.a == OEnumMovingObjectType.a) {
            int var5 = var2.a(var4.b, var4.c, var4.d);

            if (var5 == OBlock.bK.bO) {
                return var1;
            }
        }

        if (!var2.I) {
            OChunkPosition var7 = var2.b("Stronghold", (int) var3.bm, (int) var3.bn, (int) var3.bo);

            if (var7 != null) {
                OEntityEnderEye var6 = new OEntityEnderEye(var2, var3.bm, var3.bn + 1.62D - (double) var3.bF, var3.bo);

                var6.a((double) var7.a, var7.b, (double) var7.c);
                var2.b((OEntity) var6);
                var2.a(var3, "random.bow", 0.5F, 0.4F / (c.nextFloat() * 0.4F + 0.8F));
                var2.a((OEntityPlayer) null, 1002, (int) var3.bm, (int) var3.bn, (int) var3.bo, 0);
                if (!var3.L.d) {
                    --var1.a;
                }
            }
        }

        return var1;
    }
}


public class OItemBucket extends OItem {

    private int a;

    public OItemBucket(int var1, int var2) {
        super(var1);
        this.bQ = 1;
        this.a = var2;
    }

    public OItemStack a(OItemStack var1, OWorld var2, OEntityPlayer var3) {
        float var4 = 1.0F;
        double var5 = var3.bj + (var3.bm - var3.bj) * (double) var4;
        double var7 = var3.bk + (var3.bn - var3.bk) * (double) var4 + 1.62D - (double) var3.bF;
        double var9 = var3.bl + (var3.bo - var3.bl) * (double) var4;
        boolean var11 = this.a == 0;
        OMovingObjectPosition var12 = this.a(var2, var3, var11);

        if (var12 == null) {
            return var1;
        } else {
            if (var12.a == OEnumMovingObjectType.a) {
                int var13 = var12.b;
                int var14 = var12.c;
                int var15 = var12.d;

                if (!var2.a(var3, var13, var14, var15)) {
                    return var1;
                }
                
                // CanaryMod: Click == placed when handling an empty bucket!
                Block blockClicked = new Block(var2.world, var2.a(var13, var14, var15), var13, var14, var15);

                blockClicked.setFaceClicked(Block.Face.fromId(var12.e));
                Block blockPlaced = new Block(var2.world, 0, var13, var14, var15);

                if (this.a == 0) {
                    if (!var3.d(var13, var14, var15)) {
                        return var1;
                    }
                    
                    if (var2.d(var13, var14, var15) == OMaterial.g && var2.c(var13, var14, var15) == 0) {
                        // Filling a bucket with water!
                        if (var3 instanceof OEntityPlayerMP && (Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE, ((OEntityPlayerMP) var3).getPlayer(), blockPlaced, blockClicked, new Item(var1)) && var3.L.d) {
                            return var1;
                        }
                        
                        var2.e(var13, var14, var15, 0);

                        return new OItemStack(OItem.aw);
                    }

                    if (var2.d(var13, var14, var15) == OMaterial.h && var2.c(var13, var14, var15) == 0) {
                        // Filling a bucket with water!
                        if (var3 instanceof OEntityPlayerMP && (Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE, ((OEntityPlayerMP) var3).getPlayer(), blockPlaced, blockClicked, new Item(var1)) && var3.L.d) {
                            return var1;
                        }
                        var2.e(var13, var14, var15, 0);
                        return new OItemStack(OItem.ax);
                    }
                } else {
                    if (this.a < 0) {
                        return new OItemStack(OItem.av);
                    }

                    if (var12.e == 0) {
                        --var14;
                    }

                    if (var12.e == 1) {
                        ++var14;
                    }

                    if (var12.e == 2) {
                        --var15;
                    }

                    if (var12.e == 3) {
                        ++var15;
                    }

                    if (var12.e == 4) {
                        --var13;
                    }

                    if (var12.e == 5) {
                        ++var13;
                    }

                    if (!var3.d(var13, var14, var15)) {
                        return var1;
                    }

                    if (var2.g(var13, var14, var15) || !var2.d(var13, var14, var15).a()) {
                        if (var2.t.d && this.a == OBlock.A.bO) {
                            var2.a(var5 + 0.5D, var7 + 0.5D, var9 + 0.5D, "random.fizz", 0.5F, 2.6F + (var2.r.nextFloat() - var2.r.nextFloat()) * 0.8F);

                            for (int var16 = 0; var16 < 8; ++var16) {
                                var2.a("largesmoke", (double) var13 + Math.random(), (double) var14 + Math.random(), (double) var15 + Math.random(), 0.0D, 0.0D, 0.0D);
                            }
                        } else {
                            // CanaryMod: bucket empty
                            blockPlaced = new Block(var2.world, a, var13, var14, var15);
                            if (var3 instanceof OEntityPlayerMP && (Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE, ((OEntityPlayerMP) var3).getPlayer(), blockPlaced, blockClicked, new Item(var1))) {
                                return var1;
                            }
                            var2.b(var13, var14, var15, this.a, 0);
                        }

                        if (var3.L.d) {
                            return var1;
                        }

                        return new OItemStack(OItem.av);
                    }
                }
            } else if (this.a == 0 && var12.g instanceof OEntityCow) {
                // CanaryMod hook: onCowMilk
                if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.COW_MILK, etc.getServer().getPlayer(var3.v), new Mob((OEntityCow) var12.g))) {
                    return new OItemStack(OItem.aF);
                }
            }

            return var1;
        }
    }
}

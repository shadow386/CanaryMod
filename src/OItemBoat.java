import java.util.List;


public class OItemBoat extends OItem {

    public OItemBoat(int var1) {
        super(var1);
        this.bO = 1;
    }

    public OItemStack a(OItemStack var1, OWorld var2, OEntityPlayer var3) {
        float var4 = 1.0F;
        float var5 = var3.bv + (var3.bt - var3.bv) * var4;
        float var6 = var3.bu + (var3.bs - var3.bu) * var4;
        double var7 = var3.bj + (var3.bm - var3.bj) * (double) var4;
        double var9 = var3.bk + (var3.bn - var3.bk) * (double) var4 + 1.62D - (double) var3.bF;
        double var11 = var3.bl + (var3.bo - var3.bl) * (double) var4;
        OVec3D var13 = OVec3D.b(var7, var9, var11);
        float var14 = OMathHelper.b(-var6 * 0.017453292F - 3.1415927F);
        float var15 = OMathHelper.a(-var6 * 0.017453292F - 3.1415927F);
        float var16 = -OMathHelper.b(-var5 * 0.017453292F);
        float var17 = OMathHelper.a(-var5 * 0.017453292F);
        float var18 = var15 * var16;
        float var20 = var14 * var16;
        double var21 = 5.0D;
        OVec3D var23 = var13.c((double) var18 * var21, (double) var17 * var21, (double) var20 * var21);
        OMovingObjectPosition var24 = var2.a(var13, var23, true);

        if (var24 == null) {
            return var1;
        } else {
            OVec3D var25 = var3.e(var4);
            boolean var26 = false;
            float var27 = 1.0F;
            List var28 = var2.b((OEntity) var3, var3.bw.a(var25.a * var21, var25.b * var21, var25.c * var21).b((double) var27, (double) var27, (double) var27));

            for (int var29 = 0; var29 < var28.size(); ++var29) {
                OEntity var30 = (OEntity) var28.get(var29);

                if (var30.e_()) {
                    float var31 = var30.j_();
                    OAxisAlignedBB var32 = var30.bw.b((double) var31, (double) var31, (double) var31);

                    if (var32.a(var13)) {
                        var26 = true;
                    }
                }
            }

            if (var26) {
                return var1;
            } else {
                if (var24.a == OEnumMovingObjectType.a) {
                    int var33 = var24.b;
                    int var34 = var24.c;
                    int var35 = var24.d;

                    if (!var2.I) {
                        if (var2.a(var33, var34, var35) == OBlock.aU.bO) {
                            --var34;
                        }
                  
                        // CanaryMod: placing of a boat
                        Block blockClicked = new Block(var2.world, var2.a(var33, var34, var35), var33, var34, var35);

                        blockClicked.setFaceClicked(Block.Face.fromId(var24.e));
                        Block blockPlaced = new Block(var2.world, 0, var33, var34, var35);

                        // CanaryMod: Call hook
                        if (var3 instanceof OEntityPlayerMP && (Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE, ((OEntityPlayerMP) var3).getPlayer(), blockPlaced, blockClicked, new Item(var1))) {
                            return var1;
                        }

                        var2.b((OEntity) (new OEntityBoat(var2, (double) ((float) var33 + 0.5F), (double) ((float) var34 + 1.0F), (double) ((float) var35 + 0.5F))));
                    }

                    if (!var3.L.d) {
                        --var1.a;
                    }
                }

                return var1;
            }
        }
    }
}

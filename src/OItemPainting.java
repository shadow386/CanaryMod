
public class OItemPainting extends OItem {

    public OItemPainting(int var1) {
        super(var1);
    }

    public boolean a(OItemStack var1, OEntityPlayer var2, OWorld var3, int var4, int var5, int var6, int var7) {
        // CanaryMod: store clicked block data
        Block blockClicked = new Block(var3.world, var3.world.getBlockIdAt(var4, var5, var6), var4, var5, var6);

        blockClicked.setFaceClicked(Block.Face.fromId(var7));

        if (var7 == 0) {
            return false;
        } else if (var7 == 1) {
            return false;
        } else {
            byte var8 = 0;

            if (var7 == 4) {
                var8 = 1;
            }

            if (var7 == 3) {
                var8 = 2;
            }

            if (var7 == 5) {
                var8 = 3;
            }

            if (!var2.d(var4, var5, var6)) {
                return false;
            } else {
                OEntityPainting var9 = new OEntityPainting(var3, var4, var5, var6, var8);

                if (var9.j()) {
                    if (!var3.I) {
                        // CanaryMod: Painting place hook
                        if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE, ((OEntityPlayerMP) var2).getPlayer(), null, blockClicked, new Item(var1))) {
                            return false;
                        }
                        var3.b((OEntity) var9);
                    }

                    --var1.a;
                }

                return true;
            }
        }
    }
}

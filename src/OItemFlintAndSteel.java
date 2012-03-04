
public class OItemFlintAndSteel extends OItem {

    public OItemFlintAndSteel(int var1) {
        super(var1);
        this.bQ = 1;
        this.f(64);
    }

    public boolean a(OItemStack var1, OEntityPlayer var2, OWorld var3, int var4, int var5, int var6, int var7) {
		// CanaryMod: Store block data clicked
        Block blockClicked = new Block(var3.world, var3.a(var4, var5, var6), var4, var5, var6);

        blockClicked.setFaceClicked(Block.Face.fromId(var7));
		
        if (var7 == 0) {
            --var5;
        }

        if (var7 == 1) {
            ++var5;
        }

        if (var7 == 2) {
            --var6;
        }

        if (var7 == 3) {
            ++var6;
        }

        if (var7 == 4) {
            --var4;
        }

        if (var7 == 5) {
            ++var4;
        }

        if (!var2.d(var4, var5, var6)) {
            return false;
        } else {
            int var8 = var3.a(var4, var5, var6);

            if (var8 == 0) {
				// CanaryMod: Hook to control ignites AND ligther use
                Block blockPlaced = new Block(var3.world, Block.Type.Fire.getType(), var4, var5, var6);
                Player player = ((OEntityPlayerMP) var2).getPlayer();

                Boolean preventLighter = (Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE, player, blockPlaced, blockClicked, new Item(var1));

                blockPlaced.setStatus(2); // Specifically to mark this ignite as from a lighter
                Boolean preventIgnite = (Boolean) etc.getLoader().callHook(PluginLoader.Hook.IGNITE, blockPlaced, player);

                if (preventIgnite || preventLighter) {
                    return false;
                }
				
                var3.a((double) var4 + 0.5D, (double) var5 + 0.5D, (double) var6 + 0.5D, "fire.ignite", 1.0F, c.nextFloat() * 0.4F + 0.8F);
                var3.e(var4, var5, var6, OBlock.ar.bO);
            }

            var1.a(1, var2);
            return true;
        }
    }
}

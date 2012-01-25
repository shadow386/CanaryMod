
import java.util.ArrayList;


/**
 * Used so we don't have class collisions with Runecraft - Patch by Zeerix
 * 
 * @author James
 */
public class Digging extends OItemInWorldManager {

    /**
     * Creates a digging class
     * 
     * @param world
     */
    public Digging(OWorldServer world) {
        super(world);
    }

    /**
     * Bloop.
     * 
     * @param world
     * @param player
     */
    public Digging(OWorldServer world, OEntityPlayerMP player) {
        this(world);
        b = player;
    }

    // Drop block break lag fix here.
    /**
     * Called when a block is destroyed. We intercept it.
     * 
     * @param x
     * @param y
     * @param z
     * @return
     */
    @Override
    public boolean c(int x, int y, int z) {
        // Block block = etc.getServer().getBlockAt(x, y, z);
        Block block = ((OEntityPlayerMP) b).getPlayer().getWorld().getBlockAt(x, y, z); //

        if (block.getType() == Block.Type.Obsidian.getType()) {
            boolean removeAll = true;
            ArrayList<Player> updatedPlayers = new ArrayList<Player>();

            for (Player player : etc.getServer().getPlayerList()) {
                if (player.getWorld().equals(block.getWorld())) {
                    updatedPlayers.add(player);
                }
            }
            int[][] blockOffsets = new int[][] { new int[] { 0, 1, 0}, new int[] { 1, 0, 0}, new int[] { -1, 0, 0}, new int[] { 0, 0, 1}, new int[] { 0, 0, -1}, new int[] { 0, -1, 0}};

            for (int i = 0; i < blockOffsets.length; i += 1) {
                Block[][] blocks = getPortalBlocks(block.getWorld(), x + blockOffsets[i][0], y + blockOffsets[i][1], z + blockOffsets[i][2]);

                if (blocks != null) {
                    // CanaryMod hook onPortalDestroy
                    if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.PORTAL_DESTROY, (Object) blocks)) {
                        removeAll = false;
                    } else {
                        for (int j = 0; j < 3; j += 1) {
                            for (int k = 0; k < 2; k += 1) {
                                block.getWorld().getWorld().c(blocks[j][k].getX() >> 4, blocks[j][k].getZ() >> 4).a(blocks[j][k].getX() & 15, blocks[j][k].getY(), blocks[j][k].getZ() & 15, 0, false);
                                for (Player player : updatedPlayers) {
                                    player.getUser().a.b(new OPacket53BlockChange(blocks[j][k].getX(), blocks[j][k].getY(), blocks[j][k].getZ(), block.getWorld().getWorld()));
                                }
                            }
                        }
                    }
                }
            }
            if (removeAll == false) {
                return true;
            }
        }
        if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.BLOCK_BROKEN, ((OEntityPlayerMP) b).getPlayer(), block)) {
            return true;
        }
        return super.c(x, y, z);
    }

    private Block[][] getPortalBlocks(World world, int x, int y, int z) {
        int portalId = Block.Type.Portal.getType();

        if (world.getBlockIdAt(x, y, z) == portalId) {
            // These will be equal 1 if the portal is defined on their axis
            // and 0 if not.
            int portalXOffset = (world.getBlockIdAt(x - 1, y, z) == portalId || world.getBlockIdAt(x + 1, y, z) == portalId) ? 1 : 0;
            int portalZOffset = (world.getBlockIdAt(x, y, z - 1) == portalId || world.getBlockIdAt(x, y, z + 1) == portalId) ? 1 : 0;

            // If the portal is either x aligned or z aligned but not both
            // (has neighbor portal in x or z plane but not both)
            if (portalXOffset != portalZOffset) {
                // Get the edge of the portal.
                int portalX = x - ((world.getBlockIdAt(x - 1, y, z) == portalId) ? 1 : 0);
                int portalZ = z - ((world.getBlockIdAt(x, y, z - 1) == portalId) ? 1 : 0);
                int portalY = y;

                while (world.getBlockIdAt(portalX, ++portalY, portalZ) == portalId) {
                    ;
                }
                portalY -= 1;
                // Scan the portal and see if its still all there (2x3
                // formation)
                boolean completePortal = true;
                Block[][] portalBlocks = new Block[3][2];

                for (int i = 0; i < 3 && completePortal == true; i += 1) {
                    for (int j = 0; j < 2 && completePortal == true; j += 1) {
                        portalBlocks[i][j] = world.getBlockAt(portalX + j * portalXOffset, portalY - i, portalZ + j * portalZOffset);
                        if (portalBlocks[i][j].getType() != portalId) {
                            completePortal = false;
                        }
                    }
                }
                if (completePortal == true) {
                    return portalBlocks;
                }
            }
        }
        return null;
    }

    /**
     * Called when a player right-click air with an item in hand. We intercept
     * it.
     * 
     * @param player
     * @param world
     * @param item
     * @param blockToPlace
     * @param blockClicked
     * @return
     */
    public boolean a(OEntityPlayer player, OWorld world, OItemStack item, Block blockToPlace, Block blockClicked) {
        // CanaryMod: only call this hook if we're not using buckets/signs
        if (item != null) {
            if (item.a > 0 && item.c != Item.Type.Sign.getId() && item.c != Item.Type.Bucket.getId() && item.c != Item.Type.WaterBucket.getId() && item.c != Item.Type.LavaBucket.getId() && item.c != Item.Type.MilkBucket.getId()) {
                if (player instanceof OEntityPlayerMP && (Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE, ((OEntityPlayerMP) player).getPlayer(), blockToPlace, blockClicked, new Item(item))) {
                    return false;
                }
            }
        }
        return super.a(player, world, item);
    }

}

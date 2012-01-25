
import java.util.ArrayList;


public class PortalReconstructJob implements Runnable {
    ArrayList<Integer[]> portalBlocks;
    World world;

    public PortalReconstructJob(World world, int portalX, int portalY, int portalZ, boolean portalXAxis) {
        portalBlocks = new ArrayList<Integer[]>();
        this.world = world;

        int portalXOffset = (portalXAxis == true) ? 1 : 0;
        int portalZOffset = 1 - portalXOffset;
        int portalId = Block.Type.Portal.getType();
        int obsidianId = Block.Type.Obsidian.getType();

        portalBlocks.add(new Integer[] { portalX, portalY, portalZ, portalId});
        portalBlocks.add(new Integer[] { portalX + portalXOffset, portalY, portalZ + portalZOffset, portalId});
        portalBlocks.add(new Integer[] { portalX, portalY - 1, portalZ, portalId});
        portalBlocks.add(new Integer[] { portalX + portalXOffset, portalY - 1, portalZ + portalZOffset, portalId});
        portalBlocks.add(new Integer[] { portalX, portalY - 2, portalZ, portalId});
        portalBlocks.add(new Integer[] { portalX + portalXOffset, portalY - 2, portalZ + portalZOffset, portalId});
        portalBlocks.add(new Integer[] { portalX, portalY + 1, portalZ, obsidianId});
        portalBlocks.add(new Integer[] { portalX + portalXOffset, portalY + 1, portalZ + portalZOffset, obsidianId});
        portalBlocks.add(new Integer[] { portalX + portalXOffset * 2, portalY, portalZ + portalZOffset * 2, obsidianId});
        portalBlocks.add(new Integer[] { portalX + portalXOffset * 2, portalY - 1, portalZ + portalZOffset * 2, obsidianId});
        portalBlocks.add(new Integer[] { portalX + portalXOffset * 2, portalY - 2, portalZ + portalZOffset * 2, obsidianId});
        portalBlocks.add(new Integer[] { portalX - portalXOffset, portalY, portalZ - portalZOffset, obsidianId});
        portalBlocks.add(new Integer[] { portalX - portalXOffset, portalY - 1, portalZ - portalZOffset, obsidianId});
        portalBlocks.add(new Integer[] { portalX - portalXOffset, portalY - 2, portalZ - portalZOffset, obsidianId});
        portalBlocks.add(new Integer[] { portalX, portalY - 3, portalZ, obsidianId});
        portalBlocks.add(new Integer[] { portalX + portalXOffset, portalY - 3, portalZ + portalZOffset, obsidianId});
    }

    public void run() {
        ArrayList<Player> updatedPlayers = new ArrayList<Player>();

        for (Player player : etc.getServer().getPlayerList()) {
            if (player.getWorld().equals(world)) {
                updatedPlayers.add(player);
            }
        }
        for (Integer[] frameCoord : portalBlocks) {
            world.getWorld().c(frameCoord[0] >> 4, frameCoord[2] >> 4).a(frameCoord[0] & 15, frameCoord[1], frameCoord[2] & 15, frameCoord[3]);
            for (Player player : updatedPlayers) {
                player.getUser().a.b(new OPacket53BlockChange(frameCoord[0], frameCoord[1], frameCoord[2], world.getWorld()));
            }
        }
    }

}

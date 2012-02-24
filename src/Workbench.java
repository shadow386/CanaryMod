public class Workbench extends ItemArray<OInventoryCraftResult> implements Inventory {
    private final OContainerWorkbench workbench;

    public Workbench(OContainerWorkbench block) {
        super((OInventoryCraftResult) block.b);
        workbench = block;
    }

    public void update() {
        throw new UnsupportedOperationException("Not supported. Please use update(Player player)");
        // This is ugly but we kinda need it. :(
    }

    public void update(Player player) {
        workbench.a((OEntityPlayer) player.getUser());
    }

    public String getName() {
        return container.getName();
    }

    public void setName(String value) {
        container.setName(value);
    }
}

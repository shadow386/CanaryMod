/**
 * Class used for interfacing with note blocks.
 * @author 14mRh4X0r
 */
public class NoteBlock implements ComplexBlock {

    OTileEntityNote note;

    public NoteBlock(OTileEntityNote note) {
        this.note = note;
    }

    @Override
    public int getX() {
        return note.l;
    }

    @Override
    public int getY() {
        return note.m;
    }

    @Override
    public int getZ() {
        return note.n;
    }

    @Override
    public void update() {
        note.l_();
    }

    @Override
    public Block getBlock() {
        return getWorld().getBlockAt(getX(), getY(), getZ());
    }

    @Override
    public World getWorld() {
        return note.k.world;
    }

    /**
     * Returns the current pitch of the note block.
     * @return current pitch
     */
    public byte getNote() {
        return note.a;
    }

    /**
     * Sets the pitch of the note block to a given value.
     * @param note The new pitch
     */
    public void setNote(byte note) {
        this.note.a = note;
    }

}

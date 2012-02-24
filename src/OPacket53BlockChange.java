import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class OPacket53BlockChange extends OPacket {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;

    public OPacket53BlockChange() {
        this.l = true;
    }

    public OPacket53BlockChange(int paramInt1, int paramInt2, int paramInt3, OWorld paramOWorld) {
        this.l = true;
        this.a = paramInt1;
        this.b = paramInt2;
        this.c = paramInt3;
    
        // CanaryMod start
        if (etc.getInstance().isAntiXRayEnabled()) {
            OChunk activeChunk = paramOWorld.b(a, c);
        
            int blockID = paramOWorld.a(paramInt1, paramInt2, paramInt3);

            // If we are attemting to send a hidden block
            if (etc.getDataSource().getAntiXRayBlocks().contains(new Integer(blockID))) {
                Integer index = new Integer((paramInt1 & 0xf) << 11 | (paramInt3 & 0xf) << 7 | paramInt2);

                // If the block is supposed to be hidden
                if (activeChunk.antiXRayBlocks.containsKey(index)) {
                    if (activeChunk.isHidden(paramInt1 & 0xf, paramInt2, paramInt3 & 0xf)) {
                        // Send it as a stone.
                        this.d = 1;
                        this.e = 0;
                    } else {
                        this.d = blockID;
                        this.e = paramOWorld.c(paramInt1, paramInt2, paramInt3);
                    }
                } else {
                    // This should never happen. It means the cache is not complete!
                    synchronized (activeChunk.antiXRayBlocksLock) {
                        activeChunk.antiXRayBlocks.put(index, new Integer(blockID));
                    }
                    if (activeChunk.isHidden(paramInt1 & 0xf, paramInt2, paramInt3 & 0xf)) {
                        // Sent it as a stone.
                        this.d = 1;
                        this.e = 0;
                    } else {
                        this.d = blockID;
                        this.e = paramOWorld.c(paramInt1, paramInt2, paramInt3);
                    }
                }
            } else {
                this.d = blockID;
                this.e = paramOWorld.c(paramInt1, paramInt2, paramInt3);
            }
        } else {
            this.d = paramOWorld.a(paramInt1, paramInt2, paramInt3);
            this.e = paramOWorld.c(paramInt1, paramInt2, paramInt3);
        }
        // CanaryMod end
    }

    public void a(DataInputStream paramDataInputStream) {
        try {
            this.a = paramDataInputStream.readInt();
            this.b = paramDataInputStream.read();
            this.c = paramDataInputStream.readInt();
            this.d = paramDataInputStream.read();
            this.e = paramDataInputStream.read();
        } catch (IOException e) {}
    }

    public void a(DataOutputStream paramDataOutputStream) {
        try {
            paramDataOutputStream.writeInt(this.a);
            paramDataOutputStream.write(this.b);
            paramDataOutputStream.writeInt(this.c);
            paramDataOutputStream.write(this.d);
            paramDataOutputStream.write(this.e);
        } catch (IOException e) {}
    }

    public void a(ONetHandler paramONetHandler) {
        paramONetHandler.a(this);
    }

    public int a() {
        return 11;
    }
}

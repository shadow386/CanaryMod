import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class OPacket52MultiBlockChange extends OPacket {
    public int a;
    public int b;
    public short[] c;
    public byte[] d;
    public byte[] e;
    public int f;

    public OPacket52MultiBlockChange() {
        this.l = true;
    }

    public OPacket52MultiBlockChange(int paramInt1, int paramInt2, short[] paramArrayOfShort, int paramInt3, OWorld paramOWorld) {
        this.l = true;
        this.a = paramInt1;
        this.b = paramInt2;
        this.f = paramInt3;
        this.c = new short[paramInt3];
        this.d = new byte[paramInt3];
        this.e = new byte[paramInt3];
        OChunk localOChunk = paramOWorld.c(paramInt1, paramInt2);
    
        for (int i = 0; i < paramInt3; i++) {
            int j = paramArrayOfShort[i] >> 12 & 0xF;
            int k = paramArrayOfShort[i] >> 8 & 0xF;
            int m = paramArrayOfShort[i] & 0xFF;

            this.c[i] = paramArrayOfShort[i];
            // CanaryMod start
            if (etc.getInstance().isAntiXRayEnabled()) {
                int blockID = localOChunk.a(j, m, k);

                // If we are attemting to send a hidden block
                if (etc.getDataSource().getAntiXRayBlocks().contains(new Integer(blockID))) {
                    Integer index = new Integer(j << 11 | k << 7 | m);

                    // If the block is supposed to be hidden
                    if (localOChunk.antiXRayBlocks.containsKey(index)) {
                        if (localOChunk.isHidden(j, m, k)) {
                            // Send it as a stone.
                            this.d[i] = 1;
                            this.e[i] = 0;
                        } else {
                            this.d[i] = (byte) blockID;
                            this.e[i] = (byte) localOChunk.b(j, m, k);
                        }
                    } else {
                        // This should never happen. It means the cache is not complete!
                        synchronized (localOChunk.antiXRayBlocksLock) {
                            localOChunk.antiXRayBlocks.put(index, new Integer(blockID));
                        }
                        if (localOChunk.isHidden(j, m, k)) {
                            this.d[i] = 1;
                            this.e[i] = 0;
                        } else {
                            this.d[i] = (byte) blockID;
                            this.e[i] = (byte) localOChunk.b(j, m, k);
                        }
                    }
                } else {
                    this.d[i] = (byte) blockID;
                    this.e[i] = (byte) localOChunk.b(j, m, k);
                }
            } else {
                this.d[i] = (byte) localOChunk.a(j, m, k);
                this.e[i] = (byte) localOChunk.b(j, m, k);
            }
            // CanaryMod end
        }
    }

    public void a(DataInputStream paramDataInputStream) {
        try {
            this.a = paramDataInputStream.readInt();
            this.b = paramDataInputStream.readInt();
	
            this.f = (paramDataInputStream.readShort() & 0xFFFF);
            this.c = new short[this.f];
            this.d = new byte[this.f];
            this.e = new byte[this.f];
            for (int i = 0; i < this.f; i++) {
                this.c[i] = paramDataInputStream.readShort();
            }
            paramDataInputStream.readFully(this.d);
            paramDataInputStream.readFully(this.e);
        } catch (IOException e) {}
    }

    public void a(DataOutputStream paramDataOutputStream) {
        try {
            paramDataOutputStream.writeInt(this.a);
            paramDataOutputStream.writeInt(this.b);
            paramDataOutputStream.writeShort((short) this.f);
            for (int i = 0; i < this.f; i++) {
                paramDataOutputStream.writeShort(this.c[i]);
            }
            paramDataOutputStream.write(this.d);
            paramDataOutputStream.write(this.e);
        } catch (IOException e) {}
    }

    public void a(ONetHandler paramONetHandler) {
        paramONetHandler.a(this);
    }

    public int a() {
        return 10 + this.f * 4;
    }
}

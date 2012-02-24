import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


public class OPacket51MapChunk extends OPacket {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public byte[] g;
    private int h;

    public OPacket51MapChunk() {
        this.l = true;
    }

    public OPacket51MapChunk(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, OWorld paramOWorld) {
        this.l = true;
        this.a = paramInt1;
        this.b = paramInt2;
        this.c = paramInt3;
        this.d = paramInt4;
        this.e = paramInt5;
        this.f = paramInt6;

        // CanaryMod if anti xray is enabled we should modify the outgoing packet to hide the blocks.
        byte[] arrayOfByte = paramOWorld.c(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, etc.getInstance().isAntiXRayEnabled());
    
        Deflater localDeflater = new Deflater(-1);

        try {
            localDeflater.setInput(arrayOfByte);
            localDeflater.finish();
            this.g = new byte[paramInt4 * paramInt5 * paramInt6 * 5 / 2];
            this.h = localDeflater.deflate(this.g);
        } finally {
            localDeflater.end();
        }
    }

    public void a(DataInputStream paramDataInputStream) {
        try {
            this.a = paramDataInputStream.readInt();
            this.b = paramDataInputStream.readShort();
            this.c = paramDataInputStream.readInt();
            this.d = (paramDataInputStream.read() + 1);
            this.e = (paramDataInputStream.read() + 1);
            this.f = (paramDataInputStream.read() + 1);

            this.h = paramDataInputStream.readInt();
            byte[] arrayOfByte = new byte[this.h];

            paramDataInputStream.readFully(arrayOfByte);

            this.g = new byte[this.d * this.e * this.f * 5 / 2];

            Inflater localInflater = new Inflater();

            localInflater.setInput(arrayOfByte);
            try {
                localInflater.inflate(this.g);
            } catch (DataFormatException localDataFormatException) {
                throw new IOException("Bad compressed data format");
            } finally {
                localInflater.end();
            }
        } catch (IOException e) {}
    }

    public void a(DataOutputStream paramDataOutputStream) {
        try {
            paramDataOutputStream.writeInt(this.a);
            paramDataOutputStream.writeShort(this.b);
            paramDataOutputStream.writeInt(this.c);
            paramDataOutputStream.write(this.d - 1);
            paramDataOutputStream.write(this.e - 1);
            paramDataOutputStream.write(this.f - 1);

            paramDataOutputStream.writeInt(this.h);
            paramDataOutputStream.write(this.g, 0, this.h);
        } catch (IOException e) {}
    }

    public void a(ONetHandler paramONetHandler) {
        paramONetHandler.a(this);
    }

    public int a() {
        return 17 + this.h;
    }
}

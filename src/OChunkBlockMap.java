public class OChunkBlockMap {
    private static byte[] a = new byte[256];

    public static void a(byte[] paramArrayOfByte) {
        for (int i = 0; i < paramArrayOfByte.length; i++) {
            paramArrayOfByte[i] = a[(paramArrayOfByte[i] & 0xFF)];
        }
    }
  
    // CanaryMod function: Overloaded method to allow the anti xray cache to be built through this iteration.
    public static void a(OChunk chunk) {
        for (int var1 = 0; var1 < chunk.b.length; ++var1) {
            chunk.b[var1] = a[chunk.b[var1] & 255];
            // CanaryMod: If the block is one of the anti xray blocks
            if (etc.getDataSource().getAntiXRayBlocks().contains(new Integer(chunk.b[var1]))) {
                // Add it to the anti xray blocks if it doesn't already exist.
                Integer index = new Integer(var1);

                synchronized (chunk.antiXRayBlocksLock) {
                    if (!chunk.antiXRayBlocks.containsKey(index)) {
                        chunk.antiXRayBlocks.put(index, new Integer(chunk.b[var1]));
                    }
                }
            }
        }
    }

    static {
        try {
            for (int i = 0; i < 256; i++) {
                byte j = (byte) i;

                if ((j != 0) && (OBlock.m[(j & 0xFF)] == null)) {
                    j = 0;
                }
                a[i] = j;
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }
}

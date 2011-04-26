import java.util.Random;

public class OBlockStationary extends OBlockFluid {

    protected OBlockStationary(int paramInt, OMaterial paramOMaterial) {
        super(paramInt, paramOMaterial);

        a(false);
        if (paramOMaterial == OMaterial.g)
            a(true);
    }

    @Override
    public void a(OWorld paramOWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        super.a(paramOWorld, paramInt1, paramInt2, paramInt3, paramInt4);
        if (paramOWorld.a(paramInt1, paramInt2, paramInt3) == bl)
            i(paramOWorld, paramInt1, paramInt2, paramInt3);
    }

    private void i(OWorld paramOWorld, int paramInt1, int paramInt2, int paramInt3) {
        int i = paramOWorld.b(paramInt1, paramInt2, paramInt3);
        paramOWorld.j = true;
        paramOWorld.a(paramInt1, paramInt2, paramInt3, bl - 1, i);
        paramOWorld.b(paramInt1, paramInt2, paramInt3, paramInt1, paramInt2, paramInt3);
        paramOWorld.c(paramInt1, paramInt2, paramInt3, bl - 1, b());
        paramOWorld.j = false;
    }

    @Override
    public void a(OWorld paramOWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
        if (by == OMaterial.g) {
            int i = paramRandom.nextInt(3);
            // CanaryMod: prevent lava from putting something on fire.
            Block block = new Block(paramOWorld.a(paramInt1, paramInt2, paramInt3), paramInt1, paramInt2, paramInt3);
            block.setStatus(1);
            if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.IGNITE, block, null))
                for (int j = 0; j < i; j++) {
                    paramInt1 += paramRandom.nextInt(3) - 1;
                    paramInt2++;
                    paramInt3 += paramRandom.nextInt(3) - 1;
                    int k = paramOWorld.a(paramInt1, paramInt2, paramInt3);
                    if (k == 0) {
                        if ((j(paramOWorld, paramInt1 - 1, paramInt2, paramInt3)) || (j(paramOWorld, paramInt1 + 1, paramInt2, paramInt3)) || (j(paramOWorld, paramInt1, paramInt2, paramInt3 - 1)) || (j(paramOWorld, paramInt1, paramInt2, paramInt3 + 1)) || (j(paramOWorld, paramInt1, paramInt2 - 1, paramInt3)) || (j(paramOWorld, paramInt1, paramInt2 + 1, paramInt3))) {
                            paramOWorld.e(paramInt1, paramInt2, paramInt3, OBlock.ar.bl);
                            return;
                        }
                    } else if (OBlock.m[k].by.c())
                        return;
                }
        }
    }

    private boolean j(OWorld paramOWorld, int paramInt1, int paramInt2, int paramInt3) {
        return paramOWorld.c(paramInt1, paramInt2, paramInt3).e();
    }
}

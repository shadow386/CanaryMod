import java.util.Random;

public class OBlockPortal extends OBlockBreakable {

    public OBlockPortal(int paramInt1, int paramInt2) {
        super(paramInt1, paramInt2, OMaterial.y, false);
    }

    @Override
    public OAxisAlignedBB d(OWorld paramOWorld, int paramInt1, int paramInt2, int paramInt3) {
        return null;
    }

    @Override
    public void a(OIBlockAccess paramOIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
        float f1;
        float f2;
        if ((paramOIBlockAccess.a(paramInt1 - 1, paramInt2, paramInt3) == bn) || (paramOIBlockAccess.a(paramInt1 + 1, paramInt2, paramInt3) == bn)) {
            f1 = 0.5F;
            f2 = 0.125F;
            a(0.5F - f1, 0.0F, 0.5F - f2, 0.5F + f1, 1.0F, 0.5F + f2);
        } else {
            f1 = 0.125F;
            f2 = 0.5F;
            a(0.5F - f1, 0.0F, 0.5F - f2, 0.5F + f1, 1.0F, 0.5F + f2);
        }
    }

    @Override
    public boolean a() {
        return false;
    }

    public boolean a_(OWorld paramOWorld, int paramInt1, int paramInt2, int paramInt3) {
        int i = 0;
        int j = 0;
        if ((paramOWorld.a(paramInt1 - 1, paramInt2, paramInt3) == OBlock.aq.bn) || (paramOWorld.a(paramInt1 + 1, paramInt2, paramInt3) == OBlock.aq.bn))
            i = 1;
        if ((paramOWorld.a(paramInt1, paramInt2, paramInt3 - 1) == OBlock.aq.bn) || (paramOWorld.a(paramInt1, paramInt2, paramInt3 + 1) == OBlock.aq.bn))
            j = 1;

        if (i == j)
            return false;

        if (paramOWorld.a(paramInt1 - i, paramInt2, paramInt3 - j) == 0) {
            paramInt1 -= i;
            paramInt3 -= j;
        }
        int m;
        for (int k = -1; k <= 2; k++)
            for (m = -1; m <= 3; m++) {
                int n = (k == -1) || (k == 2) || (m == -1) || (m == 3) ? 1 : 0;
                if (((k == -1) || (k == 2)) && ((m == -1) || (m == 3)))
                    continue;
                int i1 = paramOWorld.a(paramInt1 + i * k, paramInt2 + m, paramInt3 + j * k);

                if (n != 0) {
                    if (i1 != OBlock.aq.bn)
                        return false;
                } else if ((i1 != 0) && (i1 != OBlock.as.bn))
                    return false;
            }

        paramOWorld.o = true;
        for (int k = 0; k < 2; k++)
            for (m = 0; m < 3; m++)
                paramOWorld.e(paramInt1 + i * k, paramInt2 + m, paramInt3 + j * k, OBlock.bf.bn);
        paramOWorld.o = false;

        return true;
    }

    @Override
    public void a(OWorld paramOWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        int i = 0;
        int j = 1;
        if ((paramOWorld.a(paramInt1 - 1, paramInt2, paramInt3) == bn) || (paramOWorld.a(paramInt1 + 1, paramInt2, paramInt3) == bn)) {
            i = 1;
            j = 0;
        }

        if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.BLOCK_PHYSICS, new Block(paramOWorld.world, bn, paramInt1, paramInt2, paramInt3), false)) {
            int k = paramInt2;
            while (paramOWorld.a(paramInt1, k - 1, paramInt3) == bn)
                k--;
            if (paramOWorld.a(paramInt1, k - 1, paramInt3) != OBlock.aq.bn) {
                paramOWorld.e(paramInt1, paramInt2, paramInt3, 0);
                return;
            }

            int m = 1;
            while ((m < 4) && (paramOWorld.a(paramInt1, k + m, paramInt3) == bn))
                m++;
            if ((m != 3) || (paramOWorld.a(paramInt1, k + m, paramInt3) != OBlock.aq.bn)) {
                paramOWorld.e(paramInt1, paramInt2, paramInt3, 0);
                return;
            }

            int n = (paramOWorld.a(paramInt1 - 1, paramInt2, paramInt3) == bn) || (paramOWorld.a(paramInt1 + 1, paramInt2, paramInt3) == bn) ? 1 : 0;
            int i1 = (paramOWorld.a(paramInt1, paramInt2, paramInt3 - 1) == bn) || (paramOWorld.a(paramInt1, paramInt2, paramInt3 + 1) == bn) ? 1 : 0;
            if ((n != 0) && (i1 != 0)) {
                paramOWorld.e(paramInt1, paramInt2, paramInt3, 0);
                return;
            }

            if (((paramOWorld.a(paramInt1 + i, paramInt2, paramInt3 + j) != OBlock.aq.bn) || (paramOWorld.a(paramInt1 - i, paramInt2, paramInt3 - j) != bn)) && ((paramOWorld.a(paramInt1 - i, paramInt2, paramInt3 - j) != OBlock.aq.bn) || (paramOWorld.a(paramInt1 + i, paramInt2, paramInt3 + j) != bn))) {
                paramOWorld.e(paramInt1, paramInt2, paramInt3, 0);
                return;
            }
        }
    }

    @Override
    public int a(Random paramRandom) {
        return 0;
    }

    @Override
    public void a(OWorld paramOWorld, int paramInt1, int paramInt2, int paramInt3, OEntity paramOEntity) {
        if (paramOEntity.aK == null && paramOEntity.aJ == null)
            paramOEntity.O();
    }
}

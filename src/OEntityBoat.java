import java.util.List;


public class OEntityBoat extends OEntity {

    private int a;
    private double b;
    private double c;
    private double d;
    private double e;
    private double f;
	// CanaryMod Start
    Boat boat = new Boat(this);

    // CanaryMod end

    public OEntityBoat(OWorld var1) {
        super(var1);
        this.bf = true;
        this.b(1.5F, 0.6F);
        this.bF = this.bH / 2.0F;
    }

    protected boolean g_() {
        return false;
    }

    protected void b() {
        this.bY.a(17, new Integer(0));
        this.bY.a(18, new Integer(1));
        this.bY.a(19, new Integer(0));
    }

    public OAxisAlignedBB b_(OEntity var1) {
        return var1.bw;
    }

    public OAxisAlignedBB h() {
        return this.bw;
    }

    public boolean e_() {
        return true;
    }

    public OEntityBoat(OWorld var1, double var2, double var4, double var6) {
        this(var1);
        this.c(var2, var4 + (double) this.bF, var6);
        this.bp = 0.0D;
        this.bq = 0.0D;
        this.br = 0.0D;
        this.bj = var2;
        this.bk = var4;
        this.bl = var6;
		
		// CanaryMod: Creation of the boat
        manager.callHook(PluginLoader.Hook.VEHICLE_CREATE, boat);
    }

    public double x_() {
        return (double) this.bH * 0.0D - 0.30000001192092896D;
    }

    public boolean a(ODamageSource var1, int var2) {
		// CanaryMod: Attack of the boat
        BaseEntity entity = null;
		
		if (var1 != null && var1.a() != null) {
            entity = new BaseEntity(var1.a());
        }
        if ((Boolean) manager.callHook(PluginLoader.Hook.VEHICLE_DAMAGE, boat, entity, var2)) {
            return true;
        }
		
        if (!this.bi.F && !this.bE) {
            this.d(-this.m());
            this.c(10);
            this.b(this.k() + var2 * 10);
            this.aV();
            if (this.k() > 40) {
                if (this.bg != null) {
                    this.bg.b((OEntity) this);
                }

                int var3;

                for (var3 = 0; var3 < 3; ++var3) {
                    this.a(OBlock.x.bO, 1, 0.0F);
                }

                for (var3 = 0; var3 < 2; ++var3) {
                    this.a(OItem.C.bP, 1, 0.0F);
                }

                this.W();
            }

            return true;
        } else {
            return true;
        }
    }

    public boolean o_() {
        return !this.bE;
    }

    public void G_() {
        super.G_();
		// CanaryMod: Update of the boat
        manager.callHook(PluginLoader.Hook.VEHICLE_UPDATE, boat);
		
		double prevX = bj;
        double prevY = bk;
        double prevZ = bl;
		
        if (this.l() > 0) {
            this.c(this.l() - 1);
        }

        if (this.k() > 0) {
            this.b(this.k() - 1);
        }

        this.bj = this.bm;
        this.bk = this.bn;
        this.bl = this.bo;
        byte var1 = 5;
        double var2 = 0.0D;

        for (int var4 = 0; var4 < var1; ++var4) {
            double var5 = this.bw.b + (this.bw.e - this.bw.b) * (double) (var4 + 0) / (double) var1 - 0.125D;
            double var7 = this.bw.b + (this.bw.e - this.bw.b) * (double) (var4 + 1) / (double) var1 - 0.125D;
            OAxisAlignedBB var9 = OAxisAlignedBB.b(this.bw.a, var5, this.bw.c, this.bw.d, var7, this.bw.f);

            if (this.bi.b(var9, OMaterial.g)) {
                var2 += 1.0D / (double) var1;
            }
        }

        double var10 = Math.sqrt(this.bp * this.bp + this.br * this.br);
        double var12;
        double var14;

        if (var10 > 0.15D) {
            var12 = Math.cos((double) this.bs * 3.141592653589793D / 180.0D);
            var14 = Math.sin((double) this.bs * 3.141592653589793D / 180.0D);

            for (int var16 = 0; (double) var16 < 1.0D + var10 * 60.0D; ++var16) {
                double var17 = (double) (this.bS.nextFloat() * 2.0F - 1.0F);
                double var19 = (double) (this.bS.nextInt(2) * 2 - 1) * 0.7D;
                double var21;
                double var23;

                if (this.bS.nextBoolean()) {
                    var21 = this.bm - var12 * var17 * 0.8D + var14 * var19;
                    var23 = this.bo - var14 * var17 * 0.8D - var12 * var19;
                    this.bi.a("splash", var21, this.bn - 0.125D, var23, this.bp, this.bq, this.br);
                } else {
                    var21 = this.bm + var12 + var14 * var17 * 0.7D;
                    var23 = this.bo + var14 - var12 * var17 * 0.7D;
                    this.bi.a("splash", var21, this.bn - 0.125D, var23, this.bp, this.bq, this.br);
                }
            }
        }

        double var25;
        double var27;

        if (this.bi.F) {
            if (this.a > 0) {
                var12 = this.bm + (this.b - this.bm) / (double) this.a;
                var14 = this.bn + (this.c - this.bn) / (double) this.a;
                var25 = this.bo + (this.d - this.bo) / (double) this.a;

                for (var27 = this.e - (double) this.bs; var27 < -180.0D; var27 += 360.0D) {
                    ;
                }

                while (var27 >= 180.0D) {
                    var27 -= 360.0D;
                }

                this.bs = (float) ((double) this.bs + var27 / (double) this.a);
                this.bt = (float) ((double) this.bt + (this.f - (double) this.bt) / (double) this.a);
                --this.a;
                this.c(var12, var14, var25);
                this.c(this.bs, this.bt);
            } else {
                var12 = this.bm + this.bp;
                var14 = this.bn + this.bq;
                var25 = this.bo + this.br;
                this.c(var12, var14, var25);
                if (this.bx) {
                    this.bp *= 0.5D;
                    this.bq *= 0.5D;
                    this.br *= 0.5D;
                }

                this.bp *= 0.9900000095367432D;
                this.bq *= 0.949999988079071D;
                this.br *= 0.9900000095367432D;
            }

        } else {
            if (var2 < 1.0D) {
                var12 = var2 * 2.0D - 1.0D;
                this.bq += 0.03999999910593033D * var12;
            } else {
                if (this.bq < 0.0D) {
                    this.bq /= 2.0D;
                }

                this.bq += 0.007000000216066837D;
            }

            if (this.bg != null) {
                this.bp += this.bg.bp * 0.2D;
                this.br += this.bg.br * 0.2D;
            }

            var12 = 0.4D;
            if (this.bp < -var12) {
                this.bp = -var12;
            }

            if (this.bp > var12) {
                this.bp = var12;
            }

            if (this.br < -var12) {
                this.br = -var12;
            }

            if (this.br > var12) {
                this.br = var12;
            }

            if (this.bx) {
                this.bp *= 0.5D;
                this.bq *= 0.5D;
                this.br *= 0.5D;
            }

            this.a(this.bp, this.bq, this.br);
            if (this.by && var10 > 0.2D) {
                if (!this.bi.F) {
                    this.W();

                    int var29;

                    for (var29 = 0; var29 < 3; ++var29) {
                        this.a(OBlock.x.bO, 1, 0.0F);
                    }

                    for (var29 = 0; var29 < 2; ++var29) {
                        this.a(OItem.C.bP, 1, 0.0F);
                    }
                }
            } else {
                this.bp *= 0.9900000095367432D;
                this.bq *= 0.949999988079071D;
                this.br *= 0.9900000095367432D;
            }

            this.bt = 0.0F;
            var14 = (double) this.bs;
            var25 = this.bj - this.bm;
            var27 = this.bl - this.bo;
            if (var25 * var25 + var27 * var27 > 0.001D) {
                var14 = (double) ((float) (Math.atan2(var27, var25) * 180.0D / 3.141592653589793D));
            }

            double var30;

            for (var30 = var14 - (double) this.bs; var30 >= 180.0D; var30 -= 360.0D) {
                ;
            }

            while (var30 < -180.0D) {
                var30 += 360.0D;
            }

            if (var30 > 20.0D) {
                var30 = 20.0D;
            }

            if (var30 < -20.0D) {
                var30 = -20.0D;
            }

            this.bs = (float) ((double) this.bs + var30);
            this.c(this.bs, this.bt);
			
			// CanaryMod: Change of the boat
            if ((int) bj != (int) prevX || (int) bk != (int) prevY || (int) bl != (int) prevZ) {
                manager.callHook(PluginLoader.Hook.VEHICLE_POSITIONCHANGE, boat, (int) bj, (int) bk, (int) bl);
            }
			
            List var32 = this.bi.b((OEntity) this, this.bw.b(0.20000000298023224D, 0.0D, 0.20000000298023224D));
            int var33;

            if (var32 != null && var32.size() > 0) {
                for (var33 = 0; var33 < var32.size(); ++var33) {
                    OEntity var34 = (OEntity) var32.get(var33);

                    if (var34 != this.bg && var34.e_() && var34 instanceof OEntityBoat) {
                        var34.k(this);
                    }
                }
            }

            for (var33 = 0; var33 < 4; ++var33) {
                int var37 = OMathHelper.b(this.bm + ((double) (var33 % 2) - 0.5D) * 0.8D);
                int var35 = OMathHelper.b(this.bn);
                int var36 = OMathHelper.b(this.bo + ((double) (var33 / 2) - 0.5D) * 0.8D);

                if (this.bi.a(var37, var35, var36) == OBlock.aS.bO) {
                    this.bi.e(var37, var35, var36, 0);
                }
            }

            if (this.bg != null && this.bg.bE) {
                this.bg = null;
            }

        }
    }

    public void i_() {
        if (this.bg != null) {
            double var1 = Math.cos((double) this.bs * 3.141592653589793D / 180.0D) * 0.4D;
            double var3 = Math.sin((double) this.bs * 3.141592653589793D / 180.0D) * 0.4D;

            this.bg.c(this.bm + var1, this.bn + this.x_() + this.bg.V(), this.bo + var3);
        }
    }

    protected void b(ONBTTagCompound var1) {}

    protected void a(ONBTTagCompound var1) {}

    public boolean b(OEntityPlayer var1) {
		// CanaryMod: Entering the boat
        manager.callHook(PluginLoader.Hook.VEHICLE_ENTERED, boat, var1.entity);
		
        if (this.bg != null && this.bg instanceof OEntityPlayer && this.bg != var1) {
            return true;
        } else {
            if (!this.bi.F) {
                var1.b((OEntity) this);
            }

            return true;
        }
    }

    public void b(int var1) {
        this.bY.b(19, Integer.valueOf(var1));
    }

    public int k() {
        return this.bY.c(19);
    }

    public void c(int var1) {
        this.bY.b(17, Integer.valueOf(var1));
    }

    public int l() {
        return this.bY.c(17);
    }

    public void d(int var1) {
        this.bY.b(18, Integer.valueOf(var1));
    }

    public int m() {
        return this.bY.c(18);
    }
}

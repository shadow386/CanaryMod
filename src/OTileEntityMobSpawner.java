
public class OTileEntityMobSpawner extends OTileEntity {

    public int a = -1;
    protected String d = "Pig"; // CanaryMod: private -> protected
    public double b;
    public double c = 0.0D;

    public OTileEntityMobSpawner() {
        super();
        this.a = 20;
    }

    public void a(String var1) {
        this.d = var1;
    }

    public boolean c() {
        return this.k.a((double) this.l + 0.5D, (double) this.m + 0.5D, (double) this.n + 0.5D, 16.0D) != null;
    }

    public void q_() {
        this.c = this.b;
        if (this.c()) {
            double var1 = (double) ((float) this.l + this.k.r.nextFloat());
            double var3 = (double) ((float) this.m + this.k.r.nextFloat());
            double var5 = (double) ((float) this.n + this.k.r.nextFloat());

            this.k.a("smoke", var1, var3, var5, 0.0D, 0.0D, 0.0D);
            this.k.a("flame", var1, var3, var5, 0.0D, 0.0D, 0.0D);

            for (this.b += (double) (1000.0F / ((float) this.a + 200.0F)); this.b > 360.0D; this.c -= 360.0D) {
                this.b -= 360.0D;
            }

            if (!this.k.F) {
                if (this.a == -1) {
                    this.e();
                }

                if (this.a > 0) {
                    --this.a;
                    return;
                }

                byte var7 = 4;

                for (int var8 = 0; var8 < var7; ++var8) {
                    OEntityLiving var9 = (OEntityLiving) ((OEntityLiving) OEntityList.a(this.d, this.k));

                    if (var9 == null) {
                        return;
                    }

                    int var10 = this.k.a(var9.getClass(), OAxisAlignedBB.b((double) this.l, (double) this.m, (double) this.n, (double) (this.l + 1), (double) (this.m + 1), (double) (this.n + 1)).b(8.0D, 4.0D, 8.0D)).size();

                    if (var10 >= 6) {
                        this.e();
                        return;
                    }

                    if (var9 != null) {
                        double var11 = (double) this.l + (this.k.r.nextDouble() - this.k.r.nextDouble()) * 4.0D;
                        double var13 = (double) (this.m + this.k.r.nextInt(3) - 1);
                        double var15 = (double) this.n + (this.k.r.nextDouble() - this.k.r.nextDouble()) * 4.0D;

                        var9.c(var11, var13, var15, this.k.r.nextFloat() * 360.0F, 0.0F);
                        if (var9.l()) {
                            // CanaryMod - set spawner block for spawned entity
                            var9.spawner = (MobSpawner) k.world.getComplexBlock(l, m, n);
                            this.k.b((OEntity) var9);
                            this.k.f(2004, this.l, this.m, this.n, 0);
                            var9.aB();
                            this.e();
                        }
                    }
                }
            }

            super.q_();
        }
    }

    private void e() {
        this.a = 200 + this.k.r.nextInt(600);
    }

    public void a(ONBTTagCompound var1) {
        super.a(var1);
        // CanaryMod - There is no more Monster class
        String entityId = var1.j("EntityId");

        if (entityId.equalsIgnoreCase("Monster")) {
            this.d = "Pig";
        } else {
            this.d = entityId;
        }
        this.a = var1.e("Delay");
    }

    public void b(ONBTTagCompound var1) {
        super.b(var1);
        var1.a("EntityId", this.d);
        var1.a("Delay", (short) this.a);
    }

    public OPacket d() {
        int var1 = OEntityList.a(this.d);

        return new OPacket132UpdateTileEntity(this.l, this.m, this.n, 1, var1);
    }
}

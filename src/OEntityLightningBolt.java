import java.util.List;

public class OEntityLightningBolt extends OEntityWeatherEffect {

   private int b;
   public long a = 0L;
   private int c;


   public OEntityLightningBolt(OWorld var1, double var2, double var4, double var6) {
      super(var1);
      this.c(var2, var4, var6, 0.0F, 0.0F);
      this.b = 2;
      this.a = this.bv.nextLong();
      this.c = this.bv.nextInt(3) + 1;
      if(var1.q >= 2 && var1.a(OMathHelper.b(var2), OMathHelper.b(var4), OMathHelper.b(var6), 10)) {
         int var8 = OMathHelper.b(var2);
         int var9 = OMathHelper.b(var4);
         int var10 = OMathHelper.b(var6);
         if(var1.a(var8, var9, var10) == 0 && OBlock.as.a(var1, var8, var9, var10)) {
            var1.e(var8, var9, var10, OBlock.as.bn);
         }

         for(var8 = 0; var8 < 4; ++var8) {
            var9 = OMathHelper.b(var2) + this.bv.nextInt(3) - 1;
            var10 = OMathHelper.b(var4) + this.bv.nextInt(3) - 1;
            int var11 = OMathHelper.b(var6) + this.bv.nextInt(3) - 1;
            if(var1.a(var9, var10, var11) == 0 && OBlock.as.a(var1, var9, var10, var11)) {
               var1.e(var9, var10, var11, OBlock.as.bn);
            }
         }
      }

   }

   public void o_() {
      super.o_();
      if(this.b == 2) {
         this.aL.a(this.aP, this.aQ, this.aR, "ambient.weather.thunder", 10000.0F, 0.8F + this.bv.nextFloat() * 0.2F);
         this.aL.a(this.aP, this.aQ, this.aR, "random.explode", 2.0F, 0.5F + this.bv.nextFloat() * 0.2F);
      }

      --this.b;
      if(this.b < 0) {
         if(this.c == 0) {
            this.I();
         } else if(this.b < -this.bv.nextInt(10)) {
            --this.c;
            this.b = 1;
            this.a = this.bv.nextLong();
            if(this.aL.a(OMathHelper.b(this.aP), OMathHelper.b(this.aQ), OMathHelper.b(this.aR), 10)) {
               int var1 = OMathHelper.b(this.aP);
               int var2 = OMathHelper.b(this.aQ);
               int var3 = OMathHelper.b(this.aR);
               if(this.aL.a(var1, var2, var3) == 0 && OBlock.as.a(this.aL, var1, var2, var3)) {
                  this.aL.e(var1, var2, var3, OBlock.as.bn);
               }
            }
         }
      }

      if(this.b >= 0) {
         double var4 = 3.0D;
         List var8 = this.aL.b((OEntity)this, OAxisAlignedBB.b(this.aP - var4, this.aQ - var4, this.aR - var4, this.aP + var4, this.aQ + 6.0D + var4, this.aR + var4));

         for(int var6 = 0; var6 < var8.size(); ++var6) {
            OEntity var7 = (OEntity)var8.get(var6);
            if (!(Boolean) manager.callHook(PluginLoader.Hook.LIGHTNING_STRIKE, new BaseEntity(var7)))
                var7.a(this);
         }

         this.aL.n = 2;
      }

   }

   protected void b() {}

   protected void a(ONBTTagCompound var1) {}

   protected void b(ONBTTagCompound var1) {}
}
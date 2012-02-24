import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.MinecraftServer;

public class OWorldServer extends OWorld {

   public OChunkProviderServer J;
   public boolean K = false;
   public boolean L;
   private MinecraftServer M;
   private OIntHashMap N;


   public OWorldServer(MinecraftServer var1, OISaveHandler var2, String var3, int var4, OWorldSettings var5) {
      super(var2, var3, var5, OWorldProvider.a(var4));
      this.M = var1;
      if(this.N == null) {
         this.N = new OIntHashMap();
      }

   }

   public void a(OEntity var1, boolean var2) {
      if(!this.M.o && (var1 instanceof OEntityAnimal || var1 instanceof OEntityWaterMob)) {
         var1.T();
      }

      if(!this.M.p && var1 instanceof OINpc) {
         var1.T();
      }

      if(var1.bg == null || !(var1.bg instanceof OEntityPlayer)) {
         super.a(var1, var2);
      }

   }

   public void b(OEntity var1, boolean var2) {
      super.a(var1, var2);
   }

   protected OIChunkProvider b() {
      OIChunkLoader var1 = this.B.a(this.y);
      this.J = new OChunkProviderServer(this, var1, this.y.b());
      return this.J;
   }

   public List d(int var1, int var2, int var3, int var4, int var5, int var6) {
      ArrayList var7 = new ArrayList();

      for(int var8 = 0; var8 < this.h.size(); ++var8) {
         OTileEntity var9 = (OTileEntity)this.h.get(var8);
         if(var9.l >= var1 && var9.m >= var2 && var9.n >= var3 && var9.l < var4 && var9.m < var5 && var9.n < var6) {
            var7.add(var9);
         }
      }

      return var7;
   }

   public boolean a(OEntityPlayer var1, int var2, int var3, int var4) {
      int var5 = OMathHelper.a(var2 - this.C.c());
      int var6 = OMathHelper.a(var4 - this.C.e());
      if(var5 > var6) {
         var6 = var5;
      }

      return var6 > 16 || this.M.h.h(var1.v);
   }

   protected void c() {
      if(this.N == null) {
         this.N = new OIntHashMap();
      }

      super.c();
   }

   protected void c(OEntity var1) {
      super.c(var1);
      this.N.a(var1.bd, var1);
      OEntity[] var2 = var1.aR();
      if(var2 != null) {
         for(int var3 = 0; var3 < var2.length; ++var3) {
            this.N.a(var2[var3].bd, var2[var3]);
         }
      }

   }

   protected void d(OEntity var1) {
      super.d(var1);
      this.N.d(var1.bd);
      OEntity[] var2 = var1.aR();
      if(var2 != null) {
         for(int var3 = 0; var3 < var2.length; ++var3) {
            this.N.d(var2[var3].bd);
         }
      }

   }

   public OEntity a(int var1) {
      return (OEntity)this.N.a(var1);
   }

   public boolean a(OEntity var1) {
      if(super.a(var1)) {
         this.M.h.a(var1.bm, var1.bn, var1.bo, 512.0D, this.y.h, new OPacket71Weather(var1));
         return true;
      } else {
         return false;
      }
   }

   public void a(OEntity var1, byte var2) {
      OPacket38EntityStatus var3 = new OPacket38EntityStatus(var1.bd, var2);
      this.M.b(this.y.h).b(var1, var3);
   }

   public OExplosion a(OEntity var1, double var2, double var4, double var6, float var8, boolean var9) {
      OExplosion var10 = new OExplosion(this, var1, var2, var4, var6, var8);
      var10.a = var9;
      var10.a();
      var10.a(false);
      this.M.h.a(var2, var4, var6, 64.0D, this.y.h, new OPacket60Explosion(var2, var4, var6, var8, var10.g));
      return var10;
   }

   public void e(int var1, int var2, int var3, int var4, int var5) {
      super.e(var1, var2, var3, var4, var5);
      this.M.h.a((double)var1, (double)var2, (double)var3, 64.0D, this.y.h, new OPacket54PlayNoteBlock(var1, var2, var3, var4, var5));
   }

   public void y() {
      this.B.e();
   }

   protected void i() {
      boolean var1 = this.w();
      super.i();
      if(var1 != this.w()) {
         if(var1) {
            this.M.h.a((OPacket)(new OPacket70Bed(2, 0)));
         } else {
            this.M.h.a((OPacket)(new OPacket70Bed(1, 0)));
         }
      }

   }
}

import java.util.ArrayList;
import java.util.Random;

public class OBlockEnderPortal extends OBlockContainer {

   public static boolean a = false;


   protected OBlockEnderPortal(int var1, OMaterial var2) {
      super(var1, 0, var2);
      this.a(1.0F);
   }

   public OTileEntity a_() {
      return new OTileEntityEndPortal();
   }

   public void a(OIBlockAccess var1, int var2, int var3, int var4) {
      float var5 = 0.0625F;
      this.a(0.0F, 0.0F, 0.0F, 1.0F, var5, 1.0F);
   }

   public void a(OWorld var1, int var2, int var3, int var4, OAxisAlignedBB var5, ArrayList var6) {}

   public boolean a() {
      return false;
   }

   public boolean b() {
      return false;
   }

   public int a(Random var1) {
      return 0;
   }

   public void a(OWorld var1, int var2, int var3, int var4, OEntity var5) {
      if(var5.bh == null && var5.bg == null && var5 instanceof OEntityPlayer && !var1.I) {
    	  // CanaryMod: Check if end is enabled
    	  if (etc.getInstance().isEndEnabled()) {
    		  ((OEntityPlayer)var5).e(1);
    	  }
      }

   }

   public int c() {
      return -1;
   }

   public void a(OWorld var1, int var2, int var3, int var4) {
      if(!a) {
         if(var1.y.h != 0) {
            var1.e(var2, var3, var4, 0);
         }
      }
   }

}

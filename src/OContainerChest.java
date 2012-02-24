
public class OContainerChest extends OContainer {

   private OIInventory a;
   private int b;
   
   //CanaryMod: silenced - Used to determine wether the chest should be opened or closed stealthily
   private boolean silenced;


   public OContainerChest(OIInventory var1, OIInventory var2, boolean silenced) {
      super();
      this.a = var2;
      this.b = var2.c() / 9;
      this.silenced = silenced;
      if (!this.silenced)
      {
          var2.f();
      }
      int var3 = (this.b - 4) * 18;

      int var4;
      int var5;
      for(var4 = 0; var4 < this.b; ++var4) {
         for(var5 = 0; var5 < 9; ++var5) {
            this.a(new OSlot(var2, var5 + var4 * 9, 8 + var5 * 18, 18 + var4 * 18));
         }
      }

      for(var4 = 0; var4 < 3; ++var4) {
         for(var5 = 0; var5 < 9; ++var5) {
            this.a(new OSlot(var1, var5 + var4 * 9 + 9, 8 + var5 * 18, 103 + var4 * 18 + var3));
         }
      }

      for(var4 = 0; var4 < 9; ++var4) {
         this.a(new OSlot(var1, var4, 8 + var4 * 18, 161 + var3));
      }

   }

   public boolean b(OEntityPlayer var1) {
      return this.a.a(var1);
   }

   public OItemStack a(int var1) {
      OItemStack var2 = null;
      OSlot var3 = (OSlot)this.e.get(var1);
      if(var3 != null && var3.c()) {
         OItemStack var4 = var3.b();
         var2 = var4.j();
         if(var1 < this.b * 9) {
            if(!this.a(var4, this.b * 9, this.e.size(), true)) {
               return null;
            }
         } else if(!this.a(var4, 0, this.b * 9, false)) {
            return null;
         }

         if(var4.a == 0) {
            var3.c((OItemStack)null);
         } else {
            var3.d();
         }
      }

      return var2;
   }

   public void a(OEntityPlayer var1) {
      super.a(var1);
      if (!this.silenced)
      {
          this.a.g();
      }
   }
}

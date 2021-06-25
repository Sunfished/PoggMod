package com.sunfished.poggmod.client.render.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sunfished.poggmod.client.render.model.PoggModel;
import com.sunfished.poggmod.common.entities.PoggEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.FoxModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PoggHeldItemLayer extends LayerRenderer<PoggEntity, PoggModel<PoggEntity>>
{
   public PoggHeldItemLayer(IEntityRenderer<PoggEntity, PoggModel<PoggEntity>> p_i50938_1_) {
      super(p_i50938_1_);
   }

   public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, PoggEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
	   boolean flag = p_225628_4_.getMainArm() == HandSide.RIGHT;
	      ItemStack itemstack = flag ? p_225628_4_.getOffhandItem() : p_225628_4_.getMainHandItem();
	      ItemStack itemstack1 = flag ? p_225628_4_.getMainHandItem() : p_225628_4_.getOffhandItem();
	      if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
	         p_225628_1_.pushPose();

	         this.renderArmWithItem(p_225628_4_, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HandSide.RIGHT, p_225628_1_, p_225628_2_, p_225628_3_,p_225628_9_,p_225628_10_);
	         this.renderArmWithItem(p_225628_4_, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HandSide.LEFT, p_225628_1_, p_225628_2_, p_225628_3_,p_225628_9_,p_225628_10_);
	         
	         p_225628_1_.popPose();
	      }
   }
   
   private void renderArmWithItem(LivingEntity p_229135_1_, ItemStack p_229135_2_, ItemCameraTransforms.TransformType p_229135_3_, HandSide p_229135_4_, MatrixStack p_229135_5_, IRenderTypeBuffer p_229135_6_, int p_229135_7_, float f1, float f2) {
	      if (!p_229135_2_.isEmpty()) {
	         p_229135_5_.pushPose();
	         
	         this.getParentModel().translateToHand(p_229135_4_, p_229135_5_);
	         p_229135_5_.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
	         p_229135_5_.mulPose(Vector3f.YP.rotationDegrees(180.0F));
	         boolean flag = p_229135_4_ == HandSide.LEFT;
	         p_229135_5_.translate((double)((this.getParentModel()).armright.x / 16.0F)+0.85D,
	        		 (double)((this.getParentModel()).armright.y / 16.0F),
	        		 (double)((this.getParentModel()).armright.z / 16.0F)-1D);
	         
	         p_229135_5_.mulPose(Vector3f.YP.rotationDegrees(f1));
	         p_229135_5_.mulPose(Vector3f.XP.rotationDegrees(-f2));
	         
	         Minecraft.getInstance().getItemInHandRenderer().renderItem(p_229135_1_, p_229135_2_, p_229135_3_, flag, p_229135_5_, p_229135_6_, p_229135_7_);
	         
	         p_229135_5_.popPose();
	      }
	   }
	
}


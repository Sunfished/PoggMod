package com.sunfished.poggmod.client.render.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sunfished.poggmod.common.entities.PoggEntity;

import net.minecraft.client.renderer.entity.model.TintedAgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// Made with Blockbench 3.8.4
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports

@OnlyIn(Dist.CLIENT)
public class PoggModel<T extends PoggEntity> extends TintedAgeableModel<T> {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer armleft;
	private final ModelRenderer armright;
	private final ModelRenderer legleft;
	private final ModelRenderer legright;
	private final ModelRenderer antenna;

	public PoggModel() {
		texHeight = 64;
		texHeight = 64;

		body = new ModelRenderer(this);
		body.setPos(0.0F, 17.0F, 0.0F);
		

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		body.addChild(head);
		head.texOffs(0, 0).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, 0.0F, false);

		armleft = new ModelRenderer(this);
		armleft.setPos(4.0F, 1.0F, 0.0F);
		head.addChild(armleft);
		setRotationAngle(armleft, 0.0F, 0.0F, 0.3927F);
		armleft.texOffs(12, 20).addBox(-1.0F, -2.0F, -1.5F, 5.0F, 3.0F, 3.0F, 0.0F, false);

		armright = new ModelRenderer(this);
		armright.setPos(-4.0F, 1.0F, 0.0F);
		head.addChild(armright);
		setRotationAngle(armright, 0.0F, 0.0F, -0.3927F);
		armright.texOffs(12, 20).addBox(-4.0F, -2.0F, -1.5F, 5.0F, 3.0F, 3.0F, 0.0F, false);

		legleft = new ModelRenderer(this);
		legleft.setPos(3.0F, 3.0F, 0.0F);
		body.addChild(legleft);
		legleft.texOffs(0, 20).addBox(-2.0F, 1.0F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);

		legright = new ModelRenderer(this);
		legright.setPos(-3.0F, 3.0F, 0.0F);
		body.addChild(legright);
		legright.texOffs(0, 20).addBox(-1.0F, 1.0F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);

		antenna = new ModelRenderer(this);
		antenna.setPos(0.0F, -4.0F, 0.0F);
		body.addChild(antenna);
		setRotationAngle(antenna, -0.3491F, 0.0F, 0.0F);
		antenna.texOffs(0, 26).addBox(-1.5F, -5.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, false);
		antenna.texOffs(12, 26).addBox(-2.0F, -12.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	@Override
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of(this.head,this.antenna);
	}

	@Override
	protected Iterable<ModelRenderer> bodyParts() {
		// TODO Auto-generated method stub
		return ImmutableList.of(this.legleft,this.legright);
	}
	
	@Override
	public void prepareMobModel(T p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
		// TODO Auto-generated method stub
		this.legleft.xRot = MathHelper.cos(p_212843_2_ * 0.6662F) * 1.4F * p_212843_3_;
		this.legright.xRot = MathHelper.cos(p_212843_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_212843_3_;
		
		this.armright.yRot = MathHelper.cos(p_212843_2_ * 0.6662F) * 1.4F * p_212843_3_;
		this.armleft.yRot = MathHelper.cos(p_212843_2_ * 0.6662F) * 1.4F * p_212843_3_;
		
	}

	@Override
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_,
			float p_225597_6_) {
		// TODO Auto-generated method stub
		this.head.xRot = p_225597_6_ * ((float)Math.PI / 180F);
	    this.head.yRot = p_225597_5_ * ((float)Math.PI / 180F);
	    
	    this.antenna.xRot =  -0.3491F + (p_225597_6_ * ((float)Math.PI / 180F));
	    this.antenna.yRot = (p_225597_5_ * ((float)Math.PI / 180F));
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
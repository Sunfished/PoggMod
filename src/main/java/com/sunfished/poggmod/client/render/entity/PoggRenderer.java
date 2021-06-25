package com.sunfished.poggmod.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sunfished.poggmod.PoggMod;
import com.sunfished.poggmod.client.render.entity.layers.PoggAccentLayer;
import com.sunfished.poggmod.client.render.entity.layers.PoggHeldItemLayer;
import com.sunfished.poggmod.client.render.entity.layers.PoggMainLayer;
import com.sunfished.poggmod.client.render.model.PoggModel;
import com.sunfished.poggmod.common.entities.PoggEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class PoggRenderer extends MobRenderer<PoggEntity, PoggModel<PoggEntity>>{

	protected static final ResourceLocation TEXTURE = new ResourceLocation(PoggMod.MOD_ID, "textures/entities/pogg_entity.png");
	protected static final ResourceLocation TEXTURE_ANGRY = new ResourceLocation(PoggMod.MOD_ID, "textures/entities/pogg_entity_face_angry.png");
	protected static final ResourceLocation TEXTURE_IDLE = new ResourceLocation(PoggMod.MOD_ID, "textures/entities/pogg_entity_face_idle.png");
	
	public LayerRenderer<PoggEntity, PoggModel<PoggEntity>> poggMainLayer;// = new PoggMainLayer(this);
	public LayerRenderer<PoggEntity, PoggModel<PoggEntity>> poggAccentLayer;// = new PoggMainLayer(this);

	public PoggRenderer(EntityRendererManager rendererManagerIn)
	{
		super(rendererManagerIn, new PoggModel<PoggEntity>(), 0.25f);
		
		poggMainLayer = new PoggMainLayer(this);
		poggAccentLayer = new PoggAccentLayer(this);
		
		this.addLayer(poggMainLayer);
		this.addLayer(poggAccentLayer);
		
		this.addLayer(new PoggHeldItemLayer(this));
	}
	@Override
	public void render(PoggEntity pogg, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_,
			IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
		// TODO Auto-generated method stub
		super.render(pogg, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
	
	}

	@Override
	public ResourceLocation getTextureLocation(PoggEntity entity) {
		// TODO Auto-generated method stub
		if(entity.isAngry())
			return TEXTURE_ANGRY;
		else if(entity.isInSittingPose())
			return TEXTURE_IDLE;
		return TEXTURE;
	}
	
}

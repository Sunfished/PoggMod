package com.sunfished.poggmod.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sunfished.poggmod.PoggMod;
import com.sunfished.poggmod.client.render.entity.layers.PoggAccentLayer;
import com.sunfished.poggmod.client.render.entity.layers.PoggMainLayer;
import com.sunfished.poggmod.client.render.model.PoggModel;
import com.sunfished.poggmod.common.entities.PoggEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class PoggRenderer extends MobRenderer<PoggEntity, PoggModel<PoggEntity>>{

	protected static final ResourceLocation TEXTURE = new ResourceLocation(PoggMod.MOD_ID, "textures/entities/pogg_entity.png");

	public PoggRenderer(EntityRendererManager rendererManagerIn)
	{
		super(rendererManagerIn, new PoggModel<PoggEntity>(), 0.25f);
		this.addLayer(new PoggMainLayer(this));
		this.addLayer(new PoggAccentLayer(this));
	}
	@Override
	public void render(PoggEntity pogg, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_,
			IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
		// TODO Auto-generated method stub
		super.render(pogg, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
		
		//this.model.setColor(1.0F, 1.0F, 1.0F);
		
	}

	@Override
	public ResourceLocation getTextureLocation(PoggEntity entity) {
		// TODO Auto-generated method stub
		return TEXTURE;
	}
	
}

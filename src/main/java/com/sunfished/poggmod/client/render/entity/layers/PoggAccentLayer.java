package com.sunfished.poggmod.client.render.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sunfished.poggmod.PoggMod;
import com.sunfished.poggmod.client.render.model.PoggModel;
import com.sunfished.poggmod.common.entities.PoggEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class PoggAccentLayer extends LayerRenderer<PoggEntity, PoggModel<PoggEntity>> {
	protected static final ResourceLocation ACCENTTEXTURE = new ResourceLocation(PoggMod.MOD_ID,
			"textures/entities/pogg_entity_accent.png");

	public PoggAccentLayer(IEntityRenderer<PoggEntity, PoggModel<PoggEntity>> p_i50914_1_) {
		super(p_i50914_1_);
	}

	@Override
	public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, PoggEntity p_225628_4_,
			float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_,
			float p_225628_10_) {
		// TODO Auto-generated method stub
		float[] afloat = p_225628_4_.getAccentColor().getTextureDiffuseColors();
		renderColoredCutoutModel(this.getParentModel(), ACCENTTEXTURE, p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_,
				afloat[0], afloat[1], afloat[2]);
	}

}

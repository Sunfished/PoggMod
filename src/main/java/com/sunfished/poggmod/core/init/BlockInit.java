package com.sunfished.poggmod.core.init;

import com.sunfished.poggmod.PoggMod;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

	public static final DeferredRegister<Block> BLOCKS = 
			DeferredRegister.create(ForgeRegistries.BLOCKS, PoggMod.MOD_ID);
	
	public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block",
			() -> new Block(AbstractBlock.Properties.of(Material.DIRT, MaterialColor.COLOR_BLUE)
					.strength(2.0f,10.0f)
					.harvestTool(ToolType.PICKAXE)
					.harvestLevel(-1)
					.sound(SoundType.METAL)
					));
	
	/*public static final RegistryObject<BaseHorizontalBlock> CUSTOM_BLOCK = BLOCKS.register("custom_block",
			() -> new BaseHorizontalBlock(AbstractBlock.Properties.of(Material.WOOD)
					.harvestTool(ToolType.AXE)
					.harvestLevel(1)
					.sound(SoundType.WOOD)
					.requiresCorrectToolForDrops()
					));//*/
}

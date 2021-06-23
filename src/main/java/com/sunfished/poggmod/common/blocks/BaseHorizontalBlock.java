package com.sunfished.poggmod.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;

public class BaseHorizontalBlock extends Block{

	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
	
	public BaseHorizontalBlock(Properties properties)
	{
		super(properties);
		//this.setDefaultState(this.getStateDefinition().)
	}
}

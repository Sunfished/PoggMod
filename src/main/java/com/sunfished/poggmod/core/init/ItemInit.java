package com.sunfished.poggmod.core.init;

import com.sunfished.poggmod.PoggMod;
import com.sunfished.poggmod.common.items.SpecialItem;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PoggMod.MOD_ID);
	
	public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item",
			() -> new Item(new Item.Properties().tab(PoggMod.POGG_ITEMGROUP)));
	
	public static final RegistryObject<SpecialItem> SPECIAL_ITEM = ITEMS.register("special_item",
			() -> new SpecialItem(new Item.Properties().tab(PoggMod.POGG_ITEMGROUP)));
	// Block Items
	public static final RegistryObject<BlockItem> EXAMPLE_BLOCK = ITEMS.register("example_block",
			() -> new BlockItem(BlockInit.EXAMPLE_BLOCK.get(), new Item.Properties().tab(PoggMod.POGG_ITEMGROUP)));
	
	/*public static final RegistryObject<Item> POGG_SPAWN_EGG = ITEMS.register("pogg_spawn_egg",
			() -> new Item(new Item));//*/
	
	public static Item.Properties defaultBuilder() {
		return new Item.Properties().tab(ItemGroup.TAB_MISC);
	}
}

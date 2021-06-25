package com.sunfished.poggmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sunfished.poggmod.client.render.entity.PoggRenderer;
import com.sunfished.poggmod.core.init.BlockInit;
import com.sunfished.poggmod.core.init.ContainerTypeInit;
import com.sunfished.poggmod.core.init.EntityTypeInit;
import com.sunfished.poggmod.core.init.ItemInit;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PoggMod.MOD_ID)
public class PoggMod
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "blitzpogg";
    public static final ItemGroup POGG_ITEMGROUP = new PoggModGroup("poggmodgroup");

    public PoggMod() {
    	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        ContainerTypeInit.CONTAINERS.register(bus);
        //EntityTypeInit.ENTITY_TYPES.register(bus);
        //bus.addListener(PoggMod::setupCommon);
    	bus.addListener(PoggMod::setupClient);
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    	
    }
    
    @SubscribeEvent
	public static void setupClient(final FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.POGG_ENTITY, PoggRenderer::new);
	}
    
    public static class PoggModGroup extends ItemGroup
    {
    	public PoggModGroup(String label)
    	{
    		super(label);
    	}

		@Override
		public ItemStack makeIcon() {
			return ItemInit.EXAMPLE_ITEM.get().getDefaultInstance();
		}
		
    }

}
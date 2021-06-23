package com.sunfished.poggmod.common.entities;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PoggEntity extends TameableEntity {

	private static final DataParameter<Integer> DATA_MAIN_COLOR = EntityDataManager.defineId(PoggEntity.class,
			DataSerializers.INT);
	private static final DataParameter<Integer> DATA_ACCENT_COLOR = EntityDataManager.defineId(PoggEntity.class,
			DataSerializers.INT);

	public DyeColor[] dyeList = new DyeColor[] { DyeColor.BLACK, DyeColor.BLUE, DyeColor.BROWN, DyeColor.CYAN,
			DyeColor.GRAY, DyeColor.GREEN, DyeColor.LIGHT_BLUE, DyeColor.LIGHT_GRAY, DyeColor.LIME, DyeColor.MAGENTA,
			DyeColor.ORANGE, DyeColor.PINK, DyeColor.PURPLE, DyeColor.RED, DyeColor.WHITE, DyeColor.YELLOW };

	public PoggEntity(EntityType<? extends PoggEntity> type, World worldIn) {
		super(type, worldIn);
		this.setTame(false);
		this.setMainColor(getRandomColor());
		this.setAccentColor(getRandomColor());
	}

	@Override
	protected void defineSynchedData() {
		// TODO Auto-generated method stub
		super.defineSynchedData();
		this.entityData.define(DATA_MAIN_COLOR, DyeColor.LIME.getId());
		this.entityData.define(DATA_ACCENT_COLOR, DyeColor.YELLOW.getId());
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
		super.addAdditionalSaveData(p_213281_1_);
		p_213281_1_.putByte("MainColor", (byte) this.getMainColor().getId());
		p_213281_1_.putByte("AccentColor", (byte) this.getAccentColor().getId());
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
		super.readAdditionalSaveData(p_70037_1_);
		if (p_70037_1_.contains("MainColor", 99)) {
			this.setMainColor(DyeColor.byId(p_70037_1_.getInt("MainColor")));
		}
		if (p_70037_1_.contains("AccentColor", 99)) {
			this.setAccentColor(DyeColor.byId(p_70037_1_.getInt("AccentColor")));
		}
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 6.0f));
		this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 8.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ATTACK_DAMAGE, 2.0D);

	}

	@Override
	public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		Item item = itemstack.getItem();

		if (item instanceof DyeItem) {
			DyeColor dyecolor = ((DyeItem) item).getDyeColor();
			if (dyecolor != this.getMainColor()) {
				this.setMainColor(dyecolor);
				if (!player.abilities.instabuild) {
					itemstack.shrink(1);
				}

				return ActionResultType.SUCCESS;
			}
		}

		return super.mobInteract(player, hand);
	}

	public DyeColor getMainColor() {
		return DyeColor.byId(this.entityData.get(DATA_MAIN_COLOR));
	}

	public void setMainColor(DyeColor p_175547_1_) {
		this.entityData.set(DATA_MAIN_COLOR, p_175547_1_.getId());
	}

	public DyeColor getAccentColor() {
		return DyeColor.byId(this.entityData.get(DATA_ACCENT_COLOR));
	}

	public void setAccentColor(DyeColor p_175547_1_) {
		this.entityData.set(DATA_ACCENT_COLOR, p_175547_1_.getId());
	}

	public DyeColor getRandomColor() {
		return this.dyeList[getRandom().nextInt(dyeList.length)];
	}

}

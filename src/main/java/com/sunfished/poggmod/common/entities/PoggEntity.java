package com.sunfished.poggmod.common.entities;

import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PoggEntity extends TameableEntity implements IAngerable {

	// Colors
	private static final DataParameter<Integer> DATA_MAIN_COLOR = EntityDataManager.defineId(PoggEntity.class,
			DataSerializers.INT);
	private static final DataParameter<Integer> DATA_ACCENT_COLOR = EntityDataManager.defineId(PoggEntity.class,
			DataSerializers.INT);
	public DyeColor[] dyeList = new DyeColor[] { DyeColor.BLACK, DyeColor.BLUE, DyeColor.BROWN, DyeColor.CYAN,
			DyeColor.GRAY, DyeColor.GREEN, DyeColor.LIGHT_BLUE, DyeColor.LIGHT_GRAY, DyeColor.LIME, DyeColor.MAGENTA,
			DyeColor.ORANGE, DyeColor.PINK, DyeColor.PURPLE, DyeColor.RED, DyeColor.WHITE, DyeColor.YELLOW };

	// Interest
	private static final DataParameter<Boolean> DATA_INTERESTED_ID = EntityDataManager.defineId(PoggEntity.class,
			DataSerializers.BOOLEAN);
	private float interestedAngle;
	private float interestedAngleO;

	// Anger
	private static final DataParameter<Integer> DATA_REMAINING_ANGER_TIME = EntityDataManager.defineId(PoggEntity.class,
			DataSerializers.INT);
	public static final Predicate<LivingEntity> PREY_SELECTOR = (p_213440_0_) -> {
		EntityType<?> entitytype = p_213440_0_.getType();
		return entitytype == EntityType.SHEEP || entitytype == EntityType.RABBIT || entitytype == EntityType.FOX;
	};
	private static final RangedInteger PERSISTENT_ANGER_TIME = TickRangeConverter.rangeOfSeconds(20, 39);
	private UUID persistentAngerTarget;

	// Constructor
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
		this.entityData.define(DATA_INTERESTED_ID, false);
		this.entityData.define(DATA_MAIN_COLOR, DyeColor.LIME.getId());
		this.entityData.define(DATA_ACCENT_COLOR, DyeColor.YELLOW.getId());
		this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
		super.addAdditionalSaveData(p_213281_1_);
		p_213281_1_.putByte("MainColor", (byte) this.getMainColor().getId());
		p_213281_1_.putByte("AccentColor", (byte) this.getAccentColor().getId());
		this.addPersistentAngerSaveData(p_213281_1_);
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
		if (!level.isClientSide) // FORGE: allow this entity to be read from nbt on client. (Fixes MC-189565)
			this.readPersistentAngerSaveData((ServerWorld) this.level, p_70037_1_);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, new SitGoal(this));
		// this.goalSelector.addGoal(3, new PoggEntity.AvoidEntityGoal(this,
		// LlamaEntity.class, 24.0F, 1.5D, 1.5D));
		this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
		// this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		// this.goalSelector.addGoal(9, new BegGoal(this, 8.0F));
		this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(10, new LookRandomlyGoal(this));

		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(4,
				new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, false));
		this.targetSelector.addGoal(8, new ResetAngerGoal<>(this, true));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.ATTACK_SPEED, 0.2f);

	}

	@Override
	public void setTame(boolean taming) {
		super.setTame(taming);
		if (taming)
		{
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
			this.setHealth(20.0F);
		}
		else
		{
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
		}

		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level.isClientSide && !this.isPathFinding() && this.onGround) {
			this.level.broadcastEntityEvent(this, (byte) 8);
		}

		if (!this.level.isClientSide) {
			this.updatePersistentAnger((ServerWorld) this.level, true);
		}

	}

	@Override
	public void tick() {
		super.tick();
		if (this.isAlive()) {
			this.interestedAngleO = this.interestedAngle;
			if (this.isInterested()) {
				this.interestedAngle += (1.0F - this.interestedAngle) * 0.2F;
			} else {
				this.interestedAngle += (0.0F - this.interestedAngle) * 0.2F;
			}
		}
	}

	@Override
	public boolean wantsToAttack(LivingEntity target, LivingEntity p_142018_2_) {
		if (!(target instanceof CreeperEntity) && !(target instanceof GhastEntity)) {
			if (target instanceof PoggEntity) {
				PoggEntity poggentity = (PoggEntity) target;
				return !poggentity.isTame() || poggentity.getOwner() != p_142018_2_;
			} else if (target instanceof PlayerEntity && p_142018_2_ instanceof PlayerEntity
					&& !((PlayerEntity) p_142018_2_).canHarmPlayer((PlayerEntity) target)) {
				return false;
			} else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity) target).isTamed()) {
				return false;
			} else {
				return !(target instanceof TameableEntity) || !((TameableEntity) target).isTame();
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean hurt(DamageSource damagesource, float damage) {
		if (this.isInvulnerableTo(damagesource))
		{
			return false;
		}
		else
		{
			Entity entity = damagesource.getEntity();
			this.setOrderedToSit(false);
			if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity))
			{
				damage = (damage + 1.0F) / 2.0F;
			}//*/

			return super.hurt(damagesource, damage);
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean flag = entity.hurt(DamageSource.mobAttack(this),(float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
		if (flag) {
			this.doEnchantDamageEffects(this, entity);
		}

		return flag;
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

		// Change Color
		if (item instanceof DyeItem) {

			DyeColor dyecolor = ((DyeItem) item).getDyeColor();

			if (Hand.MAIN_HAND == hand) {
				if (dyecolor != this.getMainColor()) {
					this.setMainColor(dyecolor);
					if (!player.abilities.instabuild)
						itemstack.shrink(1);

					return ActionResultType.SUCCESS;
				}
			}

			else {
				if (dyecolor != this.getAccentColor()) {
					this.setAccentColor(dyecolor);
					if (!player.abilities.instabuild)
						itemstack.shrink(1);

					return ActionResultType.SUCCESS;
				}
			}
		}

		// Already Tamed Events
		// Allows sitting
		if (this.isTame()) {
			
			if (player.isCrouching())
			{
				boolean handempty = itemstack.isEmpty();
				boolean poggempty = this.getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty();
				
				if (!handempty && poggempty)
				{
					this.setItemSlot(EquipmentSlotType.MAINHAND, itemstack);
					player.setItemInHand(hand, ItemStack.EMPTY);
					return ActionResultType.SUCCESS;
				}
				else if(!poggempty && handempty)
				{
					player.setItemInHand(hand, this.getItemBySlot(EquipmentSlotType.MAINHAND));
					this.setItemSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
					return ActionResultType.SUCCESS;
				}
			}
			ActionResultType actionresulttype = super.mobInteract(player, hand);
			if ((!actionresulttype.consumesAction() || this.isBaby()) && this.isOwnedBy(player)) {
				this.setOrderedToSit(!this.isOrderedToSit());
				this.jumping = false;
				this.navigation.stop();
				this.setTarget((LivingEntity) null);
				return ActionResultType.SUCCESS;
			}

			return actionresulttype;
		}

		// Tame
		else if (item == Items.BONE) {
			if (!player.abilities.instabuild) {
				itemstack.shrink(1);
			}

			if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
				this.tame(player);
				this.navigation.stop();
				this.setTarget((LivingEntity) null);
				this.level.broadcastEntityEvent(this, (byte) 7);
			} else {
				this.level.broadcastEntityEvent(this, (byte) 6);
			}

			return ActionResultType.SUCCESS;
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

	public int getRemainingPersistentAngerTime() {
		return this.entityData.get(DATA_REMAINING_ANGER_TIME);
	}

	public void setRemainingPersistentAngerTime(int p_230260_1_) {
		this.entityData.set(DATA_REMAINING_ANGER_TIME, p_230260_1_);
	}

	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.randomValue(this.random));
	}

	@Nullable
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	public void setPersistentAngerTarget(@Nullable UUID p_230259_1_) {
		this.persistentAngerTarget = p_230259_1_;
	}

	public void setIsInterested(boolean p_70918_1_) {
		this.entityData.set(DATA_INTERESTED_ID, p_70918_1_);
	}

	public boolean isInterested() {
		return this.entityData.get(DATA_INTERESTED_ID);
	}

}

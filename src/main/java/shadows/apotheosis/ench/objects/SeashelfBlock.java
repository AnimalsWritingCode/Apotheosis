package shadows.apotheosis.ench.objects;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import shadows.apotheosis.ApotheosisObjects;

public class SeashelfBlock extends Block implements IEnchantingBlock {

	public static final IntegerProperty INFUSION = IntegerProperty.create("infusion", 0, 5);

	public SeashelfBlock() {
		super(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(2, 10).sound(SoundType.STONE));
	}

	@Override
	public float getEnchantPowerBonus(BlockState state, IWorldReader world, BlockPos pos) {
		return 1.5F + state.getValue(INFUSION) * 0.1F;
	}

	@Override
	public float getArcanaBonus(BlockState state, IWorldReader world, BlockPos pos) {
		return 1.5F + state.getValue(INFUSION) * 0.1F;
	}

	@Override
	public float getMaxEnchantingPower(BlockState state, IWorldReader world, BlockPos pos) {
		return 22.5F + state.getValue(INFUSION) * 1.5F;
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(INFUSION);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		ItemStack stack = ctx.getItemInHand();
		return this.defaultBlockState().setValue(INFUSION, Math.min(5, EnchantmentHelper.getItemEnchantmentLevel(ApotheosisObjects.SEA_INFUSION, stack)));
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		ItemStack stack = new ItemStack(this);
		if (state.getValue(INFUSION) > 0) EnchantmentHelper.setEnchantments(ImmutableMap.of(ApotheosisObjects.SEA_INFUSION, state.getValue(INFUSION)), stack);
		return Arrays.asList(stack);
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		ItemStack stack = new ItemStack(this);
		if (state.getValue(INFUSION) > 0) EnchantmentHelper.setEnchantments(ImmutableMap.of(ApotheosisObjects.SEA_INFUSION, state.getValue(INFUSION)), stack);
		return stack;
	}

}
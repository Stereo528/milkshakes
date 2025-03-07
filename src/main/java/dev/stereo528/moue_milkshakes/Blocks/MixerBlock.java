package dev.stereo528.moue_milkshakes.Blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.stereo528.moue_milkshakes.Util.Registar;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MixerBlock extends BaseEntityBlock {

    public MixerBlock(Properties properties) {
        super(properties);
    }

    public static final MapCodec<MixerBlock> CODEC = simpleCodec(MixerBlock::new);

    @Override
    public MapCodec<? extends BaseEntityBlock> codec() {return CODEC;}

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MixerBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createBlockEntityTicker(level, blockEntityType, Registar.MIXERBET);
    }

    @Nullable
    protected <T extends BlockEntity> BlockEntityTicker<T> createBlockEntityTicker(Level level, BlockEntityType<T> blockEntityType, BlockEntityType<? extends MixerBlockEntity> blockEntityType2) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, blockEntityType2, MixerBlockEntity::serverTick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, @NotNull Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            openContainer(level, blockPos, player);
            return InteractionResult.CONSUME;
        }
    }

    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MixerBlockEntity) {
            player.openMenu((MixerBlockEntity) blockEntity);
        }
    }

    @Override
    protected void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.is(blockState2.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof MixerBlockEntity) {
                if (level instanceof ServerLevel) {
                    Containers.dropContents(level, blockPos, (MixerBlockEntity)blockEntity);
                }
            }
            super.onRemove(blockState, level, blockPos, blockState2, bl);
        }
    }


}

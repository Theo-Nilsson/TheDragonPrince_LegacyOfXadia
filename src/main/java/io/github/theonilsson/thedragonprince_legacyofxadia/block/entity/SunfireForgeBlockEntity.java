package io.github.theonilsson.thedragonprince_legacyofxadia.block.entity;

import io.github.theonilsson.thedragonprince_legacyofxadia.block.ModBlocks;
import io.github.theonilsson.thedragonprince_legacyofxadia.recipe.SunForgeRecipe;
import io.github.theonilsson.thedragonprince_legacyofxadia.screen.SunfireForgeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SunfireForgeBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(10);

    private static final int INPUT_SLOT_0 = 0;
    private static final int INPUT_SLOT_1 = 1;
    private static final int INPUT_SLOT_2 = 2;
    private static final int INPUT_SLOT_3 = 3;
    private static final int INPUT_SLOT_4 = 4;
    private static final int INPUT_SLOT_5 = 5;
    private static final int INPUT_SLOT_6 = 6;
    private static final int INPUT_SLOT_7 = 7;
    private static final int INPUT_SLOT_8 = 8;
    private static final int OUTPUT_SLOT = 9;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public SunfireForgeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SUNFIRE_FORGE_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> SunfireForgeBlockEntity.this.progress;
                    case 1 -> SunfireForgeBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int i1) {
                switch (i) {
                    case 0 -> SunfireForgeBlockEntity.this.progress = i1;
                    case 1 -> SunfireForgeBlockEntity.this.maxProgress = i1;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.tdp_lox.sunfire_forge");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new SunfireForgeMenu(i, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("sunfire_forge.progress", progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("sunfire_forge.progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (hasRecipe()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);
            
            if (hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<SunForgeRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());
        this.itemHandler.extractItem(INPUT_SLOT_0, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_1, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_2, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_3, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_4, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_5, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_6, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_7, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_8, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        /* boolean hasInput1 = this.itemHandler.getStackInSlot(INPUT_SLOT_0).getItem() == Items.LAVA_BUCKET;
        boolean hasInput2 = this.itemHandler.getStackInSlot(INPUT_SLOT_1).getItem() == ModItems.STEEL_DRAGON_INGOT.get();
        boolean hasInput3 = this.itemHandler.getStackInSlot(INPUT_SLOT_2).getItem() == Items.LAVA_BUCKET;
        boolean hasInput4 = this.itemHandler.getStackInSlot(INPUT_SLOT_3).getItem() == IafItemRegistry.FIRE_DRAGON_BLOOD.get();
        boolean hasInput5 = this.itemHandler.getStackInSlot(INPUT_SLOT_4).getItem() == ModItems.FIRERUNE.get();
        boolean hasInput6 = this.itemHandler.getStackInSlot(INPUT_SLOT_5).getItem() == IafItemRegistry.FIRE_DRAGON_BLOOD.get();
        boolean hasInput7 = this.itemHandler.getStackInSlot(INPUT_SLOT_6).getItem() == Items.LEATHER;
        boolean hasInput8 = this.itemHandler.getStackInSlot(INPUT_SLOT_7).getItem() == ModItems.WITHERSTEEL_ROD.get();
        boolean hasInput9 = this.itemHandler.getStackInSlot(INPUT_SLOT_8).getItem() == IafItemRegistry.FIRE_DRAGON_HEART.get();
        ItemStack result = new ItemStack(ModItems.SUNFIREBLADE.get()); */

        Optional<SunForgeRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        boolean blocksUnder = checkBlocksUnderneath();
        boolean blocksAbove = checkBlocksAbove();

        return /* hasInput1 && hasInput2 && hasInput3 && hasInput4 && hasInput5 && hasInput6 && hasInput7 && hasInput8 && hasInput9 && */ blocksAbove && blocksUnder && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<SunForgeRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(SunForgeRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean checkBlocksUnderneath() {
        BlockPos pos = this.worldPosition.below();
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos checkPos = pos.offset(x, 0, z);
                BlockState blockState = this.level.getBlockState(checkPos);
                if (!isValidBlockUnderneath(blockState)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkBlocksAbove() {
        BlockPos pos = this.worldPosition.above(2);
        BlockState blockState = this.level.getBlockState(pos);
        boolean isCorrectBlock = blockState.is(ModBlocks.SUNFORGE.get());
        return isCorrectBlock;
    }

    private boolean isValidBlockUnderneath(BlockState blockState) {
        return blockState.is(ModBlocks.SUNFIRE_BRICKS.get());
    }
}

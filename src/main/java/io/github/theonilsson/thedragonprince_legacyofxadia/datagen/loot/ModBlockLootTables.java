package io.github.theonilsson.thedragonprince_legacyofxadia.datagen.loot;

import io.github.theonilsson.thedragonprince_legacyofxadia.block.ModBlocks;
import io.github.theonilsson.thedragonprince_legacyofxadia.block.crops.ScorchBerryBushBlock;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.SUNFIRE_BRICKS.get());
        this.dropSelf(ModBlocks.SUNFIRE_STONE.get());
        this.dropSelf(ModBlocks.SUNFIRE_FORGE.get());
        this.dropSelf(ModBlocks.SUNFORGE.get());

        this.dropSelf(ModBlocks.SUNFIRE_STONE_STAIRS.get());
        this.dropSelf(ModBlocks.SUNFIRE_STONE_BUTTON.get());
        this.dropSelf(ModBlocks.SUNFIRE_STONE_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.SUNFIRE_STONE_WALL.get());
        this.dropSelf(ModBlocks.SUNFIRE_STONE_TRAPDOOR.get());

        this.add(ModBlocks.SUNFIRE_STONE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SUNFIRE_STONE_SLAB.get()));
        this.add(ModBlocks.SUNFIRE_STONE_DOOR.get(),
                block -> createDoorTable(ModBlocks.SUNFIRE_STONE_DOOR.get()));

        this.add(ModBlocks.SCORCH_BERRY_BUSH.get(), createScorchBerryBushDrop(ModBlocks.SCORCH_BERRY_BUSH.get()));
    }

    private LootTable.Builder createScorchBerryBushDrop(Block block) {
        LootItemCondition.Builder condition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(ScorchBerryBushBlock.AGE, 3));

        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .when(condition)
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.SCORCH_BERRIES.get())
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2))))
                .withPool(LootPool.lootPool()
                        .when(condition)
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.SCORCH_BERRIES.get())));
    }

    protected LootTable.Builder createOreDrops(Block pBlock, Item pItem, float min, float max) {
        return createSilkTouchDispatchTable(pBlock, (LootPoolEntryContainer.Builder)this.applyExplosionDecay(pBlock, LootItem.lootTableItem(pItem).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}

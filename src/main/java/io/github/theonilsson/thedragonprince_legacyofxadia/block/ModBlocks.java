package io.github.theonilsson.thedragonprince_legacyofxadia.block;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.ModItems;
import io.github.theonilsson.thedragonprince_legacyofxadia.block.crops.ScorchBerryBushBlock;
import io.github.theonilsson.thedragonprince_legacyofxadia.block.sunforge.SunForgeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, LOX.MOD_ID);

    public static final RegistryObject<Block> SUNFIRE_BRICKS = registerBlock("sunfire_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    public static final RegistryObject<Block> SUNFIRE_STONE = registerBlock("sunfire_stone",
            () -> new Block(BlockBehaviour.Properties.copy(SUNFIRE_BRICKS.get())));

    public static final RegistryObject<Block> SUNFIRE_FORGE = registerBlock("sunfire_forge",
            () -> new SunForgeBlock(BlockBehaviour.Properties.copy(SUNFIRE_BRICKS.get()).noOcclusion().lightLevel(s -> 7)));

    public static final RegistryObject<Block> SUNFORGE = registerBlock("sunforge",
            () -> new Block(BlockBehaviour.Properties.copy(SUNFIRE_STONE.get()).noOcclusion().lightLevel(s -> 15)){
                @Override
                public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
                    super.onNeighborChange(state, level, pos, neighbor);
                }
            });

    public static final RegistryObject<Block> SCORCH_BERRY_BUSH = BLOCKS.register("scorch_berry_bush",
            () -> new ScorchBerryBushBlock(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH).noOcclusion().noCollission()));

    public static final RegistryObject<Block> SUNFIRE_STONE_STAIRS = registerBlock("sunfire_stone_stairs",
            () -> new StairBlock(() -> ModBlocks.SUNFIRE_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(SUNFIRE_STONE.get())));

    public static final RegistryObject<Block> SUNFIRE_STONE_SLAB = registerBlock("sunfire_stone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(SUNFIRE_STONE.get())));

    public static final RegistryObject<Block> SUNFIRE_STONE_BUTTON = registerBlock("sunfire_stone_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.copy(SUNFIRE_STONE.get()),
            BlockSetType.STONE, 10, true));

    public static final RegistryObject<Block> SUNFIRE_STONE_PRESSURE_PLATE = registerBlock("sunfire_stone_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(SUNFIRE_STONE.get()),
                    BlockSetType.STONE));

    public static final RegistryObject<Block> SUNFIRE_STONE_WALL = registerBlock("sunfire_stone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(SUNFIRE_STONE.get())));

    public static final RegistryObject<Block> SUNFIRE_STONE_DOOR = registerBlock("sunfire_stone_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(SUNFIRE_STONE.get()).noOcclusion(), BlockSetType.STONE));

    public static final RegistryObject<Block> SUNFIRE_STONE_TRAPDOOR = registerBlock("sunfire_stone_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(SUNFIRE_STONE.get()).noOcclusion(), BlockSetType.STONE));

    public static final RegistryObject<Block> TENEBRIS_PRAESIDIUM = registerBlock("tenebris_praesidium",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BRICKS).strength(-1.0F, 3600000.0F).noLootTable()));

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {

        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}

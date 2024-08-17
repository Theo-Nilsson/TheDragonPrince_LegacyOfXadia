package io.github.theonilsson.thedragonprince_legacyofxadia.datagen;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.ModItems;
import io.github.theonilsson.thedragonprince_legacyofxadia.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, LOX.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.Items.INGOT)
                .add(Items.IRON_INGOT)
                .add(Items.NETHERITE_INGOT)
                .add(IafItemRegistry.DRAGONSTEEL_FIRE_INGOT.get())
                .add(IafItemRegistry.DRAGONSTEEL_ICE_INGOT.get())
                .add(IafItemRegistry.DRAGONSTEEL_LIGHTNING_INGOT.get())
                .add(ModItems.SUN_INGOT.get())
                .add(ModItems.STEEL_INGOT.get());

        this.tag(ModTags.Items.STEEL)
                .add(ModItems.STEEL_INGOT.get());
    }
}

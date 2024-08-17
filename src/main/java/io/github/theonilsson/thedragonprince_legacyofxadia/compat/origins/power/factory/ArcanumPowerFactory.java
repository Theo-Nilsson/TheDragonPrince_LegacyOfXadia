package io.github.theonilsson.thedragonprince_legacyofxadia.compat.origins.power.factory;

import io.github.edwinmindcraft.apoli.api.ApoliAPI;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.compat.origins.power.ModPowers;
import io.github.theonilsson.thedragonprince_legacyofxadia.compat.origins.power.configuration.ArcanumPowerConfiguration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import org.apache.commons.lang3.StringUtils;

public class ArcanumPowerFactory extends PowerFactory<ArcanumPowerConfiguration> {
    public ArcanumPowerFactory() {
        super(ArcanumPowerConfiguration.CODEC);
    }

    public static boolean hasPower(Entity entity) {
        IPowerContainer powerContainer = ApoliAPI.getPowerContainer(entity);
        return powerContainer != null && powerContainer.hasPower(ModPowers.ARCANUM.get());
    }

    private void addData(ConfiguredPower<ArcanumPowerConfiguration, ?> configuration, Entity entity) {
        CompoundTag tag = entity.getPersistentData();
        ListTag arcanumList;
        if (tag.contains("arcanumList", 9)) {
            arcanumList = tag.getList("arcanumList", 9); // 9 is the tag type for lists
        } else {
            arcanumList = new ListTag();
            tag.put("arcanumList", arcanumList);
        }
        boolean exists = false;
        for (Tag arcanumTag : arcanumList) {
            StringTag arcanum = StringTag.valueOf(arcanumTag.getAsString());
            if (!arcanum.getAsString().equals(configuration.getConfiguration().getArcanumType())) {
                exists = true;
            }
        }
        if (!exists) {
            arcanumList.add(StringTag.valueOf(configuration.getConfiguration().getArcanumType()));
            tag.putBoolean("%sArcanumAddedByPower".formatted(configuration.getConfiguration().getArcanumType()), true);
            LOX.LOGGER.info("{}: Added \"{}\" arcanum to persistent data.", entity.getDisplayName(), StringUtils.capitalize(configuration.getConfiguration().getArcanumType()));
        } else {
            LOX.LOGGER.info("{}: Data not changed; entity already has \"{}\" arcanum.", entity.getDisplayName(), StringUtils.capitalize(configuration.getConfiguration().getArcanumType()));
        }
    }

    private void removeData(ConfiguredPower<ArcanumPowerConfiguration, ?> configuration, Entity entity) {
        CompoundTag tag = entity.getPersistentData();
        if (tag.contains("arcanumList")) {
            ListTag arcanumList = tag.getList("arcanumList", 9);
            for (Tag arcanumTag : arcanumList) {
                StringTag arcanum = StringTag.valueOf(arcanumTag.getAsString());
                if (arcanum.getAsString().equals(configuration.getConfiguration().getArcanumType()) && tag.getBoolean("%sArcanumAddedByPower".formatted(configuration.getConfiguration().getArcanumType()))) {
                    arcanumList.remove(StringTag.valueOf(configuration.getConfiguration().getArcanumType()));
                    tag.remove("%sArcanumAddedByPower".formatted(configuration.getConfiguration().getArcanumType()));
                    LOX.LOGGER.info("{}: Removed \"{}\" arcanum from persistent data.", entity.getDisplayName(), StringUtils.capitalize(configuration.getConfiguration().getArcanumType()));
                } else {
                    LOX.LOGGER.info("{}: Data not changed; entity doesn't have \"{}\" arcanum.", entity.getDisplayName(), StringUtils.capitalize(configuration.getConfiguration().getArcanumType()));
                }
            }
        }
    }

    @Override
    public void onGained(ConfiguredPower<ArcanumPowerConfiguration, ?> configuration, Entity entity) {
        this.addData(configuration, entity);
        super.onGained(configuration, entity);
    }

    @Override
    public void onAdded(ConfiguredPower<ArcanumPowerConfiguration, ?> configuration, Entity entity) {
        this.addData(configuration, entity);
        super.onAdded(configuration, entity);
    }

    @Override
    public void onLost(ConfiguredPower<ArcanumPowerConfiguration, ?> configuration, Entity entity) {
        this.removeData(configuration, entity);
        super.onLost(configuration, entity);
    }

    @Override
    public void onRemoved(ConfiguredPower<ArcanumPowerConfiguration, ?> configuration, Entity entity) {
        this.removeData(configuration, entity);
        super.onRemoved(configuration, entity);
    }
}

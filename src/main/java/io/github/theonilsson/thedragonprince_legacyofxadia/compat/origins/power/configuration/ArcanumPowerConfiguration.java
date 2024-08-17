package io.github.theonilsson.thedragonprince_legacyofxadia.compat.origins.power.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.edwinmindcraft.apoli.api.IDynamicFeatureConfiguration;
import net.minecraft.network.chat.Component;

public class ArcanumPowerConfiguration implements IDynamicFeatureConfiguration {
    private final String arcanumType;

    public ArcanumPowerConfiguration(String arcanumType) {
        this.arcanumType = arcanumType;
    }

    public String getArcanumType() {
        return arcanumType;
    }

    public static final Codec<ArcanumPowerConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            SerializableDataTypes.STRING.fieldOf("arcanum").forGetter(ArcanumPowerConfiguration::getArcanumType)
    ).apply(instance, ArcanumPowerConfiguration::new));
}

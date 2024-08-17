package io.github.theonilsson.thedragonprince_legacyofxadia.event;

import io.github.theonilsson.thedragonprince_legacyofxadia.command.PlayerDataCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ForgeCommandRegisterEvent {
    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        PlayerDataCommand.register(event.getDispatcher());
    }
}

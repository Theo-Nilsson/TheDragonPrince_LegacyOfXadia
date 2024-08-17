package io.github.theonilsson.thedragonprince_legacyofxadia.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_LOX = "key.category.tdp_lox.1";
    public static final String KEY_CHANGE_SPELL = "key.tdp_lox.change_spell";
    public static final String KEY_INNATE_SPELL = "key.tdp_lox.innate_spell";

    public static final KeyMapping CHANGE_SPELL_KEY = new KeyMapping(KEY_CHANGE_SPELL, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Y, KEY_CATEGORY_LOX);

    public static final KeyMapping INNATE_SPELL_KEY = new KeyMapping(KEY_INNATE_SPELL, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CATEGORY_LOX);
}

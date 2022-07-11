package com.raptor.sdu.event;

import com.jaquadro.minecraft.storagedrawers.StorageDrawers;
import com.jaquadro.minecraft.storagedrawers.api.storage.EnumBasicDrawer;
import com.jaquadro.minecraft.storagedrawers.config.ConfigManager;
import com.raptor.sdu.block.BlockUnlimitedDrawers;
import com.raptor.sdu.block.BlockUnlimitedTrim;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static com.raptor.sdu.type.Mods.BLOCKS;
import static net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE;

public class RegisterOreDictionary {

    public static void register() {
        ConfigManager config = StorageDrawers.config;
        boolean trimEnabled = config.isBlockEnabled("trim");
        boolean anyDrawerEnabled = false;
        for(EnumBasicDrawer type : EnumBasicDrawer.values()) {
            if(config.isBlockEnabled(type.getUnlocalizedName())) {
                anyDrawerEnabled = true;
                break;
            }
        }

        for(Block block : BLOCKS) {
            boolean isDrawer = block instanceof BlockUnlimitedDrawers;
            boolean isTrim = block instanceof BlockUnlimitedTrim;
            if((!isDrawer || anyDrawerEnabled)
                    && (!isTrim || trimEnabled)) {
                if(isDrawer)
                    OreDictionary.registerOre("drawerBasic", new ItemStack(block, 1, WILDCARD_VALUE));
                else if(isTrim)
                    OreDictionary.registerOre("drawerTrim", new ItemStack(block, 1, WILDCARD_VALUE));
            }
        }
    }
}

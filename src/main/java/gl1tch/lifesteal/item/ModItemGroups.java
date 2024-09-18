package gl1tch.lifesteal.item;

import gl1tch.lifesteal.Lifesteal;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup SLIMES_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Lifesteal.MOD_ID, "tester"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup." + Lifesteal.MOD_ID + ".tester"))
                    .icon(() -> new ItemStack(ModItems.HEART_ITEM)).entries((displayContext, entries) -> {
                        entries.add(ModItems.HEART_ITEM);
                    }).build());

    public static void registeritemGroups() {
        Lifesteal.LOGGER.info("Registering Item Groups for " + Lifesteal.MOD_ID);
    }
}

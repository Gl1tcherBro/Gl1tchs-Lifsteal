package gl1tch.lifesteal.item;

import gl1tch.lifesteal.Lifesteal;
import gl1tch.lifesteal.item.custom.HeartItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static Item HEART_ITEM = registerItem("heart_item",
            new HeartItem(new Item.Settings()
                    .rarity(Rarity.RARE)
                    .maxCount(64)
                    .component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.of("§r§7Use this to gain a heart")))));

    public static void addItemsToIngredientsGroup(FabricItemGroupEntries entries) {
        //None
    }

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Lifesteal.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Lifesteal.LOGGER.info("Registering ModItems for " + Lifesteal.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientsGroup);
    }
}

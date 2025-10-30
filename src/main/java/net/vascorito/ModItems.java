package net.vascorito;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WeaponComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.vascorito.items.*;

import java.util.function.Function;

public class ModItems {
    public static Item register(Function<Item.Settings, Item> factory, Item.Settings settings, String id) {
        Identifier itemID = NetheriteTrident.id(id);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, itemID);

        return Registry.register(Registries.ITEM, itemID, factory.apply(settings.registryKey(key)));
    }

    public static Item.Settings settings() {
        return new Item.Settings()
                .rarity(Rarity.EPIC)
                .maxDamage(250)
                .enchantable(1)
                .component(DataComponentTypes.WEAPON, new WeaponComponent(1));
    }

    public static final Item NETHERITE = register(Netherite::new, settings()
                    .maxDamage(2031)
                    .fireproof()
                    .attributeModifiers(Netherite.createAttributeModifiers())
                    .component(DataComponentTypes.TOOL, Netherite.createToolComponent()),
            "netherite"
    );

    public static void initialise() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(group ->
                group.addAfter(Items.TRIDENT, NETHERITE));
    }
}

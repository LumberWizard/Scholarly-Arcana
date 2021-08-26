package lumberwizard.scholarlyarcana.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import lumberwizard.scholarlyarcana.common.capability.IMageArmor;
import lumberwizard.scholarlyarcana.common.capability.MageEquipmentProvider;
import lumberwizard.scholarlyarcana.common.capability.ModCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.UUID;

public class RobesItem extends ArmorItem {

    private static final UUID[] MAGE_ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("BE42E953-DD44-49B0-B251-DECE5D89AE11"), UUID.fromString("073FC7C2-9800-4D64-B032-842A2A768F58"), UUID.fromString("2FF92CF5-32AE-4677-9094-2E4F6416C184"), UUID.fromString("5B1E957B-602A-4D95-88B2-B3DF1B8FBC7D")};

    private final double costModifier;

    public RobesItem(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Properties itemProperties, double costModifier) {
        super(armorMaterial, equipmentSlot, itemProperties);
        this.costModifier = costModifier;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> defaultAttributes = super.getDefaultAttributeModifiers(slot);
        return stack.getCapability(ModCapabilities.MAGE_ARMOR, null).map((cap) -> getAttributesFromMageArmor(defaultAttributes, cap)).orElse(defaultAttributes);
    }

    private Multimap<Attribute, AttributeModifier> getAttributesFromMageArmor(Multimap<Attribute, AttributeModifier> existingAttributes, IMageArmor cap) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(existingAttributes);
        double mageArmor = cap.getMageArmorLevel();
        UUID uuid = MAGE_ARMOR_MODIFIER_UUID_PER_SLOT[getSlot().getIndex()];
        builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Mage Armor modifier", mageArmor, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Mage Armor toughness", mageArmor * 0.5, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        MageEquipmentProvider provider = new MageEquipmentProvider();
        provider.setSpellCostModifier(0.05);
        provider.initializeMageArmor();
        return provider;
    }

}

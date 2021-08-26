package lumberwizard.scholarlyarcana.world.level.block;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import lumberwizard.scholarlyarcana.common.capability.IMageArmor;
import lumberwizard.scholarlyarcana.common.capability.ModCapabilities;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class RobesItem extends ArmorItem {

    private static final UUID[] MAGE_ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("be42e953-dd44-49b0-b251-dece5d89ae11"), UUID.fromString("073fc7c2-9800-4d64-b032-842a2a768f58"), UUID.fromString("2ff92cf5-32ae-4677-9094-2e4f6416c184"), UUID.fromString("2887c2ae3-2d7a-44cc-8014-793242144330")};

    private double costModifier;

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

}

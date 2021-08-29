package lumberwizard.scholarlyarcana.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import lumberwizard.scholarlyarcana.ScholarlyArcana;
import lumberwizard.scholarlyarcana.world.entity.ai.attributes.ModAttributes;
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

    private static final UUID[] SPELL_COST_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("18E767B8-77B6-4F70-B332-08A45BA2960E"), UUID.fromString("01AC0928-C5B4-44D0-8666-80B0860C3F3C"), UUID.fromString("17144686-B9F9-456E-821D-F21C99CC37B7"), UUID.fromString("2B83A638-1284-4ECC-82C8-32B775056A56")};

    private Multimap<Attribute, AttributeModifier> attributeModifiers;
    private final double costModifier;

    public RobesItem(ArmorMaterial armorMaterial, EquipmentSlot slot, Properties itemProperties, double costModifier) {
        super(armorMaterial, slot, itemProperties);
        this.costModifier = costModifier;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        if (attributeModifiers == null && slot == this.slot) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(slot));
            UUID uuid = SPELL_COST_MODIFIER_UUID_PER_SLOT[slot.getIndex()];
            builder.put(ModAttributes.SPELL_COST_MODIFIER.get(), new AttributeModifier(uuid, "Spell cost modifier", costModifier, AttributeModifier.Operation.ADDITION));
            attributeModifiers = builder.build();
        }
        return slot == this.slot ? attributeModifiers : super.getDefaultAttributeModifiers(slot);
    }

}

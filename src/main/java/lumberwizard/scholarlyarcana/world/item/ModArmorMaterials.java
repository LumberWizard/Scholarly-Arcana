package lumberwizard.scholarlyarcana.world.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.LazyOptional;

public enum ModArmorMaterials implements ArmorMaterial {
    ARCANE_THREAD("arcane_thread", 5, new int[]{1, 2, 3, 1}, 20, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, 0.0F, LazyOptional.empty()),
    GOLDEN_WEAVE("golden_weave", 6, new int[]{1, 2, 3, 1}, 30, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, 0.0F, LazyOptional.empty()),
    NETHERITE_WEAVE("netherite_weave", 6, new int[]{2, 5, 6, 2}, 20, SoundEvents.ARMOR_EQUIP_GENERIC, 1.0F, 0.0F, LazyOptional.of(() -> Ingredient.of(Items.NETHERITE_INGOT)));

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyOptional<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, LazyOptional<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    public int getDurabilityForSlot(EquipmentSlot slot) {
        return HEALTH_PER_SLOT[slot.getIndex()] * this.durabilityMultiplier;
    }

    public int getDefenseForSlot(EquipmentSlot slot) {
        return this.slotProtections[slot.getIndex()];
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.orElse(Ingredient.EMPTY);
    }

    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

}

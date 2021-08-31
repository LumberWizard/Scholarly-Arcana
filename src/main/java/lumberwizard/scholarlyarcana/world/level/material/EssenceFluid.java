package lumberwizard.scholarlyarcana.world.level.material;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import lumberwizard.scholarlyarcana.util.MiscUtils;
import lumberwizard.scholarlyarcana.world.item.ModItems;
import lumberwizard.scholarlyarcana.world.level.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidAttributes;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class EssenceFluid extends FlowingFluid {

    private final EssenceType essenceType;

    public EssenceFluid(EssenceType type) {
        essenceType = type;
    }

    public EssenceType getEssenceType() {
        return essenceType;
    }

    @Override
    public Fluid getFlowing() {
        return essenceType.flowing.get();
    }

    @Override
    public Fluid getSource() {
        return essenceType.source.get();
    }

    @Override
    protected boolean canConvertToSource() {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
    }

    @Override
    protected int getSlopeFindDistance(LevelReader level) {
        return 3;
    }

    @Override
    protected int getDropOff(LevelReader level) {
        return 1;
    }

    @Override
    public Item getBucket() {
        return Items.AIR;
    }

    public Item getFlask() {
        return essenceType.flask.get();
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluid, Direction side) {
        return true;
    }

    @Override
    public int getTickDelay(LevelReader level) {
        return 2;
    }

    @Override
    protected float getExplosionResistance() {
        return 0;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return essenceType.legacyBlock.get().defaultBlockState().setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(state)));
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL);
    }

    @Override
    protected FluidAttributes createAttributes() {
        return essenceType.getAttributeBuilder().build(this);
    }

    @Override
    public boolean isSame(Fluid other) {
        return other instanceof EssenceFluid && ((EssenceFluid) other).getEssenceType() == essenceType;
    }

    public enum EssenceType {
        FIRE(createBasicAttributesBuilder().color(0xFFF26F18).luminosity(8).temperature(1000).translationKey(MiscUtils.createTranslationKey("block", "fire_essence")),
                ModFluids.FIRE_ESSENCE, ModFluids.FLOWING_FIRE_ESSENCE, ModBlocks.FIRE_ESSENCE, ModItems.FIRE_FLASK),
        AIR(createBasicAttributesBuilder().color(0xD8D9F5FF).translationKey(MiscUtils.createTranslationKey("block", "air_essence")),
                ModFluids.AIR_ESSENCE, ModFluids.FLOWING_AIR_ESSENCE, ModBlocks.AIR_ESSENCE, ModItems.AIR_FLASK),
        WATER(createBasicAttributesBuilder().color(0xFF4C73CF).translationKey(MiscUtils.createTranslationKey("block", "water_essence")),
                ModFluids.WATER_ESSENCE, ModFluids.FLOWING_WATER_ESSENCE, ModBlocks.WATER_ESSENCE, ModItems.WATER_FLASK),
        EARTH(createBasicAttributesBuilder().color(0xFF704F38).translationKey(MiscUtils.createTranslationKey("block", "earth_essence")),
                ModFluids.EARTH_ESSENCE, ModFluids.FLOWING_EARTH_ESSENCE, ModBlocks.EARTH_ESSENCE, ModItems.EARTH_FLASK);

        protected final Supplier<? extends FlowingFluid> source;
        protected final Supplier<? extends FlowingFluid> flowing;
        protected final Supplier<? extends Block> legacyBlock;
        protected final Supplier<? extends Item> flask;
        private final FluidAttributes.Builder attributeBuilder;

        EssenceType(FluidAttributes.Builder attributeBuilder, Supplier<? extends FlowingFluid> source, Supplier<? extends FlowingFluid> flowing, Supplier<? extends Block> legacyBlock, Supplier<? extends Item> flask) {
            this.attributeBuilder = attributeBuilder;
            this.source = source;
            this.flowing = flowing;
            this.legacyBlock = legacyBlock;
            this.flask = flask;
        }

        private static FluidAttributes.Builder createBasicAttributesBuilder() {
            //TODO: add sound?
            return FluidAttributes.builder(new ResourceLocation(ScholarlyArcana.MODID, "block/essence_still"), new ResourceLocation(ScholarlyArcana.MODID, "block/essence_flowing"))
                    .overlay(new ResourceLocation(ScholarlyArcana.MODID, "block/essence_overlay"))
                    .density(-1).gaseous().rarity(Rarity.UNCOMMON).viscosity(100)
                    .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY);
        }

        protected FluidAttributes.Builder getAttributeBuilder() {
            return attributeBuilder;
        }

        public Supplier<? extends Fluid> getSource() {
            return source;
        }

        @Override
        public String toString() {
            return StringUtils.capitalize(this.name().toLowerCase(Locale.ROOT));
        }
    }

    public static class Flowing extends EssenceFluid {
        public Flowing(EssenceType type) {
            super(type);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> stateDefinitionBuilder) {
            super.createFluidStateDefinition(stateDefinitionBuilder);
            stateDefinitionBuilder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Source extends EssenceFluid {
        public Source(EssenceType type) {
            super(type);
        }

        @Override
        public int getAmount(FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
    }

}

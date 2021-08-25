package lumberwizard.scholarlyarcana.world.entity.spell;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import lumberwizard.scholarlyarcana.world.spell.Spell;
import lumberwizard.scholarlyarcana.world.spell.Spells;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class FireboltEntity extends AbstractProjectileSpell {

    private float baseDamage = 3.0F;

    public FireboltEntity(EntityType<? extends FireboltEntity> type, Level level) {
        super(type, level);
    }

    public FireboltEntity(EntityType<? extends FireboltEntity> type, Level level, double x, double y, double z, LivingEntity caster) {
        super(type, level, x, y, z, caster);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        super.tick();
        for(int i = 0; i < 4; ++i) {
            double xMovement = getDeltaMovement().x;
            double yMovement = getDeltaMovement().y;
            double zMovement = getDeltaMovement().z;
            this.level.addParticle(ParticleTypes.FLAME, this.getX() + xMovement * (double)i / 4.0D, this.getY() + yMovement * (double)i / 4.0D, this.getZ() + zMovement * (double)i / 4.0D, -xMovement, -yMovement + 0.2D, -zMovement);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        Entity hit = hitResult.getEntity();
        Entity caster = this.getOwner();
        DamageSource damageSource;
        if (caster == null) {
            damageSource = DamageSource.indirectMagic(this, this);
        }
        else {
            damageSource = DamageSource.indirectMagic(this, caster);
            if (caster instanceof LivingEntity) {
                ((LivingEntity) caster).setLastHurtMob(hit);
            }
        }
        hit.hurt(damageSource, baseDamage);
        this.discard();
    }

    @Override
    public Spell getSpell() {
        return Spells.FIREBOLT.get();
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return super.shouldRenderAtSqrDistance(distance / 100);
    }

}

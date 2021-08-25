package lumberwizard.scholarlyarcana.world.entity.spell;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import lumberwizard.scholarlyarcana.world.spell.Spell;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

public abstract class AbstractProjectileSpell extends Projectile implements SpellEntity {

    private int life;
    private double gravity = 0;
    protected ClipContext.Fluid hitFluids;

    protected AbstractProjectileSpell(EntityType<? extends AbstractProjectileSpell> type, Level level) {
        super(type, level);
        this.hitFluids = ClipContext.Fluid.NONE;
    }

    public AbstractProjectileSpell(EntityType<? extends AbstractProjectileSpell> type, Level level, double x, double y, double z, LivingEntity caster) {
        this(type, level);
        setOwner(caster);
        setPos(x, y, z);
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity, inaccuracy);
        this.life = 0;
    }

    @Override
    public void lerpMotion(double x, double y, double z) {
        super.lerpMotion(x, y, z);
        this.life = 0;
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 deltaMovement = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = deltaMovement.horizontalDistance();
            this.setYRot((float)(Mth.atan2(deltaMovement.x, deltaMovement.z) * (double)(180F / (float)Math.PI)));
            this.setXRot((float)(Mth.atan2(deltaMovement.y, d0) * (double)(180F / (float)Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }
        if (!this.level.isClientSide) {
            this.tickDespawn();
        }
        Vec3 position = this.position();
        Vec3 target = position.add(deltaMovement);
        HitResult hitResult = this.level.clip(new ClipContext(position, target, ClipContext.Block.COLLIDER, hitFluids, this));
        if (hitResult.getType() != HitResult.Type.MISS) {
            target = hitResult.getLocation();
        }
        while (!this.isRemoved()) {
            EntityHitResult entityHitResult = this.findHitEntity(position, target);
            if (entityHitResult != null) {
                hitResult = entityHitResult;
            }

            if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
                Entity hit = ((EntityHitResult) hitResult).getEntity();
                Entity caster = this.getOwner();
                if (getSpell().getTargetType() != Spell.TargetType.ENTITY || hit instanceof Player && caster instanceof Player && !((Player) caster).canHarmPlayer((Player) hit)) {
                    hitResult = null;
                    entityHitResult = null;
                }
            }

            if (hitResult != null && hitResult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitResult)) {
                this.onHit(hitResult);
                this.hasImpulse = true;
            }

            if (entityHitResult == null) {
                break;
            }

            hitResult = null;
        }
        this.setPos(target);
        this.checkInsideBlocks();
    }

    protected void tickDespawn() {
        ++this.life;
        if (this.life >= 1200) {
            this.discard();
        }

    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        if (getSpell().getTargetType() != Spell.TargetType.ENTITY) {
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        if (getSpell().getTargetType() != Spell.TargetType.BLOCK) {
            this.discard();
        }
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 position, Vec3 target) {
        return ProjectileUtil.getEntityHitResult(this.level, this, position, target, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
    }

    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }
}

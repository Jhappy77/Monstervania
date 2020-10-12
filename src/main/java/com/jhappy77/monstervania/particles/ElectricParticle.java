package com.jhappy77.monstervania.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElectricParticle extends SpriteTexturedParticle {

    protected ElectricParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn){
        super((ClientWorld) worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        float f = this.rand.nextFloat() * 1.0F;
        this.particleRed = f;
        this.particleGreen = f;
        this.particleBlue = f;

        this.setSize(0.2f, 0.2f);
        this.particleScale *= this.rand.nextFloat() + 0.6F;
        this.maxAge =(int)(20/(Math.random() + 0.1));

        this.motionX *= 0.1D;
        this.motionY *= 0.1D;
        this.motionZ *= 0.1D;
    }

    // Handles particle's motion, deletes when necessary
    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            //this.motionY -= 0.04D * (double)this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            double multiplier = 0.98;
            if(this.age % 20 == 0)
                multiplier *= -1;
            this.motionX *= multiplier;
            this.motionY *= multiplier;
            this.motionZ *= multiplier;
            if (this.onGround) {
                this.motionX *= (double)0.7F;
                this.motionZ *= (double)0.7F;
            }
        }
        //Particle.java's tick method
        //super.tick();

    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    // A factory for creating particle
    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType>

    {
        private final IAnimatedSprite spriteSet;
        public Factory(IAnimatedSprite sprite){
            this.spriteSet = sprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            ElectricParticle p = new ElectricParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            p.setColor(1.0f, 1.0f, 1.0f);
            p.selectSpriteRandomly(this.spriteSet);
            return p;
        }
    }
}

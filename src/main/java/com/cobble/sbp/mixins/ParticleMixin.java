package com.cobble.sbp.mixins;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.gui.screen.dwarven.ChestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class ParticleMixin {
    @Inject(method = "Lnet/minecraft/client/network/NetHandlerPlayClient;handleParticles(Lnet/minecraft/network/play/server/S2APacketParticles;)V", at = @At("HEAD"))
    public void onParticle(S2APacketParticles packetIn, CallbackInfo ci) {
        if(SBP.onSkyblock && DataGetter.findBool("dwarven.chestHelper.toggle")) {
            if(packetIn.getParticleType().equals(EnumParticleTypes.CRIT)) {
                World world = Minecraft.getMinecraft().theWorld;
                BlockPos pos = null;
                Block lookingAt = Blocks.air;
                try {
                    MovingObjectPosition mop = Minecraft.getMinecraft().getRenderViewEntity().rayTrace(5, 1.0f);
                    if(mop != null)
                    {
                        pos = mop.getBlockPos();
                        IBlockState blockLookingAt = world.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())) ;
                        lookingAt = blockLookingAt.getBlock();

                    }
                } catch(Exception ignored) { }

                if(lookingAt.equals(Blocks.chest)) {
                    assert pos != null;
                    double x1 = pos.getX()-1;
                    double y1 = pos.getY()-1;
                    double z1 = pos.getZ()-1;
                    double x2 = x1+3;
                    double y2 = y1+3;
                    double z2 = z1+3;

                    if(packetIn.getXCoordinate() >= x1 && packetIn.getYCoordinate() >= y1 && packetIn.getZCoordinate() >= z1 && packetIn.getXCoordinate() <= x2 && packetIn.getYCoordinate() <= y2 && packetIn.getZCoordinate() <= z2) {
                        double x = packetIn.getXCoordinate()-0.1;
                        double y = packetIn.getYCoordinate()-0.1;
                        double z = packetIn.getZCoordinate()-0.1;
                        ChestHelper.x = (int) x;
                        ChestHelper.y = (int) y;
                        ChestHelper.z = (int) z;
                        ChestHelper.xOff = x - ChestHelper.x;
                        ChestHelper.yOff = y - ChestHelper.y;
                        ChestHelper.zOff = z - ChestHelper.z;
                        //Utils.sendMessage(packetIn.getParticleType().getParticleName()+"   :   "+packetIn.getXCoordinate()+", "+packetIn.getYCoordinate()+", "+packetIn.getZCoordinate());
                    }


                }
            }
        }



    }
}

package com.cobble.sbp.gui.screen.dwarven;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class ChestHelper extends Gui {

    public static double x = -1;
    public static double y = -1;
    public static double z = -1;
    public static double xOff = -1;
    public static double yOff = -1;
    public static double zOff = -1;
    private static boolean chestLooking = false;

    public ChestHelper(RenderWorldLastEvent event) {
        if(!DataGetter.findBool("dwarven.chestHelper.toggle")) {return;}
        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        Block lookingAt = Blocks.air;
        try {
            MovingObjectPosition mop = Minecraft.getMinecraft().getRenderViewEntity().rayTrace(5, 1.0F);
            if(mop != null) { IBlockState blockLookingAt = world.getBlockState(new BlockPos(mop.getBlockPos().getX(), mop.getBlockPos().getY(), mop.getBlockPos().getZ())) ; lookingAt = blockLookingAt.getBlock(); }
        } catch(Exception ignored) { }

        if(chestLooking) {
            if(!lookingAt.equals(Blocks.chest)) {
                x = -1;
                y = -1;
                z = -1;
                xOff = -1;
                yOff = -1;
                zOff = -1;
                chestLooking = false;
            }
        } else {
            if(lookingAt.equals(Blocks.chest)) {
                chestLooking=true;
            }
        }

        if(x != -1 && y != -1 && z != -1) {
            WorldUtils.drawCube(x, y, z, 0.2, xOff, yOff, zOff, Colors.configToColor(DataGetter.findStr("dwarven.chestHelper.color")), event);
        }


    }


}

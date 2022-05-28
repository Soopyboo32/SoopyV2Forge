package me.soopyboo32.soopyv2forge.RenderTypes;

import me.soopyboo32.soopyv2forge.RenderWorldAble;
import me.soopyboo32.soopyv2forge.SoopyV2Forge;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class Points implements RenderWorldAble {
    public ArrayList<Vec3> points;
    public float red;
    public float green;
    public float blue;
    public float alpha;
    public float thickness;
    public boolean depthtest;
    public int glmode;
    public boolean disableCullFace;

    public Points(ArrayList<Vec3> points, float red, float green, float blue, float alpha, float thickness, boolean depthtest){
        this.points = points;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.thickness = thickness;
        this.depthtest = depthtest;
        this.glmode = 2;
        this.disableCullFace = false;
    }

    public void render(float partialTicks){
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(thickness);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        if(!depthtest){
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
        }
        if(disableCullFace){
            GL11.glDisable(GL11.GL_CULL_FACE);
        }
        GlStateManager.pushMatrix();

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.translate(-SoopyV2Forge.INSTANCE.renderManager.viewerPosX, -SoopyV2Forge.INSTANCE.renderManager.viewerPosY, -SoopyV2Forge.INSTANCE.renderManager.viewerPosZ);
        SoopyV2Forge.INSTANCE.worldRenderer.begin(glmode, DefaultVertexFormats.POSITION);
        GlStateManager.color(red, green, blue, alpha);

        for(Vec3 point : points){
            SoopyV2Forge.INSTANCE.worldRenderer.pos(point.xCoord, point.yCoord, point.zCoord).endVertex();
        }

        SoopyV2Forge.INSTANCE.tessellator.draw();
        GlStateManager.color(1,1,1);

        GlStateManager.disableBlend();

        GlStateManager.popMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        if(disableCullFace){
            GL11.glEnable(GL11.GL_CULL_FACE);
        }
        if(!depthtest){
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
        }
        GL11.glDisable(GL11.GL_BLEND);
    }
}

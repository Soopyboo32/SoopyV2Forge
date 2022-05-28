package me.soopyboo32.soopyv2forge.RenderTypes;

import me.soopyboo32.soopyv2forge.RenderWorldAble;
import me.soopyboo32.soopyv2forge.SoopyV2Forge;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class WorldText implements RenderWorldAble {
    public Vec3 location;
    public String text;
    public boolean depthtest;
    public float scale;
    public boolean shadow;

    public WorldText(Vec3 location, String text, boolean depthtest, float scale){
        this.location = location;
        this.text = text;
        this.depthtest = depthtest;
        this.scale = scale;
        this.shadow = false;
    }

    public void render(float partialTicks){
        if(!depthtest){
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
        }
        GlStateManager.pushMatrix();

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.translate(location.xCoord-SoopyV2Forge.INSTANCE.renderManager.viewerPosX, location.yCoord-SoopyV2Forge.INSTANCE.renderManager.viewerPosY, location.zCoord-SoopyV2Forge.INSTANCE.renderManager.viewerPosZ);
        GlStateManager.color(1,1,1, 0.5F);

        GlStateManager.rotate(-SoopyV2Forge.INSTANCE.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(SoopyV2Forge.INSTANCE.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);

        GlStateManager.scale(-scale/25, -scale/25, scale/25);
        if (shadow) {
            SoopyV2Forge.INSTANCE.fontRenderer.drawStringWithShadow(text, -SoopyV2Forge.INSTANCE.fontRenderer.getStringWidth(text)/2, 0,0);
        }else{
            SoopyV2Forge.INSTANCE.fontRenderer.drawString(text, -SoopyV2Forge.INSTANCE.fontRenderer.getStringWidth(text)/2, 0,0);
        }

        GlStateManager.color(1,1,1);

        GlStateManager.disableBlend();

        GlStateManager.popMatrix();
        if(!depthtest){
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
        }
    }
}

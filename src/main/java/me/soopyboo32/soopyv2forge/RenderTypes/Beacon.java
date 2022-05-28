package me.soopyboo32.soopyv2forge.RenderTypes;

import me.soopyboo32.soopyv2forge.RenderWorldAble;
import me.soopyboo32.soopyv2forge.SoopyV2Forge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class Beacon implements RenderWorldAble {

    private static ResourceLocation beaconBeam = new ResourceLocation("textures/entity/beacon_beam.png");

    public Vec3 location;
    public float red;
    public float green;
    public float blue;
    public float alpha;
    public boolean depthtest;

    public Beacon(Vec3 location, float red, float green, float blue, float alpha, boolean depthtest){
        this.location = location;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.depthtest = depthtest;
    }

    public void render(float partialTicks){
        int height = 300;
        int bottomOffset = 0;
        int topOffset = bottomOffset + height;

        GlStateManager.pushMatrix();
        if(!depthtest){
            GlStateManager.disableDepth();
        }
        GlStateManager.translate(-SoopyV2Forge.INSTANCE.renderManager.viewerPosX, -SoopyV2Forge.INSTANCE.renderManager.viewerPosY, -SoopyV2Forge.INSTANCE.renderManager.viewerPosZ);
        Minecraft.getMinecraft().getTextureManager().bindTexture(beaconBeam);

        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GlStateManager.disableLighting();
        GlStateManager.enableCull();
        GlStateManager.enableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        double time = Minecraft.getMinecraft().theWorld.getWorldTime() + partialTicks;

        double d1 = MathHelper.func_181162_h(-time * 0.2 - MathHelper.floor_double(-time * 0.1));

        double d2 = time * 0.025 * -1.5;
        double d4 = 0.5 + Math.cos(d2 + 2.356194490192345) * 0.2;
        double d5 = 0.5 + Math.sin(d2 + 2.356194490192345) * 0.2;
        double d6 = 0.5 + Math.cos(d2 + (Math.PI / 4)) * 0.2;
        double d7 = 0.5 + Math.sin(d2 + (Math.PI / 4)) * 0.2;
        double d8 = 0.5 + Math.cos(d2 + 3.9269908169872414) * 0.2;
        double d9 = 0.5 + Math.sin(d2 + 3.9269908169872414) * 0.2;
        double d10 = 0.5 + Math.cos(d2 + 5.497787143782138) * 0.2;
        double d11 = 0.5 + Math.sin(d2 + 5.497787143782138) * 0.2;
        double d14 = -1 + d1;
        double d15 = height * 2.5 + d14;

        double x = location.xCoord;
        double y = location.yCoord;
        double z = location.zCoord;

        SoopyV2Forge.INSTANCE.worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + d4, y + topOffset, z + d5).tex(1.0D, d15).color(red, green, blue, 1.0F * alpha).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + d4, y + bottomOffset, z + d5).tex(1.0D, d14).color(red, green, blue, 1.0F).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + d6, y + bottomOffset, z + d7).tex(0.0D, d14).color(red, green, blue, 1.0F).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + d6, y + topOffset, z + d7).tex(0.0D, d15).color(red, green, blue, 1.0F * alpha).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + d10, y + topOffset, z + d11).tex(1.0D, d15).color(red, green, blue, 1.0F * alpha).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + d10, y + bottomOffset, z + d11).tex(1.0D, d14).color(red, green, blue, 1.0F).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + d8, y + bottomOffset, z + d9).tex(0.0D, d14).color(red, green, blue, 1.0F).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + d8, y + topOffset, z + d9).tex(0.0D, d15).color(red, green, blue, 1.0F * alpha).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + d6, y + topOffset, z + d7).tex(1.0D, d15).color(red, green, blue, 1.0F * alpha).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + d6, y + bottomOffset, z + d7).tex(1.0D, d14).color(red, green, blue, 1.0F).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + d10, y + bottomOffset, z + d11).tex(0.0D, d14).color(red, green, blue, 1.0F).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + d10, y + topOffset, z + d11).tex(0.0D, d15).color(red, green, blue, 1.0F * alpha).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + d8, y + topOffset, z + d9).tex(1.0D, d15).color(red, green, blue, 1.0F * alpha).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + d8, y + bottomOffset, z + d9).tex(1.0D, d14).color(red, green, blue, 1.0F).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + d4, y + bottomOffset, z + d5).tex(0.0D, d14).color(red, green, blue, 1.0F).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + d4, y + topOffset, z + d5).tex(0.0D, d15).color(red, green, blue, 1.0F * alpha).endVertex();
        SoopyV2Forge.INSTANCE.tessellator.draw();

        GlStateManager.disableCull();
        double d12 = -1.0D + d1;
        double d13 = height + d12;

        SoopyV2Forge.INSTANCE.worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + 0.2D, y + topOffset, z + 0.2D).tex(1.0D, d13).color(red, green, blue, 0.25F * alpha).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + 0.2D, y + bottomOffset, z + 0.2D).tex(1.0D, d12).color(red, green, blue, 0.25F).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + 0.8D, y + bottomOffset, z + 0.2D).tex(0.0D, d12).color(red, green, blue, 0.25F).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + 0.8D, y + topOffset, z + 0.2D).tex(0.0D, d13).color(red, green, blue, 0.25F * alpha).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + 0.8D, y + topOffset, z + 0.8D).tex(1.0D, d13).color(red, green, blue, 0.25F * alpha).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + 0.8D, y + bottomOffset, z + 0.8D).tex(1.0D, d12).color(red, green, blue, 0.25F).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + 0.2D, y + bottomOffset, z + 0.8D).tex(0.0D, d12).color(red, green, blue, 0.25F).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + 0.2D, y + topOffset, z + 0.8D).tex(0.0D, d13).color(red, green, blue, 0.25F * alpha).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + 0.8D, y + topOffset, z + 0.2D).tex(1.0D, d13).color(red, green, blue, 0.25F * alpha).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + 0.8D, y + bottomOffset, z + 0.2D).tex(1.0D, d12).color(red, green, blue, 0.25F).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + 0.8D, y + bottomOffset, z + 0.8D).tex(0.0D, d12).color(red, green, blue, 0.25F).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + 0.8D, y + topOffset, z + 0.8D).tex(0.0D, d13).color(red, green, blue, 0.25F * alpha).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + 0.2D, y + topOffset, z + 0.8D).tex(1.0D, d13).color(red, green, blue, 0.25F * alpha).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + 0.2D, y + bottomOffset, z + 0.8D).tex(1.0D, d12).color(red, green, blue, 0.25F).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + 0.2D, y + bottomOffset, z + 0.2D).tex(0.0D, d12).color(red, green, blue, 0.25F).endVertex();
        SoopyV2Forge.INSTANCE.worldRenderer.pos(x + 0.2D, y + topOffset, z + 0.2D).tex(0.0D, d13).color(red, green, blue, 0.25F * alpha).endVertex();
        SoopyV2Forge.INSTANCE.tessellator.draw();

        GlStateManager.disableLighting();
        GlStateManager.enableTexture2D();

        if(!depthtest){
            GlStateManager.enableDepth();
        }

        GlStateManager.popMatrix();
    }
}

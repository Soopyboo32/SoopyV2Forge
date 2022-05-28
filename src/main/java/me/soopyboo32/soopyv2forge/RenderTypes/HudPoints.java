package me.soopyboo32.soopyv2forge.RenderTypes;

import me.soopyboo32.soopyv2forge.RenderHudAble;
import me.soopyboo32.soopyv2forge.SoopyV2Forge;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector2f;
import java.util.ArrayList;

public class HudPoints implements RenderHudAble {

    public ArrayList<Vector2f> points;
    public float colorR;
    public float colorG;
    public float colorB;
    public float colorA;
    public float thickness;
    public int glmode;

    public HudPoints(ArrayList<Vector2f> points, float colorR, float colorG, float colorB, float colorA, float thickness){
        this.points = points;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.colorA = colorA;
        this.thickness = thickness;
        this.glmode = 5;
    }

    public void render(float partialTicks){
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GL11.glLineWidth(thickness);
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(colorR, colorG, colorB, colorA);

        SoopyV2Forge.INSTANCE.worldRenderer.begin(glmode, DefaultVertexFormats.POSITION);

        for(Vector2f point : points){
            SoopyV2Forge.INSTANCE.worldRenderer.pos(point.x, point.y, 0).endVertex();
        }

        SoopyV2Forge.INSTANCE.tessellator.draw();
        GlStateManager.color(1,1,1, 1);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}

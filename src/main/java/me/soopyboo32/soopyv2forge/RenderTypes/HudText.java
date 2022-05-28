package me.soopyboo32.soopyv2forge.RenderTypes;

import me.soopyboo32.soopyv2forge.RenderHudAble;
import me.soopyboo32.soopyv2forge.SoopyV2Forge;
import net.minecraft.client.renderer.GlStateManager;

public class HudText implements RenderHudAble {

    public String text;
    public int x;
    public int y;
    public float scale;
    public boolean shadow;

    public HudText(String text, int x, int y, boolean shadow){
        this.text = text;
        this.x = x;
        this.y = y;
        this.shadow = shadow;
        this.scale = 1;
    }

    public void render(float partialTicks){
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(scale, scale, scale);
        if (shadow) {
            SoopyV2Forge.INSTANCE.fontRenderer.drawStringWithShadow(text,0,0, 0);
        }else{
            SoopyV2Forge.INSTANCE.fontRenderer.drawString(text,0,0, 0);
        }
        GlStateManager.popMatrix();
    }
}

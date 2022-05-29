package me.soopyboo32.soopyv2forge.RenderTypes;

import me.soopyboo32.soopyv2forge.RenderHudAble;
import me.soopyboo32.soopyv2forge.SoopyV2Forge;
import net.minecraft.client.renderer.GlStateManager;

import java.util.regex.Pattern;

public class HudText implements RenderHudAble {

    public String[] textLines;
    public int x;
    public int y;
    public float scale;
    public boolean shadow;

    public HudText(String[] textLines, int x, int y, boolean shadow){
        this.textLines = textLines;
        this.x = x;
        this.y = y;
        this.shadow = shadow;
        this.scale = 1;
    }

    public void render(float partialTicks){
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(scale, scale, scale);
        int yOff = 0;
        for(String line : textLines){
            if (shadow) {
                SoopyV2Forge.INSTANCE.fontRenderer.drawStringWithShadow(line,0,yOff, 0);
            }else{
                SoopyV2Forge.INSTANCE.fontRenderer.drawString(line,0,yOff, 0);
            }
            yOff += 10;
        }
        GlStateManager.popMatrix();
    }
}

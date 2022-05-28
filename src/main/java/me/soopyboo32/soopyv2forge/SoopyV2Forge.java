package me.soopyboo32.soopyv2forge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

@Mod(modid = SoopyV2Forge.MODID, version = SoopyV2Forge.VERSION)
public class SoopyV2Forge
{
    public static final String MODID = "soopyv2forge";
    public static final String VERSION = "1.0";

    public static SoopyV2Forge INSTANCE;

    public Tessellator tessellator;
    public WorldRenderer worldRenderer;
    public RenderManager renderManager;
    public FontRenderer fontRenderer;

    public ArrayList<RenderWorldAble> renderWorldList;
    public ArrayList<RenderHudAble> renderHudList;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        INSTANCE = this;

        tessellator = Tessellator.getInstance();
        worldRenderer = tessellator.getWorldRenderer();
        renderManager = Minecraft.getMinecraft().getRenderManager();

        fontRenderer = Minecraft.getMinecraft().fontRendererObj;

        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setRenderWorldList(ArrayList<RenderWorldAble> list){
        renderWorldList = list;
    }
    public void setRenderHudList(ArrayList<RenderHudAble> list){
        renderHudList = list;
    }

    @SubscribeEvent
    public void renderWorldEvent(RenderWorldLastEvent event){
        if(renderWorldList == null) return;

        for(RenderWorldAble render : renderWorldList){
            render.render(event.partialTicks);
        }
    }

    @SubscribeEvent
    public void renderOverlayEvent(RenderGameOverlayEvent.Text event){
        if(renderHudList == null) return;

        for(RenderHudAble render : renderHudList){
            render.render(event.partialTicks);
        }
    }

    public String getVersion(){
        return VERSION;
    }
}

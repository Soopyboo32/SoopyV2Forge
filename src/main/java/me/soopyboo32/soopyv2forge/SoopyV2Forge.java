package me.soopyboo32.soopyv2forge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Mod(modid = SoopyV2Forge.MODID, version = SoopyV2Forge.VERSION)
public class SoopyV2Forge
{
    public static final String MODID = "soopyv2forge";
    public static final String VERSION = "1.0";

    public static SoopyV2Forge INSTANCE;

    public boolean soopyV2Installed = false;
    public boolean shouldCtReload = false;

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

    public void soopyIsInstalled(){
        soopyV2Installed = true;
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
        if(shouldCtReload){
            shouldCtReload = false;
            ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().thePlayer, "/ct reload");
        }
        if(renderHudList == null) return;

        for(RenderHudAble render : renderHudList){
            render.render(event.partialTicks);
        }
    }

    @SubscribeEvent
    public void worldLoadEvent(WorldEvent.Load event){
        if(!soopyV2Installed){
            soopyIsInstalled();

            new Thread(() -> {
                try {
                    URL downloadURL = new URL("http://soopymc.my.to/api/soopyv2/downloadlatest.zip");

                    if (!new File("." + File.separator + "config" + File.separator + "ChatTriggers" + File.separator
                            + "modules").exists())
                        new File("." + File.separator + "config" + File.separator + "ChatTriggers" + File.separator
                                + "modules").mkdirs();


                    if (new File("." + File.separator + "config" + File.separator + "ChatTriggers" + File.separator
                            + "modules" + File.separator + "SoopyV2").exists())
                        this.deleteDirectory(new File(
                                "." + File.separator + "config" + File.separator + "ChatTriggers" + File.separator
                                        + "modules" + File.separator + "SoopyV2"));

                    this.urlToFile(downloadURL,
                            "." + File.separator + "config" + File.separator + "ChatTriggers" + File.separator
                                    + "modules" + File.separator + "SoopyV2.zip",
                            10000,
                            10000);
                    this.unzip("." + File.separator + "config" + File.separator + "ChatTriggers" + File.separator
                                    + "modules" + File.separator + "SoopyV2.zip",
                            "." + File.separator + "config" + File.separator + "ChatTriggers" + File.separator
                                    + "modules");

                    new File("." + File.separator + "config" + File.separator + "ChatTriggers" + File.separator
                            + "modules" + File.separator + "SoopyV2.zip").delete();

                    Thread.sleep(1000);
                    shouldCtReload = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
    boolean deleteDirectory(File directoryToBeDeleted) {
    File[] allContents = directoryToBeDeleted.listFiles();
    if (allContents != null) {
        for (File file : allContents) {
            deleteDirectory(file);
        }
    }
    return directoryToBeDeleted.delete();
}
    private void urlToFile(URL url, String destination, int connecttimeout, int readtimeout) {
        File d = new File(destination);
        d.getParentFile().mkdirs();
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        connection.setDoOutput(true);
        connection.setConnectTimeout(connecttimeout);
        connection.setReadTimeout(readtimeout);
        try (InputStream IS = connection.getInputStream()) {
            PrintStream FilePS = new PrintStream(destination);
            byte[] buf = new byte[65536];
            int len = 0;
            try {
                while ((len = IS.read(buf)) > 0) {
                    FilePS.write(buf, 0, len);
                }
                IS.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FilePS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void unzip(String zipFile, String unziploc) { // code translated from chattriggers FileLib.unzip
        File unzipDir = new File(unziploc);
        if (!unzipDir.exists())
            unzipDir.mkdir();

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry = zipIn.getNextEntry();
            // iterates over entries in the zip file
            while (entry != null) {
                String filePath = unziploc + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // if the entry is a file, extracts it
                    this.extractFile(zipIn, filePath);
                } else {
                    // if the entry is a directory, make the directory
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void extractFile(ZipInputStream zipIn, String filePath) {
        try {
            File toWrite = new File(filePath);
            toWrite.getParentFile().mkdirs();
            toWrite.createNewFile();

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
            byte[] bytesIn = new byte[4096];
            int read = zipIn.read(bytesIn);
            while (read != -1) {
                bos.write(bytesIn, 0, read);
                read = zipIn.read(bytesIn);
            }
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getVersion(){
        return VERSION;
    }
}

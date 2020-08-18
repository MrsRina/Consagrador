package rina.turok;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.*;

import java.util.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author 086
 *
 * Update by Rina.
 * 08/04/20.
 *
 **/
public class TurokRenderHelp extends Tessellator {
    public static TurokRenderHelp INSTANCE = new TurokRenderHelp();

    public TurokRenderHelp() {
        super(0x200000);
    }

    public static void prepare(String mode_requested) {
        int mode = 0;

        if (mode_requested.equalsIgnoreCase("quads")) {
            mode = GL_QUADS;
        } else if (mode_requested.equalsIgnoreCase("lines")) {
            mode = GL_LINES;
        }

        prepareGL(1.0f);
        begin(mode);
    }

    public static void prepare(String mode_requested, float size) {
    	int mode = 0;

    	if (mode_requested.equalsIgnoreCase("quads")) {
    		mode = GL_QUADS;
    	} else if (mode_requested.equalsIgnoreCase("lines")) {
    		mode = GL_LINES;
    	}

        prepareGL(size);
        begin(mode);
    }

    public static void prepareGL(float size) {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(size);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.color(1, 1, 1);
    }

    public static void begin(int mode) {
        INSTANCE.getBuffer().begin(mode, DefaultVertexFormats.POSITION_COLOR);
    }

    public static void release() {
        render();
        releaseGL();
    }

    public static void render() {
        INSTANCE.draw();
    }

    public static void releaseGL() {
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
    }

    public static BufferBuilder getBufferBuild() {
        return INSTANCE.getBuffer();
    }

    public static void drawCube(BlockPos blockPos, int argb, String sides) {
        final int a = (argb >>> 24) & 0xFF;
        final int r = (argb >>> 16) & 0xFF;
        final int g = (argb >>> 8) & 0xFF;
        final int b = argb & 0xFF;

        drawCube(blockPos, r, g, b, a, sides);
    }

    public static void drawCube(float x, float y, float z, int argb, String sides) {
        final int a = (argb >>> 24) & 0xFF;
        final int r = (argb >>> 16) & 0xFF;
        final int g = (argb >>> 8) & 0xFF;
        final int b = argb & 0xFF;

        drawCube(getBufferBuild(), x, y, z, 1, 1, 1, r, g, b, a, sides);
    }

    public static void drawCube(BlockPos blockPos, int r, int g, int b, int a, String sides) {
        drawCube(getBufferBuild(), blockPos.x, blockPos.y, blockPos.z, 1, 1, 1, r, g, b, a, sides);
    }

    public static void drawCube(final BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a, String sides) {
        if (((boolean) Arrays.asList(sides.split("-")).contains("down")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
            buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
            buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
            buffer.pos(x, y, z).color(r, g, b, a).endVertex();
        }

        if (((boolean) Arrays.asList(sides.split("-")).contains("up")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
            buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
            buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
            buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
        }

        if (((boolean) Arrays.asList(sides.split("-")).contains("north")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
            buffer.pos(x, y, z).color(r, g, b, a).endVertex();
            buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
            buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
        }

        if (((boolean) Arrays.asList(sides.split("-")).contains("south")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
            buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
            buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
            buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
        }

        if (((boolean) Arrays.asList(sides.split("-")).contains("south")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x, y, z).color(r, g, b, a).endVertex();
            buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
            buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
            buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
        }

        if (((boolean) Arrays.asList(sides.split("-")).contains("south")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
            buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
            buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
            buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
        }
    }

    public static void drawOutlineCube(BlockPos blockPos, int argb, String sides) {
        final int a = (argb >>> 24) & 0xFF;
        final int r = (argb >>> 16) & 0xFF;
        final int g = (argb >>> 8) & 0xFF;
        final int b = argb & 0xFF;

        drawOutlineCube(blockPos, r, g, b, a, sides);
    }

    public static void drawOutlineCube(float x, float y, float z, int argb, String sides) {
        final int a = (argb >>> 24) & 0xFF;
        final int r = (argb >>> 16) & 0xFF;
        final int g = (argb >>> 8) & 0xFF;
        final int b = argb & 0xFF;

        drawOutlineCube(getBufferBuild(), x, y, z, 1, 1, 1, r, g, b, a, sides);
    }

    public static void drawOutlineCube(BlockPos blockPos, int r, int g, int b, int a, String sides) {
        drawOutlineCube(getBufferBuild(), blockPos.x, blockPos.y, blockPos.z, 1, 1, 1, r, g, b, a, sides);
    }

    public static void drawOutlineCube(final BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a, String sides) {
        if (((boolean) Arrays.asList(sides.split("-")).contains("DownWest")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x, y, z).color(r, g, b, a).endVertex();
            buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
        }

        if (((boolean) Arrays.asList(sides.split("-")).contains("UpWest")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
            buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
        }

        if (((boolean) Arrays.asList(sides.split("-")).contains("DownEast")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
            buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
        }

        if (((boolean) Arrays.asList(sides.split("-")).contains("UpEast")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
            buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
        }

       if (((boolean) Arrays.asList(sides.split("-")).contains("DownNorth")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x, y, z).color(r, g, b, a).endVertex();
            buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
        }

        if (((boolean) Arrays.asList(sides.split("-")).contains("UpNorth")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
            buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
        }

        if (((boolean) Arrays.asList(sides.split("-")).contains("DownSouth")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
            buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
        }

        if (((boolean) Arrays.asList(sides.split("-")).contains("UpSouth")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
            buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
        }

        if (((boolean) Arrays.asList(sides.split("-")).contains("NortWeast")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x, y, z).color(r, g, b, a).endVertex();
            buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
        }

        if (((boolean) Arrays.asList(sides.split("-")).contains("NortEast")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
            buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
        }

        if (((boolean) Arrays.asList(sides.split("-")).contains("SouthWeast")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
            buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
        }

        if (((boolean) Arrays.asList(sides.split("-")).contains("SouthEast")) || sides.equalsIgnoreCase("all")) {
            buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
            buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
        }
    }
}
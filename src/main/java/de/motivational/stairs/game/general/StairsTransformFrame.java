package de.motivational.stairs.game.general;

import com.jogamp.newt.Display;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.FPSAnimator;

import java.awt.image.BufferedImage;

/**
 * Created by vspadi on 23.11.16.
 */
public class StairsTransformFrame implements IBeamerFrame, GLEventListener {

    private GLWindow glWindow = null;
    private FPSAnimator animator;
    private Display display;
    private Screen screen;

    public StairsTransformFrame() {
        // allocate display
        this.display = NewtFactory.createDisplay(null);
        

        this.animator = new FPSAnimator(this.glWindow, 60);
    }

    @Override
    public void draw(BufferedImage bufferedImage) {

    }

    @Override
    public void init(GLAutoDrawable drawable) {

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }
}

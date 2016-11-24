package de.motivational.stairs.beamer.opengl;

import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import de.motivational.stairs.beamer.opengl.util.ShaderHelper;
import de.motivational.stairs.database.entity.BeamerEntity;
import de.motivational.stairs.database.entity.BeamerSetupEntity;
import de.motivational.stairs.game.general.IBeamerFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL.GL_NO_ERROR;
import static com.jogamp.opengl.GL2ES3.GL_TEXTURE_BUFFER;


/**
 * Created by vspadi on 23.11.16.
 */
public class StairsTransformFrame implements IBeamerFrame, GLEventListener {

    // DEBUG
    Logger logger;

    // GL Context
    private GLWindow glWindow = null;
    private Animator animator;
    private boolean glInitialized = false;

    // Stairs Beamer & co.
    private BeamerSetupEntity beamerSetup = null;

    // OPENGL
    private final String SHADERS_ROOT = "shaders";
    private final String SHADERS_SOURCE = "stairs-transform";
    private boolean imageNeedsUpdate = false;

    private final class Semantic {
        static final int POSITION = 0;
    }

    private ShaderHelper shaderHelper;

    private IntBuffer bufferName = GLBuffers.newDirectIntBuffer(Buffer.MAX);
    private IntBuffer textureName = GLBuffers.newDirectIntBuffer(1);

    private class Buffer {
        private static final int VERTEX = 0;
        private static final int ELEMENT = 1;
        private static final int TEXTURE = 2;
        private static final int MAX = 3;

    }

    // outside data
    private BufferedImage nextImage;

    public StairsTransformFrame() {
        this.logger = LoggerFactory.getLogger(StairsTransformFrame.class);
        this.internalGLInit();
    }

    public BeamerSetupEntity getBeamerSetup() {
        return beamerSetup;
    }

    public void setBeamerSetup(BeamerSetupEntity beamerSetup) {
        this.beamerSetup = beamerSetup;
        this.internalGLInit();
    }

    private void internalGLInit() {
        this.logger.debug("Internal Initialization started");

        // create GL context independent of beamer and stairs
        if(!this.glInitialized) {
            this.logger.info("Initializing OpenGL Context");
            // get GL profile
            GLProfile glProfile = GLProfile.get(GLProfile.GL2ES2);
            GLCapabilities glCapabilities = new GLCapabilities(glProfile);

            glCapabilities.setDoubleBuffered(true);
            // init all known window props
            this.glWindow = GLWindow.create(glCapabilities);
            this.glWindow.setUndecorated(false);
            this.glWindow.setSize(640, 480);
            this.glWindow.setAlwaysOnTop(false);
            this.glWindow.setFullscreen(false);
            this.glWindow.setPointerVisible(true);
            this.glWindow.confinePointer(false);
            this.glWindow.setTitle("MotivationalStairs - Stairs Projection mapper");

            this.glWindow.setVisible(true);
            this.glWindow.setContextCreationFlags(GLContext.CTX_OPTION_DEBUG);

            this.glWindow.addGLEventListener(this);

            this.animator = new Animator(this.glWindow);
            this.animator.start();
            this.glInitialized = true;
        } else {
            this.logger.debug("OpenGL Context already created, skipping");
        }

        if(this.beamerSetup != null) {
            // resize window
            BeamerEntity beamer = this.beamerSetup.getBeamerByBeamerId();
            this.glWindow.setSize(beamer.getWidth(), beamer.getHeight());

            // create object buffer with stairs TODO only rebuild IF new beamer is set

        }
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2ES2 gl = drawable.getGL().getGL2ES2();

        this.logger.info("Initializing OpenGL Drawable");

        gl.glClearColor(0.1f, 0.1f, 0.1f, 0f);
        // shaders
        this.initShaders(gl);

        // TODO move
        this.initBuffers(gl);

        gl.glGenTextures(1, textureName);

        gl.glEnable(GL2ES2.GL_DEPTH_TEST);
        checkError(gl, "Init");
    }

    private void initShaders(GL2ES2 gl) {
        // see helper class for more detail
        this.shaderHelper = new ShaderHelper(gl, SHADERS_ROOT, SHADERS_SOURCE);

        // bind attributes
        this.shaderHelper.bindAttribute("position");
        this.shaderHelper.bindAttribute("tex_coord_vert");

        // bind uniforms
        this.shaderHelper.bindUniform("mvp");
        this.shaderHelper.bindUniform("diffuse");
        //this.shaderHelper.bindUniform("texture_sampler");


        if(!checkError(gl, "Shader")) {

            //gl.glGetShaderInfoLog(this.shaderHelper.getShaderProgram());
        }
    }

    private void initBuffers(GL2ES2 gl) {
        gl.glGenBuffers(Buffer.MAX, this.bufferName);
        gl.glUseProgram(this.shaderHelper.getShaderProgram());

        // TODO REPLACE WITH STAIRS
        float[] positions = new float[]{
                1.0f,  1.0f, 0.0f,
                -1.0f, 1.0f, 0.0f,
                -1.0f, -1.0f, 0.0f,
                1.0f, -1.0f, 0.0f
        };

        float[] texture = new float[] {
                1.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f
        };

        short[] elements = new short[] {
                0, 1, 2,
                2, 3, 0
        };

        // Create VBO´s
        FloatBuffer positionVBO = GLBuffers.newDirectFloatBuffer(positions);
        FloatBuffer textureVBO = GLBuffers.newDirectFloatBuffer(texture);
        ShortBuffer indexVBO = GLBuffers.newDirectShortBuffer(elements);

        // vertices
        gl.glBindBuffer(GL_ARRAY_BUFFER, this.bufferName.get(Buffer.VERTEX));
        gl.glBufferData(GL_ARRAY_BUFFER, positionVBO.capacity() * Float.BYTES, positionVBO, GL_STATIC_DRAW);
        gl.glBindBuffer(GL_ARRAY_BUFFER, 0);

        // textures
        gl.glBindBuffer(GL_ARRAY_BUFFER, this.bufferName.get(Buffer.TEXTURE));
        gl.glBufferData(GL_ARRAY_BUFFER, textureVBO.capacity() * Float.BYTES, textureVBO, GL_STATIC_DRAW);
        gl.glBindBuffer(GL_ARRAY_BUFFER, 0);

        // elements
        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.bufferName.get(Buffer.ELEMENT));
        gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexVBO.capacity()*Short.BYTES, indexVBO, GL_STATIC_DRAW);
        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        // Vertices
        gl.glBindBuffer(GL_ARRAY_BUFFER, bufferName.get(Buffer.VERTEX));
        gl.glVertexAttribPointer(this.shaderHelper.getAttribute("position"), 3, GL_FLOAT, false, 0, 0);

        // elements
        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferName.get(Buffer.ELEMENT));
        gl.glEnableVertexAttribArray(this.shaderHelper.getAttribute("position"));

        // tex coords
        //gl.glBindBuffer(GL_ARRAY_BUFFER, bufferName.get(Buffer.TEXTURE));
        //gl.glVertexAttribPointer(this.shaderHelper.getAttribute("tex_coord_vert"), 2, GL_FLOAT, false, 0,0);

        //gl.glEnableVertexAttribArray(this.shaderHelper.getAttribute("tex_coord_vert"));

        // texture
        //gl.glActiveTexture(GL_TEXTURE0);
        //gl.glBindTexture(GL_TEXTURE_2D, textureName.get(0));
        //gl.glUniform1i(this.shaderHelper.getUniform("texture_sampler"), 0);

        checkError(gl, "Buffer");
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL2ES2 gl = drawable.getGL().getGL2ES2();

        checkError(gl, "Dispose");
    }

    // MATH

    private Mat4 getViewMatrix(float x, float y, float z, float yaw, float pitch, float roll) {
        return transformModel(x, -y, z, yaw, -pitch, roll);
    }

    private Mat4 transformModel(float x, float y, float z, float yaw, float pitch, float roll) {
        float deg2rad = (float) (Math.PI / 180.0);
        return new Mat4(1.0f)
                .multiply(Matrices.rotate(pitch * deg2rad, new Vec3(1.f, 0.f, 0.f))) // Roll v^+
                .multiply(Matrices.rotate(yaw * deg2rad, new Vec3(0.f, 1.f, 0.f))) // Pitch <-->+
                .multiply(Matrices.rotate(roll * deg2rad, new Vec3(0.f, 0.f, 1.f))) // Yaw O+
                .translate(new Vec3(x, y, z)); // translation z neg is away from center
    }

    private Mat4 getProjectionMatrix(float fov, float near, float far) {
        return Matrices.perspective(fov, // FOV
                (float)this.glWindow.getWidth() / (float)this.glWindow.getHeight(), // ASPECT
                near, far);
    }

    private double t0 = System.currentTimeMillis();
    private double theta;
    private double s;


    boolean once = false;
    @Override
    public void display(GLAutoDrawable drawable) {
        GL2ES2 gl = drawable.getGL().getGL2ES2();

        double t1 = System.currentTimeMillis();
        theta += (t1-t0)*0.001f;
        t0 = t1;
        s = Math.sin(theta);

        if(this.imageNeedsUpdate && false) {
            gl.glBindTexture(GL_TEXTURE_2D, textureName.get(0));

            TextureData tex = AWTTextureIO.newTextureData(gl.getGLProfile(), this.nextImage, true);

            try {
                tex = TextureIO.newTextureData(gl.getGLProfile(), this.getClass().getClassLoader().getResource("img/tex.png"),true, "png");
            } catch (IOException e) {
                e.printStackTrace();
            }


            gl.glTexImage2D(GL_TEXTURE_2D, 0, tex.getInternalFormat(), tex.getWidth(), tex.getHeight(), 0, tex.getPixelFormat(), GL_UNSIGNED_BYTE, tex.getBuffer());
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);

            this.imageNeedsUpdate = false;
        }

        Mat4 projection = this.getProjectionMatrix(55, 0.1f, 1000.f);// NEAR, FAR
        Mat4 model = transformModel(0, 0, 0, (float) (90*s), 0, 0);

        Mat4 view = getViewMatrix(0, 0.5f, -3, 0, -10, 0);

        Mat4 mvp = projection.multiply(view).multiply(model);

        gl.glUseProgram(this.shaderHelper.getShaderProgram());

        gl.glUniformMatrix4fv(this.shaderHelper.getUniform("mvp"), 1, false, mvp.getBuffer().array(), 0);
        gl.glUniform4f(this.shaderHelper.getUniform("diffuse"), 0.5f, 0.5f, 0.5f, 0.0f);

        //drawable.swapBuffers();
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl.glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_SHORT, 0);

        gl.glFlush();
        checkError(gl, "Draw");

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2ES2 gl = drawable.getGL().getGL2ES2();
        this.logger.info(String.format("Window has been resized to <%d, %d>", width, height));

        // recalculate projection matrix TODO

        // get shader and set new projectionmatrix TODO


        // update viewport
        gl.glViewport(0,0, width, height);

    }


    // from IBeamerFrame
    @Override
    public void draw(BufferedImage bufferedImage) {
        this.nextImage = bufferedImage;
        this.imageNeedsUpdate = true;
    }

    protected boolean checkError(GL gl, String title) {
        int error = gl.glGetError();
        if (error != GL_NO_ERROR) {
            String errorString;
            switch (error) {
                case GL_INVALID_ENUM:
                    errorString = "GL_INVALID_ENUM";
                    break;
                case GL_INVALID_VALUE:
                    errorString = "GL_INVALID_VALUE";
                    break;
                case GL_INVALID_OPERATION:
                    errorString = "GL_INVALID_OPERATION";
                    break;
                case GL_INVALID_FRAMEBUFFER_OPERATION:
                    errorString = "GL_INVALID_FRAMEBUFFER_OPERATION";
                    break;
                case GL_OUT_OF_MEMORY:
                    errorString = "GL_OUT_OF_MEMORY";
                    break;
                default:
                    errorString = "UNKNOWN";
                    break;
            }
            this.logger.error(String.format("GL Error(%03X): %s in '%s'",error, errorString, title));
        }
        return error == GL_NO_ERROR;
    }
}

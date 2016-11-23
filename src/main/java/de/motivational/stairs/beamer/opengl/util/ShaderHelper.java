package de.motivational.stairs.beamer.opengl.util;

import com.jogamp.opengl.GLES2;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;

/**
 * Created by vspadi on 23.11.16.
 */
public class ShaderHelper {
    int shaderProgram = -1;
    AtomicInteger attributeCounter;
    AtomicInteger uniformCounter;
    private HashMap<String, Integer> attributes;
    private HashMap<String, Integer> uniforms;
    private HashMap<String, Integer> textures;
    private GLES2 gl;

    public ShaderHelper(GLES2 gl, String shaderRoot, String shaderSource) {
        this.gl = gl;
        this.attributes = new HashMap<>();
        this.uniforms = new HashMap<>();
        this.textures = new HashMap<>();
        this.attributeCounter = new AtomicInteger();

        // load shader sources
        ShaderCode vertexShader = ShaderCode.create(this.gl, GL_VERTEX_SHADER, this.getClass(), shaderRoot, null, shaderSource, "vert", null, true);
        ShaderCode fragmentShader = ShaderCode.create(this.gl, GL_FRAGMENT_SHADER, this.getClass(), shaderRoot, null, shaderSource, "frag", null, true);

        // default customization
        vertexShader.defaultShaderCustomization(gl, true, false);
        fragmentShader.defaultShaderCustomization(gl, true, false);

        // compile program
        ShaderProgram program = new ShaderProgram();
        program.add(vertexShader);
        program.add(fragmentShader);

        program.init(this.gl);
        this.shaderProgram = program.program();
        program.link(this.gl, System.out);
    }

    public int getShaderProgram() {
        return shaderProgram;
    }

    public void bindAttribute(String name) {
        int location = this.attributeCounter.getAndIncrement();
        this.gl.glUseProgram(this.shaderProgram);
        this.gl.glBindAttribLocation(this.shaderProgram, location, name);
        this.gl.glUseProgram(0);
        this.attributes.put(name.toUpperCase(), location);
    }

    public int getAttribute(String name) {
        return this.attributes.getOrDefault(name.toUpperCase(), -1);
    }

    public void bindUniform(String name) {
        this.gl.glUseProgram(this.shaderProgram);
        this.uniforms.put(name.toUpperCase(), this.gl.glGetUniformLocation(this.shaderProgram, name));
        this.gl.glUseProgram(0);
    }

    public int getUniform(String name) {
        return this.uniforms.getOrDefault(name.toUpperCase(), -1);
    }

    public void bindTexture(String name, BufferedImage image) {

    }
}

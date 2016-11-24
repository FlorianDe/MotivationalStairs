precision highp float;

uniform vec4 diffuse;
//varying vec2 tex_coord;

//uniform sampler2D texture_sampler;
//out vec4 fragColor;

void main (void)
{
    //gl_FragColor = texture(texture_sampler, tex_coord);
    gl_FragColor = diffuse;
}
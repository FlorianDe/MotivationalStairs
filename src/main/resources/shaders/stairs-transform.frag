precision highp float;

uniform vec4 diffuse;
in vec2 tex_coord;

uniform sampler2D texture_sampler;
out vec4 fragColor;

void main (void)
{
    //fragColor = vec4(tex_coord, 1.0, 1.0) + texture(texture_sampler, tex_coord);
    fragColor = texture(texture_sampler, tex_coord) + diffuse;
    //gl_FragColor = diffuse;
    //fragColor = diffuse;
}
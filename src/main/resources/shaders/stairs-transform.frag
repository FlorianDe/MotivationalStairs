precision highp float;

uniform vec4 diffuse;
in vec2 tex_coord;

uniform sampler2D texture_sampler;
out vec4 fragColor;

void main (void)
{
    fragColor = texture(texture_sampler, tex_coord);
}
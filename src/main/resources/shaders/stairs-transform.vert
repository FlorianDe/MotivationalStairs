precision highp float;

uniform mat4 mvp;
in vec4 position;
in vec2 tex_coord_vert;

out vec2 tex_coord;

void main()
{
    tex_coord = tex_coord_vert;
    gl_Position = mvp * position;
}
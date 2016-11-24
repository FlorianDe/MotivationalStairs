precision highp float;

uniform mat4 mvp;
attribute vec4 position;
//attribute vec2 tex_coord_vert;

//varying vec2 tex_coord;

void main()
{
    //tex_coord = tex_coord_vert;
    gl_Position = mvp * position;
}
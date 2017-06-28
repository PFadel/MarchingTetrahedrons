#version 330

layout (location = 0) in vec4 position;
layout (location = 1) in vec4 normal;
layout (location = 2) in vec4 color;

//matrix
uniform mat4 modelmatrix;
uniform mat4 viewmatrix;
uniform mat4 projection;

out vec4 newNormal;
out vec4 ourPostion;
out mat4 ourModView;
out vec4 ourColor;

void main()
{
    mat4 modelView = viewmatrix * modelmatrix;
    mat4 normalMatrix = transpose(inverse(modelView));

    // final vertex position
    gl_Position = projection * modelView * position;

    vec4 newNormal = normalize(normalMatrix * normal);

    ourPostion = position;
    ourModView = modelView;
    ourColor = color;
}
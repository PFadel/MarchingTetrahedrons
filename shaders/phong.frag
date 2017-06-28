#version 330

//matrix
uniform mat4 modelmatrix;

//light parameters
uniform vec3 lightPos;
uniform vec3 ambientColor; 
uniform vec3 diffuseColor;
uniform vec3 speclarColor;
uniform float kA, kD, kS, sN;

out vec4 outputColor;
in vec4 newNormal;
in vec4 ourPostion;
in mat4 ourModView;
in vec4 ourColor;

void main()
{
    vec4 positionWorld = modelmatrix * ourPostion;

    //diffuse
    vec3 lightDir = normalize(lightPos - positionWorld.xyz);
    float iD = max(0.0, dot(lightDir, newNormal.xyz));

    //specular
    vec3  v  = -normalize((ourModView * ourPostion).xyz);
    vec3  h  =  normalize(lightDir + v);
    float iS =  pow(max(0.0, dot(newNormal.xyz, h)), sN);

    vec3 lightFactor = kA * ambientColor + kD * iD * diffuseColor + kS * iS * speclarColor;

    outputColor = vec4(ourColor.rgb * lightFactor, ourColor.a);
}
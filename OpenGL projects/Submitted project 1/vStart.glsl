#version 150

in  vec4 vPosition;
in  vec3 vNormal;
in  vec2 vTexCoord;

out vec2 texCoord;
out vec3 pos;
out vec3 normal;
 
uniform mat4 ModelView;
uniform mat4 Projection;


void main()
{
    // Transform vertex position into eye coordinates
    pos = (ModelView * vPosition).xyz;

     normal = (mat3(ModelView) * vNormal);


    gl_Position = Projection * ModelView * vPosition;
    texCoord = vTexCoord;
}

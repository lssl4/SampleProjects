#version 130

in  vec4 vPosition;
in  vec4 vNormal;
in  vec2 vTexCoord;
in ivec4 boneIDs;
in  vec4 boneWeights;
uniform mat4 boneTransforms[64];


out vec2 texCoord;
out vec3 pos;
out vec3 normal;

uniform mat4 ModelView;
uniform mat4 Projection;


void main()
{
	mat4 boneTransform = boneWeights[0] * boneTransforms[boneIDs[0]] +
	boneWeights[1] * boneTransforms[boneIDs[1]] +
	boneWeights[2] * boneTransforms[boneIDs[2]] +
	boneWeights[3] * boneTransforms[boneIDs[3]];
    

    vec4 vPosTrans = boneTransform * vPosition;
    vec4 normTrans = boneTransform * vNormal;
    
    normal = normTrans.xyz;

    // Transform vertex position into eye coordinates
    pos = (ModelView * vPosTrans).xyz;
    gl_Position = Projection * ModelView * vPosTrans;
    texCoord = vTexCoord;
}

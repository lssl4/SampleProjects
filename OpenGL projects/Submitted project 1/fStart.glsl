#version 150

in  vec2 texCoord;  // The third coordinate is always 0.0 and is discarded
in  vec3 normal;
in  vec3 pos;

out vec4 fColor;

uniform sampler2D texture;

uniform mat4 ModelView;
uniform vec3 AmbientProduct, DiffuseProduct, SpecularProduct;
uniform vec4 LightPosition;
uniform vec4 LightPosition2; //For second light sphere
uniform vec3 Light1rgbBright;
uniform vec3 Light1rgbBright2; //For second light sphere
uniform float Shininess;

uniform vec4 Origin;
void
main()
{

    vec3 Lvec = LightPosition.xyz - pos; //point light
   vec3 Lvec2 = (LightPosition2 - Origin).xyz; ;//directional light


    if(LightPosition.w == 0.0) {
      Lvec = LightPosition.xyz;
    }

  if(LightPosition2.w == 0.0){
	     Lvec2 = LightPosition2.xyz;
  }
    
   
    vec3 N = normalize(ModelView*vec4(normal.xyz, 0.0)).xyz; // Unit direction vectors for Blinn-Phong shading calculation
    vec3 L = normalize( Lvec );   // Direction to the light source
    vec3 L2 = normalize( Lvec2 );   // Direction to the light source 2

    vec3 E = normalize( -pos );   // Direction to the eye/camera
    vec3 H = normalize( L + E );  // Halfway vector
    vec3 H2 = normalize( L2 + E );  // Halfway vector 2


 
    
    // Compute terms in the illumination equation
    vec3 ambient = AmbientProduct;

    float Kd = max( dot(L, N), 0.0 );
    float Kd2 = max( dot(L2, N), 0.0 );

    vec3  diffuse = (Kd*DiffuseProduct);
    vec3  diffuse2 = (Kd2*DiffuseProduct);


    float Ks = pow( max(dot(N, H), 0.0), Shininess );
    float Ks2 = pow( max(dot(N, H2), 0.0), Shininess );

    vec3  specular = Ks * SpecularProduct;
    vec3  specular2 = Ks2 * SpecularProduct;

    
    if( dot(L, N) < 0.0 ) {
	specular = vec3(0.0, 0.0, 0.0);
    } 
    if( dot(L2, N) < 0.0 ) {
	specular2 = vec3(0.0, 0.0, 0.0);
    } 

    // globalAmbient is independent of distance from the light source
    vec3 globalAmbient = vec3(0.15, 0.15, 0.15);
    float distanceBrightness = length(Lvec) * 0.25; //Add one to prevent distance to become zero 
    float distanceBrightness2 = length(Lvec2) * 0.25; //Add one to prevent distance to become zero 

    vec3 light1ad = (Light1rgbBright * (ambient + diffuse))/distanceBrightness;  
    vec3 light1ad2 = (Light1rgbBright2 * (ambient + diffuse2))/distanceBrightness2; 

    vec3 color = (globalAmbient + light1ad);
    vec3 color2 = ( light1ad2);

    

  fColor = vec4(color + color2, 1.0) * texture2D(texture, texCoord * 2.0)+ vec4(specular,0)+vec4(specular2,0);
}


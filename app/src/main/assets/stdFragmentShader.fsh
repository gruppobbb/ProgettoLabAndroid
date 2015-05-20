precision mediump float;

varying vec3 varyingNormal;
varying vec3 varyingPosition;
varying vec3 varyingTxCoord;

uniform sampler2D textureMaterial;

void main(){
  
    vec2 texCoord = varyingTxCoord.xy;
  
    vec4 color1=texture2D(textureMaterial,texCoord);

/*
	color1.x = 0.8f;
	color1.y = 0.0f;
	color1.z = 0.2f;
	color1.w = 1.0f;
*/
	gl_FragColor=color1;

}
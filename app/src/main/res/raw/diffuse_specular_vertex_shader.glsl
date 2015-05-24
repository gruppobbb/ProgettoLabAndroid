uniform mat4 u_PMatrix;
uniform mat4 u_VMatrix;
uniform mat4 u_InverseVMatrix;
uniform mat4 u_MMatrix;
uniform vec3 u_LightPos;

attribute vec4 a_Position;
attribute vec3 a_Normal;
attribute vec2 a_TextureCoordinates;

varying vec2 v_TextureCoordinates;
varying vec3 v_SurfaceNormal;
varying vec3 v_toCamera;
varying vec3 v_toLightVector;



void main() {

	vec3 tmp = u_LightPos;
	tmp.x = 0.0;
	tmp.y = 10.0;
	tmp.z = 10.0;

    vec4 wordlPosition = u_MMatrix * a_Position;
    gl_Position = u_PMatrix * u_VMatrix * wordlPosition;

    v_TextureCoordinates = a_TextureCoordinates;

    v_SurfaceNormal = normalize( (u_MMatrix * vec4(a_Normal, 0.0)).xyz );

    //v_toLightVector = u_LightPos - wordlPosition.xyz;
    v_toLightVector = tmp.xyz - wordlPosition.xyz;

    v_toCamera = ( u_InverseVMatrix * vec4(0.0, 0.0, 0.0, 1.0) ).xyz - wordlPosition.xyz;

}
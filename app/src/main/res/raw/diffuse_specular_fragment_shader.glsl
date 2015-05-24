precision mediump float;

uniform sampler2D u_TextureUnit;
uniform vec4 u_LightColor;
uniform float u_ShineDamper;
uniform float u_Reflectivity;

varying vec2 v_TextureCoordinates;
varying vec3 v_SurfaceNormal;
varying vec3 v_toCamera;
varying vec3 v_toLightVector;

void main() {

    vec4 color = vec4(0.8, 0, 0.2, 1.0);

    float distance = length( v_toLightVector );

    vec3 normalizedLightVector = normalize(v_toLightVector);

    float lightDot = dot(v_SurfaceNormal, normalizedLightVector);

    float diffuse = max( lightDot, 0.1);

    //diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance )));
    diffuse = diffuse * (1.0 / (1.0 + (0.10 * distance )));

    diffuse = diffuse + 0.3;

    vec3 normalizedToCamera = normalize(v_toCamera);
    vec3 lightDirection = -normalizedLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, v_SurfaceNormal);

    float specularFactor = max( dot(reflectedLightDirection, normalizedToCamera ), 0.0 );
    float dampedFactor = pow(specularFactor, u_ShineDamper);
    vec3 finalSpecular = dampedFactor * u_Reflectivity * u_LightColor.xyz;

    //gl_FragColor = diffuse * color + vec4(finalSpecular, 1.0);
    gl_FragColor = diffuse * texture2D(u_TextureUnit, v_TextureCoordinates) + vec4(finalSpecular, 1.0);
    //gl_FragColor = diffuse * texture2D(u_TextureUnit, v_TextureCoordinates) + vec4(finalSpecular, 1.0);

}
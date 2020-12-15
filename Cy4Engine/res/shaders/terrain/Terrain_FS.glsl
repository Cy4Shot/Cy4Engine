#version 330

layout(location = 0) out vec4 outputColor;

in vec2 mapCoord_FS;
in vec3 position_FS;
in vec3 tangent_FS;

struct Material {
	sampler2D diffusemap;
	sampler2D normalmap;
	sampler2D heightmap;
	float heightScaling;
	float horizontalScaling;
};

uniform sampler2D normalmap;
uniform Material materials[2];
uniform int tbn_range;
uniform vec3 cameraPosition;

const vec3 direction = vec3(0.1, -1, 0.1);
const float intensity = 1.2;

float diffuse(vec3 direction, vec3 normal, float intensity) {
	return max(0.03, dot(normal, -direction) * intensity);
}

void main() {

	float dist = length(cameraPosition - position_FS);
	float height = position_FS.y;

	vec3 normal = normalize(texture(normalmap, mapCoord_FS).rgb);

	vec3 material0Color = texture(materials[0].diffusemap,
			mapCoord_FS * materials[0].horizontalScaling).rgb;
	vec3 material1Color = texture(materials[1].diffusemap,
			mapCoord_FS * materials[1].horizontalScaling).rgb;
	float[2] materialAlpha = float[](0, 0);
	if (normal.y > 0.5) {
		materialAlpha[1] = 1;
	} else {
		materialAlpha[0] = 1;
	}

	if (dist < tbn_range - 50) {
		float attenuation = clamp(-dist / (tbn_range - 50) + 1, 0.0, 1.0);

		vec3 bitangent = normalize(cross(tangent_FS, normal));
		mat3 TBN = mat3(bitangent, normal, tangent_FS);

		vec3 bumpNormal;
		for (int i = 0; i < 2; i++) {
			bumpNormal += (2 * (texture(materials[i].normalmap,mapCoord_FS * materials[i].horizontalScaling).rbg) - 1) * materialAlpha[i];
		}
		bumpNormal = normalize(bumpNormal);
		bumpNormal.xz *= attenuation;
		normal = normalize(TBN * bumpNormal);
	}

	vec3 fragColor = material0Color * materialAlpha[0]
			+ material1Color * materialAlpha[1];

	float diffuse = diffuse(direction, normal, intensity);

	fragColor *= diffuse;

	outputColor = vec4(fragColor, 1.0);
}

#version 430

in vec3 worldPosition;

layout (location = 0) out vec4 outputColor;

const vec3 baseColor = vec3(0.18,0.27,0.47);

void main()
{
	float red = -0.00022 * (worldPosition.y - 2800) + baseColor.x;
	float gre = -0.00025 * (worldPosition.y - 2800) + baseColor.y;
	float blu = -0.00019 * (worldPosition.y - 2800) + baseColor.z;
	
	outputColor = vec4(red,gre,blu,1);
}

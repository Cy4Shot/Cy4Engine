#version 430

layout (location = 0) in vec3 position;

out vec3 worldPosition;

uniform mat4 m_MVP;
uniform mat4 m_World;

void main()
{
	gl_Position = m_MVP * vec4(position,1);
	worldPosition = (m_World * vec4(position,1)).xyz;
}

package io.spring.blog.api.role;

import javax.validation.constraints.NotEmpty;

public class RoleDto {

	private Long id;

	@NotEmpty(message = "Role name should not be null or empty")
	private String name;

	public RoleDto() {
	}

	public RoleDto(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

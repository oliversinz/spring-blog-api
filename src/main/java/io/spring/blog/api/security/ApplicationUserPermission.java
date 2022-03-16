package io.spring.blog.api.security;

public enum ApplicationUserPermission {

	POST_READ("post:read"),
	POST_WRITE("post:write"),
	ROLE_READ("role:read"),
	ROLE_WRITE("role:write");

	private final String permission;

	ApplicationUserPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}

}

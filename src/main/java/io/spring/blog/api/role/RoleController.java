package io.spring.blog.api.role;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleController {

	private RoleService roleService;

	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	// createEntity
	@PostMapping("/roles")
	public RoleDto createRole(@Valid @RequestBody RoleDto roleDto) {
		return roleService.createRole(roleDto);
	}

	// getAllEntities
	@GetMapping("/roles")
	public List<RoleDto> getAllRoles() {
		return roleService.getAllRoles();
	}

	// getOneEntity
	@GetMapping("/roles/{id}")
	public RoleDto getRoleById(@PathVariable("id") Long id) {
		return roleService.getRoleById(id);
	}

	// updateEntity
	@PutMapping("/roles/{id}")
	public RoleDto updateRole(@PathVariable("id") Long id, @Valid @RequestBody RoleDto roleDto) {
		return roleService.updateRole(id, roleDto);
	}

	// deleteEntity
	@DeleteMapping("/roles/{id}")
	public void deleteRoleById(@PathVariable("id") Long id) {
		roleService.deleteRoleById(id);
	}

}

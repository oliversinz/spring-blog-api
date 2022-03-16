package io.spring.blog.api.role;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.spring.blog.api.exception.ResourceNotFoundException;

@Service
public class RoleService {

	private RoleRepository roleRepository;

	private ModelMapper mapper;

	@Autowired
	public RoleService(RoleRepository roleRepository, ModelMapper mapper) {
		this.roleRepository = roleRepository;
		this.mapper = mapper;
	}

	// createEntity
	public RoleDto createRole(RoleDto roleDto) {
		Role role = mapToEntity(roleDto);
		Role newRole = roleRepository.save(role);
		return mapToDTO(newRole);
	}

	// getAllEntities
	public List<RoleDto> getAllRoles() {
		return roleRepository.findAll().stream().map(element -> mapToDTO(element)).collect(Collectors.toList());
	}

	// getOneEntity
	public RoleDto getRoleById(Long id) {
		Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
		return mapToDTO(role);
	}

	// updateEntity
	public RoleDto updateRole(Long id, RoleDto roleDto) {
		Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
		role.setName(roleDto.getName());
		Role updatedRole = roleRepository.save(role);
		return mapToDTO(updatedRole);
	}

	// deleteEntity
	public void deleteRoleById(Long id) {
		Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
		roleRepository.delete(role);
	}

	// Entity to DTO
	private RoleDto mapToDTO(Role role) {
		RoleDto roleDto = mapper.map(role, RoleDto.class);
		return roleDto;
	}

	// DTO to entity
	private Role mapToEntity(RoleDto roleDto) {
		Role role = mapper.map(roleDto, Role.class);
		return role;
	}

}

package io.spring.blog.api.user;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.spring.blog.api.exception.ResourceNotFoundException;

@Service
public class UserService {

	private UserRepository userRepository;

	private ModelMapper mapper;

	@Autowired
	public UserService(UserRepository userRepository, ModelMapper mapper) {
		this.userRepository = userRepository;
		this.mapper = mapper;
	}

	// createEntity
	public UserDto createUser(UserDto userDto) {
		User user = mapToEntity(userDto);
		User newUser = userRepository.save(user);
		return mapToDTO(newUser);
	}

	// getAllEntities
	public List<UserDto> getAllUsers() {
		return userRepository.findAll().stream().map(element -> mapToDTO(element)).collect(Collectors.toList());
	}

	// getOneEntity
	public UserDto getUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return mapToDTO(user);
	}

	// updateEntity
	public UserDto updateUser(Long id, UserDto userDto) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		User updatedUser = userRepository.save(user);
		return mapToDTO(updatedUser);
	}

	// deleteEntity
	public void deleteUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		userRepository.delete(user);
	}

	// Entity to DTO
	private UserDto mapToDTO(User user) {
		UserDto userDto = mapper.map(user, UserDto.class);
		return userDto;
	}

	// DTO to entity
	private User mapToEntity(UserDto userDto) {
		User user = mapper.map(userDto, User.class);
		return user;
	}

}

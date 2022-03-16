package io.spring.blog.api.user;

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
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	// createEntity
	@PostMapping("/users")
	public UserDto createUser(@Valid @RequestBody UserDto userDto) {
		return userService.createUser(userDto);
	}

	// getAllEntities
	@GetMapping("/users")
	public List<UserDto> getAllUsers() {
		return userService.getAllUsers();
	}

	// getOneEntity
	@GetMapping("/users/{id}")
	public UserDto getUserById(@PathVariable("id") Long id) {
		return userService.getUserById(id);
	}

	// updateEntity
	@PutMapping("/users/{id}")
	public UserDto updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto) {
		return userService.updateUser(id, userDto);
	}

	// deleteEntity
	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable("id") Long id) {
		userService.deleteUserById(id);
	}

}

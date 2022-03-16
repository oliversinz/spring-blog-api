package io.spring.blog.api.post;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
public class PostController {

	private PostService postService;

	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping(value = "/username")
	public String currentUserName(Authentication authentication) {
		return authentication.getName();
	}

	// getAllEntities
	@GetMapping("/posts")
	public List<PostDto> getAllPosts() {
		return postService.getAllPosts();
	}

	// getOneEntity
	@GetMapping("/posts/{id}")
	public PostDto getPostById(@PathVariable("id") Long id) {
		return postService.getPostById(id);
	}

	// createEntity (based on logged in user)
	@PostMapping("/posts")
	public PostDto createPost(@Valid @RequestBody PostDto postDto) {
		return postService.createPost(postDto);
	}

	// updateEntity (if post belongs to user)
	@PutMapping("/posts/{id}")
	public PostDto updatePost(@PathVariable("id") Long id, @Valid @RequestBody PostDto postDto) {
		return postService.updatePost(id, postDto);
	}

	// deleteEntity (if post belongs to user)
	@DeleteMapping("/posts/{id}")
	public void deletePostById(@PathVariable("id") Long id) {
		postService.deletePostById(id);
	}

}

package io.spring.blog.api.post;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.spring.blog.api.exception.ResourceNotFoundException;
import io.spring.blog.api.exception.RestAPIException;
import io.spring.blog.api.user.User;
import io.spring.blog.api.user.UserRepository;

@Service
public class PostService {

	private PostRepository postRepository;

	private UserRepository userRepository;

	private ModelMapper mapper;

	@Autowired
	public PostService(PostRepository postRepository, UserRepository userRepository, ModelMapper mapper) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.mapper = mapper;
	}

	// getAllEntities
	public List<PostDto> getAllPosts() {
		List<Post> postList = postRepository.findAll();
		return postList.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
	}

	// getOneEntity
	public PostDto getPostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapToDTO(post);
	}

	// createEntity (based on logged in user)
	public PostDto createPost(PostDto postDto) {
		User user = userRepository.findByEmail(getActiveUserEmail()).get();
		Post post = mapToEntity(postDto);
		post.setUser(user);
		Post newPost = postRepository.save(post);
		return mapToDTO(newPost);
	}

	// updateEntity (if post belongs to user)
	public PostDto updatePost(Long id, PostDto postDto) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		if (!post.getUser().getEmail().equals(getActiveUserEmail())) {
			throw new RestAPIException(HttpStatus.BAD_REQUEST, "Post does not belong to author");
		}
		post.setPostTitle(postDto.getPostTitle());
		post.setPostContent(postDto.getPostContent());
		Post updatedPost = postRepository.save(post);
		return mapToDTO(updatedPost);
	}

	// deleteEntity (if post belongs to user)
	public void deletePostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		if (!post.getUser().getEmail().equals(getActiveUserEmail())) {
			throw new RestAPIException(HttpStatus.BAD_REQUEST, "Post does not belong to author");
		}
		postRepository.delete(post);
	}

	private String getActiveUserEmail() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			return username;
		} else {
			String username = principal.toString();
			return username;
		}
	}

	// Entity to DTO
	private PostDto mapToDTO(Post post) {
		PostDto postDto = mapper.map(post, PostDto.class);
		return postDto;
	}

	// DTO to entity
	private Post mapToEntity(PostDto postDto) {
		Post post = mapper.map(postDto, Post.class);
		return post;
	}

}

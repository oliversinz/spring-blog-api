package io.spring.blog.api.tag;

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
import io.spring.blog.api.post.Post;
import io.spring.blog.api.post.PostRepository;

@Service
public class TagService {

	private TagRepository tagRepository;

	private PostRepository postRepository;

	private ModelMapper mapper;

	@Autowired
	public TagService(TagRepository tagRepository, PostRepository postRepository, ModelMapper mapper) {
		this.tagRepository = tagRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	// getAllEntities for one Tag
	public List<TagDto> getTagsByPostId(Long postId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		List<Tag> tags = tagRepository.findByPosts_Id(postId);

		return tags.stream().map(tag -> mapToDTO(tag)).collect(Collectors.toList());

	}

	// createEntity (if post belongs to logged in user)
	public TagDto createTag(Long postId, TagDto tagDto) {

		Tag tag = mapToEntity(tagDto);

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		if (!post.getUser().getEmail().equals(getActiveUserEmail())) {
			throw new RestAPIException(HttpStatus.BAD_REQUEST, "Post does not belong to author");
		}

		Tag newTag = tagRepository.save(tag);

		post.addTag(tag);

		postRepository.save(post);

		return mapToDTO(newTag);

	}

	// updateEntity (if post belongs to logged in user)
	public TagDto updateTag(Long postId, Long tagId, TagDto tagDto) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		if (!post.getUser().getEmail().equals(getActiveUserEmail())) {
			throw new RestAPIException(HttpStatus.BAD_REQUEST, "Post does not belong to author");
		}

		Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));

		List<Tag> tags = tagRepository.findByPosts_Id(postId);

		if (!tags.contains(tag)) {
			throw new RestAPIException(HttpStatus.BAD_REQUEST, "Tag does not belong to post");
		}

		tag.setTagName(tagDto.getTagName());
		tag.setTagDescription(tagDto.getTagDescription());

		Tag updatedTag = tagRepository.save(tag);

		return mapToDTO(updatedTag);

	}

	// deleteEntity (if post belongs to logged in user)
	public void deleteTag(Long postId, Long tagId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		if (!post.getUser().getEmail().equals(getActiveUserEmail())) {
			throw new RestAPIException(HttpStatus.BAD_REQUEST, "Post does not belong to author");
		}

		Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));

		List<Tag> tags = tagRepository.findByPosts_Id(postId);

		if (!tags.contains(tag)) {
			throw new RestAPIException(HttpStatus.BAD_REQUEST, "Tag does not belong to post");
		}

		post.removeTag(tag);

		tagRepository.delete(tag);

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
	private TagDto mapToDTO(Tag tag) {
		TagDto tagDto = mapper.map(tag, TagDto.class);
		return tagDto;
	}

	// DTO to entity
	private Tag mapToEntity(TagDto tagDto) {
		Tag tag = mapper.map(tagDto, Tag.class);
		return tag;
	}

}

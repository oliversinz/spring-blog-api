package io.spring.blog.api.post;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.spring.blog.api.comment.CommentDto;
import io.spring.blog.api.tag.TagDto;
import io.spring.blog.api.user.UserDto;

public class PostDto {

	private Long id;

	@NotNull
	@Size(min = 5, message = "Post title should have at least 5 characters")
	private String postTitle;

	@NotNull
	@Size(min = 10, message = "Post content should have at least 10 characters")
	private String postContent;

	private UserDto userDto;

	private List<CommentDto> comments = new ArrayList<CommentDto>();

	private Set<TagDto> tags = new HashSet<TagDto>();

	public PostDto() {
	}

	public PostDto(String postTitle, String postContent) {
		this.postTitle = postTitle;
		this.postContent = postContent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public List<CommentDto> getComments() {
		return comments;
	}

	public void setComments(List<CommentDto> comments) {
		this.comments = comments;
	}

	public Set<TagDto> getTags() {
		return tags;
	}

	public void setTags(Set<TagDto> tags) {
		this.tags = tags;
	}

}

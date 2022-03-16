package io.spring.blog.api.comment;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentDto {

	private Long id;

	@NotNull
	@Size(min = 3, message = "Comment author should have at least 3 characters")
	private String commentAuthor;

	@NotNull
	@Size(min = 10, message = "Comment content should have at least 5 characters")
	private String commentContent;

	public CommentDto() {
	}

	public CommentDto(String commentAuthor, String commentContent) {
		this.commentAuthor = commentAuthor;
		this.commentContent = commentContent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCommentAuthor() {
		return commentAuthor;
	}

	public void setCommentAuthor(String commentAuthor) {
		this.commentAuthor = commentAuthor;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

}

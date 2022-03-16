package io.spring.blog.api.comment;

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
public class CommentController {

	private CommentService commentService;

	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	// createEntity (based on post)
	@PostMapping("posts/{postId}/comments")
	public CommentDto createComment(@PathVariable("postId") Long postId, @Valid @RequestBody CommentDto commentDto) {
		return commentService.createComment(postId, commentDto);
	}

	// getAllEntities for one Post
	@GetMapping("posts/{postId}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable("postId") Long postId) {
		return commentService.getCommentsByPostId(postId);
	}

	// getOneEntity for Post and Comment
	@GetMapping("posts/{postId}/comments/{commentId}")
	public CommentDto getCommentById(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
		return commentService.getCommentById(postId, commentId);
	}

	// updateEntity
	@PutMapping("posts/{postId}/comments/{commentId}")
	public CommentDto updateComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId,
			@Valid @RequestBody CommentDto commentDto) {
		return commentService.updateComment(postId, commentId, commentDto);
	}

	// deleteEntity
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public void deleteComment(@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "commentId") Long commentId) {
		commentService.deleteComment(postId, commentId);
	}

}

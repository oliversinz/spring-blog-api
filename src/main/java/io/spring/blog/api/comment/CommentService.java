package io.spring.blog.api.comment;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.spring.blog.api.exception.ResourceNotFoundException;
import io.spring.blog.api.exception.RestAPIException;
import io.spring.blog.api.post.Post;
import io.spring.blog.api.post.PostRepository;

@Service
public class CommentService {

	private CommentRepository commentRepository;

	private PostRepository postRepository;

	private ModelMapper mapper;

	@Autowired
	public CommentService(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		super();
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	// createEntity (based on post)
	public CommentDto createComment(Long postId, CommentDto commentDto) {

		Comment comment = mapToEntity(commentDto);

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		comment.setPost(post);

		Comment newComment = commentRepository.save(comment);

		return mapToDTO(newComment);

	}

	// getAllEntities for one Post
	public List<CommentDto> getCommentsByPostId(Long postId) {

		List<Comment> comments = commentRepository.findByPostId(postId);

		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());

	}

	// getOneEntity for Post and Comment
	public CommentDto getCommentById(Long postId, Long commentId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new RestAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}

		return mapToDTO(comment);

	}

	// updateEntity
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new RestAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}

		comment.setCommentAuthor(commentDto.getCommentAuthor());
		comment.setCommentContent(commentDto.getCommentContent());

		Comment updatedComment = commentRepository.save(comment);

		return mapToDTO(updatedComment);

	}

	// deleteEntity
	public void deleteComment(Long postId, Long commentId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new RestAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}

		commentRepository.delete(comment);
	}

	// Entity to DTO
	private CommentDto mapToDTO(Comment comment) {
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
		return commentDto;
	}

	// DTO to entity
	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = mapper.map(commentDto, Comment.class);
		return comment;
	}

}

package io.spring.blog.api.tag;

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
public class TagController {

	private TagService tagService;

	@Autowired
	public TagController(TagService tagService) {
		this.tagService = tagService;
	}

	// getAllEntities for one Tag
	@GetMapping("posts/{postId}/tags")
	public List<TagDto> getTagsByPostId(@PathVariable("postId") Long postId) {
		return tagService.getTagsByPostId(postId);
	}

	// createEntity (if post belongs to logged in user)
	@PostMapping("posts/{postId}/tags")
	public TagDto createTag(@PathVariable("postId") Long postId, @Valid @RequestBody TagDto tagDto) {
		return tagService.createTag(postId, tagDto);
	}

	// updateEntity (if post belongs to logged in user)
	@PutMapping("posts/{postId}/tags/{tagId}")
	public TagDto updateTag(@PathVariable("postId") Long postId, @PathVariable("tagId") Long tagId,
			@Valid @RequestBody TagDto tagDto) {
		return tagService.updateTag(postId, tagId, tagDto);
	}

	// deleteEntity (if post belongs to logged in user)
	@DeleteMapping("/posts/{postId}/tags/{tagId}")
	public void deleteTag(@PathVariable(value = "postId") Long postId, @PathVariable(value = "tagId") Long tagId) {
		tagService.deleteTag(postId, tagId);
	}

}

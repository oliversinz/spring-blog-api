package io.spring.blog.api.tag;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TagDto {

	private Long id;

	@NotNull
	@Size(min = 3, message = "Tag name should have at least 3 characters")
	private String tagName;

	private String tagDescription;

	public TagDto() {
	}

	public TagDto(String tagName) {
		this.tagName = tagName;
	}

	public TagDto(String tagName, String tagDescription) {
		this.tagName = tagName;
		this.tagDescription = tagDescription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagDescription() {
		return tagDescription;
	}

	public void setTagDescription(String tagDescription) {
		this.tagDescription = tagDescription;
	}

}

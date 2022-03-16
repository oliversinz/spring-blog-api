package io.spring.blog.api.tag;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.spring.blog.api.post.Post;

@Entity
@Table(name = "tag")
public class Tag implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tag_name", nullable = false)
	private String tagName;

	@Column(name = "tag_description", nullable = true)
	private String tagDescription;

	@JsonBackReference(value = "post-tag")
	@ManyToMany(mappedBy = "tags")
	private Set<Post> posts = new HashSet<Post>();

	public Tag() {
	}

	public Tag(String tagName, String tagDescription) {
		this.tagName = tagName;
		this.tagDescription = tagDescription;
	}

	public Tag(String tagName) {
		this.tagName = tagName;
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

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

}

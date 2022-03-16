package io.spring.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.spring.blog.api.comment.Comment;
import io.spring.blog.api.post.Post;
import io.spring.blog.api.post.PostRepository;
import io.spring.blog.api.role.Role;
import io.spring.blog.api.tag.Tag;
import io.spring.blog.api.user.User;
import io.spring.blog.api.user.UserRepository;

@Component
public class Dataloader implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {

		Role role1 = new Role("ROLE_ADMIN");
		User user1 = new User("Oliver", "Sinz", "admin", "oliversinz@gmail.com",
				"$2a$10$aqjsBQkVdkf4BZpyWOc/Seb284QDDnym8lsjuvZ6YvUnMbc6yGteO");
		user1.addRole(role1);
		userRepository.save(user1);

		Role role2 = new Role("ROLE_USER");
		User user2 = new User("Anucha", "Thamnao", "user", "anuchathamnao@gmail.com",
				"$2a$10$aqjsBQkVdkf4BZpyWOc/Seb284QDDnym8lsjuvZ6YvUnMbc6yGteO");
		user2.addRole(role2);
		userRepository.save(user2);

		Post post1 = new Post("First post", "First post content");

		Comment comment1 = new Comment("Jack", "Very useful information");
		Tag tag1 = new Tag("Java", "Java and beyond");

		post1.addComment(comment1);
		post1.addTag(tag1);
		post1.setUser(user1);

		postRepository.save(post1);

		Post post2 = new Post("Second post", "Second post content");

		Comment comment2 = new Comment("Nick", "Thanks for the great blog post");
		Tag tag2 = new Tag("Spring", "Spring and beyond");

		post2.addComment(comment2);
		post2.addTag(tag2);
		post2.setUser(user2);

		postRepository.save(post2);

	}

}

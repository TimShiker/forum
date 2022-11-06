package telran.java2022.forum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Post {
	@Id
	String id;
	
	@Setter
	String title;
	
	@Setter
	String content;
	String author;
	LocalDateTime dateCreated = LocalDateTime.now().withNano(0);
	
	@Setter
	HashSet<String> tags;
	
	@Setter
	Integer likes;
	
	@Setter
	List<Comment> comments = new ArrayList<Comment>();
	
	public Post(String author, String title, String content, HashSet<String> tags) {
		this.author = author;
		this.title = title;
		this.content = content;
		this.tags = tags;
		likes = 0;
	}
}

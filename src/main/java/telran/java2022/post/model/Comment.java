package telran.java2022.post.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class Comment {
	@Setter
	String user;
	
	@Setter
	String message;
	
	LocalDateTime dateCreated = LocalDateTime.now();
	
	@Setter
	int likes;
	
	public Comment(String user, String message) {
		this.user = user;
		this.message = message;
		likes = 0;
	}
}

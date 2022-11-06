package telran.java2022.post.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class CommentDto {
	String user;
	String message;
	LocalDateTime dateCreated;
	Integer likes;
}

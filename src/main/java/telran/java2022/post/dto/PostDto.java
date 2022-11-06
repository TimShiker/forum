package telran.java2022.post.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostDto {
	String id;
	String title;
	String content;
	String author;
	LocalDateTime dateCreated;
	HashSet<String> tags;
	Integer likes;
	List<CommentDto> comments;
}

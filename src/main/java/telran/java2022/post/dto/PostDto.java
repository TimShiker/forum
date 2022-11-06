package telran.java2022.post.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostDto {
	String id;
	String title;
	String content;
	String author;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime dateCreated;
	@Singular
	HashSet<String> tags;
	Integer likes;
	@Singular
	List<CommentDto> comments;
}

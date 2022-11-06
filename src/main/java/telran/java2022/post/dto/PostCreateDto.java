package telran.java2022.post.dto;

import java.util.HashSet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostCreateDto {
	String title;
	String content;
	HashSet<String> tags;
}

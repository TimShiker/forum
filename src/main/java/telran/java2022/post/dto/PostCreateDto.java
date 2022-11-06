package telran.java2022.post.dto;

import java.util.HashSet;

import lombok.Getter;

@Getter
public class PostCreateDto {
	String title;
	String content;
	HashSet<String> tags;
}

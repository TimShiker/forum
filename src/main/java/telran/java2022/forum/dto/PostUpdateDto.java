package telran.java2022.forum.dto;

import java.util.HashSet;

import lombok.Getter;

@Getter
public class PostUpdateDto {
	String title;
	HashSet<String> tags;
}

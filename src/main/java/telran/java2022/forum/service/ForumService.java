package telran.java2022.forum.service;

import java.util.HashSet;
import java.util.List;

import telran.java2022.forum.dto.CommentDto;
import telran.java2022.forum.dto.PostCreateDto;
import telran.java2022.forum.dto.PostDto;
import telran.java2022.forum.dto.PostGetByPeriodDto;
import telran.java2022.forum.dto.PostUpdateDto;

public interface ForumService {
	
	PostDto addPost(String author, PostCreateDto postCreateDto);
	
	PostDto findPost(String id);
	
	void addLike(String id); 
	
	List<PostDto> getPostsByAuthor(String author);
	
	PostDto addComment(String id, String user, CommentDto commentDto);
	
	PostDto deletePost(String id);
	
	List<PostDto> getPostsByTags(HashSet<String> tags);
	
	List<PostDto> getPostsByPeriod(PostGetByPeriodDto postGetByPeriodDto);
	
	PostDto updatePost(String id, PostUpdateDto postUpdateDto);
}

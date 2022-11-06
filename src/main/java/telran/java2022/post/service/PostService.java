package telran.java2022.post.service;

import java.util.HashSet;
import java.util.List;

import telran.java2022.post.dto.CommentDto;
import telran.java2022.post.dto.PostCreateDto;
import telran.java2022.post.dto.PostDto;
import telran.java2022.post.dto.PostGetByPeriodDto;
import telran.java2022.post.dto.PostUpdateDto;

public interface PostService {
	
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

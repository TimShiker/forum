package telran.java2022.post.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java2022.post.dto.CommentDto;
import telran.java2022.post.dto.PostCreateDto;
import telran.java2022.post.dto.PostDto;
import telran.java2022.post.dto.PostGetByPeriodDto;
import telran.java2022.post.dto.PostUpdateDto;
import telran.java2022.post.service.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class PostController {
	
	final PostService forumService;
	
	@PostMapping("/post/{author}")
	public PostDto addPost(@PathVariable String author, @RequestBody PostCreateDto postCreateDto) {
		return forumService.addPost(author, postCreateDto);
	}
	
	@GetMapping("/post/{id}")
	public PostDto findPost(@PathVariable String id) {
		return forumService.findPost(id);
	}
	
	@PutMapping("/post/{id}/like")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addLike(@PathVariable String id) {
		forumService.addLike(id);
	}
	
	@GetMapping("/posts/author/{author}")
	public List<PostDto> findPostsByAuthor(@PathVariable String author) {
		return forumService.getPostsByAuthor(author);
	}
	
	@PutMapping("/post/{id}/comment/{user}")
	public PostDto addComment(@PathVariable String id, @PathVariable String user, @RequestBody CommentDto commentDto) {
		return forumService.addComment(id, user, commentDto);
	}
	
	@DeleteMapping("/post/{id}")
	public PostDto deletePost(@PathVariable String id) {
		return forumService.deletePost(id);
	}
	
	@PostMapping("/posts/tags")
	public List<PostDto> findPostByTags(@RequestBody HashSet<String> tags) {
		return forumService.getPostsByTags(tags);
	}
	
	@PostMapping("/posts/period")
	public List<PostDto> findPostByPeriod(@RequestBody PostGetByPeriodDto postGetByPeriodDto) {
		return forumService.getPostsByPeriod(postGetByPeriodDto);
	}
	
	@PutMapping("/post/{id}")
	public PostDto updatePost(@PathVariable String id, @RequestBody PostUpdateDto postUpdateDto) {
		return forumService.updatePost(id, postUpdateDto);
	}
}

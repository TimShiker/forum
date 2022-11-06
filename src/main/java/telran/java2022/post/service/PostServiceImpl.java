package telran.java2022.post.service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java2022.post.dao.PostRepository;
import telran.java2022.post.dto.CommentDto;
import telran.java2022.post.dto.PostCreateDto;
import telran.java2022.post.dto.PostDto;
import telran.java2022.post.dto.PostGetByPeriodDto;
import telran.java2022.post.dto.PostUpdateDto;
import telran.java2022.post.dto.exceptions.PostNotFoundException;
import telran.java2022.post.model.Comment;
import telran.java2022.post.model.Post;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
	
	final PostRepository forumRepository;
	final ModelMapper modelMapper;

	@Override
	public PostDto addPost(String author, PostCreateDto postCreateDto) {
		Post post = new Post(author, postCreateDto.getTitle(), postCreateDto.getContent(), postCreateDto.getTags());
		forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto findPost(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public void addLike(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		post.setLikes(post.getLikes() + 1);
		forumRepository.save(post);
	}

	@Override
	public List<PostDto> getPostsByAuthor(String author) {
		return forumRepository.findByAuthorIgnoreCase(author)
				.map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public PostDto addComment(String id, String user, CommentDto commentDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		Comment comment = new Comment(user, commentDto.getMessage());
		post.getComments().add(comment);
		forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto deletePost(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		forumRepository.delete(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByTags(HashSet<String> tags) {
		return forumRepository.findByTagsContainingIgnoreCase(tags)
				.map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<PostDto> getPostsByPeriod(PostGetByPeriodDto postGetByPeriodDto) {
		return forumRepository
				.findByDateCreatedBetween(postGetByPeriodDto.getDateFrom().atTime(23, 59, 59), postGetByPeriodDto.getDateTo().atTime(23, 59, 59))
				.map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public PostDto updatePost(String id, PostUpdateDto postUpdateDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		post.setTitle(postUpdateDto.getTitle());
		post.getTags().addAll(postUpdateDto.getTags());
		forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}
}

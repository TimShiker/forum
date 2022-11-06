package telran.java2022.post.dao;

import java.time.LocalDateTime;
import java.util.HashSet;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import telran.java2022.post.model.Post;

public interface ForumRepository extends CrudRepository<Post, String>{
	
	Stream<Post> findByAuthorIgnoreCase(String author);
	
	Stream<Post> findByTagsContainingIgnoreCase(HashSet<String> tags);
	
	@Query(value = "{'dateCreated':{ $gte: ?0, $lte: ?1}}")
	Stream<Post> findByDateCreatedBetween(LocalDateTime dateFrom, LocalDateTime dateTo);

}

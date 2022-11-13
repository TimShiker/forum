package telran.java2022.user.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "login")
@Document(collection = "users")
public class User {
	
	@Id
	String login;
	
	@Setter
	String firstName;
	
	@Setter
	String lastName;
	
	@Setter
    Set<String> roles = new HashSet<>(Arrays.asList("USER"));
	
	@Setter
	String password;
	
	public User(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	public void setRole(String role) {
		roles.add(role);
	}
}

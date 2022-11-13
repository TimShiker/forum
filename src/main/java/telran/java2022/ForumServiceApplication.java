package telran.java2022;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import telran.java2022.user.dao.UserRepository;
import telran.java2022.user.model.User;

@SpringBootApplication
public class ForumServiceApplication implements CommandLineRunner{
	
	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ForumServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(!userRepository.existsById("admin")) {
			String password = BCrypt.hashpw("admin", BCrypt.gensalt());
			User user = new User("admin", password);
			user.setRole("MODERATOR");
			user.setRole("ADMINISTRATOR");
			userRepository.save(user);
		}		
	}
}

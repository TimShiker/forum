package telran.java2022.user.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserWithSameLoginExistsException extends RuntimeException{

	private static final long serialVersionUID = 7894330585903838910L;
	
	public UserWithSameLoginExistsException(String login) {
		super("User with login " + login + " already exists");
	}
}

package telran.java2022.user.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PasswordNotCorrectException extends RuntimeException{

	private static final long serialVersionUID = -1537148069472827775L;

	public PasswordNotCorrectException(String password) {
		super("Entered password " + password + " is not correct");
	}
}

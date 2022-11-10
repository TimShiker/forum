package telran.java2022.user.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java2022.user.dto.CreateUserDto;
import telran.java2022.user.dto.LoginUserDto;
import telran.java2022.user.dto.RolesResponseDto;
import telran.java2022.user.dto.UpdateUserDto;
import telran.java2022.user.dto.UserDto;
import telran.java2022.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class UserController {
	
	final UserService userService;

	@PostMapping("/register")
	public UserDto registerUser(@RequestBody CreateUserDto userCreateDto) {
		return userService.registerUser(userCreateDto);
	}
	
	@PostMapping("/login")
	public UserDto loginUser(@RequestHeader("Authorization") String token) {
		String[] basicAuth = token.split(" ");
		String decode = new String(Base64.decodeBase64(basicAuth[1]));
		String[] credentials = decode.split(":");
		return userService.loginUser(credentials[0]);
	}
	
	@DeleteMapping("/user/{login}")
	public UserDto deleteUser(@PathVariable String login) {
		return userService.deleteUser(login);
	}
	
	@PutMapping("/user/{login}")
	public UserDto updateUser(@PathVariable String login, @RequestBody UpdateUserDto updateUserDto) {
		return userService.updateUser(login, updateUserDto);
	}
	
	@PutMapping("/user/{login}/role/{role}")
	public RolesResponseDto addRole(@PathVariable String login, @PathVariable String role) {
		return userService.addRole(login, role);
	}
	
	@DeleteMapping("/user/{login}/role/{role}")
	public UserDto deleteRole(@PathVariable String login, @PathVariable String role) {
		return userService.deleteRole(login, role);
	}
	
	@PutMapping("/password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changePassword(@RequestBody LoginUserDto loginUserDto) {
		userService.changePassword(loginUserDto);
	}
}

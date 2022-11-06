package telran.java2022.user.service;

import telran.java2022.user.dto.LoginUserDto;
import telran.java2022.user.dto.UpdateUserDto;
import telran.java2022.user.dto.CreateUserDto;
import telran.java2022.user.dto.UserDto;

public interface UserService {
	UserDto registerUser(CreateUserDto userCreateDto);
	
	UserDto loginUser(LoginUserDto loginUserDto);
	
	UserDto deleteUser(String login);
	
	UserDto updateUser(String login, UpdateUserDto updateUserDto);
	
	UserDto addRole(String login, String role);
	
	UserDto deleteRole(String login, String role);
	
	void changePassword(LoginUserDto loginUserDto);

}

package telran.java2022.user.service;

import telran.java2022.user.dto.RolesResponseDto;
import telran.java2022.user.dto.UpdateUserDto;
import telran.java2022.user.dto.CreateUserDto;
import telran.java2022.user.dto.UserDto;

public interface UserService {
	UserDto registerUser(CreateUserDto userCreateDto);
	
	UserDto loginUser(String login);
	
	UserDto deleteUser(String login);
	
	UserDto updateUser(String login, UpdateUserDto updateUserDto);
	
	RolesResponseDto addRole(String login, String role);
	
	UserDto deleteRole(String login, String role);
	
	void changePassword(String login, String newPassword);

}

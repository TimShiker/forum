package telran.java2022.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java2022.user.dao.UserRepository;
import telran.java2022.user.dto.LoginUserDto;
import telran.java2022.user.dto.UpdateUserDto;
import telran.java2022.user.dto.CreateUserDto;
import telran.java2022.user.dto.UserDto;
import telran.java2022.user.dto.exceptions.PasswordNotCorrectException;
import telran.java2022.user.dto.exceptions.UserNotFoundException;
import telran.java2022.user.dto.exceptions.UserWithSameLoginExistsException;
import telran.java2022.user.model.User;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	final ModelMapper modelMapper;
	final UserRepository userRepository;

	@Override
	public UserDto registerUser(CreateUserDto userCreateDto) {
		
		if(userRepository.findById(userCreateDto.getLogin()).isPresent()){
			throw new UserWithSameLoginExistsException(userCreateDto.getLogin());
		}
		
		User user = modelMapper.map(userCreateDto, User.class);
		userRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto loginUser(LoginUserDto loginUserDto) {
		User user = userRepository.findById(loginUserDto.getLogin())
				.orElseThrow(() -> new UserNotFoundException(loginUserDto.getLogin()));
		
		if(!user.getPassword().equals(loginUserDto.getPassword())) {
			throw new PasswordNotCorrectException(loginUserDto.getPassword());
		}
		
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto deleteUser(String login) {
		User user = userRepository.findById(login)
									.orElseThrow(() -> new UserNotFoundException(login));
		userRepository.delete(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto updateUser(String login, UpdateUserDto updateUserDto) {
		User user = userRepository.findById(login)
				.orElseThrow(() -> new UserNotFoundException(login));
		
		user.setFirstName(updateUserDto.getFirstName());
		user.setLastName(updateUserDto.getLastName());
		userRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto addRole(String login, String role) {
		User user = userRepository.findById(login)
				.orElseThrow(() -> new UserNotFoundException(login));
		
		user.getRoles().add(role);
		userRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto deleteRole(String login, String role) {
		User user = userRepository.findById(login)
				.orElseThrow(() -> new UserNotFoundException(login));
		
		user.getRoles().remove(role);
		userRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public void changePassword(LoginUserDto loginUserDto) {
		User user = userRepository.findById(loginUserDto.getLogin())
				.orElseThrow(() -> new UserNotFoundException(loginUserDto.getLogin()));
		
		user.setPassword(loginUserDto.getPassword());
		userRepository.save(user);
	}
}

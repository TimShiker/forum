package telran.java2022.user.service;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java2022.user.dao.UserRepository;
import telran.java2022.user.dto.LoginUserDto;
import telran.java2022.user.dto.RolesResponseDto;
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
		String password = BCrypt.hashpw(userCreateDto.getPassword(), BCrypt.gensalt());
		user.setPassword(password);
		userRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto loginUser(String login) {
		User user = userRepository.findById(login)
				.orElseThrow(() -> new UserNotFoundException(login));
		
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
		if(updateUserDto.getFirstName() != null) {
			user.setFirstName(updateUserDto.getFirstName());
		}
		if(updateUserDto.getLastName() != null) {
			user.setLastName(updateUserDto.getLastName());
		}
		
		userRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public RolesResponseDto addRole(String login, String role) {
		User user = userRepository.findById(login)
				.orElseThrow(() -> new UserNotFoundException(login));
		
		user.getRoles().add(role.toUpperCase());
		userRepository.save(user);
		return modelMapper.map(user, RolesResponseDto.class);
	}

	@Override
	public UserDto deleteRole(String login, String role) {
		User user = userRepository.findById(login)
				.orElseThrow(() -> new UserNotFoundException(login));
		
		user.getRoles().remove(role.toUpperCase());
		userRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public void changePassword(String login, String newPassword) {
		User user = userRepository.findById(login)
				.orElseThrow(() -> new UserNotFoundException(login));
		String password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
		user.setPassword(password);
		userRepository.save(user);
	}
}

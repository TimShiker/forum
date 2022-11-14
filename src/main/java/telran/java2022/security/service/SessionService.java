package telran.java2022.security.service;

import telran.java2022.user.model.User;

public interface SessionService {
	User addUser(String sessionId, User userAccount);
	
	User getUser(String sessionId);
	
	User removeUser(String sessionId);
}

package telran.java2022.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.java2022.user.dao.UserRepository;
import telran.java2022.user.dto.exceptions.UserNotFoundException;
import telran.java2022.user.model.User;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {
	
	final UserRepository userRepository;
	private User user;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse) res; 
		
		if(checkEndPoint(request.getMethod(), request.getServletPath())) {
			String token = request.getHeader("Authorization");
			if(token == null) {
				response.sendError(401, "Token not exist");
				return;
			}
			
			String[] credentials = getCredentialsFromToken(token);
			
			if(!isSuccessfulAuthentication(credentials)) {
				response.sendError(401, "Wrong login or password");
				return;
			}
			
			if(!isHasAdministratorRole() && isEndPointOnlyForAdministratorRole(request.getMethod(), request.getServletPath())
					|| isEndPointOnlyForUser(request)){
				response.sendError(403, "You don't have access");
				return;
			}
		}
		chain.doFilter(request, response);

	}

	private boolean isEndPointOnlyForUser(HttpServletRequest request) {
		String method = request.getMethod();
		String servletPath = request.getServletPath();
		String loginFromServletPath = servletPath.substring(servletPath.lastIndexOf("/") + 1);
		
		return "PUT".equalsIgnoreCase(method) 
				&& servletPath.contains("user") 
				&& !loginFromServletPath.equals(user.getLogin());
	}

	private boolean isEndPointOnlyForAdministratorRole(String method, String servletPath) {
		return ("PUT".equalsIgnoreCase(method) && servletPath.contains("role")) ||
				("DELETE".equalsIgnoreCase(method) && servletPath.contains("role"));
	}

	private boolean isHasAdministratorRole() {
		String roleAdministrator = "ADMINISTRATOR";
		return user != null && user.getRoles().contains(roleAdministrator);
	}

	private boolean isSuccessfulAuthentication(String[] credentials) {
		String loginFromRequest = credentials[0];
		String passwordFromRequest = credentials[1];
		
		this.user = userRepository.findById(loginFromRequest)
				.orElseThrow(() -> new UserNotFoundException(loginFromRequest));
		
		return user.getPassword().equals(passwordFromRequest);
	}

	private String[] getCredentialsFromToken(String token) {
		String[] basicAuth = token.split(" ");
		String decode = new String(Base64.decodeBase64(basicAuth[1]));
		return decode.split(":");
	}

	private boolean checkEndPoint(String method, String servletPath) {
		return !("POST".equalsIgnoreCase(method) && servletPath.equals("/account/register"));
	}
}

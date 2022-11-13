package telran.java2022.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.java2022.user.dao.UserRepository;
import telran.java2022.user.model.User;

@Component
@RequiredArgsConstructor
@Order(20)
public class OwnerFilter implements Filter {
	
	final UserRepository userRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		if (checkEndPoint(request.getServletPath())) {
			
			User user = userRepository
					.findById(request.getUserPrincipal().getName()).get();
			
			if(!isEqualsLogins(request.getServletPath(), user.getLogin()) 
					&& (isPostMethod(request.getMethod()) || isPutMethod(request.getMethod()))) {
				response.sendError(403);
				return;
			}
			else if (!isEqualsLogins(request.getServletPath(), user.getLogin())){
				request.setAttribute("ExecuteAdminFilter", true);
			}
		}
		
		chain.doFilter(request, response);
	}
	
	private boolean isPostMethod(String method) {
		return "POST".equals(method);
	}

	private boolean isPutMethod(String method) {
		return "PUT".equals(method);
	}

	private boolean checkEndPoint(String servletPath) {
		return (servletPath.matches("/account/user/\\w+/?"));
	}
	private boolean isEqualsLogins(String servletPath, String userLogin) {
		String loginByServletPath = servletPath.split("/")[3].trim();
		return loginByServletPath.equals(userLogin);
	}
}

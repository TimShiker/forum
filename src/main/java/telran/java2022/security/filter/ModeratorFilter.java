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
import telran.java2022.security.context.SecurityContext;
import telran.java2022.user.dao.UserRepository;
import telran.java2022.user.model.User;

@RequiredArgsConstructor
@Component
@Order(35)
public class ModeratorFilter implements Filter {

	final SecurityContext context;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		if(checkEndPoint(request.getMethod(), request.getServletPath()) 
				&& request.getAttribute("ExecuteModeratorFilter") != null){
			
			telran.java2022.security.context.User user = 
					context.getUser(request.getUserPrincipal().getName());
			
			if(!user.getRoles().contains("Moderator".toUpperCase())) {
				response.sendError(403);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String servletPath) {
		return ("DELETE".equalsIgnoreCase(method) && servletPath.matches("/forum/post/\\w+/?"));
	}
}

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
import telran.java2022.post.dao.PostRepository;
import telran.java2022.post.dto.exceptions.PostNotFoundException;
import telran.java2022.post.model.Post;
import telran.java2022.user.dao.UserRepository;
import telran.java2022.user.model.User;

@Component
@RequiredArgsConstructor
@Order(25)
public class OwnerPostFilter implements Filter {

	final PostRepository postRepository;
	final UserRepository userRepository;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		if (checkEndPoint(request.getServletPath())) {
			
			User user = userRepository
					.findById(request.getUserPrincipal().getName()).get();
			
			if(isPostMethod(request.getMethod())){
				String loginFromPathVariable = getPathVariableFromServletPath(request.getServletPath());
				
				if(!loginFromPathVariable.equals(user.getLogin())) {
					response.sendError(403);
					return;
				}				
			}
			else if(request.getServletPath().contains("comment")) {
				String authorOfComment = getAuthorComment(request.getServletPath());
				if(!authorOfComment.equals(user.getLogin())) {
					response.sendError(403);
					return;
				}
			}
			else {
				String idPost = getPathVariableFromServletPath(request.getServletPath());
				Post post = postRepository.findById(idPost).orElseThrow(() -> new PostNotFoundException(idPost));

				if(isDeleteMethod(request.getMethod()) && !post.getAuthor().equals(user.getLogin())) {
					request.setAttribute("ExecuteModeratorFilter", true);
				}
				else if(!post.getAuthor().equals(user.getLogin()) && isPutMethod(request.getMethod())) {
					response.sendError(403);
					return;
				}
			}
		}
		
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String servletPath) {
		return (servletPath.matches("/forum/post/\\w+/?") || servletPath.matches("/forum/post/\\w+/comment/\\w+/?"));
	}
	
	private boolean isPutMethod(String method) {
		return "PUT".equals(method);
	}
	
	private boolean isPostMethod(String method) {
		return "POST".equals(method);
	}
	
	private boolean isDeleteMethod(String method) {
		return "DELETE".equals(method);
	}
	
	private String getPathVariableFromServletPath(String servletPath) {
		return servletPath.split("/")[3].trim();
	}
	
	private String getAuthorComment(String servletPath) {
		return servletPath.split("/")[5].trim();
	}
}
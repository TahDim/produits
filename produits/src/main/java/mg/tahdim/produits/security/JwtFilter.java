package mg.tahdim.produits.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import mg.tahdim.produits.service.UserServiceImpl;

@Slf4j
@Service
public class JwtFilter extends OncePerRequestFilter {
	
	private HandlerExceptionResolver handlerExceptionResolver;
	private JwtService jwtService;
	private UserServiceImpl userServiceImpl;

	public JwtFilter(HandlerExceptionResolver handlerExceptionResolver, JwtService jwtService,
			UserServiceImpl userServiceImpl) {
		this.handlerExceptionResolver = handlerExceptionResolver;
		this.jwtService = jwtService;
		this.userServiceImpl = userServiceImpl;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {	
		
		String token = null;
		String username = null;
		boolean isTokenExpired = true;
		
		try {
			String authorization = request.getHeader("Authorization");
			if(authorization != null && authorization.startsWith(SecParams.PREFIX)) {
				token = authorization.substring(7);
				isTokenExpired = this.jwtService.isTokenExpired(token);
				username = this.jwtService.extractUsername(token);
			}
			
			if(!isTokenExpired && username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = this.userServiceImpl.loadUserByUsername(username);
				log.info("ito {}", userDetails.getAuthorities());
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			
			filterChain.doFilter(request, response);
			
		} catch (Exception e) {
			this.handlerExceptionResolver.resolveException(request, response, null, e);
		}
	}

}

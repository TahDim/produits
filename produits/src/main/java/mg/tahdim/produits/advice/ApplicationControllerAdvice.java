package mg.tahdim.produits.advice;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.util.Map;

import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class ApplicationControllerAdvice {
	
	@ResponseStatus(FORBIDDEN)
	@ExceptionHandler(value = AccessDeniedException.class)
	public @ResponseBody ProblemDetail accessDeniedException(AccessDeniedException exception) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(FORBIDDEN, "Vos droits ne vous permettent pas d'effectuer cette action");
		return problemDetail;
	}
	
	@ResponseStatus(UNAUTHORIZED)
	@ExceptionHandler(value = BadCredentialsException.class)
	public @ResponseBody ProblemDetail badCredentialsException(BadCredentialsException exception) {
		//log.error(exception.getMessage(), exception);
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(UNAUTHORIZED, "identifiants invalides");
		problemDetail.setProperty("erreur", "nous n'avons pas pu vous identifier");
		return problemDetail;
	}
	
	@ResponseStatus(UNAUTHORIZED)
	@ExceptionHandler(value = MalformedJwtException.class)
	public @ResponseBody ProblemDetail malformedJwtException(MalformedJwtException exception) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(UNAUTHORIZED, "Token mal formé");
		return problemDetail;
	}
	
	@ResponseStatus(UNAUTHORIZED)
	@ExceptionHandler(value = SignatureException.class)
	public @ResponseBody ProblemDetail signatureException(SignatureException exception) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(UNAUTHORIZED, "Token invalide");
		return problemDetail;
	}
	
	@ResponseStatus(UNAUTHORIZED)
	@ExceptionHandler(value = Exception.class)
	public Map<String, String> exceptionsHandler() {
		return Map.of("erreur", "description");
	}
	
}

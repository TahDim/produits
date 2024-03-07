package mg.tahdim.produits.security;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import mg.tahdim.produits.entities.User;
import mg.tahdim.produits.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;

@AllArgsConstructor
@Service
public class JwtService {
	private UserServiceImpl userServiceImpl;
	private HttpServletResponse response;
	
	public Map<String, String> generate(String username) {
		User user = (User) this.userServiceImpl.loadUserByUsername(username);
		return this.generatJwt(user);
	}

	public String extractUsername(String token) {
		return this.getClaim(token, Claims::getSubject);
	}

	public boolean isTokenExpired(String token) {
		Date expirationDate = this.getClaim(token, Claims::getExpiration);
		return expirationDate.before(new Date());
	}
	
	private <T> T getClaim(String token, Function<Claims, T> function) {
		Claims claims = getAllClaims(token);
		return function.apply(claims);
	}

	private Claims getAllClaims(String token) {
		return Jwts.parser()
				.verifyWith((SecretKey) getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	private Map<String, String> generatJwt(User user) {
		Map<String, Object> claims = Map.of(
				"role", user.getRoles(),
				Claims.EXPIRATION, SecParams.EXPIRATION_TIME,
				Claims.SUBJECT, user.getUsername()
		);
		
		String bearer = Jwts.builder()
			.issuedAt(new Date(SecParams.CURRENT_TIME))
			.expiration(new Date(SecParams.EXPIRATION_TIME))
			.subject(user.getUsername())
			.claims(claims)
			.signWith(getKey())
			.compact();
		
		//response.addHeader("Authorization", bearer);
		
		return Map.of("Authorization", bearer);
	}

	private Key getKey() {
		byte[] decodeer = Decoders.BASE64.decode(SecParams.ENCRIPTION_KEY);
		return Keys.hmacShaKeyFor(decodeer);
	}
}	

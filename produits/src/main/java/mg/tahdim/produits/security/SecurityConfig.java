package mg.tahdim.produits.security;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private JwtFilter jwtFilter;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private UserDetailsService userDetailsService;

	public SecurityConfig(JwtFilter jwtFilter, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) {
		this.jwtFilter = jwtFilter;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return
				http
					.csrf(AbstractHttpConfigurer::disable)
					.authorizeHttpRequests(
							authorize ->
								authorize
									.requestMatchers(POST, "/users/inscription", "/users/connexion", "/users/ajouterRole").permitAll()
									.requestMatchers(GET, "/role/liste").permitAll()
									// Consulter tous les produits
									.requestMatchers("/api/all/**").hasAnyAuthority("ADMIN", "USER")
									// Consulter tous les utilisateurs
									.requestMatchers(GET, "/users/all").hasAuthority("ADMIN")
									// Consulter un produit par son id
									.requestMatchers(GET, "/api/**").hasAnyAuthority("ADMIN", "USER")
									// Ajouter un nouveau produit et un role
									.requestMatchers(POST, "/api/**", "/role/add").hasAuthority("ADMIN")
									// Modifier un produit
									.requestMatchers(PUT, "/api/**").hasAuthority("ADMIN")
									// Supprimer un produit
									.requestMatchers(DELETE, "/api/**", "/users/**").hasAuthority("ADMIN")
									.anyRequest().authenticated()
					)
					.sessionManagement(httpSecuritySessionManagementConfigurer ->
						httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					)
					.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
					.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
		return daoAuthenticationProvider;
	}
}

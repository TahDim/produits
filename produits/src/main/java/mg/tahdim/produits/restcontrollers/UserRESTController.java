package mg.tahdim.produits.restcontrollers;

import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mg.tahdim.produits.dto.AddRoleDTO;
import mg.tahdim.produits.dto.AuthenticationDTO;
import mg.tahdim.produits.entities.User;
import mg.tahdim.produits.security.JwtService;
import mg.tahdim.produits.service.UserServiceImpl;

@Slf4j
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
@RestController
public class UserRESTController {
	private UserServiceImpl userServiceImpl;
	private AuthenticationManager authenticationManager;
	private JwtService jwtService;
	
	@PostMapping(path = "inscription")
	public void creer(@RequestBody User user) {
		this.userServiceImpl.saveUser(user);
		log.info("{} a été incrit", user.getUsername());
	}
	
	@PostMapping(path = "ajouterRole")
	public void ajouterRole(@RequestBody AddRoleDTO addRoleDTO) {
		this.userServiceImpl.addRoleToUser(addRoleDTO.username(), addRoleDTO.role());
		log.info("Role OK");
	}
	
	@PostMapping(path = "connexion")
	public Map<String, String> connexion(@RequestBody AuthenticationDTO authenticatDTO) {
		final Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticatDTO.username(), authenticatDTO.password())
		);
		log.info("Connexion state : {}", authenticate.isAuthenticated());
		if (authenticate.isAuthenticated()) {
			return this.jwtService.generate(authenticatDTO.username());
		}
		return null;
	}
	
	@GetMapping(path = "all")
	public List<User> getAllUsers() {
		return this.userServiceImpl.findAllUsers();
	}
	
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		this.userServiceImpl.deleteUser(id);
	}
}

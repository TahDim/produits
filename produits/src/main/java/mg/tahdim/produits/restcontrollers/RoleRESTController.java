package mg.tahdim.produits.restcontrollers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mg.tahdim.produits.entities.Role;
import mg.tahdim.produits.repos.RoleRepository;
import mg.tahdim.produits.service.UserServiceImpl;

@Slf4j
@AllArgsConstructor
@RequestMapping("/role")
@RestController
public class RoleRESTController {
	
	private UserServiceImpl userServiceImpl;
	private RoleRepository roleRepository;
	
	@PostMapping(path = "add")
	public void creerRole(@RequestBody Role role) {
		this.userServiceImpl.addRole(role);
		log.info("{} a été incrit", role.getRole());
	}
	
	@GetMapping(path = "liste")
	public List<Role> listeRole() {
		return this.roleRepository.findAll();
	}
}

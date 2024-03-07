package mg.tahdim.produits.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import mg.tahdim.produits.entities.Role;
import mg.tahdim.produits.entities.User;
import mg.tahdim.produits.repos.RoleRepository;
import mg.tahdim.produits.repos.UserRepository;

@AllArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User saveUser(User user) {
		Optional<User> ua = userRepository.findByUsername(user.getUsername());
		if (ua.isPresent()) {
			throw new RuntimeException("Votre nom est déjà utilisé");
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Role addRole(Role role) {
		if (roleRepository.findByRole(role.getRole()) != null) {
			throw new RuntimeException("Role existe déjà dans la base");
		}
		return roleRepository.save(role);
	}

	@Override
	public Optional<User> addRoleToUser(String username, String rolename) {
		Optional<User> u = userRepository.findByUsername(username);
		Role r = roleRepository.findByRole(rolename);
		
		u.get().getRoles().add(r);
		
		return u;
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository
				.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur ne correspond à cette identification"));
	}

	@Override
	public void deleteUser(Long id) {
		this.userRepository.deleteById(id);
	}

}

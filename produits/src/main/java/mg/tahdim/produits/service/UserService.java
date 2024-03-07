package mg.tahdim.produits.service;

import java.util.List;
import java.util.Optional;

import mg.tahdim.produits.entities.Role;
import mg.tahdim.produits.entities.User;

public interface UserService {
	User saveUser(User user);
	Optional<User> findByUsername(String username);
	Role addRole(Role role);
	Optional<User> addRoleToUser(String username, String rolename);
	List<User> findAllUsers();
	void deleteUser(Long id);
}

package mg.tahdim.produits.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.tahdim.produits.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByRole(String role);
}

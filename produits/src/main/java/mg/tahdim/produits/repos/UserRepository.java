package mg.tahdim.produits.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.tahdim.produits.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}

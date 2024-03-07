package mg.tahdim.produits.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import mg.tahdim.produits.entities.Categorie;

@RepositoryRestResource(path = "cat")
@CrossOrigin
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

}

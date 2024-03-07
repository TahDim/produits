package mg.tahdim.produits.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.tahdim.produits.entities.Produit;
import mg.tahdim.produits.service.ProduitService;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:4200")
public class ProduitRESTController {
	
	@Autowired
	private ProduitService produitService;
	
	@GetMapping(path = "all")
	public List<Produit> getAllProduits() {
		return produitService.getAllProduits();
	}
	
	@GetMapping(value = "/{id}")
	public Produit getProduitById(@PathVariable("id") Long id) {
		return produitService.getProduit(id);
	}
	
	@PostMapping
	public Produit createProduit(@RequestBody Produit produit) {
		return produitService.saveProduit(produit);
	}
	
	@PutMapping
	public Produit updateProduit(@RequestBody Produit produit) {
		return produitService.updateProduit(produit);
	}
	
	@DeleteMapping(value = "/{id}")
	public void deleteProduit(@PathVariable("id") Long id) {
		produitService.deleteProduitById(id);
	}
	
	@GetMapping(value = "/prodscat/{idCat}")
	public List<Produit> getProduitsByCatId(@PathVariable("idCat") Long idCat) {
		return produitService.findByCategorieIdCat(idCat);
	}
	
	@GetMapping(value = "/prodsByName/{nom}")
	public List<Produit> findByNomProduitContains(@PathVariable("nom") String nom) {
		return produitService.findByNomProduitContains(nom);
	}
}

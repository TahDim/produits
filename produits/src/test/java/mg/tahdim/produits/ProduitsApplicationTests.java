package mg.tahdim.produits;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mg.tahdim.produits.entities.Categorie;
import mg.tahdim.produits.entities.Produit;
import mg.tahdim.produits.repos.ProduitRepository;

@SpringBootTest
class ProduitsApplicationTests {

	@Autowired
	private ProduitRepository produitRepository;
	
	@Test
	public void testCreateProduit() {
		Produit prod = new Produit("Imprimante Canon",1100.0,new Date());
		produitRepository.save(prod);
	}
	
	@Test
	public void testFindProduit() {
		Produit p = produitRepository.findById(1L).get();
		System.out.println(p);
	}
	
	@Test
	public void testUpdateProduit() {
		Produit p = produitRepository.findById(1L).get();
		p.setPrixProduit(1000.0);
		produitRepository.save(p);
	}
	
	@Test
	public void testDeleteProduit() {
		produitRepository.deleteById(1L);
	}
	
	@Test
	public void testFindAllProduits() {
		List<Produit> prods = produitRepository.findAll();
		for (Produit p : prods) {
			System.out.println(p);
		}
	}
	
	@Test
	public void testFindProduitByNom() {
		List<Produit> listeProduit = produitRepository.findByNomProduit("PS4");
		for (Produit p : listeProduit) {
			System.out.println(p);
		}
	}
	
	@Test
	public void testFindProduitByNomContains() {
		List<Produit> listeProduit = produitRepository.findByNomProduitContains("p");
		for (Produit p : listeProduit) {
			System.out.println(p);
		}
	}
	
	@Test
	public void testFindProduitByNomPrix() {
		List<Produit> listeProduit = produitRepository.findByNomPrix("PS4", 1000.0);
		for (Produit p : listeProduit) {
			System.out.println(p);
		}
	}
	
	@Test
	public void testFindProduitByCategorie() {
		Categorie cat = new Categorie();
		cat.setIdCat(1L);
		
		List<Produit> listeProduit = produitRepository.findByCategorie(cat);
		for (Produit p : listeProduit) {
			System.out.println(p);
		}
	}
	
	@Test
	public void testFindByCategorie() {
		List<Produit> listeProduit = produitRepository.findByCategorieIdCat(1L);
		for (Produit p : listeProduit) {
			System.out.println(p);
		}
	}
	
	@Test
	public void testFindByOrderByNomProduitAsc() {
		List<Produit> listeProduit = produitRepository.findByOrderByNomProduitAsc();
		for (Produit p : listeProduit) {
			System.out.println(p);
		}
	}
	
	@Test
	public void testTrierProduitsNomsPrix() {
		List<Produit> listeProduit = produitRepository.trierProduitsNomsPrix();
		for (Produit p : listeProduit) {
			System.out.println(p);
		}
	}
}

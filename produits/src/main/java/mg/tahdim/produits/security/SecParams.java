package mg.tahdim.produits.security;

public interface SecParams {

	public static final String ENCRIPTION_KEY = "608f36e92dc66d97d5933f0e6371493tahdim4fc05b1aa8f8de64014732472303a7c";
	public static final String PREFIX = "Bearer ";
	public static final long CURRENT_TIME = System.currentTimeMillis();
	public static final long EXPIRATION_TIME = CURRENT_TIME + 10 * 60 *1000;
	
}

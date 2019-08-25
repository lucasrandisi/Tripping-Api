package Triping.utils;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashing {
	private static final int ITERATIONS = 65536;
	private static final int KEY_LENGTH = 512;							// Length of the resulting cryptographic key in bits
	private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
	private static final SecureRandom RAND = new SecureRandom();


	public static Optional<String> generateSalt (final int length) {
	    if (length < 1) {
	      System.err.println("error in generateSalt: length must be > 0");
	      return Optional.empty();
	    }
	
	   
	    // Salt generation
	    byte[] salt = new byte[length];
	    RAND.nextBytes(salt);
	
	    //Constructs a new String by using the ISO-8859-1 charset. 
	    return Optional.of(Base64.getEncoder().encodeToString(salt));
	  }
	
	public static Optional<String> hashPassword (String password, String salt) {
		char[] chars = password.toCharArray();
		byte[] bytes = salt.getBytes();
	
		// Key material 
	    PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);
	
	    // Overwrite password
	    Arrays.fill(chars, Character.MIN_VALUE);
	
		try {
			// Generate secure password
			SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
			byte[] securePassword = fac.generateSecret(spec).getEncoded();
			return Optional.of(Base64.getEncoder().encodeToString(securePassword));
	    } 
		catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
		    System.err.println("Exception encountered in hashPassword()");
		    return Optional.empty();
	    } 
		finally {
			spec.clearPassword();
	    }
	}	
	
	public static boolean verifyPassword (String password, String salt, String key) {
		Optional<String> optEncrypted = hashPassword(password, salt);
	    
		if (!optEncrypted.isPresent()) { 
			return false;
		}
	    
		return optEncrypted.get().equals(key);
	 }
}

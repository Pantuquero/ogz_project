package clases;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class is used to easily manipulate MD5 hash.
 * @author Pantuquero
 */
public class ConversorMD5 {
    
    /**
     * Returns the passed String cyphrated as MD5
     * @param entrada
     * @return String
     */
    public static String convertirMD5(String entrada){
        
        try {
            System.out.println("Cifrando contraseña...");
            
            MessageDigest digestor = MessageDigest.getInstance("MD5");
            
            digestor.update(entrada.getBytes());
            byte entrada_bytes[] = digestor.digest();
            
            StringBuffer buffer = new StringBuffer();
            for(int i = 0; i<entrada_bytes.length; i++){
                buffer.append(Integer.toString((entrada_bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            return buffer.toString();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        
        return "false";
    }    
}

package bo.gob.adsib.busa.cliente.modelos;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Clase para acceder a los objetos de un keystore p12
 * @author Eva Jimenez
 */
public class P12 {

    private static final String tipoKeyStore = "pkcs12";
    private String rutaKeyStore;
    private String contrasenaKeyStore;    
    private KeyStore ks;

    /**
     * Inicializa parametros para acceder al keystore p12
     * @param rutaKeyStore Ubicacion del archivo p12
     * @param contrasenaKeyStore Cotraseña del keystore     
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException 
     */
    public P12(String rutaKeyStore, String contrasenaKeyStore) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        this.rutaKeyStore = rutaKeyStore;
        this.contrasenaKeyStore = contrasenaKeyStore;        

        // Cargamos el almacen de claves
        ks = KeyStore.getInstance(tipoKeyStore);
        ks.load(new FileInputStream(rutaKeyStore), contrasenaKeyStore.toCharArray());
    }

    /**
     * Obtiene la clave privada del keystore p12
     * @param alias Alias de la clave privada
     * @param contrasenaClavePrivada Contraseña de la clave privada
     * @return Clave privada
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws UnrecoverableKeyException 
     */
    public PrivateKey getClavePrivada(String alias, String contrasenaClavePrivada) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        PrivateKey privateKey = (PrivateKey) ks.getKey(alias, contrasenaClavePrivada.toCharArray());
        return privateKey;
    }

    /**
     * Obtiene un certificado del keystore 
     * @param alias Alias del certificado
     * @return Certificado en formato X509
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws UnrecoverableKeyException 
     */
    public X509Certificate getCertificado(String alias) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
        return cert;
    }       
    

}

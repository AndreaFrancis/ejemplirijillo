package bo.gob.adsib.busa.cliente.test;

import bo.gob.adsib.busa.cliente.BancoUnionCliente;
import bo.gob.adsib.busa.cliente.modelos.Extracto;
import bo.gob.adsib.busa.cliente.modelos.P12;
import bo.gob.adsib.busa.cliente.modelos.Solicitud;
import bo.gob.adsib.busa.cliente.modelos.Movimiento;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Properties;
import org.apache.log4j.LogManager;

/**
 *
 * @author Eva Jimenez
 */
public class Test {

    private static final org.apache.log4j.Logger log = LogManager.getLogger(Test.class);

    public static String getProperty(String clave) {
        Properties properties = new Properties();
        String valor = "";
        try {
            InputStream inputStream = Test.class.getClassLoader().getResourceAsStream("configuracion.properties");
            properties.load(inputStream);
            valor = properties.getProperty(clave);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valor;
    }

    public static void main(String[] args) throws Exception {
        String rutaKeyStore = Test.getProperty("rutaKeyStore");
        String contrasenaKeyStore = Test.getProperty("contrasenaKeyStore");
        String contrasenaClavePrivada = Test.getProperty("contrasenaClavePrivada");
        String alias = Test.getProperty("alias");
        String numeroCuenta = Test.getProperty("numeroCuenta");
        String codUninet = Test.getProperty("codUninet");
        String ip = Test.getProperty("ip");
        String institucion = Test.getProperty("institucion");
        String certificadoBU = Test.getProperty("certificadoBU");
        String metodo = Test.getProperty("metodo");
        

        // Obteniendo Certificado y Clave para firmar
        P12 p12 = new P12(rutaKeyStore, contrasenaKeyStore);
        X509Certificate certificadoAdsib = p12.getCertificado(alias);
        PrivateKey claveAdsib = p12.getClavePrivada(alias, contrasenaClavePrivada);

        // Obteniendo Certificado para validar
        File certificateFile = new File(certificadoBU);
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        FileInputStream is = new FileInputStream(certificateFile);
        X509Certificate certificadoBusa = (X509Certificate) fact.generateCertificate(is);
        int cantidad = Integer.parseInt(Test.getProperty("rango"));
        
    
        // Configuración obligatoria
        Solicitud solicitud = new Solicitud(numeroCuenta, codUninet, ip, institucion, metodo, certificadoAdsib, claveAdsib, certificadoBusa);
        BancoUnionCliente.SOLICITUD = solicitud;
        
        // Consumo
        Extracto extracto = BancoUnionCliente.consultarExtractoBancario(cantidad);
        
        // Visualización del resultado
        log.info(extracto);
        if (extracto != null) {
            log.info(" ------------- Resultado " + extracto.getResultado());
            log.info(" ------------- Saldo inicial" + extracto.getSaldoInicial());
            log.info(" ------------- Total movimientos = " + extracto.getMovimientos().size());
            for (Movimiento m : extracto.getMovimientos()) {
                log.info("  !!------ FechaMovimiento = " + m.getFechaMovimiento());
                log.info("  !!------ FechaAdicion = " + m.getFechaAdicion());
                log.info("  !!------ NumDocumento = " + m.getNumDocumento());
                log.info("  !!------ Descripcion = " + m.getDescripcion());
                log.info("  !!------ TipoMovimiento = " + m.getTipoMovimiento());
                log.info("  !!------ Monto = " + m.getMonto());
                log.info("  !!------ NumMovtoDiario = " + m.getNumMovtoDiario());
                log.info("  !!------ FecLiteral = " + m.getFecLiteral());
                log.info("  !!------ Detalle = " + m.getDetalle());
                log.info("  !!------ ");
            }
            log.info("Fin de la consulta al servicio web");
        }

    }

}

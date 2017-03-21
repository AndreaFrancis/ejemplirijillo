/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.adsib.busa.proxy;

import bo.gob.adsib.busa.cliente.BancoUnionCliente;
import bo.gob.adsib.busa.cliente.modelos.Extracto;
import bo.gob.adsib.busa.cliente.modelos.P12;
import bo.gob.adsib.busa.cliente.modelos.Solicitud;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author jmedina
 */
@RestController
public class Controlador {

    private static final Logger log = LogManager.getLogger(Controlador.class);

    public static String getProperty(String clave) {
        Properties properties = new Properties();
        String valor = "";
        try {
            InputStream inputStream = Controlador.class.getClassLoader().getResourceAsStream("configuracion.properties");
            properties.load(inputStream);
            valor = properties.getProperty(clave);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valor;
    }
    
    public Controlador() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException {
        String rutaKeyStore = Controlador.getProperty("rutaKeyStore");
        String contrasenaKeyStore = Controlador.getProperty("contrasenaKeyStore");
        String contrasenaClavePrivada = Controlador.getProperty("contrasenaClavePrivada");
        String alias = Controlador.getProperty("alias");
        String numeroCuenta = Controlador.getProperty("numeroCuenta");
        String codUninet = Controlador.getProperty("codUninet");
        String ip = Controlador.getProperty("ip");
        String institucion = Controlador.getProperty("institucion");
        String certificadoBU = Controlador.getProperty("certificadoBU");
        String metodo = Controlador.getProperty("metodo");

        // Obteniendo Certificado y Clave para firmar
        P12 p12 = new P12(rutaKeyStore, contrasenaKeyStore);
        X509Certificate certificadoAdsib = p12.getCertificado(alias);
        PrivateKey claveAdsib = p12.getClavePrivada(alias, contrasenaClavePrivada);

        // Obteniendo Certificado para validar
        File certificateFile = new File(certificadoBU);
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        FileInputStream is = new FileInputStream(certificateFile);
        X509Certificate certificadoBusa = (X509Certificate) fact.generateCertificate(is);
        int cantidad = Integer.parseInt(Controlador.getProperty("rango"));

        // Configuración obligatoria
        Solicitud solicitud = new Solicitud(numeroCuenta, codUninet, ip, institucion, metodo, certificadoAdsib, claveAdsib, certificadoBusa);
        BancoUnionCliente.SOLICITUD = solicitud;

        // Visualización del resultado
        log.info("fin constructor");
    }
    
    /**
     * @api {get} /busa/v1/movimientos
     * @apiVersion 1.0.0
     * @apiGroup Movimientos
     * @apiName ObtenerUltimosMovimientos
     * @apiDescription Permite obtener el listado de los ultimos movimientos indicando la cantidad requerida.
     * @apiExample {js} Ejemplo de Consumo:
     * http://192.168.1.164/busa/v1/movimientos?cantidad=5
     * @apiSuccess {int} resultado Resultado de la consulta
     * @apiSuccess {BigDecimal} saldo Saldo inicial de la cuenta
     * @apiSuccess {Object[]} movimientos Lista de Movimientos
     * @apiSuccess {String} extracto.fechaMovimiento Fecha en la que se realizo en movimiento
     * @apiSuccess {String} extracto.fechaAdicion Fecha en la que se ralizo la adicion
     * @apiSuccess {int} extracto.numDocumento Numero de documento
     * @apiSuccess {String} extracto.descripcion Descripcion del movimiento
     * @apiSuccess {String} extracto.ipoMovimiento tipo de Movimiento
     * @apiSuccess {BigDecimal} extracto.monto Monto del movimiento
     * @apiSuccess {String} extracto.numMovtoDiario Numero de movimiento diario
     * @apiSuccess {String} extracto.fecLiteral Fecha literal
     * @apiSuccess {String} extracto.detalle Detalle extenso
     * @apiSuccessExample {json} Respuesta exitosa: HTTP/1.1 200 OK 
     * {
     *      "resultado":"0",
     *      "saldoInicial":7894.14,
     *      "movimientos":[
     *          {
     *              "fechaMovimiento": "30/11/2016",
     *              "fechaAdicion": "30/11/2016",
     *              "numDocumento": 61130,
     *              "descripcion": "N/D TRANSFERENCIA DE FONDOS A C.U.T.",
     *              "tipoMovimiento": "D",
     *              "monto": -3910,
     *              "numMovtoDiario": "1036886821",
     *              "fecLiteral": "30/11/2016 17:30:00",
     *              "detalle": ""
     *          },
     *          {
     *              "fechaMovimiento": "30/11/2016",
     *              "fechaAdicion": "30/11/2016",
     *              "numDocumento": 61130,
     *              "descripcion": "N/D TRANSFERENCIA DE FONDOS A C.U.T.",
     *              "tipoMovimiento": "D",
     *              "monto": -560,
     *              "numMovtoDiario": "1036904889",
     *              "fecLiteral": "30/11/2016 18:30:00",
     *              "detalle": "BANCO MERCANTIL;Cta. Origen: 4022417305;Originante: CORTES ARCE FRANCISCO;Monto: 280;Moneda: Bolivianos"
     *          }
     *      ]
     * }
     * @apiSampleRequest http://192.168.1.164:9000/busa/v1/movimientos?cantidad=3
     */
    @CrossOrigin
    @RequestMapping(value = "busa/v1/movimientos", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Object> movimientos(@RequestParam(value = "cantidad", required = true, defaultValue = "0") int cantidad) 
    {
        // Consumo
        Extracto extracto = null;
        try {
            // obtencion del Extracto en formato XML
            extracto = BancoUnionCliente.consultarExtractoBancario(cantidad);
            log.info(extracto);
            return new ResponseEntity<Object>(extracto, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}

package bo.gob.adsib.busa.cliente;

import bo.gob.adsib.busa.cliente.modelos.Extracto;
import bo.gob.adsib.busa.cliente.modelos.Solicitud;
import bo.gob.adsib.busa.cliente.firmador.FirmaDigital;
import java.io.StringReader;
import java.security.cert.X509Certificate;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.tempuri.CProxyIntercambioInformacion;
import org.tempuri.IProxyIntercambioInformacion;
import org.w3c.dom.Document;

/**
 *
 * @author Eva Jimenez
 */
public class BancoUnionCliente {

    private static final Logger log = LogManager.getLogger(BancoUnionCliente.class);

    public static Solicitud SOLICITUD;

    /**
     * Realiza el consumo del metodo WS_ULT_MOV_EXTRACTO del servicio web del
     * banco union, con los siguientes parametros
     * @param cantidad Cantidad de movimientos a consultar
     * @return Extracto Extracto contiene una lista de una cantidad movimientos 
     * @throws java.lang.Exception
     */
    public static Extracto consultarExtractoBancario(int cantidad) throws Exception {
       
        SOLICITUD.setCantidad(cantidad);
        String xmlConsulta = FirmaDigital.convertirObjetoXML(SOLICITUD, Solicitud.class);

        Document docConsulta = FirmaDigital.convertirStringADocument(xmlConsulta);
        Document docFirmado = FirmaDigital.firmarXML(docConsulta, SOLICITUD.getClaveAdsib());
        String cadenaFirmada = FirmaDigital.convertirDocumentAString(docFirmado);

        log.info("===========Solicitud============");
        log.info(cadenaFirmada);

        //Verificando firma del xml enviado
        boolean valido = FirmaDigital.validarFirmaXML(docFirmado, SOLICITUD.getCertificadoAdsib().getPublicKey(), false);

        //Consumo del servicio web del banco union
        CProxyIntercambioInformacion servicio = new CProxyIntercambioInformacion();
        IProxyIntercambioInformacion sp = servicio.getBasicHttpBindingIProxyIntercambioInformacion();

        String respuesta = sp.procesaPeticion(cadenaFirmada);

        log.info("===========Respuesta============");
        log.info(respuesta);

        boolean respuestaVerificada = validarRespuestaBU(respuesta, SOLICITUD.getCertificadoBusa());

        //if (respuestaVerificada) {
        JAXBContext jaxbContext = JAXBContext.newInstance(Extracto.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Extracto extracto = (Extracto) jaxbUnmarshaller.unmarshal(new StreamSource(new StringReader(respuesta)));
        return extracto;
        //}
        
    }

    /**
     * Valida la firma del xml de respuesta del banco union
     *
     * @param respuesta Cadena xml de respuesta
     * @param certificadoBU Ruta del certificado del banco union para validar
     * xml
     * @return
     * @throws java.lang.Exception
     */
    private static boolean validarRespuestaBU(String respuesta, X509Certificate cert) throws Exception {
       
        //Conversion a Document de la cadena de respuesta
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        //dbf.setAttribute("http://xml.org/sax/features/namespaces", Boolean.TRUE);
        Document resp = FirmaDigital.convertirStringADocument(respuesta);

        //Validacion de la firma digital del xml de respuesta
        boolean valido = FirmaDigital.validarFirmaXML(resp, cert.getPublicKey(), false);

        return valido;
    }
}

package bo.gob.adsib.busa.cliente.firmador;

import bo.gob.adsib.busa.cliente.BancoUnionCliente;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 *
 * @author Eva Jimenez
 */
public class FirmaDigital {
    
    private static final Logger log = LogManager.getLogger(FirmaDigital.class);

    
    /**
     * Firma digitalmente documentos XML. Aplica el tipo de firma Enveloped, sin agregar informacion del certificado digital.
     * @param doc Documento a firmar.
     * @param privateKey Clave privada con la que se firmara.
     * @return
     * @throws Exception 
     */
    public static Document firmarXML(Document doc, PrivateKey privateKey) throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        org.apache.xml.security.Init.init();

        Constants.setSignatureSpecNSprefix("");

        // Instanciamos un objeto XMLSignature desde el Document.
        XMLSignature xmlSignature = new XMLSignature(doc, null, XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA256);

        // Añadimos el nodo de la firma a la raiz antes de firmar.
        // Observe que ambos elementos pueden ser mezclados en una forma con referencias separadas      
        doc.getDocumentElement().appendChild(xmlSignature.getElement());

        // Creamos el objeto que mapea: Document/Reference
        Transforms transforms = new Transforms(doc);
        transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
        transforms.addTransform(Transforms.TRANSFORM_C14N_EXCL_OMIT_COMMENTS);

        // Añadimos lo anterior Documento / Referencia        
        xmlSignature.addDocument("", transforms, "http://www.w3.org/2001/04/xmlenc#sha256");

        // Realizamos la firma 
        xmlSignature.sign(privateKey);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLUtils.outputDOMc14nWithComments(doc, baos);

        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(baos.toString()));
        Document xml = db.parse(is);

        return xml;
    }

    /**
     * Valida la firma digital de un XML segun la clave publica
     * @param doc Documento 
     * @param pk Clave publica con la cual se validara la firma
     * @param conDs true, si el xml contiene el elemento ds:Signature, false si solo contiene el elemento Signature sin prefijo ds
     * @return
     * @throws Exception 
     */
    public static boolean validarFirmaXML(Document doc, PublicKey pk, boolean conDs) throws Exception {

        org.apache.xml.security.Init.init();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);

        Element sigElement;
        if (conDs) {
            sigElement = (Element) doc.getElementsByTagName("ds:Signature").item(0);
        } else {
            sigElement = (Element) doc.getElementsByTagName("Signature").item(0);
        }

        //doc.normalize();
        XMLSignature signature = new XMLSignature(sigElement, null);

        if (pk != null) {
            // Validamos usando la clave pública
            if (signature.checkSignatureValue(pk)) {
                log.info("Valido segun clave publica.");
                return true;
            } else {
                log.info("Invalido segun clave publica.");
            }
        } else {
            log.info("No se puede validar el xml la clave pública es nula.");
        }

        return false;
    }
    
        public static String convertirObjetoXML(Object objeto, Class clase) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(clase);
        StringWriter sw = new StringWriter();

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.marshal(objeto, sw);

        return sw.toString();
    }

    public static String convertirDocumentAStringUnaSolaLinea(Document doc) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
            return output;
        } catch (TransformerException ex) {
            java.util.logging.Logger.getLogger(BancoUnionCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Document convertirStringADocument(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }
    
    public static String convertirDocumentAString(Document doc) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            //transformer.setOutputProperty(OutputKeys.INDENT, "no");
            
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

}

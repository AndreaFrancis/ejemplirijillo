package bo.gob.adsib.busa.cliente.modelos;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * POJO para el manejo del objeto Solicitud
 * @author Eva Jimenez
 */
@XmlRootElement(name = "XML")
@XmlAccessorType(XmlAccessType.FIELD)
public class Solicitud {

    @XmlElement(name = "NumeroCuenta")
    String numeroCuenta;

    @XmlElement(name = "Cantidad")
    int cantidad;

    @XmlElement(name = "CodUninet")
    String codUninet;

    @XmlElement(name = "IP")
    String ip;

    @XmlElement(name = "Institucion")
    String institucion;

    @XmlElement(name = "CodEmpresa")
    String codEmpresa;

    @XmlElement(name = "IdDispositivo")
    String idDispositivo;

    @XmlElement(name = "Metodo")
    String metodo;

    @XmlTransient
    X509Certificate certificadoAdsib;

    @XmlTransient
    PrivateKey claveAdsib;
    
    @XmlTransient
    X509Certificate certificadoBusa;

    public Solicitud() {

    }

    /**
     * Version Actual, los parametros actuales
     *
     *
     */
    public Solicitud(String numeroCuenta, String codUninet, String ip, String institucion, String metodo, X509Certificate certificadoAdsib,PrivateKey claveAdsib, X509Certificate certificadoBusa) {
        this.numeroCuenta = numeroCuenta;
        this.codUninet = codUninet;
        this.ip = ip;
        this.institucion = institucion;
        this.metodo = metodo;
        this.certificadoAdsib = certificadoAdsib;
        this.claveAdsib = claveAdsib;
        this.certificadoBusa = certificadoBusa;
    }

    public Solicitud(String numeroCuenta, int cantidad, String codUninet, String ip, String codEmpresa, String idDispositivo, String metodo) {
        this.numeroCuenta = numeroCuenta;
        this.cantidad = cantidad;
        this.codUninet = codUninet;
        this.ip = ip;
        this.codEmpresa = codEmpresa;
        this.idDispositivo = idDispositivo;
        this.metodo = metodo;

    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodUninet() {
        return codUninet;
    }

    public void setCodUninet(String codUninet) {
        this.codUninet = codUninet;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public X509Certificate getCertificadoAdsib() {
        return certificadoAdsib;
    }

    public void setCertificadoAdsib(X509Certificate certificadoAdsib) {
        this.certificadoAdsib = certificadoAdsib;
    }

    public PrivateKey getClaveAdsib() {
        return claveAdsib;
    }

    public void setClaveAdsib(PrivateKey claveAdsib) {
        this.claveAdsib = claveAdsib;
    }

    public X509Certificate getCertificadoBusa() {
        return certificadoBusa;
    }

    public void setCertificadoBusa(X509Certificate certificadoBusa) {
        this.certificadoBusa = certificadoBusa;
    }

    
}

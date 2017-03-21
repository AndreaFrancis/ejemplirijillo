package bo.gob.adsib.busa.cliente.modelos;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * POJO para el manejo del objeto Movimiento
 * @author Eva Jimenez
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Movimiento {

    @XmlElement(name = "FechaMovimiento")
    String fechaMovimiento;

    @XmlElement(name = "FechaAdicion")
    String fechaAdicion;

    @XmlElement(name = "NumDocumento")
    int numDocumento; //no es ci, cod usuario movdiario unibanca

    @XmlElement(name = "Descripcion")
    String descripcion;

    @XmlElement(name = "TipoMovimiento")
    String tipoMovimiento;

    @XmlElement(name = "Monto")
    BigDecimal monto;

    @XmlElement(name = "NumMovtoDiario")
    String numMovtoDiario;

    @XmlElement(name = "FecLiteral")
    String fecLiteral;

    @XmlElement(name = "Detalle")
    String detalle; //varia dependiendo del origen del servicio, puede tener datos como banco orige, destino, etc

    /*             <Movimiento>
     *             <FechaMovimiento>15/05/2015</FechaMovimiento>
     *             <FechaAdicion>29/12/2015</FechaAdicion>
     *             <NumDocumento>54</NumDocumento>
     *             <Descripcion>N/D PAGO BBVA PREVISION AFP - JUBILACION</Descripcion>
     *             <TipoMovimiento>D</TipoMovimiento>
     *             <Monto>-799.11</Monto>
     *             <NumMovtoDiario>703639957</NumMovtoDiario>
     *             <FecLiteral>12/29/2015 18:57:56</FecLiteral><Detalle></Detalle>
     *             </Movimiento>
     */
    public Movimiento() {
    }

    public Movimiento(String fechaMovimiento, String fechaAdicion, int numDocumento, String descripcion, String tipoMovimiento, BigDecimal monto, String numMovtoDiario, String fecLiteral, String detalle) {
        this.fechaMovimiento = fechaMovimiento;
        this.fechaAdicion = fechaAdicion;
        this.numDocumento = numDocumento;
        this.descripcion = descripcion;
        this.tipoMovimiento = tipoMovimiento;
        this.monto = monto;
        this.numMovtoDiario = numMovtoDiario;
        this.fecLiteral = fecLiteral;
        this.detalle = detalle;
    }

    public String getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(String fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getFechaAdicion() {
        return fechaAdicion;
    }

    public void setFechaAdicion(String fechaAdicion) {
        this.fechaAdicion = fechaAdicion;
    }

    public int getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(int numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getNumMovtoDiario() {
        return numMovtoDiario;
    }

    public void setNumMovtoDiario(String numMovtoDiario) {
        this.numMovtoDiario = numMovtoDiario;
    }

    public String getFecLiteral() {
        return fecLiteral;
    }

    public void setFecLiteral(String fecLiteral) {
        this.fecLiteral = fecLiteral;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    
    /**
     * Retorna el movimiento en formato json
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Movimiento{" + "fechaMovimiento=" + fechaMovimiento + ", fechaAdicion=" + fechaAdicion + ", numDocumento=" + numDocumento + ", descripcion=" + descripcion + ", tipoMovimiento=" + tipoMovimiento + ", monto=" + monto + ", numMovtoDiario=" + numMovtoDiario + ", fecLiteral=" + fecLiteral + ", detalle=" + detalle + '}';
    }

}

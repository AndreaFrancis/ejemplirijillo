package bo.gob.adsib.busa.cliente.modelos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * POJO para el manejo del objeto Extracto
 * @author Eva Jimenez
 * 
 */
@XmlRootElement(name = "XtradoMovimientosUltimosCadena")
@XmlAccessorType(XmlAccessType.FIELD)
public class Extracto {

    @XmlElement(name = "Result")
    int resultado;

    @XmlElement(name = "saldoInicial")
    BigDecimal saldoInicial;

    @XmlElementWrapper(name = "Extracto")
    @XmlElement(name = "Movimiento")
    List<Movimiento> movimientos = new ArrayList<>();
    
    public Extracto() {
    }

    public Extracto(int resultado, BigDecimal saldoInicial, List<Movimiento> movimientos) {
        this.resultado = resultado;
        this.saldoInicial = saldoInicial;
        this.movimientos = movimientos;
    }
    
    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }
  
}

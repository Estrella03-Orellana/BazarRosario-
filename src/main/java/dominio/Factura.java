package dominio;

import java.util.Date;

public class Factura {
    private int codFac;
    private String nombreUsu;
    private double pago;
    private Date fecha;

    public Factura() {
    }

    public Factura(String nombreUsu, double pago, Date fecha) {
        this.nombreUsu = nombreUsu;
        this.pago = pago;
        this.fecha = fecha;
    }

    public int getCodFac() {
        return codFac;
    }

    public void setCodFac(int codFac) {
        this.codFac = codFac;
    }

    public String getNombreUsu() {
        return nombreUsu;
    }

    public void setNombreUsu(String nombreUsu) {
        this.nombreUsu = nombreUsu;
    }

    public double getPago() {
        return pago;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}

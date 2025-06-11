package dominio;

import java.sql.Timestamp;

public class Factura {
    private int codFac;
    private String nombreUsu;
    private double pago;
    private Timestamp fecha;

    public Factura() {
    }

    public Factura(String nombreUsu, double pago, Timestamp fecha) {
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

    public Timestamp getFecha() {
        return fecha;
    }


    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}

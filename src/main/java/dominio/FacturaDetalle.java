package dominio;

public class FacturaDetalle {
    private int id;
    private int codFac;
    private String nombreUsu;
    private String nombreProdu;
    private int cantidad;
    private double pago;

    public FacturaDetalle() {
    }

    public FacturaDetalle(int codFac, String nombreUsu, String nombreProdu, int cantidad, double pago) {
        this.codFac = codFac;
        this.nombreUsu = nombreUsu;
        this.nombreProdu = nombreProdu;
        this.cantidad = cantidad;
        this.pago = pago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNombreProdu() {
        return nombreProdu;
    }

    public void setNombreProdu(String nombreProdu) {
        this.nombreProdu = nombreProdu;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPago() {
        return pago;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }
}

package dominio;

public class Producto {
    private int id;
    private int categoriaId;
    private String tipoProdu;
    private String nombreProdu;
    private double precio;
    private int stock;
    private byte isOferta;

    public Producto() {
    }

    public Producto(int categoriaId, String tipoProdu, String nombreProdu, double precio, int stock, byte isOferta) {
        this.categoriaId = categoriaId;
        this.tipoProdu = tipoProdu;
        this.nombreProdu = nombreProdu;
        this.precio = precio;
        this.stock = stock;
        this.isOferta = isOferta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getTipoProdu() {
        return tipoProdu;
    }

    public void setTipoProdu(String tipoProdu) {
        this.tipoProdu = tipoProdu;
    }

    public String getNombreProdu() {
        return nombreProdu;
    }

    public void setNombreProdu(String nombreProdu) {
        this.nombreProdu = nombreProdu;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public byte getIsOferta() {
        return isOferta;
    }

    public void setIsOferta(byte isOferta) {
        this.isOferta = isOferta;
    }
}

package modelo;

public class Producto {
    private int codigoProducto;
    private String nombre;
    private int stock;
    private int stockMinimo;
    private Ubicacion ubicacion;

    public Producto(int codigoProducto, String nombre, int stock, int stockMinimo, Ubicacion ubicacion) {
        this.codigoProducto = codigoProducto;
        this.nombre = nombre;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.ubicacion = ubicacion;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "codigo=" + codigoProducto +
                ", nombre='" + nombre + '\'' +
                ", stock=" + stock +
                ", stockMinimo=" + stockMinimo +
                ", ubicacion=" + ubicacion +
                '}';
    }
}

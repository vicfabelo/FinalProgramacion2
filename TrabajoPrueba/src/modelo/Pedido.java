package modelo;

public class Pedido {
    private int idPedido;
    private int codigoProducto;
    private int cantidadSolicitada;
    private EstadoPedido estado;

    public Pedido(int idPedido, int codigoProducto, int cantidadSolicitada, EstadoPedido estado) {
        this.idPedido = idPedido;
        this.codigoProducto = codigoProducto;
        this.cantidadSolicitada = cantidadSolicitada;
        this.estado = estado;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(int cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", codigoProducto=" + codigoProducto +
                ", cantidadSolicitada=" + cantidadSolicitada +
                ", estado=" + estado +
                '}';
    }
}

package sistema;

import modelo.EstadoPedido;
import modelo.Observacion;
import modelo.Pedido;
import modelo.Producto;
import tdas.implementaciones.*;
import tdas.interfaces.*;
import tdas.nodos.NodoArbolProducto;

public class SistemaLogistico {

    private IArbolProductos productos;
    private IColaPedidos pedidos;
    private IPilaObservaciones observaciones;
    private IGrafoDeposito deposito;
    private IListaDoble<Observacion> historialObservaciones;


    public SistemaLogistico() {
        productos = new ArbolProductos();
        pedidos = new ColaPedidos();
        observaciones = new PilaObservaciones();
        historialObservaciones = new ListaDoble<>();
        deposito = new GrafoDeposito();
    }
    // Auditoria ─────────────────────────────────────────────────────────────
    private void registrarAuditoria(String descripcion){

        Observacion observacion =
                new Observacion(descripcion);

        observaciones.apilarObservacion(observacion);

        historialObservaciones.agregar(observacion);
    }

    public IListaDoble<Observacion> obtenerHistorialObservaciones() {

        return historialObservaciones;
    }
    // ── Productos ─────────────────────────────────────────────────────────────

    public boolean agregarProducto(Producto producto) {
        if (productos.existeProducto(producto.getCodigoProducto())) {
            return false;
        }
        productos.insertarProducto(producto.getCodigoProducto(), producto);
        registrarAuditoria(
                "Se agregó el producto '" +
                        producto.getNombre() +
                        "' (Código " +
                        producto.getCodigoProducto() +
                        ").");
        return true;
    }

    public Producto buscarProducto(int codigo) {
        NodoArbolProducto nodo = productos.buscarProducto(codigo);
        if (nodo == null) {
            return null;
        }
        return nodo.getProducto();
    }

    public boolean eliminarProducto(int codigo) {
        if (!productos.existeProducto(codigo)) {
            return false;
        }
        productos.eliminarProducto(codigo);
        registrarAuditoria(
                "Se eliminó el producto con código "
                        + codigo + ".");
        return true;
    }

    public boolean modificarStock(int codigo, int nuevoStock) {
        Producto p = buscarProducto(codigo);
        if (p == null) {
            return false;
        }
        p.setStock(nuevoStock);
        registrarAuditoria(
                "Se modificó el stock del producto "
                        + codigo +
                        ". Nuevo stock: "
                        + nuevoStock + ".");

        return true;
    }

    public int cantidadProductos() {
        return productos.tamanio();
    }

    public IListaDoble<Producto> obtenerProductosStockCritico() {
        NodoArbolProducto raiz = ((ArbolProductos) productos).getRaiz();

        IListaDoble<Producto> criticos = new ListaDoble<>();
        llenarCriticos(raiz, criticos);

        return criticos;
    }

    private void llenarCriticos(NodoArbolProducto nodo, IListaDoble<Producto> criticos) {
        if (nodo == null) {
            return;
        }
        llenarCriticos(nodo.getHijoIzq(), criticos);
        if (nodo.getProducto().getStock() <= nodo.getProducto().getStockMinimo()) {
            criticos.agregar(nodo.getProducto());
        }
        llenarCriticos(nodo.getHijoDer(), criticos);
    }

// ── Observaciones ─────────────────────────────────────────────────────────


    public Observacion verUltimaObservacion() {
        return observaciones.topeObservacion();
    }

    public void eliminarUltimaObservacion() {

        Observacion observacion =
                observaciones.desapilarObservacion();

        if (observacion == null) {
            System.out.println("No hay observaciones registradas.");
            return;
        }

        System.out.println("Se eliminó la última observación.");
    }

    public int cantidadObservaciones() {
        return observaciones.tamanio();
    }
    // ── Pedidos ───────────────────────────────────────────────────────────────

    public void agregarPedido(Pedido pedido) {
        pedidos.encolarPedido(pedido);
        registrarAuditoria(
                "Se registró el pedido #" +
                        pedido.getIdPedido() +
                        " del producto " +
                        pedido.getCodigoProducto() + ".");
    }

    public Pedido buscarPedido(int idPedido) {
        return pedidos.buscarPedido(idPedido);
    }

    public boolean cambiarEstadoPedido(int idPedido,
                                       EstadoPedido nuevoEstado) {
        Pedido p = pedidos.buscarPedido(idPedido);

        if (p == null) {
            return false;
        }

        if (nuevoEstado == EstadoPedido.DESPACHADO) {

            System.out.println("El estado DESPACHADO solo puede asignarse desde la opción 'Despachar pedido'.");

            return false;
        }

        p.setEstado(nuevoEstado);
        registrarAuditoria(
                "El pedido #" +
                        idPedido +
                        " cambió al estado " +
                        nuevoEstado + ".");

        return true;
    }

    public Pedido despacharPedido() {

        Pedido p = pedidos.verFrente();

        if (p == null) {
            return null;
        }

        // Solo se despachan pedidos que estén listos
        if (p.getEstado() != EstadoPedido.LISTO_PARA_DESPACHAR) {
            System.out.println("El pedido debe estar en estado LISTO para poder despacharse.");
            return p;
        }

        Producto prod = buscarProducto(p.getCodigoProducto());

        if (prod == null) {
            System.out.println("El producto asociado al pedido ya no existe.");
            pedidos.desencolarPedido();
            return p;
        }

        if (prod.getStock() < p.getCantidadSolicitada()) {
            return p;
        }

        // Despacho
        pedidos.desencolarPedido();

        prod.setStock(
                prod.getStock() - p.getCantidadSolicitada());

        p.setEstado(EstadoPedido.DESPACHADO);
        registrarAuditoria(
                "Se despachó el pedido #" +
                        p.getIdPedido() +
                        ". Stock restante: " +
                        prod.getStock() + ".");

        if (prod.getStock() < prod.getStockMinimo()) {

            System.out.println("\n[ALERTA] El producto '" +
                    prod.getNombre() +
                    "' quedó por debajo del stock mínimo.");
        }

        System.out.println("Pedido despachado correctamente.");

        return p;
    }

    public void saltarSiguientePedido() {
        pedidos.desencolarPedido();
    }

    public Pedido verSiguientePedido() {
        return pedidos.verFrente();
    }

    public void mostrarPedidos() {
        pedidos.mostrarPedidos();
    }

    public boolean hayPedidos() {
        return !pedidos.estaVacia();
    }

    public int contarPedidosDeProducto(int codigoProducto) {
        int contador = 0;
        IColaPedidos aux = new ColaPedidos();

        while (!pedidos.estaVacia()) {
            Pedido p = pedidos.desencolarPedido();
            if (p.getCodigoProducto() == codigoProducto) {
                contador++;
            }
            aux.encolarPedido(p);
        }

        while (!aux.estaVacia()) {
            pedidos.encolarPedido(aux.desencolarPedido());
        }

        return contador;
    }

    public void eliminarPedidosDeProducto(int codigoProducto) {
        IColaPedidos aux = new ColaPedidos();

        while (!pedidos.estaVacia()) {
            Pedido p = pedidos.desencolarPedido();
            if (p.getCodigoProducto() != codigoProducto) {
                aux.encolarPedido(p);
            }
        }

        while (!aux.estaVacia()) {
            pedidos.encolarPedido(aux.desencolarPedido());
        }
    }

    // ── Deposito ──────────────────────────────────────────────────────────────

    public void agregarPasillo(String codigoPasillo) {
        deposito.insertarPasillo(codigoPasillo);
    }

    public boolean eliminarPasillo(String codigoPasillo) {
        if (!deposito.existePasillo(codigoPasillo)) {
            return false;
        }
        deposito.eliminarPasillo(codigoPasillo);
        return true;
    }

    public boolean conectarPasillos(String origen, String destino) {
        if (!deposito.existePasillo(origen) || !deposito.existePasillo(destino)) {
            return false;
        }
        deposito.insertarConexion(origen, destino);
        return true;
    }

    public boolean desconectarPasillos(String origen, String destino) {
        if (!deposito.existeConexion(origen, destino)) {
            return false;
        }
        deposito.eliminarConexion(origen, destino);
        return true;
    }

    public void mostrarMapaDeposito() {
        deposito.mostrarMapaDeposito();
    }

    public boolean existePasillo(String codigoPasillo) {
        return deposito.existePasillo(codigoPasillo);
    }

    public void recorridoDesde(String inicio, boolean usarBFS) {
        if (usarBFS) {
            deposito.recorridoBFS(inicio);
        } else {
            deposito.recorridoDFS(inicio);
        }
    }
}

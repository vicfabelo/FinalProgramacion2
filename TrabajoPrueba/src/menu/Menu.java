package menu;

import modelo.*;
import modelo.Observacion;
import sistema.SistemaLogistico;
import tdas.interfaces.IListaDoble;

import java.util.Scanner;

public class Menu {

    private SistemaLogistico sistema;
    private Scanner scanner;

    public Menu() {
        this.sistema = new SistemaLogistico();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerOpcion();
            switch (opcion) {
                case 1 -> menuProductos();
                case 2 -> menuObservaciones();
                case 3 -> menuPedidos();
                case 4 -> menuDeposito();
                case 0 -> System.out.println("Hasta luego.");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    // ── Menú principal ────────────────────────────────────────────────────────

    private void mostrarMenuPrincipal() {
        System.out.println("\n================================");
        System.out.println("       SISTEMA LOGISTICO        ");
        System.out.println("================================");
        System.out.println(" 1. Gestion de Productos");
        System.out.println(" 2. Auditoria");
        System.out.println(" 3. Gestion de Pedidos");
        System.out.println(" 4. Gestion de Deposito");
        System.out.println(" 0. Salir");
        System.out.println("================================");
        System.out.print("Opcion: ");
    }

    // ── Productos ─────────────────────────────────────────────────────────────

    private void menuProductos() {
        int opcion;
        do {
            System.out.println("\n--- PRODUCTOS ---");
            System.out.println("1. Agregar producto");
            System.out.println("2. Buscar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Modificar stock");
            System.out.println("5. Mostrar cantidad de productos");
            System.out.println("6. Mostrar productos con stock critico");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = leerOpcion();
            switch (opcion) {
                case 1 -> agregarProducto();
                case 2 -> buscarProducto();
                case 3 -> eliminarProducto();
                case 4 -> modificarStock();
                case 5 -> System.out.println("Cantidad de productos: " + sistema.cantidadProductos());
                case 6 -> mostrarStockCritico();
                case 0 -> {}
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void agregarProducto() {
        System.out.print("Codigo del producto: ");
        int codigo = leerIntValido("Codigo del producto", 1);
        if (!entradaValida(codigo)) return;

        if (sistema.buscarProducto(codigo) != null) {
            System.out.println("Error: ya existe un producto con ese codigo.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = leerStringSoloLetras("Nombre");
        if (!entradaValida(nombre)) return;

        System.out.print("Stock inicial: ");
        int stock = leerIntValido("Stock inicial", 0);
        if (!entradaValida(stock)) return;

        System.out.print("Stock minimo: ");
        int stockMinimo = leerIntValido("Stock minimo", 1);
        if (!entradaValida(stockMinimo)) return;

        System.out.print("Sector: ");
        String sector = leerStringSoloLetras("Sector");
        if (!entradaValida(sector)) return;

        System.out.print("Estanteria: ");
        int estanteria = leerIntValido("Estanteria", 0);
        if (!entradaValida(estanteria)) return;

        System.out.print("Nivel: ");
        int nivel = leerIntValido("Nivel", 0);
        if (!entradaValida(nivel)) return;

        Ubicacion ubicacion = new Ubicacion(sector, estanteria, nivel);
        Producto producto = new Producto(codigo, nombre, stock, stockMinimo, ubicacion);

        sistema.agregarProducto(producto);
        System.out.println("Producto agregado.");
    }

    private void buscarProducto() {
        System.out.print("Codigo del producto: ");
        int codigo = leerIntValido("Codigo del producto", 1);
        if (!entradaValida(codigo)) return;

        Producto p = sistema.buscarProducto(codigo);
        if (p == null)
            System.out.println("No encontrado.");
        else
            mostrarProducto(p);
    }

    private void eliminarProducto() {
        System.out.print("Codigo del producto a eliminar: ");
        int codigo = leerIntValido("Codigo del producto", 1);
        if (!entradaValida(codigo)) return;

        Producto prod = sistema.buscarProducto(codigo);
        if (prod == null) {
            System.out.println("No existe un producto con ese codigo.");
            return;
        }

        int cantidadPedidos = sistema.contarPedidosDeProducto(codigo);

        if (cantidadPedidos > 0) {
            System.out.println("\n[ADVERTENCIA] Existen " + cantidadPedidos + " pedido(s) asociados al producto \"" + prod.getNombre() + "\".");
            System.out.print("¿Esta seguro de que desea continuar? Se cancelaran todos los pedidos relacionados (si/no): ");
            String confirmacion = scanner.nextLine().trim().toLowerCase();

            if (!confirmacion.equals("si")) {
                System.out.println("Operacion cancelada. El producto no fue eliminado.");
                return;
            }

            sistema.eliminarPedidosDeProducto(codigo);
            System.out.println("Pedidos asociados cancelados con exito.");
        }

        boolean ok = sistema.eliminarProducto(codigo);
        if (ok) {
            System.out.println("Producto eliminado del catalogo.");
        } else {
            System.out.println("No se pudo eliminar el producto.");
        }
    }

    private void modificarStock() {
        System.out.print("Codigo del producto: ");
        int codigo = leerIntValido("Codigo del producto", 1);
        if (!entradaValida(codigo)) return;

        Producto p = sistema.buscarProducto(codigo);
        if (p == null) {
            System.out.println("No existe un producto con ese codigo.");
            return;
        }
        System.out.println("Stock actual: " + p.getStock());
        System.out.print("Nuevo stock: ");
        int nuevoStock = leerIntValido("Nuevo stock", 0);
        if (!entradaValida(nuevoStock)) return;

        sistema.modificarStock(codigo, nuevoStock);
        System.out.println("Stock actualizado.");
    }

    private void mostrarProducto(Producto p) {
        System.out.println("  Codigo     : " + p.getCodigoProducto());
        System.out.println("  Nombre     : " + p.getNombre());
        System.out.println("  Stock      : " + p.getStock());
        System.out.println("  StockMinimo: " + p.getStockMinimo());
        System.out.println("  Ubicacion  : " + p.getUbicacion());
        System.out.println("  ---");
    }

    private void mostrarStockCritico() {
        IListaDoble<Producto> criticos = sistema.obtenerProductosStockCritico();
        if (criticos.estaVacia()) {
            System.out.println("No hay productos con stock critico.");
            return;
        }
        System.out.println("\n--- Productos con stock critico ---");
        for (int i = 0; i < criticos.tamanio(); i++) {
            mostrarProducto(criticos.obtener(i));
        }
    }

    // ── Movimientos ───────────────────────────────────────────────────────────

    private void menuObservaciones() {
        int opcion;
        do {
            System.out.println("\n--- OBSERVACIONES ---");
            System.out.println("1. Ver ultima observacion");
            System.out.println("2. Eliminar ultima observacion");
            System.out.println("3. Ver historial completo de observaciones");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");

            opcion = leerOpcion();

            switch (opcion) {
                case 1 -> verUltimaObservacion();
                case 2 -> eliminarUltimaObservacion();
                case 3 -> mostrarHistorialObservaciones();
                case 0 -> {}
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }


    private void verUltimaObservacion() {

        Observacion observacion = sistema.verUltimaObservacion();

        if (observacion == null) {
            System.out.println("No hay observaciones registradas.");
            return;
        }

        System.out.println(observacion);
    }

    private void eliminarUltimaObservacion() {

        if (sistema.cantidadObservaciones() == 0) {
            System.out.println("No hay observaciones registradas.");
            return;
        }

        sistema.eliminarUltimaObservacion();

        System.out.println("Ultima observacion eliminada.");
    }

    private void mostrarHistorialObservaciones() {

        IListaDoble<Observacion> historial =
                sistema.obtenerHistorialObservaciones();

        if (historial.estaVacia()) {

            System.out.println("No hay observaciones registradas.");

            return;
        }

        System.out.println("\n--- HISTORIAL DE OBSERVACIONES ---");

        for (int i = 0; i < historial.tamanio(); i++) {

            System.out.println("-----------------------------");
            System.out.println(historial.obtener(i));
        }
    }
    // ── Pedidos ───────────────────────────────────────────────────────────────

    private void menuPedidos() {
        int opcion;

        do {
            System.out.println("\n--- PEDIDOS ---");
            System.out.println("1. Agregar pedido");
            System.out.println("2. Despachar pedido");
            System.out.println("3. Ver siguiente pedido");
            System.out.println("4. Mostrar pedidos en cola");
            System.out.println("5. Cambiar estado de un pedido");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");

            opcion = leerOpcion();

            switch (opcion) {
                case 1 -> agregarPedido();
                case 2 -> despacharPedido();
                case 3 -> verSiguientePedido();
                case 4 -> sistema.mostrarPedidos();
                case 5 -> cambiarEstadoPedido();
                case 0 -> {}
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    private void agregarPedido() {

        System.out.print("ID del pedido: ");
        int idPedido = leerIntValido("ID del pedido", 1);
        if (!entradaValida(idPedido)) return;

        if (sistema.buscarPedido(idPedido) != null) {
            System.out.println("Error: ya existe un pedido con ese ID.");
            return;
        }

        System.out.print("Codigo del producto: ");
        int codigoProducto = leerIntValido("Codigo del producto", 1);
        if (!entradaValida(codigoProducto)) return;

        if (sistema.buscarProducto(codigoProducto) == null) {
            System.out.println("No existe un producto con ese codigo.");
            return;
        }

        System.out.print("Cantidad solicitada: ");
        int cantidad = leerIntValido("Cantidad solicitada", 1);
        if (!entradaValida(cantidad)) return;

        Pedido pedido = new Pedido(
                idPedido,
                codigoProducto,
                cantidad,
                EstadoPedido.PENDIENTE
        );

        sistema.agregarPedido(pedido);

        System.out.println("Pedido agregado correctamente.");
    }

    private void despacharPedido() {

        if (!sistema.hayPedidos()) {
            System.out.println("No hay pedidos en cola.");
            return;
        }

        while (sistema.hayPedidos()) {

            Pedido p = sistema.verSiguientePedido();

            Producto prod = sistema.buscarProducto(
                    p.getCodigoProducto());

            if (prod != null &&
                    prod.getStock() < p.getCantidadSolicitada()) {

                System.out.println("\nNo hay stock suficiente para el pedido.");
                System.out.println("Pedido: " + p.getIdPedido());
                System.out.println("Producto: " + prod.getNombre());
                System.out.println("Solicitado: " + p.getCantidadSolicitada());
                System.out.println("Disponible: " + prod.getStock());

                System.out.print("¿Desea descartar este pedido y pasar al siguiente? (si/no): ");

                String respuesta =
                        scanner.nextLine().trim().toLowerCase();

                if (respuesta.equals("si")) {

                    sistema.saltarSiguientePedido();

                    if (!sistema.hayPedidos()) {
                        System.out.println("No quedan pedidos en la cola.");
                    }

                } else {

                    System.out.println("Operacion cancelada.");
                    return;
                }

            } else {

                Pedido despachado =
                        sistema.despacharPedido();

                if (despachado != null) {
                    mostrarPedido(despachado);
                }

                return;
            }
        }
    }

    private void verSiguientePedido() {

        Pedido p = sistema.verSiguientePedido();

        if (p == null) {

            System.out.println("No hay pedidos en cola.");

        } else {

            mostrarPedido(p);
        }
    }

    private void mostrarPedido(Pedido p) {

        System.out.println("ID Pedido : " + p.getIdPedido());
        System.out.println("Producto  : " + p.getCodigoProducto());
        System.out.println("Cantidad  : " + p.getCantidadSolicitada());
        System.out.println("Estado    : " + p.getEstado());
    }

    private void cambiarEstadoPedido() {

        System.out.print("ID del pedido: ");
        int idPedido = leerIntValido("ID del pedido", 1);
        if (!entradaValida(idPedido)) return;

        System.out.print("Nuevo estado (PENDIENTE, EN_PREPARACION, LISTO_PARA_DESPACHAR): ");
        String entrada = scanner.nextLine().trim().toUpperCase();

        EstadoPedido nuevoEstado;
        try {
            nuevoEstado = EstadoPedido.valueOf(entrada);
        } catch (IllegalArgumentException e) {
            System.out.println("Estado invalido.");
            return;
        }

        boolean actualizado = sistema.cambiarEstadoPedido(idPedido, nuevoEstado);

        if (actualizado) {
            System.out.println("Estado actualizado correctamente.");
        }else {
            System.out.println("No se pudo actualizar el estado (verifique el ID o el estado solicitado).");
        }
    }

    // ── Deposito ──────────────────────────────────────────────────────────────

    private void menuDeposito() {
        int opcion;
        do {
            System.out.println("\n--- DEPOSITO ---");
            System.out.println("1. Agregar pasillo");
            System.out.println("2. Eliminar pasillo");
            System.out.println("3. Conectar pasillos");
            System.out.println("4. Desconectar pasillos");
            System.out.println("5. Mostrar mapa");
            System.out.println("6. Recorrido desde un pasillo (BFS)");
            System.out.println("7. Recorrido desde un pasillo (DFS)");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = leerOpcion();
            switch (opcion) {
                case 1 -> agregarPasillo();
                case 2 -> eliminarPasillo();
                case 3 -> conectarPasillos();
                case 4 -> desconectarPasillos();
                case 5 -> sistema.mostrarMapaDeposito();
                case 6 -> recorridoDeposito(true);
                case 7 -> recorridoDeposito(false);
                case 0 -> {}
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void agregarPasillo() {
        System.out.print("Codigo del pasillo (PX, reemplace X por el numero de pasillo que desee ingresar): ");
        String codigo = leerCodigoPasillo("Codigo del pasillo");
        if (!entradaValida(codigo)) return;

        sistema.agregarPasillo(codigo);
        System.out.println("Pasillo agregado.");
    }

    private void eliminarPasillo() {
        System.out.print("Codigo del pasillo a eliminar(PX, reemplace X por el numero de pasillo que desee eliminar): ");
        String codigo = leerCodigoPasillo("Codigo del pasillo");
        if (!entradaValida(codigo)) return;

        boolean ok = sistema.eliminarPasillo(codigo);
        if (ok) {
            System.out.println("Pasillo eliminado.");
        } else {
            System.out.println("No existe un pasillo con ese codigo.");
        }
    }

    private void conectarPasillos() {
        System.out.print("Pasillo origen: ");
        String origen = leerCodigoPasillo("Pasillo origen");
        if (!entradaValida(origen)) return;

        System.out.print("Pasillo destino: ");
        String destino = leerCodigoPasillo("Pasillo destino");
        if (!entradaValida(destino)) return;

        boolean ok = sistema.conectarPasillos(origen, destino);
        if (ok) {
            System.out.println("Conexion creada.");
        } else {
            System.out.println("No se pudo conectar (verificar que ambos pasillos existan).");
        }


    }

    private void desconectarPasillos() {
        System.out.print("Pasillo origen: ");
        String origen = leerCodigoPasillo("Pasillo origen");
        if (!entradaValida(origen)) return;

        System.out.print("Pasillo destino: ");
        String destino = leerCodigoPasillo("Pasillo destino");
        if (!entradaValida(destino)) return;

        boolean ok = sistema.desconectarPasillos(origen, destino);
        if (ok) {
            System.out.println("Conexion eliminada.");
        } else {
            System.out.println("No existe esa conexion.");
        }

    }

    private void recorridoDeposito(boolean usarBFS) {
        System.out.print("Pasillo de inicio:");
        String inicio = leerCodigoPasillo("Pasillo de inicio");
        if (!entradaValida(inicio)) return;

        if (!sistema.existePasillo(inicio)) {
            System.out.println("No existe un pasillo con ese codigo.");
            return;
        }
        sistema.recorridoDesde(inicio, usarBFS);
    }

    // ── Utilidades ────────────────────────────────────────────────────────────

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int leerIntValido(String etiqueta, int minimo) {
        String texto = scanner.nextLine().trim();
        int valor;
        try {
            valor = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + etiqueta + "\" debe ser un numero entero.");
            return Integer.MIN_VALUE;
        }
        if (valor < minimo) {
            System.out.println("Error: \"" + etiqueta + "\" debe ser mayor o igual a " + minimo + ".");
            return Integer.MIN_VALUE;
        }
        return valor;
    }

    private String leerStringValido(String etiqueta) {
        String texto = scanner.nextLine().trim();
        if (texto.isEmpty()) {
            System.out.println("Error: \"" + etiqueta + "\" no puede estar vacio.");
            return null;
        }
        return texto;
    }

    private String leerStringSoloLetras(String etiqueta) {
        String texto = scanner.nextLine().trim();
        if (texto.isEmpty()) {
            System.out.println("Error: \"" + etiqueta + "\" no puede estar vacio.");
            return null;
        }
        if (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.println("Error: \"" + etiqueta + "\" solo puede contener letras y espacios.");
            return null;
        }
        return texto;
    }

    private String leerCodigoPasillo(String etiqueta) {
        String texto = scanner.nextLine().trim();
        if (texto.isEmpty()) {
            System.out.println("Error: \"" + etiqueta + "\" no puede estar vacio.");
            return null;
        }
        if (!texto.matches("P[0-9]+")) {
            System.out.println("Error: \"" + etiqueta + "\" debe tener el formato de P seguida de numeros (ej: P1).");
            return null;
        }
        return texto;
    }

    private boolean entradaValida(int valor) {
        return valor != Integer.MIN_VALUE;
    }

    private boolean entradaValida(String valor) {
        return valor != null;
    }
}

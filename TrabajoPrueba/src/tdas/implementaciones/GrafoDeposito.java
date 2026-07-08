package tdas.implementaciones;

import tdas.interfaces.IGrafoDeposito;
import tdas.nodos.NodoAdyacente;
import tdas.nodos.NodoPasillo;

public class GrafoDeposito implements IGrafoDeposito {

    private NodoPasillo primero;

    public GrafoDeposito() {
        this.primero = null;
    }

    private NodoPasillo buscarPasillo(String codigoPasillo) {

        NodoPasillo aux = primero;

        while (aux != null) {

            if (aux.getCodigoPasillo().equalsIgnoreCase(codigoPasillo)) {
                return aux;
            }

            aux = aux.getSiguiente();
        }

        return null;
    }

    @Override
    public void insertarPasillo(String codigoPasillo) {

        if (existePasillo(codigoPasillo)) {
            return;
        }

        NodoPasillo nuevo = new NodoPasillo(codigoPasillo);

        if (primero == null) {
            primero = nuevo;
        } else {

            NodoPasillo aux = primero;

            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }

            aux.setSiguiente(nuevo);
        }
    }

    @Override
    public void eliminarPasillo(String codigoPasillo) {

        NodoPasillo actual = primero;
        NodoPasillo anterior = null;

        while (actual != null &&
                !actual.getCodigoPasillo().equalsIgnoreCase(codigoPasillo)) {

            anterior = actual;
            actual = actual.getSiguiente();
        }

        if (actual == null) {
            return;
        }

        NodoPasillo aux = primero;

        while (aux != null) {

            eliminarConexion(aux.getCodigoPasillo(),
                    codigoPasillo);

            aux = aux.getSiguiente();
        }

        if (anterior == null) {
            primero = actual.getSiguiente();
        } else {
            anterior.setSiguiente(actual.getSiguiente());
        }
    }

    @Override
    public void insertarConexion(String origen,
                                 String destino) {

        NodoPasillo vOrigen = buscarPasillo(origen);
        NodoPasillo vDestino = buscarPasillo(destino);

        if (vOrigen == null || vDestino == null) {
            return;
        }

        if (!existeConexion(origen, destino)) {

            NodoAdyacente nuevoDestino =
                    new NodoAdyacente(destino);

            nuevoDestino.setSiguiente(
                    vOrigen.getAdyacentes());

            vOrigen.setAdyacentes(
                    nuevoDestino);

            NodoAdyacente nuevoOrigen =
                    new NodoAdyacente(origen);

            nuevoOrigen.setSiguiente(
                    vDestino.getAdyacentes());

            vDestino.setAdyacentes(
                    nuevoOrigen);
        }
    }

    @Override
    public void eliminarConexion(String origen,
                                 String destino) {

        NodoPasillo vOrigen = buscarPasillo(origen);
        NodoPasillo vDestino = buscarPasillo(destino);

        if (vOrigen == null || vDestino == null) {
            return;
        }

        eliminarAdyacente(vOrigen, destino);
        eliminarAdyacente(vDestino, origen);
    }

    private void eliminarAdyacente(
            NodoPasillo vertice,
            String destino) {

        NodoAdyacente actual =
                vertice.getAdyacentes();

        NodoAdyacente anterior = null;

        while (actual != null &&
                !actual.getCodigoPasillo()
                        .equalsIgnoreCase(destino)) {

            anterior = actual;
            actual = actual.getSiguiente();
        }

        if (actual == null) {
            return;
        }

        if (anterior == null) {
            vertice.setAdyacentes(
                    actual.getSiguiente());
        } else {
            anterior.setSiguiente(
                    actual.getSiguiente());
        }
    }

    @Override
    public boolean existePasillo(
            String codigoPasillo) {

        return buscarPasillo(codigoPasillo) != null;
    }

    @Override
    public boolean existeConexion(
            String origen,
            String destino) {

        NodoPasillo vertice =
                buscarPasillo(origen);

        if (vertice == null) {
            return false;
        }

        NodoAdyacente aux =
                vertice.getAdyacentes();

        while (aux != null) {

            if (aux.getCodigoPasillo()
                    .equalsIgnoreCase(destino)) {

                return true;
            }

            aux = aux.getSiguiente();
        }

        return false;
    }

    @Override
    public void mostrarMapaDeposito() {

        NodoPasillo aux = primero;

        while (aux != null) {

            System.out.print(
                    aux.getCodigoPasillo() + " -> ");

            NodoAdyacente ady =
                    aux.getAdyacentes();

            while (ady != null) {

                System.out.print(
                        ady.getCodigoPasillo()
                                + " ");

                ady = ady.getSiguiente();
            }

            System.out.println();

            aux = aux.getSiguiente();
        }
    }

    private void marcarNoVisitados() {

        NodoPasillo aux = primero;

        while (aux != null) {

            aux.setVisitado(false);

            aux = aux.getSiguiente();
        }
    }

    @Override
    public void recorridoDFS(String inicio) {

        marcarNoVisitados();

        NodoPasillo origen =
                buscarPasillo(inicio);

        if (origen != null) {
            dfs(origen);
        }
    }

    private void dfs(NodoPasillo actual) {

        actual.setVisitado(true);

        System.out.println(
                actual.getCodigoPasillo());

        NodoAdyacente ady =
                actual.getAdyacentes();

        while (ady != null) {

            NodoPasillo vecino =
                    buscarPasillo(
                            ady.getCodigoPasillo());

            if (!vecino.isVisitado()) {

                dfs(vecino);
            }

            ady = ady.getSiguiente();
        }
    }

    @Override
    public void recorridoBFS(String inicio) {

        marcarNoVisitados();

        NodoPasillo origen =
                buscarPasillo(inicio);

        if (origen == null) {
            return;
        }

        ColaPasillos cola =
                new ColaPasillos();

        origen.setVisitado(true);

        cola.encolar(origen);

        while (!cola.estaVacia()) {

            NodoPasillo actual =
                    cola.desencolar();

            System.out.println(
                    actual.getCodigoPasillo());

            NodoAdyacente ady =
                    actual.getAdyacentes();

            while (ady != null) {

                NodoPasillo vecino =
                        buscarPasillo(
                                ady.getCodigoPasillo());

                if (!vecino.isVisitado()) {

                    vecino.setVisitado(true);

                    cola.encolar(vecino);
                }

                ady = ady.getSiguiente();
            }
        }
    }
}
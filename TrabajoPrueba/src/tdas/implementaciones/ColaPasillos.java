package tdas.implementaciones;

import tdas.nodos.NodoColaPasillo;
import tdas.nodos.NodoPasillo;

public class ColaPasillos {

    private NodoColaPasillo frente;
    private NodoColaPasillo fin;

    public ColaPasillos() {
        this.frente = null;
        this.fin = null;
    }

    public void encolar(NodoPasillo dato) {

        NodoColaPasillo nuevo =
                new NodoColaPasillo(dato);

        if (estaVacia()) {

            frente = nuevo;
            fin = nuevo;

        } else {

            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
    }

    public NodoPasillo desencolar() {

        if (estaVacia()) {
            return null;
        }

        NodoPasillo dato =
                frente.getDato();

        frente = frente.getSiguiente();

        if (frente == null) {
            fin = null;
        }

        return dato;
    }

    public boolean estaVacia() {
        return frente == null;
    }
}
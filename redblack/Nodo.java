package redblack;

public class Nodo {
    public static final boolean ROJO = true;
    public static final boolean NEGRO = false;

    int clave;
    boolean color;
    Nodo izquierda;
    Nodo derecha;
    Nodo padre;

    public Nodo(int clave) {
        this.clave = clave;
        this.color = ROJO;
        this.izquierda = null;
        this.derecha = null;
        this.padre = null;
    }
}

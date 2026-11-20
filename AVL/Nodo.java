package AVL;
public class Nodo {
    int clave;
    int altura;
    Nodo izquierda;
    Nodo derecha;

    public Nodo(int clave) {
        this.clave = clave;
        this.altura = 1;
        this.izquierda = null;
        this.derecha = null;
    }
}

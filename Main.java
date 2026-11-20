import AVL.*;
import redblack.*;
public class Main {
	public static void main(String[] args) {
		ArbolAVL arbol = new ArbolAVL();

		int[] valores = {30, 20, 40, 10, 25, 35, 50, 5};
		for (int valor : valores) {
			arbol.agregar(valor);
		}

		System.out.println("Árbol AVL:");
		arbol.mostrar();

		System.out.println("\n¿Existe 25?: " + arbol.buscar(25));
		System.out.println("¿Existe 99?: " + arbol.buscar(99));

		System.out.println("\nEliminando 30...");
		arbol.eliminar(30);
		arbol.mostrar();

		ArbolRedBlack arb = new ArbolRedBlack();

		int[] vals = {10, 20, 30, 15, 25, 5, 1};
		for (int valor : vals) {
			arb.agregar(valor);
		}

		System.out.println("Árbol Red-Black (R = rojo, N = negro):");
		arb.mostrar();

		System.out.println("\nEliminando 20...");
		arb.eliminar(20);
		arb.mostrar();

		System.out.println("\nEliminando 10...");
		arb.eliminar(10);
		arb.mostrar();
	}
}

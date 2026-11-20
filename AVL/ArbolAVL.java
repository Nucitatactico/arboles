/*
 *Este codigo fue creado basandose en el proporcionado por apache commons para el arbol
 * */
package AVL;
public class ArbolAVL {
	private Nodo raiz;

	public ArbolAVL() {
		this.raiz = null;
	}

	private int altura(Nodo nodo) {
		return (nodo == null) ? 0 : nodo.altura;
	}

	private int balance(Nodo nodo) {
		if (nodo == null) return 0;
		return altura(nodo.izquierda) - altura(nodo.derecha);
	}

	private void actualizarAltura(Nodo nodo) {
		nodo.altura = 1 + Math.max(altura(nodo.izquierda), altura(nodo.derecha));
	}

	private Nodo rotarDerecha(Nodo y) {
		Nodo x = y.izquierda;
		Nodo temp = x.derecha;
		x.derecha = y;
		y.izquierda = temp;
		actualizarAltura(y);
		actualizarAltura(x);
		return x;
	}

	private Nodo rotarIzquierda(Nodo x) {
		Nodo y = x.derecha;
		Nodo temp = y.izquierda;
		y.izquierda = x;
		x.derecha = temp;
		actualizarAltura(x);
		actualizarAltura(y);
		return y;
	}

	public void agregar(int clave) {
		raiz = agregar(raiz, clave);
	}
	private Nodo agregar(Nodo nodo, int clave) {
		if (nodo == null) {
			return new Nodo(clave);
		}

		if (clave < nodo.clave) {
			nodo.izquierda = agregar(nodo.izquierda, clave);
		} else if (clave > nodo.clave) {
			nodo.derecha = agregar(nodo.derecha, clave);
		} else {
			return nodo;	//lave duplicada asi que no se agrega
		}

		actualizarAltura(nodo);

		//rebalancear (Antonio perdoname esto se lo copie a apache)
		int balance = balance(nodo);

		// Caso Izquierda-Izquierda
		if (balance > 1 && clave < nodo.izquierda.clave) {
			return rotarDerecha(nodo);
		}
		// Caso Derecha-Derecha
		if (balance < -1 && clave > nodo.derecha.clave) {
			return rotarIzquierda(nodo);
		}
		// Caso Izquierda-Derecha
		if (balance > 1 && clave > nodo.izquierda.clave) {
			nodo.izquierda = rotarIzquierda(nodo.izquierda);
			return rotarDerecha(nodo);
		}
		// Caso Derecha-Izquierda
		if (balance < -1 && clave < nodo.derecha.clave) {
			nodo.derecha = rotarDerecha(nodo.derecha);
			return rotarIzquierda(nodo);
		}

		return nodo;
	}

	public boolean buscar(int clave) {
		return buscar(raiz, clave);
	}
	private boolean buscar(Nodo nodo, int clave) {
		if (nodo == null) return false;
		if (clave == nodo.clave) return true;
		if (clave < nodo.clave) return buscar(nodo.izquierda, clave);
		return buscar(nodo.derecha, clave);
	}

	public void eliminar(int clave) {
		raiz = eliminar(raiz, clave);
	}

	private Nodo eliminar(Nodo nodo, int clave) {
		if (nodo == null) return nodo;

		//bsqueda del nodo a eliminar
		if (clave < nodo.clave) {
			nodo.izquierda = eliminar(nodo.izquierda, clave);
		} else if (clave > nodo.clave) {
			nodo.derecha = eliminar(nodo.derecha, clave);
		} else {
			//aso con 0 o 1 hijo
			if (nodo.izquierda == null) {
				return nodo.derecha;
			} else if (nodo.derecha == null) {
				return nodo.izquierda;
			}
			//caso cn 2 hijos: mnimo del subárbol derecho
			Nodo temp = minimo(nodo.derecha);
			nodo.clave = temp.clave;
			nodo.derecha = eliminar(nodo.derecha, temp.clave);
		}

		//actualizar altura
		actualizarAltura(nodo);

		//rebalancear
		int balance = balance(nodo);

		if (balance > 1 && balance(nodo.izquierda) >= 0) {
			return rotarDerecha(nodo);
		}
		if (balance > 1 && balance(nodo.izquierda) < 0) {
			nodo.izquierda = rotarIzquierda(nodo.izquierda);
			return rotarDerecha(nodo);
		}
		if (balance < -1 && balance(nodo.derecha) <= 0) {
			return rotarIzquierda(nodo);
		}
		if (balance < -1 && balance(nodo.derecha) > 0) {
			nodo.derecha = rotarDerecha(nodo.derecha);
			return rotarIzquierda(nodo);
		}

		return nodo;
	}

	private Nodo minimo(Nodo nodo) {
		Nodo actual = nodo;
		while (actual.izquierda != null) {
			actual = actual.izquierda;
		}
		return actual;
	}

	public void mostrar() {
		if (raiz == null) {
			System.out.println("no hay nada");
			return;
		}
		mostrar(raiz, 0);
	}
	private void mostrar(Nodo nodo, int nivel) {
		if (nodo != null) {
			mostrar(nodo.derecha, nivel + 1);
			String sangria = "\t".repeat(nivel);
			System.out.printf("%s%d (h:%d|b:%d)\n",sangria,nodo.clave,nodo.altura,balance(nodo));
			mostrar(nodo.izquierda, nivel + 1);
		}
	}
}

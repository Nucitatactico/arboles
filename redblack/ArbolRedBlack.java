package redblack;

public class ArbolRedBlack {

	// NIL centinela: representa todas las hojas y el padre de la raíz.
	private final Nodo NIL;
	private Nodo raiz;

	public ArbolRedBlack() {
		NIL = new Nodo(0);
		NIL.color = Nodo.NEGRO;
		NIL.izquierda = NIL;
		NIL.derecha = NIL;
		NIL.padre = NIL;
		raiz = NIL;
	}

	private void rotarIzquierda(Nodo x) {
		Nodo y = x.derecha;
		x.derecha = y.izquierda;

		if (y.izquierda != NIL)
			y.izquierda.padre = x;
		y.padre = x.padre;
		if (x.padre == NIL)
			raiz = y;
		else if (x == x.padre.izquierda)
			x.padre.izquierda = y;
		else
			x.padre.derecha = y;

		y.izquierda = x;
		x.padre = y;
	}

	private void rotarDerecha(Nodo x) {
		Nodo y = x.izquierda;
		x.izquierda = y.derecha;

		if (y.derecha != NIL)
			y.derecha.padre = x;
		y.padre = x.padre;

		if (x.padre == NIL)
			raiz = y;
		else if (x == x.padre.derecha)
			x.padre.derecha = y;
		else
			x.padre.izquierda = y;

		y.derecha = x;
		x.padre = y;
	}

	public void agregar(int clave) {
		Nodo nuevo = new Nodo(clave);
		nuevo.izquierda = NIL;
		nuevo.derecha = NIL;

		Nodo padre = NIL;
		Nodo actual = raiz;

		while (actual != NIL) {
			padre = actual;
			if (clave < actual.clave)
				actual = actual.izquierda;
			else if (clave > actual.clave)
				actual = actual.derecha;
			else
				return; //clave duplciada
		}
		nuevo.padre = padre;
		if (padre == NIL)
			raiz = nuevo;
		else if (clave < padre.clave)
			padre.izquierda = nuevo;
		else
			padre.derecha = nuevo;
		insertarArreglar(nuevo);
	}

	//Perdon denuevo antonio intente simplificar pero es casi lo mismo que la fuente de apache
	private void insertarArreglar(Nodo z) {
		while (z.padre.color == Nodo.ROJO) {
			if (z.padre == z.padre.padre.izquierda) {
				Nodo tio = z.padre.padre.derecha;
				if (tio.color == Nodo.ROJO) {
					// Caso 1: el tío es rojo → recolorear
					z.padre.color = Nodo.NEGRO;
					tio.color = Nodo.NEGRO;
					z.padre.padre.color = Nodo.ROJO;
					z = z.padre.padre;
				} else {
					if (z == z.padre.derecha) {
						// Caso 2: triángulo → rotar para convertirlo en línea
						z = z.padre;
						rotarIzquierda(z);
					}
					// Caso 3: línea → recolorear + rotar
					z.padre.color = Nodo.NEGRO;
					z.padre.padre.color = Nodo.ROJO;
					rotarDerecha(z.padre.padre);
				}
			} else {
				// Espejo del caso anterior
				Nodo tio = z.padre.padre.izquierda;
				if (tio.color == Nodo.ROJO) {
					z.padre.color = Nodo.NEGRO;
					tio.color = Nodo.NEGRO;
					z.padre.padre.color = Nodo.ROJO;
					z = z.padre.padre;
				} else {
					if (z == z.padre.izquierda) {
						z = z.padre;
						rotarDerecha(z);
					}
					z.padre.color = Nodo.NEGRO;
					z.padre.padre.color = Nodo.ROJO;
					rotarIzquierda(z.padre.padre);
				}
			}
		}
		raiz.color = Nodo.NEGRO;
	}

	public void eliminar(int clave) {
		Nodo z = buscarNodo(clave);
		if (z == NIL) return;
		Nodo y = z;
		Nodo x;
		boolean colorOriginalY = y.color;

		if (z.izquierda == NIL) {
			x = z.derecha;
			transplantar(z, z.derecha);
		} else if (z.derecha == NIL) {
			x = z.izquierda;
			transplantar(z, z.izquierda);
		} else {
			y = minimo(z.derecha);
			colorOriginalY = y.color;
			x = y.derecha;
			if (y.padre == z) {
				x.padre = y;
			} else {
				transplantar(y, y.derecha);
				y.derecha = z.derecha;
				y.derecha.padre = y;
			}
			transplantar(z, y);
			y.izquierda = z.izquierda;
			y.izquierda.padre = y;
			y.color = z.color;
		}

		if (colorOriginalY == Nodo.NEGRO) {
			eliminarArreglar(x);
		}
	}

	private void transplantar(Nodo u, Nodo v) {
		if (u.padre == NIL) {
			raiz = v;
		} else if (u == u.padre.izquierda) {
			u.padre.izquierda = v;
		} else {
			u.padre.derecha = v;
		}
		v.padre = u.padre;
	}

	private Nodo minimo(Nodo nodo) {
		while (nodo.izquierda != NIL) {
			nodo = nodo.izquierda;
		}
		return nodo;
	}

	private void eliminarArreglar(Nodo x) {
		while (x != raiz && x.color == Nodo.NEGRO) {
			if (x == x.padre.izquierda) {
				Nodo hermano = x.padre.derecha;

				// Caso 1: hermano rojo
				if (hermano.color == Nodo.ROJO) {
					hermano.color = Nodo.NEGRO;
					x.padre.color = Nodo.ROJO;
					rotarIzquierda(x.padre);
					hermano = x.padre.derecha;
				}

				// Caso 2: hermano negro con ambos hijos negros
				if (hermano.izquierda.color == Nodo.NEGRO
						&& hermano.derecha.color == Nodo.NEGRO) {
					hermano.color = Nodo.ROJO;
					x = x.padre;
				} else {
					// Caso 3: hermano negro, hijo derecho negro
					if (hermano.derecha.color == Nodo.NEGRO) {
						hermano.izquierda.color = Nodo.NEGRO;
						hermano.color = Nodo.ROJO;
						rotarDerecha(hermano);
						hermano = x.padre.derecha;
					}
					// Caso 4: hermano negro, hijo derecho rojo
					hermano.color = x.padre.color;
					x.padre.color = Nodo.NEGRO;
					hermano.derecha.color = Nodo.NEGRO;
					rotarIzquierda(x.padre);
					x = raiz;
				}
			} else {
				// Espejo
				Nodo hermano = x.padre.izquierda;

				if (hermano.color == Nodo.ROJO) {
					hermano.color = Nodo.NEGRO;
					x.padre.color = Nodo.ROJO;
					rotarDerecha(x.padre);
					hermano = x.padre.izquierda;
				}

				if (hermano.derecha.color == Nodo.NEGRO
						&& hermano.izquierda.color == Nodo.NEGRO) {
					hermano.color = Nodo.ROJO;
					x = x.padre;
				} else {
					if (hermano.izquierda.color == Nodo.NEGRO) {
						hermano.derecha.color = Nodo.NEGRO;
						hermano.color = Nodo.ROJO;
						rotarIzquierda(hermano);
						hermano = x.padre.izquierda;
					}
					hermano.color = x.padre.color;
					x.padre.color = Nodo.NEGRO;
					hermano.izquierda.color = Nodo.NEGRO;
					rotarDerecha(x.padre);
					x = raiz;
				}
			}
		}
		x.color = Nodo.NEGRO;
	}

	private Nodo buscarNodo(int clave) {
		Nodo actual = raiz;
		while (actual != NIL) {
			if (clave == actual.clave) return actual;
			if (clave < actual.clave) actual = actual.izquierda;
			else actual = actual.derecha;
		}
		return NIL;
	}

	public void mostrar() {
		if (raiz == NIL) {
			System.out.println("no hay nada");
			return;
		}
		mostrar(raiz, 0);
	}

	private void mostrar(Nodo nodo, int nivel) {
		if (nodo != NIL) {
			mostrar(nodo.derecha, nivel + 1);
			String sangria = "\t".repeat(nivel);
			String color = (nodo.color == Nodo.ROJO) ? "R" : "N";
			System.out.printf("%s%d (%s)\n",sangria,nodo.clave,color);
			mostrar(nodo.izquierda, nivel + 1);
		}
	}
}

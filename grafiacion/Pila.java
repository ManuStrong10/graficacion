import java.util.ArrayList;
import java.util.EmptyStackException;

public class Pila<T> {
    private ArrayList<T> elementos;

    public Pila() {
        this.elementos = new ArrayList<>();
    }

    public void push(T valor) {
        elementos.add(valor);
    }

    public T pop() {
        if (elementos.isEmpty()) {
            throw new EmptyStackException();
        }
        return elementos.remove(elementos.size() - 1);
    }

    public T top() {
        if (elementos.isEmpty()) {
            throw new EmptyStackException();
        }
        return elementos.get(elementos.size() - 1);
    }

    public T secondTop() {
        if (elementos.size() < 2) {
            throw new IllegalStateException("No hay suficientes elementos en la pila");
        }
        return elementos.get(elementos.size() - 2);
    }

    public T thirdTop() {
        if (elementos.size() < 2) {
            throw new IllegalStateException("No hay suficientes elementos en la pila");
        }
        return elementos.get(elementos.size() - 3);
    }

    public boolean isEmpty() {
        return elementos.isEmpty();
    }

    public int size() {
        return elementos.size();
    }

    public void printStack() {
        System.out.println(elementos);
    }

     public void imprimir() {
        if (isEmpty()) {
            System.out.println("La pila está vacía.");
        } else {
            System.out.println("Elementos en la pila:");
            for (int i = 0; i < elementos.size(); i++) {
                T elemento = elementos.get(i);
                System.out.print("[");
                Integer[] arreglo = (Integer[]) elemento;
                for (int j = 0; j < arreglo.length; j++) {
                    System.out.print(arreglo[j]);
                    if (j < arreglo.length - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println("]");
            }
        }
    }
}


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class Pantalla extends JPanel {
    public Pixel[][] pixels;
    private int figura;
    private Circulo circulo;
    private Triangulo triangulo;
    private Cuadrado cuadrado;
    private Color color;
    private int filas, columnas;
    private int tamPixel;
    public int getXmouse = -1;
    public int getYmouse = -1;

    public Pantalla(int columnas, int filas) {
        this.filas = filas;
        this.columnas = columnas;
        pixels = new Pixel[columnas][filas];
        tamPixel = Pixel.tamPixel;
        llenarMatriz();
        
        // Configuración del tamaño del panel
        setBounds(0, 0, columnas * tamPixel, filas * tamPixel);
        setPreferredSize(new Dimension(columnas * tamPixel, filas * tamPixel));

        color = new Color(0, 0, 0);  // Color negro por defecto
        
        // Agregar el MouseListener para detectar clics
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Obtener la posición en píxeles del mouse
                int x = e.getX();
                int y = e.getY();
                
                // Calcular la posición de la celda (píxel) en la matriz
                int columna = x / tamPixel;
                int fila = y / tamPixel;

		getXmouse = fila;
		getYmouse = columna;
                
                // Pintar el píxel en esa posición
                pintarPixel(columna, fila);
                
                // Imprimir las coordenadas del píxel pintado
                System.out.println("Píxel pintado en posición: (" + columna + ", " + fila + ")");
                
                // Redibujar la pantalla para actualizar la vista
                repaint();
            }
        });
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private void llenarMatriz() {
        Color blanco = new Color(200, 200, 200);  // Color blanco por defecto para los píxeles
        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < filas; j++) {
                pixels[i][j] = new Pixel(i * tamPixel, j * tamPixel, blanco);
            }
        }
    }

    public void pintarPixel(int i, int j) {
        if (i >= 0 && i < columnas && j >= 0 && j < filas) {
            pixels[i][j].setColor(color);  // Establecer el color del píxel
            pixels[i][j].setPintado(true); // Marcar el píxel como pintado
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < filas; j++) {
                pixels[i][j].dibujarPixel(g);  // Dibujar el píxel
            }
        }
    }
    // Método para limpiar todos los píxeles
public void limpiarPixeles() {
    Color blanco = new Color(200, 200, 200);  // Color blanco por defecto para los píxeles
    for (int i = 0; i < columnas; i++) {
        for (int j = 0; j < filas; j++) {
            pixels[i][j].setColor(blanco);  // Limpiar el color de cada píxel
            pixels[i][j].setPintado(false); // Marcar como no pintado
        }
    }
    repaint();  // Redibujar la pantalla después de limpiar los píxeles
}

// Método para rotar la figura
public void rotarFigura(int angulo) {
    if (figura == 1) {
        circulo.rotar(angulo);  // Llamada a rotar el círculo
    } else if (figura == 2) {
        cuadrado.rotar(angulo);  // Llamada a rotar el cuadrado
    } else if (figura == 3) {
        triangulo.rotar(angulo);  // Llamada a rotar el triángulo
    }
}

// Método para escalar la figura
public void escalarFigura(double escala) {
    if (figura == 1) {
        circulo.escalar(escala);  // Escalar el círculo
    } else if (figura == 2) {
        cuadrado.escalar(escala);  // Escalar el cuadrado
    } else if (figura == 3) {
        triangulo.escalar(escala, escala);  // Escalar el triángulo
    }
}

// Método para rellenar la figura
public void rellenarFigura() {
    if (figura == 1) {
        circulo.dibujarConRelleno();  // Rellenar el círculo
    } else if (figura == 2) {
        cuadrado.dibujarConRelleno();  // Rellenar el cuadrado
    } else if (figura == 3) {
        triangulo.dibujarConRelleno();  // Rellenar el triángulo
    }
}

// Método para trasladar la figura
public void trasladarFigura(int trasX, int trasY) {
    if (figura == 1) {
        circulo.trasladar(trasX, trasY);  // Trasladar el círculo
    } else if (figura == 2) {
        cuadrado.trasladar(trasX, trasY);  // Trasladar el cuadrado
    } else if (figura == 3) {
        triangulo.trasladar(trasX, trasY);  // Trasladar el triángulo
    }
}

// Método para graficar un círculo
public void graficarCirculo(int xC, int yC, int radio, boolean segmentado, int grosor) {
    figura = 1;
    circulo = new Circulo(xC, yC, radio, segmentado, grosor, this);
    circulo.dibujarCirculo();
}

// Método para graficar un cuadrado
public void graficarCuadrado(int xC, int yC, int lado, boolean segmentado, int grosor) {
    figura = 2;
    cuadrado = new Cuadrado(xC, yC, lado, segmentado, grosor, this);
    cuadrado.dibujarCuadrado();
}

// Método para graficar un triángulo
public void graficarTriangulo(int[] coordenada, boolean segmentado, int grosor) {
    figura = 3;
    triangulo = new Triangulo(coordenada[0], coordenada[1], coordenada[2], coordenada[3], coordenada[4], coordenada[5], segmentado, grosor, this);
    triangulo.dibujarTriangulo();
}

}


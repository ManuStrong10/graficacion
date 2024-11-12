import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Pantalla extends JPanel{
    public Pixel[][] pixels;
    private int figura;
    private Circulo circulo;
    private Triangulo triangulo;
    private Cuadrado cuadrado;
    private Color color;
    private int filas, columnas;
    private int tamPixel;

    public Pantalla(int columnas, int filas) {
        this.filas = filas;
        this.columnas = columnas;
        pixels = new Pixel[columnas][filas];
        tamPixel = Pixel.tamPixel;
        llenarMatriz();
        setBounds(0,0, columnas*tamPixel, filas*tamPixel);
        setPreferredSize(new Dimension(columnas*tamPixel, filas*tamPixel));
        color = new Color(255, 255, 255);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private void llenarMatriz() {
        Color negro = new Color(10,23,67);
        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < filas; j++) {
                pixels[i][j] = new Pixel(i * tamPixel, j * tamPixel, negro);
            }
        }
    }  
    
    public void limpiarPixeles() {
        Color negro = new Color(10,23,67);
        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < filas; j++) {
                pixels[i][j].setColor(negro);
                pixels[i][j].setPintado(false);
            }
        }
        repaint();
    }

    public void pintarPixel(int i, int j) {
        if (i >= 0 && i < columnas && j >= 0 && j < filas) {
            pixels[i][j].setColor(color);
            pixels[i][j].setPintado(true);
        }
    }

    public void graficarCirculo(int xC, int yC, int radio, boolean segmentado, int grosor) {
        figura = 1;
        circulo = new Circulo(xC, yC, radio, segmentado, grosor, this);
        circulo.dibujarCirculo();
    }

    public void graficarCuadrado(int xC, int yC, int lado,boolean segmentado, int grosor) {
        figura = 2;
        cuadrado = new Cuadrado(xC, yC, lado, segmentado, grosor, this);
        cuadrado.dibujarCuadrado();
    }

    public void graficarTriangulo(int [] coordenada,boolean segmentado, int grosor) {
        figura = 3;
        triangulo = new Triangulo(coordenada[0], coordenada[1], coordenada[2], coordenada[3], coordenada[4], coordenada[5], segmentado, grosor, this);
        triangulo.dibujarTriangulo();
    }

    public void rellenarFigura() {
        if (figura == 1) {
            circulo.dibujarConRelleno();
        } else if (figura == 2) {
           cuadrado.dibujarConRelleno();
        } else if (figura == 3) {
            triangulo.dibujarConRelleno();
        }
    }

    public void escalarFigura(double escala) {
        if (figura == 1) {
            circulo.escalar(escala);
        } else if (figura == 2) {
            cuadrado.escalar(escala);
        } else if (figura == 3) {
            triangulo.escalar(escala, escala);
        }
    }

    public void trasladarFigura(int trasX, int trasY) {
        if (figura == 1) {
            circulo.trasladar(trasX, trasY);
        } else if (figura == 2) {
            cuadrado.trasladar(trasX, trasY);
        } else if (figura == 3) {
            triangulo.trasladar(trasX, trasY);
        }
    }

    public void rotarFigura(int angulo) {
        if (figura == 1) {
            circulo.rotar(angulo);
        }else if (figura == 2) {
            cuadrado.rotar(angulo);
        }else if (figura == 3) {
            triangulo.rotar(angulo);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for(int i = 0; i < columnas; i++) {
            for (int j = 0; j < filas; j++) {
                pixels[i][j].dibujarPixel(g);
            }
        }
    }
}

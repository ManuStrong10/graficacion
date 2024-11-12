
public class Cuadrado {
    private Pantalla pantalla;
    private int xC, yC;
    private int lado;
    private boolean segmentado;
    private int grosor;
    private boolean relleno;
    private int mitadLado;
    private int[][] vertices;
    private int contador;
    private boolean control;

    public Cuadrado(int xC, int yC, int lado, boolean segmentado, int grosor, Pantalla pantalla) {
        this.pantalla = pantalla;
        this.xC = xC;
        this.yC = yC;
        this.lado = lado;
        this.segmentado = segmentado;
        this.grosor = grosor;
        relleno = false;
        contador = 0;
        control = true;
        vertices = new int[4][2];
        calcularVertices();
    }

    private void calcularVertices() {
        mitadLado = lado / 2;
        vertices[0][0] = xC - mitadLado;
        vertices[0][1] = yC - mitadLado;
        vertices[1][0] = xC + mitadLado;
        vertices[1][1] = yC - mitadLado;
        vertices[2][0] = xC + mitadLado;
        vertices[2][1] = yC + mitadLado;
        vertices[3][0] = xC - mitadLado;
        vertices[3][1] = yC + mitadLado;
        
    }

    public void dibujarCuadrado() {
        lineaBresenham(vertices[0][0], vertices[0][1], vertices[1][0], vertices[1][1], grosor);
        lineaBresenham(vertices[1][0], vertices[1][1], vertices[2][0], vertices[2][1], grosor);
        lineaBresenham(vertices[2][0], vertices[2][1], vertices[3][0], vertices[3][1], grosor);
        lineaBresenham(vertices[3][0], vertices[3][1], vertices[0][0], vertices[0][1], grosor);
    }

    public void dibujarConRelleno() {
        relleno = true;
        if (segmentado) {
            segmentado = false;
        }
        dibujarCuadrado();
        rellenar();
    }

    private void rellenar() {
        int menorX = Math.min(Math.min(vertices[0][0], vertices[1][0]), Math.min(vertices[2][0], vertices[3][0]));
        int menorY = Math.min(Math.min(vertices[0][1], vertices[1][1]), Math.min(vertices[2][1], vertices[3][1]));
        int mayorX = Math.max(Math.max(vertices[0][0], vertices[1][0]), Math.max(vertices[2][0], vertices[3][0]));
        int mayorY = Math.max(Math.max(vertices[0][1], vertices[1][1]), Math.max(vertices[2][1], vertices[3][1]));
        for (int i = menorY; i <= mayorY; i++) {
            int inicio = -1;
            int fin = -1;
            for (int j = menorX; j <= mayorX; j++) {
                if (pantalla.pixels[j][i].getPintado()) {
                    if (inicio == -1) {
                        inicio = j;
                    } else {
                        fin = j;
                    }
                }
            }
            if (inicio != -1 && fin != -1) {
                pintarFila(inicio, fin, i);
            }
        }
        pantalla.repaint();
    }

    private void pintarFila(int inicio, int fin, int fila) {
        for (int i = inicio + 1; i < fin; i++) {
            pantalla.pintarPixel(i, fila);
        }
    }

    public void escalar(double escala) {
//        lado = (int) Math.round(escala*lado);
        for (int i = 0; i < 4; i++) {
            vertices[i][0] = (int) Math.round(xC + (vertices[i][0] - xC) * escala);
            vertices[i][1] = (int) Math.round(yC + (vertices[i][1] - yC) * escala);
        }
        pantalla.limpiarPixeles();
        if (relleno) {
            dibujarConRelleno();
        } else {
            dibujarCuadrado();
        }
    }

    public void trasladar(int trasX, int trasY) {
        xC += trasX;
        yC += trasY;
        for (int i = 0; i < 4; i++) {
            vertices[i][0] += trasX;
            vertices[i][1] += trasY;
        }

        pantalla.limpiarPixeles();
        if (relleno) {
            dibujarConRelleno();
        } else {
            dibujarCuadrado();
        }
    }

    public void rotar(int angulo) {
        double sen = Math.sin(Math.toRadians(angulo));
        double cos = Math.cos(Math.toRadians(angulo));
        int dx = 0, dy = 0;
        for (int i = 0; i < 4; i++) {
            dx = vertices[i][0] - xC;
            dy = vertices[i][1] - yC;
            vertices[i][0] = xC + (int) Math.round(dx*cos - dy*sen);
            vertices[i][1] = yC + (int) Math.round(dx*sen + dy*cos);
        }  
        pantalla.limpiarPixeles();
        if (relleno) {
            dibujarConRelleno();
        } else {
            dibujarCuadrado();
        }
    }

    private void lineaBresenham(int x0, int y0, int x1, int y1, int grosor) {
        int x, y, dx, dy, p, incE, incNE, stepx, stepy;
        dx = (x1 - x0);
        dy = (y1 - y0);
        if (dy < 0) {
            dy = -dy;
            stepy = -1;
        } else
            stepy = 1;
        if (dx < 0) {
            dx = -dx;
            stepx = -1;
        } else
            stepx = 1;
        x = x0;
        y = y0;
        controlGrosor(x, y);
        if (dx > dy) {
            p = 2 * dy - dx;
            incE = 2 * dy;
            incNE = 2 * (dy - dx);
            while (x != x1) {
                x = x + stepx;
                if (p < 0) {
                    p = p + incE;
                } else {
                    y = y + stepy;
                    p = p + incNE;
                }
                if (segmentado) {
                    controlLinea(x, y);
                } else {
                    controlGrosor(x, y);
                }
            }
        } else {
            p = 2 * dx - dy;
            incE = 2 * dx;
            incNE = 2 * (dx - dy);
            while (y != y1) {
                y = y + stepy;
                if (p < 0) {
                    p = p + incE;
                } else {
                    x = x + stepx;
                    p = p + incNE;
                }
                if (segmentado) {
                    controlLinea(x, y);
                } else {
                    controlGrosor(x, y);
                }
            }
        }
        pantalla.repaint();
    }

    private void controlLinea(int x, int y) {
        if (control) {
            contador++;
            if (contador == grosor * 2) {
                control = false;
            }
            controlGrosor(x, y);
        } else {
            contador--;
            if (contador == 0) {
                control = true;
            }
        }
    }

    private void controlGrosor(int x, int y) {
        pantalla.pintarPixel(x, y);
        if (grosor > 1) {
            pantalla.pintarPixel(x + 1, y);
            pantalla.pintarPixel(x, y + 1);
            pantalla.pintarPixel(x + 1, y + 1);
            if (grosor > 2) {
                pantalla.pintarPixel(x - 1, y);
                pantalla.pintarPixel(x - 1, y + 1);
                pantalla.pintarPixel(x - 1, y - 1);
                pantalla.pintarPixel(x, y - 1);
                pantalla.pintarPixel(x + 1, y - 1);
            }
        }
    }

}


public class Triangulo {
    private Pantalla pantalla;
    private int x1,x2,x3;
    private int y1,y2,y3;
    private int contador;
    private boolean control;
    private boolean segmentado;
    private int grosor;
    private boolean relleno;

    public Triangulo(int x1, int y1, int x2, int y2, int x3, int y3, boolean segmentado, int grosor, Pantalla pantalla) {
        this.pantalla = pantalla;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        this.segmentado = segmentado;
        this.grosor = grosor;
        relleno = false;
        contador = 0;
        control = true;
    }

    public void dibujarTriangulo() {
        lineaBresenham(x1, y1, x2, y2, grosor);
        lineaBresenham(x2, y2, x3, y3, grosor);
        lineaBresenham(x3, y3, x1, y1, grosor);
    }

    public void dibujarConRelleno() {
        relleno = true;
        if (segmentado) segmentado = false;
        dibujarTriangulo();
        rellenar();
    }

    private void rellenar() {
        int menorX = Math.min(Math.min(x1, x2), x3);
        int menorY = Math.min(Math.min(y1, y2), y3);
        int mayorX = Math.max(Math.max(x1, x2), x3);
        int mayorY = Math.max(Math.max(y1, y2), y3);
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

    public void escalar(double xE, double yE) {
        x2 = (int) Math.round(x1 + (x2 - x1) * xE);
        y2 = (int) Math.round(y1 + (y2 - y1) * yE);
        x3 = (int) Math.round(x1 + (x3 - x1) * xE);
        y3 = (int) Math.round(y1 + (y3 - y1) * yE);
        pantalla.limpiarPixeles();
        if (relleno) {
            dibujarConRelleno();
        } else {
            dibujarTriangulo();
        }
    }

    public void trasladar(int trasX, int trasY) {
        x1 += trasX;
        y1 += trasY;
        x2 += trasX;
        y2 += trasY;
        x3 += trasX;
        y3 += trasY;
        pantalla.limpiarPixeles();
        if (relleno) {
            dibujarConRelleno();
        } else {
            dibujarTriangulo();
        }
    }

    public void rotar(int angulo) {
        double sen = Math.sin(Math.toRadians(angulo));
        double cos = Math.cos(Math.toRadians(angulo));
        int auxX = x2;
        int auxY = y2;
        x2 = x1 + (int) Math.round((auxX - x1) * cos) - (int) Math.round((auxY - y1) * sen);
        y2 = y1 + (int) Math.round((auxX - x1) * sen) + (int) Math.round((auxY - y1) * cos);
        auxX = x3;
        auxY = y3;
        x3 = x1 + (int) Math.round((auxX - x1) * cos) - (int) Math.round((auxY - y1) * sen);
        y3 = y1 + (int) Math.round((auxX - x1) * sen) + (int) Math.round((auxY - y1) * cos);
        pantalla.limpiarPixeles();
        if (relleno) {
            dibujarConRelleno();
        } else {
            dibujarTriangulo();
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
                pantalla.pintarPixel(x -1 , y - 1);
                pantalla.pintarPixel(x, y - 1);
                pantalla.pintarPixel(x + 1, y - 1);
            }
        }
    }
}

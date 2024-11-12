import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

public class Circulo {

    private int xC, yC;
    private int radio;
    private boolean segmentado;
    private int grosorBorde;
    private Pantalla pantalla;
    private boolean relleno;
    private int xP;
    private int yP;

    public Circulo(int xC, int yC, int radio, boolean segmentado, int grosorBorde, Pantalla pantalla) {
        this.xC = xC;
        this.yC = yC;
        this.radio = radio;
        this.segmentado = segmentado;
        this.grosorBorde = grosorBorde;
        this.pantalla = pantalla;
        relleno = false;
        xP = xC - radio;
        yP = yC;
    }

    public void dibujarCirculo() {
        int x = 0;
        int y = radio;
        int d = 1 - radio; 
        int contador = 0; 
        boolean control = true;
    
        while (x <= y) {
            if (segmentado) {
                if (control) {
                    pintarConGrosor(x, y);
                    contador++;
                    if (contador  == 3) {
                        control = false;
                    }
                } else {
                    contador--;
                    if (contador == 0) {
                        control = true;
                    }
                }
            } else {
                pintarConGrosor(x, y);
            }
    
            x++;
            if (d < 0) {
                d += 2 * x + 1;
            } else {
                y--;
                d += 2 * (x - y) + 1;
            }
        }
    }
    
    private void pintarConGrosor(int x, int y) {
        pintar(x, y);
        if (grosorBorde >= 2) {
            pintar(x + 1, y);
            pintar(x, y + 1);
            pintar(x + 1, y + 1);
        }
        if (grosorBorde >= 3) {
            pintar(x - 1, y);
            pintar(x, y - 1);
            pintar(x + 1, y - 1);
            pintar(x - 1, y + 1);
        }
    }
    
    private void pintar(int x, int y) {
        pantalla.pintarPixel(xC + x, yC + y);
        pantalla.pintarPixel(xC - x, yC + y);
        pantalla.pintarPixel(xC + x, yC - y);
        pantalla.pintarPixel(xC - x, yC - y);
        pantalla.pintarPixel(xC + y, yC + x);
        pantalla.pintarPixel(xC - y, yC + x);
        pantalla.pintarPixel(xC + y, yC - x);
        pantalla.pintarPixel(xC - y, yC - x);
        pantalla.repaint();
    }
    
    public void dibujarConRelleno() {
        relleno = true;
        pantalla.limpiarPixeles();
        dibujarCirculo();
        rellenar();
    }

    private void rellenar() {
        int x,y;
        Queue<Point> cola = new LinkedList<>();
        cola.add(new Point(xC, yC)); 
    
        while (!cola.isEmpty()) {
            Point p = cola.poll();
            x = p.x;
            y = p.y;
        
            if (dentroDelCirculo(x, y) && !pantalla.pixels[x][y].getPintado()) {
                pantalla.pintarPixel(x, y);

                cola.add(new Point(x + 1, y));
                cola.add(new Point(x - 1, y));
                cola.add(new Point(x, y + 1));
                cola.add(new Point(x, y - 1));
                cola.add(new Point(x + 1, y + 1));
                cola.add(new Point(x - 1, y + 1));
                cola.add(new Point(x + 1, y - 1));
                cola.add(new Point(x - 1, y - 1));
            } else if (pantalla.pixels[x][y].getPintado()) {
            }
        }
    
        pantalla.repaint();
    }

    private boolean dentroDelCirculo(int x, int y) {
        int dx = x - xC;
        int dy = y - yC;
        return dx * dx + dy * dy <= radio * radio;
    }

    public void escalar(double escala) {
        radio = (int) Math.round(radio * escala);
        pantalla.limpiarPixeles();
        if (relleno) {
            dibujarConRelleno();
        } else {
            dibujarCirculo();
        }
    }

    public void trasladar(int trasX, int trasY) {
        xC += trasX;
        yC += trasY;

        pantalla.limpiarPixeles();
        if (relleno) {
            dibujarConRelleno();
        } else {
            dibujarCirculo();
        }
    }

    public void rotar(int angulo) {
        double sen = Math.sin(Math.toRadians(angulo));
        double cos = Math.cos(Math.toRadians(angulo));
        int auxX = xC;
        int auxY = yC;
        xC = xP + (int) Math.round((auxX - xP) * cos) - (int) Math.round((auxY - yP) * sen);
        yC = yP + (int) Math.round((auxX - xP) * sen) + (int) Math.round((auxY - yP) * cos);
        pantalla.limpiarPixeles();
        if (relleno) {
            dibujarConRelleno();
        } else {
            dibujarCirculo();
        }
    }

}

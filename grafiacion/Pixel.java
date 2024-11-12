import java.awt.Color;
import java.awt.Graphics;

public class Pixel {
    private int x,y;
    private Color color;
    public static int tamPixel = 5;
    private boolean pintado;

    public Pixel(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        pintado = false;    
    }

    public void setPintado(boolean pintado) {
        this.pintado = pintado;
    }

    public boolean getPintado() { 
        return pintado;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void dibujarPixel(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, tamPixel, tamPixel);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, tamPixel, tamPixel);
    }

}
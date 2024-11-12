import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ventana extends JFrame {
    private int WIDTH, HEIGHT;

    public Ventana(int columnas, int filas) {
        WIDTH = columnas * Pixel.tamPixel + 16;
        HEIGHT = filas * Pixel.tamPixel + 110;
        configuracionFrame();
        configuracionElementos(columnas, filas);
        pack();

    }

    private void configuracionFrame() {
        setTitle("Graficacion por computadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);
        //setResizable(false);
        setLocationRelativeTo(null);
    }

    private void configuracionElementos(int columnas, int filas) {
        JPanel panel = new JPanel();
        BoxLayout lay = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setLayout(lay);
        Pantalla pantalla = new Pantalla(columnas, filas);
        panel.add(pantalla);
        Control control = new Control(getWidth() - 2, 60, pantalla, this);
        panel.add(control);
        add(panel, BorderLayout.CENTER);
    }
}
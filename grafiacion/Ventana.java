import java.awt.BorderLayout;
import java.awt.Dimension;

//import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ventana extends JFrame {
    private int WIDTH, HEIGHT;

    public Ventana(int columnas, int filas) {
        WIDTH = columnas * Pixel.tamPixel + 320;
        HEIGHT = filas * Pixel.tamPixel + 44;
        configuracionFrame();
        configuracionElementos(columnas, filas);
        pack();

    }

    private void configuracionFrame() {
        setTitle("Primitivas de salida Final");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);
        //setResizable(false);
        setLocationRelativeTo(null);
    }

    private void configuracionElementos(int columnas, int filas) {
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();        
        panel2.setLayout(new BorderLayout());
        panel.setLayout(new BorderLayout());
        Pantalla pantalla = new Pantalla(columnas, filas);        
        Control control = new Control(300, 400, pantalla, this);
        panel2.add(control,BorderLayout.NORTH);
        panel.add(panel2,BorderLayout.WEST);
        panel.add(pantalla,BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);
    }
}
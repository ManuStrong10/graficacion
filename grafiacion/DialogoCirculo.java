import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogoCirculo extends JDialog {

    private JTextField txtCentroX, txtCentroY, txtRadio;
    private JButton btnDibujar;
    private int[] datos;
    private Pantalla p;

    public DialogoCirculo(Frame padre, Pantalla pa) {
        super(padre, "Círculo", true); 
	this.p = pa;
        datos = new int[3];
        setLayout(new GridLayout(4, 2));  

        add(new JLabel("Centro X:"));
        txtCentroX = new JTextField(String.valueOf(p.getXmouse));
        add(txtCentroX);

        add(new JLabel("Centro Y:"));
        txtCentroY = new JTextField(String.valueOf(p.getYmouse));
        add(txtCentroY);

        add(new JLabel("Radio:"));
        txtRadio = new JTextField("10");
        add(txtRadio);

        btnDibujar = new JButton("Dibujar");
        add(btnDibujar);

        btnDibujar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    datos[0] = Integer.parseInt(txtCentroX.getText());
                    datos[1] = Integer.parseInt(txtCentroY.getText());
                    datos[2] = Integer.parseInt(txtRadio.getText());

                    setVisible(false);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(DialogoCirculo.this, "Ingrese valores válidos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setSize(300, 200);
        setLocationRelativeTo(padre);
    }

    public int[] getDatos() {
        return datos;
    }
}

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DialogoCuadrado extends JDialog {

    private JTextField txtCentroX, txtCentroY, txtLado;
    private JButton btnDibujar;
    private int[] datos;

    public DialogoCuadrado(Frame padre) {
        super(padre, "Cuadrado", true); 
        datos = new int[3];
        setLayout(new GridLayout(4, 2)); 

        add(new JLabel("Centro X:"));
        txtCentroX = new JTextField("60");
        add(txtCentroX);

        add(new JLabel("Centro Y:"));
        txtCentroY = new JTextField("40");
        add(txtCentroY);

        add(new JLabel("Lado:"));
        txtLado = new JTextField("20");
        add(txtLado);

        btnDibujar = new JButton("Dibujar");
        add(btnDibujar);

        btnDibujar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    datos[0] = Integer.parseInt(txtCentroX.getText());
                    datos[1] = Integer.parseInt(txtCentroY.getText());
                    datos[2] = Integer.parseInt(txtLado.getText());

                    setVisible(false);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(DialogoCuadrado.this, "Ingrese valores válidos", "Error", JOptionPane.ERROR_MESSAGE);
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

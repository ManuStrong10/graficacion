import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class DialogoTriangulo extends JDialog{
    private JTextField txtX1, txtY1, txtX2, txtY2, txtX3, txtY3;
    private JButton btnDibujar;
    private int[] datos;
    

    public DialogoTriangulo(Frame padre) {
        super(padre, "Triangulo", true);
        datos = new int[6];

        setLayout(new GridLayout(7, 2));

        add(new JLabel("X1:"));
        txtX1 = new JTextField("60");
        add(txtX1);
        
        add(new JLabel("Y1:"));
        txtY1 = new JTextField("30");
        add(txtY1);
        
        add(new JLabel("X2:"));
        txtX2 = new JTextField("70");
        add(txtX2);
        
        add(new JLabel("Y2:"));
        txtY2 = new JTextField("50");
        add(txtY2);
        
        add(new JLabel("X3:"));
        txtX3 = new JTextField("50");
        add(txtX3);
        
        add(new JLabel("Y3:"));
        txtY3 = new JTextField("50");
        add(txtY3);

        
        btnDibujar = new JButton("Dibujar");
        add(btnDibujar);

        btnDibujar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    datos[0] = Integer.parseInt(txtX1.getText());
                    datos[1] = Integer.parseInt(txtY1.getText());
                    datos[2] = Integer.parseInt(txtX2.getText());
                    datos[3] = Integer.parseInt(txtY2.getText());
                    datos[4] = Integer.parseInt(txtX3.getText());
                    datos[5] = Integer.parseInt(txtY3.getText());

                    setVisible(false);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(DialogoTriangulo.this, "Ingrese valores válidos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setSize(300, 300);
        setLocationRelativeTo(padre);
    }

    public int[] getDatos() {
        return datos;
    }
}

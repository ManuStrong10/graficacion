import java.awt.AWTException;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Control extends JPanel{

    private JButton btnCirculo, btnCuadrado, btnTriangulo, btnLimpiar, btnRotar, btnEscalar, btnRellenar, btnTrasladar, btnGuardar, btnEljrColor;

    private JTextField txtfldAngulo, textfldEscala, txtfldTrasX, txtfldTrasY;

    private JComboBox<String> cmbxtipo, cmbxgrosor;
    private Pantalla pantalla;
    private Ventana ventana;

    private JPanel colorPrevio;

    public Control(int WIDTH, int HEIGHT, Pantalla pantalla, Ventana ventana) {
        this.pantalla = pantalla;
        this.ventana = ventana;
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addComponents();
        addActionListeners();
    }

    private void addComponents() {
        GridBagConstraints contenedor = new GridBagConstraints();
        contenedor.weightx = 1.0;
        contenedor.weighty = 1.0;
        contenedor.insets = new Insets(2, 4, 4, 4);
        contenedor.fill = GridBagConstraints.BOTH;

        addButtons(contenedor);
        addLabels(contenedor);
        addComboBoxes(contenedor);
        addTextFields(contenedor);
        addColorPrevio(contenedor);

    }

    private void addColorPrevio(GridBagConstraints contenedor) {
        colorPrevio = new JPanel();
        colorPrevio.setBackground(Color.WHITE);
        colorPrevio.setPreferredSize(new Dimension(50, 50));
        contenedor.gridx = 8;
        contenedor.gridy = 0;
        add(colorPrevio, contenedor);
    }

    private void addButtons(GridBagConstraints contenedor) {
        btnCirculo = createButton("Circulo", 0, 0, contenedor);
        btnCuadrado = createButton("Cuadrado", 1, 0, contenedor);
        btnTriangulo = createButton("Triangulo", 0, 1, contenedor);
        btnLimpiar = createButton("Limpiar", 1, 1, contenedor);
        btnRotar = createButton("Rotar", 4, 0, contenedor);
        btnEscalar = createButton("Escalar", 4, 1, contenedor);
        btnTrasladar = createButton("Trasladar", 6, 1, contenedor);
        btnRellenar = createButton("Rellenar", 6, 0, contenedor);
        btnEljrColor = createButton("Elegir Color", 7, 0, contenedor);
        btnGuardar = createButton("Guardar", 10, 1, contenedor);
    }

    private JButton createButton(String texto, int gridx, int gridy, GridBagConstraints contenedor) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(82, 22));
        contenedor.gridx = gridx;
        contenedor.gridy = gridy;
        add(boton, contenedor);
        return boton;
    }

    private void addLabels(GridBagConstraints contenedor) {
        createLabels("Tipo de linea", 2, 0, contenedor);
        createLabels("Grosor de linea", 2, 1, contenedor);
    }

    private void createLabels(String texto, int gridx, int gridy, GridBagConstraints contenedor) {
        JLabel label = new JLabel(texto);
        contenedor.gridx = gridx;
        contenedor.gridy = gridy;
        add(label, contenedor);
    }

    private void addComboBoxes(GridBagConstraints contenedor) {
        cmbxtipo = createComboBox(new String[] {"Continua", "Segmentada"}, 3, 0, contenedor);
        cmbxgrosor = createComboBox(new String[] {"Normal", "Medio", "Gruesa"}, 3, 1, contenedor);
    }

    private JComboBox<String> createComboBox(String[] items, int gridx, int gridy, GridBagConstraints contenedor) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        contenedor.gridx = gridx;
        contenedor.gridy = gridy;
        add(comboBox, contenedor);
        return comboBox;
    }

    private void addTextFields(GridBagConstraints contenedor) {
        txtfldAngulo = createTextField("0", 5, 0, contenedor);
        textfldEscala = createTextField("0.0", 5, 1, contenedor);
        txtfldTrasX = createTextField("0", 7, 1, contenedor);
        txtfldTrasY = createTextField("0", 8, 1, contenedor);
    }

    private JTextField createTextField(String texto, int gridx, int gridy, GridBagConstraints contenedor) {
        JTextField textField = new JTextField(texto);
        contenedor.gridx = gridx;
        contenedor.gridy = gridy;
        add(textField, contenedor);
        return textField;
    }

    private void addActionListeners() {

        btnCirculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogoCirculo dlgCirculo = new DialogoCirculo(ventana);
                dlgCirculo.setVisible(true);
                graficarFigura(dlgCirculo.getDatos(),1);
            }
        });

        btnCuadrado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogoCuadrado dlgCuadrado = new DialogoCuadrado(ventana);
                dlgCuadrado.setVisible(true);
                graficarFigura(dlgCuadrado.getDatos(),2);
            }
        });

        btnTriangulo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogoTriangulo dlgTriangulo = new DialogoTriangulo(ventana);
                dlgTriangulo.setVisible(true);
                graficarFigura(dlgTriangulo.getDatos(),3);
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla.limpiarPixeles();
            }
        });

        btnRotar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoAngulo = txtfldAngulo.getText();
                if(!textoAngulo.equals("")){
                    try {
                        int angulo = Integer.parseInt(textoAngulo);
                        pantalla.rotarFigura(angulo);            
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "El valor ingresado no es un numero", "Error", JOptionPane.ERROR_MESSAGE);

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El campo no puede estar vacio", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnEscalar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoEscala = textfldEscala.getText();
                if (!textoEscala.equals("")) {
                    try {
                        double escala = Double.parseDouble(textoEscala);
                        pantalla.escalarFigura(escala);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "El valor ingresado no es un numero", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(null, "El campo no puede estar vacio", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnRellenar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla.rellenarFigura();
            }
        });

        btnTrasladar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoX = txtfldTrasX.getText();
                String textoY = txtfldTrasY.getText();
                if (!textoX.equals("") && !textoY.equals("")) {
                    try {
                        int trasX = Integer.parseInt(textoX);
                        int trasY = Integer.parseInt(textoY);
                        pantalla.trasladarFigura(trasX, trasY);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "El valor ingresado no es un numero", "Error", JOptionPane.ERROR_MESSAGE);
                    } 
                    
                } else {
                    JOptionPane.showMessageDialog(null, "El campo no puede estar vacio", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        btnEljrColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogoElegirColor colorDialog = new DialogoElegirColor(ventana, "Elija un color", pantalla);
                colorDialog.setVisible(true);

                Color colorSeleccionado = colorDialog.getSelectedColor();

                if (colorSeleccionado != null) {
                    colorPrevio.setBackground(colorSeleccionado);
                }
            }
        });
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarGrafico();
            }            
        });
    }

    private void graficarFigura(int[] datos,int fig) {
        boolean segementado = false;
        if (cmbxtipo.getSelectedIndex() == 1) {
            segementado = true;
        }
        int grosor = 1;
        if (cmbxgrosor.getSelectedIndex() == 1) {
            grosor = 2;
        }

        if (cmbxgrosor.getSelectedIndex() == 2) {
            grosor = 3;
        }

//        pantalla.limpiarPixeles();
        if (fig == 1) {
            pantalla.graficarCirculo(datos[0], datos[1], datos[2], segementado, grosor);
        }

        if (fig == 2) {
            pantalla.graficarCuadrado(datos[0], datos[1], datos[2], segementado, grosor);
        }

        if (fig == 3) {
            pantalla.graficarTriangulo(datos,segementado, grosor);
        }
    }

    private void guardarGrafico() {
        BufferedImage img = null;
        try {
            Point p = ventana.getLocationOnScreen(); 
            img = new Robot().createScreenCapture(new Rectangle((int) p.getX() + pantalla.getLocation().x + 8,
                (int) p.getY() + pantalla.getLocation().y + 31, pantalla.getWidth(), pantalla.getHeight()));
            
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar como");

                fileChooser.setFileFilter(new FileNameExtensionFilter("Imagen PNG y JPG", "png","jpg"));

                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File archivoSalida = fileChooser.getSelectedFile();

                    String formato = "png";
                    if (fileChooser.getFileFilter().getDescription().contains("JPG")) {
                        formato = "jpg";
                    }
                    if (!archivoSalida.getName().toLowerCase().endsWith("."+formato)) {
                        archivoSalida = new File(archivoSalida.getAbsolutePath() + "." + formato);
                    }

                    ImageIO.write(img, formato, archivoSalida);
                    System.out.println("Imagen guardada correctamente en: " + archivoSalida.getAbsolutePath());
                    Desktop.getDesktop().open(archivoSalida.getAbsoluteFile());
                }

        } catch (AWTException | IOException ex) {
            ex.printStackTrace();
            System.err.println("Error al guardar la imagen: " + ex.getMessage());
            System.out.println("Error als guardar la imagen: " + ex.getMessage());
        }
    }
}

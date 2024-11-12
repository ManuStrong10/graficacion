
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogoElegirColor extends JDialog {
    
    private JColorChooser elegirColor;
    private JComboBox<String> modeloColorCombo;
    private JPanel vistaPrevia;
    private Pantalla pantalla;
    private JLabel valorModeloLabel;

    public DialogoElegirColor(JFrame padre, String titulo, Pantalla pantalla) {
        super(padre, titulo, true);
        this.pantalla = pantalla;
        
        setLayout(new BorderLayout());
        setSize(700, 550);
        setLocationRelativeTo(padre);

        modeloColorCombo = new JComboBox<>(new String[] {"RGB", "HSV", "HSL"});
        modeloColorCombo.addActionListener(new ColorModelListener());
        
        valorModeloLabel = new JLabel("Valor actual: ");

        vistaPrevia = new JPanel();
        vistaPrevia.setPreferredSize(new Dimension(100, 100));
        vistaPrevia.setBackground(Color.WHITE);

        elegirColor = new JColorChooser();
        elegirColor.getSelectionModel().addChangeListener(e -> updatePreview(elegirColor.getColor()));
        
        JButton btnAplicar = new JButton("Aplicar");
        btnAplicar.addActionListener(e -> aplicarColor());

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(modeloColorCombo, BorderLayout.WEST);
        panelTop.add(vistaPrevia, BorderLayout.EAST);
        panelTop.add(valorModeloLabel, BorderLayout.CENTER);

        add(panelTop, BorderLayout.NORTH);
        add(elegirColor, BorderLayout.CENTER);
        add(btnAplicar, BorderLayout.SOUTH);

        actualizarColorChooser(vistaPrevia.getBackground());
    }

    private void updatePreview(Color color) {
        vistaPrevia.setBackground(color);
        actualizarValoresChooser(color);
    }

    private void actualizarValoresChooser(Color color) {
        String modelo = (String) modeloColorCombo.getSelectedItem();
        if (modelo.equals("RGB")) {
            valorModeloLabel.setText("RGB: " + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue());
        } else if (modelo.equals("HSV")) {
            float[] hsv = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
            valorModeloLabel.setText(String.format("HSV: %.2f, %.2f, %.2f", hsv[0], hsv[1], hsv[2]));
        } else if (modelo.equals("HSL")) {
            float[] hsl = RGBAHSL(color);
            valorModeloLabel.setText(String.format("HSL: %.2f, %.2f, %.2f", hsl[0], hsl[1], hsl[2]));
        }
    }

    public Color getSelectedColor() {
        return vistaPrevia.getBackground();
    }

    private void aplicarColor() {
        Color colorSelec = vistaPrevia.getBackground();
        pantalla.setColor(colorSelec);
        dispose();
    }

    private class ColorModelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            actualizarColorChooser(vistaPrevia.getBackground());
        }
    }

    private void actualizarColorChooser(Color color) {
        String modelo = (String) modeloColorCombo.getSelectedItem();
        if (modelo.equals("RGB")) {
            elegirColor.setColor(color);
        } else if(modelo.equals("HSV")) {
            float[] hsv = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
            Color hsvColor = Color.getHSBColor(hsv[0], hsv[1], hsv[2]);
            elegirColor.setColor(hsvColor);
        } else if (modelo.equals("HSL")) {
            float[] hsl = RGBAHSL(color);
            Color hslColor = HSLARGB(hsl[0], hsl[1], hsl[2]);
            elegirColor.setColor(hslColor);
        }
        updatePreview(color);
    }

    private float[] RGBAHSL(Color color) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float max = Math.max(r, Math.max(g, b));
        float min = Math.min(r, Math.min(g, b));
        float h = 0, s, l = (max + min) / 2;

        if (max == min) {
            h = s = 0;
        } else {
            float delta = max - min;
            s = l > 0.5 ? delta / (2 - max - min) : delta / (max + min);
            if (max == r) h = (g - b) / delta + (g < b ? 6 : 0);
            else if (max == g) h = (b - r) / delta + 2;
            else h = (r - g) / delta + 4;
            h /= 6;
        }
        return new float[]{h, s, l};
    }

    private Color HSLARGB(float h, float s, float l) {
        float r, g, b;
        if (s == 0) {
            r = g = b = l;
        } else {
            float q = l < 0.5 ? l * (1 + s) : l + s - l * s;
            float p = 2 * l - q;
            r = hueARGB(p, q, h + 1f / 3);
            g = hueARGB(p, q, h);
            b = hueARGB(p, q, h - 1f / 3);
        }
        return new Color(Math.round(r * 255), Math.round(g * 255), Math.round(b * 255));
    }

    private float hueARGB(float p, float q, float t) {
        if (t < 0) t += 1;
        if (t > 1) t -= 1;
        if (t < 1 / 6f) return p + (q - p) * 6 * t;
        if (t < 1 / 2f) return q;
        if (t < 2 / 3f) return p + (q - p) * (2 / 3f - t) * 6;
        return p;
    }
}

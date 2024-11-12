import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        Ventana interfaz = new Ventana(120, 75);
        interfaz.setVisible(true);
    }
}
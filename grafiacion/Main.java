import com.formdev.flatlaf.FlatDarkLaf;

public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        Ventana interfaz = new Ventana(240, 160);
        interfaz.setVisible(true);
    }
}
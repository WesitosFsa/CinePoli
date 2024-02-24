import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Código de tu programa aquí
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new login();
            }
        });
    }
}
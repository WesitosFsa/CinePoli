import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ADMIN extends JFrame{
    JPanel panel1;
    private JButton VERSALASButton;
    private JButton VERPELICULASButton;
    private JButton VERVENTASButton;
    private JButton cerrarSesionButton;

    private verpelis pantallaVerPelis;

    // Constructor de la clase ADMIN
    public ADMIN() {
        /*Configuracion dela pantalla ADMIN*/
        setContentPane(panel1);
        setSize(800,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        // Configuración de ActionListener para el botón "Ver Películas"
        VERPELICULASButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verpelis pelis = new verpelis();
                pelis.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.dispose();
            }
        });

        // Configuración de ActionListener para el botón "Ver Ventas"
        VERVENTASButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Manejar el evento de VERVENTASButton
            }
        });

        VERSALASButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salas salas = new salas();
                salas.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.dispose();
            }
        });
        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    login login = new login();
                    login.setVisible(true);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                    frame.dispose();
                } else {

                }
            }
        });
    }
}


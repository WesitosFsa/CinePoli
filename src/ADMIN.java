import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase ADMIN que extiende de JFrame
 */
public class ADMIN extends JFrame {
    // Panel principal
    JPanel panel1;

    // Componentes de la interfaz de usuario
    private JButton VERSALASButton;
    private JButton VERPELICULASButton;
    private JButton VERVENTASButton;
    private JButton cerrarSesionButton;

    private verpelis pantallaVerPelis;

    /**
     * Constructor de la clase ADMIN
     */
    public ADMIN() {
        /* Configuración de la pantalla ADMIN */
        setContentPane(panel1); // Establece el panel como el contenido del marco
        setSize(800, 500); // Establece el tamaño del marco
        setResizable(false); // Hace que el marco no sea redimensionable
        setLocationRelativeTo(null); // Centra el marco en la pantalla
        setUndecorated(true); // Elimina la decoración del marco (bordes y botones de cierre)
        setVisible(true); // Hace que el marco sea visible

        // Configuración de ActionListener para el botón "Ver Películas"
        VERPELICULASButton.addActionListener(new ActionListener() {
            /**
             * Acción realizada cuando se presiona el botón "Ver Películas"
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crea una instancia de la clase verpelis y la hace visible
                verpelis pelis = new verpelis();
                pelis.setVisible(true);
                // Cierra el marco actual
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.dispose();
            }
        });

        // Configuración de ActionListener para el botón "Ver Ventas"
        VERVENTASButton.addActionListener(new ActionListener() {
            /**
             * Acción realizada cuando se presiona el botón "Ver Ventas"
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crea una instancia de la clase Estadistica y la hace visible
                Estadistica pantalla = new Estadistica();
                pantalla.setVisible(true);
                // Cierra el marco actual
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.dispose();
            }
        });

        // Configuración de ActionListener para el botón "Ver Salas"
        VERSALASButton.addActionListener(new ActionListener() {
            /**
             * Acción realizada cuando se presiona el botón "Ver Salas"
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crea una instancia de la clase salas y la hace visible
                salas salas = new salas();
                salas.setVisible(true);
                // Cierra el marco actual
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.dispose();
            }
        });

        // Configuración de ActionListener para el botón "Cerrar Sesión"
        cerrarSesionButton.addActionListener(new ActionListener() {
            /**
             * Acción realizada cuando se presiona el botón "Cerrar Sesión"
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Muestra un mensaje de confirmación y cierra la sesión si el usuario lo confirma
                int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    // Crea una instancia de la clase login y la hace visible
                    login login = new login();
                    login.setVisible(true);
                    // Cierra el marco actual
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                    frame.dispose();
                } else {
                    // Si el usuario elige no cerrar sesión, no hace nada
                }
            }
        });
    }
}

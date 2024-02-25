import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ADMIN {
    JPanel panel1;
    private JButton VERSALASButton;
    private JButton VERPELICULASButton;
    private JButton VERVENTASButton;

    private verpelis pantallaVerPelis;

    // Constructor de la clase ADMIN
    public ADMIN(verpelis verPelisInstance) {
        this.pantallaVerPelis = verPelisInstance;

        // Configuración de ActionListener para el botón "Ver Salas"
        VERSALASButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarSalas();
            }
        });

        // Configuración de ActionListener para el botón "Ver Películas"
        VERPELICULASButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVerPelis();
            }
        });

        // Configuración de ActionListener para el botón "Ver Ventas"
        VERVENTASButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Manejar el evento de VERVENTASButton
            }
        });
    }

    // Método para mostrar la ventana de administración
    public void mostrarAdmin() {
        JFrame frame = new JFrame("ADMIN");
        frame.setContentPane(panel1);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Método para mostrar la ventana de Salas
    private void mostrarSalas() {
        // Verificar que la instancia de pantallaVerPelis no sea nula
        if (pantallaVerPelis != null) {
            List<verpelis.Pelicula> listaPeliculas = pantallaVerPelis.listaPeliculas;
            // Verificar que la lista de películas no sea nula
            if (listaPeliculas != null) {
                // Crear una nueva ventana de Salas y pasar la instancia actual de ADMIN y la lista de películas
                JFrame frame = new JFrame("SalasApp");
                frame.setContentPane(new salas(this, listaPeliculas).panel1);
                frame.setSize(800, 500);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

                // Cerrar la ventana actual de ADMIN
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                if (currentFrame != null) {
                    currentFrame.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "La lista de películas en pantallaVerPelis es null.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "La instancia de pantallaVerPelis es null.");
        }
    }

    // Método para mostrar la ventana de VerPelis
    void mostrarVerPelis() {
        // Crear una nueva ventana de VerPelis y pasar la instancia actual de ADMIN y la instancia de verpelis
        JFrame frame = new JFrame("VerPelis");
        frame.setContentPane(new verpelis(this, pantallaVerPelis).panel1);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Cerrar la ventana actual de ADMIN
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
        if (currentFrame != null) {
            currentFrame.dispose();
        }
    }

    // Método para actualizar la lista de películas en la instancia de verpelis
    public void actualizarListaPeliculas(List<verpelis.Pelicula> listaPeliculas) {
        pantallaVerPelis.listaPeliculas = listaPeliculas;
        pantallaVerPelis.mostrarPeliculas();
    }
}


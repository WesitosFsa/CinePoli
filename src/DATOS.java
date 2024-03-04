import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

 /**
 * Clasa frame que imprime los datos de la pelicula
 */
public class DATOS extends JFrame {
    // Componentes de la interfaz de usuario
    private JPanel DATOS;
    private JButton salirButton;
    private JButton reservarAsientoButton;
    private JScrollPane Info_pel;
    private JTextArea sinopsis;
    private JLabel director;
    private JLabel anho;
    private JLabel genero;
    private JLabel titulo;
    private JLabel clasificacion;
    private JLabel labelimagen;
    private JPanel horarios;

    // Variables para los datos de la película
    private String nombrePelicula;
    private String generoPelicula;
    private String directorPelicula;
    private String anhoPelicula;
    private ImageIcon imagenPelicula;
    private String sinopses;
    private String clasificacions;
    private Connection conexion;

    // Constructor de la clase DATOS
    public DATOS(String sinopses, String nombrePelicula, String generoPelicula, String directorPelicula, String anhoPelicula, ImageIcon imagenPelicula, String clasificacions) {
        // Inicialización de variables con los datos de la película
        this.sinopses = sinopses;
        this.nombrePelicula = nombrePelicula;
        this.generoPelicula = generoPelicula;
        this.directorPelicula = directorPelicula;
        this.anhoPelicula = anhoPelicula;
        this.imagenPelicula = imagenPelicula;

        // Configuración del JFrame
        setContentPane(DATOS);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        // Establece los valores de los componentes con los datos de la película
        titulo.setText(nombrePelicula);
        genero.setText(generoPelicula);
        director.setText(directorPelicula);
        anho.setText(anhoPelicula);
        labelimagen.setIcon(imagenPelicula);
        sinopsis.setText(sinopses);
        clasificacion.setText(clasificacions);

        // ActionListener para el botón "Salir"
        salirButton.addActionListener(new ActionListener() {
            /**
             * Acción realizada cuando se presiona el botón "Salir"
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crea una instancia de la clase PELICULAS y la hace visible
                PELICULAS pelis = new PELICULAS();
                pelis.setVisible(true);
                // Cierra el marco actual
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(DATOS);
                frame.dispose();
            }
        });

        // ActionListener para el botón "Reservar Asiento"
        reservarAsientoButton.addActionListener(new ActionListener() {
            /**
             * Acción realizada cuando se presiona el botón "Reservar Asiento"
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crea una instancia de la clase Reser y la hace visible
                Reser reser = new Reser();
                reser.setVisible(true);
                // Cierra el marco actual
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(DATOS);
                frame.dispose();
            }
        });

        // Intenta establecer la conexión a la base de datos
        establecerConexion();
    }

    /**
     * Método para establecer la conexión a la base de datos
     */
    private void establecerConexion() {
        try {
            // Crea una instancia de la clase Main para establecer la conexión
            Main conexionbd = new Main();
            conexion = conexionbd.establecerConexion();
        } catch (SQLException ex) {
            // En caso de error, muestra un mensaje de error
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

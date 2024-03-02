import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class salas extends JFrame{
    JPanel panel1;
    private JTextField textFieldIDPELICULA;
    private JTextField textFieldHORARIO;
    private JTextField textFieldASIENTOS;
    private JButton INHABILITARButton;
    private JLabel HORARIO;
    private JLabel IDPELICULA;
    private JLabel ASIENTOS;
    private JTextField mostrarPelicula;
    private JTextField mostrarHorario;
    private JTextField mostrarAsientos;
    private JLabel asientos;
    private JLabel horario;
    private JLabel nombrePeli;
    private JButton ingresarButton;
    private JButton verInformacionButton;
    private JButton menuButton;

    private List<verpelis.Pelicula> listaPeliculas;
    private Map<String, Salas> salas;
    private ADMIN salasScreen;

    // Constructor de la clase salas
    public salas() {
        /*Configuracion dela pantalla Salas*/
        setContentPane(panel1);
        setSize(800,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        this.salasScreen = salasScreen;
        this.listaPeliculas = listaPeliculas;
        salas = new HashMap<String, Salas>();

        // Configuración de ActionListener para el botón "Ingresar"
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // Configuración de ActionListener para el botón "Ver Información"
        verInformacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // Configuración de ActionListener para el botón "Inhabilitar"
        INHABILITARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // Configuración de ActionListener para el botón "Menu"
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ADMIN Admin = new ADMIN();
                Admin.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.dispose();
            }
        });
    }
    // Método para CRUD
    // estos metodos deben de estar conectada a la bd

    // Método para actualizar la lista de películas desde otras partes de la aplicación
    public void actualizarListaPeliculas(List<verpelis.Pelicula> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
        // Puedes realizar acciones adicionales aquí si es necesario
        // Por ejemplo, actualizar la información en la interfaz gráfica
    }

    // Clase interna que representa una sala
    class Salas {
        private String idPelicula;
        private String horario;
        private String asientos;

        // Constructor de la clase Salas
        public Salas(String idPelicula, String horario, String asientos) {
            this.idPelicula = idPelicula;
            this.horario = horario;
            this.asientos = asientos;
        }

        // Métodos getter para obtener información de la sala
        public String getIdPelicula() {
            return idPelicula;
        }

        public String getHorario() {
            return horario;
        }

        public String getAsientos() {
            return asientos;
        }
    }
}









import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class salas {
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
    public salas(ADMIN salasScreen, List<verpelis.Pelicula> listaPeliculas) {
        this.salasScreen = salasScreen;
        this.listaPeliculas = listaPeliculas;
        salas = new HashMap<String, Salas>();

        // Configuración de ActionListener para el botón "Ingresar"
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarSala();
            }
        });

        // Configuración de ActionListener para el botón "Ver Información"
        verInformacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verInformacion();
            }
        });

        // Configuración de ActionListener para el botón "Inhabilitar"
        INHABILITARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inhabilitarSala();
            }
        });

        // Configuración de ActionListener para el botón "Menu"
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAAdmin();
            }
        });
    }

    // Método para ingresar una nueva sala
    private void ingresarSala() {
        String idPelicula = textFieldIDPELICULA.getText();
        String horario = textFieldHORARIO.getText();
        String asientos = textFieldASIENTOS.getText();

        // Crear una nueva instancia de la clase Salas y agregarla al mapa de salas
        Salas sala = new Salas(idPelicula, horario, asientos);
        salas.put(idPelicula, sala);

        // Buscar la película correspondiente en la lista de películas
        Optional<verpelis.Pelicula> peliculaOptional = listaPeliculas.stream()
                .filter(p -> p.getId() == Integer.parseInt(idPelicula))
                .findFirst();

        // Verificar si la película fue encontrada
        if (peliculaOptional.isPresent()) {
            String nombrePelicula = peliculaOptional.get().getNombre();
            JOptionPane.showMessageDialog(null, "Sala ingresada correctamente para la película: " + nombrePelicula);

            // Actualizar la lista de películas en tiempo real en la interfaz gráfica
            if (salasScreen != null) {
                salasScreen.actualizarListaPeliculas(listaPeliculas);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Película no encontrada");
        }
    }

    // Método para ver la información de una sala
    private void verInformacion() {
        String idPelicula = textFieldIDPELICULA.getText();

        // Verificar si la película con el ID proporcionado existe en la lista de películas
        if (listaPeliculas.stream().anyMatch(pelicula -> pelicula.getId() == Integer.parseInt(idPelicula))) {
            // Obtener la película correspondiente de la lista
            verpelis.Pelicula pelicula = listaPeliculas.stream()
                    .filter(p -> p.getId() == Integer.parseInt(idPelicula))
                    .findFirst()
                    .orElse(null);

            // Mostrar información de la película y su sala asociada, si existe
            mostrarPelicula.setText(pelicula.getNombre());

            if (salas.containsKey(idPelicula)) {
                Salas sala = salas.get(idPelicula);
                mostrarHorario.setText(sala.getHorario());
                mostrarAsientos.setText(sala.getAsientos());
            } else {
                JOptionPane.showMessageDialog(null, "La sala con ID " + idPelicula + " no existe");
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sala con ID " + idPelicula + " no existe");
        }
    }

    // Método para inhabilitar una sala
    private void inhabilitarSala() {
        String idPelicula = textFieldIDPELICULA.getText();

        // Verificar si la película con el ID proporcionado existe en la lista de películas
        if (listaPeliculas.stream().anyMatch(pelicula -> pelicula.getId() == Integer.parseInt(idPelicula))) {
            // Eliminar la película y su sala asociada de las listas respectivas
            listaPeliculas.removeIf(pelicula -> pelicula.getId() == Integer.parseInt(idPelicula));
            salas.remove(idPelicula);
            JOptionPane.showMessageDialog(null, "Sala inhabilitada correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "La sala con ID " + idPelicula + " no existe");
        }
    }

    // Método para volver al menú principal de administración
    private void volverAAdmin() {
        if (salasScreen != null) {
            salasScreen.mostrarVerPelis();
            SwingUtilities.getWindowAncestor(panel1).dispose();
        } else {
            JOptionPane.showMessageDialog(null, "La instancia de VerPelis es null");
        }
    }

    // Método principal para ejecutar la aplicación
    public static void main(String[] args) {
        // En este ejemplo, crea una lista de películas ficticia para pasarla a la instancia de salas
        List<verpelis.Pelicula> listaPeliculas = new ArrayList<>();
        JFrame frame = new JFrame("SalasApp");
        frame.setContentPane(new salas(null, listaPeliculas).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

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









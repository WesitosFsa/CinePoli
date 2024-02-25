import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class verpelis {
    JPanel panel1;
    private JButton añadirButton;
    private JButton eliminarButton;
    private JTable peliculasTable;
    private JButton menuPrincipalButton;
    private DefaultTableModel tableModel;
    List<Pelicula> listaPeliculas;

    private verpelis salasScreen;
    private ADMIN adminScreen;

    private Preferences preferences = Preferences.userNodeForPackage(getClass());

    // Constructor de la clase verpelis
    public verpelis(ADMIN adminScreen, verpelis salasScreen) {
        this.adminScreen = adminScreen;
        this.salasScreen = salasScreen;
        this.listaPeliculas = new ArrayList<>();

        // Configuración del modelo de tabla para mostrar películas
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Género");
        tableModel.addColumn("Descripción");

        peliculasTable.setModel(tableModel);
        peliculasTable.setEnabled(false);

        // Configuración de ActionListener para el botón "Añadir"
        añadirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPelicula();
            }
        });

        // Configuración de ActionListener para el botón "Eliminar"
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPelicula();
            }
        });

        // Configuración de ActionListener para el botón "Menu Principal"
        menuPrincipalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAAdmin();
            }
        });

        // Mostrar las películas y cargarlas desde preferencias al inicializar
        mostrarPeliculas();
        cargarPeliculasDesdePreferencias();
    }

    // Método para agregar una nueva película
    private void agregarPelicula() {
        // Solicitar al usuario ingresar información de la nueva película
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre de la película:");
        String genero = JOptionPane.showInputDialog("Ingrese el género de la película:");
        String descripcion = JOptionPane.showInputDialog("Ingrese la descripción de la película:");

        // Generar un nuevo ID para la película
        int id = listaPeliculas.size() + 1;
        Pelicula nuevaPelicula = new Pelicula(id, nombre, genero, descripcion);
        listaPeliculas.add(nuevaPelicula);

        // Guardar películas en preferencias
        guardarPeliculasEnPreferencias();

        // Actualizar la tabla de películas y enviar la información a la pantalla salas
        mostrarPeliculas();
        if (salasScreen != null) {
            salasScreen.actualizarListaPeliculas(listaPeliculas);
        }
    }

    // Método para eliminar una película existente
    private void eliminarPelicula() {
        try {
            // Solicitar al usuario ingresar el ID de la película a eliminar
            int idEliminar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la película a eliminar:"));
            // Intentar remover la película de la lista
            boolean removed = listaPeliculas.removeIf(pelicula -> pelicula.getId() == idEliminar);

            // Guardar películas en preferencias
            guardarPeliculasEnPreferencias();

            // Actualizar la tabla de películas
            mostrarPeliculas();

            // Mostrar mensaje si la película no existe
            if (!removed) {
                JOptionPane.showMessageDialog(null, "La película con ID " + idEliminar + " no existe.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID válido (número entero).");
        }
    }

    // Método para actualizar la tabla de películas en la interfaz gráfica
    void mostrarPeliculas() {
        tableModel.setRowCount(0);

        for (Pelicula pelicula : listaPeliculas) {
            Object[] rowData = {pelicula.getId(), pelicula.getNombre(), pelicula.getGenero(), pelicula.getDescripcion()};
            tableModel.addRow(rowData);
        }
    }

    // Método para volver a la pantalla principal de administración
    private void volverAAdmin() {
        if (adminScreen != null) {
            adminScreen.mostrarAdmin();
            SwingUtilities.getWindowAncestor(panel1).dispose();
        } else {
            JOptionPane.showMessageDialog(null, "La instancia de Admin es null");
        }
    }

    // Método para cargar películas desde preferencias al iniciar la aplicación
    private void cargarPeliculasDesdePreferencias() {
        String peliculasGuardadas = preferences.get("peliculas", "");
        if (!peliculasGuardadas.isEmpty()) {
            // Parsear la cadena y cargar las películas en la lista
            String[] peliculasArray = peliculasGuardadas.split(";");
            listaPeliculas.clear(); // Limpiar la lista antes de cargar las películas
            for (String peliculaStr : peliculasArray) {
                String[] datos = peliculaStr.split(",");
                int id = Integer.parseInt(datos[0]);
                String nombre = datos[1];
                String genero = datos[2];
                String descripcion = datos[3];
                Pelicula pelicula = new Pelicula(id, nombre, genero, descripcion);
                listaPeliculas.add(pelicula);
            }
            // Actualizar la pantalla con las películas cargadas
            mostrarPeliculas();

            // También puedes actualizar la lista en la pantalla de salas si es necesario
            if (salasScreen != null) {
                salasScreen.actualizarListaPeliculas(listaPeliculas);
            }
        }
    }

    // Método para guardar películas en preferencias al realizar cambios
    private void guardarPeliculasEnPreferencias() {
        StringBuilder peliculasStr = new StringBuilder();
        for (Pelicula pelicula : listaPeliculas) {
            peliculasStr.append(pelicula.getId()).append(",")
                    .append(pelicula.getNombre()).append(",")
                    .append(pelicula.getGenero()).append(",")
                    .append(pelicula.getDescripcion()).append(";");
        }
        preferences.put("peliculas", peliculasStr.toString());
    }

    // Método para mostrar la pantalla principal de "VerPelis"
    public void mostrarVerPelis() {
        JFrame frame = new JFrame("VerPelis");
        frame.setContentPane(panel1);  // Utiliza el panel1 actual de la instancia existente
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Actualiza la lista de películas al volver a la pantalla
        mostrarPeliculas();

        // Envía la información actualizada de la película a la pantalla salas
        if (salasScreen != null) {
            salasScreen.actualizarListaPeliculas(listaPeliculas);
        }

        frame.setVisible(true);
    }

    // Método principal para ejecutar la aplicación
    public static void main(String[] args) {
        JFrame frame = new JFrame("VerPelis");
        frame.setContentPane(new verpelis(null, null).panel1);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Método para actualizar la lista de películas desde otras partes de la aplicación
    public void actualizarListaPeliculas(List<Pelicula> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
        mostrarPeliculas();
    }

    // Clase estática interna que representa una película
    public static class Pelicula {
        private int id;
        private String nombre;
        private String genero;
        private String descripcion;

        // Constructor de la clase Pelicula
        public Pelicula(int id, String nombre, String genero, String descripcion) {
            this.id = id;
            this.nombre = nombre;
            this.genero = genero;
            this.descripcion = descripcion;
        }

        // Métodos getter para obtener información de la película
        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public String getGenero() {
            return genero;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }
}





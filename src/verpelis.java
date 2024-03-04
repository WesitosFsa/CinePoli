import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Esta clase proporciona una interfaz gráfica para visualizar y administrar películas.
 */
public class verpelis extends JFrame {
    // Componentes de la interfaz gráfica
    private JPanel panel1;
    private JButton añadirButton;
    private JButton eliminarButton;
    private JTable peliculasTable;
    private JButton menuPrincipalButton;
    private DefaultTableModel tableModel;
    private List<Pelicula> listaPeliculas;
    private Connection conexion;

    private Preferences preferences = Preferences.userNodeForPackage(getClass());

    // Constructor de la clase verpelis
    public verpelis() {
        // Configuración de la ventana
        setContentPane(panel1);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        // Inicialización de la lista de películas
        this.listaPeliculas = new ArrayList<>();

        // Establecer la conexión a la base de datos
        establecerConexion();

        // Configuración del modelo de tabla para mostrar películas
        configurarModeloTabla();

        // Configuración de ActionListener para los botones
        añadirButton.addActionListener(e -> agregarPelicula());
        eliminarButton.addActionListener(e -> eliminarPelicula());
        menuPrincipalButton.addActionListener(e -> volverMenuPrincipal());

        // Cargar las películas desde la base de datos al inicializar
        cargarPeliculasDesdeBaseDatos();
    }

    // Método para establecer la conexión a la base de datos
    private void establecerConexion() {
        try {
            Main conexionbd = new Main();
            conexion = conexionbd.establecerConexion();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para configurar el modelo de tabla para mostrar películas
    private void configurarModeloTabla() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Género");
        tableModel.addColumn("Descripción");
        tableModel.addColumn("Director");
        tableModel.addColumn("Año");
        tableModel.addColumn("Clasificación");
        tableModel.addColumn("Ruta imagen");
        peliculasTable.setModel(tableModel);
        peliculasTable.setEnabled(false);
    }

    // Método para agregar una película
    private void agregarPelicula() {
        try {
            // Se solicita al usuario que ingrese los detalles de la película
            int id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la película:"));
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre de la película:");
            String genero = JOptionPane.showInputDialog("Ingrese el género de la película:");
            String descripcion = JOptionPane.showInputDialog("Ingrese la descripción de la película:");
            String director = JOptionPane.showInputDialog("Ingrese la director de la película:");
            int anio = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el año de la película:"));
            String clasificacion = JOptionPane.showInputDialog("Ingrese la clasificación de la película:");
            String ruta = JOptionPane.showInputDialog("Ingrese la ruta de la imagen de la película:");
            byte[] imagen = leerFoto(ruta);

            // Insertar la película en la base de datos
            insertarPeliculaEnBaseDatos(id, nombre, genero, descripcion, director, anio, clasificacion, imagen);

            // Actualizar la tabla con las películas de la base de datos
            cargarPeliculasDesdeBaseDatos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El año y el ID deben ser números enteros.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para insertar una película en la base de datos
    private void insertarPeliculaEnBaseDatos(int id, String nombre, String genero, String descripcion, String director, int anio, String clasificacion, byte[] imagen) {
        try {
            String sql = "INSERT INTO peliculas (id_pelicula, nombre_pelicula, genero, sinopsis, Director, anho, clasificacion, foto_pelicula) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, nombre);
            statement.setString(3, genero);
            statement.setString(4, descripcion);
            statement.setString(5, director);
            statement.setInt(6, anio);
            statement.setString(7, clasificacion);
            statement.setBytes(8, imagen);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar la película en la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para eliminar una película
    private void eliminarPelicula() {
        try {
            int idEliminar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la película a eliminar:"));
            // Eliminar la película de la base de datos
            eliminarPeliculaEnBaseDatos(idEliminar);
            // Actualizar la tabla con las películas de la base de datos
            cargarPeliculasDesdeBaseDatos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID válido (número entero).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para eliminar una película de la base de datos
    private void eliminarPeliculaEnBaseDatos(int idEliminar) {
        try {
            String sql = "DELETE FROM peliculas WHERE id_pelicula = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, idEliminar);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar la película de la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para cargar películas desde la base de datos
    private void cargarPeliculasDesdeBaseDatos() {
        try {
            String sql = "SELECT * FROM peliculas";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            listaPeliculas.clear(); // Limpiar la lista antes de cargar las películas desde la base de datos

            while (resultSet.next()) {
                // Se obtienen los datos de la película desde la base de datos
                int id = resultSet.getInt("id_pelicula");
                String nombre = resultSet.getString("nombre_pelicula");
                String genero = resultSet.getString("genero");
                String descripcion = resultSet.getString("sinopsis");
                String director = resultSet.getString("Director");
                int anio = resultSet.getInt("anho");
                String clasificacion = resultSet.getString("clasificacion");
                String imagen = resultSet.getString("foto_pelicula");

                // Se crea un objeto Pelicula y se agrega a la lista de películas
                Pelicula pelicula = new Pelicula(id, nombre, genero, descripcion, director, anio, clasificacion, imagen);
                listaPeliculas.add(pelicula);
            }

            mostrarPeliculas(); // Actualizar la tabla con las películas cargadas desde la base de datos
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar las películas desde la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para mostrar películas en la tabla
    private void mostrarPeliculas() {
        // Limpiar el modelo de tabla
        tableModel.setRowCount(0);
        // Agregar las películas al modelo de tabla
        for (Pelicula pelicula : listaPeliculas) {
            Object[] rowData = {pelicula.getId(), pelicula.getNombre(), pelicula.getGenero(), pelicula.getDescripcion(), pelicula.getDirector(), pelicula.getAnio(), pelicula.getClasificacion(), pelicula.getImagen()};
            tableModel.addRow(rowData);
        }
    }

    // Método para volver al menú principal
    private void volverMenuPrincipal() {
        ADMIN Admin = new ADMIN();
        Admin.setVisible(true);
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
        frame.dispose();
    }

    // Clase interna que representa una película
    public static class Pelicula {
        private int id;
        private String nombre;
        private String genero;
        private String descripcion;
        private String director;
        private int anio;
        private String clasificacion;
        private String imagen;

        // Constructor de la clase Pelicula
        public Pelicula(int id, String nombre, String genero, String descripcion, String director, int anio, String clasificacion, String imagen) {
            this.id = id;
            this.nombre = nombre;
            this.genero = genero;
            this.descripcion = descripcion;
            this.director = director;
            this.anio = anio;
            this.clasificacion = clasificacion;
            this.imagen = imagen;
        }

        // Métodos getter para obtener los detalles de la película
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

        public String getDirector() {
            return director;
        }

        public int getAnio() {
            return anio;
        }

        public String getClasificacion() {
            return clasificacion;
        }

        public String getImagen() {
            return imagen;
        }
    }

    // Método para leer una imagen desde una ruta
    private static byte[] leerFoto(String ruta) {
        byte[] imagenBytes = null;
        try {
            File imagenFile = new File(ruta);
            FileInputStream fis = new FileInputStream(imagenFile);
            imagenBytes = new byte[(int) imagenFile.length()];
            fis.read(imagenBytes);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagenBytes;
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class verpelis extends JFrame {
    private JPanel panel1;
    private JButton añadirButton;
    private JButton eliminarButton;
    private JTable peliculasTable;
    private JButton menuPrincipalButton;
    private DefaultTableModel tableModel;
    private List<Pelicula> listaPeliculas;
    private Connection conexion;

    private Preferences preferences = Preferences.userNodeForPackage(getClass());

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

    private void establecerConexion() {
        try {
            Main conexionbd = new Main();
            conexion = conexionbd.establecerConexion();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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

    private void agregarPelicula() {
        try {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre de la película:");
            String genero = JOptionPane.showInputDialog("Ingrese el género de la película:");
            String descripcion = JOptionPane.showInputDialog("Ingrese la descripción de la película:");
            String director = JOptionPane.showInputDialog("Ingrese la director de la película:");
            int anio = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el año de la película:"));
            String clasificacion = JOptionPane.showInputDialog("Ingrese la clasificación de la película:");
            String imagen = JOptionPane.showInputDialog("Ingrese la ruta de la imagen de la película:");

            // Insertar la película en la base de datos
            insertarPeliculaEnBaseDatos(nombre, genero, descripcion, director, anio, clasificacion, imagen);

            // Actualizar la tabla con las películas de la base de datos
            cargarPeliculasDesdeBaseDatos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El año debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertarPeliculaEnBaseDatos(String nombre, String genero, String descripcion, String director, int anio, String clasificacion, String imagen) {
        try {
            String sql = "INSERT INTO peliculas (nombre_pelicula, genero, sinopsis, Director, anho, clasificacion, foto_pelicula) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, genero);
            statement.setString(3, descripcion);
            statement.setString(4, director);
            statement.setInt(5, anio);
            statement.setString(6, clasificacion);
            statement.setString(7, imagen);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar la película en la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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


    private void cargarPeliculasDesdeBaseDatos() {
        try {
            String sql = "SELECT * FROM peliculas";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            listaPeliculas.clear(); // Limpiar la lista antes de cargar las películas desde la base de datos

            while (resultSet.next()) {
                int id = resultSet.getInt("id_pelicula");
                String nombre = resultSet.getString("nombre_pelicula");
                String genero = resultSet.getString("genero");
                String descripcion = resultSet.getString("sinopsis");
                String director = resultSet.getString("Director");
                int anio = resultSet.getInt("anho");
                String clasificacion = resultSet.getString("clasificacion");
                String imagen = resultSet.getString("foto_pelicula");

                Pelicula pelicula = new Pelicula(id, nombre, genero, descripcion, director, anio, clasificacion, imagen);
                listaPeliculas.add(pelicula);
            }

            mostrarPeliculas(); // Actualizar la tabla con las películas cargadas desde la base de datos
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar las películas desde la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void mostrarPeliculas() {
        // Limpiar el modelo de tabla
        tableModel.setRowCount(0);
        // Agregar las películas al modelo de tabla
        for (Pelicula pelicula : listaPeliculas) {
            Object[] rowData = {pelicula.getId(), pelicula.getNombre(), pelicula.getGenero(), pelicula.getDescripcion(), pelicula.getDirector(), pelicula.getAnio(), pelicula.getClasificacion(), pelicula.getImagen()};
            tableModel.addRow(rowData);
        }
    }

    private void volverMenuPrincipal() {
        ADMIN Admin = new ADMIN();
        Admin.setVisible(true);
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
        frame.dispose();
    }

    public static class Pelicula {
        private int id;
        private String nombre;
        private String genero;
        private String descripcion;
        private String director;
        private int anio;
        private String clasificacion;
        private String imagen;

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
}
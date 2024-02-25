import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class PELICULAS extends JFrame {
    private JPanel PELICULAS;
    private JButton verCarteleraButton;
    private JButton cerrarSesionButton;
    private JScrollPane PanelPeliculas;
    private JPanel PeliculasAdentro;
    private JPanel Cartelera;
    private Connection conexion;
    public PELICULAS() {
        super("Reservas");
        setContentPane(PELICULAS);
        PeliculasAdentro = new JPanel(new GridLayout(0, 3));
        PanelPeliculas.setViewportView(PeliculasAdentro);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        establecerConexion();
        // Agregar más películas según sea necesario
        verCarteleraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    cargarPeliculasDesdeBD();
            }
        });
        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login volver = new login();
                volver.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PELICULAS);
                frame.dispose();
            }
        });
    }
    private void cargarPeliculasDesdeBD() {
        try {

            // Preparar la consulta SQL
            String sql = "SELECT nombre_pelicula, foto_pelicula FROM peliculas";
            PreparedStatement statement = conexion.prepareStatement(sql);

            // Ejecutar la consulta y obtener el conjunto de resultados
            ResultSet resultSet = statement.executeQuery();

            // Iterar sobre los resultados y agregar cada película a la interfaz gráfica
            while (resultSet.next()) {
                String nombrePelicula = resultSet.getString("nombre_pelicula");
                byte[] fotoPelicula = resultSet.getBytes("foto_pelicula");

                // Convertir la imagen de bytes a ImageIcon
                ImageIcon imagen = new ImageIcon(fotoPelicula);

                // Llamar al método agregarPelicula con el nombre y la imagen
                agregarPelicula(nombrePelicula, imagen);
            }

            // Cerrar recursos
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar películas desde la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void agregarPelicula(String titulo, ImageIcon imagen) {
        // Crear un panel para la película individual
        JPanel panelIndividual = new JPanel(new BorderLayout());

        // Establecer el color de fondo del panel
        panelIndividual.setBackground(new Color(33, 33, 33)); // #212121 en formato RGB

        // Añadir imagen de la película
        JLabel labelImagen = new JLabel(imagen);
        panelIndividual.add(labelImagen, BorderLayout.CENTER);

        // Crear y configurar el botón para el título de la película
        JButton botonTitulo = new JButton(titulo);
        botonTitulo.setBackground(Color.decode("#0063A5"));  // Color de fondo del botón
        botonTitulo.setForeground(Color.WHITE); // Color del texto
        botonTitulo.setFont(new Font("Century Gothic", Font.BOLD, 11)); // Fuente personalizada

        // Deshabilitar el efecto de resaltado al pasar el mouse sobre el botón
        botonTitulo.setFocusPainted(true);
        botonTitulo.setBorderPainted(true);
        botonTitulo.setContentAreaFilled(true);

        // Establecer el tamaño preferido del botón para que el fondo sea visible
        botonTitulo.setPreferredSize(new Dimension(200, 30)); // Ajusta el tamaño según sea necesario

        // Agregar ActionListener al botón para abrir el panel registro y cerrar PELICULAS
        botonTitulo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir el panel registro
                DATOS DATOS = new DATOS();
                DATOS.setVisible(true);

                // Cerrar el panel PELICULAS
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PELICULAS);
                frame.dispose();
            }
        });

        // Añadir el botón del título de la película al panel individual
        panelIndividual.add(botonTitulo, BorderLayout.SOUTH); // Agregar el título en la parte inferior

        // Añadir el panel individual al panel principal
        PeliculasAdentro.add(panelIndividual);

        // Actualizar la interfaz gráfica
        revalidate(); // Revalidar el layout del contenedor
        repaint();   // Volver a pintar los componentes
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
    public static void main(String[] args) {
        new PELICULAS();
    }
}
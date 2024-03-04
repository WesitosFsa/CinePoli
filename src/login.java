import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Clase principal login que extiende de JFrame
 * @author Luis Guaygua
 * */
public class login extends JFrame {
    /**
     *  Componentes de la interfaz de usuario
     *  */
    private JPanel panel1;
    private JPanel LOGIN;
    private JButton Ingresar;
    private JButton registrarse;
    private JButton salir;
    private JTextField nombre;
    private JPasswordField contrasenia;
    private JPanel panel_credenciales;
    private JLabel labelBienvenida;
    private JLabel text;
    private Image imagen;

    public static String NombreUsuario;

    /**
     *  Constructor de la clase login
     *  */
    public login() {
        /* Configuración de la pantalla login */
        setContentPane(panel1);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        /**
         * Action listener de la pantalla login
         * */
        Ingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = nombre.getText();
                String password = new String(contrasenia.getPassword());
                NombreUsuario = nombreUsuario;

                int idRol = obtenerIdRol(nombreUsuario, password);

                if (idRol != -1) {
                    if (idRol == 2) {
                        // Rol 2 - Ir a la pantalla ADMIN
                        ADMIN pantallaAdmin = new ADMIN();
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                        frame.dispose();
                    } else {
                        // Otro rol - Ir a la pantalla PELICULAS
                        PELICULAS pantallaPeliculas = new PELICULAS();
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                        frame.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        /**
         * Action listener para el boton de registrarse
         * */
        registrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registro registro = new registro();
                registro.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.dispose();
            }
        });

        /**
         * Action listener para el boton de salir
         * */
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null,
                        "¿Quieres cerrar la app?",
                        "Salir de la app",JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    dispose(); // Esto permite cerrar la ventana la ventana
                    System.exit(0); // Esto permite salir de la aplicación
                }
            }
        });
    }

    /**
     * Método para obtener el ID de rol del usuario
     * */
    private int obtenerIdRol(String nombreUsuario, String password) {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Main main = new Main();
            conexion = main.establecerConexion();

            String consulta = "SELECT id_rol FROM clientes WHERE nom_usuario = ? AND contra_usuario = ?";
            preparedStatement = conexion.prepareStatement(consulta);
            preparedStatement.setString(1, nombreUsuario);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id_rol");
            } else {
                // No se encontraron credenciales
                return -1; // Valor por defecto para indicar que no se encontró el rol
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1; // Manejo de error, también se podría lanzar una excepción en lugar de devolver un valor predeterminado
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

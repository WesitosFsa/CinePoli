import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase principal Estadistica que extiende de JFrame
 */
public class Main extends JFrame {

    /**
     * Método para establecer la conexión con la base de datos
     * @return La conexión establecida
     * @throws SQLException Si ocurre un error al establecer la conexión
     */
    Connection establecerConexion() throws SQLException {
        // URL de la base de datos
        String url = "jdbc:mysql://ukghiar85gp7mrpy:nQVmOkgbY4UHYZybHvO2@bxrwabtu14qddifcwky1-mysql.services.clever-cloud.com:3306/bxrwabtu14qddifcwky1";
        // Usuario de la base de datos
        String usuarioDB = "ukghiar85gp7mrpy";
        // Contraseña de la base de datos
        String contraseniaDB = "nQVmOkgbY4UHYZybHvO2";
        // Retorna la conexión establecida utilizando DriverManager
        return DriverManager.getConnection(url, usuarioDB, contraseniaDB);
    }

    /**
     * Método principal
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        // Ejecuta el código de la interfaz de usuario en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Crea una instancia de la clase Main
                Main main = new Main();
                // Intenta establecer la conexión a la base de datos
                main.conexion_base();
                // Crea una instancia de la interfaz de inicio de sesión
                new login();
            }
        });
    }

    /**
     * Método para establecer la conexión a la base de datos
     */
    public void conexion_base() {
        try {
            // Intenta establecer la conexión a la base de datos
            Connection conexion = establecerConexion();
            // Cierra la conexión
            conexion.close();
        } catch (SQLException ex) {
            // En caso de error, muestra un mensaje de error utilizando JOptionPane
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

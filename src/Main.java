import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    Connection establecerConexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/cinefilo";
        String usuarioDB = "root";
        String contraseniaDB = "123456";
        return DriverManager.getConnection(url, usuarioDB, contraseniaDB);
    }
    public static void main(String[] args) {
        // Código de tu programa aquí
        //Pinche git y java que no carga ajajajahhhha
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main main = new Main();
                main.conexion_base();
                new login();
            }
        });
    }
    public void conexion_base() {
        try {
            Connection conexion = establecerConexion();
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
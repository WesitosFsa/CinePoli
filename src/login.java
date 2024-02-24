import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class login extends JFrame{
    private JPanel panel1;
    private JPanel LOGIN;
    private JButton Ingresar;
    private JButton registrarse;
    private JLabel labelBienvenida;
    private JButton salir;
    private JTextField nombre;
    private JPasswordField contrasenia;
    private JPanel panel_credenciales;
    private Image imagen;

    public login(){
        /*Configuracion dela pantalla login*/
        setContentPane(panel1);
        setSize(500,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);


        /** Action listener de la pantalla login
         * Ingresar: Busca dentro de la bd el usuario y contraseña que ha ingresado en el textfiel nombre y passwordfield contraseña
         * Registrarse: Le enviara a la pantalla de resgistro en dode colocara la informacion pertinente
         * Salir: cerrará el programa con un JOptionpane
         */

        Ingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = nombre.getText();
                String password = new String(contrasenia.getPassword());

                if (validarCredenciales(nombreUsuario, password)) {
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                    // Agrega aquí la lógica para lo que deseas hacer después del inicio de sesión exitoso
                } else {
                    JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        registrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registro registro = new registro();
                registro.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.dispose();
            }
        });
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null,
                                                "¿Quieres cerrar la app?",
                                                "Salir de la app",JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    //JOptionPane.showMessageDialog(null, "Cerrando la aplicación");
                    dispose(); // Esto permite cerrar la ventana la ventana
                    System.exit(0); // Esto permite salir de la aplicación
                }
            }
        });
    }
    private boolean validarCredenciales(String nombreUsuario, String password) {
        // Lógica para validar las credenciales en la base de datos
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Main main = new Main();
            // Establecer conexión
            conexion = main.establecerConexion();

            // Consulta SQL para verificar las credenciales
            String consulta = "SELECT * FROM clientes WHERE nom_usuario = ? AND contra_usuario = ?";
            preparedStatement = conexion.prepareStatement(consulta);
            preparedStatement.setString(1, nombreUsuario);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            // Si se encuentra algún resultado, las credenciales son válidas
            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                // Cerrar recursos
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

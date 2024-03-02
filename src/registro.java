import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class registro extends JFrame {
    private JPanel panel1;
    private JTextField correo;
    private JTextField usuario;
    private JTextField contra;
    private JTextField telefono;
    private JButton registrarseButton;
    private JButton inicioButton;
    private JPanel bienvenida;
    private JTextField num_tarjeta;
    private JTextField Año_Nacimiento;
    private JTextField cedula;
    private JTextField Saldo;
    private SimpleDateFormat dateFormat;

    public registro() {
        setContentPane(panel1);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        inicioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login login = new login();
                login.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.dispose();
            }
        });

        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener los valores de los campos
                    String id = generarID();
                    String nomUsuario = usuario.getText();
                    String contraUsuario = contra.getText();
                    String correoUsuario = correo.getText();
                    int telefonoUsuario = Integer.parseInt(telefono.getText());
                    String numtarjeta = num_tarjeta.getText();
                    String AnioNacimiento = Año_Nacimiento.getText();
                    String Cedula = cedula.getText();
                    String rol = "1";
                    String Saldo_trajeta = Saldo.getText();

                    if (camposNoVacios()) {
                        // Validar el formato del año de nacimiento
                        boolean formatoValido = validarFormatoAnioNacimiento(AnioNacimiento);
                        if (!formatoValido) {
                            JOptionPane.showMessageDialog(null, "Error en el año de nacimiento. Debe contener solo 4 dígitos numéricos.");
                            return;
                        }

                        // Validar el rango del año de nacimiento
                        int anio = Integer.parseInt(AnioNacimiento);
                        if (anio < 1940 || anio > 2024) {
                            JOptionPane.showMessageDialog(null, "Ingrese un año válido (1940 - 2024)");
                            return;
                        }

                        // Conectar a la base de datos y realizar la inserción
                        try {
                            Main main = new Main();
                            Connection conexion = main.establecerConexion();

                            String query = "INSERT INTO clientes (idcliente, nom_usuario, contra_usuario, correo, telf_usuario, Año_nacimiento, num_tarj, id_rol, cedula, saldo) VALUES (?,?,?,?,?,?,?,?,?,?)";

                            // Preparar la declaración
                            try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
                                // Establecer los valores de los parámetros
                                preparedStatement.setString(1, id);
                                preparedStatement.setString(2, nomUsuario);
                                preparedStatement.setString(3, contraUsuario);
                                preparedStatement.setString(4, correoUsuario);
                                preparedStatement.setInt(5, telefonoUsuario);
                                preparedStatement.setString(6, AnioNacimiento);
                                preparedStatement.setInt(7, Integer.parseInt(numtarjeta));
                                preparedStatement.setString(6, AnioNacimiento);
                                preparedStatement.setInt(7, Integer.parseInt(numtarjeta));
                                preparedStatement.setString(8, rol);
                                preparedStatement.setString(9, Cedula);
                                preparedStatement.setInt(10, Integer.parseInt(Saldo_trajeta));

                                // Ejecutar la consulta
                                preparedStatement.executeUpdate();

                                JOptionPane.showMessageDialog(null, "Registro exitoso");
                                login login = new login();
                                login.setVisible(true);
                                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                                frame.dispose();

                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error al registrar");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage());
                }
            }
        });
    }

    private boolean camposNoVacios() {
        return !usuario.getText().isEmpty() && !contra.getText().isEmpty() && !correo.getText().isEmpty()
                && !telefono.getText().isEmpty() && !Año_Nacimiento.getText().isEmpty();
    }

    private boolean validarFormatoAnioNacimiento(String anioNacimiento) {
        for (char c : anioNacimiento.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return anioNacimiento.length() == 4;
    }

    private static final String CARACTERES_PERMITIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LONGITUD_ID = 7;

    public static String generarID() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(LONGITUD_ID);

        for (int i = 0; i < LONGITUD_ID; i++) {
            int index = random.nextInt(CARACTERES_PERMITIDOS.length());
            char caracter = CARACTERES_PERMITIDOS.charAt(index);
            sb.append(caracter);
        }

        return sb.toString();
    }
}

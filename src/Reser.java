import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Reser extends JFrame{

    public static final Color COLOR_RESERVADO = new Color(147, 168, 172);
    private JPanel panel1;
    private JPanel Salaspanel;
    private JButton a1Button, a2Button, a3Button, a4Button, a6Button,a7Button,a8Button,a9Button,
            SALIRButton, continuarButton;

    private JButton[] Salas;
    private List<JButton> asientosReservados = new ArrayList<>();
    private boolean salaSeleccionada = false;

    public Reser() {
        super("Reservas");
        setContentPane(panel1);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        Salas = new JButton[]{
                a1Button, a2Button, a3Button, a4Button, a6Button,a7Button,a8Button,a9Button,
        };

        // Conectar a la base de datos y obtener las salas
        try {
            Connection connection = establecerConexion();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM sala");
            int index = 0;
            while (resultSet.next() && index < Salas.length) {
                JButton button = Salas[index];
                button.setText(resultSet.getString("Num_Sala"));
                button.setName(resultSet.getString("Dia") + " " + resultSet.getString("Horario_Sala"));
                index++;
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar las salas desde la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }

        for (JButton button : Salas) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();

                    if (salaSeleccionada) {
                        // Si ya se seleccionó una sala, mostrar el mensaje de advertencia
                        JOptionPane.showMessageDialog(null, "Solo puede seleccionar una sala a la vez", "Alerta", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if (clickedButton.getBackground().equals(COLOR_RESERVADO)) {
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Quiere reservar esta sala?",
                                "Reservar", JOptionPane.YES_NO_OPTION);
                        if (respuesta == JOptionPane.YES_OPTION) {
                            JOptionPane.showMessageDialog(null, "Reserva Realizada\nSala: " + clickedButton.getText() + "\nDía: " + getDiaHoraSala(clickedButton.getText()) + "\nHorario: " + clickedButton.getName(), "Reservar", JOptionPane.WARNING_MESSAGE);
                            clickedButton.setBackground(Color.GREEN);
                            asientosReservados.remove(clickedButton);
                            salaSeleccionada=true;
                        }
                    }
                    if (clickedButton.getBackground().equals(Color.GREEN)) {
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea cancelar la reserva?",
                                "Cancelar", JOptionPane.YES_NO_OPTION);
                        if (respuesta == JOptionPane.YES_OPTION) {
                            JOptionPane.showMessageDialog(null, "Reserva Cancelada", "Cancelar", JOptionPane.WARNING_MESSAGE);
                            clickedButton.setBackground(COLOR_RESERVADO);

                            // Agregar el botón a la lista de asientos reservados
                            asientosReservados.add(clickedButton);

                            // Habilitar la selección de sala nuevamente
                            for (JButton salaButton : Salas) {
                                salaButton.setEnabled(true);
                            }

                            // Reiniciar el estado de selección de sala
                            salaSeleccionada = false;
                        }
                        return;
                    } else if(clickedButton.getBackground().equals(COLOR_RESERVADO)) {
                        JOptionPane.showMessageDialog(null, "Solo puede reservar una vez", "Alerta", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            });
        }

        continuarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reservas reserva = new Reservas();
                reserva.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(continuarButton);
                frame.dispose();
            }
        });
        SALIRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PELICULAS pelis = new PELICULAS();
                pelis.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SALIRButton);
                frame.dispose();
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

    private Connection establecerConexion() throws SQLException {
        String url = "jdbc:mysql://ukghiar85gp7mrpy:nQVmOkgbY4UHYZybHvO2@bxrwabtu14qddifcwky1-mysql.services.clever-cloud.com:3306/bxrwabtu14qddifcwky1";
        String usuarioDB = "ukghiar85gp7mrpy";
        String contraseniaDB = "nQVmOkgbY4UHYZybHvO2";
        return DriverManager.getConnection(url, usuarioDB, contraseniaDB);
    }

    private String getDiaHoraSala(String sala) {
        String diaHora = "";
        try {
            Connection connection = establecerConexion();
            PreparedStatement statement = connection.prepareStatement("SELECT Dia, Horario_Sala FROM sala WHERE Num_Sala = ?");
            statement.setString(1, sala);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                diaHora = resultSet.getString("Dia") + " " + resultSet.getString("Horario_Sala");
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la sala desde la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return diaHora;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Reser::new);
    }
}

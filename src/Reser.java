
/**
 * Librerias nesesarias para que la pantalla se ejecute perfectamente
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * La clase `Reser` representa la interfaz de reserva de salas.
 * Extiende JFrame y contiene elementos gráficos para seleccionar y reservar salas de cine.
 */
public class Reser extends JFrame{

    /**
     * Se agregan los elementos como paneles, botones y sus respectivos arreglos para el funcionamiento correcto de
     * la pantalla
     */

    // Color para representar asientos reservados
    public static final Color COLOR_RESERVADO = new Color(147, 168, 172);

    // Paneles y botones de la interfaz
    private JPanel panel1;
    private JPanel Salaspanel;
    private JButton a1Button, a2Button, a3Button, a4Button, a6Button,a7Button,a8Button,a9Button,
            SALIRButton, continuarButton;

    // Arreglo de botones de las salas y lista de asientos reservados
    private JButton[] Salas;
    private List<JButton> asientosReservados = new ArrayList<>();

    // Bandera para verificar si se ha seleccionado una sala
    private boolean salaSeleccionada = false;

    // Constantes para almacenar la hora y sala seleccionadas

    public static  String Horapublica;
    public static String salapublica;
    private static final String HORA;
    private static final String SALA;

    /**
     * Bloque estático para inicializar las constantes con los datos de la base de datos
     * Como la coneccion a la base de datos y obtener las salas
     */

    static {
        // Conectar a la base de datos y obtener las salas
        String horaTemp = "";
        String salaTemp = "";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://ukghiar85gp7mrpy:nQVmOkgbY4UHYZybHvO2@bxrwabtu14qddifcwky1-mysql.services.clever-cloud.com:3306/bxrwabtu14qddifcwky1", "ukghiar85gp7mrpy", "nQVmOkgbY4UHYZybHvO2");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM sala");
            if(resultSet.next()) {
                salaTemp = resultSet.getString("Num_Sala");
                horaTemp = resultSet.getString("Dia") + " " + resultSet.getString("Horario_Sala");
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar las salas desde la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
        HORA = horaTemp;
        SALA = salaTemp;
    }
    /**
     * Constructor de la clase
     */
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

        // Configuración de los listeners para los botones de las salas
        for (JButton button : Salas) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();

                    // Verificar si ya se ha seleccionado una sala
                    if (salaSeleccionada) {
                        // Mostrar mensaje de advertencia si ya se seleccionó una sala
                        JOptionPane.showMessageDialog(null, "Solo puede seleccionar una sala a la vez", "Alerta", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Procesar la acción según el estado del botón
                    if (clickedButton.getBackground().equals(COLOR_RESERVADO)) {
                        // Mostrar mensaje de confirmación para reservar la sala
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Quiere reservar esta sala?",
                                "Reservar", JOptionPane.YES_NO_OPTION);
                        if (respuesta == JOptionPane.YES_OPTION) {
                            // Realizar reserva y actualizar interfaz
                            JOptionPane.showMessageDialog(null, "Reserva Realizada\nSala: " + clickedButton.getText() + "\nDía: " + HORA + "\nHorario: " + clickedButton.getName(), "Reservar", JOptionPane.WARNING_MESSAGE);
                            Horapublica = (HORA);
                            salapublica = clickedButton.getText();
                            clickedButton.setBackground(Color.GREEN);
                            asientosReservados.remove(clickedButton);
                            salaSeleccionada=true;
                        }
                    }
                    if (clickedButton.getBackground().equals(Color.GREEN)) {
                        // Mostrar mensaje de confirmación para cancelar reserva
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea cancelar la reserva?",
                                "Cancelar", JOptionPane.YES_NO_OPTION);
                        if (respuesta == JOptionPane.YES_OPTION) {
                            // Cancelar reserva y restaurar interfaz
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
                        // Mostrar mensaje de advertencia si el asiento ya está reservado
                        JOptionPane.showMessageDialog(null, "Solo puede reservar una vez", "Alerta", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            });
        }
        /**
         * Configuracion de los botones
         */

        // Configuración del listener para el botón de continuar
        continuarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar información de la sala y hora seleccionadas
                Reservas reserva = new Reservas();
                reserva.setVisible(true);
                JOptionPane.showMessageDialog(null, "Sala seleccionada: " + SALA + "\nHora seleccionada: " + HORA, "Información", JOptionPane.INFORMATION_MESSAGE);

                // Cerrar la ventana actual
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(continuarButton);
                frame.dispose();
            }
        });

        // Configuración del listener para el botón de salir
        SALIRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Volver a la ventana de selección de películas
                PELICULAS pelis = new PELICULAS();
                pelis.setVisible(true);

                // Cerrar la ventana actual
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SALIRButton);
                frame.dispose();
            }
        });
    }
}


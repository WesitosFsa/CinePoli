import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
public class Reser extends JFrame{

    public static final Color COLOR_RESERVADO = new Color(147, 168, 172);
    private JPanel panel1;
    private JPanel Salaspanel;
    private JButton a1Button, a2Button, a3Button, a4Button, a6Button,
            b1Button, b2Button, b3Button, b4Button, b5Button, b6Button, b7Button,
            c1Button, c2Button, c3Button, c4Button,
            SALIRButton, continuarButton;
    private JPanel Diaspanel;
    private JPanel Horariospanel;

    private JButton[] Salas;
    private JButton[] Dias;
    private JButton[] Horarios;
    private List<JButton> asientosReservados = new ArrayList<>();
    private boolean salaSeleccionada = false;
    private boolean diaSeleccionado = false;
    private boolean horaSeleccionado = false;

    Reser() {
        super("Reservas");
        setContentPane(panel1);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        Salas = new JButton[]{
                a1Button, a2Button, a3Button, a4Button, a6Button
        };
        Dias= new JButton[]{
                b1Button, b2Button, b3Button, b4Button, b5Button, b6Button, b7Button
        };
        Horarios = new JButton[]{
                c1Button, c2Button, c3Button, c4Button
        };

        for (JButton button : Salas) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();

                    if (salaSeleccionada) {
                        // Si ya se seleccionó un día, mostrar el mensaje de advertencia
                        JOptionPane.showMessageDialog(null, "Solo puede seleccionar una sala a la vez", "Alerta", JOptionPane.WARNING_MESSAGE);
                        return; // Salir del método sin realizar más acciones
                    }

                    if (clickedButton.getBackground().equals(COLOR_RESERVADO)) {
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Quiere reservar esta sala?",
                                "Reservar", JOptionPane.YES_NO_OPTION);
                        if (respuesta == JOptionPane.YES_OPTION) {
                            JOptionPane.showMessageDialog(null, "Reserva Realizada", "Reservar", JOptionPane.WARNING_MESSAGE);
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

                            // Reiniciar el estado de selección de día
                            salaSeleccionada = false;
                        }else{

                        }
                        return; // Salir del método sin realizar más acciones
                    } else if(clickedButton.getBackground().equals(COLOR_RESERVADO)) {
                        JOptionPane.showMessageDialog(null, "Solo puede reservar una vez", "Alerta", JOptionPane.WARNING_MESSAGE);
                        return; // Salir del método sin realizar más acciones
                    }
                    return;
                }
            });
        }


        for (JButton button : Dias) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();

                    if (diaSeleccionado) {
                        // Si ya se seleccionó un día, mostrar el mensaje de advertencia
                        JOptionPane.showMessageDialog(null, "Solo puede seleccionar un día a la vez", "Alerta", JOptionPane.WARNING_MESSAGE);
                        return; // Salir del método sin realizar más acciones
                    }

                    if (clickedButton.getBackground().equals(COLOR_RESERVADO)) {
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Quiere reservar este día?",
                                "Reservar", JOptionPane.YES_NO_OPTION);
                        if (respuesta == JOptionPane.YES_OPTION) {
                            JOptionPane.showMessageDialog(null, "Reserva Realizada", "Reservar", JOptionPane.WARNING_MESSAGE);
                            clickedButton.setBackground(Color.GREEN);
                            asientosReservados.remove(clickedButton);
                            diaSeleccionado=true;
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
                            for (JButton salaButton : Dias) {
                                salaButton.setEnabled(true);
                            }

                            // Reiniciar el estado de selección de día
                            diaSeleccionado = false;
                        }else{

                        }
                        return; // Salir del método sin realizar más acciones
                    } else if(clickedButton.getBackground().equals(COLOR_RESERVADO)) {
                        JOptionPane.showMessageDialog(null, "Solo puede reservar una vez", "Alerta", JOptionPane.WARNING_MESSAGE);
                        return; // Salir del método sin realizar más acciones
                    }
                    return;
                }
            });
        }
        for (JButton button : Horarios) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();

                    if (horaSeleccionado) {
                        // Si ya se seleccionó un día, mostrar el mensaje de advertencia
                        JOptionPane.showMessageDialog(null, "Solo puede seleccionar un horario a la vez", "Alerta", JOptionPane.WARNING_MESSAGE);
                        return; // Salir del método sin realizar más acciones
                    }

                    if (clickedButton.getBackground().equals(COLOR_RESERVADO)) {
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Quiere reservar este horario?",
                                "Reservar", JOptionPane.YES_NO_OPTION);
                        if (respuesta == JOptionPane.YES_OPTION) {
                            JOptionPane.showMessageDialog(null, "Reserva Realizada", "Reservar", JOptionPane.WARNING_MESSAGE);
                            clickedButton.setBackground(Color.GREEN);
                            asientosReservados.remove(clickedButton);
                            horaSeleccionado=true;
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
                            for (JButton salaButton : Horarios) {
                                salaButton.setEnabled(true);
                            }

                            // Reiniciar el estado de selección de día
                            horaSeleccionado = false;
                        }else{

                        }
                        return; // Salir del método sin realizar más acciones
                    } else if(clickedButton.getBackground().equals(COLOR_RESERVADO)) {
                        JOptionPane.showMessageDialog(null, "Solo puede reservar una vez", "Alerta", JOptionPane.WARNING_MESSAGE);
                        return; // Salir del método sin realizar más acciones
                    }
                    return;
                }
            });
        }


        continuarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reservas reserva = new Reservas();
                reserva.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(continuarButton);frame.dispose();
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



    public static void main(String[] args) {
        SwingUtilities.invokeLater(Reser::new);
    }
}
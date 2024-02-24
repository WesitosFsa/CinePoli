import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
public class Reservas extends JFrame{

    public static final Color COLOR_RESERVADO = new Color(147, 168, 172);
    private JPanel panel1;
    private JButton a1Button, a2Button, a3Button, a4Button, a5Button, a6Button, a7Button, a8Button, a9Button,
            b1Button, b2Button, b3Button, b4Button, b5Button, b6Button, b7Button, b8Button, b9Button,
            c1Button, c2Button, c3Button, c4Button, c5Button, c6Button, c7Button, c8Button, c9Button,
            d1Button, d2Button, d3Button, d4Button, d5Button, d6Button, d7Button, d8Button, d9Button,
            e1Button, e2Button, e3Button, e4Button, e5Button, e6Button, e7Button, e8Button, e9Button,
            f1Button, f2Button, f3Button, f4Button, f5Button, f6Button, f7Button, f8Button, f9Button,
            g1Button, g2Button, g3Button, g4Button, g5Button, g6Button, g7Button, g8Button, g9Button,
            h1Button, h2Button, h3Button, h4Button, h5Button, h6Button, h7Button, h8Button, h9Button,
            SALIRButton, continuarButton;
    private JPanel JpanelAsientos;

    private JLabel Dinero;
    private double totalPagar = 0.0;
    private JButton[] buttons;
    private List<JButton> asientosReservados = new ArrayList<>();
    Reservas() {
        super("Reservas");
        setContentPane(panel1);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        buttons = new JButton[]{
                a1Button, a2Button, a3Button, a4Button, a5Button, a6Button, a7Button, a8Button, a9Button,
                b1Button, b2Button, b3Button, b4Button, b5Button, b6Button, b7Button, b8Button, b9Button,
                c1Button, c2Button, c3Button, c4Button, c5Button, c6Button, c7Button, c8Button, c9Button,
                d1Button, d2Button, d3Button, d4Button, d5Button, d6Button, d7Button, d8Button, d9Button,
                e1Button, e2Button, e3Button, e4Button, e5Button, e6Button, e7Button, e8Button, e9Button,
                f1Button, f2Button, f3Button, f4Button, f5Button, f6Button, f7Button, f8Button, f9Button,
                g1Button, g2Button, g3Button, g4Button, g5Button, g6Button, g7Button, g8Button, g9Button,
                h1Button, h2Button, h3Button, h4Button, h5Button, h6Button, h7Button, h8Button, h9Button
        };
        for (JButton button : buttons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();
                    if (clickedButton.getBackground().equals(Color.GREEN)) {
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Quiere cancelar esta reserva?",
                                "Imprimir", JOptionPane.YES_NO_OPTION);
                        if (respuesta == JOptionPane.YES_OPTION) {
                            JOptionPane.showMessageDialog(null, "Reserva Cancelada", "Cancelar", JOptionPane.WARNING_MESSAGE);
                            clickedButton.setBackground(COLOR_RESERVADO);
                            totalPagar -= 7.0;
                            Dinero.setText("$" + totalPagar);
                            asientosReservados.remove(clickedButton);
                        }
                    } else if (clickedButton.getBackground().equals(Color.RED)) {
                        JOptionPane.showMessageDialog(null, "Asiento Reservado", "Reservas", JOptionPane.WARNING_MESSAGE);


                    } else {
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Quiere reservar este asiento?",
                                "Imprimir", JOptionPane.YES_NO_OPTION);
                        if (respuesta == JOptionPane.YES_OPTION) {
                            JOptionPane.showMessageDialog(null, "Asiento Reservado", "Factura", JOptionPane.WARNING_MESSAGE);
                            clickedButton.setBackground(Color.GREEN);
                            totalPagar += 7.0;
                            Dinero.setText("$" + totalPagar);
                            asientosReservados.add(clickedButton);
                        }
                    }
                }
            });
        }

        SALIRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Quiere salir de la app?",
                        "Salir", JOptionPane.YES_NO_OPTION);
                try {
                    if (respuesta == JOptionPane.YES_OPTION) {
                        dispose();
                        System.exit(0);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        continuarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Marcar los asientos reservados en rojo
                for (JButton button : asientosReservados) {
                    button.setBackground(Color.RED);
                }
                // Limpiar el total a pagar y el label
                totalPagar = 0.0;
                Dinero.setText("$" + totalPagar);
                // Limpiar la lista de asientos reservados
                asientosReservados.clear();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Reservas());
    }
}
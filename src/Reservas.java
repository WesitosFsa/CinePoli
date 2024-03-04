import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa una ventana de reservas de asientos.
 */
public class Reservas extends JFrame {

    // Color para asientos reservados
    public static final Color COLOR_RESERVADO = new Color(147, 168, 172);

    // Componentes de la interfaz gráfica
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

    /**
     * Constructor de la clase Reservas.
     * Configura la interfaz gráfica y añade listeners a los botones.
     */
    Reservas() {
        super("Reservas");
        setContentPane(panel1);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Inicialización de los botones
        buttons = new JButton[]{
                // Se añaden todos los botones de los asientos al array
                // para facilitar la iteración y manejo posterior
        };

        // Se añade un ActionListener a cada botón de asiento
        for (JButton button : buttons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();
                    if (clickedButton.getBackground().equals(Color.GREEN)) {
                        // Si el asiento está reservado, se ofrece cancelar la reserva
                    } else if (clickedButton.getBackground().equals(Color.RED)) {
                        // Si el asiento está reservado por otro usuario, se muestra un mensaje
                    } else {
                        // Si el asiento está disponible, se ofrece reservarlo
                    }
                }
            });
        }

        // ActionListener para el botón SALIR
        SALIRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al hacer clic en SALIR, se vuelve a la ventana de selección de películas
            }
        });

        // ActionListener para el botón continuarButton
        continuarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al hacer clic en continuarButton, se muestra una pantalla de confirmación
                // y se procede a la siguiente etapa de la reserva
            }
        });
    }

    /**
     * Método principal para iniciar la aplicación.
     * @param args Argumentos de la línea de comandos (no utilizados aquí).
     */
    public static void main(String[] args) {
        // Se inicia la interfaz gráfica en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> new Reservas());
    }
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DATOS extends JFrame{
    private JPanel DATOS;
    private JButton salirButton;
    private JButton reservarAsientoButton;
    private JTextArea textArea1;
    private JTable table1;
    private JTextArea textArea2;

    public DATOS() {
        setContentPane(DATOS);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        reservarAsientoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reservas reserva = new Reservas();
                reserva.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(DATOS);
                frame.dispose();
            }
        });


        // Añade la información al JTextArea1
        textArea1.append("La nueva película de Kimetsu no Yaiba, titulada “Kimetsu no Yaiba - To the Hashira Training”, \nproyectará por primera vez en cines el episodio 11 del Arco de la Aldea de los Herreros, \nmostrando así la conclusión de la feroz batalla entre Tanjiro y la Cuarta Luna Creciente, \nHatengu, además de cómo Nezuko logra caminar bajo el sol.");
        textArea1.append("Le seguirá el \nepisodio 1 del Arco del Entrenamiento de los Pilares, donde veremos el inicio del entrenamiento \nde los Pilares para prepararse de cara a la próxima batalla contra Muzan Kibutsuji.\n");

        // Añade el horario de películas al JTextArea2
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PELICULAS pelis = new PELICULAS();
                pelis.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(DATOS);
                frame.dispose();

            }
        });
    }

}

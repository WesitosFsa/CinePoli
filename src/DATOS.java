import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DATOS extends JFrame{
    private JPanel DATOS;
    private JButton salirButton;
    private JButton reservarAsientoButton;
    private JTextArea textArea1;
    private JTextArea textArea2;

    public DATOS() {
        setContentPane(DATOS);
        setSize(500,500);
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
        textArea2.append("Horario de Reservas:\n\n");
        textArea2.append("Lunes:\n");
        textArea2.append("Sala 1:\n");
        textArea2.append(" - 12:00 PM: Kimetsu no Yaiba - To the Hashira Training - ESPAÑOL\n");
        textArea2.append(" - 3:00 PM: Kimetsu no Yaiba - To the Hashira Training - JAPONES\n");
        textArea2.append(" - 6:00 PM: Kimetsu no Yaiba - To the Hashira Training - INGLES\n");
        textArea2.append("\nMartes:\n");
        textArea2.append("\nSala 2:\n");
        textArea2.append(" - 1:00 PM: Kimetsu no Yaiba - To the Hashira Training - ESPAÑOL\n");
        textArea2.append(" - 4:00 PM: Kimetsu no Yaiba - To the Hashira Training - JAPONES\n");
        textArea2.append(" - 7:00 PM: Kimetsu no Yaiba - To the Hashira Training - INGLES\n");
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

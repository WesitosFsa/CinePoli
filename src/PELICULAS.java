import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**Comentario sobre cartelera
 * imagenes en el programa
 */
public class PELICULAS {
    private JPanel PELICULAS;
    private JLabel PELI1;
    private JLabel PELI2;
    private JLabel PELI3;
    private JLabel PELI4;
    private JLabel PELI5;
    private JLabel PELI6;

    public PELICULAS() {
        /**Peliculas quemadas del momento
         * UwU
         */
        PELI1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mostrarDialogo("Kimetsu no Yaiba - To the Hashira Training", "Director: Haruo Sotozaki\nAño: 2024\nGénero: Animación");
            }
        });
        PELI2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mostrarDialogo("Ferrari", "Director: Michael Mann\nAño: 2023\nGénero: Biografía");
            }
        });
        PELI3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mostrarDialogo("Yo Capitán", "Director: Matteo Garrone\nAño: 2023\nGénero: Drama");
            }
        });
        PELI4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mostrarDialogo("Duna 2", "Director: Denis Villeneuve\nAño: 2024\nGénero: Ciencia ficción");
            }
        });
        PELI5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mostrarDialogo("Vidas Pasadas", "Director: Celine Song\nAño: 2023\nGénero: Drama");
            }
        });
        PELI6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mostrarDialogo("Wonka", "Director: Paul King\nAño: 2024\nGénero: Fantasía musical");
            }
        });

        // Agrega MouseListeners a los demás JLabels de manera similar para mostrar el cuadro de diálogo correspondiente

        JFrame frame = new JFrame("PELICULAS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(PELICULAS);
        frame.pack();
        frame.setVisible(true);
    }

    private void mostrarDialogo(String titulo, String informacion) {
        // Creamos un panel personalizado con la información de la película y el botón "Comprar"
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea texto = new JTextArea(informacion);
        texto.setEditable(false);
        panel.add(texto, BorderLayout.CENTER);

        JButton botonComprar = new JButton("Comprar");
        botonComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acciones cuando se hace clic en el botón comprar
                // Aquí puedes agregar la lógica para la compra de la película
                JOptionPane.showMessageDialog(null, "Compra realizada");
            }
        });
        panel.add(botonComprar, BorderLayout.SOUTH);

        // Mostramos el panel personalizado con la información de la película
        JOptionPane.showMessageDialog(null, panel, titulo, JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {

        new PELICULAS();
    }
}

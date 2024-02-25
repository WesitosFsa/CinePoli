import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PELICULAS extends JFrame {
    private JPanel PELICULAS;
    private JButton verCarteleraButton;
    private JButton cerrarSesionButton;
    private JScrollPane PanelPeliculas;
    private JPanel PeliculasAdentro;
    private JPanel Cartelera;

    public PELICULAS() {
        super("Reservas");
        setContentPane(PELICULAS);
        PeliculasAdentro = new JPanel(new GridLayout(0, 3));
        PanelPeliculas.setViewportView(PeliculasAdentro);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        // Agregar más películas según sea necesario
        verCarteleraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPelicula("Kimetsu no Yaiba", "src/img/uno.png");
                agregarPelicula("Ferrari", "src/img/dos.png");
                agregarPelicula("Los Oscars", "src/img/tres.png");
                agregarPelicula("Dune", "src/img/cuatro.png");
                agregarPelicula("Vida Pasada", "src/img/cinco.png");
                agregarPelicula("Wonka", "src/img/seis.png");
            }
        });
    }
    private void agregarPelicula(String titulo, String rutaImagen) {
        // Crear un panel para la película individual
        JPanel panelIndividual = new JPanel(new BorderLayout());

        // Establecer el color de fondo del panel
        panelIndividual.setBackground(new Color(33, 33, 33)); // #212121 en formato RGB

        // Añadir imagen de la película
        ImageIcon imagen = new ImageIcon(rutaImagen);
        JLabel labelImagen = new JLabel(imagen);
        panelIndividual.add(labelImagen, BorderLayout.CENTER);

        // Crear y configurar el botón para el título de la película
        JButton botonTitulo = new JButton(titulo);
        botonTitulo.setBackground(Color.decode("#0063A5"));  // Color de fondo del botón
        botonTitulo.setForeground(Color.WHITE); // Color del texto
        botonTitulo.setFont(new Font("Century Gothic", Font.BOLD, 11)); // Fuente personalizada

        // Deshabilitar el efecto de resaltado al pasar el mouse sobre el botón
        botonTitulo.setFocusPainted(true);
        botonTitulo.setBorderPainted(true);
        botonTitulo.setContentAreaFilled(true);

        // Establecer el tamaño preferido del botón para que el fondo sea visible
        botonTitulo.setPreferredSize(new Dimension(200, 30)); // Ajusta el tamaño según sea necesario

        // Añadir el botón del título de la película al panel individual
        panelIndividual.add(botonTitulo, BorderLayout.SOUTH); // Agregar el título en la parte inferior

        // Añadir el panel individual al panel principal
        PeliculasAdentro.add(panelIndividual);

        // Actualizar la interfaz gráfica
        revalidate(); // Revalidar el layout del contenedor
        repaint();   // Volver a pintar los componentes
    }




    public static void main(String[] args) {
        new PELICULAS();
    }
}
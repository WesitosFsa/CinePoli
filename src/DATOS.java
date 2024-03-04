import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DATOS extends JFrame {
    private JPanel DATOS;
    private JButton salirButton;
    private JButton reservarAsientoButton;
    private JScrollPane Info_pel;
    private JTextArea sinopsis;
    private JLabel director;
    private JLabel anho;
    private JLabel genero;
    private JLabel titulo;
    private JLabel clasificacion;
    private JLabel labelimagen;
    private JPanel horarios;
    private String nombrePelicula;
    private String generoPelicula;
    private String directorPelicula;
    private String anhoPelicula;
    private ImageIcon imagenPelicula;
    private String sinopses;
    private String clasificacions;
    private Connection conexion;

    public DATOS(String sinopses,String nombrePelicula, String generoPelicula, String directorPelicula, String anhoPelicula, ImageIcon imagenPelicula,String clasificacions){
        this.sinopses = sinopses;
        this.nombrePelicula = nombrePelicula;
        this.generoPelicula = generoPelicula;
        this.directorPelicula = directorPelicula;
        this.anhoPelicula = anhoPelicula;
        this.imagenPelicula = imagenPelicula;
        setContentPane(DATOS);
        setSize(800,500);
        setResizable(false);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        // Establece la política de la barra de desplazamiento vertical aquí
        setVisible(true);
        titulo.setText(nombrePelicula);
        genero.setText(generoPelicula);
        director.setText(directorPelicula);
        anho.setText(anhoPelicula);
        labelimagen.setIcon(imagenPelicula);
        sinopsis.setText(sinopses);
        clasificacion.setText(clasificacions);

        // ActionListener para el botón "Salir"
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PELICULAS pelis = new PELICULAS();
                pelis.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(DATOS);
                frame.dispose();
            }
        });
        // ActionListener para el botón "Reservar Asiento"
        reservarAsientoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reser reser = new Reser();
                reser.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(DATOS);
                frame.dispose();
            }
        });

    }
    private void establecerConexion() {
        try {
            Main conexionbd = new Main();
            conexion = conexionbd.establecerConexion();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

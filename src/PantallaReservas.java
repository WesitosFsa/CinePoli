import javax.swing.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PantallaReservas extends JFrame {

    private JPanel panel1;
    private JButton imprimirButton;
    private JLabel NombreCli;
    private JLabel Correo;
    private JLabel Telefono;
    private JLabel NombrePeli;
    private JLabel Genero;
    private JLabel NumSala;
    private JLabel CantidadAsientos;
    private JLabel NumerodeAsiento;
    private JLabel Horario;
    private JLabel Costo;
    private JButton regresarButton;
    private JLabel cinepoli;
    private Connection conexion;

    PantallaReservas() {
        super("Reservas");
        setContentPane(panel1);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        String name = login.NombreUsuario;
        NombreCli.setText(name);
        establecerConexion();
        String correo = obtenercorreo(name);
        String telefono = obtenertelefono(name);
        Correo.setText(correo);
        Telefono.setText(telefono);
        String namepeli = DATOS.peliculapublica;
        NombrePeli.setText(namepeli);
        String generopeli = DATOS.generopublico;
        Genero.setText(generopeli);



        imprimirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (true) {
                    try {
                        generarPDF();
                        JOptionPane.showMessageDialog(null, "PDF generado exitosamente.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al generar el PDF.");
                    }
                    PELICULAS pelis = new PELICULAS();
                    pelis.setVisible(true);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios.");
                }
            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reservas reserva = new Reservas();
                reserva.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaReservas());
    }

    private void generarPDF() throws Exception {
        Document document = new Document(PageSize.LETTER);
        PdfWriter.getInstance(document, new FileOutputStream("Factura.pdf"));
        document.open();
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

        Paragraph title = new Paragraph("Detalle de la pelicula", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        Reservas reserva = new Reservas();
        double totalPagar = reserva.getTotalPagar();
        String[] asientosReservados = reserva.getAsientosReservados();

        // Agregar detalles al PDF
        document.add(new Paragraph("Nombre Cliente: "));
        document.add(new Paragraph("Correo: "));
        document.add(new Paragraph("Telefono: "));
        document.add(new Paragraph("Nombre Pelicula: "));
        document.add(new Paragraph("Genero: "));
        document.add(new Paragraph("Numero Sala: "));
        document.add(new Paragraph("Cantidad de asientos: "));
        document.add(new Paragraph("Numero de asientos: "));
        document.add(new Paragraph("Horario: "));
        document.add(new Paragraph("Costo: " + totalPagar));

        // Asientos reservados
        StringBuilder asientosStr = new StringBuilder("Asientos seleccionados:\n");
        for (String asiento : asientosReservados) {
            if (asiento != null) {
                asientosStr.append(asiento).append("\n");
            }
        }
        document.add(new Paragraph(asientosStr.toString()));

        document.add(Chunk.NEWLINE);
        document.close();

        // Mostrar el total a pagar y los asientos reservados en los labels correspondientes
;
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
    private String obtenercorreo(String nombreusuario) {
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            // Preparar la consulta SQL con un filtro por nombre de película
            String sql = "SELECT correo FROM clientes WHERE nom_usuario = ?";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, nombreusuario); // Establecer el nombre de la película como parámetro

            // Ejecutar la consulta y obtener el resultado
            resultSet = statement.executeQuery();

            // Verificar si hay resultados
            if (resultSet.next()) {
                // Obtener la sinopsis de la película desde la columna 'sinopsis'
                String correo = resultSet.getString("correo");
                return correo;
            } else {
                // Si no hay resultados, devolver un mensaje indicando que la película no fue encontrada
                return "El usuario '" + nombreusuario + "' no fue encontrada en la base de datos.";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener correo desde la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        } finally {
            // Cerrar recursos
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    private String obtenertelefono(String nombreusuario) {
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            // Preparar la consulta SQL con un filtro por nombre de película
            String sql = "SELECT telf_usuario FROM clientes WHERE nom_usuario = ?";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, nombreusuario); // Establecer el nombre de la película como parámetro

            // Ejecutar la consulta y obtener el resultado
            resultSet = statement.executeQuery();

            // Verificar si hay resultados
            if (resultSet.next()) {
                // Obtener la sinopsis de la película desde la columna 'sinopsis'
                String telefono = resultSet.getString("telf_usuario");
                return telefono;
            } else {
                // Si no hay resultados, devolver un mensaje indicando que la película no fue encontrada
                return "El usuario '" + nombreusuario + "' no fue encontrada en la base de datos.";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el telefono desde la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        } finally {
            // Cerrar recursos
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}




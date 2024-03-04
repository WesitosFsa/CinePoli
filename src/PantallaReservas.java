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
        String horario = Reser.Horapublica;
        Horario.setText(horario);
        String sala = Reser.salapublica;
        NumSala.setText(sala);
        String[] asientosReservados = Reservas.asientospublicos;
        StringBuilder textoAsientos = new StringBuilder();
        for (String asiento : asientosReservados) {
            if (asiento != null) {
                textoAsientos.append(asiento).append(", "); // Puedes cambiar la coma por cualquier otro separador si prefieres
            }
        }
        // Eliminar la última coma y espacio extra si hay asientos reservados
        if (textoAsientos.length() > 0) {
            textoAsientos.setLength(textoAsientos.length() - 2);
        }
        String textoFinal = textoAsientos.toString();
        NumerodeAsiento.setText(textoFinal);
        String dinerito = Reservas.Dineropublico;
        Costo.setText(dinerito);



        imprimirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar ventana emergente para confirmar pago
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea efectuar el pago?",
                        "Confirmar Pago", JOptionPane.YES_NO_OPTION);
                // Utilizar un bucle for para iterar sobre los botones
                for (int i = 0; i < 1; i++) {
                    if (respuesta == JOptionPane.YES_OPTION) {
                        // Imprimir el PDF si se confirma el pago
                        try {
                            generarPDF(name,correo,telefono,namepeli,generopeli,horario,sala,textoFinal,dinerito);
                            JOptionPane.showMessageDialog(null, "PDF generado exitosamente.");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error al generar el PDF.");
                        }
                    } else {
                        // Regresar a la pantalla si se cancela el pago
                        JOptionPane.showMessageDialog(null, "Operación Cancelada.");
                    }
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

    private void generarPDF(String nombre,String correo,String telefono,String nombrepelicula,String Genero,String sala,String nasientos,String Horario,String costo) throws Exception {
        Document document = new Document(PageSize.LETTER);
        PdfWriter.getInstance(document, new FileOutputStream("Factura.pdf"));
        document.open();
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

        Paragraph title = new Paragraph("Detalle de la pelicula", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        Reservas reserva = new Reservas();

        // Agregar detalles al PDF
        document.add(new Paragraph("Nombre Cliente: " + nombre));
        document.add(new Paragraph("Correo: " + correo));
        document.add(new Paragraph("Telefono: " + telefono));
        document.add(new Paragraph("Nombre Pelicula: " + nombrepelicula));
        document.add(new Paragraph("Genero: " + Genero));
        document.add(new Paragraph("Numero Sala: " + sala));
        document.add(new Paragraph("Numero de asientos: " + nasientos));
        document.add(new Paragraph("Horario: " + Horario));
        document.add(new Paragraph("Costo: " + costo));

        // Asientos reservados

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
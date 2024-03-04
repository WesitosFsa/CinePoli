import javax.swing.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;

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

    PantallaReservas() {
        super("Reservas");
        setContentPane(panel1);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        NumerodeAsiento.setText(Reservas.ASIENTOS_RESERVADOS[81]);
        String Totalpago = String.valueOf(Reservas.totalPagar);
        Costo.setText(Totalpago);

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
}




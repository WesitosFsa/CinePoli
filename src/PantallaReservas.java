import javax.swing.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;

/** Clase principal PantallaReservas que extiende de JFrame */
public class PantallaReservas  extends JFrame{

    /** Componentes de la interfaz de usuario*/
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

    /** Constructor de la clase PantallaReservas*/
    PantallaReservas() {
        /** Configuración de la ventana principal*/
        super("Reservas");
        setContentPane(panel1);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

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
        /* Ejecuta la creación de la interfaz de usuario en el hilo de despacho de eventos de Swing*/
        SwingUtilities.invokeLater(() -> new PantallaReservas());
    }
    private static void generarPDF() throws Exception {
        // Tamaño específico del documento (por ejemplo, tamaño carta)
        Document document = new Document(PageSize.LETTER);

        PdfWriter.getInstance(document, new FileOutputStream("Factura.pdf"));
        document.open();

        // Establecer fondo gris
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Factura.pdf"));
        document.open();

        // Contenido del documento
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

        Paragraph title = new Paragraph("Detalle de la pelicula", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("Nombre Cliente: "));
        document.add(new Paragraph("Correo: "));
        document.add(new Paragraph("Telefono: "));
        document.add(new Paragraph("Nombre Pelicula: "));
        document.add(new Paragraph("Genero: "));
        document.add(new Paragraph("Numero Sala: "));
        document.add(new Paragraph("Cantidad de asientos: "));
        document.add(new Paragraph("Numero de asientos: "));
        document.add(new Paragraph("Horario: "));
        document.add(new Paragraph("Costo: "));
        document.add(Chunk.NEWLINE);


        document.close();
    }

}

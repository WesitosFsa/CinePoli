import javax.swing.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
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
    /** Constructor de la clase PantallaReservas*/
    PantallaReservas() {
        /** Configuración de la ventana principal*/
        super("Reservas");
        setContentPane(panel1);
        setSize(500, 500);
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
                        } else {
                            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios.");
                        }
                    }
                });
            }
    public static void main(String[] args) {
        /* Ejecuta la creación de la interfaz de usuario en el hilo de despacho de eventos de Swing*/
        SwingUtilities.invokeLater(() -> new PantallaReservas());
    }
    private void generarPDF() throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Factura.pdf"));
        document.open();

        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font fontGreen = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        fontGreen.setColor(0, 128, 0); // Verde
        Font fontYellow = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        fontYellow.setColor(255, 255, 0); // Amarillo
        Font fontOrange = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        fontOrange.setColor(255, 165, 0); // Anaranjado

        document.add(new Paragraph("FACTURA", fontHeader));
        document.add(new Paragraph("TEST", fontHeader));
        document.add(Chunk.NEWLINE);
        document.close();
    }




}

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Clase principal Estadistica que extiende de JFrame*/
public class Estadistica extends JFrame{
    /**Componentes de la interfaz de usuario */
    private JPanel panel1;
    private JButton ventas;
    private JButton salirButton;
    private JButton salasOcupadasButton;
    private JButton menuButton;
    private JPanel Estadisticas;

    /**Constructor de la clase Estadistica */
    Estadistica() {
        /**Config*uración de la ventana principal*/
        super("Estadisticas");
        setContentPane(panel1);
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        /**Acción cuando se hace clic en el botón "ventas"*/
        ventas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**Datos de ejemplo para las ventas*/
                int n1 = 8;
                int n2 = 10;
                int n3 = 15;
                /**Creación de un conjunto de datos*/
                DefaultCategoryDataset datos = new DefaultCategoryDataset();
                datos.setValue(n1,"Bajas","Junio");
                datos.setValue(n2,"Medias","Abril");
                datos.setValue(n3,"Altas","Mayo");
                /**Creación del gráfico de barras*/
                JFreeChart grafico_barras = ChartFactory.createBarChart3D(
                        "Estadisticas de Ventas de Este Mes",   //nombre del grafico
                        "Ventas por mes",//nombre de las barras o columnas
                        "N Ventas",   //nombre de la numeracion
                        datos,   //datos del grafico
                        PlotOrientation.VERTICAL,   //orientacion
                        true,  //legenda de barra individuales por color
                        true, // herramientas
                        false //url del grafico
                );
                /**Elimina todos los componentes del panel Estadisticas*/
                Estadisticas.removeAll();
                /**Añade el gráfico al panel Estadisticas*/
                ChartPanel panel = new ChartPanel(grafico_barras);
                panel.setMouseWheelEnabled(true);
                panel.setPreferredSize(new Dimension(500,400));
                Estadisticas.setLayout(new BorderLayout());
                Estadisticas.add(panel);
                pack();
                repaint();
            }
        });

        /**Acción cuando se hace clic en el botón "salasOcupadasButton" */
        salasOcupadasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**Datos de ejemplo para las salas ocupadas */
                int n1 = 8;
                int n2 = 10;
                int n3 = 15;
                /**Creación de un conjunto de datos*/
                DefaultCategoryDataset datos = new DefaultCategoryDataset();
                datos.setValue(n1,"Bajas","Sala1");
                datos.setValue(n2,"Medias","Sala2");
                datos.setValue(n3,"Altas","Sala3");
                /**Creación del gráfico de barras*/
                JFreeChart grafico_barras = ChartFactory.createBarChart3D(
                        "Estadisticas de Salas mas ocupadas por Mes",   //nombre del grafico
                        "Salas ocupadas",//nombre de las barras o columnas
                        "N Personas",   //nombre de la numeracion
                        datos,   //datos del grafico
                        PlotOrientation.VERTICAL,   //orientacion
                        true,  //legenda de barra individuales por color
                        true, // herramientas
                        false //url del grafico
                );
                /**Elimina todos los componentes del panel Estadisticas*/
                Estadisticas.removeAll();
                /**Añade el gráfico al panel Estadisticas*/
                ChartPanel panel = new ChartPanel(grafico_barras);
                panel.setMouseWheelEnabled(true);
                panel.setPreferredSize(new Dimension(500,400));
                Estadisticas.setLayout(new BorderLayout());
                Estadisticas.add(panel);
                pack();
                repaint();
            }
        });

        /**Acción cuando se hace clic en el botón "salirButton" */
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**Muestra un diálogo de confirmación para salir de la aplicación*/
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Quiere salir de la app?",
                        "Salir", JOptionPane.YES_NO_OPTION);
                try {
                    if (respuesta == JOptionPane.YES_OPTION) {
                        /**Si se confirma la salida, cierra la ventana y termina la aplicación*/
                        dispose();
                        System.exit(0);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });
    }

    // Método principal para iniciar la aplicación
    public static void main(String[] args) {
        // Ejecuta la creación de la interfaz de usuario en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> new Estadistica());
    }
}

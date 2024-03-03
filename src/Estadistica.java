import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


/** Clase principal Estadistica que extiende de JFrame*/
public class Estadistica extends JFrame{
    /**Componentes de la interfaz de usuario */
    private JPanel panel1;
    private JButton ventas;
    private JButton salasOcupadasButton;
    private JButton menuButton;
    private JPanel Estadisticas;
    private Connection conexion;
    private java.util.List<String> nombrePeliculas = new ArrayList<>();
    private java.util.List<Integer> costoBoletos = new ArrayList<>();

    private java.util.List<String> horarios = new ArrayList<>();
    private java.util.List<Integer> costoBoletos2 = new ArrayList<>();


    /**Constructor de la clase Estadistica */
    Estadistica() {

        /**Configuración de la ventana principal*/
        super("Estadisticas");
        setContentPane(panel1);
        setSize(800,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        establecerConexion();
        /**Acción cuando se hace clic en el botón "ventas"*/
        ventas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarventas();

                // Crear un mapa para almacenar el total de ventas por película
                Map<String, Integer> totalVentasPorPelicula = new HashMap<>();

                // Iterar sobre los datos de ventas y sumar los costos por película
                for (int i = 0; i < nombrePeliculas.size(); i++) {
                    String nombrePelicula = nombrePeliculas.get(i);
                    int costoBoleto = costoBoletos.get(i);

                    // Si la película ya está en el mapa, sumar el costo
                    if (totalVentasPorPelicula.containsKey(nombrePelicula)) {
                        int totalCosto = totalVentasPorPelicula.get(nombrePelicula);
                        totalCosto += costoBoleto;
                        totalVentasPorPelicula.put(nombrePelicula, totalCosto);
                    } else {
                        // Si es la primera vez que se encuentra esta película, agregarla al mapa con su costo inicial
                        totalVentasPorPelicula.put(nombrePelicula, costoBoleto);
                    }
                }

                // Crear un conjunto de datos con los totales de ventas por película
                DefaultCategoryDataset datos = new DefaultCategoryDataset();

                for (Map.Entry<String, Integer> entry : totalVentasPorPelicula.entrySet()) {
                    String nombrePelicula = entry.getKey();
                    int totalCosto = entry.getValue();
                    datos.addValue(totalCosto, nombrePelicula, "Este Mes");
                }
                // Crear el gráfico de barras con los datos acumulados
                JFreeChart grafico_barras = ChartFactory.createBarChart3D(
                        "Estadisticas de Ganancias por pelicula",
                        "Pelicula",
                        "Total Ventas",
                        datos,
                        PlotOrientation.VERTICAL,
                        true,
                        true,
                        false
                );

                // Limpiar el panel de estadísticas y añadir el nuevo gráfico
                Estadisticas.removeAll();
                ChartPanel panel = new ChartPanel(grafico_barras);
                panel.setMouseWheelEnabled(true);
                panel.setPreferredSize(new Dimension(800, 400));
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
                cargarpersonas();

                // Crear un mapa para almacenar el total de ventas por película
                Map<String, Integer> totalVentasPorPelicula = new HashMap<>();

                // Iterar sobre los datos de ventas y sumar los costos por película
                for (int i = 0; i < horarios.size(); i++) {
                    String horario = horarios.get(i);
                    int costoBoletodos = costoBoletos2.get(i);

                    // Si la película ya está en el mapa, sumar el costo
                    if (totalVentasPorPelicula.containsKey(horario)) {
                        int totalCosto = totalVentasPorPelicula.get(horario);
                        totalCosto += costoBoletodos;
                        totalVentasPorPelicula.put(horario, totalCosto);
                    } else {
                        // Si es la primera vez que se encuentra esta película, agregarla al mapa con su costo inicial
                        totalVentasPorPelicula.put(horario, costoBoletodos);
                    }
                }

                // Crear un conjunto de datos con los totales de ventas por película
                DefaultCategoryDataset datos = new DefaultCategoryDataset();

                for (Map.Entry<String, Integer> entry : totalVentasPorPelicula.entrySet()) {
                    String nombrePelicula = entry.getKey();
                    int totalCosto = entry.getValue();
                    datos.addValue(totalCosto, nombrePelicula, "Este Mes");
                }
                // Crear el gráfico de barras con los datos acumulados
                JFreeChart grafico_barras = ChartFactory.createBarChart3D(
                        "Horario Pico de Ganancias",
                        "Horario",
                        "Total Ventas",
                        datos,
                        PlotOrientation.VERTICAL,
                        true,
                        true,
                        false
                );

                // Limpiar el panel de estadísticas y añadir el nuevo gráfico
                Estadisticas.removeAll();
                ChartPanel panel = new ChartPanel(grafico_barras);
                panel.setMouseWheelEnabled(true);
                panel.setPreferredSize(new Dimension(800, 400));
                Estadisticas.setLayout(new BorderLayout());
                Estadisticas.add(panel);
                pack();
                repaint();
            }
        });

        /**Acción cuando se hace clic en el botón "salirButton" */
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ADMIN Admin = new ADMIN();
                Admin.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.dispose();
            }
        });

    }

    // Método principal para iniciar la aplicación
    public static void main(String[] args) {
        // Ejecuta la creación de la interfaz de usuario en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> new Estadistica());
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
    private void cargarventas() {
        try {
            String sql = "select nom_pelicula, costo_boleto from factura";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Limpiar los datos anteriores, si los hubiera
            nombrePeliculas.clear();
            costoBoletos.clear();

            while (resultSet.next()) {
                String nombrePeli = resultSet.getString("nom_pelicula");
                int costoBoleto = resultSet.getInt("costo_boleto");

                // Guardar los datos en las listas
                nombrePeliculas.add(nombrePeli);
                costoBoletos.add(costoBoleto);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar películas desde la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void cargarpersonas() {
        try {
            String sql = "select horario, costo_boleto from factura";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Limpiar los datos anteriores, si los hubiera
            horarios.clear();
            costoBoletos2.clear();

            while (resultSet.next()) {
                String horario = resultSet.getString("horario");
                int costoBoleto = resultSet.getInt("costo_boleto");

                // Guardar los datos en las listas
                horarios.add(horario);
                costoBoletos2.add(costoBoleto);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar películas desde la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
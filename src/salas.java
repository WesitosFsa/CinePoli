import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Clase que representa la interfaz gráfica para administrar información de salas y proyecciones de películas.
 */
public class salas extends JFrame {
    private JPanel panel1;
    private JButton ingresarButton;
    private JButton verInformacionButton;
    private JButton menuButton;
    private JLabel NUMSALA;
    private JLabel IDPELICULA;
    private JLabel ASIENTOS;
    private JLabel asientos;
    private JLabel horario;
    private JLabel nombrePeli;
    private JLabel HORARIO;
    private JLabel Sala;
    private JLabel DIA;
    private JLabel Dia;
    private JLabel NumSala;
    private JLabel numSala;
    private JTextField textFieldNUMSALAS;
    private JTextField textFieldDIA;
    private JTextField textFieldIDPELICULA;
    private JTextField textFieldHORARIO;
    private JTextField mostrarPelicula;
    private JTextField mostrarHorario;
    private JTextField mostrarNumsala;
    private JTextField mostrarDia;
    private JTextField numsala;

    private List<verpelis.Pelicula> listaPeliculas;
    private Map<Integer, SalasInfo> salas;

    private Connection conexion;
    /**
     * Constructor de la clase Salas que inicializa la interfaz gráfica y establece la conexión a la base de datos.
     */
    public salas() {
        /*Configuracion de la pantalla Salas*/
        setContentPane(panel1);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        // Inicialización de variables
        salas = new HashMap<>();
        establecerConexion();

        // Configuración de ActionListener para el botón "Ingresar"
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idPelicula = Integer.parseInt(textFieldIDPELICULA.getText());
                    String horario = textFieldHORARIO.getText();
                    int numSala = Integer.parseInt(textFieldNUMSALAS.getText());
                    String dia = textFieldDIA.getText();

                    // Verificar si la sala ya existe en el mapa de salas
                    if (salas.containsKey(numSala)) {
                        // Si la sala ya existe, actualizar los datos en lugar de insertar una nueva fila
                        SalasInfo salaExistente = salas.get(numSala);
                        salaExistente.actualizarInfo(idPelicula, horario, dia);
                        // También podrías actualizar la información en la base de datos aquí
                    } else {
                        // Si la sala no existe, agregarla al mapa de salas y a la base de datos
                        SalasInfo nuevaSala = new SalasInfo(idPelicula, horario, numSala, dia);
                        salas.put(numSala, nuevaSala);
                        // Insertar información en la base de datos
                        nuevaSala.insertarSalaEnBD();
                    }

                    // Limpia los campos después de ingresar la información
                    limpiarCampos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingrese números válidos para la ID de la película y el número de sala.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // Configuración de ActionListener para el botón "Ver Información"
        verInformacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idPelicula = Integer.parseInt(textFieldIDPELICULA.getText()); // Se obtiene la ID de la película desde el campo textFieldIDPELICULA

                    String Nombrepeli = obtenerNombrePelicula(idPelicula);
                    String Horarios = obtenerHorario(idPelicula);
                    String Dia = obtenerDia(idPelicula);
                    String Numsala = obtenerNumsala(idPelicula);
                    mostrarPelicula.setText(Nombrepeli);
                    mostrarHorario.setText(Horarios);
                    mostrarDia.setText(Dia);
                    mostrarNumsala.setText(Numsala);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingrese un número válido para la ID de la película.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Configuración de ActionListener para el botón "Menu"
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ADMIN admin = new ADMIN();
                admin.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.dispose();
            }
        });

        // Cargar las salas desde la base de datos al iniciar la aplicación
        cargarSalasDesdeBD();
    }
    /**
     * Establece la conexión con la base de datos.
     */
    private void establecerConexion() {
        try {
            // Establecer la conexión con la base de datos
            conexion = Main.establecerConexion();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Limpia los campos de entrada de información.
     */
    private void limpiarCampos() {
        textFieldIDPELICULA.setText("");
        textFieldHORARIO.setText("");
        textFieldNUMSALAS.setText("");
        textFieldDIA.setText("");
    }

    // Método para obtener el nombre de la película según el ID
    private String obtenerNombrePelicula(int idPelicula) {
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            // Preparar la consulta SQL con un filtro por nombre de película
            String sql = "SELECT nombre_pelicula FROM peliculas WHERE id_pelicula = ?";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, idPelicula); // Establecer el ID DE PELICULA COMO PARAMETRO

            // Ejecutar la consulta y obtener el resultado
            resultSet = statement.executeQuery();

            // Verificar si hay resultados
            if (resultSet.next()) {
                // Obtener la sinopsis de la película desde la columna 'sinopsis'
                String nombrepelicula = resultSet.getString("nombre_pelicula");
                return nombrepelicula;
            } else {
                // Si no hay resultados, devolver un mensaje indicando que la película no fue encontrada
                return "El '" + idPelicula + "' no fue encontrada en la base de datos.";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener información de la película desde la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
    private String obtenerHorario(int idPelicula) {
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            // Preparar la consulta SQL con un filtro por nombre de película
            String sql = "SELECT Horario_Sala FROM sala WHERE id_pelicula = ?";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, idPelicula); // Establecer el ID DE PELICULA COMO PARAMETRO

            // Ejecutar la consulta y obtener el resultado
            resultSet = statement.executeQuery();

            // Verificar si hay resultados
            if (resultSet.next()) {
                // Obtener la sinopsis de la película desde la columna 'sinopsis'
                String horario = resultSet.getString("Horario_Sala");
                return horario;
            } else {
                // Si no hay resultados, devolver un mensaje indicando que la película no fue encontrada
                return "El '" + idPelicula + "' no fue encontrada en la base de datos.";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener información de la película desde la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

    private String obtenerDia(int idPelicula) {
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            // Preparar la consulta SQL con un filtro por nombre de película
            String sql = "SELECT Dia FROM sala WHERE id_pelicula = ?";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, idPelicula); // Establecer el ID DE PELICULA COMO PARAMETRO

            // Ejecutar la consulta y obtener el resultado
            resultSet = statement.executeQuery();

            // Verificar si hay resultados
            if (resultSet.next()) {
                // Obtener la sinopsis de la película desde la columna 'sinopsis'
                String Dia = resultSet.getString("Dia");
                return Dia;
            } else {
                // Si no hay resultados, devolver un mensaje indicando que la película no fue encontrada
                return "El '" + idPelicula + "' no fue encontrada en la base de datos.";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener información de la película desde la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
    private String obtenerNumsala(int idPelicula) {
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            // Preparar la consulta SQL con un filtro por nombre de película
            String sql = "SELECT Num_Sala FROM sala WHERE id_pelicula = ?";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, idPelicula); // Establecer el ID DE PELICULA COMO PARAMETRO

            // Ejecutar la consulta y obtener el resultado
            resultSet = statement.executeQuery();

            // Verificar si hay resultados
            if (resultSet.next()) {
                // Obtener la sinopsis de la película desde la columna 'sinopsis'
                String Numsala = resultSet.getString("Num_Sala");
                return Numsala;
            } else {
                // Si no hay resultados, devolver un mensaje indicando que la película no fue encontrada
                return "El '" + idPelicula + "' no fue encontrada en la base de datos.";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener información de la película desde la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

    // Método para cargar las salas desde la base de datos al iniciar la aplicación
    private void cargarSalasDesdeBD() {
        try {
            String query = "SELECT * FROM sala";
            PreparedStatement statement = conexion.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idPelicula = resultSet.getInt("id_pelicula");
                String horario = resultSet.getString("Horario_Sala");
                int numSala = resultSet.getInt("Num_Sala");
                String dia = resultSet.getString("Dia");

                // Agregar la sala al mapa de salas
                SalasInfo sala = new SalasInfo(idPelicula, horario, numSala, dia);
                salas.put(numSala, sala);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar las salas desde la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Clase interna que representa la información de una sala.
     */
    class SalasInfo {
        private int idPelicula;
        private String horario;
        private int numSala;
        private String dia;

        /**
         * Constructor de la clase SalasInfo.
         * @param idPelicula ID de la película asociada a la sala.
         * @param horario Horario de proyección en la sala.
         * @param numSala Número de la sala.
         * @param dia Día de la proyección en la sala.
         */
        public SalasInfo(int idPelicula, String horario, int numSala, String dia) {
            this.idPelicula = idPelicula;
            this.horario = horario;
            this.numSala = numSala;
            this.dia = dia;
        }


        /**
         * Inserta la información de la sala en la base de datos.
         */
        private void insertarSalaEnBD() {
            try {
                String query = "INSERT INTO sala (id_pelicula, Horario_Sala, Num_Sala, Dia) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = conexion.prepareStatement(query);
                statement.setInt(1, idPelicula);
                statement.setString(2, horario);
                statement.setInt(3, numSala);
                statement.setString(4, dia);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al insertar la sala en la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Métodos getter para obtener información de la sala
        public int getIdPelicula() {
            return idPelicula;
        }

        public String getHorario() {
            return horario;
        }

        public int getNumSala() {
            return numSala;
        }

        public String getDia() {
            return dia;
        }


        /**
         * Actualiza la información de la sala.
         * @param idPelicula Nueva ID de la película asociada a la sala.
         * @param horario Nuevo horario de proyección en la sala.
         * @param dia Nuevo día de la proyección en la sala.
         */
        public void actualizarInfo(int idPelicula, String horario, String dia) {
            mostrarPelicula.setText(obtenerNombrePelicula(idPelicula));
            mostrarHorario.setText(horario);
            mostrarDia.setText(dia);
        }

    }
}
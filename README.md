Base de datos en la nube  (debe estar en main)

Connection establecerConexion() throws SQLException {
        String url = "jdbc:mysql://ukghiar85gp7mrpy:nQVmOkgbY4UHYZybHvO2@bxrwabtu14qddifcwky1-mysql.services.clever-cloud.com:3306/bxrwabtu14qddifcwky1";
        String usuarioDB = "ukghiar85gp7mrpy";
        String contraseniaDB = "nQVmOkgbY4UHYZybHvO2";
        return DriverManager.getConnection(url, usuarioDB, contraseniaDB);
    }

 public void conexion_base() {
        try {
            Connection conexion = establecerConexion();
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

Instanciar la conexion en otra pantalla

Main main = new Main();
main.conexion_base();

package AccessLifeV2.Persistencia;

import java.sql.DriverManager;

/**
 *
 * @author estdi
 */
public class AccesoDatosJDBCPostgreSQL extends AccesoDatosJDBC {

    @Override
    public void abrirConexion() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/AccessV2";
            conexion = DriverManager.getConnection(url, "postgres", "krhizpostgresql");
        } catch (Exception e) {
            throw new Exception("Ocurrio un problema en la conexión con la base de datos.", e);
        }
    }
}

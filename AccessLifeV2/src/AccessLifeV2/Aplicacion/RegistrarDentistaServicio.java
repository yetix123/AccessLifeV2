/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccessLifeV2.Aplicacion;

import AccessLifeV2.Dominio.Dentista;
import AccessLifeV2.Persistencia.AccesoDatosJDBC;
import AccessLifeV2.Persistencia.AccesoDatosJDBCPostgreSQL;
import AccessLifeV2.Persistencia.DentistaPostgreSQL;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author estdi
 */
public class RegistrarDentistaServicio {
    
    private AccesoDatosJDBC accesoDatosJDBC;
    private DentistaPostgreSQL dentistaPostgreSQL;

    public RegistrarDentistaServicio() {
        accesoDatosJDBC = new AccesoDatosJDBCPostgreSQL();
        dentistaPostgreSQL = new DentistaPostgreSQL(accesoDatosJDBC);        
    }
    
    public int IncremetID() throws Exception{
        int id;
        accesoDatosJDBC.abrirConexion();
        accesoDatosJDBC.iniciarTransaccion();
        id = dentistaPostgreSQL.IdIncrement();
        accesoDatosJDBC.terminarTransaccion();
        return id;
    }

    public void MostraDentista(DefaultTableModel modelo) throws Exception {
        accesoDatosJDBC.abrirConexion();
        accesoDatosJDBC.iniciarTransaccion();
        dentistaPostgreSQL.MostrarDentista(modelo);
        accesoDatosJDBC.terminarTransaccion();
    }
    
    public Dentista buscarDentista(String nombres) throws Exception {
        accesoDatosJDBC.abrirConexion();
        Dentista dentista = dentistaPostgreSQL.buscar(nombres);
        accesoDatosJDBC.cerrarConexion();
        return dentista;
    }    
    
    public void guardarDentista(Dentista dentista) throws Exception {    
        accesoDatosJDBC.abrirConexion();
        accesoDatosJDBC.iniciarTransaccion();
        dentistaPostgreSQL.guardar(dentista);
        accesoDatosJDBC.terminarTransaccion();                
    }
    
}

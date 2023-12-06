/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccessLifeV2.Aplicacion;

import AccessLifeV2.Dominio.Paciente;
import AccessLifeV2.Persistencia.AccesoDatosJDBC;
import AccessLifeV2.Persistencia.AccesoDatosJDBCPostgreSQL;
import AccessLifeV2.Persistencia.PacientePostgreSQL;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author estdi
 */
public class RegistrarPacienteServicio {
    
    private AccesoDatosJDBC accesoDatosJDBC;
    private PacientePostgreSQL pacientePostgreSQL;

    public RegistrarPacienteServicio() {
        accesoDatosJDBC = new AccesoDatosJDBCPostgreSQL();
        pacientePostgreSQL = new PacientePostgreSQL(accesoDatosJDBC);        
    }
    
     public int IncremetID() throws Exception{
        int id;
        accesoDatosJDBC.abrirConexion();
        accesoDatosJDBC.iniciarTransaccion();
        id = pacientePostgreSQL.IdIncrement();
        accesoDatosJDBC.terminarTransaccion();
        return id;
    }

    public void MostraPaciente(DefaultTableModel modelo) throws Exception {
        accesoDatosJDBC.abrirConexion();
        accesoDatosJDBC.iniciarTransaccion();
        pacientePostgreSQL.MostrarPaciente(modelo);
        accesoDatosJDBC.terminarTransaccion();
    }
    
    public void guardarPaciente(Paciente paciente) throws Exception {
        //----- VALIDAMOS REGLAS DE NEGOCIO -----
//        if(!paciente.verificarDniExistente()){
//            JOptionPane.showMessageDialog(null, "EL PACIENTE YA EXISTE, POR FAVOR VUELVA INGRESAR UN NUEVO DNI", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
//            throw new Exception("EL PACIENTE YA EXISTE, POR FAVOR VUELVA INGRESAR UN NUEVO DNI");
//        }
        accesoDatosJDBC.abrirConexion();
        accesoDatosJDBC.iniciarTransaccion();
        pacientePostgreSQL.guardar(paciente);
        accesoDatosJDBC.terminarTransaccion();
                
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccessLifeV2.Persistencia;

import AccessLifeV2.Dominio.Paciente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author estdi
 */
public class PacientePostgreSQL {
    
    private AccesoDatosJDBC accesoDatosJDBC;

    public PacientePostgreSQL(AccesoDatosJDBC accesoDatosJDBC) {
        this.accesoDatosJDBC = accesoDatosJDBC;
    }
    
    public int IdIncrement(){
        int id = 1;
        String consultaSQL = "SELECT MAX(idpaciente) FROM paciente";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            sentencia = accesoDatosJDBC.prepararSentencia(consultaSQL);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                id = resultado.getInt(1) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER ULTIMO ID DE PACIENTE", "ERROR", JOptionPane.ERROR_MESSAGE);
        } 
        return id;
    }
    
    public void guardar(Paciente paciente) throws Exception {
        String insertSQL = "Insert Into paciente(nombres, dni, edad, sexo, telefono, email) " +
                            "values(?, ?, ?, ?, ?, ?)";
        PreparedStatement sentencia;
        try{
            sentencia = accesoDatosJDBC.prepararSentencia(insertSQL);
            sentencia.setString(1, paciente.getNombres());
            sentencia.setString(2, paciente.getDni());
            sentencia.setInt(3, paciente.getEdad());
            sentencia.setString(4, paciente.getSexo());
            sentencia.setString(5, paciente.getTelefono());
            sentencia.setString(6, paciente.getEmail());
            sentencia.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("ERROR AL INTENTAR GUARDAR PACIENTE", e);
        }
     
    }
    
    public Paciente buscar(String dni) throws Exception {
        String consultaSQL = "select idpaciente, nombres, edad, sexo, telefono, email from paciente where dni = ?";
        PreparedStatement sentencia;
        try {
            sentencia = accesoDatosJDBC.prepararSentencia(consultaSQL);
            sentencia.setString(1, dni);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                Paciente paciente = new Paciente();
                paciente.setDni(dni);
                paciente.setIdpaciente(resultado.getInt("idpaciente"));
                paciente.setNombres(resultado.getString("nombres"));
                paciente.setEdad(resultado.getInt("edad"));
                paciente.setSexo(resultado.getString("sexo"));
                paciente.setTelefono(resultado.getString("telefono"));
                paciente.setEmail(resultado.getString("email"));
                return paciente;
            }
            else {
                throw new Exception("No existe el Paciente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("ERROR AL INTENTAR BUSCAR EL PACIENTE. ", e);
        }
    }
    
    public void MostrarPaciente(DefaultTableModel modelo) throws Exception {

        String mostraSQL = "select * from paciente";
        PreparedStatement sentencia = null;

        String titulos[] = {"ID", "NOMBRES", "DNI", "EDAD", "SEXO", "TELEFONO", "CORREO"};
        modelo.getDataVector().removeAllElements();
        modelo.setColumnIdentifiers(titulos);

        try {
            sentencia = accesoDatosJDBC.prepararSentencia(mostraSQL);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                String id = resultado.getString("Idpaciente");
                String nombres = resultado.getString("nombres");
                String dni = resultado.getString("dni");
                String edad = resultado.getString("edad");
                String sexo = resultado.getString("sexo");
                String correo = resultado.getString("telefono");
                String telefono = resultado.getString("email");

                String fila[] = {id, nombres, dni, edad, sexo, correo, telefono};
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("ERROR AL INTENTAR MOSTRAR PACIENTES.", e);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccessLifeV2.Persistencia;

import AccessLifeV2.Dominio.Dentista;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author estdi
 */
public class DentistaPostgreSQL {

    private AccesoDatosJDBC accesoDatosJDBC;

    public DentistaPostgreSQL(AccesoDatosJDBC accesoDatosJDBC) {
        this.accesoDatosJDBC = accesoDatosJDBC;
    }

    public int IdIncrement() {
        String consultaSQL = "SELECT MAX(iddentista) FROM dentista";
        PreparedStatement sentencia;
        int id = 1;
        try {
            sentencia = accesoDatosJDBC.prepararSentencia(consultaSQL);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                id = resultado.getInt(1) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER ULTIMO ID DEL DENTISTA", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return id;
    } 

    public void guardar(Dentista dentista) throws Exception {
        String insertSQL = "INSERT INTO dentista(nombres, dni, dialaboral, horario, estado, telefono, email)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?) ";
        PreparedStatement sentencia;
        try {
            sentencia = accesoDatosJDBC.prepararSentencia(insertSQL);
            sentencia.setString(1, dentista.getNombres());
            sentencia.setString(2, dentista.getDni());
            sentencia.setString(3, dentista.getDialaboral());
            sentencia.setString(4, dentista.getHorario());
            sentencia.setString(5, dentista.getEstado());
            sentencia.setString(6, dentista.getTelefono());
            sentencia.setString(7, dentista.getEmail());
            sentencia.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("ERROR AL GUARDAR DENTISTA", e);
        }
    }

    public Dentista buscar(String nombre) throws Exception {
        String consultaSQL = " select iddentista, dni, dialaboral, horario, estado, telefono, email from dentista where nombres = ?";
        PreparedStatement sentencia;
        try {
            sentencia = accesoDatosJDBC.prepararSentencia(consultaSQL);
            sentencia.setString(1, nombre);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                Dentista dentista = new Dentista();
                dentista.setNombres(nombre);
                dentista.setIddentista(resultado.getInt("iddentista"));
                dentista.setDni(resultado.getString("dni"));
                dentista.setDialaboral(resultado.getString("dialaboral"));
                dentista.setHorario(resultado.getString("horario"));
                dentista.setEstado(resultado.getString("estado"));
                dentista.setTelefono(resultado.getString("telefono"));
                dentista.setEmail(resultado.getString("email"));
                return dentista;
            } else {
                throw new Exception("NO EXISTE EL DENTISTA.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("ERROR AL INTENTAR BUSCAR EL DENTISTA.", e);
        }
    }

    public void MostrarDentista(DefaultTableModel modelo) throws Exception {

        String mostraSQL = "select * from dentista";
        PreparedStatement sentencia = null;

        String titulos[] = {"ID",  "NOMBRES", "DNI", "DIA LABORAL", "HORARIO", "ESTADO", "TELEFONO", "CORREO"};
        modelo.getDataVector().removeAllElements();
        modelo.setColumnIdentifiers(titulos);

        try {
            sentencia = accesoDatosJDBC.prepararSentencia(mostraSQL);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                String id = resultado.getString("iddentista");
                String nombres = resultado.getString("nombres");
                String dni = resultado.getString("dni");
                String dialab = resultado.getString("dialaboral");
                String horario = resultado.getString("horario");
                String estado = resultado.getString("estado");
                String telefono = resultado.getString("telefono");
                String correo = resultado.getString("email");

                String fila[] = {id, nombres, dni, dialab, horario, estado, telefono, correo};
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("ERROR AL INTENTAR MOSTRAR DENTISTAS.", e);
        }
    }

}

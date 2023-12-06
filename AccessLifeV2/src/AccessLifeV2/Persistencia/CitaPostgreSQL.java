/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccessLifeV2.Persistencia;

import AccessLifeV2.Dominio.Dentista;
import AccessLifeV2.Dominio.Cita;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author estdi
 */
public class CitaPostgreSQL {

    private AccesoDatosJDBC accesoDatosJDBC;

    public CitaPostgreSQL(AccesoDatosJDBC accesoDatosJDBC) {
        this.accesoDatosJDBC = accesoDatosJDBC;
    }

    public int IdIncrement() throws Exception {
        String consultaSQL = "SELECT MAX(idcita) FROM citas;";
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
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER ULTIMO ID DE CITA", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return id;
    }

    public void guardar(Cita cita) throws Exception {

        String insertSQL = "insert into citas(dentista, paciente, fecha, hora, estado, servicio, detallecita, pagototal)"
                + "values(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement sentencia;
        try {
            sentencia = accesoDatosJDBC.prepararSentencia(insertSQL);
            sentencia.setInt(1, cita.getDentista().getIddentista());
            sentencia.setInt(2, cita.getPaciente().getIdpaciente());
            sentencia.setString(3, cita.getFecha());
            sentencia.setString(4, cita.getHora());
            sentencia.setString(5, cita.getEstado());
            sentencia.setString(6, cita.getServicio());
            sentencia.setString(7, cita.getDetallecita());
            sentencia.setDouble(8, cita.getPagototal());
            sentencia.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("ERROR AL INTENTAR GUARDAR LA CITA.", e);
        }
    }

    public int consultarTotalDeCitas(Dentista dentista) throws Exception {
        String consultaSQL = "SELECT COUNT(idcita) AS total FROM citas WHERE dentista = ?";
        PreparedStatement sentencia;
        int totalCitas = 0;
        try {
            sentencia = accesoDatosJDBC.prepararSentencia(consultaSQL);
            sentencia.setInt(1, dentista.getIddentista());
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                totalCitas = resultado.getInt("total");
            }
            return totalCitas;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("ERROR AL INTENTAR CONSULTAR EL TOTAL DE CITAS.", e);
        }
    }

    public void MostrarCitas(DefaultTableModel modelo) throws Exception {

        String mostraSQL = "SELECT * FROM citas";
        PreparedStatement sentencia = null;

        String titulos[] = {"ID", "DENTISTA", "PACIENTE", "FECHA", "HORA", "ESTADO", "SERVICIO", "DETALLE", "PAGO TOTAL"};
        modelo.getDataVector().removeAllElements();
        modelo.setColumnIdentifiers(titulos);

        try {
            sentencia = accesoDatosJDBC.prepararSentencia(mostraSQL);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                String id = resultado.getString("idcita");
                String dentista = resultado.getString("dentista");
                String paciente = resultado.getString("paciente");
                String horario = resultado.getString("fecha");
                String servicio = resultado.getString("hora");
                String fcita = resultado.getString("estado");
                String hora = resultado.getString("servicio");
                String estado = resultado.getString("detallecita");
                String pagot = resultado.getString("pagototal");
                String fila[] = {id, dentista, paciente, horario, servicio, fcita, hora, estado, pagot};
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("ERROR AL INTENTAR MOSTRAR CITAS.", e);
        }
    }

    public void cambiarEstado(Cita cita) {
        
        String updateSQL = "UPDATE citas SET estado = 'RESERVADO' WHERE hora = ? AND fecha = ?";
        PreparedStatement sentencia;
        try {
//             Cambiar estado en la tabla cita
            sentencia = accesoDatosJDBC.prepararSentencia(updateSQL);
            sentencia.setString(1, cita.getHora());
            sentencia.setString(2, cita.getFecha());
            sentencia.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR AL CAMBIAR ESTADO " + ex.getMessage());
        }
    }

    public List<String> listarHorarios(Dentista dentista) throws Exception {
        String listaHorariosSQL = "SELECT estado, horario  FROM dentista WHERE nombres = ?";
        PreparedStatement sentencia;
        List<String> horarios = new ArrayList<>();
        try {
            sentencia = accesoDatosJDBC.prepararSentencia(listaHorariosSQL);
            sentencia.setString(1, dentista.getNombres());
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                String estado = resultado.getString("estado");
                if ("DISPONIBLE".equals(estado)) {
                    horarios.add(resultado.getString("horario"));
                }
            }
            return horarios;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("ERROR AL INTENTAR OBTENER LOS HORARIOS DEL DENTISTA.", e);
        }
    }

}

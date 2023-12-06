/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccessLifeV2.Aplicacion;

import AccessLifeV2.Persistencia.*;
import AccessLifeV2.Dominio.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author estdi
 */
public class RegistrarCitaServicio {

    private AccesoDatosJDBC accesoDatosJDBC;
    private CitaPostgreSQL citaPostgreSQL;
    private DentistaPostgreSQL dentistaPostgreSQL;
    private PacientePostgreSQL pacientePostgreSQL;

    public RegistrarCitaServicio() {
        accesoDatosJDBC = new AccesoDatosJDBCPostgreSQL();
        citaPostgreSQL = new CitaPostgreSQL(accesoDatosJDBC);
        dentistaPostgreSQL = new DentistaPostgreSQL(accesoDatosJDBC);
        pacientePostgreSQL = new PacientePostgreSQL(accesoDatosJDBC);
    }

    public int IncrementarId() throws Exception {
        int id;
        accesoDatosJDBC.abrirConexion();
        id = citaPostgreSQL.IdIncrement();
        accesoDatosJDBC.cerrarConexion();
        return id;
    }

    public Dentista buscarDentista(String nombres) throws Exception {
        accesoDatosJDBC.abrirConexion();
        Dentista dentista = dentistaPostgreSQL.buscar(nombres);
        accesoDatosJDBC.cerrarConexion();
        return dentista;
    }

    public Paciente buscarPaciente(String dni) throws Exception {
        accesoDatosJDBC.abrirConexion();
        Paciente paciente = pacientePostgreSQL.buscar(dni);
        accesoDatosJDBC.cerrarConexion();
        return paciente;
    }

    public void MostrarCita(DefaultTableModel modelo) throws Exception {
        accesoDatosJDBC.abrirConexion();
        accesoDatosJDBC.iniciarTransaccion();
        citaPostgreSQL.MostrarCitas(modelo);
        accesoDatosJDBC.terminarTransaccion();
    }

    public void cambiarEstado(Cita cita) throws Exception {
        accesoDatosJDBC.abrirConexion();
        accesoDatosJDBC.iniciarTransaccion();
        citaPostgreSQL.cambiarEstado(cita);
        accesoDatosJDBC.terminarTransaccion();
    }

    public List<String> listaDeHoras(Dentista dentista) throws Exception {
        accesoDatosJDBC.abrirConexion();
        accesoDatosJDBC.iniciarTransaccion();
        List<String> horariosDisponibles = citaPostgreSQL.listarHorarios(dentista);
        accesoDatosJDBC.terminarTransaccion();
        return horariosDisponibles;
    }

    public int TotalCitas(Dentista dentista) throws Exception {
        accesoDatosJDBC.abrirConexion();
        accesoDatosJDBC.iniciarTransaccion();
        int totalcitas = citaPostgreSQL.consultarTotalDeCitas(dentista);
        accesoDatosJDBC.terminarTransaccion();
        return totalcitas;
    }

    public void guardarCita(Cita cita) throws Exception {
        // VALIDAMOS QUE LA FECHA NO SEA ANTES DE LA FECHA ACTUAL
        if (!cita.fechaPasada()) {
            throw new Exception("NO SE PUEDE REGISTRAR UNA CITA CON FECHA PASADA");
        }

        // VALIDAMOS QUE NO SEA UNA FECHA DE FERIADO EN EL CALENDARIO
        if (cita.esFeriado()) {
            throw new Exception("NO HAY CITAS PARA ESA FECHA, ELIJA OTRA FECHA QUE NO SEA FERIADO");
        }
        
        // VERIFICAMOS EL TOTAL DE CITAS DEL MEDICO
        Dentista dentista = cita.getDentista();
        int totalCitas = TotalCitas(dentista);
        if (totalCitas >= 10) {
            throw new Exception("EL MEDICO ALCANZÒ EL LIMITE DE CITAS POR DÌA.");
        }

        accesoDatosJDBC.abrirConexion();
        accesoDatosJDBC.iniciarTransaccion();
        citaPostgreSQL.guardar(cita);
        accesoDatosJDBC.terminarTransaccion();
    }

}

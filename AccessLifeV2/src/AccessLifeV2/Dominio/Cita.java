package AccessLifeV2.Dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author estdi
 * @version 1.0
 * @created 21-may.-2022 21:58:44
 */
public class Cita {

    private int idcita;
    private Dentista dentista;
    private Paciente paciente;
    private String fecha;
    private String hora;
    private String estado;
    private String servicio;
    private String detallecita;
    private double pagototal;

    public Cita() {
    }

    public Cita(int idcita, Dentista dentista, Paciente paciente, String fecha, String hora, String estado, String servicio, String detallecita, double pagototal) {
        this.idcita = idcita;
        this.dentista = dentista;
        this.paciente = paciente;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.servicio = servicio;
        this.detallecita = detallecita;
        this.pagototal = pagototal;
    }

    public int getIdcita() {
        return idcita;
    }

    public void setIdcita(int idcita) {
        this.idcita = idcita;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getDetallecita() {
        return detallecita;
    }

    public void setDetallecita(String detallecita) {
        this.detallecita = detallecita;
    }

    public double getPagototal() {
        return pagototal;
    }

    public void setPagototal(double pagototal) {
        this.pagototal = pagototal;
    }

//  REGLAS DE NEGOCIO
    //-------- REGLA 1 DE VALIDACION --------
    public boolean fechaPasada() {
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-yyyy");
        LocalDate fechaAlmacenada = LocalDate.parse(fecha, formatter);
        return !fechaAlmacenada.isBefore(fechaActual);
    }

    //-------- REGLA 2 DE VALIDACION --------
    public boolean esFeriado() {
        Set<LocalDate> feriados = new HashSet<>(Arrays.asList(
                LocalDate.of(2023, 1, 1), // Año nuevo
                LocalDate.of(2023, 1, 6), // Día de los Reyes Magos
                LocalDate.of(2023, 3, 21), // Semana Santa (Martes Santo)
                LocalDate.of(2023, 3, 22), // Semana Santa (Miércoles Santo)
                LocalDate.of(2023, 3, 24), // Semana Santa (Jueves Santo)
                LocalDate.of(2023, 4, 28), // Semana Santa (Viernes Santo)
                LocalDate.of(2023, 5, 1), // Día del Trabajador
                LocalDate.of(2023, 6, 29), // San Pedro y San Pablo
                LocalDate.of(2023, 7, 28), // Santa Rosa de Lima
                LocalDate.of(2023, 9, 8), // Día de la Independencia
                LocalDate.of(2023, 10, 28), // Día de los Fallenos
                LocalDate.of(2023, 11, 25), // Día de la Virgen de Cocharcas
                LocalDate.of(2023, 12, 8), // Día de la Inmaculada Concepción
                LocalDate.of(2023, 12, 25), // Navidad
                LocalDate.of(2023, 12, 26), // Navidad (festivo diario)
                LocalDate.of(2023, 12, 27), // Navidad (festivo diario)
                LocalDate.of(2023, 12, 28), // Navidad (festivo diario)
                LocalDate.of(2023, 12, 29), // Navidad (festivo diario)
                LocalDate.of(2023, 12, 30), // Navidad (festivo diario)
                LocalDate.of(2023, 12, 31) // Fin de año
        ));
        return feriados.contains(LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd-M-yyyy")));
    }

    //-------- REGLA 1 DE NEGOCIO --------
    public String TipoServicio(int cod) {
        switch (cod) {
            case 1 -> 
                servicio = "CONSULTA";
            case 2 ->
                servicio = "LIMPIEZA DENTAL";
            case 3 ->
                servicio = "EXTRACCION SIMPLE";
            case 4 ->
                servicio = "EXTRACCION ESPECIAL";
            case 5 ->
                servicio = "PROTESIS COMPLETA";
            case 6 ->
                servicio = "BLANQUEAMIENTO DENTAL";
            default ->
                servicio = "SERVICIO NO ENCONTRADO";
        }
        return servicio;
    }

    //-------- REGLA 2 DE NEGOCIO --------
    public double PrecioServicio() {
        double Precio = 0;
        switch (servicio) {
            case "CONSULTA" ->
                Precio = 20;
            case "LIMPIEZA DENTAL" ->
                Precio = 150;
            case "EXTRACCION SIMPLE" ->
                Precio = 80;
            case "EXTRACCION ESPECIAL" ->
                Precio = 250;
            case "PROTESIS COMPLETA" ->
                Precio = 2500;
            case "BLANQUEAMIENTO DENTAL" ->
                Precio = 350;
            default ->
                servicio = "SERVICIO NO ENCONTRADO";
        }
        return Precio;
    }

    //-------- REGLA 3 DE NEGOCIO --------
    public double PagoTotal(int edad) {
        edad = paciente.getEdad();
        if (edad >= 50) {
            pagototal = PrecioServicio() - Descuento();
        } else {
            pagototal = PrecioServicio();
        }
        return pagototal;
    }

    //-------- REGLA 4 DE NEGOCIO --------
    public double Descuento() {
        double descuento = 0;
        pagototal = PrecioServicio();
        switch (servicio) {
            case "CONSULTA" ->
                descuento = pagototal * 0.10;
            case "LIMPIEZA DENTAL" ->
                descuento = pagototal * 0.15;
            case "EXTRACCION SIMPLE" ->
                descuento = pagototal * 0.15;
            case "EXTRACCION ESPECIAL" ->
                descuento = pagototal * 0.20;
            case "PROTESIS COMPLETA" ->
                descuento = pagototal * 0.25;
            case "BLANQUEAMIENTO DENTAL" ->
                descuento = pagototal * 0.15;
            default ->
                servicio = "SERVICIO NO ENCONTRADO";
        }
        return descuento;
    }

}
    //end Cita

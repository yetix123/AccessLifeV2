package AccessLifeV2.Dominio;

import javax.swing.JOptionPane;

/**
 * @author estdi
 * @version 1.0
 * @created 21-may.-2022 21:58:44
 */
public class Dentista {

    private int iddentista;
    private String nombres;
    private String dni;
    private String dialaboral;
    private String horario;
    private String estado;
    private String telefono;
    private String email;

    public Dentista() {
    }

    public Dentista(int iddentista, String nombres, String dni, String dialaboral, String horario, String estado, String telefono, String email) {
        this.iddentista = iddentista;
        this.nombres = nombres;
        this.dni = dni;
        this.dialaboral = dialaboral;
        this.horario = horario;
        this.estado = estado;
        this.telefono = telefono;
        this.email = email;
    }

    public int getIddentista() {
        return iddentista;
    }

    public void setIddentista(int iddentista) {
        this.iddentista = iddentista;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDialaboral() {
        return dialaboral;
    }

    public void setDialaboral(String dialaboral) {
        this.dialaboral = dialaboral;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // REGLAS DE NEGOCIO    
    public int totalCitas() {
        int totalC = 10;
        return totalC;
    }

}//end Dentista

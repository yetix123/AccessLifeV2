package AccessLifeV2.Dominio;

import java.util.List;
import javax.swing.JOptionPane;

/**
 * @author estdi
 * @version 1.0
 * @created 21-may.-2022 21:58:44
 */
public class Paciente {
    
    private int idpaciente;
    private String nombres;
    private String dni;
    private int edad;
    private String sexo;
    private String telefono;
    private String email;
    
    public Paciente() {        
    }
    
    public Paciente(int idpaciente, String nombres, String dni, int edad, String sexo, String telefono, String email) {
        this.idpaciente = idpaciente;
        this.nombres = nombres;
        this.dni = dni;
        this.edad = edad;
        this.sexo = sexo;
        this.telefono = telefono;
        this.email = email;
    }
    
    public int getIdpaciente() {
        return idpaciente;
    }
    
    public void setIdpaciente(int idpaciente) {
        this.idpaciente = idpaciente;
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
    
    public int getEdad() {
        return edad;
    }
    
    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    public String getSexo() {
        return sexo;
    }
    
    public void setSexo(String sexo) {
        this.sexo = sexo;
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
    // ------- REGLA 1 DE NEGOCIO ---------   
    public boolean verificarDniExistente() {  
        return true;
    }
    
}//end Paciente

package edu.uptc.dominio;

import edu.uptc.enums.TipoDocumento;
import edu.uptc.enums.TipoPersona;

public class Usuario {
    protected TipoPersona tipoPersona;
    protected TipoDocumento tipoDocumento;
    protected String numeroDocumento ;
    protected String nombre ;
    protected String correo;
    protected String contraseña ;
    protected String direcccion ;
    protected String ciudad ;

    public Usuario(TipoPersona tipoPersona, TipoDocumento tipoDocumento, String numeroDocumento , String nombre,
                   String correo,String contraseña ,String direcccion,String ciudad ) {
        this.tipoPersona = tipoPersona;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.direcccion = direcccion;
        this.ciudad = ciudad;
    }
    public Usuario (){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "tipoPersona=" + tipoPersona +
                ", tipoDocumento=" + tipoDocumento +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", direcccion='" + direcccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }
}

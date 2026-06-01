package edu.uptc.dominio;

import edu.uptc.enums.TipoDocumento;
import edu.uptc.enums.TipoPersona;

public class Administrador extends Usuario{

    public Administrador(TipoPersona tipoPersona, TipoDocumento tipoDocumento, String numeroDocumento, String nombre, String correo, String contraseña, String telefono, String direcccion, String ciudad) {
        super(tipoPersona, tipoDocumento, numeroDocumento, nombre, correo, contraseña, telefono, direcccion, ciudad);
    }

    public Administrador() {
    }

    @Override
    public String toString() {
        return "Administrador: " + getNombre() + " | Doc: " + getNumeroDocumento();
    }


}

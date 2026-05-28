package edu.uptc.dominio;

import edu.uptc.enums.TipoDocumento;
import edu.uptc.enums.TipoPersona;

public class Contratista extends Usuario{
    private String entidadPublica;
    private double areaIntervencion;


    public Contratista(TipoPersona tipoPersona, TipoDocumento tipoDocumento, String numeroDocumento, String nombre, String correo, String contraseña,
                       String direcccion, String ciudad, String entidadPublica, double areaIntervencion) {
        super(tipoPersona, tipoDocumento, numeroDocumento, nombre, correo, contraseña, direcccion, ciudad);
        this.entidadPublica = entidadPublica;
        this.areaIntervencion = areaIntervencion;
    }

    public Contratista() {}
}

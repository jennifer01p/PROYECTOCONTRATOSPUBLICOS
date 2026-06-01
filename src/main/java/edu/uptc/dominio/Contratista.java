package edu.uptc.dominio;

import edu.uptc.enums.TipoDocumento;
import edu.uptc.enums.TipoPersona;

public class Contratista extends Usuario{
    private boolean esEntidadPublica;
    private String areaDesempeno;


    public Contratista(TipoPersona tipoPersona, TipoDocumento tipoDocumento, String numeroDocumento, String nombre, String correo, String contraseña,
                       String telefono, String direcccion, String ciudad, boolean esEntidadPublica, String areaDesempeno) {
        super(tipoPersona, tipoDocumento, numeroDocumento, nombre, correo, contraseña, telefono, direcccion, ciudad);
        this.esEntidadPublica = esEntidadPublica;
        this.areaDesempeno = areaDesempeno;
    }

    public Contratista() {}

    public boolean isEsEntidadPublica() {
        return esEntidadPublica;
    }

    public void setEsEntidadPublica(boolean esEntidadPublica) {
        this.esEntidadPublica = esEntidadPublica;
    }

    public String getAreaDesempeno() {
        return areaDesempeno;
    }

    public void setAreaDesempeno(String areaDesempeno) {
        this.areaDesempeno = areaDesempeno;
    }
}

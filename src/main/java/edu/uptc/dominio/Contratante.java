package edu.uptc.dominio;

import edu.uptc.enums.NivelEntidad;
import edu.uptc.enums.TipoDocumento;
import edu.uptc.enums.TipoPersona;

public class Contratante extends Usuario{

    private String sector;
    private NivelEntidad nivelEntidad;
    private String codigoEntidad;

    public Contratante(TipoPersona tipoPersona, TipoDocumento tipoDocumento, String numeroDocumento, String nombre, String correo, String contraseña,
                       String telefono, String direcccion, String ciudad, String sector, NivelEntidad nivelEntidad,String codigoEntidad) {
        super(tipoPersona, tipoDocumento, numeroDocumento, nombre, correo, contraseña, telefono, direcccion, ciudad);
        this.sector = sector;
        this.nivelEntidad = nivelEntidad;
        this.codigoEntidad = codigoEntidad;
    }

    public Contratante() {}

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public NivelEntidad getNivelEntidad() {
        return nivelEntidad;
    }

    public void setNivelEntidad(NivelEntidad nivelEntidad) {
        this.nivelEntidad = nivelEntidad;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }
}

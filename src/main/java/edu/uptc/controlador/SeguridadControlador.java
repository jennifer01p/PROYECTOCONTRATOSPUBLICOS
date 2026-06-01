package edu.uptc.controlador;

import edu.uptc.dominio.Administrador;
import edu.uptc.dominio.Contratante;
import edu.uptc.dominio.Contratista;
import edu.uptc.dominio.Usuario;
import edu.uptc.servicio.SeguridadServicio;

/**
 * Controlador de seguridad. Actúa como puente entre la vista y el servicio.
 * No contiene lógica de negocio ni interfaces gráficas.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class SeguridadControlador {

    /** Servicio de autenticación. */
    private SeguridadServicio seguridadServicio;

    /**
     * Constructor del controlador de seguridad.
     *
     * @param seguridadServicio Servicio de autenticación.
     */
    public SeguridadControlador(SeguridadServicio seguridadServicio) {
        this.seguridadServicio = seguridadServicio;
    }

    /**
     * Autentica a un usuario con correo y contraseña.
     *
     * @param correo     Correo electrónico.
     * @param contrasena Contraseña.
     * @return El usuario autenticado o null si las credenciales son incorrectas.
     */
    public Usuario iniciarSesion(String correo, String contrasena) {
        return seguridadServicio.iniciarSesion(correo, contrasena);
    }

    /**
     * Determina el rol del usuario autenticado.
     *
     * @param usuario El usuario cuyo rol se desea conocer.
     * @return Cadena con el rol: "ADMINISTRADOR", "CONTRATANTE", "CONTRATISTA" o "DESCONOCIDO".
     */
    public String obtenerRol(Usuario usuario) {
        if (usuario instanceof Administrador) return "ADMINISTRADOR";
        if (usuario instanceof Contratante)   return "CONTRATANTE";
        if (usuario instanceof Contratista)   return "CONTRATISTA";
        return "DESCONOCIDO";
    }
}

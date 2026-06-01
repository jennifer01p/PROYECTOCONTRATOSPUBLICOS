package edu.uptc.servicio;

import edu.uptc.dominio.Usuario;

import java.util.ArrayList;

/**
 * Servicio encargado de gestionar la autenticación de usuarios en el sistema.
 * Permite iniciar sesión validando correo y contraseña.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class SeguridadServicio {

    /** Lista de usuarios registrados disponibles para autenticación. */
    private ArrayList<Usuario> usuarios;

    /**
     * Constructor que recibe la lista compartida de usuarios del sistema.
     *
     * @param usuarios Lista de usuarios registrados.
     */
    public SeguridadServicio(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Autentica a un usuario verificando su correo y contraseña.
     *
     * @param correo      Correo electrónico del usuario.
     * @param contrasena  Contraseña del usuario.
     * @return El objeto {@link Usuario} si las credenciales son válidas; {@code null} en caso contrario.
     */
    public Usuario iniciarSesion(String correo, String contrasena) {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equals(correo) && u.getContraseña().equals(contrasena)) {
                return u;
            }
        }
        return null;
    }
}

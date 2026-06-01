package edu.uptc.controlador;

import edu.uptc.dominio.Administrador;
import edu.uptc.dominio.Contratante;
import edu.uptc.dominio.Contratista;
import edu.uptc.dominio.Usuario;
import edu.uptc.servicio.SeguridadServicio;

import javax.swing.*;

/**
 * Controlador de seguridad que gestiona el inicio de sesión
 * y determina el rol del usuario autenticado.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class SeguridadControlador {

    /** Servicio de seguridad utilizado para la autenticación. */
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
     * Muestra el formulario de inicio de sesión y retorna el usuario autenticado.
     * Permite hasta 3 intentos antes de bloquear el acceso.
     *
     * @return El {@link Usuario} autenticado, o {@code null} si falló la autenticación.
     */
    public Usuario iniciarSesion() {
        int intentos = 0;
        while (intentos < 3) {
            String correo = JOptionPane.showInputDialog(null, "Correo electrónico:", "Iniciar Sesión", JOptionPane.PLAIN_MESSAGE);
            if (correo == null) return null;
            String clave = JOptionPane.showInputDialog(null, "Contraseña:", "Iniciar Sesión", JOptionPane.PLAIN_MESSAGE);
            if (clave == null) return null;

            Usuario usuario = seguridadServicio.iniciarSesion(correo.trim(), clave.trim());
            if (usuario != null) {
                JOptionPane.showMessageDialog(null, "Bienvenido/a, " + usuario.getNombre() + "!", "Acceso Exitoso", JOptionPane.INFORMATION_MESSAGE);
                return usuario;
            } else {
                intentos++;
                int restantes = 3 - intentos;
                if (restantes > 0) {
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas. Intentos restantes: " + restantes, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Acceso bloqueado por demasiados intentos fallidos.", "Bloqueado", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    /**
     * Determina el rol del usuario autenticado.
     *
     * @param usuario El usuario cuyo rol se desea conocer.
     * @return Cadena con el rol: "ADMINISTRADOR", "CONTRATANTE", "CONTRATISTA" o "DESCONOCIDO".
     */
    public String obtenerRol(Usuario usuario) {
        if (usuario instanceof Administrador) return "ADMINISTRADOR";
        if (usuario instanceof Contratante) return "CONTRATANTE";
        if (usuario instanceof Contratista) return "CONTRATISTA";
        return "DESCONOCIDO";
    }
}

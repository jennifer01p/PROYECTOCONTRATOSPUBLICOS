package edu.uptc.servicio;

import edu.uptc.dominio.Usuario;
import edu.uptc.enums.TipoPersona;

import java.util.ArrayList;

/**
 * Servicio encargado de gestionar los usuarios del sistema.
 * Permite crear, consultar, actualizar y eliminar usuarios.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class UsuarioServicio {

    /** Lista de usuarios registrados en el sistema. */
    private ArrayList<Usuario> usuarios;

    /**
     * Constructor que inicializa la lista de usuarios.
     */
    public UsuarioServicio() {
        this.usuarios = new ArrayList<>();
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param usuario El usuario a registrar.
     */
    public void crearUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    /**
     * Busca un usuario por su número de documento.
     *
     * @param documento Número de documento del usuario.
     * @return El usuario encontrado, o {@code null} si no existe.
     */
    public Usuario buscarUsuario(String documento) {
        for (Usuario usuarioAux : usuarios) {
            if (documento.equals(usuarioAux.getNumeroDocumento())) {
                return usuarioAux;
            }
        }
        return null;
    }

    /**
     * Actualiza los datos básicos de un usuario identificado por cédula.
     *
     * @param cedula      Número de documento del usuario.
     * @param tipoPersona Nuevo tipo de persona.
     * @param correo      Nuevo correo electrónico.
     * @param contraseña  Nueva contraseña.
     * @param direcccion  Nueva dirección.
     * @param ciudad      Nueva ciudad.
     * @return Mensaje de resultado de la operación.
     */
    public String actualizarUsuario(String cedula, TipoPersona tipoPersona, String correo,
                                    String contraseña, String direcccion, String ciudad) {
        Usuario usuario = buscarUsuario(cedula);
        if (usuario != null) {
            usuario.setTipoPersona(tipoPersona);
            usuario.setCorreo(correo);
            usuario.setContraseña(contraseña);
            usuario.setDirecccion(direcccion);
            usuario.setCiudad(ciudad);
            return "Información de usuario actualizada";
        }
        return "No se encontró el usuario identificado con la cédula " + cedula;
    }

    /**
     * Elimina un usuario del sistema por su número de documento.
     *
     * @param documento Número de documento del usuario a eliminar.
     * @return Mensaje de resultado de la operación.
     */
    public String eliminarUsuario(String documento) {
        Usuario usuario = buscarUsuario(documento);
        if (usuario != null) {
            usuarios.remove(usuario);
            return "Usuario eliminado";
        }
        return "Usuario no encontrado";
    }

    /**
     * Retorna la lista completa de usuarios registrados.
     *
     * @return Lista de usuarios.
     */
    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
}

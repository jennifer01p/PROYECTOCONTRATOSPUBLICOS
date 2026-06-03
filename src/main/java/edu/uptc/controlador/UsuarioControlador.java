package edu.uptc.controlador;

import edu.uptc.dominio.Contratante;
import edu.uptc.dominio.Contratista;
import edu.uptc.dominio.Usuario;
import edu.uptc.enums.NivelEntidad;
import edu.uptc.enums.TipoDocumento;
import edu.uptc.enums.TipoPersona;
import edu.uptc.servicio.UsuarioServicio;

/**
 * Controlador de usuarios. Actúa como puente entre la vista y el servicio.
 * No contiene lógica de negocio ni interfaces gráficas.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class UsuarioControlador {

    /** Servicio de usuarios. */
    private UsuarioServicio usuarioServicio;

    /**
     * Constructor que recibe el servicio de usuarios.
     *
     * @param usuarioServicio Servicio de gestión de usuarios.
     */
    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    /**
     * Crea y registra un nuevo contratante.
     *
     * @param tipoPersona    Tipo de persona.
     * @param tipoDocumento  Tipo de documento.
     * @param numeroDocumento Número de documento.
     * @param nombre         Nombre completo.
     * @param correo         Correo electrónico.
     * @param contrasena     Contraseña.
     * @param telefono       Teléfono.
     * @param direccion      Dirección.
     * @param ciudad         Ciudad.
     * @param sector         Sector de la entidad.
     * @param nivelEntidad   Nivel de la entidad.
     * @param codigoEntidad  Código único de entidad.
     */
    public String crearContratante(TipoPersona tipoPersona, TipoDocumento tipoDocumento,
                                  String numeroDocumento, String nombre, String correo,
                                  String contrasena, String telefono, String direccion,
                                  String ciudad, String sector, NivelEntidad nivelEntidad,
                                  String codigoEntidad) {
        Contratante contratante = new Contratante(tipoPersona, tipoDocumento, numeroDocumento,
                nombre, correo, contrasena, telefono, direccion, ciudad,
                sector, nivelEntidad, codigoEntidad);
        return usuarioServicio.crearUsuario(contratante);
    }

    /**
     * Crea y registra un nuevo contratista.
     *
     * @param tipoPersona     Tipo de persona.
     * @param tipoDocumento   Tipo de documento.
     * @param numeroDocumento Número de documento.
     * @param nombre          Nombre completo.
     * @param correo          Correo electrónico.
     * @param contrasena      Contraseña.
     * @param telefono        Teléfono.
     * @param direccion       Dirección.
     * @param ciudad          Ciudad.
     * @param esEntidadPublica Si es entidad pública.
     * @param areaDesempeno   Función principal del área de desempeño.
     */
    public String crearContratista(TipoPersona tipoPersona, TipoDocumento tipoDocumento,
                                  String numeroDocumento, String nombre, String correo,
                                  String contrasena, String telefono, String direccion,
                                  String ciudad, boolean esEntidadPublica, String areaDesempeno) {
        Contratista contratista = new Contratista(tipoPersona, tipoDocumento, numeroDocumento,
                nombre, correo, contrasena, telefono, direccion, ciudad,
                esEntidadPublica, areaDesempeno);
        return usuarioServicio.crearUsuario(contratista);
    }

    /**
     * Busca un usuario por número de documento.
     *
     * @param numeroDocumento Número de documento.
     * @return El usuario encontrado o null.
     */
    public Usuario consultarUsuario(String numeroDocumento) {
        return usuarioServicio.buscarUsuario(numeroDocumento);
    }

    /**
     * Actualiza los datos básicos de un usuario.
     *
     * @param numeroDocumento Documento del usuario a actualizar.
     * @param tipoPersona     Nuevo tipo de persona.
     * @param correo          Nuevo correo.
     * @param contrasena      Nueva contraseña.
     * @param direccion       Nueva dirección.
     * @param ciudad          Nueva ciudad.
     * @return Mensaje de resultado.
     */
    public String actualizarUsuario(String numeroDocumento, TipoPersona tipoPersona,
                                     String correo, String contrasena,
                                     String direccion, String ciudad) {
        return usuarioServicio.actualizarUsuario(numeroDocumento, tipoPersona, correo, contrasena, direccion, ciudad);
    }

    /**
     * Elimina un usuario por número de documento.
     *
     * @param numeroDocumento Documento del usuario a eliminar.
     * @return Mensaje de resultado.
     */
    public String eliminarUsuario(String numeroDocumento) {
        return usuarioServicio.eliminarUsuario(numeroDocumento);
    }

    /**
     * Verifica si ya existe un usuario con el número de documento dado.
     *
     * @param numeroDocumento Número de documento a verificar.
     * @return {@code true} si ya existe, {@code false} si no.
     */
    public boolean existeDocumento(String numeroDocumento) {
        return usuarioServicio.existeDocumento(numeroDocumento);
    }

    /**
     * Retorna el servicio de usuarios.
     *
     * @return Instancia de {@link UsuarioServicio}.
     */
    public UsuarioServicio getUsuarioServicio() {
        return usuarioServicio;
    }
}

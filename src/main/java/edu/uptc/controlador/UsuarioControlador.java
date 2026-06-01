package edu.uptc.controlador;

import edu.uptc.dominio.*;
import edu.uptc.enums.*;
import edu.uptc.servicio.UsuarioServicio;

import javax.swing.*;

/**
 * Controlador encargado de gestionar las operaciones CRUD de usuarios
 * (contratantes y contratistas) a través de cuadros de diálogo JOptionPane.
 * Solo el administrador puede usar estas funciones.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class UsuarioControlador {

    /** Servicio de usuarios utilizado por este controlador. */
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
     * Solicita los datos comunes de un usuario mediante JOptionPane.
     * Retorna null si el usuario cancela algún campo obligatorio.
     *
     * @return Arreglo con los datos comunes, o null si se canceló.
     */
    private String[] pedirDatosComunes() {
        String[] opcTipoPersona = {"NATURAL", "JURIDICA"};
        int tpIdx = JOptionPane.showOptionDialog(null, "Tipo de persona:", "Registro",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcTipoPersona, opcTipoPersona[0]);
        if (tpIdx < 0) return null;

        String[] opcTipoDoc = {"CC", "TI", "CE", "PASAPORTE", "NIT"};
        int tdIdx = JOptionPane.showOptionDialog(null, "Tipo de documento:", "Registro",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcTipoDoc, opcTipoDoc[0]);
        if (tdIdx < 0) return null;

        String numDoc = JOptionPane.showInputDialog("Número de documento:");
        if (numDoc == null || numDoc.isBlank()) return null;
        String nombre = JOptionPane.showInputDialog("Nombre completo:");
        if (nombre == null || nombre.isBlank()) return null;
        String correo = JOptionPane.showInputDialog("Correo electrónico:");
        if (correo == null || correo.isBlank()) return null;
        String clave = JOptionPane.showInputDialog("Contraseña:");
        if (clave == null || clave.isBlank()) return null;
        String tel = JOptionPane.showInputDialog("Teléfono:");
        if (tel == null || tel.isBlank()) return null;
        String dir = JOptionPane.showInputDialog("Dirección:");
        if (dir == null || dir.isBlank()) return null;
        String ciudad = JOptionPane.showInputDialog("Ciudad:");
        if (ciudad == null || ciudad.isBlank()) return null;

        return new String[]{opcTipoPersona[tpIdx], opcTipoDoc[tdIdx], numDoc, nombre, correo, clave, tel, dir, ciudad};
    }

    /**
     * Muestra el menú de creación de usuarios (contratante o contratista)
     * y registra el nuevo usuario en el sistema.
     */
    public void crearUsuario() {
        String[] tipos = {"Contratante", "Contratista"};
        int tipoIdx = JOptionPane.showOptionDialog(null, "¿Qué tipo de usuario desea crear?", "Crear Usuario",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
        if (tipoIdx < 0) return;

        String[] datos = pedirDatosComunes();
        if (datos == null) {
            JOptionPane.showMessageDialog(null, "Creación cancelada.");
            return;
        }

        TipoPersona tp = TipoPersona.valueOf(datos[0]);
        TipoDocumento td = TipoDocumento.valueOf(datos[1]);

        if (tipoIdx == 0) {
            // Contratante
            String sector = JOptionPane.showInputDialog("Sector de la entidad:");
            if (sector == null || sector.isBlank()) { JOptionPane.showMessageDialog(null, "Cancelado."); return; }
            String[] niveles = {"MUNICIPAL", "DEPARTAMENTAL", "NACIONAL"};
            int nivelIdx = JOptionPane.showOptionDialog(null, "Nivel de la entidad:", "Crear Contratante",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, niveles, niveles[0]);
            if (nivelIdx < 0) return;
            String codEntidad = JOptionPane.showInputDialog("Código único de entidad:");
            if (codEntidad == null || codEntidad.isBlank()) { JOptionPane.showMessageDialog(null, "Cancelado."); return; }

            Contratante c = new Contratante(tp, td, datos[2], datos[3], datos[4], datos[5],
                    datos[6], datos[7], datos[8], sector, NivelEntidad.valueOf(niveles[nivelIdx]), codEntidad);
            usuarioServicio.crearUsuario(c);
            JOptionPane.showMessageDialog(null, "Contratante registrado exitosamente.");
        } else {
            // Contratista
            int entPub = JOptionPane.showConfirmDialog(null, "¿Es entidad pública?", "Crear Contratista", JOptionPane.YES_NO_OPTION);
            if (entPub < 0) return;
            String area = JOptionPane.showInputDialog("Función principal del área de desempeño:");
            if (area == null || area.isBlank()) { JOptionPane.showMessageDialog(null, "Cancelado."); return; }

            Contratista c = new Contratista(tp, td, datos[2], datos[3], datos[4], datos[5],
                    datos[6], datos[7], datos[8], entPub == JOptionPane.YES_OPTION, area);
            usuarioServicio.crearUsuario(c);
            JOptionPane.showMessageDialog(null, "Contratista registrado exitosamente.");
        }
    }

    /**
     * Consulta un usuario por número de documento y muestra su información.
     */
    public void consultarUsuario() {
        String doc = JOptionPane.showInputDialog("Ingrese el número de documento a consultar:");
        if (doc == null || doc.isBlank()) return;
        Usuario u = usuarioServicio.buscarUsuario(doc);
        if (u != null) {
            JOptionPane.showMessageDialog(null, u.toString(), "Información del Usuario", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualiza los datos básicos de un usuario existente.
     */
    public void actualizarUsuario() {
        String doc = JOptionPane.showInputDialog("Ingrese el número de documento del usuario a actualizar:");
        if (doc == null || doc.isBlank()) return;
        Usuario u = usuarioServicio.buscarUsuario(doc);
        if (u == null) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] opcTipoPersona = {"NATURAL", "JURIDICA"};
        int tpIdx = JOptionPane.showOptionDialog(null, "Nuevo tipo de persona:", "Actualizar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcTipoPersona, u.getTipoPersona().name());
        if (tpIdx < 0) return;
        String correo = JOptionPane.showInputDialog("Nuevo correo:", u.getCorreo());
        if (correo == null) return;
        String clave = JOptionPane.showInputDialog("Nueva contraseña:", u.getContraseña());
        if (clave == null) return;
        String dir = JOptionPane.showInputDialog("Nueva dirección:", u.getDirecccion());
        if (dir == null) return;
        String ciudad = JOptionPane.showInputDialog("Nueva ciudad:", u.getCiudad());
        if (ciudad == null) return;

        String resultado = usuarioServicio.actualizarUsuario(doc, TipoPersona.valueOf(opcTipoPersona[tpIdx]),
                correo, clave, dir, ciudad);
        JOptionPane.showMessageDialog(null, resultado);
    }

    /**
     * Elimina un usuario del sistema por número de documento.
     */
    public void eliminarUsuario() {
        String doc = JOptionPane.showInputDialog("Ingrese el número de documento del usuario a eliminar:");
        if (doc == null || doc.isBlank()) return;
        int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        String resultado = usuarioServicio.eliminarUsuario(doc);
        JOptionPane.showMessageDialog(null, resultado);
    }

    /**
     * Retorna el servicio de usuarios.
     * @return Instancia de {@link UsuarioServicio}.
     */
    public UsuarioServicio getUsuarioServicio() {
        return usuarioServicio;
    }
}

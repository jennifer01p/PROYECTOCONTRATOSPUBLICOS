package edu.uptc.servicio;
import edu.uptc.dominio.Usuario;
import edu.uptc.enums.TipoPersona;

import java.util.ArrayList;

public class UsuarioServicio {

    private ArrayList<Usuario> usuarios;

    public UsuarioServicio(){
        this.usuarios = new ArrayList<>();
    }

    public void crearUsuario(Usuario usuario){
        usuarios.add(usuario);
    }

    public Usuario buscarUsuario(String documento){
        for(Usuario usuarioAux : usuarios){
            if(documento.equals(usuarioAux.getNumeroDocumento()));
            return usuarioAux;
        }
        return null;
    }

    public String actualizarUsuario(String cedula, TipoPersona tipoPersona, String correo,
                                    String contraseña,String direcccion, String ciudad){

        Usuario usuario = buscarUsuario(cedula);
        if(cedula != null){
            usuario.setTipoPersona(tipoPersona);
            usuario.setCorreo(correo);
            usuario.setContraseña(contraseña);
            usuario.setDirecccion(direcccion);
            usuario.setCiudad(ciudad);
            return "Informacion de usuario actualizada";
        }
        return "No se encontro el usuario identificado con la cedula "+ cedula;
    }

    public String eliminarUsuario(String documento){
        Usuario usuario = buscarUsuario(documento);
        if(usuario != null){
            usuarios.remove(usuario);
            return "Usuario eliminado";
        }
        return "Usuario no encontrado";
    }


}

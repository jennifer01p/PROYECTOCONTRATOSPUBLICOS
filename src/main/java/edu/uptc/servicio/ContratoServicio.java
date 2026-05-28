package edu.uptc.servicio;
import  edu.uptc.dominio.Contrato;
import edu.uptc.enums.EstadoContrato;

import java.util.ArrayList;

/**
 * Clase encargada de gestionar los contratos del sistema.
 * Permite crear, consultar, actualizar y eliminar contratos.
 *
 * @author Jennifer , Jesus y Santiago
 * @version 1.0
 */

public class ContratoServicio {

    private ArrayList<Contrato> contratos;


    public ContratoServicio(){
        this.contratos = new ArrayList<>();
    }

    public void crearContrato(Contrato contrato){
        contratos.add(contrato);
    }

    public Contrato consultarContrato(String id){
        for(Contrato contratoAux : contratos){
            if(id.equals(contratoAux.getId())){
                return contratoAux;

            }

        }
        return null;
    }
    public String actualizarContrato(String id,double valorContrato, int plazoContrato){
        Contrato contrato = consultarContrato(id);
        if(contrato!= null){
            contrato.setValorContrato(valorContrato);
            contrato.setPlazoContrato(plazoContrato);
            return "Informacion Actualizada";
        }
        return "Contrato no encontrado";

    }


    public String eliminar (String id){
        Contrato contrato = consultarContrato(id);
        if (contrato!= null){
           if(id.equals(contrato.getId())){
               contratos.remove(contrato);
               return "Contrato eliminado correctamente";
           }
        }
        return  "No se encontro el contrato";

    }
    public boolean validarContrato(Contrato contrato) {
        return contrato.validarContrato();
    }

    public String cambiarEstadoContrato(String id, EstadoContrato estado){
        Contrato contrato = consultarContrato(id);
        if(contrato!= null){
            contrato.setEstado(estado);
        }
        return "El contrato con la id "+id+ "no se encontro";
    }




}

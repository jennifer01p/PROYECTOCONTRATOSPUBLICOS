package edu.uptc.servicio;
import  edu.uptc.dominio.Contrato;
import java.util.ArrayList;

public class ContratoServicio {

    private ArrayList<Contrato> contratos;
    private Contrato contrato;

    public ContratoServicio(){
        this.contratos = new ArrayList<>();
        this.contrato = new Contrato();
    }

    public void crearContrato(Contrato contrato){
        contratos.add(contrato);
    }
    public Contrato consultarContrato(String id){
        for(Contrato contratoAux : contratos){
            if(id.equals(contratoAux)){
                return contratoAux;

            }

        }
        return null;
    }
    public void actualizarContrato(){}

    public String eliminar (String id){
        contrato = consultarContrato(id);
        if (contrato!= null){
           if(id.equals(contrato.getId())){
               contratos.remove(contrato);
               return "Contrato eliminado correctamente";
           }
        }
        return  "No se encontro el contrato";

    }




}

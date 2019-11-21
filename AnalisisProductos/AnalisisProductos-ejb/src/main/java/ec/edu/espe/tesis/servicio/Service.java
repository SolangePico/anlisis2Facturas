/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.servicio;

import ec.edu.espe.tesis.facturas.model.Usuario;

/**
 *
 * @author solan
 */
public interface Service {

    Boolean validarUsuario(Usuario usuarioPendiente);
}

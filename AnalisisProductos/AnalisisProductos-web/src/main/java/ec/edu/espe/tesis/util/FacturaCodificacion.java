/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.tesis.util;

import java.util.Base64;

/**
 *
 * @author alterbios
 */
public class FacturaCodificacion {
    
    public String codificarId(String id) {
        String encoded = Base64.getEncoder().encodeToString(id.getBytes());
        return encoded;
    }

    public  String decodificarId(String id) {
        String decode = null;
        try {
            byte[] encoded = Base64.getDecoder().decode(id);
            decode = new String(encoded);
        } catch (Exception ex) {
            
        }
        return decode;
    }
}

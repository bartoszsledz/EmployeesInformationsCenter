/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.issi.uz.zgora.pl;

import ejb.issi.uz.zgora.pl.PracownicyBean;
import entities.issi.uz.zgora.pl.PracownicyEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author sledzik
 */
@WebService(serviceName = "WebServis")
public class WebServis {

    @EJB
    private PracownicyBean ejbRef;

    @WebMethod(operationName = "PobierzPracownikow")
    public List<PracownicyEntity> PobierzPracownikow() {
        return ejbRef.PobierzPracownikow();
    }
}

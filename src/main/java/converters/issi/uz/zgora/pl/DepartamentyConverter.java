package converters.issi.uz.zgora.pl;

import ejb.issi.uz.zgora.pl.DepartamentyBean;
import entities.issi.uz.zgora.pl.DepartamentyEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@FacesConverter(forClass=DepartamentyEntity.class)
public class DepartamentyConverter implements Converter {

    private DepartamentyBean departamentyEJB;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {      
        try {
            InitialContext ctx = new InitialContext();
             departamentyEJB = (DepartamentyBean)ctx.lookup("java:comp/env/departamentyBean");

        } catch (NamingException ex) {
            Logger.getLogger(DepartamentyConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
           return departamentyEJB.Pobierz(Integer.parseInt(value)); 
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null)
        {
            return "";
        }
        DepartamentyEntity dep = (DepartamentyEntity)value;
        return dep.getId().toString();
    }

}
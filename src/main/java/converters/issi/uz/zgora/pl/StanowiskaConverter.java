package converters.issi.uz.zgora.pl;

import ejb.issi.uz.zgora.pl.StanowiskaBean;
import entities.issi.uz.zgora.pl.StanowiskaEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@FacesConverter(forClass=StanowiskaEntity.class)
public class StanowiskaConverter implements Converter {

    private StanowiskaBean stanowiskaEJB;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            InitialContext ctx = new InitialContext();
             stanowiskaEJB = (StanowiskaBean)ctx.lookup("java:comp/env/stanowiskaBean");

        } catch (NamingException ex) {
            Logger.getLogger(DepartamentyConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
           return stanowiskaEJB.Pobierz(Integer.parseInt(value));

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null)
        {
            return "";
        }
        StanowiskaEntity stan = (StanowiskaEntity)value;
        return stan.getId().toString();
    }

}

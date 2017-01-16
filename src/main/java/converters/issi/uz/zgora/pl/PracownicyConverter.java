package converters.issi.uz.zgora.pl;

import ejb.issi.uz.zgora.pl.PracownicyBean;
import entities.issi.uz.zgora.pl.PracownicyEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@FacesConverter(forClass=PracownicyEntity.class)
public class PracownicyConverter implements Converter {

    private PracownicyBean pracownicyBean;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            InitialContext ctx = new InitialContext();
             pracownicyBean = (PracownicyBean)ctx.lookup("java:comp/env/pracownicyBean");

        } catch (NamingException ex) {
            Logger.getLogger(PracownicyConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
           return pracownicyBean.Pobierz(Integer.parseInt(value));

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null)
        {
            return "";
        }
        PracownicyEntity pracownicyEntity = (PracownicyEntity)value;
        return pracownicyEntity.getId().toString();
    }

}


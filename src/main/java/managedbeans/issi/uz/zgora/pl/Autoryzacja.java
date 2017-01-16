package managedbeans.issi.uz.zgora.pl;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

//@Named
@ManagedBean
@SessionScoped
public class Autoryzacja implements Serializable {

    private ExternalContext ctx;
    private HttpServletRequest request;
    
    private MessageDigest messageDigest = null;

    public Autoryzacja() {
        try {
            ctx = FacesContext.getCurrentInstance().getExternalContext();
            request = (HttpServletRequest) ctx.getRequest();
             messageDigest = java.security.MessageDigest.getInstance("MD5");
            final byte bin[] = messageDigest.digest(("administrator").getBytes());
            System.out.print(bin);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Autoryzacja.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isZalogowany() {
        return request.isUserInRole("zalogowany");
    }

    public String wyloguj() {
        try {
            request.logout();
        } catch (ServletException ex) {
            Logger.getLogger(Autoryzacja.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "login";
    }
}
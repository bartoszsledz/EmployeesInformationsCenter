package managedbeans.issi.uz.zgora.pl;

import ejb.issi.uz.zgora.pl.DepartamentyBean;
import ejb.issi.uz.zgora.pl.PracownicyBean;
import ejb.issi.uz.zgora.pl.StanowiskaBean;
import entities.issi.uz.zgora.pl.DepartamentyEntity;
import entities.issi.uz.zgora.pl.PracownicyEntity;
import entities.issi.uz.zgora.pl.StanowiskaEntity;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import javax.imageio.ImageIO;
import javax.inject.Named;
import org.apache.myfaces.custom.fileupload.UploadedFile;

@Named
@SessionScoped
public class Pracownicy implements Serializable {

    @EJB
    private PracownicyBean pracownicyEJB;
    @EJB
    private DepartamentyBean departamentyEJB;
    @EJB
    private StanowiskaBean stanowiskaEJB;
    private PracownicyEntity pracownik;
    private UploadedFile zdjecieZal;
    private boolean usunZdjecie;
    private boolean admin;
    private HtmlDataTable tabela;
    private String folder;

    public Pracownicy() {
        folder = FacesContext.getCurrentInstance().
                getExternalContext().getRealPath("/") + "\\resources\\zdjecia\\";
    }

    public String DodajPracownika() {
        admin = false;
        pracownik = new PracownicyEntity();
        return "dodajPracownika";
    }

    public String Dodaj() {
        pracownicyEJB.Dodaj(pracownik);
        return "index";
    }

    public void Usun(int id) {
        pracownicyEJB.Usun(id);
        tabela.setFirst(0);
    }

    public String Pobierz(int id) {
        usunZdjecie = false;
        zdjecieZal = null;
        pracownik = pracownicyEJB.Pobierz(id);
        return "edytujPracownika";
    }

    private void zapiszZdjecie() throws IOException {
        File plik = new File(folder + pracownik.getId() + ".jpg");
        if (plik.isFile()) {
            plik.delete();
        }
        byte[] dane = zdjecieZal.getBytes();
        pracownik.setZdjecie(true);
        FileOutputStream zdj = new FileOutputStream(plik);
        zdj.write(dane);
        zdj.close();
        BufferedImage img = ImageIO.read(plik);
        int wys = 100;
        int szer = Math.round((wys * img.getHeight()) / img.getWidth());
        BufferedImage min = new BufferedImage(wys, szer, BufferedImage.TYPE_INT_RGB);
        min.createGraphics().drawImage(img.getScaledInstance(wys, szer, Image.SCALE_SMOOTH), 0, 0, null);
        ImageIO.write(min, "jpg", plik);

    }

    private void usunZdjecie(int nr) {
        File plik = new File(folder + nr + ".jpg");
        if (plik.isFile()) {
            plik.delete();
        }
    }

    public String Edytuj() {
        if (zdjecieZal != null) {
            try {
                zapiszZdjecie();
            } catch (IOException ex) {
                Logger.getLogger(Pracownicy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (usunZdjecie) {
            pracownik.setZdjecie(false);
            usunZdjecie(pracownik.getId());
        }
        pracownicyEJB.Edytuj(pracownik);
        pracownik = new PracownicyEntity();
        return "index";
    }

    public List<PracownicyEntity> getPracownicy() {
        return pracownicyEJB.PobierzPracownikow();
    }

    public List<DepartamentyEntity> getDepartamenty() {
        return departamentyEJB.PobierzDepartamenty();
    }

    public List<StanowiskaEntity> getStanowiska() {
        return stanowiskaEJB.PobierzStanowiska();
    }

    public String index() {
        if (tabela != null) {
            tabela.setFirst(0);
        }
        return "index";
    }

    public void Sprawdz(ValueChangeEvent e) {
        if ((e.getNewValue() != null && e.getNewValue().equals(true))
                || (e.getOldValue() != null && e.getOldValue().equals(true))) {
            if (admin) {
                admin = false;
            } else {
                admin = true;
            }
            FacesContext.getCurrentInstance().renderResponse();
        }
    }

    public void pierwszaStrona() {
        tabela.setFirst(0);
    }

    public void poprzedniaStrona() {
        tabela.setFirst(tabela.getFirst() - tabela.getRows());
    }

    public void nastepnaStrona() {
        tabela.setFirst(tabela.getFirst() + tabela.getRows());
    }

    public void ostatniaStrona() {
        int ile = tabela.getRowCount();
        int wiersze = tabela.getRows();
        tabela.setFirst(ile - ((ile % wiersze != 0) ? ile % wiersze : wiersze));
    }

    public void sprawdzLogin(FacesContext fc, UIComponent c, Object value) {
        Locale loc = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        ResourceBundle rb = ResourceBundle.getBundle("messages", loc);

        if (value.toString().length() < 6 || value.toString().length() > 32) {
            throw new ValidatorException(new FacesMessage(rb.getString("login.validator_dlugosc")));
        }

        if (!pracownicyEJB.sprawdzLogin(value.toString())) {
            throw new ValidatorException(new FacesMessage(rb.getString("login.validator_dostepnosc")));
        }

    }

    public void sprawdzZdjecie(FacesContext fc, UIComponent c, Object value) {
        Locale loc = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        ResourceBundle rb = ResourceBundle.getBundle("bundles.issi.uz.zgora.pl.messages", loc);

        UploadedFile plik = (UploadedFile) value;
        if (!plik.getContentType().equals("image/jpeg")) {
            throw new ValidatorException(new FacesMessage(rb.getString("zdjecie.validator_typ")));
        }
    }

    /**
     * @return the pracownik
     */
    public PracownicyEntity getPracownik() {
        return pracownik;
    }

    /**
     * @param pracownik the pracownik to set
     */
    public void setPracownik(PracownicyEntity pracownik) {
        this.pracownik = pracownik;
    }

    /**
     * @return the zdjecieZal
     */
    public UploadedFile getZdjecieZal() {
        return zdjecieZal;
    }

    /**
     * @param zdjecieZal the zdjecieZal to set
     */
    public void setZdjecieZal(UploadedFile zdjecieZal) {
        this.zdjecieZal = zdjecieZal;
    }

    /**
     * @return the usunZdjecie
     */
    public boolean isUsunZdjecie() {
        return usunZdjecie;
    }

    /**
     * @param usunZdjecie the usunZdjecie to set
     */
    public void setUsunZdjecie(boolean usunZdjecie) {
        this.usunZdjecie = usunZdjecie;
    }

    /**
     * @return the admin
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * @param admin the admin to set
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * @return the tabela
     */
    public HtmlDataTable getTabela() {
        return tabela;
    }

    /**
     * @param tabela the tabela to set
     */
    public void setTabela(HtmlDataTable tabela) {
        this.tabela = tabela;
    }
}
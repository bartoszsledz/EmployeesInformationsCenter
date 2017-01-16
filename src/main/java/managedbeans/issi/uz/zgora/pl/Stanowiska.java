package managedbeans.issi.uz.zgora.pl;

import ejb.issi.uz.zgora.pl.StanowiskaBean;
import entities.issi.uz.zgora.pl.StanowiskaEntity;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;
import javax.annotation.Resources;

@Named
@RequestScoped
public class Stanowiska implements Serializable {

    @EJB(name = "stanowiskaBean")
    private StanowiskaBean stanowiskaEJB;
    private StanowiskaEntity stanowisko;

    public Stanowiska() {

        stanowisko = new StanowiskaEntity();
    }

    public StanowiskaEntity getStanowisko() {
        return stanowisko;
    }

    public void setStanowisko(StanowiskaEntity stanowisko) {
        this.stanowisko = stanowisko;
    }

    public String Dodaj() {
        stanowiskaEJB.Dodaj(stanowisko);
        return "stanowiska";
    }

    public void Usun(int id) {
        stanowiskaEJB.Usun(id);
    }

    public String Pobierz(int id) {
        stanowisko = stanowiskaEJB.Pobierz(id);
        return "edytujStanowisko";
    }

    public List<StanowiskaEntity> getStanowiska() {
        return stanowiskaEJB.PobierzStanowiska();
    }

    public String Edytuj() {
        stanowiskaEJB.Edytuj(stanowisko);
        return "stanowiska";
    }
}
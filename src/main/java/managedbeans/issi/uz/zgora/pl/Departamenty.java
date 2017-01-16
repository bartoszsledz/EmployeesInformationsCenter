package managedbeans.issi.uz.zgora.pl;

import ejb.issi.uz.zgora.pl.DepartamentyBean;
import entities.issi.uz.zgora.pl.DepartamentyEntity;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Departamenty implements Serializable {

    @EJB(name="departamentyBean")
    private DepartamentyBean departamentyEJB;
    private DepartamentyEntity departament;

    public Departamenty() {
    	
    	departament = new DepartamentyEntity();
    }

    public DepartamentyEntity getDepartament() {
        return departament;
    }

    public void setDepartament(DepartamentyEntity departament) {
        this.departament = departament;
    }
    
    public String Dodaj ()
    {
        departamentyEJB.Dodaj(departament);
        return "departamenty";
    }

    public void Usun (int id)
    {
        departamentyEJB.Usun(id);
    }

    public String Pobierz (int id)
    {
        departament = departamentyEJB.Pobierz(id);
        return "edytujDepartament";
    }

    public List<DepartamentyEntity> getDepartamenty()
    {
        return departamentyEJB.PobierzDepartamenty();
    }

    public String Edytuj ()
    {
        departamentyEJB.Edytuj(departament);
        return "departamenty";
    }
}
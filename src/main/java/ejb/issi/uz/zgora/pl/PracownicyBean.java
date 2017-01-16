package ejb.issi.uz.zgora.pl;

import entities.issi.uz.zgora.pl.GrupyEntity;
import entities.issi.uz.zgora.pl.PracownicyEntity;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.Query;

@Stateless
@LocalBean
public class PracownicyBean {

    @PersistenceContext(name="JavaWebProjectPU")
    private EntityManager em;

    public void Dodaj (PracownicyEntity pracownik)
    {
        if(pracownik.getLogin() != null)
        {
                GrupyEntity grupa = new GrupyEntity();
                grupa.setLogin(pracownik.getLogin());
                grupa.setGrupa("zalogowany");
                em.persist(grupa);
        }
        em.persist(pracownik);
    }

    public List<PracownicyEntity> PobierzPracownikow ()
    {
        Query query = em.createNamedQuery("PracownicyEntity.findAll");
        return query.getResultList();
    }

    public void Usun (int id)
    {
        PracownicyEntity pracownik = em.find(PracownicyEntity.class, id);
        em.remove(pracownik);
    }

    public PracownicyEntity Pobierz (int id)
    {
        return em.find(PracownicyEntity.class, id);
    }

    public void Edytuj (PracownicyEntity pracownik)
    {
        em.merge(pracownik);
    }

    public boolean sprawdzLogin(String login)
    {
        Query query = em.createNamedQuery("PracownicyEntity.findByLogin");
        query.setParameter("login", login);
        if(query.getResultList().isEmpty()) return true;
        return false;
    }
    
}
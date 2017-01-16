package ejb.issi.uz.zgora.pl;

import entities.issi.uz.zgora.pl.StanowiskaEntity;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.Query;

@Stateless
@LocalBean
public class StanowiskaBean {

    @PersistenceContext(name="JavaWebProjectPU")
    private EntityManager em;

    public void Dodaj (StanowiskaEntity stanowisko)
    {
        em.persist(stanowisko);
    }

    public List<StanowiskaEntity> PobierzStanowiska ()
    {
        Query query = em.createNamedQuery("StanowiskaEntity.findAll");
        return query.getResultList();
    }

    public void Usun (int id)
    {
        StanowiskaEntity stanowisko = em.find(StanowiskaEntity.class, id);
        em.remove(stanowisko);
    }

    public StanowiskaEntity Pobierz (int id)
    {
        return em.find(StanowiskaEntity.class, id);
    }

    public void Edytuj (StanowiskaEntity stanowisko)
    {
        em.merge(stanowisko);
    }
    
}
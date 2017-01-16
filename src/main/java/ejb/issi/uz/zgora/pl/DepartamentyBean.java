package ejb.issi.uz.zgora.pl;

import entities.issi.uz.zgora.pl.DepartamentyEntity;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.Query;

@Stateless
@LocalBean
public class DepartamentyBean {

    @PersistenceContext(name="JavaWebProjectPU")
    private EntityManager em;

    public void Dodaj (DepartamentyEntity departament)
    {
        em.persist(departament);
    }

    public List<DepartamentyEntity> PobierzDepartamenty ()
    {
        Query query = em.createNamedQuery("DepartamentyEntity.findAll");
        return query.getResultList();
    }

    public void Usun (int id)
    {
        DepartamentyEntity departament = em.find(DepartamentyEntity.class, id);
        em.remove(departament);
    }

    public DepartamentyEntity Pobierz (int id)
    {
        return em.find(DepartamentyEntity.class, id);
    }

    public void Edytuj (DepartamentyEntity departament)
    {
        em.merge(departament);
    }
    
}
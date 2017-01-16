package entities.issi.uz.zgora.pl;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "grupy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupyEntity.findAll", query = "SELECT g FROM GrupyEntity g"),
    @NamedQuery(name = "GrupyEntity.findByLogin", query = "SELECT g FROM GrupyEntity g WHERE g.login = :login"),
    @NamedQuery(name = "GrupyEntity.findByGrupa", query = "SELECT g FROM GrupyEntity g WHERE g.grupa = :grupa")})
public class GrupyEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Size(min = 1, max = 32)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "grupa")
    private String grupa;
    @JoinColumn(name = "login", referencedColumnName = "login", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private PracownicyEntity pracownicyEntity;

    public GrupyEntity() {
    }

    public GrupyEntity(String login) {
        this.login = login;
    }

    public GrupyEntity(String login, String grupa) {
        this.login = login;
        this.grupa = grupa;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public PracownicyEntity getPracownicyEntity() {
        return pracownicyEntity;
    }

    public void setPracownicyEntity(PracownicyEntity pracownicyEntity) {
        this.pracownicyEntity = pracownicyEntity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (login != null ? login.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupyEntity)) {
            return false;
        }
        GrupyEntity other = (GrupyEntity) object;
        if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.issi.uz.zgora.pl.GrupyEntity[ login=" + login + " ]";
    }
    
}







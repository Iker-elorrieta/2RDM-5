package modelo;
// Generated 13 ene 2025, 12:32:46 by Hibernate Tools 6.5.1.Final

/**
 * Horarios generated by hbm2java
 */
public class Horarios implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private HorariosId id;
    private Modulos modulos;
    private Users users;

    public Horarios() {
    }

    public Horarios(HorariosId id, Modulos modulos, Users users) {
        this.id = id;
        this.modulos = modulos;
        this.users = users;
    }

    public HorariosId getId() {
        return this.id;
    }

    public void setId(HorariosId id) {
        this.id = id;
    }

    public Modulos getModulos() {
        return this.modulos;
    }

    public void setModulos(Modulos modulos) {
        this.modulos = modulos;
    }

    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

}
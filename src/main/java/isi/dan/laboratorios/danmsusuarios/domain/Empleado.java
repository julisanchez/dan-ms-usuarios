package isi.dan.laboratorios.danmsusuarios.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Integer id;
    private String mail;
    @OneToOne
    @JoinColumn(name = "USUARIO_ID")
    private Usuario user;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Usuario getUser() {
        return this.user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

}

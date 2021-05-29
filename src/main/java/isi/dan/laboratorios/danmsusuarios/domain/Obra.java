package isi.dan.laboratorios.danmsusuarios.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Obra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descripcion;
    private Float latitud;
    private Float longitud;
    private String direccion;
    private Integer superficie;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "TIPO_ID")
    TipoObra tipo;
    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID")
    Cliente cliente;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getLatitud() {
        return this.latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return this.longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getSuperficie() {
        return this.superficie;
    }

    public void setSuperficie(Integer superficie) {
        this.superficie = superficie;
    }

    public TipoObra getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoObra tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}

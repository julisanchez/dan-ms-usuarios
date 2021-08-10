package isi.dan.laboratorios.danmsusuarios.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Cliente {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   private String razonSocial;
   private String cuit;
   private String mail;
   private Double maxCuentaCorriente;
   private Boolean habilitadoOnline;
   private LocalDateTime fechaBaja;
   @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
   private List<Obra> obras;
   @OneToOne(cascade = CascadeType.PERSIST)
   @JoinColumn(name = "USUARIO_ID")
   private Usuario usuario;

   public Integer getId() {
      return this.id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getRazonSocial() {
      return this.razonSocial;
   }

   public void setRazonSocial(String razonSocial) {
      this.razonSocial = razonSocial;
   }

   public String getCuit() {
      return this.cuit;
   }

   public void setCuit(String cuit) {
      this.cuit = cuit;
   }

   public String getMail() {
      return this.mail;
   }

   public void setMail(String mail) {
      this.mail = mail;
   }

   public Double getMaxCuentaCorriente() {
      return this.maxCuentaCorriente;
   }

   public void setMaxCuentaCorriente(Double maxCuentaCorriente) {
      this.maxCuentaCorriente = maxCuentaCorriente;
   }

   public Boolean isHabilitadoOnline() {
      return this.habilitadoOnline;
   }

   public Boolean getHabilitadoOnline() {
      return this.habilitadoOnline;
   }

   public void setHabilitadoOnline(Boolean habilitadoOnline) {
      this.habilitadoOnline = habilitadoOnline;
   }

   public List<Obra> getObras() {
      return this.obras;
   }

   public void setObras(List<Obra> obras) {
      this.obras = obras;
   }

   public Usuario getUsuario() {
      return this.usuario;
   }

   public void setUsuario(Usuario usuario) {
      this.usuario = usuario;
   }

   public LocalDateTime getFechaBaja() {
      return this.fechaBaja;
   }

   public void setFechaBaja(LocalDateTime fechaBaja) {
      this.fechaBaja = fechaBaja;
   }

}

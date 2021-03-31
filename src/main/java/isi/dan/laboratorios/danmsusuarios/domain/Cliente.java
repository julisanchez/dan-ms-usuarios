package isi.dan.laboratorios.danmsusuarios.domain;

import java.util.List;

public class Cliente {
   private Integer id;
   private String razonSocial;
   private String cuit;
   private String mail;
   private Double maxCuentaCorriente;
   private Boolean habilitadoOnline;
   private List<Obra> obras;
    

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

}

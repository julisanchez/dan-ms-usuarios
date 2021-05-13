package isi.dan.laboratorios.danmsusuarios.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DetallePedido {

	@Id
	private Integer id;
	@ManyToOne
	@JoinColumn(name="PRODUCTO_ID")
	Producto producto;
	private Integer cantidad;
	private Double precio;
	@ManyToOne()
	@JoinColumn(name="PEDIDO_ID")
	private Pedido pedido;

	public Integer getId() {
		return id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}

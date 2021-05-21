package isi.dan.laboratorios.danmsusuarios.domain;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Instant fechaPedido;
	@ManyToOne
	private Obra obra;
	@OneToMany(mappedBy = "pedido")
	private List<DetallePedido> detalle;
	@OneToOne
	@JoinColumn(name = "ESTADO_ID")
	private EstadoPedido estado;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Instant getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Instant fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Obra getObra() {
		return obra;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}

	public List<DetallePedido> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<DetallePedido> detalle) {
		this.detalle = detalle;
	}

	public EstadoPedido getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}

}

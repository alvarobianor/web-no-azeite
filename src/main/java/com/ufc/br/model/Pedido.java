package com.ufc.br.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Pedido implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Pessoa idCliente;
	
	@ManyToMany
	@JoinTable(name = "pedido_prato", 
	joinColumns = @JoinColumn(name="pedido"),
	inverseJoinColumns = @JoinColumn(name="prato"))
	private List<Prato> pratos = new ArrayList<>();
	
	private double total;
	
	public Pedido() {}
	
	public Pedido(TEMP_Pedido t, Pessoa c) {
		this.id = t.getId();
		this.pratos = t.getPratos();
		this.idCliente = c;
	}

	
	public Pedido(TEMP_Pedido t) {
		this.id = t.getId();
		this.pratos = t.getPratos();
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public Pessoa getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Pessoa idCliente) {
		this.idCliente = idCliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

	
	  public List<Prato> getPratos() { return pratos; }
	  
	  
	  public void setPratos(List<Prato> pratos) { this.pratos = pratos; }
	 
	
	
	
	//metodos personalizados meus
	
	
	  public void addPrato(Prato prato) {
	  
	  this.getPratos().add(prato);
	  
	  }

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	 
	
	
}

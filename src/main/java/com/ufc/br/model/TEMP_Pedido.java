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
import javax.persistence.OneToMany;

@Entity
public class TEMP_Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToMany
	@JoinTable(name = "TEMP_pedido_prato", joinColumns = @JoinColumn(name = "pedido"), inverseJoinColumns = @JoinColumn(name = "prato"))
	private List<Prato> pratos = new ArrayList<>();

	public TEMP_Pedido() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Prato> getPratos() {
		return pratos;
	}

	public void setPratos(List<Prato> pratos) {
		this.pratos = pratos;
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
		TEMP_Pedido other = (TEMP_Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void addPrato(Prato prato) {

		this.getPratos().add(prato);

	}
	
	public boolean deletePrato(Integer id) {
		for(Prato p : this.getPratos()) {
			if(p.getId()==id) {
				if(this.getPratos().remove(p))
					return true;
			}
		}
		return false;
	}
	

}

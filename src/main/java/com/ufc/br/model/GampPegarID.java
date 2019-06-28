package com.ufc.br.model;

import java.io.Serializable;

public class GampPegarID implements Serializable{

	private Integer cod;
	
	public GampPegarID() {}
	
	public GampPegarID(Integer id) {this.cod=id;}

	public Integer getId() {
		return cod;
	}

	public void setId(Integer id) {
		this.cod = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cod == null) ? 0 : cod.hashCode());
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
		GampPegarID other = (GampPegarID) obj;
		if (cod == null) {
			if (other.cod != null)
				return false;
		} else if (!cod.equals(other.cod))
			return false;
		return true;
	}
	
	
}

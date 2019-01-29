package com.bugtracker.entity.base;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseDomainObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Long id;

	@javax.persistence.Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
    public int hashCode() {
		int hash = 13;
		hash = (hash * 7) + id.hashCode();

		return hash;
    }

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (this==obj) {
			return true;
		}
		if (obj==null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BaseDomainObject others = (BaseDomainObject) obj;
		if (id==null) {
			if (others.id !=null) {
				return false;
			}
		}else if (!id.equals(others.id)) {
			return false;
		}
		return true;
	}

}


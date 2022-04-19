package com.reviewserivce.persitance.db.objects;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subscriptions")
public class DBSubscription {

	@Id
	private String id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column
	private String description;

	public DBSubscription() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DBSubscription(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBSubscription other = (DBSubscription) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "DBSubscriptions [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

}

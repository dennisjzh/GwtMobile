package com.gwtmobile.persistence.test.domain;

import com.gwtmobile.persistence.client.Collection;
import com.gwtmobile.persistence.client.Persistable;

public interface Category extends Persistable {
	public String getName();				// Property
	public void setName(String name);
	public Collection<Task> getTasks();		// One to many relationship
}

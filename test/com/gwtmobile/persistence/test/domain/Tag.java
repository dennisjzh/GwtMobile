package com.gwtmobile.persistence.test.domain;

import com.gwtmobile.persistence.client.Collection;
import com.gwtmobile.persistence.client.Persistable;

public interface Tag extends Persistable {
	public String getName();
	public void setName(String name);	
	public Collection<Task> getTasks();		// Many to many relationship
}

package com.gwtmobile.persistence.test.domain;

import java.util.Date;

import com.google.gwt.json.client.JSONObject;
import com.gwtmobile.persistence.client.Collection;
import com.gwtmobile.persistence.client.Persistable;


public interface Task extends Persistable {
	public String getName();
	public void setName(String name);
	public String getDescription();
	public void setDescription(String description);
	public boolean getDone();	
	public void setDone(boolean done);
	public void setCompleteDate(Date date);
	public Date getCompleteDate();
	public void setPriority(int priority);
	public int getPriority();
	public void setPercentage(float percentage);
	public float getPercentage();
	public void setProfit(double profit);
	public double getProfit();
	public void setAlphabet(char alphabet);
	public char getAlphabet();
	public void setJson(JSONObject aJson);
	public JSONObject getJson();
	public Category getCategory();				//many to one relationship
	public void setCategory(Category category);
	public Collection<Tag> getTags();			//many to many relationship	
}

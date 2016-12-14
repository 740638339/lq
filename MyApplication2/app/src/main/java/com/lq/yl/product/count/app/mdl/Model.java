package com.lq.yl.product.count.app.mdl;

import java.io.Serializable;

public class Model implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6826377936846157039L;
	
	private String mID;
	
	public void setID(String id){
		this.mID = id;
	}
	public String getID(){
		return this.mID;
	}
}

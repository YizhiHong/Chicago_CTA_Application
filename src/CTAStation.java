/*
 * This is a class for a CTA Station.
 * 
 * Author: Yizhi Hong
 * Date: Dec 1,2016
 * 
 * */

import java.util.ArrayList;

public class CTAStation extends GeoLocation {
	private String name;
	private String location;
	private boolean wheelchair;
	private ArrayList<Integer> orderNum;
	private boolean isTransfer;
	private ArrayList<String> belongsTo;
	private int totalID;
	
	public CTAStation(){
		name = "no";
		location = " ";
		wheelchair = false;
		isTransfer = false;
		totalID = -1;
	}
	/*
	 * initial a empty CTAstation with Some detail
	 * */
	public CTAStation(String name,double latitute,double longitute,String location,boolean wheelchair){
		this.name = name;
		this.location = location;
		this.wheelchair = wheelchair;
		this.latitute = latitute;
		this.longitute = longitute;
		this.belongsTo = new ArrayList<String>();
		this.orderNum = new ArrayList<Integer>();
		totalID = -1;
	}
	
	/*
	 * 
	 * */

	public ArrayList<String> getBelongsTo() {
		return belongsTo;
	}

	public void setBelongsTo(ArrayList<String> belongsTo) {
		this.belongsTo = belongsTo;
	}
	/*
	 * add this station belongs to certain route
	 * */
	public int addBelongsTo(String a){
		this.belongsTo.add(a);
		return belongsTo.size()-1;
		
	}
	
	/*
	 * get this station belongs to a route, and return this route’s index
	 * */
	public int getBelongsTo(String a){
		for(int i = 0; i< belongsTo.size(); i++){
			if(belongsTo.get(i).equalsIgnoreCase(a)){
				return i;
			}
		}
		return 0;
		
	}
	
	/*
	 * remove a relationship with route
	 * */
	public void removeBelongsTo(String a){
		this.belongsTo.remove(a);
	}

	public ArrayList<Integer> getOrderNum() {
		return orderNum;
	}
	
	/*
	 * add order number after insert(parallel array list with belongs to)
	 * */
	public int addOrderNum(int a){
		orderNum.add(a);
		setTransfer();
		return orderNum.size()-1;
	}
	
	/*
	 * get this station index with route, and return it’s index
	 * */
	public int getOrderNum(int pos) {
		return orderNum.get(pos);
	}

	/*
	 * remove a relationship index with route
	 * */
	public void removeOrderNum(int a){
		this.orderNum.remove(a);
	}
	
	public void setOrderNum(ArrayList<Integer> orderNum) {
		this.orderNum = orderNum;
	}
	
	public void setOrderNum(int index,int orderNum) {
		this.orderNum.set(index,orderNum);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isWheelchair() {
		return wheelchair;
	}

	public void setWheelchair(boolean wheelchair) {
		this.wheelchair = wheelchair;
	}

	public boolean isTransfer() {
		return isTransfer;
	}
	
	public void setTransfer() {
		if(this.belongsTo.size() > 1) this.isTransfer = true;
	}
	
	public void setTransfer(boolean isTransfer) {
		this.isTransfer = isTransfer;
	}
	
	public int getTotalID() {
		return totalID;
	}
	
	public void setTotalID(int totalID) {
		this.totalID = totalID;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CTAStation other = (CTAStation) obj;
		if (isTransfer != other.isTransfer)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderNum != other.orderNum)
			return false;
		if (wheelchair != other.wheelchair)
			return false;
		return true;
	}

	public String toString() {
		return name + " [location=" + location + ", wheelchair=" + wheelchair + ", orderNum="
				+ orderNum + ", isTransfer=" + isTransfer + ", canTransferTo=" + belongsTo + " ] ";
	}

	/*
	 * calculate a distance with latitude and longitude’s input.
	 * */
	public double calcDistance(double la,double lt){
		double distance = Math.sqrt(Math.pow((la - latitute),2) + Math.pow((lt - longitute),2));
		return distance;
	};
	
	/*
	 * calculate a distance with location’s input(polymorphism)
	 * */
	public double calcDistance(GeoLocation l){
		double distance = Math.sqrt(Math.pow((l.latitute - latitute),2) + Math.pow((l.longitute - longitute),2));
		return distance;
	};
	
	/*
	 * calculate a distance with station input(polymorphism)
	 * */
	public double calcDistanceByStation(CTAStation S){
		double distance = Math.sqrt(Math.pow((S.latitute - latitute),2) + Math.pow((S.longitute - longitute),2));
		return distance;
	}

}

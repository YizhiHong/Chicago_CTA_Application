/*
 * A class handle geolocation
 * 
 * Author: Yizhi Hong
 * Date: Dec 1,2016
 * 
 * */

public abstract class GeoLocation {
	protected double latitute;
	protected double longitute;
	
	public GeoLocation(){
		latitute = 0;
		longitute = 0;
	}
	
	/*
	 * initial a location
	 * */
	public GeoLocation(double la,double lo){
		latitute = la;
		longitute = lo;
	}

	public double getLatitute() {
		return latitute;
	}

	public void setLatitute(double latitute) {
		this.latitute = latitute;
	}

	public double getLongitute() {
		return longitute;
	}

	public void setLongitute(double longitute) {
		this.longitute = longitute;
	}

	public String toString() {
		return "GeoLocation [latitute=" + latitute + ", longitute=" + longitute + "]";
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeoLocation other = (GeoLocation) obj;
		if (Double.doubleToLongBits(latitute) != Double.doubleToLongBits(other.latitute))
			return false;
		if (Double.doubleToLongBits(longitute) != Double.doubleToLongBits(other.longitute))
			return false;
		return true;
	}
	/*calculate a distance with la and lt input(polymorphism)*/
	public abstract double calcDistance(double la,double lt);
	/*calculate a distance with location’s input(polymorphism)*/
	public abstract double calcDistance(GeoLocation l);
}

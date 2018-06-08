/*
 * This is a class for a CTA Route.
 * 
 * Author: Yizhi Hong
 * Date: Dec 1,2016
 * 
 * */


import java.util.ArrayList;

public class CTARoute {
	private ArrayList<CTAStation> route;
	private String RouteName;
	private int RouteNumber;
	private String loopStation;

	public CTARoute(){
		route = new ArrayList<CTAStation>();
		RouteName = "no";
		RouteNumber = 0;
		loopStation =null;
	}
	/*
	*initial a empty route with name and Id	
	**/
	public CTARoute(String name,int num){
		route = new ArrayList<CTAStation>();
		RouteName = name;
		RouteNumber = num;
		loopStation =null;
	}
	/*
	 * initial a route with name and Id, also can contain a station ArrayList
	 * */
	public CTARoute(ArrayList<CTAStation> station,String name,int num){
		route = new ArrayList<CTAStation>();
		for(CTAStation c: station){
			route.add(c);
		}
		RouteName = name;
		RouteNumber =num;
		loopStation =null;
	}
	/*
	 *get a station index
	 * */
	public int getPosition(String name){
		for(int i=0;i<route.size();i++){
			if(route.get(i).getName().equalsIgnoreCase(name)){
				return i;
			}
		}
		return -1;
	}
	/*
	 *add station method and it’s index.
	 * */
	public boolean addStation(CTAStation station,int index){
		if(route.size() < 1){
			route.add(station);
		}else{
			for(int i=0; i< route.size(); i++){
				int thisNum= route.get(i).getBelongsTo(RouteName);
				if(route.get(i).getOrderNum(thisNum) > station.getOrderNum(index)){
					route.add(i,station);
					
					return true;
				}
			}
			route.add(station);
		}
		//handle loop

		return true;
	}
	/*
	 * set the loop situation after all station ready
	 * */
	public void setLoop(){
		int index;
		if(loopStation != null){
			for(index = getPosition(loopStation); index >= 0 ;index--){
				this.route.add(this.route.get(index));
			}
		}
//		this.refreshOrderNum();
	}
	/*
	 * refresh index number after adding station or deleting station
	 * */
	public void refreshOrderNum(){
		for(int i=0;i< route.size(); i++){
			int thisNum= route.get(i).getBelongsTo(RouteName);
			route.get(i).setOrderNum(thisNum,i);
			
		}
	}
	/*
	 * delete a station in this route
	 * */
	public boolean deleteStation(CTAStation station){
		if(route.size() < 1){
			System.out.println("There is no station!");
			return false;
		}else{
			if(route.remove(station)){
				this.displayAllStation();
				return true;
			}else{
				System.out.println("Station not found!");
				return false;
			}
		}
	}
	/*
	 * display All Station
	 * */
	public void displayAllStation(){
		System.out.println(RouteName+":");
		for(int i=0;i<route.size();i++){
			System.out.println((i+1) + "-" + route.get(i).getName());
		}
	}
	/*
	 * Display all station with detail
	 * */
	public void displayAllStationInfo(){
		System.out.println(RouteName+" Line:");
		for(int i=0;i<route.size();i++){
			System.out.println((i+1) + "." + route.get(i).toString());
		}
	}
	/*
	 * A important method for generating a route between two station(only in same route)
	 *  and return a arrayList for this new route.
	 * */
	public ArrayList<CTAStation> getBetweenStation(CTAStation Start,CTAStation End){
		int s = this.route.indexOf(Start);
		int e = this.route.indexOf(End);
		ArrayList<CTAStation> buff = new ArrayList<CTAStation>();
		if(s > e){
			for (int i = s; i >= e ;i--){
				buff.add(route.get(i));
			}
		}else{
			for (int i = s; i <= e ;i++){
				buff.add(route.get(i));
			}
		}
		return buff;
	}
	/*
	 * look up a station with information
	 * */
	public void lookupStation(String name){
		int i = getPosition(name);
		if (i > -1){
			System.out.println(RouteName +": "+route.get(i).toString());
		}else{
			System.out.println("not found in "+ RouteName);
		}
	}
	/*
	 * get a nearest station 
	 * */
	public void nearestStation(double la,double lo){

		System.out.println("Here are the nearest stations for "+RouteName+": ");
	
		double minDistance = route.get(0).calcDistance(la,lo);
		String name = route.get(0).getName();
		for(int i=0;i<route.size();i++){
			if( minDistance > route.get(i).calcDistance(la,lo)){
				minDistance = route.get(i).calcDistance(la,lo);
				name = route.get(i).getName();
			}
		}
		
		System.out.println(name + ": " + minDistance);
	}
	/*
	 * display wheelchair station
	 * */
	public void displaybyWheelchair(){
		for(int i=0;i<route.size();i++){
			if(route.get(i).isWheelchair()){
				System.out.println((i+1) + "." + route.get(i).getName());
			}
		}
	}
		
	
	public ArrayList<CTAStation> getRoute() {
		return route;
	}

	public void setRoute(ArrayList<CTAStation> route) {
		this.route = route;
	}

	public int getRouteNumber() {
		return RouteNumber;
	}

	public void setRouteNumber(int routeNumber) {
		RouteNumber = routeNumber;
	}

	public ArrayList<CTAStation> getLine() {
		return route;
	}

	public void setLine(ArrayList<CTAStation> route) {
		this.route = route;
	}

	public String getRouteName() {
		return RouteName;
	}
	
	public void setRouteName(String routeName) {
		RouteName = routeName;
	}

	public String getLoopStation() {
		return loopStation;
	}

	public void setLoopStation(String loopStation) {
		this.loopStation = loopStation;
	}

	public String toString() {
		return "CTARoute [route=" + route + ", RouteName=" + RouteName + "]";
	}
	

	
}

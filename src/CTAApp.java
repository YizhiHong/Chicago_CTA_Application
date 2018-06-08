/*
 * This is the application class. The main class
 * 
 * Author: Yizhi Hong
 * Date: Dec 1,2016
 * 
 * */

public class CTAApp {

	public static void main(String[] args) {
		//Start a CTASystem, load CVS file
		CTASystem Chicago = new CTASystem("CHICAGO","./src/CTAStops.csv");
		
		//prompt option menu for user and start this app
		Chicago.app();
	}

}

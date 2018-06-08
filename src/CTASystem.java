/*
 * This is a class for a CTA System.
 * all main method are handled by this class
 * 
 * Author: Yizhi Hong
 * Date: Dec 1,2016
 * 
 * */


import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class CTASystem {
	private ArrayList<CTARoute> routeList;
	private ArrayList<CTAStation> allStation;
	private String city;
	
	public CTASystem(){
		routeList = new ArrayList<CTARoute>();
		allStation = new ArrayList<CTAStation>();
		city ="";
	}
	/*
	 *  initial a city CTA System with empty station list and route and pass cvs 
	 *  */
	public CTASystem(String city,String url){
		routeList = new ArrayList<CTARoute>();
		allStation = new ArrayList<CTAStation>();
		this.city = city;
		initData(url);
	}
	/*
	 *  initial Data from a CVS file.
	 *  1.Route list. 
	 *  2.add all station in CTASystem station list. 
	 *  3.at the same time, add these station to a specific route.
	 *  4.handle loop situation.
	 *  
	 *  */
	private void initData(String url){
        String line = "";
        String cvsSplitBy = ",";
        int count=0;
        try (BufferedReader br = new BufferedReader(new FileReader(url))) {

            while ((line = br.readLine()) != null) {
            	
                // use comma as separator
            	
                String[] SInfo = line.split(cvsSplitBy);
                if(SInfo[0].equalsIgnoreCase("name")){
                	for(int i = 5;i< SInfo.length;i++){
                		int orderNum = i - 5;
                		String routeName = SInfo[i].split(":")[0];
                		routeList.add(new CTARoute(routeName,orderNum));
                	}
                	
                }else if(SInfo[0].equalsIgnoreCase("null")){
                	for(int i = 5;i< SInfo.length;i++){
                		if(!SInfo[i].equalsIgnoreCase("null")){
                    		int orderNum = i - 5;
                    		String loopStation = SInfo[i];
                    		routeList.get(orderNum).setLoopStation(loopStation);
                		}
                	}
                	
                }else{
                	
                	// add all station in CTASystem.
                	CTAStation newStation = new CTAStation(SInfo[0],Double.parseDouble(SInfo[1]),Double.parseDouble(SInfo[2]),SInfo[3],Boolean.parseBoolean(SInfo[4]));
                	newStation.setTotalID(count);
                	this.allStation.add(newStation);
                	count ++;
                	
                	//get the last station's position;
                	int last = allStation.size() - 1;
                	
                	for(int i = 5; i < SInfo.length;i++){
                		//the position of station in a Route
                		int routeNum = Integer.parseInt(SInfo[i]);
                		
                		if( routeNum!=-1){
                			//index for route list;
                			int index = i - 5;
                			String routeName = routeList.get(index).getRouteName();
                    		//add this station belongs to this route and label his orderNumber. return the routeIndex for station
                			allStation.get(last).addBelongsTo(routeName);
                			
                			int routeIndex = allStation.get(last).addOrderNum(routeNum);
                			//add this station to this route.
                			routeList.get(index).addStation(allStation.get(last),routeIndex);
                		}
                	}
                
                }
            }
            for(CTARoute r: routeList){
            	r.setLoop();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	/*
	 * print main menu to console
	 *  */
	public void menu(){
		System.out.println("");
		System.out.println("1.Display all route");
		System.out.println("2.Display all station with route");
		System.out.println("3.Display all station in specific route with detail");
		System.out.println("4.Find a station");
		System.out.println("5.Find the nearest station");
		System.out.println("6.Find the route between two stations");
		System.out.println("7.Add a new station");
		System.out.println("8.Modify a station");
		System.out.println("9.Delete an existing station");
		System.out.println("0.exit");
	}
	/*
	 * The start of the app. keep prompt menu and handle different option	
	 *  */
	public void app(){
		
		System.out.println("\nWelcome to CTA! please enter following option:");
		Scanner input = new Scanner(System.in);
		boolean flag = false;
		while(!flag){
			menu();
			try{
				int in =  Integer.parseInt(input.nextLine());
				if (in == 1){
					DisplayAllRoute();
				}else if(in == 2){
					DisplayAllStationWithRoute();
				}else if(in == 3){
					DisplayAllStationWithRoute(input);
				}else if(in == 4){
					FindStation(input);
				}else if(in == 5){
					FindNearestStation(input);
				}else if(in == 6){
					getRouteBetweenStation(input);
				}else if(in == 7){
					addStation(input);
				}else if(in == 8){
					ModifyStation(input);
				}else if(in == 9){
					DelectStation(input);
				}else if(in == 10){
					displayAllStation();
				}else if(in == 0){
					flag = true;
					System.out.println("Thank you for use CTA System! Good Bye");
				}
			} catch(Exception e){
				System.out.println("wrong input!!! please enter again");
			}
			
		}
		input.close();
		
	}
	/*
	 * Display all route name	
	 *  */
	public void DisplayAllRoute(){
		for(CTARoute c: routeList){
			System.out.println(c.getRouteName());
		}
		
	}
	/*
	 * Display all station with detail
	 *  */
	public void displayAllStation(){
		for(CTAStation c: allStation){
			System.out.println(c.toString());
		}
	}
	/*
	 * Scan a route name, print all station from this route
	 * */
	public void DisplayAllStationWithRoute(Scanner input){
		boolean flag = false;
		while (!flag){
			try{
				System.out.println("please enter a name (example:red/green/blue/brown/purple/pink/orange/yellow)");
				String name= input.nextLine();
				boolean found = false;
				for(CTARoute c: routeList){
					if(c.getRouteName().equalsIgnoreCase(name)){
						c.displayAllStationInfo();
						found = true;
					}
				}
				if(found){ System.out.println(name + " found it above");}else{ System.out.println("There is no route called "+name);}
				flag = true;
			}catch(Exception e){
				System.out.println("wrong input! please enter again");
			}
		}

		
	}
	/*
	 * enter a station name and print all station found
	 * */
	public void FindStation(Scanner input){
		boolean flag = false;
		while (!flag){
			try{
				System.out.println("please enter a name to check");
				String name= input.nextLine();
				boolean found = false;
				for(CTAStation s: allStation){
					if(s.getName().equalsIgnoreCase(name)){
						System.out.println(s.toString());
						found = true;
					}
				}
				if(found){ System.out.println(name + " found it above");}else{ System.out.println("There is no route called "+name);}
				flag = true;
			}catch(Exception e){
				System.out.println("wrong input! please enter again");
			}
		}
	}
	/*
	 * enter latitude and longitude and print a nearest station.
	 * */
	public void FindNearestStation(Scanner input){
		try{
			System.out.println("Please enter a latitude:");
			double la =  Double.parseDouble(input.nextLine());
			System.out.println("Please enter a longitute:");
			double lo =  Double.parseDouble(input.nextLine());
			
			double minDistance = allStation.get(0).calcDistance(la,lo);
			CTAStation buff = allStation.get(0);
			for(int i=0;i<allStation.size();i++){
				if( minDistance > allStation.get(i).calcDistance(la,lo)){
					minDistance = allStation.get(i).calcDistance(la,lo);
					buff = allStation.get(i);
				}
			}
			
			System.out.println("There nearest station to the location is " + buff.toString());
			System.out.println("The distance is " + minDistance);
		} catch(Exception e){
			System.out.println("wrong input! please enter again");
		}
	
	}
	/*
	 * enter two station name and print the shortest route.
	 * (can handle transfer two times )
	 * */
	private void getRouteBetweenStation(Scanner input){
		boolean flag = false;
		
		while(!flag){
			try{
				System.out.println("Enter the Starting station");
				String in = input.nextLine();
				CTAStation Start = searchStation(in,input);
				System.out.println("you selected "+ Start.toString());
				
				System.out.println("Enter the Ending station");
				in = input.nextLine();
				CTAStation End = searchStation(in,input);
				System.out.println("you selected "+ End.toString());
				
				
				if (Start.getName().equalsIgnoreCase("no")||End.getName().equalsIgnoreCase("no")){
					System.out.println("There are wrong station");
				}else{
					String ts = compareStationOnLine(Start,End);
					String ts1,ts2;
					int pos,endPos,midPos=0;
					CTAStation Stemp,Stemp2 = null;
					ArrayList<CTAStation> newRoute = null,endRoute =null,midRoute=null;
					ArrayList<CTAStation> Rtemp1 = null,Rtemp2 =null,Rtemp3 =null;
					String color1 = "",color2= "",color3= "";
					int minSize=136;
					boolean Found = false;
					if(!ts.equals("no")){
						// on same route
						pos = this.getRoutePosition(ts);
						newRoute = routeList.get(pos).getBetweenStation(Start,End);
						for(CTAStation c: newRoute){
							System.out.print(" -> " + c.getName());
						}
						flag = true;
					}else{
						for(int i = 0;i < allStation.size();i++){
							Stemp = allStation.get(i);
							if(Stemp.isTransfer()){
								ts = compareStationOnLine(Start,Stemp);
								pos = this.getRoutePosition(ts);
								
								if(!ts.equals("no")){
									ts = compareStationOnLine(Stemp,End);
									if(!ts.equals("no")){
										
										endPos = this.getRoutePosition(ts);
										
										newRoute = routeList.get(pos).getBetweenStation(Start, Stemp);
										endRoute = routeList.get(endPos).getBetweenStation(Stemp, End);
										if( (newRoute.size() + endRoute.size()) < minSize){
											color1 =ts;
											Rtemp1 = newRoute;
											Rtemp3 = endRoute;
											minSize = newRoute.size() + endRoute.size();
										}
										Found = true;
										flag = true;
										
									}else{
										for(int j = 0;j < allStation.size();j++){
											Stemp2 = allStation.get(j);
											ts1 = compareStationOnLine(Stemp2,End);
											if(!ts1.equals("no")){
												ts2 = compareStationOnLine(Stemp2,Stemp);
												if(!ts2.equals("no")){
													midPos = this.getRoutePosition(ts2);
													endPos = this.getRoutePosition(ts1);
													newRoute = routeList.get(pos).getBetweenStation(Start, Stemp);
													midRoute = routeList.get(midPos).getBetweenStation(Stemp, Stemp2);
													endRoute = routeList.get(endPos).getBetweenStation(Stemp2, End);
													if((newRoute.size()+midRoute.size()+endRoute.size() )< minSize){
														color2= ts2;
														color3 = ts1;
														Rtemp1 = newRoute;
														Rtemp2 = midRoute;
														Rtemp3 = endRoute;
														minSize = newRoute.size()+midRoute.size()+endRoute.size();
													}

													flag = true;
													
												}
											}
										}
										
									}
									
								}
								
							}
						}
					}
					if(Rtemp1 == null){
						System.out.println("no Route can be run");
					}else if(Found){
						System.out.println("Shortest way is ");
						for(CTAStation c: Rtemp1){
							System.out.print(" -> " + c.getName());
						}
						System.out.println(" transfer to " + color1 +" Line " );
						for(CTAStation c: Rtemp3){
							System.out.print(" -> " + c.getName());
						}
						System.out.println("");
						System.out.println("you need to take " + (Rtemp1.size()+Rtemp3.size() ) + " station");
					}else{
						System.out.println("Shortest way is ");
						for(CTAStation c: Rtemp1){
							System.out.print(" -> " + c.getName());
						}
						System.out.println(" transfer to " + color2 +" Line " );
						for(CTAStation c: Rtemp2){
							System.out.print(" -> " + c.getName());
						}
						System.out.println(" transfer to " + color3 +" Line " );
						for(CTAStation c: Rtemp3){
							System.out.print(" -> " + c.getName());
						}
						System.out.println("");
						System.out.println("you need to take " + (Rtemp1.size() + Rtemp1.size() + Rtemp1.size())  + " station");
					}

					
				}
			}catch(Exception e){
				System.out.println("wrong input! please enter again");
			}
	
		}
	}
	/*
	 * a inner method for compare two station are on the same route or not.
	 *  if in, return route name, else return “no”.
	 *  it’s for getRouteBetweenStation
	 * */
	private String compareStationOnLine(CTAStation one,CTAStation two){
		for(int i = 0; i< one.getBelongsTo().size()  ;i++){
			for(int j = 0; j< two.getBelongsTo().size()  ;j++){
				if(one.getBelongsTo().get(i).equals(two.getBelongsTo().get(j))){
					return one.getBelongsTo().get(i);
				}
			}
		}
		return "no";
		
	}
	/*
	 * keep asking user information for the new station and insert it in a specific position
	 *  update the order.
	 * */
	private void addStation(Scanner input){
		try{
			System.out.println("Please enter a Route name:");
			String RName =  input.nextLine();
			int Rpos = this.getRoutePosition(RName);
			if(Rpos==-1){
				System.out.println("There is no route named "+RName);
			}else{
				System.out.println("Please enter a new Station name that you want to add it in " + RName+ ":");
				String SName =  input.nextLine();
				if(routeList.get(Rpos).getPosition(SName) !=-1){
					System.out.println("This name are already exist in this route");
				}else{
					System.out.println("Please enter a Latitude:");
					double la =  Double.parseDouble(input.nextLine());
					System.out.println("Please enter a longitute:");
					double lo =  Double.parseDouble(input.nextLine());
					System.out.println("Please enter a location:");
					String loca = input.nextLine();
					System.out.println("Please enter a WheelChair(true or false):");
					boolean wheelchair = Boolean.parseBoolean(input.nextLine());
					System.out.println("Please enter a previous Station that you want to add it after this station:(if enter 'no' it will be the first!)");
					String pStation =  input.nextLine();
					int Spos;
					if(!pStation.equalsIgnoreCase("no")){
						Spos = routeList.get(Rpos).getPosition(pStation);
					}else{
						Spos = -2;
					}
					
					if(Spos==-1){
						System.out.println("There is no Station named "+pStation);
					}else{
						CTAStation newStation = new CTAStation(SName,la,lo,loca,wheelchair);
						allStation.add(newStation);
						newStation.addBelongsTo(RName);
						int OrderNum = newStation.addOrderNum(Spos);
						routeList.get(Rpos).addStation(newStation, OrderNum);
						routeList.get(Rpos).refreshOrderNum();
						routeList.get(Rpos).displayAllStationInfo();
						System.out.println("Done!"+ SName +" added in "+ RName);
					}
				}
			}			
		}catch(Exception e) {
			System.out.println("wrong input! please enter again");
		}
	}
	/*
	 * modify station name and automatically update this station in all route.
	 * */
	private void ModifyStation(Scanner input){
		boolean flag = false;
		while(!flag){
			try{
				System.out.println("please enter a station name");
				String name= input.nextLine();
				ArrayList<CTAStation> buff = new ArrayList<CTAStation>();
				for(CTAStation s: allStation){
					if(s.getName().equalsIgnoreCase(name)){
						buff.add(s);
					}
				}
				if(buff.size() !=0){
					int index;
					if(buff.size() > 1){
						for(int i = 0; i<buff.size();i++){
							System.out.println(buff.size()  +". "+buff.get(i));
						}
						System.out.println("please select a the station you want to modify(enter a number)");
						index = Integer.parseInt(input.nextLine())-1;
					}else{
						index =0;
					}
					System.out.println("please enter a new name for " + buff.get(index));
					String newName =  input.nextLine();
					buff.get(index).setName(newName);
					System.out.println("Done! new station is ");
					DisplayStationByName(newName);
				
				}else{ 
					System.out.println("There is no station called " + name);
				}
				flag = true;
			}catch (Exception e){
				System.out.println("wrong input! please re-enter a station you want to modidy again!");
			}
			
		}

		
	}
	/*
	 * delete Station from certain or couple  route, and update the order
	 * */
	private void DelectStation(Scanner input){
		try{
			System.out.println("Please enter a Station name that you want to remove:");
			String SName =  input.nextLine();
			CTAStation delStation = searchStation(SName,input);
			System.out.println(delStation.getName());
			if(delStation.getName() != null){
				allStation.remove(delStation);
				for(int i = 0; i< routeList.size() ;i++){
					for(int j = 0; j < delStation.getBelongsTo().size();j++){
						if(delStation.getBelongsTo().get(j).equalsIgnoreCase(routeList.get(i).getRouteName())){
							routeList.get(i).deleteStation(delStation);
							routeList.get(i).refreshOrderNum();
						}
					}
					
				}
				
			}else{
				System.out.println("Station not found!");
			}
			
		}catch (Exception e){
			System.out.println("wrong input! please enter again");
		}
	}
	/*
	 * search user input name, if have same name in different route, asking for what the station that user want, then return a CTAStaion object
	 * */
	private CTAStation searchStation(String name,Scanner input){
			try{
				ArrayList<Integer> orderNum = new ArrayList<Integer>();
				for(int i =0 ;i < allStation.size(); i++){
					if(allStation.get(i).getName().equalsIgnoreCase(name)){
						orderNum.add(i);
					}
				}
				if(orderNum.size() !=0){
					if(orderNum.size()>1){
						for(int i = 0; i<orderNum.size();i++){
							System.out.println((i+1)  +". "+allStation.get(orderNum.get(i)));
						}
						System.out.println("please select a the station you want to select(enter a number)");
						int index = Integer.parseInt(input.nextLine())-1;
						return allStation.get(orderNum.get(index));
					}else{
						return allStation.get(orderNum.get(0));
					}
				}else{ 
					System.out.println("There is no station called " + name);
				}
				
			}catch(Exception e){
				System.out.println("wrong input! please enter again");
			}
			return new CTAStation();
	}
	
	/*
	 * get Route postion in route list.
	 * */
	public int getRoutePosition(String rName){
		for(int i = 0; i< routeList.size();i++){
			if(routeList.get(i).getRouteName().equalsIgnoreCase(rName)){
				return i;
			}
			
		}
		return -1;
	}
	
	public void DisplayAllStation(){
		for(CTAStation c: allStation){
			System.out.println(c.toString());
		}
		
	}
	/*
	 * display by name
	 * */
	public void DisplayStationByName(String name){
		for(CTAStation c: allStation){
			if(c.getName().equalsIgnoreCase(name)){
				System.out.println(c.toString());
			}
		}
		
	}
	
	public void DisplayAllStationWithRoute(){
		for(CTARoute c: routeList){
			c.displayAllStationInfo();
		}
	}
	
	public ArrayList<CTARoute> getRouteList() {
		return routeList;
	}

	public void setRouteList(ArrayList<CTARoute> routeList) {
		this.routeList = routeList;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public ArrayList<CTAStation> getAllStation() {
		return allStation;
	}

	public void setAllStation(ArrayList<CTAStation> allStation) {
		this.allStation = allStation;
	}

	public String toString() {
		return "CTASystem [routeList=" + routeList + ", allStation=" + allStation + ", city=" + city + "]";
	}

	
}

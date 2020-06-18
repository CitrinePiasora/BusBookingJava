import java.util.ArrayList;
import java.util.Scanner;


public class BusSeatBooking {

    public static void main(String[] args) {
        /* The functions from Class outlines can only be used if
           a new class object is made, Functions will be the 
           object used to access the outline functions */
        BusFunctions Functions = new BusFunctions();

        ArrayList<String> Database = new ArrayList<String>();
        ArrayList<String> BusNames = new ArrayList<String>();
        ArrayList<int[]> BusSeatData = new ArrayList<int[]>();
        ArrayList<String> BusDepartureDates = new ArrayList<String>();
        ArrayList<String> Destination = new ArrayList<String>();
        
        try {
            // Tries to read the Data
            Functions.ReadData(Database, BusNames, BusDepartureDates, BusSeatData, Destination);
        } catch (Exception e) {
            // If an error is caught, print error
            System.out.println(e);
        }
        
        boolean run = false, fail = true;

        // Variables used for storing information
        String destination, Name, Date;
        
        System.out.println("Welcome back fellow administrator. Please enter your credentials in order to proceed.\n");
        
        for(int i = 3; i > 0; i--) {
            run = Functions.login();
            if(run) {
                fail = false;
                break;
            } else if(i - 1 != 0) {
                System.out.printf("Number of attempts left: %d\n", i-1);
                System.out.println();
            }
        }

        Scanner sc = new Scanner(System.in);

        while(run) {
            Functions.SelectionMenu();

            if(sc.hasNextInt()) {
                int Choice = sc.nextInt();
                System.out.println();

                switch(Choice) {

                    case 1:
                        Functions.DestinationLister(Destination);

                        if(sc.hasNextInt()) {
                            int Choice2 = sc.nextInt();
                            System.out.println();
        
                            if(Choice2 > Database.size()) {
                                System.out.println("Error, Invalid Choice");
                                
                            } else {
                                // Bus object is made in order to use the class functions of the bus class
                                Bus B1 = new Bus(BusNames.get(Choice2 - 1), Destination.get(Choice2 - 1), BusDepartureDates.get(Choice2 - 1), BusSeatData.get(Choice2 - 1));
                                System.out.println("Which Seat would you like to book?");
                                // This function lists the availability of the seats
                                B1.SeatAvailability();
                                System.out.print(">>> ");
        
                                // Reusing a previous variable in order to save space
                                Choice = sc.nextInt();
        
                                // uses class function to book a seat
                                B1.book(Choice);
                                
                                Functions.UpdateDatabase(Database, BusNames, BusDepartureDates, Destination, BusSeatData, "book", B1, Choice2 - 1);
                            }
        
                        } else {
                            System.out.println("Error, Invalid Choice. Please enter a valid choice number!");
                        }
                        
                        break;
                    case 2:
                        Functions.BusSchedules(BusNames, BusDepartureDates, Destination);
                        break;
                    
                    case 3:
                        System.out.print("What is the name of the Bus?: ");
                        Name = sc.next();
        
                        System.out.print("Where is the Bus heading to?: ");
                        destination = sc.next();
        
                        System.out.print("How many Seats does the Bus have: ");
        
                        if(sc.hasNextInt()) {
                            /* Reusing a previous variable to save space
                            represents total number of seats */
                            Choice = sc.nextInt();
                            Bus B1 = new Bus(Name, destination, Choice);
        
                            Functions.UpdateDatabase(Database, BusNames, BusDepartureDates, Destination, BusSeatData, "new", B1, 0);

                            System.out.printf("Bus %s heading to %s has been created!\n", Name, destination);
        
                        } else {
                            System.out.println("Error, invalid input!");
                        }
                        break;
                    
                    case 4:
                        Functions.BusSchedules(BusNames, BusDepartureDates, Destination);
        
                        System.out.println("Which Bus' Schedule would you like to change?: ");
                            
                        if(sc.hasNextInt()) {
                            Choice = sc.nextInt();
                                
                            Bus B1 = new Bus(BusNames.get(Choice - 1), BusDepartureDates.get(Choice - 1),  Destination.get(Choice - 1), BusSeatData.get(Choice - 1));
                                
                            System.out.println("Please enter a new Departure Date [Format: dd/mm/YY]: ");
                            Date = sc.next();
        
                            // Date is split by "/" for day, month, year then changed using the class function
                            if(B1.setDate(Date.split("/")[0], Date.split("/")[1], Date.split("/")[2])) {
                                Functions.UpdateDatabase(Database, BusNames, BusDepartureDates, Destination, BusSeatData, "date", B1, Choice - 1);

                                System.out.printf("The bus will now head to its destination at %s\n", Date);
                            } else {
                                System.out.println("Error, Invalid Date");
                            }
                        }
                        break;
                        
                    case 5:
                        Functions.BusSchedules(BusNames, BusDepartureDates, Destination);
                        System.out.println("Which Bus' Destination would you like to change?: ");
                            
                        if(sc.hasNextInt()) {
                            // Reusing a previous variable to save space
                            Choice = sc.nextInt() - 1;
        
                            Bus B1 = new Bus(BusNames.get(Choice), Destination.get(Choice), BusDepartureDates.get(Choice), BusSeatData.get(Choice));
                            sc.nextLine();

                            System.out.println("Where will this bus be heading to?: ");
                            destination = sc.nextLine();
        
                            B1.setDestination(destination);
                            Functions.UpdateDatabase(Database, BusNames, BusDepartureDates, Destination, BusSeatData, "dest", B1, Choice);
                            System.out.printf("The bus will now head to %s\n", destination);
                        }
                        break;
                        
                    case 6:
                        System.out.println("Thank you for stopping by!");
                        Functions.SaveData(Database);
                        run = false;
                        break;
                }

            } else {
                System.out.println("Error, Invalid Choice. Please enter a valid choice number!");
            }
    }
    sc.close();

    if(!run && fail) {
        System.out.println("Sorry, but you have failed to log in too many times. Have a nice day.");
    }
}
}
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

interface ClassOutlines {
    // Interface used as a guide for the class's functions
    void ReadData(ArrayList<String> Database, ArrayList<String> BusNames, ArrayList<String> BusDepartureDates, ArrayList<int[]> BusSeatData, ArrayList<String> Destination);
    void SaveData(ArrayList<String> Data);
    void SelectionMenu();
    void DestinationLister(ArrayList<String> Destination);
    void BusSchedules(ArrayList<String> Names, ArrayList<String> Dates, ArrayList<String> Destination);
    void UpdateDatabase(ArrayList<String> DB, ArrayList<String> Names, ArrayList<String> Dates, ArrayList<String> Destination, ArrayList<int[]> SeatData, String Mode, Bus B1, int Index);
    boolean login();
}

public class BusFunctions implements ClassOutlines {


// Using only 1 override tag as everything below it are overriding functions
    @Override
        public void ReadData(ArrayList<String> Database, ArrayList<String> BusNames, ArrayList<String> BusDepartureDates, ArrayList<int[]> BusSeatData, ArrayList<String> Destination) {
            // Function to Read saved database and use the data for the program
            try(Scanner input = new Scanner(new File("C:\\Users\\Citrine\\Desktop\\School Stuff\\Comp Math\\Java\\JavaProject\\Database.txt"))) {
                // The loop will keep running as long as the input file has data
                while(input.hasNextLine()) {
                    // The Line as a whole is Stored for exporting
                    String Line = input.nextLine();
                    /* Line is then Splitted using the format 
                    Name;SeatData;Date;Destination, using split
                    as a way to turn it into an array */
                    String[] Data = Line.split(";");

                    // Create a temporary var to store SeatData
                    int[] SeatData = new int[Data[1].split(",").length];
                    
                    for(int i = 0; i < Data[1].split(",").length; i++) {
                        /* SeatData is separated by commas in the stored file
                        the integer value is parsed and added to the temporary
                        var. The SeatData is organized into a list through
                        the use of split to split at commas */
                        SeatData[i] = Integer.parseInt(Data[1].split(",")[i]);
                    }

                    // The Extracted data are then added to the ArrayLists
                    Database.add(Line);
                    BusNames.add(Data[0]);
                    BusSeatData.add(SeatData);
                    BusDepartureDates.add(Data[2]);
                    Destination.add(Data[3]);
                }

            } catch(IOException e) {
                System.out.println(e);
            }
        }

        public void SaveData(ArrayList<String> Data) {
            // Function used to write the updated data into the database file
            try(FileWriter fw = new FileWriter("C:\\Users\\Citrine\\Desktop\\School Stuff\\Comp Math\\Java\\JavaProject\\Database.txt");
                PrintWriter out = new PrintWriter(new BufferedWriter(fw))){
            
                for(String Line : Data) {
                    /* Loop that exports the data line by line */
                    out.println(Line);
                }


            } catch (IOException e) {
                System.out.println(e);
            }
        }

        public void SelectionMenu() {
            // Function to print out a nice introduction screen with choices
            System.out.println();
            System.out.println("============================================================================");
            System.out.println("Welcome to Citrine and Co. Rental Services, what would you like to do today?");
            System.out.println("============================================================================");
            System.out.println("1. Book Seats in a Bus\n2. Look at Bus Schedules\n3. Add Buses to Database\n4. Change Bus Schedule\n5. Change Bus Destination\n6. Exit");
            System.out.println("============================================================================");
            System.out.print(">>> ");
        }

        public void DestinationLister(ArrayList<String> Destination) {
            // A function that lists the destinations available to travel to
            int i = 1;
            System.out.println("Where would you like to go? (Duplicates are of Different Buses)");
            
            for(String Data : Destination) {
                System.out.printf("%d. %s\n", i, Data);
                i++;
            }
            System.out.print(">>> ");
        }

        public void BusSchedules(ArrayList<String> Names, ArrayList<String> Dates, ArrayList<String> Destination) {
            /* Lists the buses that are scheduled to leave the station in the format
            i. "BusName" is heading to "Destination" on "Date" */
            System.out.println("Here are the Buses Scheduled to leave");
            for(int i = 0; i < Names.size(); i++) {
                System.out.printf("%d. %s is heading to %s on %s\n", i + 1, Names.get(i), Destination.get(i), Dates.get(i));
            }

        }

        public void UpdateDatabase(ArrayList<String> DB, ArrayList<String> Names, ArrayList<String> Dates,
                ArrayList<String> Destination, ArrayList<int[]> SeatData, String Mode, Bus B1, int Index) {
                    // This function is used to update the databases after an action has been done
                    // "new" is to make a new entry in the database
                    if(Mode.equals("new")) {
                        // Adds all relevant data to the ArrayLists
                        DB.add(B1.toString());
                        Names.add(B1.getName());
                        SeatData.add(B1.SeatData());
                        Dates.add(B1.getDate());
                        Destination.add(B1.getDestination());
                        
                    // "book" is used to update data relevant to booking seats
                    } else if(Mode.equals("book")) {
                        // Overwrites old data to be updated when exported
                        DB.set(Index, B1.toString());
                        // Makes sure that the bus seat data is up to date for later use
                        SeatData.set(Index, B1.SeatData());

                    // "date" is used to update data relevant to departure dates
                    } else if(Mode.equals("date")) {
                        // Overwrites old date to be updated when exported
                        DB.set(Index, B1.toString());
                        // Makes sure that the dates are up to date for later use
                        Dates.set(Index, B1.getDate());
                    
                    // "dest" is used to update data relevant to destination
                    } else if(Mode.equals("dest")) {
                        // Overwrites old date to be updated when exported
                        DB.set(Index, B1.toString());
                        // Makes sure that the destinations are up to date for later use
                        Destination.set(Index, B1.getDestination());
                    }

    }

		public boolean login() {
			String UserData[] = {"Judai", "cmFpbmJvd25lb3M=", "Johan", "cmFpbmJvd2RyYWc="};
		
			Console console = System.console();
			
			// Get input for Username
			String Username = new String(console.readLine("Please enter your username: "));
			// Get input for Password (With console blank while being inputted)
			String enteredPassword = new String(console.readPassword("Please enter your password: "));
			
			String bytesEncoded = Base64.getEncoder().encodeToString(enteredPassword.getBytes());
			
			// Loop to make sure that credentials are correct
			for(int i = 0; i < UserData.length;) {
				if(UserData[i].equals(Username) && UserData[i+1].equals(bytesEncoded)) {
					return true;
				} else {
					i += 2;
				}
			}
			return false;
		}
}
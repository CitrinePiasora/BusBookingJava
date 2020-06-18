import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;

class Rental {
    protected
    // Protected Variables, Only the Child Class may see these
        String Name, Date;
        boolean Booked;
    
    public
    // Public Functions that all child classes will inherit
        String getName() {
            return this.Name;
        }

        String getDate() {
            return this.Date;
        }

        boolean setDate(String day, String month, String year) {
            /* A function that checks if the date is valid before
               setting the new date. Returns a boolean to check if
               process has succeeded */
            if(Integer.parseInt(day) > 31) {
                System.out.println("Error, Day Invalid");
            } else if(Integer.parseInt(month) > 12) {
                System.out.println("Error, Month Invalid");
            } else if(Integer.parseInt(year) < Year.now().getValue()) {
                System.out.println("Error, Time Travel isn't possible");
            } else {
                this.Date = day + "/" + month + "/" + year;
                return true;
            }
            return false;
        }

        void book() {
            if(this.Booked == true) {
                System.out.println("This Vehicle is unavailable");
            } else {
                this.Booked = true;
            }
        }
}

public class Bus extends Rental {
    private
        int[] Seats;
        String Destination;

        String getSeats() {
            /* Returns a string that will be used to save data. This is
               left private due to having no other functional use that
               are relevant for the user */
            String Seat = "";
            for(int i = 0; i < this.Seats.length; i++) {
                if(i == this.Seats.length - 1) {
                    Seat += this.Seats[i];
                } else {
                    Seat += this.Seats[i] + ",";
                }
            }
            return Seat;
        }

    public
        Bus(String BusName, String Destination, int NoSeats) {
            this.Name = BusName;
            // The Date is initialized as the day the Bus instance is created
            this.Date = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now());
            this.Destination = Destination;
            this.Seats = new int[NoSeats];
        }

        Bus(String BusName, String Destination, String Date, int NoSeats) {
            this.Name = BusName;
            this.Date = Date;
            this.Destination = Destination;
            this.Seats = new int[NoSeats];
        }

        Bus(String BusName, String Destination, String Date, int[] SeatData) {
            this.Name = BusName;
            this.Date = Date;
            this.Destination = Destination;
            this.Seats = SeatData;
        }

        void SeatAvailability() {
            // This function is used to show the available seats in a bus
            String Seats = "";
            for(int i = 0; i < this.Seats.length; i++) {
                if(this.Seats[i] == 0) {
                    Seats += String.valueOf(i+1) + ": Available, ";
                } else {
                    Seats += String.valueOf(i+1) + ": Unvailable, ";
                }
            }
            // The concatenated string is then printed out
            System.out.println(Seats);
        }

        String getDestination() {
            return this.Destination;
        }

        int[] SeatData() {
            return this.Seats;
        }

        // Overloading the booking function from Rental Class
        void book(int seatNo) {
            /* In this program, 0 means available while 1 is booked
               this changes the seat status to booked if the seat is
               available for booking. Otherwise return an error */
            
            if(this.Seats[seatNo - 1] != 1) {
                this.Seats[seatNo - 1] = 1;
                System.out.println("Your booking has been made");
            } else {
                System.out.println("Seat is unavailable");
            }
        }

        void setDestination(String Destination) {
            this.Destination = Destination;
        }

    @Override
    public String toString() {
        return getName() + ";" + getSeats() + ";" + getDate() + ";" + getDestination();
    }

}
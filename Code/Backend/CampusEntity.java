//base class CampusEntity

import java.io.Serializable;
import java.util.*;
abstract public class CampusEntity implements Serializable {

    //datamembers and common attributes
    protected String name;
    protected int entityID;
    protected String location;

    //Zero Argument Constructor
    public CampusEntity() {

        name = null;
        entityID = 0;
        location = null;
    
    }

    //Argument Constructor (uses setters to intialize - reason : instead of writing the same validation checks in both setters and argument constructor, write them in setter and call them in the argument constructor. This canot be vice versa as constructors are only called when an object is to be created.)
    public CampusEntity(String name, String location, int entityID){

        setName(name);
        setLocation(location);
        setEntityId(entityID);

    }

    //Setters (provided for admin as the admin will have full system access, so in case any attribute is to be modified it can be using setters) 
    public final void setName(String name){

        if(!name.isBlank()){
            this.name = name;
        }else{
            this.name = null;
        }

    }

    public final void setLocation(String location){

        if(!location.isBlank()){
            this.location = location;
        }else{
            this.location = null;
        }

    }

    public final void setEntityId(int entityID){

        if(entityID > 0){
            this.entityID = entityID;
        }else{
            this.entityID = 0;
        }

    }

    //Getters
     public String getName() {
        return name;
    }

    public int getEntityID() {
        return entityID;
    }

    public String getLocation() {
        return location;
    }

    //Abstract method to calculate operational costs
    abstract public double calculateOperationalCost();

    @Override
    public String toString() {
        return ("Name : " +name+ "\nLocation : " +location+ "\nEntity ID : " +entityID);
    }

}//end of CampusEntity class
 
//Academic Unit
abstract class AcademicUnit extends CampusEntity{
    
    //Data Members
    protected ArrayList<Equipment> equipment;
    protected ArrayList<Student> students;

    //Constructors
    public AcademicUnit() {
        equipment = new ArrayList<>();
        students = new ArrayList<>();

    }
    public AcademicUnit(String name, String location, int entityID) {
        super(name, location, entityID);
        equipment = new ArrayList<>();
        students = new ArrayList<>();
    }
    
    public void addEquipment(Equipment equip) {
        //No need to check equipment already present since we can have more than one equipment of same type
        //Add equipment to arrayList
        equipment.add(equip);  
    }
    
    public void removeEquipment(Equipment equip) {
        //Check if equipment is present
        int index = equipment.indexOf(equip);
        if(index != -1)
        {
            equipment.remove(equip);
        }
        else
        {
            System.out.println("Equipment Not Found!");
        }
    }
    
    public void addStudent(Student student)
    {
        //Check if student is not present already
        int index = students.indexOf(student);
        if(index == -1)
        //Add student to arrayList
       {    students.add(student);
       }
       else
       {
        System.out.println("Student Already Enrolled!");
       }
    }
    
    public void removeStudent(Student student)
    {
        //Check the index to find student (if exists)
        int index = students.indexOf(student);
        if(index != -1)
        {   students.remove(student);
        }
        else
        {
            System.out.println("Student Not Found!");
        }
    }
    
    @Override
    abstract public double calculateOperationalCost();
    
}//end of Academic Unit 

//Department class
class Department extends AcademicUnit implements Reportable{
       
    //data member
    private ArrayList<Course> courses;
    
    public Department() {
        super();
        courses = new ArrayList<>();
    }
    
    public Department(String name, String location, int entityID) {
         super(name, location, entityID);
         courses = new ArrayList<>();
    }
    
    //Add courses
    public void addCourse(Course c) {
        courses.add(c);
        System.out.println("Course Added Successfully");
    }
    
    //Remove Courses 
    public void removeCourse(Course c) {
        courses.remove(c);
        System.out.println("Course Removed Successfully");
    }
    
    //total courses in the department 
    public int getTotalCoursesinDep() {
        return courses.size();
    }
    
    //total students enrolled in the department
    public int totalStudentsinDep() {
        int totalstudents = 0;
        boolean counted;
        
        //Goes through the course present at index in the course list 
        for(int i=0; i<courses.size(); i++) {
            //Obtains the student enrolled list of that course and stores it in the enrolled student arraylist 
            ArrayList<Student> enrolled = courses.get(i).getStudentList();
            //Loops through each student enrolled in that course and sets the boolean false for all of them telling the system that they havent been counted means they are unique students with who hadnt been counted before
            for(int j=0; j<enrolled.size(); j++){
                counted = false;
                
                //checks the course list of that course to see if that student was counted before or not if counted the boolean becomes true
                for(int k=0; k<i; k++){
                    if(courses.get(k).getStudentList().contains(enrolled.get(j))) {
                        counted = true;
                    }
                }
                
                //if the student wasnt counted before then it is added to the total student otherwise it isnt added.
                if(!counted){
                    totalstudents++;
                }
            }
        }
        return totalstudents;
    }
    
    @Override
    public String toString() {
        return super.toString() + ("\nTotal Courses Offered = " +getTotalCoursesinDep()+"\nTotal Students Enrolled in Department = " +totalStudentsinDep());
    }
    
    @Override
    public double calculateOperationalCost() {
        double cost = 0;
        cost += totalStudentsinDep() * 500; // students from courses
        for(int i = 0; i < equipment.size(); i++) {
            cost += equipment.get(i).getPrice(); // equipment added directly
        }
        return cost;
    }

    @Override
    public void generateReport() {
        System.out.println("DEPARTMENT PERFORMANCE REPORT");
        System.out.println(this.toString());
        System.out.println("Total Equipments : " +equipment.size());
        System.out.println("Operational Cost : " +calculateOperationalCost());
    }

}//end of Department class 

class Classroom extends AcademicUnit{
    //data members
    private boolean isAvailable;
    private int totalcapacity;

    //Zero argument constructir
    public Classroom(){
        super();
        isAvailable = true;
        totalcapacity = 0;
    }
    
    //argument Constructors
    public Classroom(String name, String location, int entityID, boolean isAvailable, int totalcapacity) {
        super(name, location, entityID);
        setIsAvailable(isAvailable);
        setCapacity(totalcapacity);
    }
    
    //Setters
    public final void setCapacity(int totalcapacity) {
        if(totalcapacity > 0) {
            this.totalcapacity = totalcapacity;
        }else{
            this.totalcapacity = 0;
        }
    }
    
    public final void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    //Getters
    public int gettotalCapacity() {
        return totalcapacity;
    }
    
    public boolean getisAvailable() {
        return isAvailable;
    }
    
    
    @Override
    public void addStudent(Student s) {
        if(students.size() < totalcapacity){
            students.add(s);
            System.out.println("Student Added");
            if(students.size() == totalcapacity) {
                isAvailable = false;
            }
        }else{
            System.out.println("Class Capacity Full. Couldn't add Student");
            isAvailable = false;
        }
    }
    
    @Override
    public void removeStudent(Student s) {
        if(students.contains(s)){
            students.remove(s);
            isAvailable = true;
            System.out.println("Student removed");
        }else{
            System.out.println("No Student Found. Student Couldn't be Removed.");
        }
    }
    
    @Override
    public double calculateOperationalCost() {
        double eqprice = 0;
        for(int i=0; i<equipment.size(); i++) {
            eqprice += equipment.get(i).getPrice();
        }
        
        return ( (students.size() * 500) + eqprice); //500 is a dummt value or the expenditure per student 
    }
    
    @Override
    public String toString() {
        return super.toString() + ("\nTotal Class Capacity : " +totalcapacity+"\nAvailable : " +isAvailable);
    }
}//end of classrom clas

 //class Lab
class Lab extends AcademicUnit {
    
    //data members
    private boolean isfree;
    private int totalcapacity;

    //Zero argument constructir
    public Lab(){
        super();
        isfree = true;
        totalcapacity = 0;
    }
    
    //argument Constructors
    public Lab(String name, String location, int entityID, boolean isfree, int totalcapacity) {
        super(name, location, entityID);
        setIsFree(isfree);
        setCapacity(totalcapacity);
    }
    
    //Setters
    public final void setCapacity(int totalcapacity) {
        if(totalcapacity > 0) {
            this.totalcapacity = totalcapacity;
        }else{
            this.totalcapacity = 0;
        }
    }
    
    public final void setIsFree(boolean isfree) {
        this.isfree = isfree;
    }
    
    //Getters
    public int gettotalCapacity() {
        return totalcapacity;
    }
    
    public boolean getisFree() {
        return isfree;
    }
    
    @Override
    public void addStudent(Student s) {
        if(students.size() < totalcapacity){
            students.add(s);
            System.out.println("Student Added");
            if(students.size() == totalcapacity) {
                isfree = false;
            }
        }else{
            System.out.println("Class Capacity Full. Couldn't add Student");
            isfree = false;
        }
    }
    
    @Override
    public void removeStudent(Student s) {
        if(students.contains(s)){
            students.remove(s);
            isfree = true;
            System.out.println("Student removed");
        }else{
            System.out.println("No Student Found. Student Couldn't be Removed.");
        }
    }
    
    @Override
    public double calculateOperationalCost() {
        double eqprice = 0;
        for(int i=0; i<equipment.size(); i++) {
            eqprice += equipment.get(i).getPrice();
        }
        
        return ( (students.size() * 500) + eqprice); //500 is a dummt value or the expenditure per student 
    }
    
    @Override
    public String toString() {
        return super.toString() + ("\nTotal Class Capacity : " +totalcapacity+"\nCurrently Free : " +isfree);
    }

}//end of lab class 

//Facility
class Facility extends CampusEntity{

    //Data Members
    protected double maintenance_cost;
    protected double usage_frequency;

    //tracking system's totalfacilityusage
    protected static double totalfacilityusage = 0; 

    //Constructors
    public Facility() {}

    public Facility(String name, String location, int entityID, double maintenance_cost, double usage_frequency) {
        super(name, location, entityID);
        setMaintenance_cost(maintenance_cost);
        setUsage_frequency(usage_frequency);
    }

    //Getters Setters
    public double getMaintenance_cost() {
        return maintenance_cost;
    }

    public final void setMaintenance_cost(double maintenance_cost) {
        if(maintenance_cost > 0) {
            this.maintenance_cost = maintenance_cost;
        }else{
            this.maintenance_cost = 0;
        }
        
    }

    public double getUsage_frequency() {
        return usage_frequency;
    }

    public final void setUsage_frequency(double usage_frequency) {
        if(usage_frequency > 0) {
            this.usage_frequency = usage_frequency;
        }else{
            this.usage_frequency = 0;
        }

        totalfacilityusage += usage_frequency;
    }
    
    //static methods
    public static double getTotalFacilityUsage() {
          return totalfacilityusage;
    }

    public static void displayTotalFacilityUsage() {
        System.out.println("Total Facility Usage = " +totalfacilityusage);
    }
    
    //Methods
    @Override
    public double calculateOperationalCost() {
        return maintenance_cost * usage_frequency;
    }

}//end of facility class


class Library extends Facility implements Reportable{
    
    //data members 
    protected ArrayList <Books> b1;
      
    //Zero Argument Constructor
    public Library(){
        super();
        b1 = new ArrayList<>();
    }
      
    //Argument Constructor
    public Library(String name, String location, int entityID, double maintenance_cost, double usage_frequency){
        super(name, location, entityID, maintenance_cost, usage_frequency);
        b1 = new ArrayList<>();
    }
      
    //Displaying all books
    public void displayAll(){
        for(int i=0; i<b1.size(); i++) {
                System.out.println((i+1)+". " +(b1.get(i)).toString());
                System.out.println();
        }
    }

      
    //Displaying the total books present in the library
    public int totalBooks() {
        return b1.size();
    }
      
    //Borrow books
    public void borrowBook(Books b) {
        System.out.println(b.getBookName() + " borrowed from Library.");
        b.setAvailability(false);
    }
      
    //Return books
    public void returnBook(Books b) {
        System.out.println(b.getBookName() + " returned to Library");
        b.setAvailability(true);
    }
      
    //add books
    public void add(Books b){
        b1.add(b);
    }
      
    //remove Books
    public void remove(Books b) {
        b1.remove(b);
    }

    //calculating available and borrowed books
    public void booksdata() {
            int available = 0;
            int borrowed = 0;
            for(int i=0; i<b1.size(); i++) {
                  if(b1.get(i).getAvailability()){
                        available++;
                  }else{
                        borrowed++;
                  }
            }
            System.out.println("Available Books = " +available);
            System.out.println("Borrowed Book = " +borrowed);
    }

    @Override
    //Calculate Operational costs
    public double calculateOperationalCost(){
        return (maintenance_cost * usage_frequency) + (b1.size() * 150);

        //multiplying the number of books with a dummy value like 150. and then adding it to the operational costs.
    }

    @Override
    //toString method
    public String toString() {
        return super.toString() + ("\nTotal Books in Library : " +b1.size());
    }
    
    @Override
    public void generateReport() {
        System.out.printf("%50s", "Usage Stats for Library");
        System.out.println();
        System.out.println("Location : " +location);
        System.out.println("Entity ID : " +entityID);
        System.out.println("Usage Information");
        System.out.println("Maintenance cost : " +maintenance_cost);
        System.out.println("Usage Frequency : " +usage_frequency);
        System.out.println("Total Books in Library = " +b1.size());
        booksdata();
        System.out.println("Operational Cost : " +calculateOperationalCost());
    }

}//end of library class

class Books implements Serializable{

    //data members
    private String bookname;
    private String authorname;
    private String edition;
    private boolean isAvailable;
      
    //Zero Argument Constructor
    public Books() {
        bookname = "Unknown";
        authorname = "Unknown";
        edition = "Unknown";
        isAvailable = true;
    }
      
    //Argument Constructor
    public Books(String bookname, String authorname, String edition) {
          
        setBookName(bookname);
        setAuthorName(authorname);
        setEdition(edition);
        isAvailable = true;
    }
      
    //Display Using To String method
    @Override
    public String toString() {
        return ("Book Details : " +"\nBook : " +bookname+ "\nAuthor : " +authorname+ "\nEdition : " +edition+"\n Availability : " +isAvailable);
    }
      
    //Setters
    public final void setBookName(String bookname) {
          
        if(!bookname.isBlank()){
              this.bookname = bookname;
        }else{
              this.bookname = "Book Title Unknown";
        }
    }
      
    public final void setAuthorName(String authorname) {
          
        if(!authorname.isBlank()){
              this.authorname = authorname;
        }else{
              this.authorname = "Author Unknown";
        }
    }
      
    public final void setEdition(String edition) {
          
        if(!edition.isBlank()){
              this.edition = edition;
        }else{
              this.edition = "No Record of Edition Found";
        }
    }

    public void setAvailability(boolean isAvailable) {
          this.isAvailable = isAvailable;
    }
      
    //Getters
    public String getBookName() {
          return bookname;
    }
      
    public String getAuthorName() {
          return authorname;
    }
      
    public String getEdition() {
          return edition;
    }

    public boolean getAvailability() {
          return isAvailable;
    }

}//end of books class

class Cafeteria extends Facility{

      
    //data Members
    private int fooditems; //food items present today
    private double average_meal_cost; //number of meals consumed 
      
    //Zero Argument Constructor
    public Cafeteria() {
            super();
            fooditems = 1;
            average_meal_cost = 0;
    }
      
    //Argument Constructor
    public Cafeteria(String name, String location, int entityID, double maintenance_cost, double usage_frequency, int fooditems, double average_meal_cost) {
            super(name, location, entityID, maintenance_cost, usage_frequency);
            setFoodItems(fooditems);
            setAverage_meal_cost(average_meal_cost);
    }
      
    //Setters
    public final void setFoodItems(int fooditems) {
            if(fooditems > 1) {
                  this.fooditems = fooditems;
            }else{
                  this.fooditems = 1;
            }
    }
      
    public final void setAverage_meal_cost(double average_meal_cost) {
            if(average_meal_cost >= 0) {
                  this.average_meal_cost = average_meal_cost;
            }else{
                  this.average_meal_cost = 0;
            }
    }
      
    //Getters
    public int getfooditems() {
            return fooditems;
    }
      
    public double getaverage_meal_cost() {
            return average_meal_cost;
    }
      
    @Override
    //calculateOperationalCost
    public double calculateOperationalCost() {
            return (maintenance_cost * usage_frequency) + (fooditems * average_meal_cost);
    }
      
    @Override
    public String toString() {
            return super.toString() + ("\nUsage Frequency : " +usage_frequency);
    }

}//end of cafeteria class

class Hostel extends Facility{
      
    //data Members
    private boolean[] isOccupied = new boolean[100];
      
    //Zero Argument Constructor
    public Hostel() {
    }
      
    //Argument Constructor
    public Hostel(String name, String location, int entityID, double maintenance_cost, double usage_frequency, int totalroom) {
            super(name, location, entityID, maintenance_cost, usage_frequency);
            isOccupied = new boolean[totalroom];
    }
      
    //Occupy Room
    public void occupy(int room) {
            if(room >= 0 && room < isOccupied.length) {
                  isOccupied[room] = true;
            }else{
                  System.out.println("Cannot Occupy Room.");
            }
    }
      
    //Vacant room
    public void vacant(int room) {
            if(room >= 0 && room < isOccupied.length) {
                  isOccupied[room] = false;
            }else{
                  System.out.println("Could not vacant room. Try again");
                  
            }
    }
      
    //Hostel data calculation 
    public int occupiedrooms() {
            int occupiedrooms = 0;
            for(int i=0; i<isOccupied.length; i++) {
                  if(isOccupied[i]) {
                        occupiedrooms++;
                  }
            }
            return occupiedrooms;
    }
      
    @Override
    public double calculateOperationalCost() {
            return (maintenance_cost * usage_frequency) + (occupiedrooms() * 20000);
    }
      
    @Override
    public String toString() {
        return super.toString() + ("\nTotal Rooms : " +isOccupied.length+ "\nOccupied Rooms : " +occupiedrooms()+ "\nUnoccupied Rooms : " +(isOccupied.length - occupiedrooms() ) );
    }
}//end of hostel class


//Service Unit
class ServiceUnit extends CampusEntity{

    //Data Members
    protected ArrayList<Staff> staff_members;
    
    //Constructors
    public ServiceUnit(){
        staff_members = new ArrayList<>();
    }

    public ServiceUnit(String name, String location, int entityID) {
        super(name, location, entityID);
        staff_members = new ArrayList<>();
    }

    //Methods to add staff
    public void addStaff(Staff member)
    {
        //If not already present
        int index = staff_members.indexOf(member);
        if(index == -1)
        {
            staff_members.add(member);
        }
        else
        {
            System.out.println("Staff Member is Already Employed Here!");
        }
    }


    public void removeStaff(Staff member)
    {
        //Check if member is present
        int index = staff_members.indexOf(member);
        if(index != -1)
        {
            staff_members.remove(member);
        }
        else
        {
            System.out.println("Staff Member does not Exist!");
        }
    }

    //Method Override
    @Override
    public double calculateOperationalCost()
    {
        double cost = 0;
        for(int i = 0; i < staff_members.size(); i++)
        {
            cost += (staff_members.get(i).getSalaryPerHour()) * (staff_members.get(i).getService_hours());
        }
        return cost;
    }
    
}//end of serviceunit class


class TransportService extends ServiceUnit implements Schedulable{ 

    private String routeName;
    private int totalBuses;
    
    public TransportService() { 
        super();
        routeName = "Unknown";
        totalBuses = 0; 
    }
    
    public TransportService(String name, String location, int entityID, String routeName, int totalBuses) {
        super(name, location, entityID);
        setRouteName(routeName);
        setTotalBuses(totalBuses);
    }

    @Override
    public String toString() {
        return super.toString() + ("\nRoute Name : " +routeName+"\nTotal Buses : " +totalBuses);
    }

    public String getRouteName() {
        return routeName;
    }

    public final void setRouteName(String routeName) {
        if(!routeName.isBlank()){
            this.routeName = routeName;
        }else{
            this.routeName = "Unknown";
        }
    }

    public int getTotalBuses() {
        return totalBuses;
    }

    public final void setTotalBuses(int totalBuses) {
        if(totalBuses > 0) {
            this.totalBuses = totalBuses;
        }else{
            this.totalBuses = 0;
        }
    }
    
}//end of TransportService class


class SecurityService extends ServiceUnit implements  Notifiable{
    
    private String alertLevel;
    
    public SecurityService() { 
        super(); 
        alertLevel = "Low"; 
    }
    
    public SecurityService(String name, String location, int entityID, String alertLevel) {
        super(name, location, entityID);
        this.alertLevel = alertLevel;
    }

    public String getAlertLevel() {
        return alertLevel;
    }

    public void setAlertLevel(String alertLevel) {
        this.alertLevel = alertLevel;
    }

    @Override
    public String toString() {
        return super.toString() + ("\nAlert Level = " +alertLevel);
    }
    
    @Override
    public void sendNotification(String message) {
        System.out.println("Notification !");
        System.out.println(message);
    }
    
}//end of SecurityService class

//class HealthCenter
class HealthCenter extends ServiceUnit implements Notifiable{

    private int doctorsAvailable;
    
    public HealthCenter() {
         super(); 
         doctorsAvailable = 0; 
    }
    
    public HealthCenter(String name, String location, int entityID, int doctorsAvailable) {
        super(name, location, entityID);
        setDoctorsAvailable(doctorsAvailable);
    }

    @Override
    public void sendNotification(String message) {
        System.out.println("Notification !");
        System.out.println(message);
    }

    @Override
    public String toString() {
        return super.toString() + ("\nDoctors Available : " +doctorsAvailable);
    }

    public int getDoctorsAvailable() {
        return doctorsAvailable;
    }

    public final void setDoctorsAvailable(int doctorsAvailable) {
        if(doctorsAvailable > 0) {
            this.doctorsAvailable = doctorsAvailable;
        }else{
            this.doctorsAvailable = 0;
        }
        
    }

}//end of HealthCenter class

//class cAMPUS ZONE
class CampusZone implements Serializable{

    private int zoneID; //3 in AB3, 2 in AB2 
    private String zoneName; //like Academic Block. Faculty block etc 
    private ArrayList<Facility> facilities;
    private ArrayList<ServiceUnit> serviceUnits;
    
    public CampusZone() {
        zoneID = 0;
        zoneName = "Unknown";
        facilities = new ArrayList<>();
        serviceUnits = new ArrayList<>();
    }
    
    public CampusZone(int zoneID, String zoneName) {
        setZoneID(zoneID);
        setZoneName(zoneName);
        facilities = new ArrayList<>();
        serviceUnits = new ArrayList<>();
    }

    public int getZoneID() {
        return zoneID;
    }

    public final void setZoneID(int zoneID) {
        if(zoneID > 0) {
            this.zoneID = zoneID;
        }else{
            this.zoneID = 0;
        }
    }

    public String getZoneName() {
        return zoneName;
    }

    public final void setZoneName(String zoneName) {
        if(!zoneName.isBlank()) {
            this.zoneName = zoneName;
        }else{
            this.zoneName = "Zone Unknown";
        }
    }
    
    public void addFacilities(Facility f) {
        facilities.add(f);
    }

    public void removeFacilities(Facility f) {
        facilities.remove(f);
    }

    @Override
    public String toString() {
        return "CampusZone [zoneID=" + zoneID + ", zoneName=" + zoneName + ", facilities=" + facilities
                + ", serviceUnits=" + serviceUnits + ", toString()=" + super.toString() + ", getZoneID()=" + getZoneID()
                + ", getZoneName()=" + getZoneName() + "]";
    }

}//end of CampusZone 

//class Campus Repository 
class CampusRepository<T> implements Serializable {
    private ArrayList<T> items; //should it be private like other class's datamembers? //yes
    
    public CampusRepository() {
        items = new ArrayList<>();
    }  
    
    public void add(T item) {
        items.add(item);
        System.out.println("Item added successfully!");
    }
    
    public void remove(T item) {
        if(items.contains(item)) {
            items.remove(item);
            System.out.println("Item removed successfully!");
        } else {
            System.out.println("Item not found!");
        }
    }
    
    public T get(int index) {
        return items.get(index);
    }
    
    public ArrayList<T> getAll() {
        return items;
    }
    
    public int size() {
        return items.size();
    }
    
    public void displayAll() {
        for(int i=0; i<items.size(); i++) {
            System.out.println(items.get(i).toString());
        }
    }
}//end of campusrepository class 
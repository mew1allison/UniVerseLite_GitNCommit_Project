//base class CampusEntity
import java.util.*;
abstract public class CampusEntity {

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
    //Aleena: For Academic Unit, I'm having two arraylists and count variables of students and equipment so that we can add/remove students and equipment and also count them to calculate cost. Logic: StudentCount * 500 (dummy values) + (for loop through equipment array , equipment[i].getPrice() sth); Let's not complicate this logic, ok?
    abstract public double calculateOperationalCost();

}//end of CampusEntity class

//Aleena: So, I'll be making all child classes inside this file, 'CampusEntity.java', that's how I've been doing it. We can change it to a separate file later. I've left the sub-child classes empty, we can work on them later IA.
//Aleena: Do review these please :)

//Academic Unit
class AcademicUnit extends CampusEntity{
    //Data Members
    protected static int studentCount = 0;
    protected ArrayList<Student> students;
    //protected static int equipmentCount = 0; I don't think this is needed
    protected ArrayList<Equipment> equipment;
    //Constructors
    public AcademicUnit() {
        students = new ArrayList<Student>();
        equipment = new ArrayList<Equipment>();

    }
    public AcademicUnit(String name, String location, int entityID) {
        super(name, location, entityID);
        students = new ArrayList<Student>();
        equipment = new ArrayList<Equipment>();
    }
    //Methods to Add/Remove students & equipment
    public void addStudent(Student student)
    {
        //Check if student is not present already
        int index = students.indexOf(student);
        if(index == -1)
        //Add student to arrayList
       {students.add(student);}
       else
       {
        System.out.println("Student Already Enrolled!");
       }
       //Increase student Count
       studentCount++;
    }
    public void removeStudent(Student student)
    {
        //Check the index to find student (if exists)
        int index = students.indexOf(student);
        if(index != -1)
        {students.remove(student);}
        else
        {
            System.out.println("Student Not Found!");
        }
        //Decrease student Count
       studentCount--;
    }
    public void addEquipment(Equipment equip)
    {
        //No need to check equipment already present since we can have more than one equipment of same type
        //Add equipment to arrayList
        equipment.add(equip);  
    }
    public void removeEquipment(Equipment equip)
    {
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
    //Override the method
    @Override
    public double calculateOperationalCost()
    {
        double cost = 0;
        cost += studentCount * 500;
        for(int i = 0; i < equipment.size(); i++)
        {
            cost += equipment.get(i).getPrice();
        }
        return cost;
    }
}
class Department extends AcademicUnit{

    public Department() {
    }
    

}
class Classroom extends AcademicUnit{

}
class Lab extends AcademicUnit{

}

//Facility
class Facility extends CampusEntity{
    //Data Members
    protected double maintenance_cost;
    protected double usage_frequency;
    //Constructors
    public Facility() {}
    public Facility(String name, String location, int entityID, double maintenance_cost, double usage_frequency) {
        super(name, location, entityID);
        this.maintenance_cost = maintenance_cost;
        this.usage_frequency = usage_frequency;
    }
    //Getters Setters
    
    //Methods
    @Override
    public double calculateOperationalCost()
    {
        return maintenance_cost * usage_frequency;
    }
    public double getMaintenance_cost() {
        return maintenance_cost;
    }
    public void setMaintenance_cost(double maintenance_cost) {
        this.maintenance_cost = maintenance_cost;
    }
    public double getUsage_frequency() {
        return usage_frequency;
    }
    public void setUsage_frequency(double usage_frequency) {
        this.usage_frequency = usage_frequency;
    }
}
class Library extends Facility{

}
class Book{

}
class Cafeteria extends Facility{

}
class Hostel extends Facility {

}

//Service Unit
class ServiceUnit extends CampusEntity{
    //Data Members
    protected ArrayList<Staff> staff_members;
    //Constructors
    public ServiceUnit(){
        staff_members = new ArrayList<Staff>();
    }
    public ServiceUnit(String name, String location, int entityID) {
        super(name, location, entityID);
        staff_members = new ArrayList<Staff>();
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
    
}
class TransportService extends ServiceUnit{

}
class SecurityService extends ServiceUnit{

}
class HealthCenter extends ServiceUnit{

}

//the reason the setters are declared final is: When I call the setters within the argument constructor for initialization, a problem "Overridable Method Call in Constructor" arises. This is bcz the child might override these methods and that will cause problems.
//However, in our system design the child will use the super keyword to call for the initialization of these parent attributes and has no need to override these setter methods as they serve only modificationa nd validation purpose. So they can easily be modified using the parent's setters without any need of them overriding these setters in the child classes.
//Aleena : Done :)
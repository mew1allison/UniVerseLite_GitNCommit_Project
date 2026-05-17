package Backend;
import java.io.Serializable;
import java.util.*;

//This class will serve as wrapper class for ALL classes(array lists) in this program for easy read and write (no modifier datatypes)
public class CampusData implements Serializable{

    private static final long serialVersionUID = 1L;
    //ArrayLists for campus entities
    ArrayList<Assignment> assignments = new ArrayList<>();
    public ArrayList<Student> students = new ArrayList<>();
    public ArrayList<Course> courses = new ArrayList<>();
    ArrayList<Equipment> equipment = new ArrayList<>();
    ArrayList<Staff> staff = new ArrayList<>();
    ArrayList<Department> departments = new ArrayList<>();
    ArrayList<Lab> labs = new ArrayList<>();
    ArrayList<Classroom> classrooms = new ArrayList<>();
    public Cafeteria cafeteria;
    public Library library;
    public ArrayList<Books> books = new ArrayList<>();
    public ArrayList<Hostel> hostels = new ArrayList<>();
    TransportService transportService;
    SecurityService securityService;
    HealthCenter healthCenter;
    ArrayList<Bus> bus = new ArrayList<>();
    public ArrayList<User> users = new ArrayList<>();

    //Constructors
    public CampusData() {
    }
}//end of campus data class 
package Backend;
import java.io.Serializable;
import java.util.*;
//This class will serve as wrapper class for ALL classes(array lists) in this program for easy read and write (no modifier datatypes)
public class CampusData implements Serializable{
    //ArrayLists for campus entities
    ArrayList<Assignment> assignments = new ArrayList<>();
    public ArrayList<Student> students = new ArrayList<>();
    ArrayList<Course> courses = new ArrayList<>();
    ArrayList<Equipment> equipment = new ArrayList<>();
    ArrayList<Staff> staff = new ArrayList<>();
    ArrayList<Department> departments = new ArrayList<>();
    ArrayList<Lab> labs = new ArrayList<>();
    ArrayList<Classroom> classrooms = new ArrayList<>();
    ArrayList<Cafeteria> cafeterias = new ArrayList<>();
    ArrayList<Library> libraries = new ArrayList<>();
    ArrayList<Books> books = new ArrayList<>();
    ArrayList<Hostel> hostels = new ArrayList<>();
    ArrayList<TransportService> transportServices = new ArrayList<>();
    ArrayList<SecurityService> securityServices = new ArrayList<>();
    ArrayList<HealthCenter> healthCenters = new ArrayList<>();
    //Constructors
    public CampusData() {
    }
}
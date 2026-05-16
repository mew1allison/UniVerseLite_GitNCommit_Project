package Backend;

 //class Lab
public class Lab extends AcademicUnit {

    private static final long serialVersionUID = 1L;
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
                System.out.println("Class Capacity Full.");
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
        
        return ( (students.size() * 5000) + eqprice); //5000 is a dummt value or the expenditure per student 
    }
    
    @Override
    public String toString() {
        return super.toString() + ("\nTotal Class Capacity : " +totalcapacity+"\nCurrently Free : " +isfree);
    }

}//end of lab class 


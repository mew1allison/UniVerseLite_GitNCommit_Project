import java.io.*;
import java.util.*;

public class FileHandler {
    //Classes to be saved to file : <AcademicUnit, Facility, ServiceUnit>, CampusZone, Campus Repository [dept, lab, classroom | cafe, lib, hostel,  | transport, security, health ]--> <> abstract classes. We only upload classes like dept, lab etc
    //--> use arrays to upload. array of three AU's three Fac's and three SU's
    //1) AU Objects 2) Fac Objects 3) SU objects 4) CZ 5) CR 
    //Main component classes (dependant/aggregation) : student, course, assignments, staff, equipment, books
    //Student, courses, assignments, equipment : come under dept, lab, classroom (covered)
    // staff : comes under cafe
    // books : come under lib

    //1) Header Remover Class
    class MyObjectOutputStream extends ObjectOutputStream{
        public MyObjectOutputStream() throws IOException{
            super();
        }
        public MyObjectOutputStream(FileOutputStream fos) throws IOException{
            super(fos);
        }
        //Overwrite writeStreamHeader method
        @Override
        protected void writeStreamHeader() throws IOException {
            // Do nothing to avoid writing header
        }

    //2) Write to File
    public <T> void saveToFile(T entityClass, String filename)
    {
        try{
            File file = new File(filename);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(entityClass);
            oos.close();
        }
        catch(FileNotFoundException e){ System.err.println("File Not Found."); e.printStackTrace();}
        catch(IOException e){ e.printStackTrace();}
    }
    //3) Read from File
    public <T> T readFromFile(String filename)
    {
        try{
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            //Read back in the same order as we wrote the objects
            Object obj = ois.readObject();
            ois.close();
            return (T) obj;
        }
        catch(FileNotFoundException e){ System.err.println("File Not Found."); e.printStackTrace();}
        catch(IOException e){ e.printStackTrace();}
        catch(ClassNotFoundException e){ e.printStackTrace();}
        return null;
    }

    //4) Implement according to our program : 1 save button should save all 11 classes [3 arrays (polymorphism) + 2 (CZ + CR)], and 1 load button vice versa.
    public <T> void saveProgramData(String filename, AcademicUnit[] academics, Facility[] facilities, ServiceUnit[] services, CampusZone zone, CampusRepository<T> repository)
    {
        //1-Write the 3 campus entities, campus zone and repo
        saveToFile(academics, filename);
        saveToFile(facilities, filename);
        saveToFile(services, filename);
        saveToFile(zone, filename);
        saveToFile(repository, filename);
    }
    public <T> void loadProgramData(String filename, AcademicUnit[] academics, Facility[] facilities, ServiceUnit[] services, CampusZone zone, CampusRepository<T> repository)
    {
        //...working on this!dir
    }
   


    

    }

}

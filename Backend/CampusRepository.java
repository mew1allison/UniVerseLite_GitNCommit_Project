package Backend;

import java.io.*;
import java.util.*;
//class Campus Repository 
public class CampusRepository<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private ArrayList<T> items; 
    
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
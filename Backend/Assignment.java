package Backend;
import java.io.Serializable;

public class Assignment implements Serializable {

private static final long serialVersionUID = 1L;

    //attributes
    private int assignment_num;
    private int totalmarks;
    private String title; //the description of the Assignment
    private String deadline; //the format DD/MM/YY (should we use it in this way or using month, year, day attributes??)

    //Zero Argument Constructor
    public Assignment(){
        
        assignment_num = 0;
        totalmarks = 1;
        title = null;
        deadline = null;
        
    }

    //Argument Constructor
    public Assignment(int assignment_num, int totalmarks, String title, String deadline){
        
        setAssignmentNum(assignment_num);
        setTotalmarks(totalmarks);
        setTitle(title);
        setDeadline(deadline);

    }

    //Setters (the teacher manages courses and students. The course class has multiple students and assignment objects. So the teacher might need setters for deadline extensions and other modifications)
    public final void setTitle(String title) {
        
        if(!title.isBlank()){
            this.title = title;
        }else{
            this.title = null;
        }

    }

    public final void setDeadline(String deadline) {

        if(!deadline.isBlank()){
            this.deadline = deadline;
        }else{
            this.deadline = null;
        }

    }

    public final void setTotalmarks(int totalmarks) {
        
        if(totalmarks > 0){
            this.totalmarks = totalmarks;
        }else{
            this.totalmarks = 1;  
        }

    }
    
    public final void setAssignmentNum(int assignment_num) {
        
        if(assignment_num > 0) {
            this.assignment_num = assignment_num;
        }else{
            this.assignment_num = 0;
        }
    }

    //Getters
    public int getTotalmarks() {
        return totalmarks;
    }

    public String getTitle() {
        return title;
    }

    public String getDeadline() {
        return deadline;
    }
    
    public int getAssignmentNum() {
        return assignment_num;
    }
    
    //display
    @Override
    public String toString() {
          return("Assignment Number : " +assignment_num+"\nAssignment Title : " +title+"\nDeadline : " +deadline+"\nTotal Marks : " +totalmarks);
    }

}//end of Assignment class

//Reason of using final with setters here. This class wont be extended and will not have a child class, it will exhibit relationship Courses class. There is no possibility of overriding it hence, final. 
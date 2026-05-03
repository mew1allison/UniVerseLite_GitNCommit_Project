//Assignment class

public class Assignment {

    //attributes
    private int totalmarks;
    private String title; //the description of the Assignment
    private String deadline; //the format DD/MM/YY (should we use it in this way or using month, year, day attributes??)

    //Zero Argument Constructor
    public Assignment(){

        totalmarks = 1;
        title = null;
        deadline = null;

    }

    //Argument Constructor
    public Assignment(int totalmarks, String title, String deadline){

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
            this.totalmarks = 1;  //i thought that total marks can never be zero so I gave it a 1. However, there are other possibilities as this might be non credit? what do u think?
            //Aleena: That is fine, but how will the teachers score the assignments? Hmm...I guess that's not required. But the Reportable interface marks departments based on 'performance' so we maybe we could just link it to attendance of faculty and students instead of marks.
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


}//end of Assignment class

//Reason of using final with setters here. This class wont be extended and will not have a child class, it will exhibit relationship Courses class. There is no possibility of overriding it hence, final. 
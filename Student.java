//Student class

public class Student {

    //attributes
    private String stname; //Student name;
    private String regnum; //FA25-BCS-005 format
    private String program; //Computer Science 

    //Zero Argument Constructor
    public Student() {

        stname = null;
        regnum = null;
        program = null;

    }
    //Aleena: Tip: You can do "public Student(){}" for zero argument constructors instead of initialising them to null. 

    //Argument Constructor
    public Student (String stname, String regnum, String program) {

        setStname(stname);
        setRegnum(regnum);
        setProgram(program);

    }

    //Setters
    public final void setStname(String stname) {

        if(!stname.isBlank()){
            this.stname = stname;
        }else{
            this.stname = null;
        }
        
    }

    public final void setRegnum(String regnum) {

        if(!regnum.isBlank()){
            this.regnum = regnum;
        }else{
            this.regnum = null;
        }

    }

    public final void setProgram(String program) {
        
        if(!program.isBlank()){
            this.program = program;
        }else{
            this.program = null;
        }

    }

    //Getters
    public String getStname() {
        return stname;
    }

    public String getRegnum() {
        return regnum;
    }

    public String getProgram() {
        return program;
    }

}//end of Student Class

//same reasoning for the final keyword as 'Assignment class's
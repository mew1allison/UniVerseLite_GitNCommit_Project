//Staff Class

import java.io.Serializable;

class Staff implements Serializable {
      
    //Data Members
    private String name;
    private double salaryPerHour;
    private double service_hours;
    
    //Constructors
    public Staff() {
          name = "Unknown";
          salaryPerHour = 0;
          service_hours = 0;
    }
    
    public Staff(String name, double salaryPerHour, double service_hours) {
        setName(name);
        setSalaryPerHour(salaryPerHour);
        setService_hours(service_hours);
    }
    
    //Getter Setters
    public String getName() {
        return name;
    }
    public final void setName(String name) {
          if(!name.isBlank()) {
                this.name = name;
          }else{
                this.name = "Unknown";
          }
    }
    public double getSalaryPerHour() {
        return salaryPerHour;
    }
    public final void setSalaryPerHour(double salaryPerHour) {
          if(salaryPerHour > 0) {
                this.salaryPerHour = salaryPerHour;
          }else{
                this.salaryPerHour = 0;
          }
        
    }
    public double getService_hours() {
        return service_hours;
    }
    
    public final void setService_hours(double service_hours) {
        if(service_hours > 0) {
              this.service_hours = service_hours;
        }else{
              this.service_hours = 0;
        }
    }
    
    @Override
    public String toString() {
            return ("Staff Name : " + name + "\nSalary Per Hour : " + salaryPerHour + "\nService Hours : " + service_hours);
    }
   
}//end of staff class 
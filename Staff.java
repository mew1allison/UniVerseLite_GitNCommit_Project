public class Staff {
    //Data Members
    private String name;
    private double salaryPerHour;
    private double service_hours;
    //Constructors
    public Staff(String name, double salaryPerHour, double service_hours) {
        this.name = name;
        this.salaryPerHour = salaryPerHour;
        this.service_hours = service_hours;
    }
    //Getter Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getSalaryPerHour() {
        return salaryPerHour;
    }
    public void setSalaryPerHour(double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }
    public double getService_hours() {
        return service_hours;
    }
    public void setService_hours(double service_hours) {
        this.service_hours = service_hours;
    }
    
    
}

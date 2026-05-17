package Backend;

//class HealthCenter
public class HealthCenter extends ServiceUnit implements Notifiable{

private static final long serialVersionUID = 1L;
    private int doctorsAvailable;
    
    public HealthCenter() {
        super(); 
        doctorsAvailable = 0; 
    }
    
    public HealthCenter(String name, String location, int entityID, int doctorsAvailable) {
        super(name, location, entityID);
        setDoctorsAvailable(doctorsAvailable);
    }

    public void medicalEmergency(String message, SecurityService s) {
        this.sendNotification("MEDICAL EMERGENCY REPORTED!!");
        s.sendNotification("MEDICAL EMERGENCY REPORTED!");
    } 

    @Override
    public void sendNotification(String message) {
        System.out.println("NOTIFICATION !");
        System.out.println(message);
    }

    @Override
    public String toString() {
        return super.toString() + ("\nDoctors Available : " +doctorsAvailable);
    }

    public int getDoctorsAvailable() {
        return doctorsAvailable;
    }

    public final void setDoctorsAvailable(int doctorsAvailable) {
        if(doctorsAvailable > 0) {
            this.doctorsAvailable = doctorsAvailable;
        }else{
            this.doctorsAvailable = 0;
        }
    }

}//end of HealthCenter class


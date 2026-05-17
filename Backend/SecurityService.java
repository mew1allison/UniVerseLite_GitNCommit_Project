package Backend;

public class SecurityService extends ServiceUnit implements  Notifiable{
    private static final long serialVersionUID = 1L;
    private String alertLevel;
    
    public SecurityService() { 
        super(); 
        alertLevel = "Low"; 
    }
    
    public SecurityService(String name, String location, int entityID, String alertLevel) {
        super(name, location, entityID);
        this.alertLevel = alertLevel;
    }

    public String getAlertLevel() {
        return alertLevel;
    }

    public void setAlertLevel(String alertLevel) {
        this.alertLevel = alertLevel;
    }

    @Override
    public String toString() {
        return super.toString() + ("\nAlert Level = " +alertLevel);
    }
    
    @Override
    public void sendNotification(String message) {
        System.out.println("NOTIFICATION !");
        System.out.println(message);
    }
    
}//end of SecurityService class

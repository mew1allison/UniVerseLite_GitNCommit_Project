//base class CampusEntity

abstract public class CampusEntity {

    //datamembers and common attributes
    protected String name;
    protected int entityID;
    protected String location;

    //Zero Argument Constructor
    public CampusEntity() {

        name = null;
        entityID = 0;
        location = null;
    
    }

    //Argument Constructor (uses setters to intialize - reason : instead of writing the same validation checks in both setters and argument constructor, write them in setter and call them in the argument constructor. This canot be vice versa as constructors are only called when an object is to be created.)
    public CampusEntity(String name, String location, int entityID){

        setName(name);
        setLocation(location);
        setEntityId(entityID);

    }

    //Setters (provided for admin as the admin will have full system access, so in case any attribute is to be modified it can be using setters) 
    public final void setName(String name){

        if(!name.isBlank()){
            this.name = name;
        }else{
            this.name = null;
        }

    }

    public final void setLocation(String location){

        if(!location.isBlank()){
            this.location = location;
        }else{
            this.location = null;
        }

    }

    public final void setEntityId(int entityID){

        if(entityID > 0){
            this.entityID = entityID;
        }else{
            this.entityID = 0;
        }

    }

    //Getters
     public String getName() {
        return name;
    }

    public int getEntityID() {
        return entityID;
    }

    public String getLocation() {
        return location;
    }

    //Abstract method to calculate operational costs
    abstract public double calculateOperationalCost();

}//end of CampusEntity class

//the reason the setters are declared final is: When I call the setters within the argument constructor for initialization, a problem "Overridable Method Call in Constructor" arises. This is bcz the child might override these methods and that will cause problems.
//However, in our system design the child will use the super keyword to call for the initialization of these parent attributes and has no need to override these setter methods as they serve only modificationa nd validation purpose. So they can easily be modified using the parent's setters without any need of them overriding these setters in the child classes.
//Aleena : Done :)
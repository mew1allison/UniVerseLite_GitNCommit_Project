import java.io.Serializable;

public class Equipment implements Serializable {

    //Data Members
    private String name;
    private int equip_ID;
    private double price;

    //Constructors

    public Equipment() {

        name = "Unknown";
        equip_ID = 0;
        price = 0;

    }

    public Equipment(String name, int equip_ID, double price) {

        setName(name);
        setEquip_ID(equip_ID);
        setPrice(price);

    }
    //Getters Setters

    public String getName() {
        return name;
    }

    public final void setName(String name) {

        if(!name.isBlank()){
            this.name = name;
        }else{
            this.name = "Unknown";
        }

    }
    public int getEquip_ID() {
        return equip_ID;
    }
    public final void setEquip_ID(int equip_ID) {
        
        if(equip_ID > 0) {
            this.equip_ID = equip_ID;
        }else{
            this.equip_ID = 0;
        }

    }
    public double getPrice() {
        return price;
    }
    public final void setPrice(double price) {
        
        if(price > 0){
            this.price = price;
        }else{
            this.price = 0;
        }

    }

    //Display
    //Aleena: Let's use this type of toString format for now since thats how the Source Action creates it
    @Override
    public String toString() {
        return "Equipment [name=" + name + ", equip_ID=" + equip_ID + ", price=" + price + "]";
    }
    
}//end of equipment class 

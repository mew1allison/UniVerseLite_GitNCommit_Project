public class Equipment {
    //Data Members
    private String name;
    private int equip_ID;
    private double price;
    //Constructors
    public Equipment(String name, int equip_ID, double price) {
        this.name = name;
        this.equip_ID = equip_ID;
        this.price = price;
    }
    //Getters Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getEquip_ID() {
        return equip_ID;
    }
    public void setEquip_ID(int equip_ID) {
        this.equip_ID = equip_ID;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    //Display
    //Aleena: Let's use this type of toString format for now since thats how the Source Action creates it
    @Override
    public String toString() {
        return "Equipment [name=" + name + ", equip_ID=" + equip_ID + ", price=" + price + "]";
    }
    
    
}

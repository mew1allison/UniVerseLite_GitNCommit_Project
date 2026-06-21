package Backend;

public class Cafeteria extends Facility{
private static final long serialVersionUID = 1L;
      
    //data Members
    private int fooditems; //food items present today
    private double average_meal_cost; //number of meals consumed 
      
    //Zero Argument Constructor
    public Cafeteria() {
            super();
            fooditems = 1;
            average_meal_cost = 0;
    }
      
    //Argument Constructor
    public Cafeteria(String name, String location, int entityID, double maintenance_cost, double usage_frequency, int fooditems, double average_meal_cost) {
            super(name, location, entityID, maintenance_cost, usage_frequency);
            setFoodItems(fooditems);
            setAverage_meal_cost(average_meal_cost);
    }
      
    //Setters
    public final void setFoodItems(int fooditems) {
            if(fooditems >= 1) {
                  this.fooditems = fooditems;
            }else{
                  this.fooditems = 1;
            }
    }
      
    public final void setAverage_meal_cost(double average_meal_cost) {
            if(average_meal_cost >= 0) {
                  this.average_meal_cost = average_meal_cost;
            }else{
                  this.average_meal_cost = 0;
            }
    }
      
    //Getters
    public int getfooditems() {
            return fooditems;
    }
      
    public double getaverage_meal_cost() {
            return average_meal_cost;
    }
      
    @Override
    //calculateOperationalCost
    public double calculateOperationalCost() {
            return (maintenance_cost * usage_frequency) + (fooditems * average_meal_cost);
    }
      
    @Override
    public String toString() {
            return super.toString() + ("\nUsage Frequency : " +usage_frequency);
    }

}//end of cafeteria class

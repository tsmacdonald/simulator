/**
 * Grid.java
 * 
 * Models a cartesian-based coordinate plane for the actors to interact within.
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * 
 * Wheaton College, CSCI 335, Spring 2013
 */
package universalsim;

public class Grid {
    
    /**
     * The grid of all slots containing all agents
     */
    Slot[][] grid;
    
    /**
     * Creates a grid with the given width and height specifications
     */
    public Grid(int width, int height){
        grid = new Slot[width][height];
    }
    
    
    /**
     * Causes all agents in the grid to act()
     */
    private void updateAgents(){
        for(Slot[] sArr: grid)
            for(Slot s: sArr)
                s.getAgent().act();
    }

     /**
     * Adds an agent to the slot at the given coordinates
     * Returns a boolean based on whether or not it was successful
     */
    private boolean addAgent(Agent a, int x, int y){
        return grid[x][y].addAgent(a);
    }
    
    /**
     * Returns the agent in the slot at the given coordinates
     */
    private Agent getAgent(int x, int y){
        return grid[x][y].getAgent();
    }
    
    /**
     * Removes an agent from the slot at the given coordinates
     * Returns a boolean based on whether or not it was successful
     */
    private boolean removeAgent(int x, int y){
        return grid[x][y].removeAgent();
    }
    
    
    /**
     * Sets the temperature at the slot at the given coordinates
     * TODO: Have this changed temperature affect surrounding slot temperatures
     */
    private void setTemperature(int temp, int x, int y){
        grid[x][y].setTemperature(temp);
    }
    
    /**
     * Returns the temperature of the slot at the given coordinates
     */
    private int getTemperature(int x, int y){
        return grid[x][y].getTemperature();
    }
    
    /**
     * Sets the humidity at the slot at the given coordinates
     * TODO: Have this changed humidity affect surrounding slot humidities
     */
    private void setHumidity(int hum, int x, int y){
        grid[x][y].setHumidity(hum);
    }
    
    /**
     * Returns the humidity of the slot at the given coordinates
     */
    private int getHumidity(int x, int y){
        return grid[x][y].getHumidity();
    }
    
    
}

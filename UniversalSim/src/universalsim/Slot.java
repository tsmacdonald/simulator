/**
 * Slot.java
 * 
 * Class that defines properties for a specific point in the overall grid.
 *
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 345, Spring 2013
 */

public class Slot {

    private Agent agent;
    private int temperature;
    private int humidity;

    public Slot() {}

    public Slot(int temp, int hum) {
	temperature = temp;
	humidity = hum;
    }

    public void setAgent(Agent a) {
	agent = a;
    }

    public Agent getAgent() {
	return agent;
    }

    public void setTemperature(int temp) {
	temperature = temp;
    }

    public int getTemperature() {
	return temperature;
    }

    public void setHumidity(int hum) {
	humidity = hum;
    }

    public int getHumidity() {
	return humidity;
    }

}
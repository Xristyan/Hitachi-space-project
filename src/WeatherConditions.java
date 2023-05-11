

public class WeatherConditions {
    private int lowestTemperature;
    private int highestTemperature;
    private int wind;
    private int humidity;


    public WeatherConditions(int lowestTemperature, int highestTemperature, int wind, int humidity) {
        this.lowestTemperature = lowestTemperature;
        this.highestTemperature = highestTemperature;
        this.wind = wind;
        this.humidity = humidity;
    }

    public int getLowestTemperature() {
        return lowestTemperature;
    }

    public void setLowestTemperature(int lowestTemperature) {
        this.lowestTemperature = lowestTemperature;
    }

    public int getHighestTemperature() {
        return highestTemperature;
    }

    public void setHighestTemperature(int highestTemperature) {
        this.highestTemperature = highestTemperature;
    }

    public int getWind() {
        return wind;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}

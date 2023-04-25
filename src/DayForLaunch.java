import java.util.Objects;

public class DayForLaunch {
private String day;
private int temperature;
private int wind;
private int humidity;
private int precipitation;
private String lightning;
private String clouds;

    public DayForLaunch(String day, int temperature, int wind, int humidity, int precipitation, String lightning, String clouds) {
        this.day = day;
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.precipitation = precipitation;
        this.lightning = lightning;
        this.clouds = clouds;
    }

    public String getDay() {
        return day;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getWind() {
        return wind;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public String getLightning() {
        return lightning;
    }

    public String getClouds() {
        return clouds;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setPrecipitation(int precipitation) {
        this.precipitation = precipitation;
    }

    public void setLightning(String lightning) {
        this.lightning = lightning;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayForLaunch that = (DayForLaunch) o;
        return temperature == that.temperature && wind == that.wind && humidity == that.humidity && precipitation == that.precipitation && Objects.equals(day, that.day) && Objects.equals(lightning, that.lightning) && Objects.equals(clouds, that.clouds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, temperature, wind, humidity, precipitation, lightning, clouds);
    }
}

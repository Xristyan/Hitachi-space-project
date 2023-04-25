import java.util.*;

public class Aggregation {
    private ArrayList<Integer> temperatures;
    private ArrayList<Integer> wind;
    private ArrayList<Integer> humidity;
    private ArrayList<Integer> precipitation;

    public Aggregation() {
        temperatures=new ArrayList<>();
        wind=new ArrayList<>();
        humidity=new ArrayList<>();
        precipitation=new ArrayList<>();
    }
public  float averageValue(ArrayList<Integer> data)
{
    float sum=0;
    for (int i : data) {
        sum += i;
    }
    return sum/data.size();
}
public int maxValue(ArrayList<Integer> data)
{
   return Collections.max(data);
}
public int minValue(ArrayList<Integer> data)
{
    return  Collections.min(data);
}
public float medianValue(ArrayList<Integer> data)
{
    Collections.sort(data);

    if (data.size() % 2 == 0) {
       return  (data.get(data.size() / 2) +data.get(data.size() / 2 - 1)) / 2;
    } else {
        return data.get(data.size() / 2);
    }
}


    public ArrayList<Integer> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(ArrayList<Integer> temperatures) {
        this.temperatures = temperatures;
    }

    public ArrayList<Integer> getWind() {
        return wind;
    }

    public void setWind(ArrayList<Integer> wind) {
        this.wind = wind;
    }

    public ArrayList<Integer> getHumidity() {
        return humidity;
    }

    public void setHumidity(ArrayList<Integer> humidity) {
        this.humidity = humidity;
    }

    public ArrayList<Integer> getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(ArrayList<Integer> precipitation) {
        this.precipitation = precipitation;
    }
}

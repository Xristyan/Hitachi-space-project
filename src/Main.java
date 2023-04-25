import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * For this project, I am exclusively using JavaMail to send emails from Gmail.
 * If you plan on sending emails from your own Gmail account, please ensure that you have enabled 2-factor authentication.
 * If you are unable to do so, I have provided a working email address and password that you can use for testing purposes.
 * email:weatherconditions6@gmail.com
 * password:oqvohhaqfcgotbyp
 * path to Hitachi file:src/hitachi information.csv
 */

public class Main {
    private static List<DayForLaunch> launchData= new ArrayList<>();
    private static final String WEATHER_REPORT_FILE="WeatherReport.csv";
    private static Aggregation aggregationData=new Aggregation();
    private static JsonNode languageJasonNode;
    public static void main(String[] args)  {
        userMenu();
    }

    private static void userMenu() {
            Scanner sc = new Scanner(System.in);
            System.out.println("Choose language for the application!");
            System.out.println("English or German");
            // Setting the language of the application

            while (true) {
                String language = sc.nextLine();
                if (language.equalsIgnoreCase("English")) {
                    setLanguage("src/languages/English.json");
                    break;
                }
                if (language.equalsIgnoreCase("German")) {
                     setLanguage("src/languages/German.json");
                    break;
                }
                if (!language.equalsIgnoreCase("English") || !language.equalsIgnoreCase("German")) {
                    System.out.println("Incorrect Language,try again!!!");
                }
            }

            // Getting user info and validating it

            UserInformation userInformation=userInfo(sc);

            // Setting weather conditions
            WeatherConditions weatherConditions=weatherConditions(sc);

            System.out.println(languageJasonNode.get("choosePath").asText());
            sc.nextLine();
            String path = sc.nextLine();

            //Getting the data from the provided file filtered it and add it to ArrayList,
            // also collecting the data from all days in add it different ArrayList,so we can get AVR,MAX,MIN,MEDIAN for the time period
            loadFileData(path,weatherConditions);

            fileCreation();

            saveToFile();

            sendEmail(userInformation);
    }
    private static void setLanguage(String filePath){
        // Create a new JsonNode instance,so we can read from the json file
        try {
            File file = new File(filePath);
            ObjectMapper objectMapper = new ObjectMapper();
            languageJasonNode = objectMapper.readTree(file);
        }
        catch (IOException e)
        {
            System.out.println(languageJasonNode.get("error").asText());
            e.printStackTrace();
        }
    }

    private static  void loadFileData(String filePath,WeatherConditions weatherConditions)
    {
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // Reading the first row of the CSV file to get the number of days
            line = br.readLine();
            String[] headers = line.split(",");

            // Iterating through each column
            for (int i = 0; i < headers.length; i++) {
                if (i > 0) {
                    // Creating ArrayList of data
                    ArrayList<String> data = new ArrayList<>();

                    // Creating a new BufferedReader instance for this column
                    BufferedReader columnReader = new BufferedReader(new FileReader(filePath));

                    // Iterating through each row in the column

                    while ((line = columnReader.readLine()) != null) {
                        String[] values = line.split(",");

                        // Access the value in the current column
                        String value = values[i];
                        data.add(value);
                    }
                    // Adding all the data for the day[i]
                    addInfoToArrayList(data,weatherConditions);
                    // Closing the BufferedReader for this column
                    columnReader.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(filePath +" "+ languageJasonNode.get("fileNotFound").asText());
        }
    }
    private static void addInfoToArrayList(List<String> data,WeatherConditions weatherConditions)
    {
        String dayForLaunch=data.get(0);
        int temperature=Integer.parseInt(data.get(1));
        int wind=Integer.parseInt(data.get(2));
        int humidity=Integer.parseInt(data.get(3));
        int precipitation=Integer.parseInt(data.get(4));
        String lightning=data.get(5);
        String clouds=data.get(6);

        aggregationData.getTemperatures().add(temperature);
        aggregationData.getWind().add(wind);
        aggregationData.getHumidity().add(humidity);
        aggregationData.getPrecipitation().add(precipitation);

        if (temperature>weatherConditions.getLowestTemperature() && temperature<weatherConditions.getHighestTemperature()
                && wind<=weatherConditions.getWind() && humidity<weatherConditions.getHumidity() && precipitation==0
                && lightning.equalsIgnoreCase("No") && !clouds.equalsIgnoreCase("Cumulus")
        && !clouds.equalsIgnoreCase("Nimbus"))
        {
            DayForLaunch day = new DayForLaunch(dayForLaunch, temperature, wind, humidity, precipitation, lightning, clouds);
            launchData.add(day);

        }


    }
    private static UserInformation userInfo(Scanner sc)
    {
        System.out.println(languageJasonNode.get("enterSenderEmail").asText());
        String emailOfTheSender = userInformationValidation(sc);

        System.out.println(languageJasonNode.get("enterPassword").asText());
        String password = sc.nextLine();

        System.out.println(languageJasonNode.get("enterReceiverEmail").asText());
        String emailOfTheReceiver = userInformationValidation(sc);

        UserInformation userInfo = new UserInformation(emailOfTheSender, password, emailOfTheReceiver);
        return userInfo;
    }
    private static String userInformationValidation(Scanner sc)
    {
        while (true) {
             String email = sc.nextLine();
            if (EmailValidation.validation(email))
            {
                return email;
            }
            System.out.println(languageJasonNode.get("emailError").asText());
        }
    }
private static  WeatherConditions weatherConditions(Scanner sc) {
        while(true) {
            System.out.println(languageJasonNode.get("weatherConditions").asText());
            System.out.println(languageJasonNode.get("enterLowestTemp").asText());
            int lowestTemperature = sc.nextInt();
            System.out.println(languageJasonNode.get("enterHighestTemp").asText());
            int highestTemperature = sc.nextInt();
            System.out.println(languageJasonNode.get("enterWind").asText());
            int wind = sc.nextInt();
            System.out.println(languageJasonNode.get("enterHumidity").asText());
            int humidity = sc.nextInt();
            if(lowestTemperature<highestTemperature && highestTemperature-lowestTemperature>1 && wind>=0 && humidity>=0 && humidity<=100 )
            {
                WeatherConditions weatherConditions = new WeatherConditions(lowestTemperature, highestTemperature, wind, humidity);
                return weatherConditions;
            }
            System.out.println(languageJasonNode.get("incorrectWeatherConditions").asText());
        }
}
private  static void  fileCreation()
{
    File file = new File(WEATHER_REPORT_FILE);
    try {
        boolean created = file.createNewFile();
        if (created) {
            System.out.println(languageJasonNode.get("fileCreateSuccess").asText());
        }
    } catch (IOException e) {
        System.out.println(languageJasonNode.get("errorWithFileCreation").asText());
        e.printStackTrace();
    }
}
private static void saveToFile()
{
    if (launchData.size()==0)
    {
        System.out.println(languageJasonNode.get("noDaysFound").asText());
        return;
    }
    //If we have multiple days, it is important to sort the ArrayList so that the first one is always
    // the most appropriate date for the space shuttle launch.
    if(launchData.size()>1) {
        launchData.sort(Comparator.comparingInt(DayForLaunch::getWind)
                .thenComparingInt(DayForLaunch::getHumidity));
    }
    try(BufferedWriter writer = new BufferedWriter(new FileWriter(WEATHER_REPORT_FILE))) {

        writer.write(languageJasonNode.get("dayParameter").asText()+","+launchData.get(0).getDay());
        writer.newLine();
        writer.write(languageJasonNode.get("temperature").asText()+","+launchData.get(0).getTemperature()
                +",Average:"+aggregationData.averageValue(aggregationData.getTemperatures())
                +",Max:"+aggregationData.maxValue(aggregationData.getTemperatures())
                +",Min:"+aggregationData.minValue(aggregationData.getTemperatures())
                +",Median:"+aggregationData.medianValue(aggregationData.getTemperatures()));
        writer.newLine();
        writer.write(languageJasonNode.get("wind").asText()+","+launchData.get(0).getWind()
                +",Average:"+aggregationData.averageValue(aggregationData.getWind())
                +",Max:"+aggregationData.maxValue(aggregationData.getWind())
                +",Min:"+aggregationData.minValue(aggregationData.getWind())
                +",Median:"+aggregationData.medianValue(aggregationData.getWind()));
        writer.newLine();
        writer.write(languageJasonNode.get("humidity").asText()+","+launchData.get(0).getHumidity()
                +",Average:"+aggregationData.averageValue(aggregationData.getHumidity())
                +",Max:"+aggregationData.maxValue(aggregationData.getHumidity())
                +",Min:"+aggregationData.minValue(aggregationData.getHumidity())
                +",Median:"+aggregationData.medianValue(aggregationData.getHumidity()));
        writer.newLine();
        writer.write(languageJasonNode.get("precipitation").asText()+","+launchData.get(0).getPrecipitation()
                +",Average:"+aggregationData.averageValue(aggregationData.getPrecipitation())
                +",Max:"+aggregationData.maxValue(aggregationData.getPrecipitation())
                +",Min:"+aggregationData.minValue(aggregationData.getPrecipitation())
                +",Median:"+aggregationData.medianValue(aggregationData.getPrecipitation()));
        writer.newLine();
        writer.write(languageJasonNode.get("lightning").asText()+","+launchData.get(0).getLightning());
        writer.newLine();
        writer.write(languageJasonNode.get("clouds").asText()+","+launchData.get(0).getClouds());
        writer.newLine();


    } catch (IOException e) {
        System.out.println(languageJasonNode.get("error").asText());
        e.printStackTrace();
    }
}
public static void sendEmail(UserInformation userInfo)
{
    if (launchData.size()==0)
    {
        return;
    }
    try {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.transport.protocol", "smtp");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userInfo.getEmailOfTheSender(), userInfo.getPassword());
            }
        });
        Message message = new MimeMessage(session);
        message.setSubject(languageJasonNode.get("emailSubject").asText());

        Address address = new InternetAddress(userInfo.getEmailOfTheReceiver());
        message.setRecipient(Message.RecipientType.TO, address);
        MimeMultipart multipart = new MimeMultipart();
        MimeBodyPart attachment = new MimeBodyPart();
        attachment.attachFile(WEATHER_REPORT_FILE);
        multipart.addBodyPart(attachment);

        message.setContent(multipart);

        Transport.send(message);
        System.out.println(languageJasonNode.get("emailSendSuccess").asText());
    }
    catch (IOException | MessagingException e)
    {
        System.out.println(languageJasonNode.get("errorWithSendingEmail").asText());
        e.printStackTrace();
    }
}
}



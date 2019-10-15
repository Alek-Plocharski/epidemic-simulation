import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

public class Parser {

    private long seed;
    private int numberOfAgents;
    private float sociableProbability;
    private float meetingProbability;
    private float infectionProbability;
    private float cureProbability;
    private float deathRate;
    private int numberOfDays;
    private int averageNumberOfFriends;
    private PrintWriter writer;

    /*
     * Loads value for a given key, converts it to int value and returns it.
     * In case of any errors it end the program and displays an adequate message.
     */
    private int getIntValue(String key, Properties properties) {

        String value = properties.getProperty(key);

        if (value == null) {
            System.out.println("Brak wartości dla klucza " + key);
            System.exit(1);
        }

        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.out.println("Niedozwolona wartość " + '"' + value + '"' + " dla klucza "+ key);
            System.exit(1);
        }

        return Integer.valueOf(value);
    }

    /*
     * Loads value for a given key, converts it to float value and returns it.
     * In case of any errors it end the program and displays an adequate message.
     */
    private float getFloatValue(String key, Properties properties) {

        String value = properties.getProperty(key);

        if (value == null) {
            System.out.println("Brak wartości dla klucza " + key);
            System.exit(1);
        }

        try {
            Float.parseFloat(value);
        } catch (NumberFormatException e) {
            System.out.println("Niedozwolona wartość " + '"' + value + '"' + " dla klucza "+ key);
            System.exit(1);
        }

        return Float.valueOf(value);
    }

    /*
     * Loads value for a given key, converts it to long value and returns it.
     * In case of any errors it end the program and displays an adequate message.
     */
    private long getLongValue(String key, Properties properties) {

        String value = properties.getProperty(key);

        if (value == null) {
            System.out.println("Brak wartości dla klucza " + key);
            System.exit(1);
        }

        try {
            Long.parseLong(value);
        } catch (NumberFormatException e) {
            System.out.println("Niedozwolona wartość " + '"' + value + '"' + " dla klucza "+ key);
            System.exit(1);
        }

        return Long.valueOf(value);
    }

    /*
     * Loads value for a given key and returns it.
     * In case of any errors it end the program and displays an adequate message.
     */
    private String getStringValue(String key, Properties properties) {

        String value = properties.getProperty(key);

        if (value == null) {
            System.out.println("Brak wartości dla klucza " + key);
            System.exit(1);
        }

        return value;
    }

    /*
     * Creates a PrintWriter for given path.
     * In case of any errors it ends the program and displays an adequate message.
     */
    private PrintWriter loadWriter(Properties properties) {

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(getStringValue("plikZRaportem", properties), "UTF-8");
        } catch (FileNotFoundException e) {
            System.out.println("Niedozwolona wartość " + '"' + getStringValue("plikZRaportem", properties) + '"' + " dla klucza plikZRaportem");
            System.exit(1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return writer;
    }

    /*
     * Checks if value is in given range.
     * If not it ends the program and displays an adequate message.
     */
    private void checkIfIntInRange(int from, int to, int value, String key) {

        if (value < from || value > to) {

            System.out.println("Niedozwolona wartość " + '"' + value + '"' + " dla klucza " + key);
            System.exit(1);
        }
    }

    /*
     * Checks if value is in given range.
     * If not it ends the program and displays an adequate message.
     */
    private void checkIfFloatInRangeInclusive(float to, float value, String key) {

        if (value < 0 || value > to) {

            System.out.println("Niedozwolona wartość " + '"' + value + '"' + " dla klucza " + key);
            System.exit(1);
        }
    }

    /*
     * Checks if value is in given range (excluding "to").
     * If not it ends the program and displays an adequate message.
     */
    private void checkIfFloatInRangeExclusive(float to, float value, String key) {

        if (value < 0 || value >= to) {

            System.out.println("Niedozwolona wartość " + '"' + value + '"' + " dla klucza " + key);
            System.exit(1);
        }
    }

    /*
     * Loads values from "default.properties" file.
     * In case of any errors the program is stopped and adequate message is displayed.
     */
    private void loadDefault() {

        Properties properties = new Properties();
        InputStream input;

        try {

            String filename = "default.properties";
            input = Symulacja.class.getClassLoader().getResourceAsStream(filename);

            if(input==null){
                System.out.println("Brak pliku default.properties");
                System.exit(1);
            }

            properties.load(new InputStreamReader(input, Charset.forName("UTF-8")));

            this.seed = getLongValue("seed", properties);
            this.numberOfAgents = getIntValue("liczbaAgentów", properties);
            this.checkIfIntInRange(1, 1000000, this.numberOfAgents, "liczbaAgentów");
            this.sociableProbability = getFloatValue("prawdTowarzyski", properties);
            this.checkIfFloatInRangeInclusive( 1, this.sociableProbability, "prawdTowarzyski");
            this.meetingProbability = getFloatValue("prawdSpotkania", properties);
            this.checkIfFloatInRangeExclusive( 1, this.meetingProbability, "prawdSpotkania");
            this.infectionProbability = getFloatValue("prawdZarażenia", properties);
            this.checkIfFloatInRangeInclusive( 1, this.infectionProbability, "prawdZarażenia");
            this.cureProbability = getFloatValue("prawdWyzdrowienia", properties);
            this.checkIfFloatInRangeInclusive( 1, this.cureProbability, "prawdWyzdrowienia");
            this.deathRate = getFloatValue("śmiertelność", properties);
            this.checkIfFloatInRangeInclusive( 1, this.deathRate, "śmiertelność");
            this.numberOfDays = getIntValue("liczbaDni", properties);
            this.checkIfIntInRange(1, 1000, this.numberOfDays, "liczbaDni");
            this.averageNumberOfFriends = getIntValue("śrZnajomych", properties);
            this.checkIfIntInRange(0, this.numberOfAgents - 1, this.averageNumberOfFriends, "śrZnajomych");
            this.writer = loadWriter(properties);

        } catch (IOException ex) {
            System.out.println("default.properties nie jest tekstowy");
            System.exit(1);
        }
    }

    /*
     * Loads values from "simulation-conf.xml" file.
     * In case of any errors the program is stopped and adequate message is displayed.
     */
    private void loadXml() {

        Properties properties = new Properties();
        InputStream input;

        try {

            String filename = "simulation-conf.xml";
            input = Symulacja.class.getClassLoader().getResourceAsStream(filename);

            if(input==null){
                System.out.println("Brak pliku simulation-conf.xml");
                System.exit(1);
            }

            properties.loadFromXML(input);

            if (properties.getProperty("seed") != null) this.seed = getLongValue("seed", properties);
            if (properties.getProperty("liczbaAgentów") != null) {
                this.numberOfAgents = getIntValue("liczbaAgentów", properties);
                this.checkIfIntInRange(1, 1000000, this.numberOfAgents, "liczbaAgentów");
            }
            if (properties.getProperty("prawdTowarzyski") != null) {
                this.sociableProbability = getFloatValue("prawdTowarzyski", properties);
                this.checkIfFloatInRangeInclusive( 1, this.sociableProbability, "prawdTowarzyski");
            }
            if (properties.getProperty("prawdSpotkania") != null) {
                this.meetingProbability = getFloatValue("prawdSpotkania", properties);
                this.checkIfFloatInRangeExclusive( 1, this.meetingProbability, "prawdSpotkania");
            }
            if (properties.getProperty("prawdZarażenia") != null) {
                this.infectionProbability = getFloatValue("prawdZarażenia", properties);
                this.checkIfFloatInRangeInclusive( 1, this.infectionProbability, "prawdZarażenia");
            }
            if (properties.getProperty("prawdWyzdrowienia") != null) {
                this.cureProbability = getFloatValue("prawdWyzdrowienia", properties);
                this.checkIfFloatInRangeInclusive( 1, this.cureProbability, "prawdWyzdrowienia");
            }
            if (properties.getProperty("śmiertelność") != null) {
                this.deathRate = getFloatValue("śmiertelność", properties);
                this.checkIfFloatInRangeInclusive( 1, this.deathRate, "śmiertelność");
            }
            if (properties.getProperty("liczbaDni") != null) {
                this.numberOfDays = getIntValue("liczbaDni", properties);
                this.checkIfIntInRange(1, 1000, this.numberOfDays, "liczbaDni");
            }
            if (properties.getProperty("śrZnajomych") != null) {
                this.averageNumberOfFriends = getIntValue("śrZnajomych", properties);
                this.checkIfIntInRange(0, this.numberOfAgents - 1, this.averageNumberOfFriends, "śrZnajomych");
            }
            if (properties.getProperty("plikZRaportem") != null) {
                this.writer = loadWriter(properties);
            }

        } catch (IOException ex) {
            System.out.println("simulation-conf.xml nie jest XML");
            System.exit(1);
        }
    }

    /*
     * Loads values from "default.properties" and overwrites them with values from "simulation-conf.xml".
     * In case of any errors the program is stopped and adequate message is displayed.
     * The function returns a constructed Simulation object.
     */
    public Simulation loadData() {

        this.loadDefault();
        this.loadXml();

        return new Simulation(this.seed, this.numberOfAgents, this.sociableProbability, this.meetingProbability, this.infectionProbability, this.cureProbability, this.deathRate, this.numberOfDays, this.averageNumberOfFriends, this.writer);
    }
}

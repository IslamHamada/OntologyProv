import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//an annotation to ignore unmentioned columns. Because we aren't using all og them
@JsonIgnoreProperties(ignoreUnknown = true)

public class Status {
    private String link;
    private String name;
    private int windspeed;
    private int dronespeed;
    private boolean pilotexperienced;
    private double altitude;
    private int temperature;
    private double distance_from_remote_control;
    private int battery_level;
    private boolean dirty_lenses;
    private String frame;
    private String weather;
    private boolean upside_down;
    private boolean good_motor_condition;
    private boolean going_backwards;
    private boolean indoor;
    private boolean waterproof_drone;
    private String flying_over;

    public boolean getWaterproof_drone() {
        return waterproof_drone;
    }

    public void setWaterproof_drone(boolean waterproof_drone) {
        this.waterproof_drone = waterproof_drone;
    }

    public String getFlying_over() {
        return flying_over;
    }

    public void setFlying_over(String flying_over) {
        this.flying_over = flying_over;
    }

    public Status(){

    }

    public Status(String link, String name, int windspeed, int dronespeed, boolean pilotexperienced, double altitude, int temperature, double distance_from_remote_control, int battery_level, boolean dirty_lenses, String frame, String weather, boolean upside_down, boolean good_motor_condition, boolean going_backwards, boolean indoor, boolean waterproof_drone, String flying_over) {
        this.link = link;
        this.name = name;
        this.windspeed = windspeed;
        this.dronespeed = dronespeed;
        this.pilotexperienced = pilotexperienced;
        this.altitude = altitude;
        this.temperature = temperature;
        this.distance_from_remote_control = distance_from_remote_control;
        this.battery_level = battery_level;
        this.dirty_lenses = dirty_lenses;
        this.frame = frame;
        this.weather = weather;
        this.upside_down = upside_down;
        this.good_motor_condition = good_motor_condition;
        this.going_backwards = going_backwards;
        this.indoor = indoor;
        this.waterproof_drone = waterproof_drone;
        this.flying_over = flying_over;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(int windspeed) {
        this.windspeed = windspeed;
    }

    public int getDronespeed() {
        return dronespeed;
    }

    public void setDronespeed(int dronespeed) {
        this.dronespeed = dronespeed;
    }

    public boolean getPilotexperienced() {
        return pilotexperienced;
    }

    public void setPilotexperienced(boolean pilotexperienced) {
        this.pilotexperienced = pilotexperienced;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public double getDistance_from_remote_control() {
        return distance_from_remote_control;
    }

    public void setDistance_from_remote_control(double distance_from_remote_control) {
        this.distance_from_remote_control = distance_from_remote_control;
    }

    public int getBattery_level() {
        return battery_level;
    }

    public void setBattery_level(int battery_level) {
        this.battery_level = battery_level;
    }

    public boolean getDirty_lenses() {
        return dirty_lenses;
    }

    public void setDirty_lenses(boolean dirty_lenses) {
        this.dirty_lenses = dirty_lenses;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public boolean getUpside_down() {
        return upside_down;
    }

    public void setUpside_down(boolean upside_down) {
        this.upside_down = upside_down;
    }

    public boolean getGood_motor_condition() {
        return good_motor_condition;
    }

    public void setGood_motor_condition(boolean good_motor_condition) {
        this.good_motor_condition = good_motor_condition;
    }

    public boolean getGoing_backwards() {
        return going_backwards;
    }

    public void setGoing_backwards(boolean going_backwards) {
        this.going_backwards = going_backwards;
    }

    public boolean getIndoor() {
        return indoor;
    }

    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

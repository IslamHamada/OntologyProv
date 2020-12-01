public class StatusUpdated extends Status{
    private boolean flying_altitude;
    private boolean low_altitude;
    private boolean high_altitude;
    private boolean fast_speed;
    private boolean emptybattery;
    private boolean lowbattery;

    public StatusUpdated(){

    }

    public StatusUpdated(String link, String name, int windspeed, int dronespeed, boolean pilotexperienced, double altitude, int temperature, double distance_from_remote_control, int battery_level, boolean dirty_lenses, String frame, String weather, boolean upside_down, boolean good_motor_condition, boolean going_backwards, boolean indoor, boolean waterproof_drone, String flying_over, boolean flying_altitude, boolean low_altitude, boolean high_altitude, boolean fast_speed, boolean emptybattery, boolean lowbattery) {
        super(link, name, windspeed, dronespeed, pilotexperienced, altitude, temperature, distance_from_remote_control, battery_level, dirty_lenses, frame, weather, upside_down, good_motor_condition, going_backwards, indoor, waterproof_drone, flying_over);
        this.flying_altitude = flying_altitude;
        this.low_altitude = low_altitude;
        this.high_altitude = high_altitude;
        this.fast_speed = fast_speed;
        this.emptybattery = emptybattery;
        this.lowbattery = lowbattery;
    }

    public boolean isFlying_altitude() {
        return flying_altitude;
    }

    public void setFlying_altitude(boolean flying_altitude) {
        this.flying_altitude = flying_altitude;
    }

    public boolean isLow_altitude() {
        return low_altitude;
    }

    public void setLow_altitude(boolean low_altitude) {
        this.low_altitude = low_altitude;
    }

    public boolean isHigh_altitude() {
        return high_altitude;
    }

    public void setHigh_altitude(boolean high_altitude) {
        this.high_altitude = high_altitude;
    }

    public boolean isFast_speed() {
        return fast_speed;
    }

    public void setFast_speed(boolean fast_speed) {
        this.fast_speed = fast_speed;
    }

    public boolean isEmptybattery() {
        return emptybattery;
    }

    public void setEmptybattery(boolean emptybattery) {
        this.emptybattery = emptybattery;
    }

    public boolean isLowbattery() {
        return lowbattery;
    }

    public void setLowbattery(boolean lowbattery) {
        this.lowbattery = lowbattery;
    }
}

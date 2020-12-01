public class StatusFinal extends StatusUpdated{
    private boolean riskofphysicaldamage;

    public boolean isRiskofphysicaldamage() {
        return riskofphysicaldamage;
    }

    public StatusFinal(String link, String name, int windspeed, int dronespeed, boolean pilotexperienced, double altitude, int temperature, double distance_from_remote_control, int battery_level, boolean dirty_lenses, String frame, String weather, boolean upside_down, boolean good_motor_condition, boolean going_backwards, boolean indoor, boolean waterproof_drone, String flying_over, boolean flying_altitude, boolean low_altitude, boolean high_altitude, boolean fast_speed, boolean emptybattery, boolean lowbattery, boolean riskofphysicaldamage, boolean flying) {
        super(link, name, windspeed, dronespeed, pilotexperienced, altitude, temperature, distance_from_remote_control, battery_level, dirty_lenses, frame, weather, upside_down, good_motor_condition, going_backwards, indoor, waterproof_drone, flying_over, flying_altitude, low_altitude, high_altitude, fast_speed, emptybattery, lowbattery);
        this.riskofphysicaldamage = riskofphysicaldamage;
        this.flying = flying;
    }

    public StatusFinal() {
    }

    public void setRiskofphysicaldamage(boolean riskofphysicaldamage) {
        this.riskofphysicaldamage = riskofphysicaldamage;
    }

    public boolean isFlying() {
        return flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    private boolean flying;
}

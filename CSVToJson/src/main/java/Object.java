public class Object {
    private String link;
    private String name;
    private int id_obj;
    private String type;
    private boolean moving;
    private boolean inpath;
    private double distance;
    private String time_stamp;
    private boolean obj_near;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_obj() {
        return id_obj;
    }

    public void setId_obj(int id_obj) {
        this.id_obj = id_obj;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isInpath() {
        return inpath;
    }

    public void setInpath(boolean inpath) {
        this.inpath = inpath;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public boolean isObj_near() {
        return obj_near;
    }

    public void setObj_near(boolean obj_near) {
        this.obj_near = obj_near;
    }

    public Object(String link, String name, int id_obj, String type, boolean moving, boolean inpath, double distance, String time_stamp, boolean obj_near) {
        this.link = link;
        this.name = name;
        this.id_obj = id_obj;
        this.type = type;
        this.moving = moving;
        this.inpath = inpath;
        this.distance = distance;
        this.time_stamp = time_stamp;
        this.obj_near = obj_near;
    }

    public Object(){

    }
}

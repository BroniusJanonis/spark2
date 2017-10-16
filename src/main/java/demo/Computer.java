package demo;

public class Computer {
    private int id;
    private String name;
    private String type;
    private String info;
    private int speed;

    public Computer() {
    }

    public Computer(String name, String type, String info, int speed) {
        this.name = name;
        this.type = type;
        this.info = info;
        this.speed = speed;
    }

    public Computer(int id, String name, String type, String info, int speed) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.info = info;
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                        ", name='" + name + '\'' +
                        ", osname='" + type + '\'' +
                        ", color='" + info + '\'' +
                        ", ramsize=" + speed;
    }
}

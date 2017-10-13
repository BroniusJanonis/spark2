package demo;

public class Computer55 {
    int id;
    String name;
    String osname;
    String color;
    int ramsize;

    public Computer55() {
    }

    public Computer55(int id, String name, String osname, String color, int ramsize) {
        this.id = id;
        this.name = name;
        this.osname = osname;
        this.color = color;
        this.ramsize = ramsize;
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

    public String getOsname() {
        return osname;
    }

    public void setOsname(String osname) {
        this.osname = osname;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRamsize() {
        return ramsize;
    }

    public void setRamsize(int ramsize) {
        this.ramsize = ramsize;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", name='" + name + '\'' +
                ", osname='" + osname + '\'' +
                ", color='" + color + '\'' +
                ", ramsize=" + ramsize;
    }
}

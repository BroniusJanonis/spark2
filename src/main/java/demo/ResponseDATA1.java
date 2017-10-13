package demo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResponseDATA1 {
    String info;
    List<String> stringList = Arrays.asList("list1", "list2", "list3");

    public ResponseDATA1() {
    }

    public ResponseDATA1(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

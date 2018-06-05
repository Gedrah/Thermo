package thermo.aziaka.donavan.com.thermo.Models;

import java.util.List;

public class WeatherList {
    private int cnt;
    private List<Weather> list;

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<Weather> getList() {
        return list;
    }

    public void setList(List<Weather> list) {
        this.list = list;
    }
}

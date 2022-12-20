package fr.cyu.airportmadness.datasets;

import com.opencsv.bean.CsvBindByName;


public class CsvCountry {

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @CsvBindByName
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @CsvBindByName
    private String name;
}

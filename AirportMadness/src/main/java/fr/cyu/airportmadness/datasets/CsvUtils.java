package fr.cyu.airportmadness.datasets;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

public final class CsvUtils {
    private CsvUtils() {}

    public static <T> List<T> getCsvBeans(String resourcePath, Class<? extends T> beanClass) {
        Reader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(CsvUtils.class.getClassLoader().getResourceAsStream(resourcePath))));

        CSVReader csvReader = new CSVReader(reader);
        CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<T>(csvReader).withOrderedResults(true).withType(beanClass);
        return csvToBeanBuilder.build().stream().toList();
    }
}



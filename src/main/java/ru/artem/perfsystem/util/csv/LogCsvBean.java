package ru.artem.perfsystem.util.csv;

import com.opencsv.bean.CsvBindAndJoinByPosition;
import com.opencsv.bean.CsvBindByPosition;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.lang3.StringUtils;

public class LogCsvBean {

    @CsvBindByPosition(position = 0)
    private String payload;
    @CsvBindByPosition(position = 4)
    private Double score;
    @CsvBindByPosition(position = 6)
    private String units;
    @CsvBindAndJoinByPosition(position = "7-", elementType = String.class)
    private MultiValuedMap<Integer, String> parameters;

    public LogCsvBean() {
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public MultiValuedMap<Integer, String> getParameters() {
        return parameters;
    }

    public void setParameters(MultiValuedMap<Integer, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "LogCsvBean{" +
                "payload='" + payload + '\'' +
                ", score=" + score +
                ", units='" + units + '\'' +
                ", parameters='" + StringUtils.join(parameters) + '\'' +
                '}';
    }
}

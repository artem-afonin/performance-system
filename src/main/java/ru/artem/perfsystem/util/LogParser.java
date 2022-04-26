package ru.artem.perfsystem.util;

import ru.artem.perfsystem.entity.dto.*;
import ru.artem.perfsystem.exception.LogParsingException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {

    private static final Pattern scoreLinePattern = Pattern.compile("^([^ ]+).*?(\\d+\\.\\d+) ±.*?(\\d+\\.\\d+).*?([^ ]+)$");

    public List<Report> parse(List<String> logContent) throws LogParsingException {
        logContent = List.copyOf(logContent);

        int resultStartIndex = -1;
        for (int i = 0; i < logContent.size(); i++) {
            if (logContent.get(i).matches("^# Run complete\\. Total time:.*$")) {
                resultStartIndex = i;
                break;
            }
        }
        if (resultStartIndex == -1) throw new LogParsingException("Can`t find results section");

        int scoreStartIndex = -1;
        for (int i = resultStartIndex; i < logContent.size(); i++) {
            if (logContent.get(i).matches("^Benchmark.*Mode.*Score.*Error.*Units$")) {
                scoreStartIndex = i + 1;
                break;
            }
        }
        if (scoreStartIndex == -1) throw new LogParsingException("Can`t find score section");

        List<Report> reportList = new ArrayList<>(logContent.size() - scoreStartIndex);
        Timestamp currentTs = new Timestamp(System.currentTimeMillis());
        for (int i = scoreStartIndex; i < logContent.size(); i++) {
            if (logContent.get(i).isBlank()) continue;
            if (logContent.get(i).matches(".*≈ 10⁻.*")) continue;
            Matcher matcher = scoreLinePattern.matcher(logContent.get(i));
            if (!matcher.matches()) throw new LogParsingException("Score line do not matches pattern");
            Report newReport = new Report();

            Host host = Host.findByName("skylake01").orElse(null); // TODO
            Payload payload = Payload.findByName(matcher.group(1)).orElseGet(() -> {
                Payload newPayload = new Payload();
                newPayload.setBenchmark(null);
                newPayload.setName(matcher.group(1));
                return newPayload;
            }); // TODO
            MetricType metricType = MetricType.findByName(matcher.group(4)).orElseGet(() -> {
                MetricType newMetricType = new MetricType();
                newMetricType.setName(matcher.group(4));
                return newMetricType;
            }); // TODO
            Jdk jdk = Jdk.findByName("openjdk").orElse(null);

            newReport.setHost(host);
            newReport.setPayload(payload);
            newReport.setMetricType(metricType);
            newReport.setJdk(jdk);
            newReport.setValue(Double.valueOf(matcher.group(2)));
            newReport.setDatetime(currentTs);
            reportList.add(newReport);
        }

        return reportList;
    }

}

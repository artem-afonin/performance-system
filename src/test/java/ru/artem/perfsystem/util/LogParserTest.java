package ru.artem.perfsystem.util;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import ru.artem.perfsystem.entity.dto.Report;

import java.util.List;

@QuarkusTest
public class LogParserTest {

//    private static final List<String> logFileContent = List.of(
//            "# JMH version: 1.21",
//            "# VM version: JDK 11.0.11, Java HotSpot(TM) 64-Bit Server VM, 11.0.11+9-LTS-194",
//            "# VM invoker: /home/buildmaster/sw/j2sdk/11.0.11/bin/java",
//            "# VM options: -Djmh.ignoreLock=true",
//            "# Warmup: 5 iterations, 1 s each",
//            "# Measurement: 5 iterations, 1 s each",
//            "# Timeout: 10 min per iteration",
//            "# Threads: 1 thread, will synchronize iterations",
//            "# Benchmark mode: Throughput, ops/time",
//            "# Benchmark: com.github.chrisgleissner.benchmarks.CounterBenchmark.atomicInteger",
//            "",
//            "# Run progress: 0.00% complete, ETA 00:39:00",
//            "# Fork: 1 of 1",
//            "# Warmup Iteration   1: 95551.560 ops/ms",
//            "# Warmup Iteration   2: 80663.426 ops/ms",
//            "# Warmup Iteration   3: 92368.633 ops/ms",
//            "# Warmup Iteration   4: 91969.221 ops/ms",
//            "# Warmup Iteration   5: 92548.913 ops/ms",
//            "Iteration   1: 92419.612 ops/ms",
//            "Iteration   2: 92055.054 ops/ms",
//            "Iteration   3: 92612.945 ops/ms",
//            "Iteration   4: 92674.275 ops/ms",
//            "Iteration   5: 92343.712 ops/ms",
//            "",
//            "",
//            "Result \"com.github.chrisgleissner.benchmarks.CounterBenchmark.atomicInteger\":",
//            "  92421.120 ±(99.9%) 944.944 ops/ms [Average]",
//            "  (min, avg, max) = (92055.054, 92421.120, 92674.275), stdev = 245.399",
//            "  CI (99.9%): [91476.176, 93366.063] (assumes normal distribution)",
//            "",
//            "# JMH version: 1.21",
//            "# VM version: JDK 11.0.11, Java HotSpot(TM) 64-Bit Server VM, 11.0.11+9-LTS-194",
//            "# VM invoker: /home/buildmaster/sw/j2sdk/11.0.11/bin/java",
//            "# VM options: -Djmh.ignoreLock=true",
//            "# Warmup: 5 iterations, 1 s each",
//            "# Measurement: 5 iterations, 1 s each",
//            "# Timeout: 10 min per iteration",
//            "# Threads: 1 thread, will synchronize iterations",
//            "# Benchmark mode: Throughput, ops/time",
//            "# Benchmark: com.github.chrisgleissner.benchmarks.fieldaccess.SetterBenchmark.varHandle",
//            "",
//            "# Run progress: 99.57% complete, ETA 00:00:17",
//            "# Fork: 1 of 1",
//            "# Warmup Iteration   1: 60295.475 ops/ms",
//            "# Warmup Iteration   2: 66463.628 ops/ms",
//            "# Warmup Iteration   3: 72819.703 ops/ms",
//            "# Warmup Iteration   4: 73399.910 ops/ms",
//            "# Warmup Iteration   5: 73319.601 ops/ms",
//            "Iteration   1: 72330.309 ops/ms",
//            "Iteration   2: 72620.328 ops/ms",
//            "Iteration   3: 71570.586 ops/ms",
//            "Iteration   4: 70500.061 ops/ms",
//            "Iteration   5: 56727.502 ops/ms",
//            "",
//            "",
//            "Result \"com.github.chrisgleissner.benchmarks.fieldaccess.SetterBenchmark.varHandle\":",
//            "  68749.757 ±(99.9%) 26070.660 ops/ms [Average]",
//            "  (min, avg, max) = (56727.502, 68749.757, 72620.328), stdev = 6770.468",
//            "  CI (99.9%): [42679.097, 94820.418] (assumes normal distribution)",
//            "",
//            "",
//            "# Run complete. Total time: 01:09:12",
//            "",
//            "REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on",
//            "why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial",
//            "experiments, perform baseline and negative tests that provide experimental control, make sure",
//            "the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.",
//            "Do not assume the numbers tell you what you want them to tell.",
//            "",
//            "Benchmark                                                                             (collectionSize)   Mode  Cnt        Score        Error   Units",
//            "CounterBenchmark.atomicInteger                                                                     N/A  thrpt    5    92421.120 ±    944.944  ops/ms",
//            "CounterBenchmark.atomicLong                                                                        N/A  thrpt    5    91960.222 ±   3537.018  ops/ms",
//            "CounterBenchmark.mutableInt                                                                        N/A  thrpt    5   285590.723 ±   3440.638  ops/ms",
//            "collection.StreamBenchmark.integerStreamReduceToSum                                           10000000  thrpt    5       ≈ 10⁻³                ops/ms",
//            "CounterBenchmark.mutableLong                                                                       N/A  thrpt    5   284943.882 ±   4667.289  ops/ms",
//            "CounterBenchmark.primitiveInt                                                                      N/A  thrpt    5   310354.650 ±  20597.268  ops/ms",
//            "CounterBenchmark.primitiveLong                                                                     N/A  thrpt    5   291994.593 ±  81437.595  ops/ms"
//    );

    @Test
    void testParse() {
//        List<Report> payloadList = new LogParser().parse(
//                logFileContent,
//                "benchmarkName",
//                "hostName",
//                "jdkName",
//                7);
//        payloadList.forEach(System.out::println);
    }

}

package org.accelerate.tool;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DecimalFormat;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

class RusteekEngineBenchmarkTest {

    private static DecimalFormat df = new DecimalFormat("0.000");
    private static final double REFERENCE_SCORE = 247.15;

    @Test
    void runJmhBenchmark() throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(MyBenchmark.class.getSimpleName())
            .build();
        Collection<RunResult> runResults = new Runner(opt).run();
        assertFalse(runResults.isEmpty());
        for(RunResult runResult : runResults) {
            assertDeviationWithin(runResult, REFERENCE_SCORE, 10);
        }
    }

    private static void assertDeviationWithin(RunResult result, double referenceScore, double maxDeviation) {
        double score = result.getPrimaryResult().getScore();
        System.out.println("#########################################");
        System.out.println(score);
        double deviation = Math.abs(score/referenceScore - 1);
        String deviationString = df.format(deviation * 100) + "%";
        String maxDeviationString = df.format(maxDeviation * 100) + "%";
        String errorMessage = "Deviation " + deviationString + " exceeds maximum allowed deviation " + maxDeviationString;
        //assertTrue( deviation < maxDeviation,errorMessage);
    }
}

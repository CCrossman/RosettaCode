package com.crossman;

import com.crossman.app.writer.Writer;

public class WriterApplication implements Runnable {
    @Override
    public void run() {
        // Calculates the Golden Ratio
        Writer.string(5.0)
            .flatMap(WriterApplication::_root)
            .flatMap(WriterApplication::_addOne)
            .flatMap(WriterApplication::_half)
            .run((value, logs) -> {
                System.err.println("value: " + value);

                logs.forEach(log -> {
                    System.out.println("  - " + log);
                });
            });
    }

    private static Writer<Double,String> _addOne(Double d) {
        return Writer.string(1 + d).log("Added one to " + d);
    }

    private static Writer<Double,String> _half(Double d) {
        return Writer.string(d / 2.0).log("Divided " + d + " in half");
    }

    private static Writer<Double,String> _root(Double d) {
        return Writer.string(Math.sqrt(d)).log("Took the square root of " + d);
    }
}

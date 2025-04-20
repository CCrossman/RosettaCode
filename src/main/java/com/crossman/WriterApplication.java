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

    public static void main(String[] args) {
        // 1. Construct a Writer monad
        //    - bind function
        //    - unit function
        // 2. Write three simple functions
        //    - root
        //    - addOne
        //    - half
        // 3. Derive Writer monad versions of these functions
        // 4. Apply a composition of the Writer versions
        //    - start with 5
        //    - (root(5) + 1) / 2 = Golden Ratio
        new WriterApplication().run();
    }
}

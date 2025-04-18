package com.crossman;

public class Main {

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

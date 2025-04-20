package com.crossman.app.either.v3;

import java.util.function.Function;

public record Left<L,R>(L value) implements RightBiasedEither<L, R> {

    @Override
    public <X> X fold(Function<L, X> onLeft, Function<R, X> onRight) {
        return onLeft.apply(value);
    }
}

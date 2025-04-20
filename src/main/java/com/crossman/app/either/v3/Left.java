package com.crossman.app.either.v3;

import java.util.function.Function;

public record Left<L,R>(L value) implements Either<L,R> {
    @Override
    public <X> X fold(Function<L, X> onLeft, Function<R, X> onRight) {
        return onLeft.apply(value);
    }

    @Override
    public Either<R, L> swap() {
        return new Right<>(value);
    }
}

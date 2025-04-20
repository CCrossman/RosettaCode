package com.crossman.app.either.v3;

import java.util.function.Function;

public sealed interface ProjectedEither<L,R> extends Either<L,R> permits LeftProjected, RightProjected {

    public Either<L,R> delegate();

    public default <X> X fold(Function<L, X> onLeft, Function<R, X> onRight) {
        return delegate().fold(onLeft, onRight);
    }

    public default LeftProjected<L,R> projectLeft() {
        return new LeftProjected<>(delegate());
    }

    public default RightProjected<L,R> projectRight() {
        return new RightProjected<>(delegate());
    }

    public ProjectedEither<R,L> reflect();

    public ProjectedEither<R,L> swap();
}

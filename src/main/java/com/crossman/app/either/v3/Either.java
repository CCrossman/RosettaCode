package com.crossman.app.either.v3;

import java.util.function.Function;

public sealed interface Either<L,R> permits RightBiasedEither {

    public <X> X fold(Function<L,X> onLeft, Function<R,X> onRight);

    public default RightBiasedEither<R,L> swap() {
        return fold(Right::new, Left::new);
    }
}

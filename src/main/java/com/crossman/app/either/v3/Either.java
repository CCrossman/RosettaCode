package com.crossman.app.either.v3;

import java.util.function.Function;

public sealed interface Either<L,R> permits Left, ProjectedEither, Right {

    public <X> X fold(Function<L,X> onLeft, Function<R,X> onRight);

    public Either<R,L> swap();
}

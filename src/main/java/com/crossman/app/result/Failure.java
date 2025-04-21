package com.crossman.app.result;

import java.util.function.Function;

public record Failure<T>(Throwable cause) implements Result<T> {
    @Override
    public <X> X fold(Function<T, X> onSuccess, Function<Throwable, X> onFailure) {
        return onFailure.apply(cause);
    }
}

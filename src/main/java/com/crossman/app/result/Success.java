package com.crossman.app.result;

import java.util.function.Function;

public record Success<T>(T value) implements Result<T> {
    @Override
    public <X> X fold(Function<T, X> onSuccess, Function<Throwable, X> onFailure) {
        return onSuccess.apply(value);
    }
}

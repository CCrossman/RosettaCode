package com.crossman.app.either.v3;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public sealed interface RightBiasedEither<L, R> extends Either<L,R> permits Left, Right {

    public default boolean contains(R match) {
        var r = getOrElseNull();
        if (r == null) {
            return false;
        }
        return r.equals(match);
    }

    public default <S> RightBiasedEither<L, S> flatMap(Function<R, RightBiasedEither<L, S>> fn) {
        return fold(Left::new, fn);
    }

    public default R getOrElse(Supplier<R> defaultSupplier) {
        return Optional.ofNullable(getOrElseNull()).orElseGet(defaultSupplier);
    }

    public default R getOrElseNull() {
        return fold(_ -> null, r -> r);
    }

    public default R getOrElseThrow() {
        return Optional.ofNullable(getOrElseNull()).orElseThrow();
    }

    public default <S> RightBiasedEither<L,S> map(Function<R, S> fn) {
        return flatMap(r -> {
            var s = fn.apply(r);
            return new Right<>(s);
        });
    }

    public default Optional<R> toOptional() {
        return fold(_ -> Optional.empty(), Optional::ofNullable);
    }

    public default <S,T> RightBiasedEither<L,T> zip(RightBiasedEither<L, S> that, BiFunction<R, S, T> fn) {
        return flatMap(r -> {
            return that.map(s -> {
                return fn.apply(r,s);
            });
        });
    }
}

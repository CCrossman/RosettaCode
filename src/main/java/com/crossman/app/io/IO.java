package com.crossman.app.io;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public final class IO<T> implements Supplier<T> {
    private final Supplier<T> thunk;

    public IO(Supplier<T> thunk) {
        this.thunk = requireNonNull(thunk);
    }

    public <U> IO<U> ap(IO<Function<T,U>> io) {
        return zip(io, (t,f) -> f.apply(t));
    }

    public <U> IO<U> flatMap(Function<T,IO<U>> fn) {
        return new IO<>(() -> {
            var uIO = requireNonNull(fn.apply(thunk.get()));
            return uIO.get();
        });
    }

    public <U> IO<T> flatTap(Function<T,IO<U>> fn) {
        return flatMap(t -> {
            // performs any associated effects
            var uResult = fn.apply(t);

            // but does not keep the result
            return uResult.map(_ -> t);
        });
    }

    @Override
    public T get() {
        return thunk.get();
    }

    public <U> IO<U> map(Function<T,U> fn) {
        return new IO<>(() -> {
            return fn.apply(thunk.get());
        });
    }

    public IO<T> memoized() {
        return new IO<>(new Supplier<>() {
            private T valueOrNull;

            @Override
            public T get() {
                if (valueOrNull == null) {
                    valueOrNull = thunk.get();
                }
                return valueOrNull;
            }
        });
    }

    public <U,V> IO<V> zip(IO<U> that, BiFunction<T,U,V> fn) {
        return flatMap(t -> {
            return that.map(u -> {
                return fn.apply(t,u);
            });
        });
    }
}

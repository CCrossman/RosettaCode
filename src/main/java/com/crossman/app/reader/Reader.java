package com.crossman.app.reader;

import com.crossman.util.TriFunction;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public final class Reader<E,T> {
    private final Function<E, T> effect;

    private Reader(Function<E, T> effect) {
        this.effect = requireNonNull(effect);
    }

    public <D> Reader<D,T> contraMap(Function<D,E> fn) {
        var self = this;
        return of(d -> {
            var e = fn.apply(d);
            return self.run(e);
        });
    }

    public <U> Reader<E,U> flatMap(Function<T,Reader<E,U>> fn) {
        return of(e -> {
            var t = run(e);
            var uReader = fn.apply(t);
            return uReader.run(e);
        });
    }

    public <U> Reader<E,U> map(Function<T,U> fn) {
        return flatMap(t -> {
            var u = fn.apply(t);
            return Reader.unit(u);
        });
    }

    public T run(E env) {
        return effect.apply(env);
    }

    public <U,V> Reader<E,V> zip(Reader<E,U> that, TriFunction<E,T,U,V> fn) {
        return this.flatMap(t -> {
            return that.flatMap(u -> {
                return Reader.of(e -> {
                    return fn.apply(e,t,u);
                });
            });
        });
    }

    public static <E,T> Reader<E,T> of(Function<E,T> fn) {
        return new Reader<>(fn);
    }

    public static <E,T> Reader<E,T> unit(T t) {
        return of(ignored -> t);
    }
}

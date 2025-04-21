package com.crossman.app.result;

import java.util.function.BiFunction;
import java.util.function.Function;

public sealed interface Result<T> permits Failure, Success {

    public default <U> Result<U> ap(Result<Function<T,U>> applier) {
        return zip(applier, (t,f) -> f.apply(t));
    }

    public default <U> Result<U> flatMap(Function<T,Result<U>> fn) {
        try {
            return fold(fn, Failure::new);
        } catch (Throwable cause) {
            return new Failure<>(cause);
        }
    }

    public default <U> Result<T> flatTap(Function<T,Result<U>> fn) {
        return flatMap(t -> {
            // performs any associated effects
            var uResult = fn.apply(t);

            // but does not keep the result
            return uResult.map(_ -> t);
        });
    }

    public <X> X fold(Function<T,X> onSuccess, Function<Throwable,X> onFailure);

    public default <X> Result<X> foldM(Function<T,Result<X>> onSuccess, Function<Throwable,Result<X>> onFailure) {
        Function<T, Result<X>> _onSuccess = t -> {
            try {
                return onSuccess.apply(t);
            } catch (Throwable cause) {
                return new Failure<>(cause);
            }
        };
        Function<Throwable, Result<X>> _onFailure = err -> {
            try {
                return onFailure.apply(err);
            } catch (Throwable cause) {
                return new Failure<>(cause);
            }
        };
        return fold(_onSuccess, _onFailure);
    }

    public default <U> Result<U> map(Function<T,U> fn) {
        return flatMap(t -> {
            var u = fn.apply(t);
            return new Success<>(u);
        });
    }

    public default <U,V> Result<V> zip(Result<U> that, BiFunction<T,U,V> fn) {
        return flatMap(t -> {
            return that.map(u -> {
                return fn.apply(t,u);
            });
        });
    }
}

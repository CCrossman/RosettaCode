package com.crossman.app.either.v3;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public record RightProjected<L,R>(Either<L,R> delegate) implements ProjectedEither<L,R> {
    public RightProjected {
        requireNonNull(delegate);
    }

    public boolean contains(R value) {
        if (delegate instanceof Right<L,R> right) {
            return Objects.equals(value,right.value());
        }
        return false;
    }

    public <S> RightProjected<L,S> flatMap(Function<R,Either<L,S>> fn) {
        if (delegate instanceof Left<L,R> left) {
            return new RightProjected<>(new Left<>(left.value()));
        }
        if (delegate instanceof Right<L,R> right) {
            return new RightProjected<>(fn.apply(right.value()));
        }
        throw new UnsupportedOperationException();
    }

    public R getOrElse(Supplier<R> defaultSupplier) {
        if (delegate instanceof Right<L,R> right) {
            return Optional.ofNullable(right.value()).orElseGet(defaultSupplier);
        }
        return null;
    }

    public R getOrElseThrow(Supplier<R> defaultSupplier) {
        if (delegate instanceof Right<L,R> right) {
            return Optional.ofNullable(right.value()).orElseGet(defaultSupplier);
        }
        throw new NoSuchElementException();
    }

    public <S> RightProjected<L,S> map(Function<R,S> fn) {
        return flatMap(r -> {
            var s = fn.apply(r);
            return new RightProjected<>(new Right<>(s));
        });
    }

    @Override
    public ProjectedEither<R, L> reflect() {
        return new LeftProjected<>(delegate().swap());
    }

    @Override
    public ProjectedEither<R, L> swap() {
        return new RightProjected<>(delegate().swap());
    }

    public Optional<R> toOptional() {
        return delegate().fold(ignored -> Optional.empty(), Optional::ofNullable);
    }

    public <S,T> RightProjected<L,T> zip(RightProjected<L,S> that, BiFunction<R,S,T> fn) {
        return flatMap(r -> {
            return that.map(s -> {
                return fn.apply(r,s);
            });
        });
    }
}

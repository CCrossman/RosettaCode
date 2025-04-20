package com.crossman.app.either.v3;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public record LeftProjected<L,R>(Either<L,R> delegate) implements ProjectedEither<L,R> {
    public LeftProjected {
        requireNonNull(delegate);
    }

    public boolean contains(L value) {
        if (delegate instanceof Left<L,R> left) {
            return Objects.equals(value,left.value());
        }
        return false;
    }

    public <M> LeftProjected<M,R> flatMap(Function<L,Either<M,R>> fn) {
        if (delegate instanceof Left<L,R> left) {
            return new LeftProjected<>(fn.apply(left.value()));
        }
        if (delegate instanceof Right<L,R> right) {
            return new LeftProjected<>(new Right<>(right.value()));
        }
        throw new UnsupportedOperationException();
    }

    public L getOrElse(Supplier<L> defaultSupplier) {
        if (delegate instanceof Left<L,R> left) {
            return Optional.ofNullable(left.value()).orElseGet(defaultSupplier);
        }
        return null;
    }

    public L getOrElseThrow(Supplier<L> defaultSupplier) {
        if (delegate instanceof Left<L,R> left) {
            return Optional.ofNullable(left.value()).orElseGet(defaultSupplier);
        }
        throw new NoSuchElementException();
    }

    public <M> LeftProjected<M,R> map(Function<L,M> fn) {
        return flatMap(l -> {
            var m = fn.apply(l);
            return new LeftProjected<>(new Left<>(m));
        });
    }

    @Override
    public ProjectedEither<R, L> reflect() {
        return new RightProjected<>(delegate().swap());
    }

    @Override
    public ProjectedEither<R, L> swap() {
        return new LeftProjected<>(delegate().swap());
    }

    public Optional<L> toOptional() {
        return delegate().fold(Optional::ofNullable, ignored -> Optional.empty());
    }

    public <M,N> LeftProjected<N,R> zip(LeftProjected<M,R> that, BiFunction<L,M,N> fn) {
        return flatMap(l -> {
            return that.map(m -> {
                return fn.apply(l,m);
            });
        });
    }
}

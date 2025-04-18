package com.crossman.app.writer;

import com.crossman.util.ImmutableList;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public final class Writer<T,M> {
    private final T value;
    private final ImmutableList<M> logs;

    private Writer(T value, ImmutableList<M> logs) {
        this.value = value;
        this.logs = requireNonNull(logs);
    }

    public <U> Writer<U,M> bind(Function<T, Writer<U,M>> fn) {
        return flatMap(fn);
    }

    public <U> Writer<U,M> flatMap(Function<T, Writer<U,M>> fn) {
        var uWriter = fn.apply(value);
        return new Writer<>(uWriter.value, this.logs.thaw(messages -> {
            uWriter.logs.forEach(messages::add);
        }));
    }

    public Writer<T,M> log(M message) {
        var updatedLogs = logs.thaw(list -> list.add(message));
        return new Writer<>(value, updatedLogs);
    }

    public void run(BiConsumer<T, ImmutableList<M>> blk) {
        blk.accept(value,logs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Writer<?, ?> writer)) return false;
        return Objects.equals(value, writer.value) && Objects.equals(logs, writer.logs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, logs);
    }

    @Override
    public String toString() {
        return "Writer{" +
                "value=" + value +
                ", logs=" + logs +
                '}';
    }

    public static <T,M> Writer<T,M> of(T value, Class<M> ignored) {
        return new Writer<>(value, ImmutableList.empty());
    }

    public static <T> Writer<T,String> string(T value) {
        return of(value, String.class);
    }
}

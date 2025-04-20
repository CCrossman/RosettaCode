package com.crossman;

import com.crossman.app.reader.Reader;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class ReaderApplication implements Runnable {
    @Override
    public void run() {
        var id = UUID.randomUUID();
        var dbReader = newDatabaseReader(id);
        var ageReader = newServiceReader(id);

        var uuidUserReader = dbReader.zip(ageReader, User::new);

        System.err.println("myUser:    " + uuidUserReader.run(id));
        System.err.println("otherUser: " + uuidUserReader.run(UUID.randomUUID()));

        var strUserReader = uuidUserReader.contraMap(UUID::fromString);

        var rawId = id.toString();
        System.err.println("myStr2User: " + strUserReader.run(rawId));
        System.err.println("other2User: " + strUserReader.run("foo"));
    }

    private static Reader<UUID, Name> newDatabaseReader(UUID matchingId) {
        requireNonNull(matchingId);

        return Reader.of(id -> {
            if (matchingId.equals(id)) {
                return new Name("Christopher", "Crossman");
            }
            return null;
        });
    }

    private static Reader<UUID, Age> newServiceReader(UUID matchingId) {
        requireNonNull(matchingId);

        return Reader.of(id -> {
            if (matchingId.equals(id)) {
                return new Age(41);
            }
            return null;
        });
    }

    public static record User(UUID id, Name name, Age age) { }

    public static record Name(String firstName, String lastName) {
        public Name(String firstName, String lastName) {
            this.firstName = (firstName == null ? "" : firstName).trim();
            this.lastName = (lastName == null ? "" : lastName).trim();
        }
    }

    public static record Age(int years) {
        public Age {
            if (0 >= years || years >= 150) {
                throw new IllegalArgumentException("Age should be between 0 and 150, exclusive");
            }
        }
    }

    public static void main(String[] args) {
        new ReaderApplication().run();
    }
}

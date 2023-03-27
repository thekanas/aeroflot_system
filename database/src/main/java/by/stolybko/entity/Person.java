package by.stolybko.entity;

import java.util.Objects;

public class Person {
    private String name;
    private String position;

    public Person(String name, String position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return name.equals(person.name) && position.equals(person.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position);
    }
}

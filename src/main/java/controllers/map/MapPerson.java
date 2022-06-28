package controllers.map;

import classesandenums.Person;

import java.util.Objects;

public class MapPerson {

    private final Person person;
    private double x;
    private double y;
    private double size;

    public MapPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MapPerson)) {
            return false;
        }
        MapPerson mapPerson = (MapPerson) o;
        return person.equals(mapPerson.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person);
    }

}

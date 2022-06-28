package classesandenums;

import java.time.LocalDateTime;
import java.util.Objects;

public class Person implements Comparable<Person> {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private int height;
    private EColor eyeColor;
    private HColor hairColor;
    private Country nationality;
    private Location location;
    private User owner;
    private static final double min_height = 0;

    public Person(Long id, String name, Coordinates coordinates, LocalDateTime creationDate, int height,
                  EColor eyeColor, HColor hairColor, Country nationality, Location location, User user) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
        this.owner = user;
    }

    public Person(Long id, String name, Coordinates coordinates, LocalDateTime creationDate, int height,
                  EColor eyeColor, HColor hairColor, Country nationality, Location location) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public int getHeight() {
        return height;
    }

    public EColor getEyeColor() {
        return eyeColor;
    }

    public HColor getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public Location getLocation() {
        return location;
    }

    public int compareTo(Person personObj) {
        return id.compareTo(personObj.getId());

    }

    public static boolean checkValidHeight(int height) {
        return !(height < min_height);
    }

    public String toString() {
        String info = "";
        info += "Person " + id;
        info += " (creationDate " + creationDate.toLocalDate() + " " + creationDate.toLocalTime() + ")";
        info += "\n name: " + name;
        info += "\n coordinates: " + coordinates;
        info += "\n height: " + height;
        info += "\n eyeColor: " + eyeColor;
        info += "\n hairColor: " + hairColor;
        info += "\n nationality: " + nationality;
        info += "\n location: " + location;
        info += "\n owner: " + owner.getUsername();
        return info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        Person person = (Person) o;
        return getId().equals(person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setEyeColor(EColor eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setHairColor(HColor hairColor) {
        this.hairColor = hairColor;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }
}
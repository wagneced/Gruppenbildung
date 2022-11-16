package ch.zhaw.springboot.model;

import ch.zhaw.springboot.entities.Person;

public class TemporaryExtendedPersonObject {
    private Person person;
    private int score;

    public TemporaryExtendedPersonObject(Person person, int score) {
        this.person = person;
        this.score = score;
    }

    public Person getPerson() {
        return this.person;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

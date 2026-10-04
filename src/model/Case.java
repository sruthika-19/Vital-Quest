package model;
import interfaces.Investigable;
import java.util.List;

public class Case implements Investigable {
    private String id;
    private String title;
    private String scenario;
    private List<Clue> clues;
    private List<Theory> theories;
    private String educationalLesson;

    public Case(String id, String title, String scenario, List<Clue> clues, List<Theory> theories, String lesson) {
        this.id = id;
        this.title = title;
        this.scenario = scenario;
        this.clues = clues;
        this.theories = theories;
        this.educationalLesson = lesson;
    }

    @Override public String getId() { return id; }
    @Override public String getTitle() { return title; }
    @Override public String getScenario() { return scenario; }
    @Override public List<Clue> getClues() { return clues; }
    @Override public List<Theory> getTheories() { return theories; }
    @Override public String getEducationalLesson() { return educationalLesson; }
}
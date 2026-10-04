package model;
import java.util.List;

public class Clue {
    private String description;
    private List<String> concepts; // Used for deduction mapping
    private boolean isRedHerring;

    public Clue(String description, List<String> concepts, boolean isRedHerring) {
        this.description = description;
        this.concepts = concepts;
        this.isRedHerring = isRedHerring;
    }

    public String getDescription() { return description; }
    public List<String> getConcepts() { return concepts; }
    public boolean isRedHerring() { return isRedHerring; }
}
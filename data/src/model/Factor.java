package model;
import java.util.List;

public class Factor {
    private String description;
    private List<String> concepts;
    private boolean isLessRelevant;

    public Factor(String description, List<String> concepts, boolean isLessRelevant) {
        this.description = description;
        this.concepts = concepts;
        this.isLessRelevant = isLessRelevant;
    }

    public String getDescription() { return description; }
    public List<String> getConcepts() { return concepts; }
    public boolean isLessRelevant() { return isLessRelevant; }
}
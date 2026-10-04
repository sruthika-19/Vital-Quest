package model;
import java.util.List;

public class Theory {
    private String description;
    private List<String> requiredConcepts;

    public Theory(String description, List<String> requiredConcepts) {
        this.description = description;
        this.requiredConcepts = requiredConcepts;
    }

    public String getDescription() { return description; }
    public List<String> getRequiredConcepts() { return requiredConcepts; }
    
    @Override
    public String toString() { return description; }
}
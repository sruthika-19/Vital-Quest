package model;
import java.util.List;

public class Assessment {
    private String id;
    private String description;
    private List<String> requiredConcepts;

    public Assessment(String id, String description, List<String> requiredConcepts) {
        this.id = id;
        this.description = description;
        this.requiredConcepts = requiredConcepts;
    }

    public String getId() { return id; }
    public String getDescription() { return description; }
    public List<String> getRequiredConcepts() { return requiredConcepts; }
    
    @Override public String toString() { return description; }
}
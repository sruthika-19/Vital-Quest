package model;
import interfaces.Analyzeable;
import java.util.List;

public class Scenario implements Analyzeable {
    private String id, title, category, difficulty, scenario, wellnessInsight, bestAssessmentId;
    private List<Factor> factors;
    private List<Assessment> assessments;

    public Scenario(String id, String title, String category, String difficulty, String scenario, 
                    List<Factor> factors, List<Assessment> assessments, String bestAssessmentId, String insight) {
        this.id = id; this.title = title; this.category = category; this.difficulty = difficulty;
        this.scenario = scenario; this.factors = factors; this.assessments = assessments; 
        this.bestAssessmentId = bestAssessmentId; this.wellnessInsight = insight;
    }

    @Override public String getId() { return id; }
    @Override public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getDifficulty() { return difficulty; }
    @Override public String getScenario() { return scenario; }
    @Override public List<Factor> getFactors() { return factors; }
    @Override public List<Assessment> getAssessments() { return assessments; }
    @Override public String getWellnessInsight() { return wellnessInsight; }
    @Override public String getBestAssessmentId() { return bestAssessmentId; }
    
    public Assessment getBestAssessment() {
        for (Assessment a : assessments) {
            if (a.getId().equals(bestAssessmentId)) return a;
        }
        return null;
    }
}
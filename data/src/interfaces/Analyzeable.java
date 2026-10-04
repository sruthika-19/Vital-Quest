package interfaces;
import java.util.List;
import model.Factor;
import model.Assessment;

public interface Analyzeable {
    String getId();
    String getTitle();
    String getScenario();
    List<Factor> getFactors();
    List<Assessment> getAssessments();
    String getWellnessInsight();
    String getBestAssessmentId();
}
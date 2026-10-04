package interfaces;
import java.util.List;
import model.Clue;
import model.Theory;

// Concept 6: Interfaces
public interface Investigable {
    String getId();
    String getTitle();
    String getScenario();
    List<Clue> getClues();
    List<Theory> getTheories();
    String getEducationalLesson();
}
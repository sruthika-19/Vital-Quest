package engine;
import java.util.*;
import model.*;
import util.Repository;

public class GameEngine {
    private Repository<Scenario> scenarioRepo = new Repository<>();
    private Map<String, Scenario> scenarioMap = new HashMap<>();

    public GameEngine() { loadScenarios(); }
    public Repository<Scenario> getScenarioRepository() { return scenarioRepo; }
    public Scenario getScenarioById(String id) { return scenarioMap.get(id); }

    public AnalysisResult processAnalysis(Scenario s, List<Factor> selectedFactors, Assessment userAssessment, String confidence, String reasoning) {
        Assessment bestAssessment = s.getBestAssessment();
        List<String> expectedConcepts = bestAssessment.getRequiredConcepts();
        
        // 1. Assessment Score (Max 50) - Strictly isolated logic!
        int aScore = 10;
        String category = "LESS SUPPORTED";
        
        if (userAssessment.getId().equals(bestAssessment.getId())) {
            aScore = 50;
            category = "STRONGLY SUPPORTED";
        } else {
            // Check if there is any conceptual overlap to grant partial credit
            for (String req : expectedConcepts) {
                if (userAssessment.getRequiredConcepts().contains(req)) {
                    aScore = 25;
                    category = "PARTIALLY SUPPORTED";
                    break;
                }
            }
        }

        // 2. Factor Score (Max 30) - Evaluated against the BEST assessment requirements
        List<Factor> relevantFactorsInScenario = new ArrayList<>();
        for (Factor f : s.getFactors()) {
            if (f.isLessRelevant()) continue;
            boolean isRelevant = false;
            for (String c : f.getConcepts()) {
                if (expectedConcepts.contains(c)) { isRelevant = true; break; }
            }
            if (isRelevant) relevantFactorsInScenario.add(f);
        }

        List<String> identified = new ArrayList<>();
        List<String> lessRelevantList = new ArrayList<>();
        int correctSelected = 0;
        
        for (Factor f : selectedFactors) {
            if (f.isLessRelevant()) {
                lessRelevantList.add(f.getDescription());
            } else if (relevantFactorsInScenario.contains(f)) {
                correctSelected++;
                identified.add(f.getDescription());
            }
        }

        double factorRatio = relevantFactorsInScenario.isEmpty() ? 0 : (double) correctSelected / relevantFactorsInScenario.size();
        int fScore = (int) (factorRatio * 30);
        fScore -= (lessRelevantList.size() * 5); // Penalty
        fScore = Math.max(0, Math.min(30, fScore));

        List<String> missed = new ArrayList<>();
        for (Factor f : relevantFactorsInScenario) {
            if (!selectedFactors.contains(f)) missed.add(f.getDescription());
        }

        // 3. Reasoning Score (Max 10) - Normalizing concepts
        int reasoningMatches = 0;
        String lowerReasoning = reasoning.toLowerCase();
        
        for (String expectedConcept : expectedConcepts) {
            String normalizedConcept = expectedConcept.replace("_", " ").toLowerCase();
            if (lowerReasoning.contains(normalizedConcept)) {
                reasoningMatches++;
            }
        }
        
        int rScore = 0;
        if (reasoningMatches >= 2) rScore = 10;
        else if (reasoningMatches == 1) rScore = 5;

        // 4. Confidence Score (Max 10)
        int cScore = 0;
        switch (category) {
            case "STRONGLY SUPPORTED":
                if (confidence.equals("High")) cScore = 10; else if (confidence.equals("Moderate")) cScore = 5; else cScore = 2;
                break;
            case "PARTIALLY SUPPORTED":
                if (confidence.equals("Moderate")) cScore = 10; else if (confidence.equals("High") || confidence.equals("Low")) cScore = 5;
                break;
            case "LESS SUPPORTED":
                if (confidence.equals("Low")) cScore = 10; else if (confidence.equals("Moderate")) cScore = 5; else cScore = 0;
                break;
        }

        int total = Math.max(0, Math.min(100, aScore + fScore + rScore + cScore));
        return new AnalysisResult(total, aScore, fScore, rScore, cScore, category, identified, missed, lessRelevantList, 
                                  userAssessment.getDescription(), bestAssessment.getDescription(), reasoning);
    }

    private void loadScenarios() {
        Scenario s1 = new Scenario("01", "The Overload Loop", "Academic Wellbeing", "Moderate",
            "A fictional college student is handling multiple deadlines, studying very late, taking almost no breaks and gradually reducing recreational activities.",
            Arrays.asList(
                new Factor("Multiple overlapping academic deadlines.", Arrays.asList("academic_workload"), false),
                new Factor("Studying very late several nights per week.", Arrays.asList("sleep_routine"), false),
                new Factor("Frequently skipping breaks.", Arrays.asList("recovery_time"), false),
                new Factor("Re-reading notes repeatedly instead of resting.", Arrays.asList("study_habits"), false),
                new Factor("Cancelling recreational activities to study.", Arrays.asList("general_wellbeing"), false),
                new Factor("Feeling strong pressure to complete everything perfectly.", Arrays.asList("academic_pressure"), false),
                new Factor("Prefers studying in the morning.", Arrays.asList("time_preference"), true)
            ),
            Arrays.asList(
                new Assessment("A1", "Academic workload and recovery imbalance", Arrays.asList("academic_workload", "sleep_routine", "recovery_time", "study_habits", "general_wellbeing", "academic_pressure")),
                new Assessment("A2", "Sleep and routine strain", Arrays.asList("sleep_routine", "recovery_time")),
                new Assessment("A3", "General temporary pressure", Arrays.asList("academic_workload")),
                new Assessment("A4", "Social pressure", Arrays.asList("social_pressure"))
            ),
            "A1", // Best Assessment ID
            "The scenario highlights an imbalance between workload and recovery. Prioritizing rest is critical to maintaining long-term academic resilience."
        );

        Scenario s2 = new Scenario("02", "Always Connected", "Digital Wellbeing", "Easy",
            "A fictional student frequently checks notifications, switches between many apps while studying, remains online late at night and finds it difficult to maintain concentration.",
            Arrays.asList(
                new Factor("Frequent notifications.", Arrays.asList("digital_habits"), false),
                new Factor("Social media use late at night.", Arrays.asList("sleep_routine", "screen_use"), false),
                new Factor("Switching between several communication apps while studying.", Arrays.asList("digital_habits", "study_habits"), false),
                new Factor("Extended screen use before sleep.", Arrays.asList("screen_use"), false),
                new Factor("Frequent attention switching.", Arrays.asList("study_habits"), false),
                new Factor("Difficulty maintaining uninterrupted study periods.", Arrays.asList("academic_pressure"), false),
                new Factor("Uses a dark browser theme.", Arrays.asList("ui_preference"), true)
            ),
            Arrays.asList(
                new Assessment("A1", "Digital overload and disrupted study routine", Arrays.asList("digital_habits", "sleep_routine", "screen_use", "study_habits")),
                new Assessment("A2", "Attention and routine disruption", Arrays.asList("study_habits", "sleep_routine")),
                new Assessment("A3", "General study-environment difficulty", Arrays.asList("study_habits"))
            ),
            "A1",
            "Digital habits strongly influence concentration. Constant context-switching limits deep focus, and late screen use can heavily disrupt normal sleep routines."
        );

        Scenario s3 = new Scenario("03", "Under Pressure", "Academic Pressure", "Hard",
            "A fictional student is preparing for an important academic period while dealing with expectations, fear of failure, comparison with classmates and a highly unbalanced study routine.",
            Arrays.asList(
                new Factor("Strong expectations about academic performance.", Arrays.asList("expectations", "academic_pressure"), false),
                new Factor("Fear of failing.", Arrays.asList("academic_pressure"), false),
                new Factor("Comparing study progress with classmates.", Arrays.asList("social_comparison"), false),
                new Factor("Increasing study hours without sufficient breaks.", Arrays.asList("recovery_time", "study_habits"), false),
                new Factor("Skipping normal routines to study.", Arrays.asList("general_wellbeing"), false),
                new Factor("Difficulty maintaining relaxation time.", Arrays.asList("recovery_time"), false),
                new Factor("Drinks herbal tea while studying.", Arrays.asList("diet"), true)
            ),
            Arrays.asList(
                new Assessment("A1", "Academic stress and performance pressure", Arrays.asList("expectations", "academic_pressure", "social_comparison", "recovery_time", "study_habits", "general_wellbeing")),
                new Assessment("A2", "Unbalanced study routine", Arrays.asList("study_habits", "recovery_time")),
                new Assessment("A3", "Temporary examination-related pressure", Arrays.asList("academic_pressure"))
            ),
            "A1",
            "The scenario reflects severe academic pressure fueled by comparison and expectations. Reducing social comparison can drastically improve study effectiveness and general wellbeing."
        );

        Scenario s4 = new Scenario("04", "The Social Battery", "Social Wellbeing", "Moderate",
            "A fictional student has reduced participation in activities they normally enjoy while dealing with coursework, changing routines and social expectations.",
            Arrays.asList(
                new Factor("Declining social activities.", Arrays.asList("social_pressure", "general_wellbeing"), false),
                new Factor("Reducing recreation to make more study time.", Arrays.asList("recovery_time", "academic_pressure"), false),
                new Factor("Feeling overwhelmed by group plans.", Arrays.asList("social_pressure"), false),
                new Factor("Increased academic demands.", Arrays.asList("academic_pressure"), false),
                new Factor("Reduced time for enjoyable activities.", Arrays.asList("general_wellbeing"), false),
                new Factor("Difficulty maintaining a balanced routine.", Arrays.asList("recovery_time"), false),
                new Factor("Changed their usual commute route.", Arrays.asList("routine_change"), true)
            ),
            Arrays.asList(
                new Assessment("A1", "Social and academic pressure", Arrays.asList("social_pressure", "general_wellbeing", "recovery_time", "academic_pressure")),
                new Assessment("A2", "Routine imbalance", Arrays.asList("recovery_time")),
                new Assessment("A3", "Situational stress", Arrays.asList("academic_pressure"))
            ),
            "A1",
            "The situation contains several wellbeing-related factors regarding social participation. Balancing academic demands with necessary social recreation sustains long-term energy."
        );

        scenarioRepo.add(s1); scenarioMap.put(s1.getId(), s1);
        scenarioRepo.add(s2); scenarioMap.put(s2.getId(), s2);
        scenarioRepo.add(s3); scenarioMap.put(s3.getId(), s3);
        scenarioRepo.add(s4); scenarioMap.put(s4.getId(), s4);
    }
}
package model;
import java.util.List;

public class AnalysisResult {
    public final int totalScore, assessmentScore, factorScore, reasoningScore, confidenceScore;
    public final String category; 
    public final List<String> identifiedFactors;
    public final List<String> missedFactors;
    public final List<String> lessRelevantFactors;
    public final String userAssessmentDesc;
    public final String expectedAssessmentDesc;
    public final String userReasoning;

    public AnalysisResult(int ts, int as, int fs, int rs, int cs, String cat, 
                          List<String> id, List<String> miss, List<String> lrF, 
                          String uAss, String eAss, String reasoning) {
        this.totalScore = ts; this.assessmentScore = as; this.factorScore = fs; 
        this.reasoningScore = rs; this.confidenceScore = cs; this.category = cat;
        this.identifiedFactors = id; this.missedFactors = miss; this.lessRelevantFactors = lrF;
        this.userAssessmentDesc = uAss; this.expectedAssessmentDesc = eAss; this.userReasoning = reasoning;
    }
}
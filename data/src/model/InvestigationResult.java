package model;

public class InvestigationResult {
    public final int score;
    public final String category; // "SUPPORTED", etc.
    public final String deductionFeedback;
    public final String reasoningFeedback;
    public final int redHerrings;

    public InvestigationResult(int score, String category, String deductionFeedback, String reasoningFeedback, int redHerrings) {
        this.score = score;
        this.category = category;
        this.deductionFeedback = deductionFeedback;
        this.reasoningFeedback = reasoningFeedback;
        this.redHerrings = redHerrings;
    }
}
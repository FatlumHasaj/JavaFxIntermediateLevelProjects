package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class MainView {

    @FXML
    private Label scoreLabel;

    @FXML
    private Label autoScoreLabel;

    @FXML
    private Button addScore;

    @FXML
    private Button addIdle;

    private long score = 0;        // Manual score
    private long autoScore = 1;    // Auto increment score

    @FXML
    private void initialize() {
        scoreLabel.setText("Score: " + formatNumber(score));
        autoScoreLabel.setText("Increment: " + formatNumber(autoScore));

        addScore.setOnAction(e -> handleAddScore());

        Timeline autoIncrementTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> handleAutoIncrement()));
        autoIncrementTimeline.setCycleCount(Timeline.INDEFINITE);
        autoIncrementTimeline.play();

        addIdle.setOnAction(e -> handleAddIdle());
    }

    private void handleAddScore() {
        score+= 1;
        scoreLabel.setText("Score: " + formatNumber(score));
    }

    private void handleAutoIncrement() {
        score += autoScore;
        scoreLabel.setText("Score: " + formatNumber(score));
    }

    private void handleAddIdle() {
        autoScore += 1_000; 
        autoScoreLabel.setText("Increment: " + formatNumber(autoScore));
    }

    /**
     * Formats numbers using short notation for idle games.
     * Examples:
     * - 1,000 -> 1.0K
     * - 1,000,000 -> 1.0M
     * - 1,000,000,000 -> 1.0B
     * Supports up to Decillions and beyond.
     */
    private String formatNumber(long number) {
        final String[] suffixes = {
            "", "K", "M", "B", "T", "Q", "Qn", "Sx", "Sp", "Oc", "N", "Dc"
        }; // Thousand, Million, Billion, Trillion, Quadrillion, Quintillion, etc.

        int index = 0;
        double value = number;

        // Divide by 1,000 iteratively to find the correct suffix
        while (value >= 1000 && index < suffixes.length - 1) {
            value /= 1000;
            index++;
        }

        // If number exceeds Decillion, use scientific notation
        if (index == suffixes.length - 1 && value >= 1000) {
            return String.format("%.2e", (double) number);
        }

        // Format with the appropriate suffix
        return String.format("%.1f%s", value, suffixes[index]);
    }
}

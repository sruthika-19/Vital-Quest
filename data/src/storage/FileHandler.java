package storage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;

public class FileHandler {
    private static final String FILE_PATH = "data/wellness_leaderboard.csv";

    public void saveScoreAsync(String playerName, String scenarioName, int score) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                File dir = new File("data");
                if (!dir.exists()) dir.mkdirs();
                
                String safeName = playerName.replace(",", " ").trim();
                String safeCase = scenarioName.replace(",", " ").trim();
                
                // Using PrintWriter and flush guarantees immediate disk writing
                try (FileWriter fw = new FileWriter(FILE_PATH, true);
                     BufferedWriter bw = new BufferedWriter(fw);
                     PrintWriter out = new PrintWriter(bw)) {
                    out.println(safeName + "," + safeCase + "," + score);
                    out.flush(); 
                } catch (IOException e) { 
                    System.err.println("File write error: " + e.getMessage()); 
                }
                return null;
            }
        };
        worker.execute();
    }

    public List<String[]> loadLeaderboard() {
        List<String[]> records = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return records;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length == 3) records.add(data);
            }
        } catch (IOException e) { System.err.println("Error reading records: " + e.getMessage()); }
        
        // Sort descending by score
        records.sort((a, b) -> {
            try {
                return Integer.compare(Integer.parseInt(b[2]), Integer.parseInt(a[2]));
            } catch (NumberFormatException e) { return 0; }
        });
        
        return records;
    }
}
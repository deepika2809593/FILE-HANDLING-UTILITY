import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProductRecommender {

    public static void main(String[] args) throws IOException, TasteException {

        // 1. Data Model: Load user preferences from a file.
        // Format: UserID,ItemID,PreferenceValue (e.g., rating)
        String dataFile = "data.csv"; // Replace with your data file path
        DataModel model = new FileDataModel(new File(dataFile));

        // 2. User Neighborhood: Define how to find similar users.
        // NearestNUserNeighborhood: Finds the N most similar users.
        int neighborhoodSize = 3; // Number of neighbors to consider
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(neighborhoodSize, model);

        // 3. Recommender: Create a user-based recommender.
        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood);

        // 4. Generate Recommendations: Get recommendations for a specific user.
        int userId = 1; // User for whom you want recommendations
        int numRecommendations = 3; // Number of recommendations to generate

        List<RecommendedItem> recommendations = recommender.recommend(userId, numRecommendations);

        // 5. Display Recommendations: Print the recommended items.
        System.out.println("Recommendations for user " + userId + ":");
        if (recommendations.isEmpty()) {
            System.out.println("No recommendations available.");
        } else {
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Item " + recommendation.getItemID() + " (Preference: " + recommendation.getValue() + ")");
            }
        }
    }
}
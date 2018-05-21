import java.util.Random;

public class RandomGoalsGenerator {

    private static Random random = new Random();

    public static int randomGoals(int bound) {
        return random.nextInt(bound);
    }

}

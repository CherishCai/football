import java.util.Random;

public class RandomCardGenerator {
    private static Random random = new Random();

    public static int randomRedCards() {
        if (random.nextInt(1000) > 800) {
            return 1;
        }
        return 0;
    }

    public static int randomYellowCards() {
        if (random.nextInt(1000) > 950) {
            return 1;
        }
        return 0;
    }
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        // Read file 'teams.txt'
        List<Team> teams = null;
        try {
            teams = getTeamsFromFile();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Read file 'teams.txt' error");
            System.exit(1);
        }

        Game game = new Game(false, new ArrayList<>(teams));

        Scanner scanner = new Scanner(System.in);

        // Enter players
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            String teamName = team.getName();
            team.setPlayers(new ArrayList<>());
            for (int j = 1; j <= 2; j++) {
                System.out.println("Please enter player" + j + " name for team[" + teamName + "] :");
                String playerName = scanner.next();
                boolean valid = valid(playerName, team.getPlayers());
                if (!valid) {
                    System.out.println("Please enter player" + j + " name for team[" + teamName + "] :");
                    playerName = scanner.next();
                    valid = valid(playerName, team.getPlayers());
                    if (!valid) {
                        playerName = "Player-" + j + "-" + teamName;
                    }
                }
                team.getPlayers().add(new Player(playerName, 0));
            }
        }

        boolean x = false;
        do {
            Menu.printMenu();
            String select = scanner.next();
            switch (select) {
                case Menu.A:
                    //1.	Play Preliminary Stage
                    if (game.isFinal()) {
                        System.err.println("You must play final stage, Please enter B!");
                        break;
                    }
                    game.playGame();
                    game.setFinal(true);
                    System.out.println("********************************************************");
                    break;
                case Menu.B:
                    if (!game.isFinal()) {
                        System.err.println("You must play preliminary stage, Please enter A!");
                        break;
                    }
                    if (game.isOver()) {
                        System.err.println("Game is over!");
                        break;
                    }
                    //2.	Play Final
                    game.playGame();
                    game.setOver(true);
                    System.out.println("********************************************************");
                    break;
                case Menu.C:
                    //3.	Display Teams
                    // For example: Played Won Lost Drawn Goals Points FairPlayScore
                    // Australia 6 4 2 0 14 12 3 China 6 3 2 1 10 10 0 Ghana 6 3 2 1 9 10 0 Spain 624066 4
                    displayTeams(game);
                    System.out.println("********************************************************");
                    break;
                case Menu.D:
                    //4.	Display Players
                    // the players are listed with their number of goals scored
                    // For example:  Cahill (Australia) - 8 Rogic (Australia) - 6 Gao (China) - 7
                    displayPlayers(game);
                    System.out.println("********************************************************");
                    break;
                case Menu.E:
                    //5.	Display Cup Result
                    // For example:
                    // Football World Cup Winner: Australia
                    // Golden Boot Award: Ronaldo from Spain
                    // Fair Play Award: Ghana and China
                    if (!game.isOver()) {
                        System.err.println("You must play final stage, Please enter B!");
                        break;
                    }
                    displayCupResult(game);
                    System.out.println("********************************************************");
                    break;
                case Menu.X:
                    x = true;
                    // Write file statistics.txt
                    writeFile(game.cupResult());
                    break;
                default:

                    break;
            }
        } while (!x);


    }

    private static boolean valid(String playerName, ArrayList<Player> players) {
        Set<String> set = new HashSet<>(players.size());
        for (Player player : players) {
            set.add(player.getName());
        }
        if (set.contains(playerName)) {
            return false;
        }

        // at most one hyphen, '-'
        if (playerName.startsWith("-") || playerName.endsWith("-")) {
            return false;
        }
        Pattern pattern = Pattern.compile("[a-zA-Z\\-]{2,}");
        if (playerName.contains("-")) {
            pattern = Pattern.compile("[a-zA-Z\\-]{3,}");
        }

        Matcher matcher = pattern.matcher(playerName);
        return matcher.matches();
    }

    // For example:
    // Football World Cup Winner: Australia
    // Golden Boot Award: Ronaldo from Spain
    // Fair Play Award: Ghana and China
    private static void displayCupResult(Game game) {
        System.out.println(game.cupResult());
    }

    // the players are listed with their number of goals scored
    // For example:  Cahill (Australia) - 8 Rogic (Australia) - 6 Gao (China) - 7
    private static void displayPlayers(Game game) {
        ArrayList<Team> teams = game.getTeams();
        for (Team team : teams) {
            ArrayList<Player> players = team.getPlayers();
            for (Player player : players) {
                System.out.println(player.getName() + "(" + team.getName() + ") - " + player.getGoals());
            }
        }
    }

    // For example: Played Won Lost Drawn Goals Points Fair Play Score
    // Australia 6 4 2 0 14 12 3 China 6 3 2 1 10 10 0 Ghana 6 3 2 1 9 10 0 Spain 624066 4
    private static void displayTeams(Game game) {
        ArrayList<Team> teams = game.getTeams();
        for (Team team : teams) {
            System.out.println(team.toString());
        }
    }

    private static void writeFile(String content) {
        FileWriter writer = null;
        try {
            File file = new File("statistics.txt");
            file.createNewFile();
            writer = new FileWriter(file);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static ArrayList<Team> getTeamsFromFile() throws Exception {
        try (
                BufferedReader reader = new BufferedReader(new FileReader("teams.txt"))
        ) {
            ArrayList<Team> list = new ArrayList<>();
            String input;
            while (null != (input = reader.readLine())) {
                String[] split = input.split(",");
                Team team = new Team(split[0], Integer.valueOf(split[1]), new ArrayList<>());
                list.add(team);
            }
            return list;
        }
    }
}

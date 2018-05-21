import java.util.*;

public class Game {

    private boolean isFinal;
    private boolean isOver;

    private ArrayList<Team> teams;

    public Game() {
    }

    public Game(boolean isFinal, ArrayList<Team> teams) {
        this.isFinal = isFinal;
        this.teams = teams;
    }

    public void playGame() {
        if (isFinal) {
            play(2);
            teams.sort(Comparator.comparingInt(Team::getPoints).thenComparingInt(Team::getGoals).reversed());
        } else {
            play(teams.size());
            teams.sort(Comparator.comparingInt(Team::getPoints).thenComparingInt(Team::getGoals).reversed());
            displayPreliminaryResult();
        }
    }

    private void play(int size) {
        for (int i = 0; i < size; i++) {
            Team teamI = teams.get(i);
            for (int j = i + 1; j < size; j++) {
                Team teamJ = teams.get(j);
                boolean RankingI = teamI.getRanking() < teamJ.getRanking();
                int diffRanking = Math.abs(teamI.getRanking() - teamJ.getRanking());

                int goalsI;
                int goalsJ;
                if (RankingI) {
                    goalsI = RandomGoalsGenerator.randomGoals(5
                            + RandomGoalsGenerator.randomGoals(2));

                    goalsJ = RandomGoalsGenerator.randomGoals(5 - diffRanking
                            + RandomGoalsGenerator.randomGoals(2));
                } else {
                    goalsI = RandomGoalsGenerator.randomGoals(5 - diffRanking
                            + RandomGoalsGenerator.randomGoals(2));

                    goalsJ = RandomGoalsGenerator.randomGoals(5
                            + RandomGoalsGenerator.randomGoals(2));
                }

                int redI = RandomCardGenerator.randomRedCards();
                int yellowI = RandomCardGenerator.randomYellowCards();

                int redJ = RandomCardGenerator.randomRedCards();
                int yellowJ = RandomCardGenerator.randomYellowCards();

                teamI.played += 1;
                teamJ.played += 1;
                teamI.fairPlayScore += redI * 2 + yellowI;
                teamJ.fairPlayScore += redJ * 2 + yellowJ;

                boolean iWin = goalsI > goalsJ;
                boolean JWin = goalsJ > goalsI;

                if (iWin) {
                    teamI.goals += goalsI;
                    teamI.points += 3;
                    teamI.won += 1;

                    teamJ.goals += goalsJ;
                    teamJ.points += 0;
                    teamJ.lost += 1;
                } else if (JWin) {
                    teamI.goals += goalsI;
                    teamI.points += 0;
                    teamI.lost += 1;

                    teamJ.goals += goalsJ;
                    teamJ.points += 3;
                    teamJ.won += 1;
                } else {
                    teamI.goals += goalsI;
                    teamI.points += 1;
                    teamI.drawn += 1;

                    teamJ.goals += goalsJ;
                    teamJ.points += 1;
                    teamJ.drawn += 1;

                    if (isFinal) {
                        //
                        playPenaltyShootOut();
                    }
                }


                // goals to players
                randomGoals2Player(teamI, goalsI);
                randomGoals2Player(teamJ, goalsJ);

                displayGameResult(teamI.getName(), teamJ.getName(), goalsI, goalsJ, redI, redJ, yellowI, yellowJ);

                System.out.println();
            }
        }
    }

    private void randomGoals2Player(Team teamJ, int goalsJ) {
        if (goalsJ < 1) {
            return;
        }
        int i1 = RandomGoalsGenerator.randomGoals(goalsJ);
        ArrayList<Player> players = teamJ.getPlayers();
        Player player1 = players.get(0);
        player1.setGoals(player1.getGoals() + i1);
        Player player2 = players.get(1);
        player2.setGoals(player2.getGoals() + goalsJ - i1);
    }

    private Random random = new Random();

    public void playPenaltyShootOut() {
        System.out.println("need to playPenaltyShootOut");
        Team team1 = teams.get(0);
        Team team2 = teams.get(1);
        int score1 = 0;
        int score2 = 0;
        for (int i = 0; i < 5; i++) {
            boolean player11Shoot = random.nextInt(100) > 50;
            if (player11Shoot) {
                score1++;
            }
            boolean player21Shoot = random.nextInt(100) > 50;
            if (player21Shoot) {
                score2++;
            }
        }

        while (score1 == score2) {
            boolean player11Shoot = random.nextInt(100) > 50;
            if (player11Shoot) {
                score1++;
            }
            boolean player21Shoot = random.nextInt(100) > 50;
            if (player21Shoot) {
                score2++;
            }
        }

        if (score1 > score2) {
            teams.set(0, team1);
            teams.set(1, team2);
        } else {
            teams.set(0, team2);
            teams.set(1, team1);
        }

    }

    // for example:
//    Game result: Spain 4 vs. Australia 0
//    Cards awarded: Australia - 1 red card.
    public void displayGameResult(String teamI, String teamJ, int goalsI, int goalsJ, int redI, int redJ, int yellowI, int yellowJ) {
        System.out.println("Game result: " + teamI + " " + goalsI + " vs. " + teamJ + " " + goalsJ);
        if (redI > 0) {
            System.out.println("Cards awarded: " + teamI + " - " + redI + " red card");
        }
        if (redJ > 0) {
            System.out.println("Cards awarded: " + teamI + " - " + redJ + " red card");
        }
        if (yellowI > 0) {
            System.out.println("Cards awarded: " + teamI + " - " + yellowI + " yellow card");
        }
        if (yellowJ > 0) {
            System.out.println("Cards awarded: " + teamI + " - " + yellowJ + " yellow card");
        }
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    @Override
    public String toString() {
        return "Game{" +
                "teams=" + teams +
                '}';
    }

    // For example:
    // Football World Cup Winner: Australia
    // Golden Boot Award: Ronaldo from Spain
    // Fair Play Award: Ghana and China
    public String cupResult() {
        Map<Integer, List<Team>> fairPlayScoreMap = new HashMap<>();
        Map<Integer, List<Player>> maxFootMap = new HashMap<>();
        int minFairPlayScore = Integer.MAX_VALUE;
        int maxFoot = Integer.MIN_VALUE;

        for (Team team : teams) {
            ArrayList<Player> players = team.getPlayers();
            for (Player player : players) {
                int goals = player.getGoals();
                if (maxFoot < goals) {
                    maxFoot = goals;
                }

                if (maxFootMap.containsKey(goals)) {
                    maxFootMap.get(goals).add(player);
                } else {
                    ArrayList<Player> list = new ArrayList<>();
                    list.add(player);
                    maxFootMap.put(goals, list);
                }
            }

            int fairPlayScore = team.getFairPlayScore();
            if (minFairPlayScore > fairPlayScore) {
                minFairPlayScore = fairPlayScore;
            }

            if (fairPlayScoreMap.containsKey(fairPlayScore)) {
                fairPlayScoreMap.get(fairPlayScore).add(team);
            } else {
                ArrayList<Team> list = new ArrayList<>();
                list.add(team);
                fairPlayScoreMap.put(fairPlayScore, list);
            }
        }

        List<Team> teams = fairPlayScoreMap.get(minFairPlayScore);
        StringBuilder teamStr = new StringBuilder(teams.get(0).getName());
        for (int i = 1; i < teams.size(); i++) {
            teamStr.append(",").append(teams.get(i).getName());
        }

        List<Player> players = maxFootMap.get(maxFoot);
        StringBuilder playerStr = new StringBuilder(players.get(0).getName());
        for (int i = 1; i < players.size(); i++) {
            playerStr.append(",").append(players.get(i).getName());
        }
        return "Football World Cup Winner: " + this.teams.get(0).getName() + "\n" +
                "Golden Boot Award: " + playerStr.toString() + "\n" +
                "Fair Play Award: " + teamStr.toString();
    }


    public void displayPreliminaryResult() {
        System.out.println("PreliminaryResult:");
        for (Team team : teams) {
            System.out.println(team.getName() + " points:" + team.getPoints());
        }
    }
}

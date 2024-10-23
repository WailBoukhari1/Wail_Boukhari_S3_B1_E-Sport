package com.esports.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.esports.controller.GameController;
import com.esports.controller.PlayerController;
import com.esports.controller.TeamController;
import com.esports.controller.TournamentController;
import com.esports.model.Game;
import com.esports.model.Player;
import com.esports.model.Team;
import com.esports.model.Tournament;
import com.esports.model.TournamentStatus;

public class ConsoleInterface {

    private Scanner scanner;
    private PlayerController playerController;
    private TeamController teamController;
    private GameController gameController;
    private TournamentController tournamentController;

    public ConsoleInterface() {
        scanner = new Scanner(System.in);
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        playerController = context.getBean(PlayerController.class);
        teamController = context.getBean(TeamController.class);
        gameController = context.getBean(GameController.class);
        tournamentController = context.getBean(TournamentController.class);
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    handlePlayerMenu();
                    break;
                case 2:
                    handleTeamMenu();
                    break;
                case 3:
                    handleGameMenu();
                    break;
                case 4:
                    handleTournamentMenu();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Thank you for using the E-Sports Tournament Management System!");
    }

    private void printMainMenu() {
        System.out.println("\n--- E-Sports Tournament Management System ---");
        System.out.println("1. Player Management");
        System.out.println("2. Team Management");
        System.out.println("3. Game Management");
        System.out.println("4. Tournament Management");
        System.out.println("5. Exit");
    }

    private void handlePlayerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Player Management ---");
            System.out.println("1. Create Player");
            System.out.println("2. View All Players");
            System.out.println("3. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    createPlayer();
                    break;
                case 2:
                    viewAllPlayers();
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createPlayer() {
        System.out.println("\nCreating a new player:");
        String username = getStringInput("Enter username (3-50 characters): ");
        int age = getIntInput("Enter age (13-100): ");
        String teamName = getStringInput("Enter team name: ");

        Player player = new Player();
        player.setUsername(username);
        player.setAge(age);

        try {
            playerController.createPlayer(player);
            teamController.addPlayerToTeam(teamName, username);
            System.out.println("Player created successfully and added to the team.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating player: " + e.getMessage());
        }
    }

    private void viewAllPlayers() {
        List<Player> players = playerController.getAllPlayers();
        if (players.isEmpty()) {
            System.out.println("No players found.");
        } else {
            System.out.println("\nAll Players:");
            for (Player player : players) {
                System.out.println("Username: " + player.getUsername() + ", Age: " + player.getAge() + ", Team: " + (player.getTeam() != null ? player.getTeam().getName() : "N/A"));
            }
        }
    }

    private void handleTeamMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Team Management ---");
            System.out.println("1. Create Team");
            System.out.println("2. View All Teams");
            System.out.println("3. Add Player to Team");
            System.out.println("4. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    createTeam();
                    break;
                case 2:
                    viewAllTeams();
                    break;
                case 3:
                    addPlayerToTeam();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createTeam() {
        System.out.println("\nCreating a new team:");
        String name = getStringInput("Enter team name (3-50 characters): ");
        int ranking = getIntInput("Enter team ranking (1-1000): ");

        Team team = new Team();
        team.setName(name);
        team.setRanking(ranking);

        try {
            teamController.createTeam(team);
            System.out.println("Team created successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating team: " + e.getMessage());
        }
    }

    private void viewAllTeams() {
        List<Team> teams = teamController.getAllTeams();
        if (teams.isEmpty()) {
            System.out.println("No teams found.");
        } else {
            System.out.println("\nAll Teams:");
            for (Team team : teams) {
                System.out.println("Name: " + team.getName() + ", Ranking: " + team.getRanking() + ", Players: " + team.getPlayers().size());
            }
        }
    }

    private void addPlayerToTeam() {
        String teamName = getStringInput("Enter team name: ");
        String playerUsername = getStringInput("Enter player username: ");

        try {
            teamController.addPlayerToTeam(teamName, playerUsername);
            System.out.println("Player added to the team successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding player to team: " + e.getMessage());
        }
    }

    private void handleGameMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Game Management ---");
            System.out.println("1. Create Game");
            System.out.println("2. View All Games");
            System.out.println("3. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    createGame();
                    break;
                case 2:
                    viewAllGames();
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createGame() {
        System.out.println("\nCreating a new game:");
        String name = getStringInput("Enter game name (3-50 characters): ");
        int difficulty = getIntInput("Enter game difficulty (1-10): ");
        int averageMatchDuration = getIntInput("Enter average match duration in minutes (1-180): ");

        Game game = new Game();
        game.setName(name);
        game.setDifficulty(difficulty);
        game.setAverageMatchDuration(averageMatchDuration);

        try {
            gameController.createGame(game);
            System.out.println("Game created successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating game: " + e.getMessage());
        }
    }

    private void viewAllGames() {
        List<Game> games = gameController.getAllGames();
        if (games.isEmpty()) {
            System.out.println("No games found.");
        } else {
            System.out.println("\nAll Games:");
            for (Game game : games) {
                System.out.println("Name: " + game.getName() + ", Difficulty: " + game.getDifficulty() + ", Average Match Duration: " + game.getAverageMatchDuration() + " minutes");
            }
        }
    }

    private void handleTournamentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Tournament Management ---");
            System.out.println("1. Create Tournament");
            System.out.println("2. View All Tournaments");
            System.out.println("3. Add Team to Tournament");
            System.out.println("4. Add Game to Tournament");
            System.out.println("5. Update Tournament");
            System.out.println("6. Delete Tournament");
            System.out.println("7. Calculate Estimated Duration");
            System.out.println("8. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    createTournament();
                    break;
                case 2:
                    viewAllTournaments();
                    break;
                case 3:
                    addTeamToTournament();
                    break;
                case 4:
                    addGameToTournament();
                    break;
                case 5:
                    updateTournament();
                    break;
                case 6:
                    deleteTournament();
                    break;
                case 7:
                    calculateEstimatedDuration();
                    break;
                case 8:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createTournament() {
        System.out.println("\nCreating a new tournament:");
        String title = getStringInput("Enter tournament title (1-100 characters): ");
        String startDateStr = getStringInput("Enter start date (yyyy-MM-dd): ");
        String endDateStr = getStringInput("Enter end date (yyyy-MM-dd): ");
        int spectatorCount = getIntInput("Enter spectator count: ");
        int matchBreakTime = getIntInput("Enter match break time in minutes: ");
        int ceremonyTime = getIntInput("Enter ceremony time in minutes: ");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate, endDate;
        try {
            startDate = dateFormat.parse(startDateStr);
            endDate = dateFormat.parse(endDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        Tournament tournament = new Tournament();
        tournament.setTitle(title);
        tournament.setStartDate(startDate);
        tournament.setEndDate(endDate);
        tournament.setSpectatorCount(spectatorCount);
        tournament.setMatchBreakTime(matchBreakTime);
        tournament.setCeremonyTime(ceremonyTime);
        tournament.setStatus(TournamentStatus.PLANNED);

        try {
            tournamentController.createTournament(tournament);
            System.out.println("Tournament created successfully.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error creating tournament: " + e.getMessage());
        }
    }

    private void viewAllTournaments() {
        List<Tournament> tournaments = tournamentController.getAllTournaments();
        if (tournaments.isEmpty()) {
            System.out.println("No tournaments found.");
        } else {
            System.out.println("\nAll Tournaments:");
            for (Tournament tournament : tournaments) {
                Set<Team> teams = tournament.getTeams();
                int estimatedDuration = tournamentController.calculateEstimatedDuration(tournament.getTitle());
                System.out.println("Title: " + tournament.getTitle() + 
                                   ", Game: " + (tournament.getGame() != null ? tournament.getGame().getName() : "N/A") +
                                   ", Status: " + tournament.getStatus() + 
                                   ", Teams: " + teams.size() + 
                                   " (" + String.join(", ", teams.stream().map(Team::getName).toArray(String[]::new)) + ")" +
                                   ", Estimated Duration: " + estimatedDuration + " minutes");
            }
        }
    }

    private void addTeamToTournament() {
        String tournamentTitle = getStringInput("Enter tournament title: ");
        String teamName = getStringInput("Enter team name: ");

        try {
            tournamentController.addTeamToTournament(tournamentTitle, teamName);
            System.out.println("Team added to the tournament successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding team to tournament: " + e.getMessage());
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public void updateTournamentStatuses() {
        tournamentController.updateTournamentStatuses();
    }

    private void addGameToTournament() {
        String tournamentTitle = getStringInput("Enter tournament title: ");
        String gameName = getStringInput("Enter game name: ");

        try {
            tournamentController.addGameToTournament(tournamentTitle, gameName);
            System.out.println("Game added to the tournament successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding game to tournament: " + e.getMessage());
        }
    }

    private void updateTournament() {
        String title = getStringInput("Enter tournament title to update: ");
        Tournament tournament = tournamentController.getTournamentByTitle(title);
        if (tournament == null) {
            System.out.println("Tournament not found.");
            return;
        }

        String newTitle = getStringInput("Enter new title (press enter to keep current): ");
        if (!newTitle.isEmpty()) {
            tournament.setTitle(newTitle);
        }

        String startDateStr = getStringInput("Enter new start date (yyyy-MM-dd) (press enter to keep current): ");
        if (!startDateStr.isEmpty()) {
            try {
                Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
                tournament.setStartDate(startDate);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Date not updated.");
            }
        }

        String endDateStr = getStringInput("Enter new end date (yyyy-MM-dd) (press enter to keep current): ");
        if (!endDateStr.isEmpty()) {
            try {
                Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);
                tournament.setEndDate(endDate);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Date not updated.");
            }
        }

        tournamentController.updateTournament(tournament);
        System.out.println("Tournament updated successfully.");
    }

    private void deleteTournament() {
        String title = getStringInput("Enter tournament title to delete: ");
        try {
            tournamentController.deleteTournamentByTitle(title);
            System.out.println("Tournament deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error deleting tournament: " + e.getMessage());
        }
    }

    private void calculateEstimatedDuration() {
        String title = getStringInput("Enter tournament title: ");
        try {
            int duration = tournamentController.calculateEstimatedDuration(title);
            System.out.println("Estimated duration for tournament '" + title + "': " + duration + " minutes");
        } catch (IllegalArgumentException e) {
            System.out.println("Error calculating estimated duration: " + e.getMessage());
        }
    }
}

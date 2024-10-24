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
            System.out.println("3. Edit Player");
            System.out.println("4. Delete Player");
            System.out.println("5. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    createPlayer();
                    break;
                case 2:
                    viewAllPlayers();
                    break;
                case 3:
                    editPlayer();
                    break;
                case 4:
                    deletePlayer();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createPlayer() {
        System.out.println("\nCreating a new player:");
        String username = getStringInput("Enter username: ", 3, 50);
        int age = getIntInput("Enter age: ", 13, 100);
    
        Player player = new Player();
        player.setUsername(username);
        player.setAge(age);
    
        try {
            playerController.createPlayer(player);
            System.out.println("Player created successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating player: " + e.getMessage());
        }
    }

    private void editPlayer() {
        String username = getStringInput("Enter the username of the player to edit: ");
        Player player = playerController.getPlayerByUsername(username);
        if (player == null) {
            System.out.println("Player not found.");
            return;
        }
    
        String newUsername = getStringInput("Enter new username (3-50 characters, press enter to keep current): ");
        if (!newUsername.isEmpty()) {
            player.setUsername(newUsername);
        }
    
        int newAge = getIntInput("Enter new age (13-100, press enter to keep current): ", 13, 100);
        if (newAge != -1) {
            player.setAge(newAge);
        }
    
        try {
            playerController.updatePlayer(player);
            System.out.println("Player updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error updating player: " + e.getMessage());
        }
    }

    private void deletePlayer() {
        String username = getStringInput("Enter the username of the player to delete: ");
        try {
            playerController.deletePlayerByUsername(username);
            System.out.println("Player deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error deleting player: " + e.getMessage());
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
            System.out.println("3. Edit Team");
            System.out.println("4. Delete Team");
            System.out.println("5. Add Player to Team");
            System.out.println("6. Remove Player from Team");
            System.out.println("7. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    createTeam();
                    break;
                case 2:
                    viewAllTeams();
                    break;
                case 3:
                    editTeam();
                    break;
                case 4:
                    deleteTeam();
                    break;
                case 5:
                    addPlayerToTeam();
                    break;
                case 6:
                    removePlayerFromTeam();
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createTeam() {
        System.out.println("\nCreating a new team:");
        String name = getStringInput("Enter team name: ", 3, 50);
        int ranking = getIntInput("Enter team ranking: ", 1, 1000);

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

    private void editTeam() {
        String name = getStringInput("Enter the name of the team to edit: ");
        Team team = teamController.getTeamByName(name);
        if (team == null) {
            System.out.println("Team not found.");
            return;
        }

        String newName = getStringInput("Enter new team name (3-50 characters, press enter to keep current): ");
        if (!newName.isEmpty()) {
            team.setName(newName);
        }

        int newRanking = getIntInput("Enter new team ranking (1-1000, press enter to keep current): ");
        if (newRanking != -1) {
            team.setRanking(newRanking);
        }

        try {
            teamController.updateTeam(team);
            System.out.println("Team updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error updating team: " + e.getMessage());
        }
    }

    private void deleteTeam() {
        String name = getStringInput("Enter the name of the team to delete: ");
        try {
            teamController.deleteTeamByName(name);
            System.out.println("Team deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error deleting team: " + e.getMessage());
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
            System.out.println("3. Edit Game");
            System.out.println("4. Delete Game");
            System.out.println("5. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    createGame();
                    break;
                case 2:
                    viewAllGames();
                    break;
                case 3:
                    editGame();
                    break;
                case 4:
                    deleteGame();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createGame() {
        System.out.println("\nCreating a new game:");
        String name = getStringInput("Enter game name: ", 3, 50);
        int difficulty = getIntInput("Enter game difficulty: ", 1, 10);
        int averageMatchDuration = getIntInput("Enter average match duration in minutes: ", 1, 180);

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

    private void editGame() {
        String name = getStringInput("Enter the name of the game to edit: ");
        Game game = gameController.getGameByName(name);
        if (game == null) {
            System.out.println("Game not found.");
            return;
        }

        String newName = getStringInput("Enter new game name (3-50 characters, press enter to keep current): ");
        if (!newName.isEmpty()) {
            game.setName(newName);
        }

        int newDifficulty = getIntInput("Enter new game difficulty (1-10, press enter to keep current): ");
        if (newDifficulty != -1) {
            game.setDifficulty(newDifficulty);
        }

        int newAverageMatchDuration = getIntInput("Enter new average match duration in minutes (1-180, press enter to keep current): ");
        if (newAverageMatchDuration != -1) {
            game.setAverageMatchDuration(newAverageMatchDuration);
        }

        try {
            gameController.updateGame(game);
            System.out.println("Game updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error updating game: " + e.getMessage());
        }
    }

    private void deleteGame() {
        String name = getStringInput("Enter the name of the game to delete: ");
        try {
            gameController.deleteGameByName(name);
            System.out.println("Game deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error deleting game: " + e.getMessage());
        }
    }

    private void removePlayerFromTeam() {
        String teamName = getStringInput("Enter team name: ");
        String playerUsername = getStringInput("Enter player username: ");

        try {
            teamController.removePlayerFromTeam(teamName, playerUsername);
            System.out.println("Player removed from the team successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error removing player from team: " + e.getMessage());
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
            System.out.println("3. Edit Tournament");
            System.out.println("4. Delete Tournament");
            System.out.println("5. Add Team to Tournament");
            System.out.println("6. Remove Team from Tournament");
            System.out.println("7. Add Game to Tournament");
            System.out.println("8. Change Tournament Status");
            System.out.println("9. Calculate Estimated Duration");
            System.out.println("10. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    createTournament();
                    break;
                case 2:
                    viewAllTournaments();
                    break;
                case 3:
                    editTournament();
                    break;
                case 4:
                    deleteTournament();
                    break;
                case 5:
                    addTeamToTournament();
                    break;
                case 6:
                    removeTeamFromTournament();
                    break;
                case 7:
                    addGameToTournament();
                    break;
                case 8:
                    changeTournamentStatus();
                    break;
                case 9:
                    calculateEstimatedDuration();
                    break;
                case 10:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createTournament() {
        System.out.println("\nCreating a new tournament:");
        String title = getStringInput("Enter tournament title: ", 1, 100);
        String startDateStr = getStringInput("Enter start date (yyyy-MM-dd): ");
        String endDateStr = getStringInput("Enter end date (yyyy-MM-dd): ");
        int spectatorCount = getIntInput("Enter spectator count: ", 0, Integer.MAX_VALUE);
        int matchBreakTime = getIntInput("Enter match break time in minutes: ", 0, Integer.MAX_VALUE);
        int ceremonyTime = getIntInput("Enter ceremony time in minutes: ", 0, Integer.MAX_VALUE);

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
                System.out.println("Title: " + tournament.getTitle()
                        + ", Game: " + (tournament.getGame() != null ? tournament.getGame().getName() : "N/A")
                        + ", Status: " + tournament.getStatus()
                        + ", Teams: " + teams.size()
                        + " (" + String.join(", ", teams.stream().map(Team::getName).toArray(String[]::new)) + ")"
                        + ", Estimated Duration: " + estimatedDuration + " minutes");
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

    private void editTournament() {
        String title = getStringInput("Enter the title of the tournament to edit: ");
        Tournament tournament = tournamentController.getTournamentByTitle(title);
        if (tournament == null) {
            System.out.println("Tournament not found.");
            return;
        }

        String newTitle = getStringInput("Enter new tournament title (1-100 characters, press enter to keep current): ");
        if (!newTitle.isEmpty()) {
            tournament.setTitle(newTitle);
        }

        String startDateStr = getStringInput("Enter new start date (yyyy-MM-dd, press enter to keep current): ");
        if (!startDateStr.isEmpty()) {
            try {
                Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
                tournament.setStartDate(startDate);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Date not updated.");
            }
        }

        String endDateStr = getStringInput("Enter new end date (yyyy-MM-dd, press enter to keep current): ");
        if (!endDateStr.isEmpty()) {
            try {
                Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);
                tournament.setEndDate(endDate);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Date not updated.");
            }
        }

        int newSpectatorCount = getIntInput("Enter new spectator count (press enter to keep current): ");
        if (newSpectatorCount != -1) {
            tournament.setSpectatorCount(newSpectatorCount);
        }

        int newMatchBreakTime = getIntInput("Enter new match break time in minutes (press enter to keep current): ");
        if (newMatchBreakTime != -1) {
            tournament.setMatchBreakTime(newMatchBreakTime);
        }

        int newCeremonyTime = getIntInput("Enter new ceremony time in minutes (press enter to keep current): ");
        if (newCeremonyTime != -1) {
            tournament.setCeremonyTime(newCeremonyTime);
        }

        try {
            tournamentController.updateTournament(tournament);
            System.out.println("Tournament updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error updating tournament: " + e.getMessage());
        }
    }

    private void removeTeamFromTournament() {
        String tournamentTitle = getStringInput("Enter tournament title: ");
        String teamName = getStringInput("Enter team name: ");

        try {
            tournamentController.removeTeamFromTournament(tournamentTitle, teamName);
            System.out.println("Team removed from the tournament successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error removing team from tournament: " + e.getMessage());
        }
    }

    private void changeTournamentStatus() {
        String tournamentTitle = getStringInput("Enter tournament title: ");
        System.out.println("Available statuses: PLANNED, IN_PROGRESS, COMPLETED, CANCELLED");
        String statusStr = getStringInput("Enter new status: ");
        try {
            TournamentStatus newStatus = TournamentStatus.valueOf(statusStr.toUpperCase());
            tournamentController.changeStatus(tournamentTitle, newStatus);
            System.out.println("Tournament status changed successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error changing tournament status: " + e.getMessage());
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

    private String getStringInput(String prompt, int minLength, int maxLength) {
        while (true) {
            String input = getStringInput(prompt);
            if (input.length() >= minLength && input.length() <= maxLength) {
                return input;
            }
            System.out.println("Input must be between " + minLength + " and " + maxLength + " characters long.");
        }
    }

    private int getIntInput(String prompt, int min, int max) {
        while (true) {
            int input = getIntInput(prompt);
            if (input >= min && input <= max) {
                return input;
            }
            System.out.println("Input must be between " + min + " and " + max + ".");
        }
    }
}

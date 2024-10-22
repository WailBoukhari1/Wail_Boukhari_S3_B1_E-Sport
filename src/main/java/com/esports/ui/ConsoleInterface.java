package com.esports.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.esports.controller.PlayerController;
import com.esports.controller.TeamController;
import com.esports.controller.TournamentController;
import com.esports.model.Player;
import com.esports.model.Team;
import com.esports.model.Tournament;

public class ConsoleInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleInterface.class);
    private PlayerController playerController;
    private TeamController teamController;
    private TournamentController tournamentController;
    private Scanner scanner;

    public ConsoleInterface() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        playerController = (PlayerController) context.getBean("playerController");
        teamController = (TeamController) context.getBean("teamController");
        tournamentController = (TournamentController) context.getBean("tournamentController");
        scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handlePlayerOperations();
                    break;
                case 2:
                    handleTeamOperations();
                    break;
                case 3:
                    handleTournamentOperations();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\nE-Sports Tournament Management System");
        System.out.println("1. Player Operations");
        System.out.println("2. Team Operations");
        System.out.println("3. Tournament Operations");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private void handlePlayerOperations() {
        System.out.println("\nPlayer Operations:");
        System.out.println("1. Create Player");
        System.out.println("2. Update Player");
        System.out.println("3. Delete Player");
        System.out.println("4. View All Players");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                createPlayer();
                break;
            case 2:
                updatePlayer();
                break;
            case 3:
                deletePlayer();
                break;
            case 4:
                viewAllPlayers();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void handleTeamOperations() {
        System.out.println("\nTeam Operations:");
        System.out.println("1. Create Team");
        System.out.println("2. Update Team");
        System.out.println("3. Delete Team");
        System.out.println("4. View All Teams");
        System.out.println("5. Add Player to Team");
        System.out.println("6. Remove Player from Team");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                createTeam();
                break;
            case 2:
                updateTeam();
                break;
            case 3:
                deleteTeam();
                break;
            case 4:
                viewAllTeams();
                break;
            case 5:
                addPlayerToTeam();
                break;
            case 6:
                removePlayerFromTeam();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void handleTournamentOperations() {
        System.out.println("\nTournament Operations:");
        System.out.println("1. Create Tournament");
        System.out.println("2. Update Tournament");
        System.out.println("3. Delete Tournament");
        System.out.println("4. View All Tournaments");
        System.out.println("5. Add Team to Tournament");
        System.out.println("6. Remove Team from Tournament");
        System.out.println("7. Calculate Estimated Duration");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                createTournament();
                break;
            case 2:
                updateTournament();
                break;
            case 3:
                deleteTournament();
                break;
            case 4:
                viewAllTournaments();
                break;
            case 5:
                addTeamToTournament();
                break;
            case 6:
                removeTeamFromTournament();
                break;
            case 7:
                calculateEstimatedDuration();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void createPlayer() {
        LOGGER.info("Creating a new player");
        System.out.println("Enter player username:");
        String username = scanner.nextLine();
        System.out.println("Enter player age:");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Player player = new Player();
        player.setUsername(username);
        player.setAge(age);

        try {
            playerController.createPlayer(player);
            LOGGER.info("Player created successfully: {}", username);
            System.out.println("Player created successfully.");
        } catch (Exception e) {
            LOGGER.error("Error creating player: {}", e.getMessage());
            System.out.println("Error creating player: " + e.getMessage());
        }
    }

    private void updatePlayer() {
        LOGGER.info("Updating a player");
        System.out.println("Enter player username to update:");
        String username = scanner.nextLine();

        Player player = playerController.getAllPlayers().stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (player == null) {
            LOGGER.warn("Player not found: {}", username);
            System.out.println("Player not found.");
            return;
        }

        System.out.println("Enter new username (or press enter to keep current):");
        String newUsername = scanner.nextLine();
        if (!newUsername.isEmpty()) {
            player.setUsername(newUsername);
        }

        System.out.println("Enter new age (or -1 to keep current):");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (age != -1) {
            player.setAge(age);
        }

        try {
            playerController.updatePlayer(player);
            LOGGER.info("Player updated successfully: {}", player.getUsername());
            System.out.println("Player updated successfully.");
        } catch (Exception e) {
            LOGGER.error("Error updating player: {}", e.getMessage());
            System.out.println("Error updating player: " + e.getMessage());
        }
    }

    private void deletePlayer() {
        LOGGER.info("Deleting a player");
        System.out.println("Enter player username to delete:");
        String username = scanner.nextLine();

        Player player = playerController.getAllPlayers().stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (player == null) {
            LOGGER.warn("Player not found: {}", username);
            System.out.println("Player not found.");
            return;
        }

        try {
            playerController.deletePlayer(player.getId());
            LOGGER.info("Player deleted successfully: {}", username);
            System.out.println("Player deleted successfully.");
        } catch (Exception e) {
            LOGGER.error("Error deleting player: {}", e.getMessage());
            System.out.println("Error deleting player: " + e.getMessage());
        }
    }

    private void viewAllPlayers() {
        LOGGER.info("Viewing all players");
        List<Player> players = playerController.getAllPlayers();
        if (players.isEmpty()) {
            System.out.println("No players found.");
        } else {
            System.out.println("Players:");
            for (Player player : players) {
                System.out.println("Username: " + player.getUsername() + ", Age: " + player.getAge());
            }
        }
    }

    private void createTeam() {
        LOGGER.info("Creating a new team");
        System.out.println("Enter team name:");
        String name = scanner.nextLine();

        Team team = new Team();
        team.setName(name);

        try {
            teamController.createTeam(team);
            LOGGER.info("Team created successfully: {}", name);
            System.out.println("Team created successfully.");
        } catch (Exception e) {
            LOGGER.error("Error creating team: {}", e.getMessage());
            System.out.println("Error creating team: " + e.getMessage());
        }
    }

    private void updateTeam() {
        LOGGER.info("Updating a team");
        System.out.println("Enter team name to update:");
        String name = scanner.nextLine();

        Team team = teamController.getAllTeams().stream()
                .filter(t -> t.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (team == null) {
            LOGGER.warn("Team not found: {}", name);
            System.out.println("Team not found.");
            return;
        }

        System.out.println("Enter new team name (or press enter to keep current):");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            team.setName(newName);
        }

        try {
            teamController.updateTeam(team);
            LOGGER.info("Team updated successfully: {}", team.getName());
            System.out.println("Team updated successfully.");
        } catch (Exception e) {
            LOGGER.error("Error updating team: {}", e.getMessage());
            System.out.println("Error updating team: " + e.getMessage());
        }
    }

    private void deleteTeam() {
        LOGGER.info("Deleting a team");
        System.out.println("Enter team name to delete:");
        String name = scanner.nextLine();

        Team team = teamController.getAllTeams().stream()
                .filter(t -> t.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (team == null) {
            LOGGER.warn("Team not found: {}", name);
            System.out.println("Team not found.");
            return;
        }

        try {
            teamController.deleteTeam(team.getId());
            LOGGER.info("Team deleted successfully: {}", name);
            System.out.println("Team deleted successfully.");
        } catch (Exception e) {
            LOGGER.error("Error deleting team: {}", e.getMessage());
            System.out.println("Error deleting team: " + e.getMessage());
        }
    }

    private void viewAllTeams() {
        LOGGER.info("Viewing all teams");
        List<Team> teams = teamController.getAllTeams();
        if (teams.isEmpty()) {
            System.out.println("No teams found.");
        } else {
            System.out.println("Teams:");
            for (Team team : teams) {
                System.out.println("Name: " + team.getName());
            }
        }
    }

    private void addPlayerToTeam() {
        LOGGER.info("Adding player to team");
        System.out.println("Enter team name:");
        String teamName = scanner.nextLine();
        System.out.println("Enter player username:");
        String playerUsername = scanner.nextLine();

        Team team = teamController.getAllTeams().stream()
                .filter(t -> t.getName().equals(teamName))
                .findFirst()
                .orElse(null);

        Player player = playerController.getAllPlayers().stream()
                .filter(p -> p.getUsername().equals(playerUsername))
                .findFirst()
                .orElse(null);

        if (team == null || player == null) {
            LOGGER.warn("Team or player not found");
            System.out.println("Team or player not found.");
            return;
        }

        try {
            teamController.addPlayerToTeam(team.getId(), player.getId());
            LOGGER.info("Player {} added to team {} successfully", playerUsername, teamName);
            System.out.println("Player added to team successfully.");
        } catch (Exception e) {
            LOGGER.error("Error adding player to team: {}", e.getMessage());
            System.out.println("Error adding player to team: " + e.getMessage());
        }
    }

    private void removePlayerFromTeam() {
        LOGGER.info("Removing player from team");
        System.out.println("Enter team name:");
        String teamName = scanner.nextLine();
        System.out.println("Enter player username:");
        String playerUsername = scanner.nextLine();

        Team team = teamController.getAllTeams().stream()
                .filter(t -> t.getName().equals(teamName))
                .findFirst()
                .orElse(null);

        Player player = playerController.getAllPlayers().stream()
                .filter(p -> p.getUsername().equals(playerUsername))
                .findFirst()
                .orElse(null);

        if (team == null || player == null) {
            LOGGER.warn("Team or player not found");
            System.out.println("Team or player not found.");
            return;
        }

        try {
            teamController.removePlayerFromTeam(team.getId(), player.getId());
            LOGGER.info("Player {} removed from team {} successfully", playerUsername, teamName);
            System.out.println("Player removed from team successfully.");
        } catch (Exception e) {
            LOGGER.error("Error removing player from team: {}", e.getMessage());
            System.out.println("Error removing player from team: " + e.getMessage());
        }
    }

    private void createTournament() {
        LOGGER.info("Creating a new tournament");
        System.out.println("Enter tournament title:");
        String title = scanner.nextLine();
        System.out.println("Enter start date (yyyy-MM-dd):");
        String startDateStr = scanner.nextLine();
        System.out.println("Enter end date (yyyy-MM-dd):");
        String endDateStr = scanner.nextLine();

        Tournament tournament = new Tournament();
        tournament.setTitle(title);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            tournament.setStartDate(dateFormat.parse(startDateStr));
            tournament.setEndDate(dateFormat.parse(endDateStr));
        } catch (ParseException e) {
            LOGGER.error("Invalid date format: {}", e.getMessage());
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        try {
            tournamentController.createTournament(tournament);
            LOGGER.info("Tournament created successfully: {}", title);
            System.out.println("Tournament created successfully.");
        } catch (Exception e) {
            LOGGER.error("Error creating tournament: {}", e.getMessage());
            System.out.println("Error creating tournament: " + e.getMessage());
        }
    }

    private void updateTournament() {
        LOGGER.info("Updating a tournament");
        System.out.println("Enter tournament title to update:");
        String title = scanner.nextLine();

        Tournament tournament = tournamentController.getAllTournaments().stream()
                .filter(t -> t.getTitle().equals(title))
                .findFirst()
                .orElse(null);

        if (tournament == null) {
            LOGGER.warn("Tournament not found: {}", title);
            System.out.println("Tournament not found.");
            return;
        }

        System.out.println("Enter new title (or press enter to keep current):");
        String newTitle = scanner.nextLine();
        if (!newTitle.isEmpty()) {
            tournament.setTitle(newTitle);
        }

        System.out.println("Enter new start date (yyyy-MM-dd) (or press enter to keep current):");
        String startDateStr = scanner.nextLine();
        System.out.println("Enter new end date (yyyy-MM-dd) (or press enter to keep current):");
        String endDateStr = scanner.nextLine();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (!startDateStr.isEmpty()) {
                tournament.setStartDate(dateFormat.parse(startDateStr));
            }
            if (!endDateStr.isEmpty()) {
                tournament.setEndDate(dateFormat.parse(endDateStr));
            }
        } catch (ParseException e) {
            LOGGER.error("Invalid date format: {}", e.getMessage());
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        try {
            tournamentController.updateTournament(tournament);
            LOGGER.info("Tournament updated successfully: {}", tournament.getTitle());
            System.out.println("Tournament updated successfully.");
        } catch (Exception e) {
            LOGGER.error("Error updating tournament: {}", e.getMessage());
            System.out.println("Error updating tournament: " + e.getMessage());
        }
    }

    private void deleteTournament() {
        LOGGER.info("Deleting a tournament");
        System.out.println("Enter tournament title to delete:");
        String title = scanner.nextLine();

        Tournament tournament = tournamentController.getAllTournaments().stream()
                .filter(t -> t.getTitle().equals(title))
                .findFirst()
                .orElse(null);

        if (tournament == null) {
            LOGGER.warn("Tournament not found: {}", title);
            System.out.println("Tournament not found.");
            return;
        }

        try {
            tournamentController.deleteTournament(tournament.getId());
            LOGGER.info("Tournament deleted successfully: {}", title);
            System.out.println("Tournament deleted successfully.");
        } catch (Exception e) {
            LOGGER.error("Error deleting tournament: {}", e.getMessage());
            System.out.println("Error deleting tournament: " + e.getMessage());
        }
    }

    private void viewAllTournaments() {
        LOGGER.info("Viewing all tournaments");
        List<Tournament> tournaments = tournamentController.getAllTournaments();
        if (tournaments.isEmpty()) {
            System.out.println("No tournaments found.");
        } else {
            System.out.println("Tournaments:");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Tournament tournament : tournaments) {
                System.out.println("Title: " + tournament.getTitle() + 
                                   ", Start Date: " + dateFormat.format(tournament.getStartDate()) + 
                                   ", End Date: " + dateFormat.format(tournament.getEndDate()) + 
                                   ", Status: " + tournament.getStatus());
            }
        }
    }

    private void addTeamToTournament() {
        LOGGER.info("Adding team to tournament");
        System.out.println("Enter tournament title:");
        String tournamentTitle = scanner.nextLine();
        System.out.println("Enter team name:");
        String teamName = scanner.nextLine();

        Tournament tournament = tournamentController.getAllTournaments().stream()
                .filter(t -> t.getTitle().equals(tournamentTitle))
                .findFirst()
                .orElse(null);

        Team team = teamController.getAllTeams().stream()
                .filter(t -> t.getName().equals(teamName))
                .findFirst()
                .orElse(null);

        if (tournament == null || team == null) {
            LOGGER.warn("Tournament or team not found");
            System.out.println("Tournament or team not found.");
            return;
        }

        try {
            tournamentController.addTeamToTournament(tournament.getId(), team.getId());
            LOGGER.info("Team {} added to tournament {} successfully", teamName, tournamentTitle);
            System.out.println("Team added to tournament successfully.");
        } catch (Exception e) {
            LOGGER.error("Error adding team to tournament: {}", e.getMessage());
            System.out.println("Error adding team to tournament: " + e.getMessage());
        }
    }

    private void removeTeamFromTournament() {
        LOGGER.info("Removing team from tournament");
        System.out.println("Enter tournament title:");
        String tournamentTitle = scanner.nextLine();
        System.out.println("Enter team name:");
        String teamName = scanner.nextLine();

        Tournament tournament = tournamentController.getAllTournaments().stream()
                .filter(t -> t.getTitle().equals(tournamentTitle))
                .findFirst()
                .orElse(null);

        Team team = teamController.getAllTeams().stream()
                .filter(t -> t.getName().equals(teamName))
                .findFirst()
                .orElse(null);

        if (tournament == null || team == null) {
            LOGGER.warn("Tournament or team not found");
            System.out.println("Tournament or team not found.");
            return;
        }

        try {
            tournamentController.removeTeamFromTournament(tournament.getId(), team.getId());
            LOGGER.info("Team {} removed from tournament {} successfully", teamName, tournamentTitle);
            System.out.println("Team removed from tournament successfully.");
        } catch (Exception e) {
            LOGGER.error("Error removing team from tournament: {}", e.getMessage());
            System.out.println("Error removing team from tournament: " + e.getMessage());
        }
    }

    private void calculateEstimatedDuration() {
        LOGGER.info("Calculating estimated duration for a tournament");
        System.out.println("Enter tournament title:");
        String tournamentTitle = scanner.nextLine();

        Tournament tournament = tournamentController.getAllTournaments().stream()
                .filter(t -> t.getTitle().equals(tournamentTitle))
                .findFirst()
                .orElse(null);

        if (tournament == null) {
            LOGGER.warn("Tournament not found: {}", tournamentTitle);
            System.out.println("Tournament not found.");
            return;
        }

        try {
            int duration = tournamentController.calculateEstimatedDuration(tournament.getId());
            LOGGER.info("Estimated duration for tournament {}: {} minutes", tournamentTitle, duration);
            System.out.println("Estimated duration for tournament: " + duration + " minutes");
        } catch (Exception e) {
            LOGGER.error("Error calculating estimated duration: {}", e.getMessage());
            System.out.println("Error calculating estimated duration: " + e.getMessage());
        }
    }
}
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

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";

    // Emojis
    private static final String PLAYER_EMOJI = "👤";
    private static final String TEAM_EMOJI = "👥";
    private static final String GAME_EMOJI = "🎮";
    private static final String TOURNAMENT_EMOJI = "🏆";
    private static final String SUCCESS_EMOJI = "✅";
    private static final String ERROR_EMOJI = "❌";
    private static final String WARNING_EMOJI = "⚠️";
    private static final String MENU_EMOJI = "📋";
    private static final String EXIT_EMOJI = "👋";
    private static final String EDIT_EMOJI = "✏️";
    private static final String DELETE_EMOJI = "🗑️";
    private static final String VIEW_EMOJI = "👀";
    private static final String ADD_EMOJI = "➕";
    private static final String REMOVE_EMOJI = "➖";
    private static final String TIME_EMOJI = "⏱️";
    private static final String STATUS_EMOJI = "🔄";

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
            int choice = getIntInput(CYAN + "Enter your choice: " + RESET, 1, 5);
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
                    System.out.println(RED + ERROR_EMOJI + " Invalid choice. Please try again." + RESET);
            }
        }
        System.out.println(GREEN + EXIT_EMOJI + " Thank you for using the E-Sports Tournament Management System!" + RESET);
    }

    private void printMainMenu() {
        System.out.println("\n" + PURPLE + MENU_EMOJI + " --- E-Sports Tournament Management System --- " + MENU_EMOJI + RESET);
        System.out.println(BLUE + "1. " + PLAYER_EMOJI + " Player Management" + RESET);
        System.out.println(BLUE + "2. " + TEAM_EMOJI + " Team Management" + RESET);
        System.out.println(BLUE + "3. " + GAME_EMOJI + " Game Management" + RESET);
        System.out.println(BLUE + "4. " + TOURNAMENT_EMOJI + " Tournament Management" + RESET);
        System.out.println(BLUE + "5. " + EXIT_EMOJI + " Exit" + RESET);
    }

    private void handlePlayerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + PURPLE + PLAYER_EMOJI + " --- Player Management --- " + PLAYER_EMOJI + RESET);
            System.out.println(BLUE + "1. " + ADD_EMOJI + " Create Player" + RESET);
            System.out.println(BLUE + "2. " + VIEW_EMOJI + " View All Players" + RESET);
            System.out.println(BLUE + "3. " + EDIT_EMOJI + " Edit Player" + RESET);
            System.out.println(BLUE + "4. " + DELETE_EMOJI + " Delete Player" + RESET);
            System.out.println(BLUE + "5. " + EXIT_EMOJI + " Back to Main Menu" + RESET);
            int choice = getIntInput(CYAN + "Enter your choice: " + RESET, 1, 5);
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
                    System.out.println(RED + ERROR_EMOJI + " Invalid choice. Please try again." + RESET);
            }
        }
    }

    private void createPlayer() {
        System.out.println("\n" + YELLOW + ADD_EMOJI + " Creating a new player:" + RESET);
        String username = getStringInput(CYAN + "Enter username: " + RESET, 3, 50);
        int age = getIntInput(CYAN + "Enter age: " + RESET, 13, 100);

        Player player = new Player();
        player.setUsername(username);
        player.setAge(age);

        try {
            playerController.createPlayer(player);
            System.out.println(GREEN + SUCCESS_EMOJI + " Player created successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error creating player: " + e.getMessage() + RESET);
        }
    }

    private void viewAllPlayers() {
        List<Player> players = playerController.getAllPlayers();
        if (players.isEmpty()) {
            System.out.println(YELLOW + WARNING_EMOJI + " No players found." + RESET);
        } else {
            System.out.println("\n" + BLUE + VIEW_EMOJI + " All Players:" + RESET);
            for (Player player : players) {
                System.out.println(CYAN + "Username: " + player.getUsername()
                        + ", Age: " + player.getAge()
                        + ", Team: " + (player.getTeam() != null ? player.getTeam().getName() : "N/A") + RESET);
            }
        }
    }

    private void editPlayer() {
        String username = getStringInput(CYAN + "Enter the username of the player to edit: " + RESET);
        Player player = playerController.getPlayerByUsername(username);
        if (player == null) {
            System.out.println(RED + ERROR_EMOJI + " Player not found." + RESET);
            return;
        }

        System.out.println(YELLOW + EDIT_EMOJI + " Editing player: " + username + RESET);
        String newUsername = getStringInput(CYAN + "Enter new username (3-50 characters, press enter to keep current): " + RESET);
        if (!newUsername.isEmpty()) {
            player.setUsername(newUsername);
        }

        int newAge = getIntInput(CYAN + "Enter new age (13-100, press enter to keep current): " + RESET, 13, 100);
        if (newAge != -1) {
            player.setAge(newAge);
        }

        try {
            playerController.updatePlayer(player);
            System.out.println(GREEN + SUCCESS_EMOJI + " Player updated successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error updating player: " + e.getMessage() + RESET);
        }
    }

    private void deletePlayer() {
        String username = getStringInput(CYAN + "Enter the username of the player to delete: " + RESET);
        try {
            playerController.deletePlayerByUsername(username);
            System.out.println(GREEN + SUCCESS_EMOJI + " Player deleted successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error deleting player: " + e.getMessage() + RESET);
        }
    }

    private void handleTeamMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + PURPLE + TEAM_EMOJI + " --- Team Management --- " + TEAM_EMOJI + RESET);
            System.out.println(BLUE + "1. " + ADD_EMOJI + " Create Team" + RESET);
            System.out.println(BLUE + "2. " + VIEW_EMOJI + " View All Teams" + RESET);
            System.out.println(BLUE + "3. " + EDIT_EMOJI + " Edit Team" + RESET);
            System.out.println(BLUE + "4. " + DELETE_EMOJI + " Delete Team" + RESET);
            System.out.println(BLUE + "5. " + ADD_EMOJI + " Add Player to Team" + RESET);
            System.out.println(BLUE + "6. " + REMOVE_EMOJI + " Remove Player from Team" + RESET);
            System.out.println(BLUE + "7. " + EXIT_EMOJI + " Back to Main Menu" + RESET);
            int choice = getIntInput(CYAN + "Enter your choice: " + RESET, 1, 7);
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
                    System.out.println(RED + ERROR_EMOJI + " Invalid choice. Please try again." + RESET);
            }
        }
    }

    private void createTeam() {
        System.out.println("\n" + YELLOW + ADD_EMOJI + " Creating a new team:" + RESET);
        String name = getStringInput(CYAN + "Enter team name: " + RESET, 3, 50);
        int ranking = getIntInput(CYAN + "Enter team ranking: " + RESET, 1, 1000);

        Team team = new Team();
        team.setName(name);
        team.setRanking(ranking);

        try {
            teamController.createTeam(team);
            System.out.println(GREEN + SUCCESS_EMOJI + " Team created successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error creating team: " + e.getMessage() + RESET);
        }
    }

    private void viewAllTeams() {
        List<Team> teams = teamController.getAllTeams();
        if (teams.isEmpty()) {
            System.out.println(YELLOW + WARNING_EMOJI + " No teams found." + RESET);
        } else {
            System.out.println("\n" + BLUE + VIEW_EMOJI + " All Teams:" + RESET);
            for (Team team : teams) {
                System.out.println(CYAN + "Name: " + team.getName()
                        + ", Ranking: " + team.getRanking()
                        + ", Players: " + team.getPlayers().size() + RESET);
            }
        }
    }

    private void editTeam() {
        String name = getStringInput(CYAN + "Enter the name of the team to edit: " + RESET);
        Team team = teamController.getTeamByName(name);
        if (team == null) {
            System.out.println(RED + ERROR_EMOJI + " Team not found." + RESET);
            return;
        }

        System.out.println(YELLOW + EDIT_EMOJI + " Editing team: " + name + RESET);
        String newName = getStringInput(CYAN + "Enter new team name (3-50 characters, press enter to keep current): " + RESET);
        if (!newName.isEmpty()) {
            team.setName(newName);
        }

        int newRanking = getIntInput(CYAN + "Enter new team ranking (1-1000, press enter to keep current): " + RESET, 1, 1000);
        if (newRanking != -1) {
            team.setRanking(newRanking);
        }

        try {
            teamController.updateTeam(team);
            System.out.println(GREEN + SUCCESS_EMOJI + " Team updated successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error updating team: " + e.getMessage() + RESET);
        }
    }

    private void deleteTeam() {
        String name = getStringInput(CYAN + "Enter the name of the team to delete: " + RESET);
        try {
            teamController.deleteTeamByName(name);
            System.out.println(GREEN + SUCCESS_EMOJI + " Team deleted successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error deleting team: " + e.getMessage() + RESET);
        }
    }

    private void addPlayerToTeam() {
        String teamName = getStringInput(CYAN + "Enter team name: " + RESET);
        String playerUsername = getStringInput(CYAN + "Enter player username: " + RESET);

        try {
            teamController.addPlayerToTeam(teamName, playerUsername);
            System.out.println(GREEN + SUCCESS_EMOJI + " Player added to the team successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error adding player to team: " + e.getMessage() + RESET);
        }
    }

    private void removePlayerFromTeam() {
        String teamName = getStringInput(CYAN + "Enter team name: " + RESET);
        String playerUsername = getStringInput(CYAN + "Enter player username: " + RESET);

        try {
            teamController.removePlayerFromTeam(teamName, playerUsername);
            System.out.println(GREEN + SUCCESS_EMOJI + " Player removed from the team successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error removing player from team: " + e.getMessage() + RESET);
        }
    }

    private void handleGameMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + PURPLE + GAME_EMOJI + " --- Game Management --- " + GAME_EMOJI + RESET);
            System.out.println(BLUE + "1. " + ADD_EMOJI + " Create Game" + RESET);
            System.out.println(BLUE + "2. " + VIEW_EMOJI + " View All Games" + RESET);
            System.out.println(BLUE + "3. " + EDIT_EMOJI + " Edit Game" + RESET);
            System.out.println(BLUE + "4. " + DELETE_EMOJI + " Delete Game" + RESET);
            System.out.println(BLUE + "5. " + EXIT_EMOJI + " Back to Main Menu" + RESET);
            int choice = getIntInput(CYAN + "Enter your choice: " + RESET, 1, 5);
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
                    System.out.println(RED + ERROR_EMOJI + " Invalid choice. Please try again." + RESET);
            }
        }
    }

    private void createGame() {
        System.out.println("\n" + YELLOW + ADD_EMOJI + " Creating a new game:" + RESET);
        String name = getStringInput(CYAN + "Enter game name: " + RESET, 3, 50);
        int difficulty = getIntInput(CYAN + "Enter game difficulty: " + RESET, 1, 10);
        int averageMatchDuration = getIntInput(CYAN + "Enter average match duration in minutes: " + RESET, 1, 180);

        Game game = new Game();
        game.setName(name);
        game.setDifficulty(difficulty);
        game.setAverageMatchDuration(averageMatchDuration);

        try {
            gameController.createGame(game);
            System.out.println(GREEN + SUCCESS_EMOJI + " Game created successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error creating game: " + e.getMessage() + RESET);
        }
    }

    private void viewAllGames() {
        List<Game> games = gameController.getAllGames();
        if (games.isEmpty()) {
            System.out.println(YELLOW + WARNING_EMOJI + " No games found." + RESET);
        } else {
            System.out.println("\n" + BLUE + VIEW_EMOJI + " All Games:" + RESET);
            for (Game game : games) {
                System.out.println(CYAN + "Name: " + game.getName()
                        + ", Difficulty: " + game.getDifficulty()
                        + ", Average Match Duration: " + game.getAverageMatchDuration() + " minutes" + RESET);
            }
        }
    }

    private void editGame() {
        String name = getStringInput(CYAN + "Enter the name of the game to edit: " + RESET);
        Game game = gameController.getGameByName(name);
        if (game == null) {
            System.out.println(RED + ERROR_EMOJI + " Game not found." + RESET);
            return;
        }

        System.out.println(YELLOW + EDIT_EMOJI + " Editing game: " + name + RESET);
        String newName = getStringInput(CYAN + "Enter new game name (3-50 characters, press enter to keep current): " + RESET);
        if (!newName.isEmpty()) {
            game.setName(newName);
        }

        int newDifficulty = getIntInput(CYAN + "Enter new game difficulty (1-10, press enter to keep current): " + RESET, 1, 10);
        if (newDifficulty != -1) {
            game.setDifficulty(newDifficulty);
        }

        int newAverageMatchDuration = getIntInput(CYAN + "Enter new average match duration in minutes (1-180, press enter to keep current): " + RESET, 1, 180);
        if (newAverageMatchDuration != -1) {
            game.setAverageMatchDuration(newAverageMatchDuration);
        }

        try {
            gameController.updateGame(game);
            System.out.println(GREEN + SUCCESS_EMOJI + " Game updated successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error updating game: " + e.getMessage() + RESET);
        }
    }

    private void deleteGame() {
        String name = getStringInput(CYAN + "Enter the name of the game to delete: " + RESET);
        try {
            gameController.deleteGameByName(name);
            System.out.println(GREEN + SUCCESS_EMOJI + " Game deleted successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error deleting game: " + e.getMessage() + RESET);
        }
    }

    private void handleTournamentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + PURPLE + TOURNAMENT_EMOJI + " --- Tournament Management --- " + TOURNAMENT_EMOJI + RESET);
            System.out.println(BLUE + "1. " + ADD_EMOJI + " Create Tournament" + RESET);
            System.out.println(BLUE + "2. " + VIEW_EMOJI + " View All Tournaments" + RESET);
            System.out.println(BLUE + "3. " + EDIT_EMOJI + " Edit Tournament" + RESET);
            System.out.println(BLUE + "4. " + DELETE_EMOJI + " Delete Tournament" + RESET);
            System.out.println(BLUE + "5. " + ADD_EMOJI + " Add Team to Tournament" + RESET);
            System.out.println(BLUE + "6. " + REMOVE_EMOJI + " Remove Team from Tournament" + RESET);
            System.out.println(BLUE + "7. " + ADD_EMOJI + " Add Game to Tournament" + RESET);
            System.out.println(BLUE + "8. " + STATUS_EMOJI + " Change Tournament Status" + RESET);
            System.out.println(BLUE + "9. " + TIME_EMOJI + " Calculate Estimated Duration" + RESET);
            System.out.println(BLUE + "10. " + EXIT_EMOJI + " Back to Main Menu" + RESET);
            int choice = getIntInput(CYAN + "Enter your choice: " + RESET, 1, 10);
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
                    System.out.println(RED + ERROR_EMOJI + " Invalid choice. Please try again." + RESET);
            }
        }
    }

    private void createTournament() {
        System.out.println("\n" + YELLOW + ADD_EMOJI + " Creating a new tournament:" + RESET);
        String title = getStringInput(CYAN + "Enter tournament title: " + RESET, 1, 100);
        Date startDate = getDateInput(CYAN + "Enter start date (yyyy-MM-dd): " + RESET);
        Date endDate = getDateInput(CYAN + "Enter end date (yyyy-MM-dd): " + RESET);

        if (endDate.before(startDate)) {
            System.out.println(RED + ERROR_EMOJI + " End date cannot be before start date. Please try again." + RESET);
            return;
        }

        int spectatorCount = getIntInput(CYAN + "Enter spectator count: " + RESET, 0, Integer.MAX_VALUE);
        int matchBreakTime = getIntInput(CYAN + "Enter match break time in minutes: " + RESET, 0, Integer.MAX_VALUE);
        int ceremonyTime = getIntInput(CYAN + "Enter ceremony time in minutes: " + RESET, 0, Integer.MAX_VALUE);

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
            System.out.println(GREEN + SUCCESS_EMOJI + " Tournament created successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error creating tournament: " + e.getMessage() + RESET);
        }
    }

    private void viewAllTournaments() {
        List<Tournament> tournaments = tournamentController.getAllTournaments();
        if (tournaments.isEmpty()) {
            System.out.println(YELLOW + WARNING_EMOJI + " No tournaments found." + RESET);
        } else {
            System.out.println("\n" + BLUE + VIEW_EMOJI + " All Tournaments:" + RESET);
            for (Tournament tournament : tournaments) {
                Set<Team> teams = tournament.getTeams();
                int estimatedDuration = tournamentController.calculateEstimatedDuration(tournament.getTitle());
                System.out.println(CYAN + "Title: " + tournament.getTitle()
                        + ", Game: " + (tournament.getGame() != null ? tournament.getGame().getName() : "N/A")
                        + ", Status: " + tournament.getStatus()
                        + ", Teams: " + teams.size()
                        + " (" + String.join(", ", teams.stream().map(Team::getName).toArray(String[]::new)) + ")"
                        + ", Estimated Duration: " + estimatedDuration + " minutes" + RESET);
            }
        }
    }

    private void editTournament() {
        String title = getStringInput(CYAN + "Enter the title of the tournament to edit: " + RESET);
        Tournament tournament = tournamentController.getTournamentByTitle(title);
        if (tournament == null) {
            System.out.println(RED + ERROR_EMOJI + " Tournament not found." + RESET);
            return;
        }

        System.out.println(YELLOW + EDIT_EMOJI + " Editing tournament: " + title + RESET);
        String newTitle = getStringInput(CYAN + "Enter new tournament title (1-100 characters, press enter to keep current): " + RESET);
        if (!newTitle.isEmpty()) {
            tournament.setTitle(newTitle);
        }

        String startDatePrompt = CYAN + "Enter new start date (yyyy-MM-dd, press enter to keep current): " + RESET;
        String endDatePrompt = CYAN + "Enter new end date (yyyy-MM-dd, press enter to keep current): " + RESET;

        Date newStartDate = getOptionalDateInput(startDatePrompt, tournament.getStartDate());
        Date newEndDate = getOptionalDateInput(endDatePrompt, tournament.getEndDate());

        if (newEndDate.before(newStartDate)) {
            System.out.println(RED + ERROR_EMOJI + " End date cannot be before start date. Changes not applied." + RESET);
        } else {
            tournament.setStartDate(newStartDate);
            tournament.setEndDate(newEndDate);
        }

        int newSpectatorCount = getIntInput(CYAN + "Enter new spectator count (press enter to keep current): " + RESET, 0, Integer.MAX_VALUE);
        if (newSpectatorCount != -1) {
            tournament.setSpectatorCount(newSpectatorCount);
        }

        int newMatchBreakTime = getIntInput(CYAN + "Enter new match break time in minutes (press enter to keep current): " + RESET, 0, Integer.MAX_VALUE);
        if (newMatchBreakTime != -1) {
            tournament.setMatchBreakTime(newMatchBreakTime);
        }

        int newCeremonyTime = getIntInput(CYAN + "Enter new ceremony time in minutes (press enter to keep current): " + RESET, 0, Integer.MAX_VALUE);
        if (newCeremonyTime != -1) {
            tournament.setCeremonyTime(newCeremonyTime);
        }

        try {
            tournamentController.updateTournament(tournament);
            System.out.println(GREEN + SUCCESS_EMOJI + " Tournament updated successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error updating tournament: " + e.getMessage() + RESET);
        }
    }

    private void deleteTournament() {
        String title = getStringInput(CYAN + "Enter tournament title to delete: " + RESET);
        try {
            tournamentController.deleteTournamentByTitle(title);
            System.out.println(GREEN + SUCCESS_EMOJI + " Tournament deleted successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error deleting tournament: " + e.getMessage() + RESET);
        }
    }

    private void addTeamToTournament() {
        String tournamentTitle = getStringInput(CYAN + "Enter tournament title: " + RESET);
        String teamName = getStringInput(CYAN + "Enter team name: " + RESET);

        try {
            tournamentController.addTeamToTournament(tournamentTitle, teamName);
            System.out.println(GREEN + SUCCESS_EMOJI + " Team added to the tournament successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error adding team to tournament: " + e.getMessage() + RESET);
        }
    }

    private void removeTeamFromTournament() {
        String tournamentTitle = getStringInput(CYAN + "Enter tournament title: " + RESET);
        String teamName = getStringInput(CYAN + "Enter team name: " + RESET);

        try {
            tournamentController.removeTeamFromTournament(tournamentTitle, teamName);
            System.out.println(GREEN + SUCCESS_EMOJI + " Team removed from the tournament successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error removing team from tournament: " + e.getMessage() + RESET);
        }
    }

    private void addGameToTournament() {
        String tournamentTitle = getStringInput(CYAN + "Enter tournament title: " + RESET);
        String gameName = getStringInput(CYAN + "Enter game name: " + RESET);

        try {
            tournamentController.addGameToTournament(tournamentTitle, gameName);
            System.out.println(GREEN + SUCCESS_EMOJI + " Game added to the tournament successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error adding game to tournament: " + e.getMessage() + RESET);
        }
    }

    private void changeTournamentStatus() {
        String title = getStringInput(CYAN + "Enter tournament title: " + RESET);
        TournamentStatus status = getTournamentStatusInput(CYAN + "Enter new status (PLANNED, ONGOING, COMPLETED): " + RESET);

        try {
            tournamentController.changeStatus(title, status);
            System.out.println(GREEN + SUCCESS_EMOJI + " Tournament status changed successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error changing tournament status: " + e.getMessage() + RESET);
        }
    }

    private void calculateEstimatedDuration() {
        String title = getStringInput(CYAN + "Enter tournament title: " + RESET);

        try {
            int estimatedDuration = tournamentController.calculateEstimatedDuration(title);
            System.out.println(CYAN + "Estimated Duration: " + estimatedDuration + " minutes" + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + ERROR_EMOJI + " Error calculating estimated duration: " + e.getMessage() + RESET);
        }
    }

    private String getStringInput(String prompt, int minLength, int maxLength) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        while (input.length() < minLength || input.length() > maxLength) {
            System.out.println(RED + ERROR_EMOJI + " Invalid input length. Please try again." + RESET);
            System.out.print(prompt);
            input = scanner.nextLine();
        }
        return input;
    }

    private int getIntInput(String prompt, int min, int max) {
        System.out.print(prompt);
        int input;
        try {
            input = Integer.parseInt(scanner.nextLine());
            while (input < min || input > max) {
                System.out.println(RED + ERROR_EMOJI + " Invalid input range. Please try again." + RESET);
                System.out.print(prompt);
                input = Integer.parseInt(scanner.nextLine());
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + ERROR_EMOJI + " Invalid input type. Please try again." + RESET);
            return getIntInput(prompt, min, max);
        }
        return input;
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private TournamentStatus getTournamentStatusInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().toUpperCase();
        while (!input.equals("PLANNED") && !input.equals("ONGOING") && !input.equals("COMPLETED")) {
            System.out.println(RED + ERROR_EMOJI + " Invalid status. Please try again." + RESET);
            System.out.print(prompt);
            input = scanner.nextLine().toUpperCase();
        }
        return TournamentStatus.valueOf(input);
    }

    private Date getOptionalDateInput(String prompt, Date currentDate) {
        String dateStr = getStringInput(prompt);
        if (dateStr.isEmpty()) {
            return currentDate;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        while (true) {
            try {
                Date inputDate = dateFormat.parse(dateStr);
                Date today = new Date();
                if (inputDate.before(today)) {
                    System.out.println(RED + ERROR_EMOJI + " Date must be present or in the future. Please try again." + RESET);
                    dateStr = getStringInput(prompt);
                    if (dateStr.isEmpty()) {
                        return currentDate;
                    }
                } else {
                    return inputDate;
                }
            } catch (ParseException e) {
                System.out.println(RED + ERROR_EMOJI + " Invalid date format. Please use yyyy-MM-dd or press enter to keep current." + RESET);
                dateStr = getStringInput(prompt);
                if (dateStr.isEmpty()) {
                    return currentDate;
                }
            }
        }
    }

    private Date getDateInput(String prompt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        while (true) {
            String dateStr = getStringInput(prompt);
            try {
                Date inputDate = dateFormat.parse(dateStr);
                Date today = new Date();
                if (inputDate.before(today)) {
                    System.out.println(RED + ERROR_EMOJI + " Date must be present or in the future. Please try again." + RESET);
                } else {
                    return inputDate;
                }
            } catch (ParseException e) {
                System.out.println(RED + ERROR_EMOJI + " Invalid date format. Please use yyyy-MM-dd." + RESET);
            }
        }
    }

}

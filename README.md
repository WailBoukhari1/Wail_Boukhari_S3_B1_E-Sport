# E-Sports Tournament Management System

## Overview

This E-Sports Tournament Management System is a robust Java application designed to handle the organization and management of e-sports tournaments. It provides comprehensive functionality for managing games, teams, players, and tournaments, utilizing Spring Framework for dependency injection and transaction management.

## Features

- CRUD operations for Games, Teams, Players, and Tournaments
- Add and remove teams from tournaments
- Create tournaments with specific games and teams
- Calculate and update estimated duration for tournaments
- Manage tournament statuses (e.g., PLANNED, ONGOING, COMPLETED, CANCELLED)
- Console-based user interface for interaction

## Project Structure

The project follows a typical Spring-based Java application structure:

- `src/main/java/com/esports/`
  - `model/`: Entity classes (Game, Team, Player, Tournament)
  - `repository/`: Data access interfaces and implementations
  - `service/`: Business logic layer
  - `controller/`: Handles user input and orchestrates operations
  - `ui/`: Console interface for user interaction

## Key Components

### Models
- Game: Represents a video game with properties like name, difficulty, and average match duration
- Team: Represents an e-sports team with associated players
- Player: Represents an individual player with a username and associated team
- Tournament: Represents an e-sports tournament with associated game, teams, and status

### Services
- GameService: Manages game-related operations
- TeamService: Handles team management and player assignments
- PlayerService: Manages player-related operations
- TournamentService: Orchestrates tournament creation, team assignment, and status management

### Controllers
- GameController: Handles game-related user inputs
- TeamController: Manages team-related user inputs
- PlayerController: Processes player-related user inputs
- TournamentController: Coordinates tournament-related user inputs

## Key Features Implementation

### Creating a Tournament with Game
```java
public void createTournamentWithGame(String title, String gameName, int difficulty, int averageMatchDuration, List<String> teamNames) {
    Game game = new Game();
    game.setName(gameName);
    game.setDifficulty(difficulty);
    game.setAverageMatchDuration(averageMatchDuration);
    gameRepository.save(game);

    Tournament tournament = new Tournament();
    tournament.setTitle(title);
    tournament.setGame(game);
    tournament.setStatus(TournamentStatus.PLANNED);

    Set<Team> teams = new HashSet<>();
    for (String teamName : teamNames) {
        Team team = teamRepository.findByName(teamName);
        if (team == null) {
            team = new Team();
            team.setName(teamName);
            teamRepository.save(team);
        }
        teams.add(team);
        team.getTournaments().add(tournament);
    }
    tournament.setTeams(teams);

    save(tournament);

    for (Team team : teams) {
        teamRepository.update(team);
    }

    updateEstimatedDuration(tournament);
}
```

### Adding a Player to a Team
```java
public void createTournamentWithGame(String title, String gameName, int difficulty, int averageMatchDuration, List<String> teamNames) {
    Game game = new Game();
    game.setName(gameName);
    game.setDifficulty(difficulty);
    game.setAverageMatchDuration(averageMatchDuration);
    gameRepository.save(game);

    Tournament tournament = new Tournament();
    tournament.setTitle(title);
    tournament.setGame(game);
    tournament.setStatus(TournamentStatus.PLANNED);

    Set<Team> teams = new HashSet<>();
    for (String teamName : teamNames) {
        Team team = teamRepository.findByName(teamName);
        if (team == null) {
            team = new Team();
            team.setName(teamName);
            teamRepository.save(team);
        }
        teams.add(team);
        team.getTournaments().add(tournament);
    }
    tournament.setTeams(teams);

    save(tournament);

    for (Team team : teams) {
        teamRepository.update(team);
    }

    updateEstimatedDuration(tournament);
}
```

### Console Interface for User Interaction
```java
public void createTournamentWithGame(String title, String gameName, int difficulty, int averageMatchDuration, List<String> teamNames) {
    Game game = new Game();
    game.setName(gameName);
    game.setDifficulty(difficulty);
    game.setAverageMatchDuration(averageMatchDuration);
    gameRepository.save(game);

    Tournament tournament = new Tournament();
    tournament.setTitle(title);
    tournament.setGame(game);
    tournament.setStatus(TournamentStatus.PLANNED);

    Set<Team> teams = new HashSet<>();
    for (String teamName : teamNames) {
        Team team = teamRepository.findByName(teamName);
        if (team == null) {
            team = new Team();
            team.setName(teamName);
            teamRepository.save(team);
        }
        teams.add(team);
        team.getTournaments().add(tournament);
    }
    tournament.setTeams(teams);

    save(tournament);

    for (Team team : teams) {
        teamRepository.update(team);
    }

    updateEstimatedDuration(tournament);
}
```

## Setup and Installation

1. Clone the repository
2. Ensure you have Java JDK 8 or higher installed
3. Set up your database (H2 in-memory database is used by default)
4. Build the project using Maven: `mvn clean install`
5. Run the application: `java -jar target/esports-tournament-management-system.jar`

## Dependencies

- Spring Framework: For dependency injection and transaction management
- Hibernate: ORM for database operations
- SLF4J: For logging
- H2 Database: In-memory database for development and testing

## Configuration

The application is configured using Spring's XML-based configuration. The main configuration file is `applicationContext.xml`, which defines:

- Data source configuration (H2 database)
- Transaction management
- Entity manager and JPA configuration
- Repository, service, and controller beans

## Running the Application

The application starts with the `Main` class, which sets up the Spring context and initializes the console interface.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License.
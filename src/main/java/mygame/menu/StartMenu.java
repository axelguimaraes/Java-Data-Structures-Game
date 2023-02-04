package mygame.menu;

import mygame.exceptions.PlayerNotFoundException;
import mygame.exceptions.PlayerWithNoTeamException;
import mygame.game.*;
import mygame.structures.classes.LinkedQueue;

import java.util.Scanner;

public class StartMenu {
    public static void main(String[] args) throws PlayerNotFoundException, PlayerWithNoTeamException {
        GameMap gameMap = new GameMap();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("== START MENU ==\n\n" +
                    "" +
                    "1. Players menu\n" +
                    "2. Map menu\n" +
                    "5. Import Demo (DEV ONLY)\n\n" + // TODO: DEV ONLY!!!
                    "" +
                    "3. Start\n" +
                    "0. Exit\n\n" +
                    "Your choice:");

            switch (scanner.nextInt()) {
                case 1:
                    playersMenu(gameMap, scanner);
                    break;
                case 2:
                    mapMenu(gameMap, scanner);
                    break;
                case 3:
                    if (gameMap.getPlayersInGame().isEmpty()) {
                        System.err.println("No players registered in the game!");
                    } else {
                        gameStart(gameMap, scanner);
                    }
                    break;
                case 5:
                    importEverything(gameMap);
                    System.err.println("Imported!");
                    break;
                case 0:
                    System.out.println("Bye!");
                    System.exit(0);
                default:
                    System.err.println("Invalid option!");
            }
        }
    }

    public static void playersMenu(GameMap gameMap, Scanner scanner) {
        while (true) {
            System.out.println("== PLAYERS MENU ==\n\n" +
                    "" +
                    "1. Register player\n" +
                    "2. List players\n" +
                    "3. Edit player\n" +
                    "4. Remove player\n" +
                    "5. Import players list\n" +
                    "6. Export players list\n" +
                    "0. Exit\n\n" +
                    "" +
                    "Your choice: ");

            switch (scanner.nextInt()) {
                case 1:
                    // TODO: Register player
                    break;
                case 2:
                    // TODO: List players
                    break;
                case 3:
                    // TODO: Edit players
                    break;
                case 4:
                    // TODO: Remove players
                    break;
                case 5:
                    // TODO: Import players
                    break;
                case 6:
                    // TODO: Export players
                    break;
                case 0:
                    return;
                default:
                    System.err.println("Invalid option!");
            }
        }
    }

    public static void mapMenu(GameMap gameMap, Scanner scanner) {
        while (true) {
            System.out.println("== MAP MENU ==\n\n" +
                    "" +
                    "1. Locations menu\n" +
                    "2. Import map\n" +
                    "3. Export map\n" +
                    "0. Exit\n\n" +
                    "" +
                    "Your choice: ");

            switch (scanner.nextInt()) {
                case 1:
                    locationsMenu(gameMap, scanner);
                    break;
                case 2:
                    // TODO: Import map
                    break;
                case 3:
                    // TODO: Export map
                    break;
                case 0:
                    return;
                default:
                    System.err.println("Invalid option!");
            }
        }
    }

    public static void gameStart(GameMap gameMap, Scanner scanner) throws PlayerNotFoundException {
        LinkedQueue<Player> playersTurn = new LinkedQueue<>(); // If stats updating in map but not here, update queue
        for (Player player : gameMap.getPlayersInGame()) {
            playersTurn.enqueue(player);
        }

        while (true) {
            Player turn = playersTurn.dequeue();
            Local currentPosition = gameMap.getLocalByID(turn.getCurrentPositionID());

            System.out.println("\n\n\t== PLAYER " + turn.getId() + " TURN ==\n" +
                    "Player name: " + turn.getName() + "\n" +
                    "Energy: " + turn.getEnergy() + "\n" +
                    "Level: " + turn.getLevel() + "\n" +
                    "XP: " + turn.getXp() + "\n" +
                    "Current location: " + currentPosition.getLocalType() + " ID " + currentPosition.getId() + "\n");

            switch (currentPosition.getLocalType()) {
                case PORTAL:
                    Portal portal = (Portal) currentPosition;
                    System.out.println("\tTeam: " + portal.getTeam() + "\n" +
                            "\tName: " + portal.getName() + "\n" +
                            "\tEnergy: " + portal.getEnergy() + "\n\n" +
                            //"\tConqueror: " + portal.getConqueror().getName() + "\n\n" +
                            "" +
                            "1. Conquer portal\n" +
                            "2. Charge portal\n" +
                            "3. Navigate to other location\n" +
                            "0. Exit to main menu\n\n" +
                            "Your choice: ");
                    switch (scanner.nextInt()) {
                        case 1:
                            ((Portal) gameMap.getLocalByID(currentPosition.getId())).getConquered(gameMap.getPlayerFromID(turn.getId()));
                            break;
                        case 2:
                            System.out.println("How much energy?:");
                            ((Portal) gameMap.getLocalByID(currentPosition.getId())).rechargeEnergy(gameMap.getPlayerFromID(turn.getId()), scanner.nextInt());
                            break;
                        case 3:
                            System.out.println("Destination ID: ");
                            gameMap.getPlayerFromID(turn.getId()).navigateTo(gameMap.getLocalByID(scanner.nextInt()));
                            break;
                        case 0:
                            return;
                        default:
                            System.err.println("Invalid option!");
                    }
                    break;

                case CONNECTOR:
                    Connector connector = (Connector) currentPosition;
                    System.out.println("\tEnergy: " + connector.getEnergy() + "\n\n" +
                            "" +
                            "1. Recharge player energy\n" +
                            "2. Navigate to other location\n" +
                            "0. Exit to main menu\n\n" +
                            "Your choive: ");
                    switch (scanner.nextInt()) {
                        case 1:
                            gameMap.getPlayerFromID(turn.getId()).rechargeEnergy((Connector) gameMap.getLocalByID(currentPosition.getId()));
                            break;
                        case 2:
                            System.out.println("Destination ID: ");
                            gameMap.getPlayerFromID(turn.getId()).navigateTo(gameMap.getLocalByID(scanner.nextInt()));
                            break;
                        case 0:
                            return;
                        default:
                            System.err.println("Invalid option!");
                    }
                    break;
            }
            playersTurn.enqueue(turn);

        }
    }

    public static void locationsMenu(GameMap gameMap, Scanner scanner) {
        while (true) {
            System.out.println("== LOCATIONS MENU ==\n\n" +
                    "" +
                    "1. Add location\n" +
                    "2. List locations\n" +
                    "3. Edit location\n" +
                    "4. Remove location\n" +
                    "5. Add path\n" +
                    "6. Remove path\n" +
                    "7. Import locations\n" +
                    "8. Export locations\n;" +
                    "0. Exit\n\n" +
                    "" +
                    "Your choice: ");

            switch (scanner.nextInt()) {
                case 1:
                    // TODO: Add location
                    break;
                case 2:
                    // TODO: List locations
                    break;
                case 3:
                    // TODO: Edit location
                    break;
                case 4:
                    // TODO: Remove location
                    break;
                case 5:
                    // TODO: Add path
                    break;
                case 6:
                    // TODO: Remove path
                    break;
                case 7:
                    // TODO: Import locations
                    break;
                case 8:
                    // TODO: Export locations
                    break;
                case 0:
                    return;
                default:
                    System.err.println("Invalid option!");
            }
        }
    }

    public static void importEverything(GameMap gameMap) throws PlayerWithNoTeamException {
        Portal portal1 = new Portal("Sao Bento Railway Station", 0, new Coordinates(41.14444, -8.61037), 500);
        Portal portal2 = new Portal("Clerigos Tower", 0, new Coordinates(41.14578, -8.61391), 250);
        Portal portal3 = new Portal("Bolsa Palace", 0, new Coordinates(41.14139, -8.61564), 300);
        Portal portal4 = new Portal("Lello Bookstore", 0, new Coordinates(41.14688, -8.61564), 350);

        Connector connector1 = new Connector(100, new Coordinates(41.14053, -8.60969), 2);
        Connector connector2 = new Connector(300, new Coordinates(41.15904, -8.63069), 8);
        Connector connector3 = new Connector(50, new Coordinates(41.15992, -8.65966), 1);
        Connector connector4 = new Connector(150, new Coordinates(41.14069, -8.61012), 5);
        Connector connector5 = new Connector(100, new Coordinates(41.13813, -8.61088), 2);

        gameMap.addLocation(portal1);
        gameMap.addLocation(portal2);
        gameMap.addLocation(portal3);
        gameMap.addLocation(portal4);
        gameMap.addLocation(connector1);
        gameMap.addLocation(connector2);
        gameMap.addLocation(connector3);
        gameMap.addLocation(connector4);
        gameMap.addLocation(connector5);

        gameMap.connectLocationsWithCoordinates(portal1, connector1);
        gameMap.connectLocationsWithCoordinates(portal1, connector5);
        gameMap.connectLocationsWithCoordinates(portal2, connector1);
        gameMap.connectLocationsWithCoordinates(portal2, connector3);
        gameMap.connectLocationsWithCoordinates(portal3, portal4);
        gameMap.connectLocationsWithCoordinates(portal3, connector4);
        gameMap.connectLocationsWithCoordinates(portal4, portal1);
        gameMap.connectLocationsWithCoordinates(portal4, connector4);
        gameMap.connectLocationsWithCoordinates(portal4, connector5);
        gameMap.connectLocationsWithCoordinates(connector1, portal2);
        gameMap.connectLocationsWithCoordinates(connector1, portal4);
        gameMap.connectLocationsWithCoordinates(connector2, portal2);
        gameMap.connectLocationsWithCoordinates(connector2, portal1);
        gameMap.connectLocationsWithCoordinates(connector3, connector2);
        gameMap.connectLocationsWithCoordinates(connector4, connector3);
        gameMap.connectLocationsWithCoordinates(connector4, portal2);
        gameMap.connectLocationsWithCoordinates(connector4, portal3);
        gameMap.connectLocationsWithCoordinates(connector5, portal4);
        gameMap.connectLocationsWithCoordinates(connector5, portal3);

        gameMap.addPlayer(new Player("John Doe", Team.SPARKS));
        gameMap.addPlayer(new Player("Tom", Team.GIANTS));
        gameMap.addPlayer(new Player("Jerry", Team.SPARKS));
        gameMap.addPlayer(new Player("Abe", Team.GIANTS));
    }
}

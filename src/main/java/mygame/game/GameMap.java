package mygame.game;

import mygame.structures.classes.ArrayUnorderedList;
import mygame.structures.classes.LinkedStack;
import mygame.structures.classes.Network;
import mygame.structures.exceptions.ElementNotFoundException;

import java.util.Iterator;

public class GameMap {
    private final Network<Local> map;
    private final ArrayUnorderedList<Player> playersInGame;

    public GameMap() {
        this.map = new Network<>();
        this.playersInGame = new ArrayUnorderedList<>();
    }

    public void addLocation(Local local) {
        this.map.addVertex(local);
    }

    public void addPlayer(Player player) {
        player.setCurrentPosition(getLocalByID(1));
        playersInGame.addToRear(player);
    }

    public ArrayUnorderedList<Player> getPlayersInGame() {
        return this.playersInGame;
    }

    public Local getLocalByID(int id) {
        Iterator<Local> iterator = this.map.iteratorDFS(0);
        while (iterator.hasNext()) {
            Local local = iterator.next();
            if (local.getId() == id) {
                return local;
            }
        }
        throw new ElementNotFoundException("network");
    }

    public ArrayUnorderedList<Local> getShortestPathToLocal(int startID, int targetID) {
        ArrayUnorderedList<Local> stack = new ArrayUnorderedList<>();

        Local local1 = getLocalByID(startID);
        Local local2 = getLocalByID(targetID);

        Iterator<Local> it = getIteratorShortestPath(local1, local2);
        while (it.hasNext()) {
            stack.addToRear(it.next());
        }
        return stack;
    }

    public void connectLocations(Local location1, Local location2, double weight) {
        this.map.addEdge(location1, location2, weight);
    }

    public void connectLocationsWithCoordinates(Local location1, Local location2) {
        this.map.addEdge(location1, location2, coordinatesDistance(location1, location2));
    }

    public String toString() {
        return this.map.toString();
    }

    private static double coordinatesDistance(Local local1, Local local2) {
        final int EARTH_RADIUS = 6371;
        double lat1 = local1.getCoordinates().getLatitude();
        double lat2 = local2.getCoordinates().getLatitude();
        double lon1 = local1.getCoordinates().getLongitude();
        double lon2 = local2.getCoordinates().getLongitude();

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    private Iterator<Local> getIteratorShortestPath(Local start, Local target) {
        return this.map.iteratorShortestPath(start, target);
    }
}

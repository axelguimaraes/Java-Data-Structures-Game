package mygame.game;

import mygame.interfaces.IConnector;
import mygame.interfaces.ILocal;
import mygame.interfaces.IPlayer;
import mygame.interfaces.IPortal;
import mygame.structures.lists.UnorderedArray;

public class Player implements IPlayer {
    private static int nextId = 1;
    private final int id;
    private int energy;
    private Team team;
    private String name;
    // cooldowns (?)


    public Player(String name, int energy, Team team) {
        this.name = name;
        this.id = nextId++;
        this.energy = energy;
        this.team = team;
    }

    @Override
    public int getEnergy() {
        return this.energy;
    }

    @Override
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public boolean conquerPortal(IPortal portal) {
        return ((Portal) portal).getConquered(this);
    }

    @Override
    public boolean rechargeEnergy(IConnector connector) {
        return ((Connector) connector).chargePlayer(this);
    }

    @Override
    public Team getTeam() {
        return this.team;
    }

    @Override
    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public UnorderedArray<IPortal> getConqueredPortals() {
        return null;
    }

    @Override
    public ILocal getLocation() {
        return null;
    }

    @Override
    public void navigateTo(ILocal destination) {

    }

    @Override
    public boolean chargePortal(IPortal portal, int energy) {
        return portal.rechargeEnergy(this, energy);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFromDifferentTeam(IPortal portal) {
        if (portal.getTeam().equals(Team.NONE)) {
            return false;
        }

        return !this.team.equals(portal.getTeam());
    }
}

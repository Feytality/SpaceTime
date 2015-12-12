package org.soen387.domain.model.pilot;

import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObject;
import org.soen387.domain.model.player.IPlayer;
import org.soen387.domain.model.team.ITeam;

public class Pilot extends DomainObject<Long> implements IPilot{
	
	String name;
	IPlayer player;
	List<ITeam> teams = new ArrayList<ITeam>();
	
	public Pilot(long id, long version, String name, IPlayer player) {
		super(id, version);
		this.name = name;
		this.player = player;
	}
	
	public Pilot(long id, long version, String name, IPlayer player,
			List<ITeam> teams) {
		super(id, version);
		this.name = name;
		this.player = player;
		this.teams = teams;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public IPlayer getPlayer() {
		return player;
	}

	@Override
	public void setPlayer(IPlayer player) {
		this.player = player;
	}
	
	@Override
	public List<ITeam> getTeams() {
		return teams;
	}

	@Override
	public void setTeams(List<ITeam> teams) {
		this.teams = teams;
	}

	@Override
	public void addTeam(ITeam team) {
		teams.add(team);
	}

	@Override
	public void removeTeam(ITeam team) {
		teams.remove(team);
	}

}

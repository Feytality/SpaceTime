package org.soen387.domain.model.team;

import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObject;
import org.soen387.domain.model.pilot.IPilot;
import org.soen387.domain.model.player.IPlayer;

public class Team extends DomainObject<Long> implements ITeam{

	String name;
	IPlayer player;
	List<IPilot> members;
	
	public Team(long id, long version, String name, IPlayer player,
			List<IPilot> members) {
		super(id, version);
		this.name = name;
		this.player = player;
		this.members = members;
	}
	
	public Team(long id, long version, String name, IPlayer player) {
		super(id, version);
		this.name = name;
		this.player = player;
		this.members = new ArrayList<IPilot>();
	}
	
	public List<IPilot> getMembers() {
		return members;
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
	public void validateTeam() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPilot(IPilot pilot) {
		members.add(pilot);		
	}

	@Override
	public void removePilot(IPilot pilot) {
		members.remove(pilot);
	}

	@Override
	public List<IPilot> getPilots() {
		return members;
	}

	@Override
	public void setPilots(List<IPilot> pilots) {
		this.members = pilots;		
	}

}

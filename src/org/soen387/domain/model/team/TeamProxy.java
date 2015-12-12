package org.soen387.domain.model.team;

import java.util.List;

import org.dsrg.soenea.domain.proxy.DomainObjectProxy;
import org.soen387.domain.model.pilot.IPilot;
import org.soen387.domain.model.player.IPlayer;
import org.soen387.domain.model.team.mapper.TeamInputMapper;

public class TeamProxy extends DomainObjectProxy<Long, Team> implements ITeam {
	
	public TeamProxy(long id) {
		super(id);
	}

	@Override
	public Team getFromMapper(Long id) {
		try {
			return TeamInputMapper.find(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getName() {
		return getInnerObject().getName();
	}

	@Override
	public void setName(String name) {
		getInnerObject().setName(name);
	}

	@Override
	public IPlayer getPlayer() {
		return getInnerObject().getPlayer();
	}

	@Override
	public void setPlayer(IPlayer player) {
		getInnerObject().setPlayer(player);
	}

	@Override
	public List<IPilot> getMembers() {
		return getInnerObject().getMembers();
	}

	@Override
	public void validateTeam() {
		getInnerObject().validateTeam();
	}

	@Override
	public void addPilot(IPilot pilot) {
		getInnerObject().addPilot(pilot);
	}

	@Override
	public void removePilot(IPilot pilot) {
		getInnerObject().removePilot(pilot);
	}

	@Override
	public List<IPilot> getPilots() {
		return getInnerObject().getPilots();
	}

	@Override
	public void setPilots(List<IPilot> pilots) {
		getInnerObject().setPilots(pilots);
	}

}

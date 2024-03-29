package org.soen387.domain.model.pilot;

import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;
import org.soen387.domain.model.pilot.mapper.PilotInputMapper;
import org.soen387.domain.model.player.IPlayer;
import org.soen387.domain.model.team.ITeam;

public class PilotProxy extends DomainObjectProxy<Long, Pilot> implements IPilot {

	public PilotProxy(long id) {
		super(id);
	}

	@Override
	protected Pilot getFromMapper(Long id) throws MapperException,
			DomainObjectCreationException {
		try {
			return PilotInputMapper.find(id);
		} catch (Exception e) {
			// It better be here! That null won't go over well!
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
	public List<ITeam> getTeams() {
		return getInnerObject().getTeams();
	}

	@Override
	public void setTeams(List<ITeam> teams) {
		getInnerObject().setTeams(teams);
	}

	@Override
	public void addTeam(ITeam team) {
		getInnerObject().addTeam(team);
	}

	@Override
	public void removeTeam(ITeam team) {
		getInnerObject().removeTeam(team);
	}

}

package org.soen387.domain.model.pilot;

import java.util.List;

import org.dsrg.soenea.domain.interf.IDomainObject;
import org.soen387.domain.model.player.IPlayer;
import org.soen387.domain.model.team.ITeam;

public interface IPilot extends IDomainObject<Long>{

	public abstract String getName();

	public abstract void setName(String name);

	public abstract IPlayer getPlayer();

	public abstract void setPlayer(IPlayer player);
		
	public abstract List<ITeam> getTeams();
	
	public abstract void setTeams(List<ITeam> teams);
	
	public abstract void addTeam(ITeam team);
	
	public abstract void removeTeam(ITeam team);

}
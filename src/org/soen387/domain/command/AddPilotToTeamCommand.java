package org.soen387.domain.command;

import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.command.impl.annotation.SetInRequestAttribute;
import org.dsrg.soenea.domain.command.validator.source.impl.PermalinkSource;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.user.User;
import org.soen387.domain.model.pilot.Pilot;
import org.soen387.domain.model.pilot.mapper.PilotInputMapper;
import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.player.mapper.PlayerInputMapper;
import org.soen387.domain.model.team.Team;
import org.soen387.domain.model.team.mapper.TeamInputMapper;
import org.soen387.domain.model.team.tdg.TeamMembershipTDG;

public class AddPilotToTeamCommand extends ValidatorCommand {

	public AddPilotToTeamCommand(Helper helper) {
		super(helper);
	}

	@SetInRequestAttribute
	public Team teamId;
	
	@Override
	public void process() throws CommandException {
		try {
			PermalinkSource ps = new PermalinkSource();
			int teamId = ps.getData(helper, Integer.class, "teamId");
			User u = (User) helper.getSessionAttribute("CurrentUser");
			Player p = PlayerInputMapper.find(u);
			
			// get the pilot id
			long pilotId = helper.getLong("pilot");

			if (p != null) {
				
				Team team = TeamInputMapper.find(teamId);
				Pilot pilot = PilotInputMapper.find(pilotId);
			
				// Add pilot to team, add team to pilot
				pilot.addTeam(team);
				team.addPilot(pilot);
				
				TeamMembershipTDG.insert(pilotId, teamId);
				helper.setRequestAttribute("team", team);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException();
		}
	}
	
}

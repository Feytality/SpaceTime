package org.soen387.domain.command;

import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.command.impl.annotation.SetInRequestAttribute;
import org.dsrg.soenea.domain.command.validator.source.impl.PermalinkSource;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.user.User;
import org.soen387.domain.model.pilot.IPilot;
import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.player.mapper.PlayerInputMapper;
import org.soen387.domain.model.team.Team;
import org.soen387.domain.model.team.mapper.TeamInputMapper;

public class ViewTeamCommand extends ValidatorCommand {

	public ViewTeamCommand(Helper helper) {
		super(helper);
	}

	@SetInRequestAttribute
	public Team teamId;
	
	@Override
	public void process() throws CommandException {
		try {
			
			PermalinkSource ps = new PermalinkSource();
			int id = ps.getData(helper, Integer.class, "teamId");
			User u = (User) helper.getSessionAttribute("CurrentUser");
			Player p = PlayerInputMapper.find(u);
			System.out.println("Team id : " + id);
			
			if (p != null) {
				
				Team team = TeamInputMapper.find(id);
				System.out.println("TEAM MEMBERS: " );
				for(IPilot pi : team.getMembers()) {
					System.out.println(pi);
				}
				
				helper.setRequestAttribute("team", team);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException();
		}
	}
}

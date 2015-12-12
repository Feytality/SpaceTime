package org.soen387.domain.command;

import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.command.impl.annotation.SetInRequestAttribute;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.user.User;
import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.player.mapper.PlayerInputMapper;
import org.soen387.domain.model.team.Team;
import org.soen387.domain.model.team.mapper.TeamOutputMapper;
import org.soen387.domain.model.team.tdg.TeamTDG;
import org.soen387.ser.name.NameFactory;

public class AddTeamCommand extends ValidatorCommand {

	public AddTeamCommand(Helper helper) {
		super(helper);
	}

	@SetInRequestAttribute
	public Player p;
	public User u;

	@Override
	public void process() throws CommandException {
		try {
			User u = (User) helper.getSessionAttribute("CurrentUser");
			Player p = PlayerInputMapper.find(u);

			if (p != null) {
				long version = 1;
				Team team = new Team(TeamTDG.getMaxId(), version,
						NameFactory.getName(), p);
				TeamOutputMapper.insertStatic(team);
				helper.setRequestAttribute("team", team);
			} else {
				throw new Exception(
						"Must be logged in to view a list of teams!");
			}

		} catch (Exception e) {
			throw new CommandException(e.getMessage());
		}
	}
}

package org.soen387.domain.command;

import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.command.impl.annotation.SetInRequestAttribute;
import org.dsrg.soenea.domain.command.validator.source.impl.PermalinkSource;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.user.User;
import org.soen387.domain.model.pilot.Pilot;
import org.soen387.domain.model.pilot.mapper.PilotInputMapper;
import org.soen387.domain.model.pilot.mapper.PilotOutputMapper;
import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.player.mapper.PlayerInputMapper;

public class FirePilotCommand extends ValidatorCommand {

	public FirePilotCommand(Helper helper) {
		super(helper);
	}

	@SetInRequestAttribute
	public Pilot pilotId;
	
	@Override
	public void process() throws CommandException {
		try {
			PermalinkSource ps = new PermalinkSource();
			int id = ps.getData(helper, Integer.class, "pilotId");
			User u = (User) helper.getSessionAttribute("CurrentUser");
			Player p = PlayerInputMapper.find(u);		

			if (p != null) {
				Pilot pilot = PilotInputMapper.find(id);
				System.out.println(pilot.getPlayer());
				System.out.println(pilot.getId());
				PilotOutputMapper.deleteStatic(pilot);
				System.out.println("deleted");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException();
		}
	}
	
}

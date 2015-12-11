package org.soen387.domain.command;

import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.command.impl.annotation.SetInRequestAttribute;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.user.User;
import org.soen387.domain.model.pilot.Pilot;
import org.soen387.domain.model.pilot.mapper.PilotOutputMapper;
import org.soen387.domain.model.pilot.tdg.PilotTDG;
import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.player.mapper.PlayerInputMapper;
import org.soen387.ser.name.NameFactory;

public class RecruitPilotCommand extends ValidatorCommand {

	public RecruitPilotCommand(Helper helper) {
		super(helper);
	}

	@SetInRequestAttribute
	public Pilot pilot;
	
	@Override
	public void process() throws CommandException {
		try {	
			//Do Stuff
			User u = (User)helper.getSessionAttribute("CurrentUser");
			Player p = PlayerInputMapper.find(u);
			
			if(p != null) {
				Pilot pilot = new Pilot(PilotTDG.getMaxId(), 1, NameFactory.getName(), p);
				PilotOutputMapper.insertStatic(pilot);
				helper.setRequestAttribute("pilot", pilot);
			} else {
				throw new Exception("Must be logged in to list pilots!");
			}
				
		} catch (Exception e1) {
			throw new CommandException(e1.getMessage());
		}
	}
}

package org.soen387.domain.command;

import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.command.impl.annotation.SetInRequestAttribute;
import org.dsrg.soenea.domain.command.validator.source.IdentityBasedProducer;
import org.dsrg.soenea.domain.command.validator.source.Source;
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

	/*
	 * 
	 * Firstly, the fields are public to make the reflection stuff easier! I'm
	 * sure there's a better way, but I haven't gone back to fix it in years.
	 * 
	 * We want to pull from the capture group in PermaLink.xml. The attribute
	 * name is the key for the source, but since it's the name of the command
	 * field, ValidatorCommand assumes. SetInRequestAttribute is
	 * self-explanatory.
	 */
	@Source(sources={PermalinkSource.class})
	@IdentityBasedProducer(mapper = PilotInputMapper.class)
	@SetInRequestAttribute
	public long id;

	@Override
	public void process() throws CommandException {
		try {
			User u = (User) helper.getSessionAttribute("CurrentUser");
			Player p = PlayerInputMapper.find(u);
			
			System.out.println("###################  " + id);

			if (p != null) {
				Pilot pilot = PilotInputMapper.find(id);
				PilotOutputMapper.deleteStatic(pilot);
			} else {
				throw new Exception("Must be logged in to list pilots!");
			}

		} catch (Exception e) {
			throw new CommandException();
		}
	}

	@Override
	public void setUp() throws CommandException {
		// TODO Auto-generated method stub

	}

	@Override
	public void tearDown() throws CommandError {
		// TODO Auto-generated method stub

	}
}

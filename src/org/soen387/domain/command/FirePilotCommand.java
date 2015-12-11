package org.soen387.domain.command;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.command.impl.annotation.SetInRequestAttribute;
import org.dsrg.soenea.domain.command.validator.source.IdentityBasedProducer;
import org.dsrg.soenea.domain.command.validator.source.Source;
import org.dsrg.soenea.domain.command.validator.source.impl.PermalinkSource;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;
import org.dsrg.soenea.domain.user.User;
import org.dsrg.soenea.uow.MissingMappingException;
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
	@Source(sources=PermalinkSource.class)
	//@IdentityBasedProducer(mapper=PilotInputMapper.class)
	public Pilot id;
	

	@Override
	public void process() throws CommandException {
		try {
			PermalinkSource ps = new PermalinkSource();
			int id = ps.getData(helper, Integer.class, "id");
			User u = (User) helper.getSessionAttribute("CurrentUser");
			Player p = PlayerInputMapper.find(u);
			System.out.println(p);
			
			System.out.println("###################  " + id);

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

	@Override
	public void setUp() throws CommandException {
		// TODO Auto-generated method stub

	}

	@Override
	public void tearDown() throws CommandError {
		// TODO Auto-generated method stub

	}
}

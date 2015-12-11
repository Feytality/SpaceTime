package org.soen387.domain.command;

import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.command.impl.annotation.SetInRequestAttribute;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.uow.MissingMappingException;
import org.soen387.domain.model.pilot.IPilot;
import org.soen387.domain.model.pilot.mapper.PilotInputMapper;

public class ListPilotsCommand extends ValidatorCommand {

	public ListPilotsCommand(Helper helper) {
		super(helper);
	}

	@SetInRequestAttribute
	public List<IPilot> pilots;
	
	@Override
	public void process() throws CommandException {
		try {
			//int page = 
			//int rows = 
			
			//System.out.println("PAGE: " + page);
			//System.out.println("ROWS: " + rows);
			
			List<IPilot> pilots = PilotInputMapper.findAll();
			helper.setRequestAttribute("pilots", pilots);
				
		} catch (MissingMappingException | MapperException e1) {
			throw new CommandException();
		}
	}
}

package org.soen387.domain.command;

import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.user.User;
import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.player.mapper.PlayerInputMapper;
import org.soen387.domain.model.player.mapper.PlayerOutputMapper;

public class DeletePlayerCommand extends ValidatorCommand {

	public DeletePlayerCommand(Helper helper) {
		super(helper);
	}

	
	@Override
	public void process() throws CommandException {
		try {	
			//Do Stuff
			System.out.println("ASKFJSDKLGJDSLG");
			User u = (User)helper.getSessionAttribute("CurrentUser");
			Player p = PlayerInputMapper.find(u);
			
			long version = helper.getLong("version");
			
			if(p != null) {
				if (p.getVersion() == version) {
					p.setFirstName("del_" + p.getId());
					PlayerOutputMapper.updateStatic(p);
				} else {
					throw new Exception("Lost update occurred, cannot delete player");
				}
			} else {
				throw new Exception("Must be logged in to list pilots!");
			}
				
		} catch (Exception e1) {
			throw new CommandException(e1.getMessage());
		}
	}
}

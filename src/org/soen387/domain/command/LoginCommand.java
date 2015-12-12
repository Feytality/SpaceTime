package org.soen387.domain.command;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.command.impl.annotation.SetInRequestAttribute;

import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.user.User;
import org.dsrg.soenea.domain.user.mapper.UserInputMapper;
import org.dsrg.soenea.uow.MissingMappingException;
import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.player.mapper.PlayerInputMapper;

public class LoginCommand extends ValidatorCommand {

	public LoginCommand(Helper helper) {
		super(helper);
	}

	@SetInRequestAttribute
	public Player p;
	public User u;
	
	@Override
	public void process() throws CommandException {
		try {
			String username = helper.getString("username");
			String password = helper.getString("password");
			User u = UserInputMapper.find(username, password);
			Player p = PlayerInputMapper.find(u);
			
			helper.setRequestAttribute("player", p);
			helper.setRequestAttribute("user", u);
			
			helper.setSessionAttribute("CurrentUser", UserInputMapper.find(username, password));
				
		} catch (MissingMappingException | SQLException | MapperException e1) {
			throw new CommandException();
		}
	}
}

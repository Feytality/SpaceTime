package org.soen387.domain.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.command.impl.annotation.SetInRequestAttribute;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.role.IRole;
import org.dsrg.soenea.domain.role.impl.GuestRole;
import org.dsrg.soenea.domain.user.User;
import org.dsrg.soenea.domain.user.UserFactory;
import org.dsrg.soenea.uow.MissingMappingException;
import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.player.PlayerFactory;
import org.soen387.domain.model.role.RegisteredRole;

public class RegisterCommand extends ValidatorCommand{

	public RegisterCommand(Helper helper) {
		super(helper);
	}

	@SetInRequestAttribute
	public Player p;
	public User u;
	
	@Override
	public void process() throws CommandException {
		
		String username = (String)helper.getString("username");
		String password = (String)helper.getString("password");
		String first = (String)helper.getString("firstName");
		String last = (String)helper.getString("lastName");
		String email = (String)helper.getString("email");
		
		System.out.println("ATTRIBUTES: " + username + password + first + last + email);
		
		try {
		List<IRole> roles = new ArrayList<IRole>();
		roles.add(new GuestRole());
		roles.add(new RegisteredRole());
		User u = UserFactory.createNew(username, password, roles);
		Player p = PlayerFactory.createNew(first, last, email, u);
		
		//PlayerOutputMapper.insertStatic(p);
		
		System.out.println("USER: " + u.getUsername());
		System.out.println("PLAYER: " + p.getFirstName());
		
		helper.setRequestAttribute("player", p);
		helper.setRequestAttribute("user", u);
		
		} catch (MapperException | MissingMappingException | SQLException e) {
			e.printStackTrace();
		}
		
	}

}
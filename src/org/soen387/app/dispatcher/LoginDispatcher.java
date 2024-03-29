package org.soen387.app.dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.domain.command.CommandException;
import org.soen387.domain.command.LoginCommand;

public class LoginDispatcher extends Dispatcher {

	@Override
	public void execute() throws ServletException, IOException {
		try {
			new LoginCommand(myHelper).execute();
			forward("/WEB-INF/jsp/xml/login.jsp");
		} catch (CommandException e) {
			e.printStackTrace();
			forward("/WEB-INF/jsp/xml/failure.jsp");
		}

	}

}

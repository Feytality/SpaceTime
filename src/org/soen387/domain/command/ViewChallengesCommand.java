package org.soen387.domain.command;

import java.util.List;

import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.command.impl.annotation.SetInRequestAttribute;
import org.dsrg.soenea.domain.command.validator.source.impl.PermalinkSource;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.user.User;
import org.soen387.domain.model.challenge.IChallenge;
import org.soen387.domain.model.challenge.mapper.ChallengeInputMapper;
import org.soen387.domain.model.challenge.tdg.ChallengeFinder;
import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.player.mapper.PlayerInputMapper;

public class ViewChallengesCommand extends ValidatorCommand {

	public ViewChallengesCommand(Helper helper) {
		super(helper);
	}

	@SetInRequestAttribute
	public int page;
	
	@SetInRequestAttribute
	public int rows;
	
	@Override
	public void process() throws CommandException {
		try {
			PermalinkSource ps = new PermalinkSource();
			int page = ps.getData(helper, Integer.class, "page");
			int rows = ps.getData(helper, Integer.class, "rows");
			System.out.println("PRAGE " +page);
			System.out.println("rows " + rows );
			// Get the current player
			User u = (User) helper.getSessionAttribute("CurrentUser");
			Player p = PlayerInputMapper.find(u);
			
			if (p != null) {
				List<IChallenge> challenges = ChallengeInputMapper.find(page, rows);
				for(IChallenge c : challenges) {
					System.out.println("Challengee " + c.getChallengee());
				}
				helper.setRequestAttribute("challenges", challenges);
				helper.setRequestAttribute("pageInt", page);
				
				helper.setRequestAttribute("max", ChallengeInputMapper.count());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException();
		}
	}
	
}
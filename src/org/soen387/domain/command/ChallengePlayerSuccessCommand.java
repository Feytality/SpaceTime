package org.soen387.domain.command;

import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.command.impl.annotation.SetInRequestAttribute;
import org.dsrg.soenea.domain.command.validator.source.impl.PermalinkSource;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.user.User;
import org.soen387.domain.model.challenge.Challenge;
import org.soen387.domain.model.challenge.ChallengeFactory;
import org.soen387.domain.model.challenge.ChallengeStatus;
import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.player.mapper.PlayerInputMapper;

public class ChallengePlayerSuccessCommand extends ValidatorCommand {

	public ChallengePlayerSuccessCommand(Helper helper) {
		super(helper);
	}

	@SetInRequestAttribute
	public Player playerId;
	
	@Override
	public void process() throws CommandException {
		try {
			PermalinkSource ps = new PermalinkSource();
			int challengeeId = ps.getData(helper, Integer.class, "playerId");
			
			// Get the current player
			User u = (User) helper.getSessionAttribute("CurrentUser");
			Player challenger = PlayerInputMapper.find(u);

			if (challenger != null) {
				Player challengee = PlayerInputMapper.find(challengeeId);
				Challenge challenge = ChallengeFactory.createNew(challenger, challengee, ChallengeStatus.Open);
				
				helper.setRequestAttribute("challenge", challenge);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException();
		}
	}
	
}
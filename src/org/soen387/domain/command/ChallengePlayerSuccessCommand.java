package org.soen387.domain.command;

import java.util.List;

import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.command.impl.annotation.SetInRequestAttribute;
import org.dsrg.soenea.domain.command.validator.source.impl.PermalinkSource;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.user.User;
import org.soen387.domain.model.challenge.Challenge;
import org.soen387.domain.model.challenge.ChallengeFactory;
import org.soen387.domain.model.challenge.ChallengeStatus;
import org.soen387.domain.model.challenge.IChallenge;
import org.soen387.domain.model.challenge.mapper.ChallengeInputMapper;
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
				List<IChallenge> challenges = ChallengeInputMapper.findAll();
				boolean isSameChallengee = false;
				boolean alreadyChallenged = false;
				for (IChallenge c : challenges) {
					System.out.println("STATUS!! " + c.getStatus());
					if (c.getChallengee().getId() == challengee.getId() && c.getStatus().ordinal() != ChallengeStatus.Refused.ordinal())
						isSameChallengee = true;
					if (c.getChallengee().getId() == challenger.getId())
						alreadyChallenged = true;
				}
				if (!isSameChallengee) {
					if (!alreadyChallenged) {
						Challenge challenge = ChallengeFactory.createNew(
								challenger, challengee, ChallengeStatus.Issued);

						helper.setRequestAttribute("challenge", challenge);
					} else {
						throw new CommandException(
								"Cannot challenge someone who has challenged you!");
					}
				} else {
					throw new CommandException(
							"Cannot challenge the same player twice!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException();
		}
	}

}
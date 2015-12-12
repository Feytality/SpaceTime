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
import org.soen387.domain.model.challenge.mapper.ChallengeInputMapper;
import org.soen387.domain.model.challenge.mapper.ChallengeOutputMapper;
import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.player.mapper.PlayerInputMapper;

public class RespondToChallengeCommand extends ValidatorCommand {

	public RespondToChallengeCommand(Helper helper) {
		super(helper);
	}

	@SetInRequestAttribute
	public Challenge challengeId;

	@Override
	public void process() throws CommandException {
		try {
			PermalinkSource ps = new PermalinkSource();
			int challengeId = ps.getData(helper, Integer.class, "challengeId");

			// Get the current player
			User u = (User) helper.getSessionAttribute("CurrentUser");
			Player p = PlayerInputMapper.find(u);

			int status = helper.getInt("status");
			long version = helper.getLong("version");

			if (p != null) {
				Challenge challenge = ChallengeInputMapper.find(challengeId);
				ChallengeStatus challengeStatus = ChallengeStatus.values()[status];
				challenge.setStatus(challengeStatus);
				ChallengeFactory.createClean(challenge.getId(), version,
						challenge.getChallenger(), challenge.getChallengee(),
						challengeStatus);
				ChallengeOutputMapper.updateStatic(challenge);
				helper.setRequestAttribute("challenge", challenge);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException();
		}
	}

}
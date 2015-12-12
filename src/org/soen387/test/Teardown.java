package org.soen387.test;

import java.sql.SQLException;

import org.dsrg.soenea.service.tdg.UserTDG;
import org.soen387.app.AbstractPageController;
import org.soen387.domain.model.challenge.tdg.ChallengeTDG;
import org.soen387.domain.model.notification.tdg.NotificationTDG;
import org.soen387.domain.model.pilot.tdg.PilotTDG;
import org.soen387.domain.model.player.tdg.PlayerTDG;
import org.soen387.domain.model.team.tdg.TeamMembershipTDG;
import org.soen387.domain.model.team.tdg.TeamTDG;

public class Teardown {

	public static void main(String[] args) throws InterruptedException {
		boolean noDebug = true;
		AbstractPageController.setupDb();
		if (noDebug) {
			try {
				PlayerTDG.dropTable();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				UserTDG.dropTable();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				UserTDG.dropUserRoleTable();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				TeamTDG.dropTable();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				PilotTDG.dropTable();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				TeamMembershipTDG.dropTable();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				ChallengeTDG.dropTable();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				NotificationTDG.dropTable();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}

package org.soen387.app;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.application.servlet.DispatcherServlet;
import org.dsrg.soenea.application.servlet.Servlet;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.user.User;
import org.dsrg.soenea.domain.user.mapper.UserOutputMapper;
import org.dsrg.soenea.service.MySQLConnectionFactory;
import org.dsrg.soenea.service.registry.Registry;
import org.dsrg.soenea.service.threadLocal.DbRegistry;
import org.dsrg.soenea.service.threadLocal.ThreadLocalTracker;
import org.dsrg.soenea.uow.MapperFactory;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;
import org.soen387.domain.model.challenge.Challenge;
import org.soen387.domain.model.challenge.mapper.ChallengeOutputMapper;
import org.soen387.domain.model.match.Match;
import org.soen387.domain.model.match.mapper.MatchOutputMapper;
import org.soen387.domain.model.notification.Notification;
import org.soen387.domain.model.notification.mapper.NotificationOutputMapper;
import org.soen387.domain.model.pilot.Pilot;
import org.soen387.domain.model.pilot.mapper.PilotOutputMapper;
import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.player.mapper.PlayerInputMapper;
import org.soen387.domain.model.player.mapper.PlayerOutputMapper;
import org.soen387.domain.model.team.Team;
import org.soen387.domain.model.team.mapper.TeamOutputMapper;

/**
 * Servlet implementation class PageController
 */
@WebServlet("/PageController")
public abstract class AbstractPageController extends Servlet {
	private static final long serialVersionUID = 1L;
    private static boolean DBSetup = false;
    /**
     * @see DispatcherServlet#DispatcherServlet()
     */
    public AbstractPageController() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	AbstractPageController.setupDb();
    	MapperFactory m = new MapperFactory();
    	m.addMapping(User.class, UserOutputMapper.class);
    	m.addMapping(Player.class, PlayerOutputMapper.class);
    	m.addMapping(Pilot.class, PilotOutputMapper.class);
    	m.addMapping(Team.class, TeamOutputMapper.class);
    	m.addMapping(Challenge.class,  ChallengeOutputMapper.class);
    	m.addMapping(Notification.class, NotificationOutputMapper.class);
    	m.addMapping(Match.class, MatchOutputMapper.class);
    	UoW.initMapperFactory(m);
    };

    public static synchronized void setupDb() {
    	if(!DBSetup) {
    		prepareDbRegistry();
    	}
    }
    
	public static void prepareDbRegistry() {
		MySQLConnectionFactory f = new MySQLConnectionFactory(null, null, null, null);
		try {
			f.defaultInitialization();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DbRegistry.setConFactory(f);
		String tablePrefix;
		try {
			tablePrefix = Registry.getProperty("mySqlTablePrefix");
		} catch (Exception e1) {
			e1.printStackTrace();
			tablePrefix = "";
		}
		if(tablePrefix == null) {
			tablePrefix = "";
		}
		DbRegistry.setTablePrefix(tablePrefix);
	}
	
	public static void setupRequest(HttpServletRequest request) {
		try { //Make sure we've started a transaction
			DbRegistry.getDbConnection().setAutoCommit(false);
		} catch (SQLException e) {
			// eat the sqlexception, but throw a stacktrace to console
			e.printStackTrace();
		}
		UoW.newCurrent();
		try {
			Long pid = (Long)request.getSession(true).getAttribute("playerid");
			System.out.println("You are playing with player: " + pid);
			if(pid != null) {
				request.setAttribute("CurrentPlayer", PlayerInputMapper.find(pid));
			}
		} catch (SQLException | MissingMappingException | MapperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void teardownRequest() {
		try {
			DbRegistry.closeDbConnectionIfNeeded();
			ThreadLocalTracker.purgeThreadLocal();
		} catch (SQLException e) {
			// eat the sqlexception, but throw a stacktrace to console
			e.printStackTrace();
		}
	}
	
	public Player getCurrentPlayer(HttpServletRequest request) {
		return (Player)request.getAttribute("CurrentPlayer");
	}
	
	public void forwardError(HttpServletRequest request,
			HttpServletResponse response, String error) throws ServletException, IOException {
		request.setAttribute("errorMessage", error);
		request.getRequestDispatcher("/WEB-INF/jsp/xml/failure.jsp").forward(request, response);
	}


}

package org.soen387.domain.model.challenge.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;
import org.soen387.domain.model.challenge.Challenge;
import org.soen387.domain.model.challenge.ChallengeFactory;
import org.soen387.domain.model.challenge.ChallengeStatus;
import org.soen387.domain.model.challenge.IChallenge;
import org.soen387.domain.model.challenge.tdg.ChallengeFinder;
import org.soen387.domain.model.player.PlayerProxy;

public class ChallengeInputMapper {
	public static Challenge find(long id) throws SQLException, MissingMappingException, MapperException {

		if(IdentityMap.has(id, Challenge.class)) {
			return IdentityMap.get(id, Challenge.class);
		}

		ResultSet rs = ChallengeFinder.find(id);
		if(rs.next()) {
			Challenge c = buildChallenge(rs);
			rs.close();
			UoW.getCurrent().registerClean(c);
			return c;
		}
		throw new DomainObjectNotFoundException("Could not create a Challenge with id \""+id+"\"");
	}
	
	public static int count() throws SQLException {
		ResultSet rs = ChallengeFinder.countRows();
		if(rs.next()) {
			return rs.getInt("total");
		}
		throw new SQLException();
	}
	
	public static List<IChallenge> find(int page, int rows) throws MapperException {
        try {
            ResultSet rs = ChallengeFinder.findByPageRows(page, rows);
            return buildCollection(rs);
        } catch (SQLException e) {
            throw new MapperException(e);
        }
	}
	
	public static List<IChallenge> findAll() throws MapperException {
        try {
            ResultSet rs = ChallengeFinder.findAll();
            return buildCollection(rs);
        } catch (SQLException e) {
            throw new MapperException(e);
        }
	}

	public static List<IChallenge> buildCollection(ResultSet rs)
		    throws SQLException, MissingMappingException, MapperException {
		    ArrayList<IChallenge> l = new ArrayList<IChallenge>();
		    while(rs.next()) {
		    	long id = rs.getLong("id");
		    	Challenge c = null;
		    	if(IdentityMap.has(id, Challenge.class)) {
		    		c = IdentityMap.get(id, Challenge.class);
		    	} else {
		    		c = buildChallenge(rs);
		    		UoW.getCurrent().registerClean(c);
		    	}
		    	l.add(c);
		    }
		    return l;
		}

	
	
	private static Challenge buildChallenge(ResultSet rs) throws SQLException, MissingMappingException, MapperException  {

		// TODO Auto-generated method stub
		return ChallengeFactory.createClean(rs.getLong("id"),
				rs.getInt("version"),
				new PlayerProxy(rs.getLong("challenger")),
				new PlayerProxy(rs.getLong("challengee")),
				ChallengeStatus.valueOf(rs.getString("status"))
				);
	}
}

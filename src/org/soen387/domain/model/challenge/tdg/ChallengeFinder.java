package org.soen387.domain.model.challenge.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class ChallengeFinder {
	public static final String FIND = "SELECT " + ChallengeTDG.COLUMNS + " FROM " + ChallengeTDG.TABLE_NAME + " WHERE id=?;";
	public static ResultSet find(long id) throws SQLException {
    	Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(FIND);
		ps.setLong(1,id);
		return ps.executeQuery();
	}

	public static final String FIND_ALL = "SELECT " + ChallengeTDG.COLUMNS + " FROM " + ChallengeTDG.TABLE_NAME + ";";
	public static ResultSet findAll() throws SQLException {
    	Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(FIND_ALL);
		return ps.executeQuery();
	}
	
	public static final String FIND_BY_PAGE_ROWS = "SELECT " + ChallengeTDG.COLUMNS + " FROM " + ChallengeTDG.TABLE_NAME + " LIMIT ?, ?;";
	public static ResultSet findByPageRows(int page, int rows) throws SQLException {
    	Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(FIND_BY_PAGE_ROWS);
		ps.setInt(1, (page*rows-rows));
		ps.setInt(2, rows);
		return ps.executeQuery();
	}
	
	public static final String COUNT_ROWS = "SELECT COUNT(*) AS total FROM " + ChallengeTDG.TABLE_NAME + ";";
	public static ResultSet countRows() throws SQLException {
    	Connection con = DbRegistry.getDbConnection();
		Statement stmnt = con.createStatement();
		return stmnt.executeQuery(COUNT_ROWS);
	}
	
}

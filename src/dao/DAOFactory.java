package dao;

import db.DBConnection;

public class DAOFactory {

	public static TaskDAO createTaskDAO() {
		return new TaskDAO(DBConnection.getConnection());
	}
}

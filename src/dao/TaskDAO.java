package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import db.DBException;
import model.Task;

public class TaskDAO {
	private Connection conn;

	public TaskDAO(Connection conn) {
		this.conn = conn;
	}
	
	public boolean create(Task task) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("INSERT INTO task(title, completed) VALUES (?, false)");

			pstmt.setString(1, task.getTitle());

			int rowsAffected = pstmt.executeUpdate();

			return rowsAffected > 0;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DBConnection.closeStatement(pstmt);
		}
	}

	public List<Task> read() {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			pstmt = conn.prepareStatement("SELECT * FROM task");
			rset = pstmt.executeQuery();

			List<Task> tasks = new ArrayList<>();

			while (rset.next()) {
				Task task = new Task();

				task.setId(rset.getInt("id"));
				task.setTitle(rset.getString("title"));
				task.setCompleted(rset.getBoolean("completed"));

				tasks.add(task);
			}
			return tasks;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DBConnection.closeStatement(pstmt);
			DBConnection.closeResultSet(rset);
		}
	}
	
	public boolean update(Task task) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("UPDATE task SET Completed = ? WHERE Id = ?");

			pstmt.setBoolean(1, task.getCompleted());
			pstmt.setInt(2, task.getId());

			int rowsAffected = pstmt.executeUpdate();

			return rowsAffected > 0;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DBConnection.closeStatement(pstmt);
		}
	}
	
	public boolean delete(Integer id) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement("DELETE FROM task WHERE Id = ?");

			pstmt.setInt(1, id);

			int rowsAffected = pstmt.executeUpdate();

			return rowsAffected > 0;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DBConnection.closeStatement(pstmt);
		}
	}

}

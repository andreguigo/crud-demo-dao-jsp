package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import dao.TaskDAO;
import db.DBException;
import model.Task;

@WebServlet("/TaskServlet")
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TaskDAO taskDAO;
	
	@Override
    public void init() throws ServletException {
        super.init();
        taskDAO = DAOFactory.createTaskDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		try {
			if ("create".equalsIgnoreCase(action)) {
				createTask(request);
			} else if ("update".equalsIgnoreCase(action)) {
				updateTask(request);
			} else if ("delete".equalsIgnoreCase(action)) {
				deleteTask(request);
			}
		} catch (Exception e) {
			throw new DBException(e.getMessage());
		}
		
		List<Task> tasks = taskDAO.read();
		request.setAttribute("tasks", tasks);
		request.getRequestDispatcher("tasks.jsp").forward(request, response);
	}
	
	private void createTask(HttpServletRequest request) {
		Task task = new Task();
		task.setTitle(request.getParameter("title"));
		task.setCompleted(false);
		taskDAO.create(task);
	}
	
	private void updateTask(HttpServletRequest request) {
        Task task = new Task();
        task.setCompleted(Boolean.parseBoolean(request.getParameter("completed")));
        task.setId(Integer.parseInt(request.getParameter("id")));
        taskDAO.update(task);
    }
	
	private void deleteTask(HttpServletRequest request) {
		int taskId = Integer.parseInt(request.getParameter("id"));
		taskDAO.delete(taskId);
	}
}

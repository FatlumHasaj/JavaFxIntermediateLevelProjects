package controllers;

public class Tasks {
	private int id;
	private String taskName;
	private String taskProgress;
	private String Priority;
	private int userid;

	public Tasks(int id, String taskName, String taskProgress, String taskPriority, int userid) {
		super();
		this.id = id;
		this.taskName = taskName;
		this.taskProgress = taskProgress;
		this.Priority =taskPriority;
		this.userid = userid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskProgress() {
		return taskProgress;
	}

	public void setTaskProgress(String taskProgress) {
		this.taskProgress = taskProgress;
	}

	public String getPriority() {
		return Priority;
	}

	public void setTaskPriority(String taskPriority) {
		this.Priority = taskPriority;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

}

package mobile.wms.beans;

import java.io.Serializable;
import java.util.List;

public class TaskUser implements Serializable {
	private TaskPropriu taskPropriu;
	private List<TaskExtern> alteTaskuri;

	public TaskPropriu getTaskPropriu() {
		return taskPropriu;
	}

	public void setTaskPropriu(TaskPropriu taskPropriu) {
		this.taskPropriu = taskPropriu;
	}

	public List<TaskExtern> getAlteTaskuri() {
		return alteTaskuri;
	}

	public void setAlteTaskuri(List<TaskExtern> alteTaskuri) {
		this.alteTaskuri = alteTaskuri;
	}
}

package www.it.com.activit01;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
/**
 * 流程中不设置办理人通过类指定办理人
 * 但需要在创建bpmn文件时指定监听的类TaskListenerImple
 * @author Administrator
 *
 */
public class TaskListenerImple implements TaskListener  {

	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		String assignee="周芷若";
		// 设置办理人
		delegateTask.setAssignee(assignee);
		
	}

}

package www.it.com.activit01;

import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 流程设置环境变量
 * 
 * @author Administrator
 *
 */
public class Variables01 {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 启动流程
	 */
	@Test
	public void test01() {
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("helloworld");
		System.out.println(processInstance.toString());
	}

	/**
	 * 查询正在运行的流程
	 */
	@Test
	public void test02() {
		List<ProcessInstance> list = processEngine.getRuntimeService().createProcessInstanceQuery().list();
		list.forEach((ProcessInstance -> {
			System.out.println(
					ProcessInstance.getName() + "/t" + ProcessInstance.getBusinessKey() + ProcessInstance.getId());
		}));
	}

	/**
	 * 设置变量
	 */

	@Test
	public void test03() {
		String assignee = "李四";
		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().taskAssignee(assignee) // 指定任务办理人
				.singleResult();
		System.out.println(task.toString());
		// 设置变量
		taskService.setVariable(task.getId(), "请假人", "大神");
		taskService.setVariable(task.getId(), "请假天数", 6);
		taskService.setVariable(task.getId(), "请假日期", new Date());
		// set存入javabean对象前提对象需要序列话
		Student student = new Student();
		student.setAge(10);
		student.setStuName("我笑了");
		taskService.setVariable(task.getId(), "学生信息", student);
	}

	/**
	 * 获取流程变量
	 */

	@Test
	public void test04() {
		String assignee = "李四";
		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().taskAssignee(assignee) // 指定任务办理人
				.singleResult();
		System.out.println(task.toString());
		// 获取流程变量
		String leaveName = (String) taskService.getVariable(task.getId(), "请假人");
		Integer leaveDay = (Integer) taskService.getVariable(task.getId(), "请假天数");
		Student student = (Student) taskService.getVariable(task.getId(), "学生信息");
		System.out.println(leaveName + "\t" + leaveDay + "\t" + student.toString());
	}

	/**
	 * 模拟流程变量的设置和获取的场景
	 */

	@Test
	public void test05() {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();
		// 设置变量的方式
		// 通过runtimeService设置变量
		// runtimeService.setVariable(executionId, variableName, value);
		// runtimeService.setVariables(executionId, variables);
		// 通过taskService设置变量
		// taskService.setVariable(taskId, variableName, value);
		// taskService.setVariables(taskId, variables);
		// 启动流程时设置变量
		// processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey,
		// variables)
		// 完成任务时设置变量
		// taskService.complete(taskId, variables);
	}

	/**
	 * 查询历史的流程变量
	 */
	@Test
	public void test06() {
		// 通过key查询
		HistoricVariableInstance singleResult = processEngine.getHistoryService().createHistoricVariableInstanceQuery()
				.variableName("请假人").singleResult();
		System.out.println(singleResult.getValue() + singleResult.getVariableTypeName());
	}

	/**
	 * 查询历史流程实例 查找按照某个流程定义的规则一共执行了多少次流程
	 */

	@Test
	public void test07() {
		List<HistoricProcessInstance> list = processEngine.getHistoryService().createHistoricProcessInstanceQuery()
				.processDefinitionKey("leaveManage").orderByProcessInstanceStartTime().desc().list();
		list.forEach((HistoricProcessInstance) -> {
			System.out.println(HistoricProcessInstance.getId() + HistoricProcessInstance.getProcessDefinitionId()
					+ HistoricProcessInstance.getDurationInMillis());
		});
	}

}

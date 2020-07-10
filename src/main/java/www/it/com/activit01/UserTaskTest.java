package www.it.com.activit01;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 用户任务（userTask，即用户操作的任务）
 * 
 * @author Administrator
 *
 */
public class UserTaskTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署和启动流程
	 */
	@Test
	public void test01() {
		Deployment deployment = processEngine.getRepositoryService().createDeployment()
				.addClasspathResource("diagrams/UserTaskTest.bpmn").addClasspathResource("diagrams/UserTaskTest.png")
				.name("用户任务").deploy();// 完成部署
		System.out.println(deployment.getId());
	}
	
	/**
	 * 删除流程
	 */
	@Test
	public void test03() {
		processEngine.getRepositoryService().deleteDeployment("77501", true);
	}
	
	/**
	 * 启动流程
	 */
	@Test
	public void test02() {
		//启动流程并指定下一个节点的任务办理人
		/*Map<String, Object> variables=new HashMap<String, Object>();
		variables.put("assignee", "大神");*/
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("userTaskTest");
		System.out.println(processInstance.getId());
	}
	
	/**
	 * 办理任务
	 */
	@Test
	public void test04() {
		//办理人直接在bpmn文件中写死了，这个时候就不需要指定任务的办理人了
		//完成任务指定下一个接单的办理人
		processEngine.getTaskService().complete("95004");
	}
	
	/**
	 * 任务办理从一个人到另一个人，即修改任务办理人
	 */
	@Test
	public void test05() {
		String taskId="95004";
		//现在办理人时周芷若，周芷若不在转交给张无忌
		String userId="张无忌";
		processEngine.getTaskService().setAssignee(taskId, userId);
	}

}

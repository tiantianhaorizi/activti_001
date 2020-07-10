package www.it.com.activit01;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 组任务
 * 
 * @author Administrator
 *
 */
public class GroupTaskTest {

	ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
    /**
     * 部署流程
     */
	@Test
	public void test01() {
	   Deployment deployment = processEngine.getRepositoryService().createDeployment()
	   									   .name("组任务") 
	   									   .addClasspathResource("diagrams/UserTaskTest.bpmn")
	   									   .addClasspathResource("diagrams/UserTaskTest.png")
	   									   .deploy();//完成部署
	   System.out.println(deployment.getId());
	}
	
	/**
	 * 删除流程
	 */
	@Test
	public void test02() {
		processEngine.getRepositoryService().deleteDeployment("", true);
	}
	
	/**
	 * 启动任务 直接在画流程图的时候指定用户组
	 */
	@Test
	public void test03() {
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("userTaskTest");
	}
	
	/**
	 * 查询组任务列表
	 */
	@Test
	public void test04() {
		String userId="小a";
		List<Task> list = processEngine.getTaskService().createTaskQuery().taskCandidateUser(userId).list();
		list.forEach((task)->{
			System.out.println("id="+task.getId());
			System.out.println("name="+task.getName());
			System.out.println("assinee="+task.getAssignee());
			System.out.println("createTime ="+task.getCreateTime());
			System.out.println("executionId="+task.getExecutionId());
			System.out.println("##################################");
		});
	}
	
	/**
	 * 组任务分配给个人任务，拾取任务
	 */
	@Test
	public void test05() {
		String taskId="110004";
		String userId="小a";
		processEngine.getTaskService().claim(taskId, userId);
	}
	
	
	
	/**
	 * 拾取任务之后就会asignee就会是小a
	 */
	@Test
	public void test07() {
		//将个人任务恢复成组任务
		processEngine.getTaskService().setAssignee("110004", null);
	}
	
	
	/**
	 * 给组任务添加成员
	 */
	@Test
	public void test08() {
		processEngine.getTaskService().addCandidateUser("110004", "大神");
	}
	
	/**|
	 * 给组任务删除成员
	 */
	@Test
	public void test09() {
		processEngine.getTaskService().deleteCandidateUser("110004", "大神");
	}
	
	/**
	 * 办理任务
	 */
	@Test
	public void test06() {
		processEngine.getTaskService().complete("105004");
	}
			
}

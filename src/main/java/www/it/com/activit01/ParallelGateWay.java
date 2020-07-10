package www.it.com.activit01;
/**
 * 并排网关
 * @author Administrator
 *
 */

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ParallelGateWay {
	
	ProcessEngine processEngine =ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 流程部署和启动
	 */
	@Test
	public void test01() {
		//部署流程
		Deployment deployment = processEngine.getRepositoryService().createDeployment()
											.addClasspathResource("diagrams/parallelGateWay.bpmn")
											.addClasspathResource("diagrams/parallelGateWay.png")
											.name("并排网关")//设置流程的名字
											.deploy();//完成部署
		System.out.println("流程的id"+deployment.getId());
		System.out.println("流程的名字"+deployment.getName());	
		//根据流程的key启动流程
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("parallerGateWay");
		System.out.println("流程实例的id"+processInstance.getId()+"流程实列的名字"+processInstance.getName());
	}
	
	/**
	 * 查看正在执行的任务
	 */
	
	@Test
	public void test02() {
		String assignee="买家";
		List<Task> list = processEngine.getTaskService().createTaskQuery()
									  .taskAssignee(assignee)//指定任务办理人
									  .list();
		list.forEach((task)->{
			System.out.println(task.getId()+"\t"+task.getName());
		});
	}
	
	/**
	 * 办理任务
	 */
	@Test
	public void test03() {
		 String taskId="50002";
	     processEngine.getTaskService().complete(taskId);
	}
	
	

}

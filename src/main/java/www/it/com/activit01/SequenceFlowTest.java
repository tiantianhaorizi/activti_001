package www.it.com.activit01;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 流程连线
 * 
 * @author Administrator
 *
 */
public class SequenceFlowTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署并启动流程
	 */
	@Test
	public void test() {
		Deployment deployment = processEngine.getRepositoryService().createDeployment()
				.addClasspathResource("diagrams/SequenceFlow.bpmn").addClasspathResource("diagrams/SequenceFlow.png")
				.name("连线").deploy();// 完成部署
		System.out.println(deployment.toString());
		// 启动流程
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("sequenceFlow");
		System.out.println(processInstance.toString());
	}
	
	/**
	 * 查询任务
	 * 
	 */
	@Test
	public void test01() {
		List<Task> list = processEngine.getTaskService().createTaskQuery().processDefinitionKey("sequenceFlow").list();
		list.forEach((aa)->System.out.println(aa.getId()+"\t"+aa.getName()));
	}
	
	/**
	 * 办理任务
	 */
	@Test
	public void test02() {
		//办理任务时指定变量和流程画的一直就会按照流程图指定的线路执行
		Map<String, Object> variables=new HashMap<String, Object>();
		variables.put("meeage", "重要");
		processEngine.getTaskService().complete("30008", variables);
	}
	
	@Test
	public void test03() {
		processEngine.getTaskService().complete("32503");
	}

}

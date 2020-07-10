package www.it.com.activit01;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.cfg.ProcessEngineConfigurator;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 部署流程
 * 
 * @author Administrator
 *
 */
public class Delopment {

	// 创建流程引擎
	ProcessEngine ProcessEngine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void test() {
		// 部署流程  act_re_deployment`   act_re_procdef
		Deployment deployment = ProcessEngine.getRepositoryService().createDeployment().name("我的第一个流程")
				.addClasspathResource("diagrams/helloworld.bpmn").addClasspathResource("diagrams/helloworld.png")
				.deploy();
		System.out.println(deployment.toString());

	}

	@Test
	public void test002() {
		// 启动流程 就会往act_ru_task添加一条数据 任务表
		ProcessInstance processInstance = ProcessEngine.getRuntimeService().startProcessInstanceByKey("helloworld");// 使用流程定义的key启动流程
		System.out.println(processInstance.toString());
	}
	
	/**
	 * 查询我的任务
	 */
	@Test
	public void test003() {
		Task task = ProcessEngine.getTaskService().createTaskQuery().singleResult();
		System.out.println(task.toString());
	}
	
	/**
	 * 完成任务
	 */
	@Test
	public void test004() {
		ProcessEngine.getTaskService().complete("5004");
	}
	
	

	@Test
	public void test01() {
		// 删除流程定义
		ProcessEngine.getRepositoryService().deleteDeployment("1", true);
		// System.out.println(deployment.toString());

	}

}

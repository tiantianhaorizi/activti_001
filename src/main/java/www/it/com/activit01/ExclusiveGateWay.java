package www.it.com.activit01;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
   * 排他网关
 * @author Administrator
 *
 */
public class ExclusiveGateWay {
	
	ProcessEngine processEngine =ProcessEngines.getDefaultProcessEngine();
	
	
   /**
           * 部署和启动流程
    */
	@Test
	public void test01() {
		processEngine.getRepositoryService().createDeployment()
											.addClasspathResource("diagrams/ExclusiveGateWay.bpmn")
											.addClasspathResource("diagrams/ExclusiveGateWay.png")
											.name("排他网关")
											.deploy();//完成部署
		//启动流程
		processEngine.getRuntimeService().startProcessInstanceByKey("ExclusiveGateWay");
	}
	
	/**
	 * 查询任务并办理任务
	 */
	@Test
	public void test02() {
		/*List<Task> list = processEngine.getTaskService().createTaskQuery().processDefinitionKey("ExclusiveGateWay").list();
		list.forEach((aa)->{
			System.out.println(aa.getId()+"\t"+aa.getName());
		});*/
		//办理流程
		Map<String, Object>  variables=new HashMap<String, Object>();
		variables.put("money", 200);
		processEngine.getTaskService().complete("37508", variables);
	}

}

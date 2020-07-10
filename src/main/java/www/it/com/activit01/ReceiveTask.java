package www.it.com.activit01;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 接受活动
 * 
 * @author Administrator
 *
 */
public class ReceiveTask {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署和启动流程
	 */

	@Test
	public void test01() {
		Deployment deployment = processEngine.getRepositoryService().createDeployment()
				.addClasspathResource("diagrams/ReceiveTask.bpmn").addClasspathResource("diagrams/ReceiveTask.png")
				.name("接受活动//等待活动")// 设置流程的名字
				.deploy();// 完成部署

	}

	/**
	 * 启动流程
	 */
	@Test
	public void test03() {
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("receiveTask");
		System.out.println(processInstance.getId());
	}

	/**
	 * 查询和办理接受任务 因为接受任务不是节点任务所以不能使用TaskService(对应的task表没有数据)只能使用RuntimeService 主要操作的是act_ru_execution表
	 */
	@Test
	public void test02() {
		Execution execution = processEngine.getRuntimeService().createExecutionQuery().activityId("汇总当日销售总额")// 定义节点的id
				.singleResult();

		// 设置一个变量，传递整个流程
		Map<String, Object> var = new HashMap<String, Object>();
		var.put("日销售额", 10000);
		// 办理接受任务 让任务向后一步
		processEngine.getRuntimeService().signal(execution.getId(), var);

		// 进入第二个节点
		Execution execution2 = processEngine.getRuntimeService().createExecutionQuery().activityId("给总经理发短信")// 定义节点的id
				.singleResult();

		// 让节点向后一步
		processEngine.getRuntimeService().signal(execution2.getId());
	}

}

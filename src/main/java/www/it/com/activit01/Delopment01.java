package www.it.com.activit01;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;

/**
 * 流程实例和任务的办理
 * 
 * @author Administrator
 *
 */
public class Delopment01 {

	// 创建流程引擎
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署流程定义
	 * 
	 */
	@Test
	public void test01() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		Deployment deployment = processEngine.getRepositoryService().createDeployment().name("helloWord")
				.addZipInputStream(zipInputStream).deploy();// 完成流程的发布
		System.out.println(deployment.toString());
	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void test02() {
		// 根据流程的key启动流程
		// 操作数据库的act_ru_execution表,如果是用户任务节点，同时也会在act_ru_task添加一条记录
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("helloworld");
		System.out.println(processInstance.toString());
	}

	/**
	 * 查看任务
	 */
	@Test
	public void test03() {
		// 查询指定任务人的任务
		String assignee = "张三";
		Task task = processEngine.getTaskService().createTaskQuery().processDefinitionKey("helloworld")
				.taskAssignee(assignee).singleResult();
		System.out.println(task.getAssignee() + task.getId());
	}

	/***
	 * 办理任务
	 */
	@Test
	public void test04() {
		String taskId = "20004";
		processEngine.getTaskService().complete(taskId);

	}

	/**
	 * 查看任务是否办理完成
	 */
	@Test
	public void test05() {
		String processInstanceId = "20001";
		ProcessInstance processInstance = processEngine.getRuntimeService()
														.createProcessInstanceQuery()
														.processInstanceId(processInstanceId)
														.singleResult();
		if(processInstance!=null) {
			System.out.println("办理的任务还没有完成");
		}else {
			System.out.println("任务已经完成");
		}
	}
	
	/**
	 * 查询历史任务
	 */
	@Test
	public void test06() {
		//查询历史任务
		List<HistoricTaskInstance> list = processEngine.getHistoryService().createHistoricTaskInstanceQuery().list();
		list.forEach((aa)->{
			System.out.println(aa.getId()+"\n"+aa.getAssignee());
		});
	}
}

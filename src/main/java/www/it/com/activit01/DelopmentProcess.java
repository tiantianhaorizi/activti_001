package www.it.com.activit01;
/**
 * 管理流程定义
 * @author Administrator
 *
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;import javax.print.attribute.standard.PDLOverrideSupported;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Deployment   部署对象
1、一次部署的多个文件的信息。对于不需要的流程可以删除和修改。
2、对应的表：
  act_re_deployment：部署对象表
  act_re_procdef：流程定义表
  act_ge_bytearray：资源文件表
  act_ge_property：主键生成策略表
 * @author Administrator
 *
 */
public class DelopmentProcess {

	// 获取流程引擎
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署zip格式流程
	 */
	@Test
	public void test01() {
		// 获取classpath路径下的资源
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("diagrams/请假流程.zip");

		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		// 部署流程定义
		Deployment deployment = processEngine.getRepositoryService().createDeployment().name("请假管理")
				.addZipInputStream(zipInputStream).deploy();

		System.out.println(deployment.toString());
	}

	/**
	 * 查询流程定义
	 */
	@Test
	public void test02() {
		/*long count = processEngine.getRepositoryService().createProcessDefinitionQuery().count();
		System.out.println(count);*/
		List<ProcessDefinition> ProcessDefinitionList = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				// .processDefinitionKey("根据流程定义的key查询")
				// .processDefinitionId("根据流程定义的id查询")
				// .processDefinitionName("根据部署流程定义的name查询")
				.list();
		ProcessDefinitionList.forEach((ProcessDefinition) -> {
			System.out.println(processEngine.toString());
		});
	}

	/**
	 * 删除流程定义
	 */
	@Test
	public void test03() {
		String deploymentId = "2501";
		processEngine.getRepositoryService()
				// .deleteDeployment(deploymentId);//根据部署的流程的id删除当有正在运行的任务是会报错
				.deleteDeployment(deploymentId, true);// 这个会级联删除
	}

	/**
	 * 查看流程定义的图片
	 */

	@Test
	public void test04() {
		String deploymentId = "12501";
		// 通过流程定义获取资源文件的name_对应的值
		List<String> resourceNames = processEngine.getRepositoryService().getDeploymentResourceNames(deploymentId);
		// png文件名
		String pngName = null;
		for (String name : resourceNames) {
			if (name.indexOf(".png") >= 0) {
				pngName = name;
			}
		}
		if (StringUtils.isNotEmpty(pngName)) {
			// 创建文件
			File file = new File("d:/" + pngName);
			// 读取文件值文件
			InputStream inputStream = processEngine.getRepositoryService().getResourceAsStream(deploymentId, pngName);
			// 将流拷贝到file文件中
			try {
				FileUtils.copyInputStreamToFile(inputStream, file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * 附加功能：查询最新版本的流程定义
	 */
	@Test
	public void test05() {
		// String processDefinitionKey="leaveManage";
		List<ProcessDefinition> list = processEngine.getRepositoryService().createProcessDefinitionQuery()
				// .processDefinitionKey(processDefinitionKey)
				.orderByProcessDefinitionVersion()// 根据版本进行排序
				.asc().list();
		// 利用LinkedHashMap可以相同会进行覆盖的特性
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (ProcessDefinition processDefinition : list) {
			map.put(processDefinition.getKey(), processDefinition);
		}

		// 循环map的value值
		for (Object processDefinition : (map.values())) {
			ProcessDefinition pf = (ProcessDefinition) processDefinition;
			System.out.println(pf.getId()+pf.getName()+pf.getVersion());
		}

	}

}

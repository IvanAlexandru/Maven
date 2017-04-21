package UnitTesting;

import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import Service.Batch;

@RunWith(MockitoJUnitRunner.class)
public class BatchTest {
	String[] springConfig = {"job.xml"};
	@InjectMocks
	private Batch batch;
	@Mock
	ApplicationContext context;
	@Mock
	private JobLauncher jobLauncher;
	@Mock
	private Job job ;
	@Mock
	private JobParameters jobParameters;
	
	@Before
	public void setUp(){
		batch = new Batch();
		context = new ClassPathXmlApplicationContext(springConfig);
		jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		job = (Job) context.getBean("job1");
	}
	
	@Test
	public void testBatch(){
		Assert.assertEquals(batch.writeBooksbyAutorToXML("Java Master"), "Cartile autorului au fost scrise in XML!");
	}
}

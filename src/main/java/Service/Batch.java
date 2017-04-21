package Service;

import java.util.ArrayList;

import javax.print.attribute.standard.JobName;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import Model.Carte;

public class Batch {

	public String writeBooksbyAutorToXML( String autor) {
		String[] springConfig = {"job.xml"};
		ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("job1");
		JobParameters jobParameters = new JobParametersBuilder().addString("autor", autor).toJobParameters();
		try {
			JobExecution execution = jobLauncher.run(job, jobParameters);
			System.out.println(execution);
			System.out.println("Exit status: "+execution.getExitStatus());
			if((""+execution.getExitStatus()).contains("COMPLETED")){
				return "Cartile autorului au fost scrise in XML!";
			}else{
				return "Ooops, se pare ca a aparut o eroare!";
			}
		} catch (JobExecutionAlreadyRunningException | JobRestartException
				| JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			e.printStackTrace();
			return "Ooops, se pare ca a aparut o eroare!";
		}
	}

}

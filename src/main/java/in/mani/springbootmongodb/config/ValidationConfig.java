package in.mani.springbootmongodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {
	//this will trigger before data persistance happnens to database, 
	//i,e its checks for nulls values if it does then it throws constraintViloation Exception
	@Bean
	public ValidatingMongoEventListener ValidatingMongoEventListener() {
		return new ValidatingMongoEventListener(validator());
	}
	
	//LocalValidatorFactoryBean is implemntation class for Validation
	@Bean
	public LocalValidatorFactoryBean validator()
	{
		return new LocalValidatorFactoryBean();
	}
	
	//overall those two beans we registered is for validation purpose
}

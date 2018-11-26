package app.ccb.config;

import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public FileUtil fileUtil() {
        // TODO : Implement Me
//        return new FileUtilImpl();
        return null;
    }

    @Bean
    public ValidationUtil validationUtil() {
        // TODO : Implement Me
        //        return new ValidationUtilImpl();
        return null;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

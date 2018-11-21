package app.config;

import app.dto.EmployeeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    private static ModelMapper mapper;

    static {
        mapper = new ModelMapper();

        EmployeeDTO.initMaps(mapper);

        mapper.validate();
    }

    @Bean
    public ModelMapper modelMapper() {
        return mapper;
    }
}

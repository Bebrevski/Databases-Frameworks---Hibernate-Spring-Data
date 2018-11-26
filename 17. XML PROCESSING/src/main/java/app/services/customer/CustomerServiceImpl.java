package app.services.customer;

import app.domain.dtos.cutomer.CustomerImportDto;
import app.domain.dtos.cutomer.CustomerImportRootDto;
import app.domain.entities.Customer;
import app.repositories.CustomerRepository;
import app.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void importCustomers(CustomerImportRootDto customerImportRootDto) {
        for (CustomerImportDto customerImportDto : customerImportRootDto.getCustomerImportDtos()) {
            if(!this.validationUtil.isValid(customerImportDto)) {
                System.out.println("Invalid data!");

                continue;
            }

            Customer entity = this.modelMapper.map(customerImportDto, Customer.class);
            entity.setBirthDate(LocalDate.parse());

            this.customerRepository.saveAndFlush(entity);
        }
    }
}

package app.services.addresses;

import app.entities.Address;
import app.repositories.AddressRepository;
import app.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AddressesServiceImpl implements AddressesService{
    private static final String CATEGORIES_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\14. SPRING DATA AUTO MAPPING OBJECTS\\src\\main\\resources\\files\\addresses.txt";
    private final AddressRepository addressRepository;
    private final FileUtil fileUtil;

    public AddressesServiceImpl(AddressRepository addressRepository, FileUtil fileUtil) {
        this.addressRepository = addressRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAddresses() throws IOException {
        if (this.addressRepository.count() != 0) {
            return;
        }

        String[] addressFileContent = this.fileUtil.getFileContent(CATEGORIES_FILE_PATH);
        for (String line : addressFileContent) {
            String[] args = line.split("\\s+");

            Address address = new Address();
            address.setTown(args[0]);
            address.setCountry(args[1]);

            this.addressRepository.saveAndFlush(address);
        }
    }
}

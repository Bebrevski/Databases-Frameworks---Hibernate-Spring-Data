package app.services.addresses;

import app.repositories.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressesServiceImpl implements AddressesService{
    private final AddressRepository addressRepository;

    public AddressesServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

}

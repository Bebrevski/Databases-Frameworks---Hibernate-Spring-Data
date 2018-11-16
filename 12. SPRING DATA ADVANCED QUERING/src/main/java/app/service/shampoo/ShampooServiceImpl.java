package app.service.shampoo;

import app.domain.entities.Size;
import app.repository.shampoo.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService {

    private final ShampooRepository shampooRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<String> selectShampoosBySize(String inputSize) {
        Size size = Size.valueOf(inputSize.toUpperCase());

        return this.shampooRepository.findAllBySize(size).stream()
                .map(s -> String.format("%s %s %.2flv."
                        , s.getBrand()
                        , s.getSize().name()
                        , s.getPrice()))
                .collect(Collectors.toList());
    }
}

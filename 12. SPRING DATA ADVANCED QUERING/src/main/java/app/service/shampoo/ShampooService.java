package app.service.shampoo;

import java.util.List;

public interface ShampooService {

    List<String> selectShampoosBySize(String inputSize);
}

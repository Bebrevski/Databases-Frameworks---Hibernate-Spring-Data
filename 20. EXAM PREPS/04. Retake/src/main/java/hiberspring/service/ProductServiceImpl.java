package hiberspring.service;

import hiberspring.common.Constants;
import hiberspring.domain.dtos.product.ProductImportDto;
import hiberspring.domain.dtos.product.ProductImportRootDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Product;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.ProductRepository;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCTS_XML_FILE_PATH = Constants.PATH_TO_FILES + "products.xml";

    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;

    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, BranchRepository branchRepository, FileUtil fileUtil, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean productsAreImported() {
        return this.productRepository.count() > 0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return this.fileUtil.readFile(PRODUCTS_XML_FILE_PATH);
    }

    @Override
    public String importProducts() throws JAXBException {
        StringBuilder importResult = new StringBuilder();

        ProductImportRootDto productImportRootDto = this.xmlParser
                .parseXml(ProductImportRootDto.class, PRODUCTS_XML_FILE_PATH);

        for (ProductImportDto productImportDto : productImportRootDto.getProductImportDtos()) {
            if (!this.validationUtil.isValid(productImportDto)) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Branch branchEntity = this.branchRepository
                    .findByName(productImportDto.getBranch())
                    .orElse(null);

            if(branchEntity == null) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Product productEntity = this.modelMapper.map(productImportDto, Product.class);
            productEntity.setBranch(branchEntity);

            this.productRepository.saveAndFlush(productEntity);

            importResult
                    .append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE
                            , productEntity.getClass().getSimpleName()
                            , productEntity.getName()))
                    .append(System.lineSeparator());
        }

        return importResult.toString();
    }
}

package alararestaurant.service;

import alararestaurant.constants.Constants;
import alararestaurant.domain.dtos.orders.ItemImportXmlDto;
import alararestaurant.domain.dtos.orders.OrderImportXmlDto;
import alararestaurant.domain.dtos.orders.OrderImportXmlRootDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Item;
import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.OrderItem;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String ORDERS_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/orders.xml";

    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final FileUtil fileUtil;

    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, EmployeeRepository employeeRepository, OrderItemRepository orderItemRepository, ItemRepository itemRepository, FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
        this.orderItemRepository = orderItemRepository;
        this.itemRepository = itemRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean ordersAreImported() {
        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {
        return this.fileUtil.readFile(ORDERS_XML_FILE_PATH);
    }

    @Override
    public String importOrders() throws JAXBException, ParseException {
        StringBuilder importResult = new StringBuilder();

        OrderImportXmlRootDto orderImportXmlRootDto = this.xmlParser.parseXml(OrderImportXmlRootDto.class, ORDERS_XML_FILE_PATH);

        for (OrderImportXmlDto orderImportXmlDto : orderImportXmlRootDto.getOrderImportXmlDtos()) {
            if (!this.validationUtil.isValid(orderImportXmlDto)) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Employee employeeEntity = this.employeeRepository.findByName(orderImportXmlDto.getEmployee()).orElse(null);
            if (employeeEntity == null) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            List<OrderItem> orderItems = new ArrayList<>();
            for (ItemImportXmlDto itemImportXmlDto : orderImportXmlDto.getItemImportXmlRootDto().getItemImportXmlDtos()) {
                Item itemEntity = this.itemRepository.findByName(itemImportXmlDto.getName()).orElse(null);

                if (itemEntity == null) {
                    importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    continue;
                }

                this.itemRepository.saveAndFlush(itemEntity);

                OrderItem orderItemEntity = new OrderItem();
                orderItemEntity.setItem(itemEntity);
                orderItemEntity.setQuantity(itemImportXmlDto.getQuantity());

                if (!this.validationUtil.isValid(orderItemEntity)) {
                    importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    continue;
                }

                orderItems.add(orderItemEntity);
            }

            Order orderEntity = this.modelMapper.map(orderImportXmlDto, Order.class);
            orderEntity.setEmployee(employeeEntity);
            orderEntity.setOrderItems(orderItems);

            String dateInString = orderImportXmlDto.getDateTime();

            LocalDateTime date = LocalDateTime.parse(dateInString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            orderEntity.setDateTime(date);

            orderEntity = this.orderRepository.saveAndFlush(orderEntity);

            for (OrderItem orderItem : orderItems) {
                orderItem.setOrder(orderEntity);
            }

            this.orderItemRepository.saveAll(orderItems);
        }

        return importResult.toString();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        // TODO : Implement me
        return null;
    }
}

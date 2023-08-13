package kg.kuban.airport.service.impl;

import com.querydsl.core.BooleanBuilder;
import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.repository.AppUserRepository;
import kg.kuban.airport.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public EmployeeServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public List<AppUser> getEmployeeReport() {

//        BooleanBuilder builder = new BooleanBuilder();
//        QPaymentHistoryEntity root = QPaymentHistoryEntity.paymentHistoryEntity;
//
//        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
//            builder.and(root.datePayment.between(startDate, endDate));
//        }
//
//        if (Objects.nonNull(startDate) && !Objects.nonNull(endDate)) {
//            builder.and(root.datePayment.gt(startDate));
//        }
//
//        if (!Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
//            builder.and(root.datePayment.loe(endDate));
//        }
//
//        if (Objects.nonNull(serviceId)) {
//            builder.and(root.service.id.eq(serviceId));
//        }
//        if (Objects.nonNull(providerId)) {
//            builder.and(root.service.groupService.providerService.id.eq(providerId));
//            //builder.and(root.service.id.eq(serviceId).and(root.service.groupService.providerService.id.eq(providerId)));
//        }
//
//        if (Objects.nonNull(credentials)) {
//            builder.and(root.credentials.eq(credentials));
//        }
//
//        if (Objects.nonNull(status)) {
//            builder.and(root.status.eq(status));
//        }
//
//        Iterable<PaymentHistoryEntity> paymentHistoryEntities = this.paymentHistoryEntityRepository.findAll(builder.getValue());
//        return StreamSupport.stream(paymentHistoryEntities.spliterator(), false)
//                .sorted(Comparator.comparing(PaymentHistoryEntity::getDatePayment))
//                .collect(Collectors.toList());

        return null;
    }
}

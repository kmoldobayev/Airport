package kg.kuban.airport.service.impl;

import com.querydsl.core.BooleanBuilder;
import kg.kuban.airport.dto.PositionRequestDto;
import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.entity.QAppUser;
import kg.kuban.airport.repository.AppUserRepository;
import kg.kuban.airport.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public List<AppUser> getEmployeeReport(LocalDate startDate,
                                           LocalDate endDate,
                                           String fullName,
                                           PositionRequestDto position,
                                           Boolean isEnabled) {

        BooleanBuilder builder = new BooleanBuilder();
        QAppUser root = QAppUser.appUser;

        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            builder.and(root.dateBegin.between(startDate, endDate));
        }

        if (Objects.nonNull(startDate) && !Objects.nonNull(endDate)) {
            builder.and(root.dateBegin.gt(startDate));
        }

        if (!Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            builder.and(root.dateBegin.loe(endDate));
        }
        if (Objects.nonNull(fullName)) {
            builder.and(root.fullName.like(fullName));
        }

        if (Objects.nonNull(position.getTitle())) {
            builder.and(root.position.title.eq(position.getTitle()));
        }

        if (Objects.nonNull(isEnabled)) {
            builder.and(root.isEnabled.eq(isEnabled));
        }

        Iterable<AppUser> appUsers = this.appUserRepository.findAll(builder.getValue());
        return StreamSupport.stream(appUsers.spliterator(), false)
                .sorted(Comparator.comparing(AppUser::getDateBegin))
                .collect(Collectors.toList());
    }
}

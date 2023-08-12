package kg.kuban.airport.service.impl;

import kg.kuban.airport.dto.AppRoleRequestDto;
import kg.kuban.airport.entity.AppRole;
import kg.kuban.airport.repository.AppRoleRepository;
import kg.kuban.airport.repository.PositionRepository;
import kg.kuban.airport.service.AppRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AppRoleServiceImpl implements AppRoleService {
    private AppRoleRepository appRoleRepository;
    private PositionRepository positionRepository;
    @Autowired
    public AppRoleServiceImpl(AppRoleRepository appRoleRepository, PositionRepository positionRepository) {
        this.appRoleRepository = appRoleRepository;
        this.positionRepository = positionRepository;
    }

    @Override
    public AppRole createRole(AppRoleRequestDto user) throws IllegalArgumentException {
        return null;
    }

    @Override
    public AppRole updateRole(AppRoleRequestDto appRoleDto, Long userId) throws NoSuchElementException {
        return null;
    }

    @Override
    public AppRole getRoleById(Long roleId) throws NoSuchElementException {
        return null;
    }

    @Override
    public List<AppRole> getRoles() {
        return null;
    }
}

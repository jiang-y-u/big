package service;

import dao.GraduateProjectTypeDao;

import domain.GraduateProjectType;

import java.sql.SQLException;
import java.util.Collection;

public final class  GraduateProjectTypeService {
    private static GraduateProjectTypeDao graduateProjectTypeTypeDao
            = GraduateProjectTypeDao.getInstance();
    private static service.GraduateProjectTypeService GraduateProjectTypeService
            =new service.GraduateProjectTypeService();
    private GraduateProjectTypeService(){}
    public static service.GraduateProjectTypeService getInstance(){
        return GraduateProjectTypeService;
    }
    public GraduateProjectType find(Integer id)throws SQLException {
        return GraduateProjectTypeDao.find(id);
    }
}

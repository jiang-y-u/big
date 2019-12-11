package service;
import dao.GraduateProjectCategoryDao;
import domain.GraduateProjectCategory;

import java.sql.SQLException;
import java.util.Collection;

public class GraduateProjectCategoryService {
    private static GraduateProjectCategoryDao graduateProjectCategoryCategoryDao
            = GraduateProjectCategoryDao.getInstance();
    private static service.GraduateProjectCategoryService GraduateProjectCategoryService
            = new service.GraduateProjectCategoryService();

    private GraduateProjectCategoryService() {
    }

    public static service.GraduateProjectCategoryService getInstance() {
        return GraduateProjectCategoryService;
    }

    public GraduateProjectCategory find(Integer id) throws SQLException {
        return GraduateProjectCategoryDao.find(id);
    }
}

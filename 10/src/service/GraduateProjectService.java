package service;

import dao.GraduateProjectDao;
import domain.GraduateProject;

import java.sql.SQLException;
import java.util.Collection;

public final class GraduateProjectService {
        private static GraduateProjectDao graduateProjectDao
                = GraduateProjectDao.getInstance();
        private static service.GraduateProjectService graduateProjectService
                =new service.GraduateProjectService();
        private GraduateProjectService(){}
        public static service.GraduateProjectService getInstance(){
            return graduateProjectService;
        }
        public Collection<GraduateProject> findAll(){
            return graduateProjectDao.findAll();
        }
        public GraduateProject find(Integer id)throws SQLException {
            return graduateProjectDao.find(id);
        }
        public boolean update(GraduateProject graduateProject)throws SQLException,ClassNotFoundException{
            return graduateProjectDao.update(graduateProject);
        }
        public boolean add(GraduateProject graduateProject) throws SQLException,ClassNotFoundException {
            return graduateProjectDao.add(graduateProject);
        }
        public boolean delete(Integer id) throws SQLException,ClassNotFoundException{
            return graduateProjectDao.delete(id);//根据id从数据库中删除
        }
    }

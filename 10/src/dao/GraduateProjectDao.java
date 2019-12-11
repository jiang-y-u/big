
package dao;

import domain.GraduateProject;
import domain.GraduateProjectCategory;
import domain.GraduateProjectType;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class GraduateProjectDao {
    private static GraduateProjectDao graduateProjectDao=
            new GraduateProjectDao();
    private GraduateProjectDao(){}
    public static GraduateProjectDao getInstance(){
        return graduateProjectDao;
    }
    //返回结果集对象
    public Collection<GraduateProject> findAll(){
        Collection<GraduateProject> graduateProjects = new TreeSet<GraduateProject>();
        try{
            //获得数据库连接对象
            Connection connection = JdbcHelper.getConn();
            //在该连接上创建语句盒子对象
            Statement stmt = connection.createStatement();
            //执行SQL查询语句并获得结果集对象
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM GraduateProject");
            //若结果存在下一条，执行循环体
            while (resultSet.next()) {
                //打印结果集中记录的id字段
                System.out.print(resultSet.getInt("id"));
                System.out.print(",");
                //打印结果集中记录的no字段
                System.out.print(resultSet.getString("title"));
                System.out.print(",");
                //打印结果集中记录的description字段
                System.out.print(GraduateProjectCategoryDao.getInstance().find(resultSet.getInt("graduateProjectCategory_id")));
                System.out.print(",");
                //打印结果集中记录的remarks字段
                System.out.print(GraduateProjectTypeDao.getInstance().find(resultSet.getInt("graduateProjecttype_id")));
                System.out.print(",");
                System.out.print(TeacherDao.getInstance().find(resultSet.getInt("teacher_id")));
                //根据数据库中的数据,创建GraduateProject类型的对象
                GraduateProject graduateProject = new GraduateProject(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        GraduateProjectCategoryDao.getInstance().find(resultSet.getInt("graduateProjectCategory_id")),
                        GraduateProjectTypeDao.getInstance().find(resultSet.getInt("graduateProjecttype_id")),
                        TeacherDao.getInstance().find(resultSet.getInt("teacher_id")));
                //添加到集合graduateProjects中
                graduateProjects.add(graduateProject);
            }
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return graduateProjects;
    }
    public GraduateProject find(Integer id) throws SQLException{
        //声明一个GraduateProject类型的变量
        GraduateProject graduateProject = null;
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String deleteGraduateProject_sql = "SELECT * FROM graduateProject WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(deleteGraduateProject_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        //执行预编译语句
        ResultSet resultSet = preparedStatement.executeQuery();
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建GraduateProject对象
        //若结果集中没有记录，则本方法返回null
        if (resultSet.next()){
             graduateProject = new GraduateProject(resultSet.getInt("id"),
                    resultSet.getString("title"),
                    GraduateProjectCategoryDao.getInstance().find(resultSet.getInt("graduateProjectCategory_id")),
                    GraduateProjectTypeDao.getInstance().find(resultSet.getInt("graduateProjecttype_id")),
                    TeacherDao.getInstance().find(resultSet.getInt("teacher_id")));
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return graduateProject;
    }
    public boolean add(GraduateProject graduateProject) throws SQLException,ClassNotFoundException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String addGraduateProject_sql = "INSERT INTO graduateProject (title, graduateProjectType_id, graduateProjectCategory_id, teacher_id) VALUES"+" (?,?,?,?)";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(addGraduateProject_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,graduateProject.getTitle());
        preparedStatement.setInt(2,graduateProject.getGraduateProjectType().getId());
        preparedStatement.setInt(3,graduateProject.getGraduateProjectCategory().getId());
        preparedStatement.setInt(4,graduateProject.getTeacher().getId());
        //执行预编译语句，获取添加记录行数并赋值给affectedRowNum
        int affectedRowNum=preparedStatement.executeUpdate();
        System.out.println("添加了"+affectedRowNum+"行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return affectedRowNum>0;
    }
    //delete方法，根据graduateProject的id值，删除数据库中对应的graduateProject对象
    public boolean delete(int id) throws ClassNotFoundException,SQLException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String deleteGraduateProject_sql = "DELETE FROM graduateProject WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(deleteGraduateProject_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        //执行预编译语句，获取删除记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("删除了"+affectedRows+"行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return affectedRows>0;
    }
    public boolean update(GraduateProject graduateProject) throws ClassNotFoundException,SQLException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String updateGraduateProject_sql = " update graduateProject set title=?,graduateProjectCategory_id=?,graduateProjectType_id=?,teacher_id=? where id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(updateGraduateProject_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,graduateProject.getTitle());
        preparedStatement.setInt(2,graduateProject.getGraduateProjectCategory().getId());
        preparedStatement.setInt(3,graduateProject.getGraduateProjectType().getId());
        preparedStatement.setInt(4,graduateProject.getTeacher().getId());
        preparedStatement.setInt(5,graduateProject.getId());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("修改了"+affectedRows+"行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return affectedRows>0;
    }
}

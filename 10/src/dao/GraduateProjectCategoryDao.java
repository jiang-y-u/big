
package dao;

import domain.GraduateProjectCategory;
import util.JdbcHelper;

import java.sql.*;


public final class GraduateProjectCategoryDao {
    private static GraduateProjectCategoryDao graduateProjectCategoryDao=
            new GraduateProjectCategoryDao();
    private GraduateProjectCategoryDao(){}
    public static GraduateProjectCategoryDao getInstance(){
        return graduateProjectCategoryDao;
    }
    public static GraduateProjectCategory find(Integer id) throws SQLException{
        //声明一个GraduateProjectCategory类型的变量
        GraduateProjectCategory graduateProjectCategory = null;
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String deleteGraduateProjectCategory_sql = "SELECT * FROM graduateProjectCategory WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(deleteGraduateProjectCategory_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        //执行预编译语句
        ResultSet resultSet = preparedStatement.executeQuery();
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建GraduateProjectCategory对象
        //若结果集中没有记录，则本方法返回null
        if (resultSet.next()){
            graduateProjectCategory = new GraduateProjectCategory(resultSet.getInt("id"),resultSet.getString("description"),resultSet.getString("no"),resultSet.getString("remarks"));
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return graduateProjectCategory;
    }
}


package dao;

import domain.GraduateProjectType;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class GraduateProjectTypeDao {
    private static GraduateProjectTypeDao graduateProjectTypeDao=
            new GraduateProjectTypeDao();
    private GraduateProjectTypeDao(){}
    public static GraduateProjectTypeDao getInstance(){
        return graduateProjectTypeDao;
    }
    public static GraduateProjectType find(Integer id) throws SQLException{
        //声明一个GraduateProjectType类型的变量
        GraduateProjectType graduateProjectType = null;
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String deleteGraduateProjectType_sql = "SELECT * FROM graduateProjectType WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(deleteGraduateProjectType_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        //执行预编译语句
        ResultSet resultSet = preparedStatement.executeQuery();
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建GraduateProjectType对象
        //若结果集中没有记录，则本方法返回null
        if (resultSet.next()){
            graduateProjectType = new GraduateProjectType(resultSet.getInt("id"),resultSet.getString("description"),resultSet.getString("no"),resultSet.getString("remarks"));
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return graduateProjectType;
    }

}

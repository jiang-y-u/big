package controller.basic.graduateProject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.GraduateProjectCategory;
import service.GraduateProjectCategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/graduateProjectCategory.ctl")
public class ListGraduateProjectCategoryController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id_str = request.getParameter("id");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            int id = Integer.parseInt(id_str);
            responseGraduateProjectCategory(id, response);
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            //响应message到前端
            response.getWriter().println(message);
        } catch (Exception e) {
            message.put("message", "网络异常");
            //响应message到前端
            response.getWriter().println(message);
        }
    }
    private void responseGraduateProjectCategory(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找
        GraduateProjectCategory graduateProjectCategory = GraduateProjectCategoryService.getInstance().find(id);
        String graduateProjectCategory_json = JSON.toJSONString(graduateProjectCategory);
        //响应graduateProjectCategory_json到前端
        response.getWriter().println(graduateProjectCategory_json);
    }
}


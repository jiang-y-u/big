package controller.basic.graduateProject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.GraduateProjectCategory;
import domain.GraduateProjectCategory;
import service.GraduateProjectCategoryService;
import service.GraduateProjectCategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
@WebServlet("/garduateProjectType.ctl")
public class ListGraduateProjectTypeController  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
                int id = Integer.parseInt(id_str);
                responseGraduateProjectCategory(id,response);

            } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
    }
    private void responseGraduateProjectCategory(int id, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            GraduateProjectCategory graduateProjectCategory = GraduateProjectCategoryService.getInstance().find(id);
            String graduateProjectCategory_json = JSON.toJSONString(graduateProjectCategory);
            //响应
            //创建JSON对象message，以便往前端响应信息
            JSONObject message = new JSONObject();
            //加入数据信息
            response.getWriter().println(graduateProjectCategory_json);
        }catch(Exception e){
            e.getStackTrace();}
    }
}

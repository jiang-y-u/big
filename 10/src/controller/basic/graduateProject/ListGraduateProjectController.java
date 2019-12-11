package controller.basic.graduateProject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.GraduateProject;
import service.GraduateProjectService;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
@WebServlet("/graduateProject.ctl")
public class ListGraduateProjectController extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            //读取参数id
            String id_str = request.getParameter("id");
            //创建JSON对象message，以便往前端响应信息
            JSONObject message = new JSONObject();
            try {
                //如果id = null, 表示响应所有院系对象，否则响应id指定的院系对象
                if (id_str != null) {
                    int id = Integer.parseInt(id_str);
                    responseGraduateProject(id,response);
                }else{
                    responseGraduateProjects(response);
                }
            }  catch (Exception e) {
                message.put("message", "网络异常");
                e.printStackTrace();
            }
        }
        private void responseGraduateProject(int id, HttpServletResponse response) throws SQLException,IOException{
                //根据id查找学院
                GraduateProject graduateProject = GraduateProjectService.getInstance().find(id);
                String graduateProject_json = JSON.toJSONString(graduateProject);
                //响应
                //创建JSON对象message，以便往前端响应信息
                JSONObject message = new JSONObject();
                //加入数据信息
                response.getWriter().println(graduateProject_json);

        }
        //响应所有学位对象
        private void responseGraduateProjects(HttpServletResponse response)
                throws ServletException, IOException {
            //获得所有学院
            Collection<GraduateProject> graduateProjects = GraduateProjectService.getInstance().findAll();
            String graduateProjects_json = JSON.toJSONString(graduateProjects);
            //创建JSON对象message，以便往前端响应信息
            JSONObject message = new JSONObject();
            //加入数据信息
            response.getWriter().println(graduateProjects_json);
        }
        @Override
        protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //读取参数id
            String id_str = request.getParameter("id");
            int id = Integer.parseInt(id_str);
            //设置响应字符编码为UTF-8
            //response.setContentType("text/html;charset=UTF-8");
            //创建JSON对象
            JSONObject message = new JSONObject();
            //到数据库表中删除对应的学院
            try {
                GraduateProjectService.getInstance().delete(id);
                message.put("message", "删除成功");
            }catch (SQLException e){
                message.put("message", "数据库操作异常");
            }catch(Exception e){
                message.put("message", "网络异常");
            }
        }
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            //根据request对象，获得代表参数的JSON字串
            String graduateProject_json = JSONUtil.getJSON(request);

            //将JSON字串解析为GraduateProject对象
            GraduateProject graduateProjectToAdd = JSON.parseObject(graduateProject_json, GraduateProject.class);
            //用大于4的随机数给graduateProjectToAdd的id赋值
            graduateProjectToAdd.setId(4 + (int)(1000*Math.random()));
            //创建JSON对象
            JSONObject message = new JSONObject();
            //在数据库表中增加GraduateProject对象
            try {
                GraduateProjectService.getInstance().add(graduateProjectToAdd);
                message.put("message", "增加成功");
            }catch (SQLException e){
                message.put("message", "数据库操作异常");
            }catch(Exception e){
                message.put("message", "网络异常");
            }
        }
        protected void doPut(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            String graduateProject_json = JSONUtil.getJSON(request);;
            //将JSON字串解析为GraduateProject对象
            GraduateProject graduateProjectToAdd = JSON.parseObject(graduateProject_json, GraduateProject.class);
            System.out.println("graduateProjectToAdd="+graduateProjectToAdd);
            //创建JSON对象
            JSONObject message = new JSONObject();
            //到数据库表修改GraduateProject对象对应的记录
            try {
                GraduateProjectService.getInstance().update(graduateProjectToAdd);
                message.put("message", "修改成功");
            }catch (SQLException e){
                message.put("message", "数据库操作异常");
            }catch(Exception e){
                message.put("message", "网络异常");
            }

        }
    }

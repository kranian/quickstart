package kranian.testapp.controller;

import kranian.testapp.model.User;
import kranian.testapp.util.Description;
import kranian.testapp.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * @author koo.taejin
 */
@Controller
public class JDBCSelfController {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @RequestMapping("/simpleDriver")
    @ResponseBody
    @Description("Call that takes simpleDriver to complete")
    public Map<String, Object> simpleDataSource() throws InterruptedException {
        Map<String,Object> result = new TreeMap<>();
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            try(Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:embdDataSource","sa","");
                PreparedStatement pstat= conn.prepareStatement("SELECT * FROM randomTable");
                ResultSet rs=pstat.executeQuery()
            ){
                int index=0;
                while(rs.next()){
                    result.put(String.valueOf(index++),rs.getString("name"));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }


        }catch (Throwable e){
            e.printStackTrace();
        }

        return result;
    }
    @RequestMapping("/simpleSelect")
    @ResponseBody
    @Description("Call that takes select account query to complete")
    public Map<String, Object> simpleSelect() throws InterruptedException {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "SELECT * FROM randomTable";
        Map<String, Object> params = new HashMap<String, Object>();
        List<String> result = jdbcTemplate.query(sql, params, new UserMapper());
        map.put("Search Result ", result);
        return map;
    }

    @RequestMapping("/simpleInsert")
    @ResponseBody
    @Description("Call that takes simpleInsert to complete")
    public Map<String, Object> simpleInsert() throws InterruptedException {

        Map<String, Object> map = new TreeMap<>();
        User user = new User();
        IntStream.range(0,20)
                 .mapToObj(i -> Integer.valueOf(i))
                 .forEach(p->{
                     user.setName(StringUtil.getRanAlphabet(15));
                     jdbcTemplate.update("INSERT INTO randomTable (name) VALUES (:name)", new BeanPropertySqlParameterSource(user));
                     map.put(p.toString(), user.getName());
                 });

        return map;
    }

    private static final class UserMapper implements RowMapper<String> {

        public String mapRow(ResultSet rs, int rowNum) throws SQLException {

//            user.setName();
//            user.setEmail(rs.getString("email"));
//            user.setName(rs.getString("firstname"));
            return rs.getString("name");
        }
    }
}

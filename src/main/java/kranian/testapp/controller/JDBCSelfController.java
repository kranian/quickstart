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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * @author koo.taejin
 */
@Controller
public class JDBCSelfController {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @RequestMapping("/simpleDriver")
    @ResponseBody
    @Description("Call that takes DriverManager to complete")
    public Map<String, Object> simpleDriver() throws InterruptedException {
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
    @Description("Call that takes jdbcTemplate select account query to complete")
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
    @Description("Call that takes jdbcTemplate simpleInsert to complete")
    public Map<String, Object> simpleInsert() throws InterruptedException {

        Map<String, Object> map = new TreeMap<>();
        User user = new User();
        for(int iter=0;iter < 20; iter++ ){
            user.setName(StringUtil.getRanAlphabet(15));
            jdbcTemplate.update("INSERT INTO randomTable (name) VALUES (:name)", new BeanPropertySqlParameterSource(user));
            map.put(String.valueOf(iter), user.getName());
        }


        return map;
    }
    @RequestMapping("/simpleHsqlDS")
    @ResponseBody
    @Description("Call that takes Cubrid Connection Pool Get  to complete")
    public Map<String, Object> simpleHsqlDS() throws InterruptedException {
        Map<String,Object> result = new TreeMap<>();
        try {
            Context initCtx = new InitialContext();
            DataSource ds = (DataSource) initCtx.lookup("dataSource");

            try(Connection conn = ds.getConnection();
                PreparedStatement pstat= conn.prepareStatement("SELECT code,gender FROM athlete");
                ResultSet rs=pstat.executeQuery()
            ){
                int index=0;
                ResultSetMetaData rsmd = rs.getMetaData();
                int numberofColumn = rsmd.getColumnCount();
                while(rs.next()) {
                    List<String> row =new ArrayList<>();
                    for( int iter=1; iter<=numberofColumn; iter++) {
                        row.add(rs.getString((iter)));
                    }
                    result.put(String.valueOf(index++), row);
                }

            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (Throwable e){
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping("/simpleCubrid")
    @ResponseBody
    @Description("Call that takes Cubrid Connection Pool Get  to complete")
    public Map<String, Object> simpleDataSource() throws InterruptedException {
        Map<String,Object> result = new TreeMap<>();
        try {
            Context initCtx = new InitialContext();
            DataSource ds = (DataSource) initCtx.lookup("java:jboss/datasources/CubridDS");

            try(Connection conn = ds.getConnection();
                PreparedStatement pstat= conn.prepareStatement("SELECT code,gender FROM athlete");
                ResultSet rs=pstat.executeQuery()
                ){
                    int index=0;
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int numberofColumn = rsmd.getColumnCount();
                    while(rs.next()) {
                        List<String> row =new ArrayList<>();
                        for( int iter=1; iter<=numberofColumn; iter++) {
                            row.add(rs.getString((iter)));
                        }
                        result.put(String.valueOf(index++), row);
                    }

            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (Throwable e){
            e.printStackTrace();
        }
        return result;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatransferutility;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 *
 * @author EPS01
 */
public class ResultsetToJson {

    public static void writeResultSetToJson(ResultSet rs) {
        try {
            JsonParser parser = new JsonParser();
            Gson gson = new Gson();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            JsonArray ja = new JsonArray();
            while (rs.next()) {
                JsonObject jo = new JsonObject();
                for (int i = 1; i <= columnCount; i++) {
                    System.err.println("rs.getObject:"+rs.getObject(i));
                    jo.add(rsmd.getColumnLabel(i).toLowerCase(),  parser.parse(gson.toJson(rs.getObject(i))));
                }
                ja.add(jo);
            }
            System.out.println("datatransferutility.ResultsetToJson.writeResultSetToJson() JsonArray::" + ja);
        } catch (Exception e) {
            System.out.println("datatransferutility.ResultsetToJson.writeResultSetToJson() ex::" + e);
        }

    }

}

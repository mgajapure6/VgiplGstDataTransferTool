/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatransferutility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author EPS01
 */
class DateFormate {

    static String dateTimeWithSecondWithoitSpaceToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh_mm_ss_a_MMM_dd_yyyy");
        String dateTimestr = formatter.format(date);
        return dateTimestr.replaceAll("\\s", "");
    }
    
}

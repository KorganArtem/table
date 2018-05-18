/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leasicar.doccreater;

import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.text.Transliterator;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import ru.leasicar.workerSql.CarSQL;
import ru.leasicar.workerSql.DriverSQL;

/**
 *
 * @author korgan
 */
public class ActInGenerator {
    Map<Integer, String> mounths = new HashMap();
    public ActInGenerator(){
        mounths.put(1, "января");
        mounths.put(2, "февраля");
        mounths.put(3, "марта");
        mounths.put(4, "апреля");
        mounths.put(5, "мая");
        mounths.put(6, "июня");
        mounths.put(7, "июля");
        mounths.put(8, "августа");
        mounths.put(9, "сентября");
        mounths.put(10, "октября");
        mounths.put(11, "ноября");
        mounths.put(12, "декабря");
    }
    public String  createAct(int DriverId, String filePath) throws ClassNotFoundException, SQLException { 
        /*
                rowDriver.put("dogovorNumber", rs.getString("dogovorNumber"));
                rowDriver.put("dogovorDate", rs.getString("dogovorDate"));
                rowDriver.put("carId", rs.getString("carId"));
        */
        System.out.println(System.getProperty("catalina.base"));
        DriverSQL dsql = new DriverSQL();
        Map<String, String> draverData = dsql.getAllDataDriver(DriverId);
        String[] dateDog = draverData.get("dogovorDate").split("-");
        String currenDogDate = dateDog[2]+" "+mounths.get(Integer.parseInt(dateDog[1]))+" "+dateDog[0]; 
        Calendar calendar = Calendar.getInstance();
        String dataAct = calendar.get(Calendar.DAY_OF_MONTH)+" "+mounths.get(calendar.get(Calendar.MONTH))+" "+calendar.get(Calendar.YEAR);
        String numberDog = draverData.get("dogovorNumber");
        Transliterator toLatinTrans = Transliterator.getInstance("Bulgarian-Latin/BGN");
        String fileName = "actInFor_"+toLatinTrans.transliterate(draverData.get("driver_lastname"))+"_"+numberDog+".doc";
        CarSQL csql = new CarSQL();
        Map<String, String> carData = csql.getCarDataForAct(Integer.parseInt(draverData.get("carId")));
        try {
            POIFSFileSystem pfs = new POIFSFileSystem(new FileInputStream("/table/doc_tmp/act_in_tmp.doc"));
            HWPFDocument doc = new HWPFDocument(pfs);
            Range range = doc.getRange();
            range.replaceText("{%date%}", dataAct);
            range.replaceText("{%numberDog%}", numberDog);
            range.replaceText("{%currentDogDate%}", currenDogDate);
            range.replaceText("{%carModel%}", carData.get("modelName"));
            range.replaceText("{%carNumber%}", carData.get("number"));
            range.replaceText("{%carVIN%}", carData.get("VIN"));
            range.replaceText("{%carYear%}", carData.get("year"));
            range.replaceText("{%carColor%}", carData.get("transmission"));
            range.replaceText("{%carTransmission%}", carData.get("transmission"));
            range.replaceText("{%carSTS%}", carData.get("sts"));
            range.replaceText("{%carTTO%}", carData.get("ttoNumber"));
            range.replaceText("{%carOSAGO%}", carData.get("insuranceNamber"));
            System.out.println(filePath+"docs/"+fileName);
            OutputStream out = new FileOutputStream(filePath+"docs/"+fileName);
            doc.write(out);
            out.flush();
            out.close();
            return fileName;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}

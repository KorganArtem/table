/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leasicar.doccreater;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import ru.leasicar.workerSql.DriverSQL;
/**
 *
 * @author korgan
 */
public class DogovorGenerator {
    public String  createDog(int DriverId, String filePath) throws ClassNotFoundException, SQLException { 
        System.out.println(System.getProperty("catalina.base"));
        DriverSQL dsql = new DriverSQL();
        Map<String, String> draverData = dsql.getAllDataDriver(DriverId);
        try {
            String fullName = draverData.get("driver_lastname")+" "+draverData.get("driver_firstname")+" "+draverData.get("driver_midName");
            POIFSFileSystem pfs = new POIFSFileSystem(new FileInputStream("/table/doc_tmp/dogovor_tmp.doc"));
            HWPFDocument doc = new HWPFDocument(pfs);
            String fileName = "dogFor_"+DriverId+".doc";
            Range range = doc.getRange();
            range.replaceText("<day>", "10");
            range.replaceText("{%fulName%}", fullName);
            range.replaceText("{%rentSum%}", "тест2");
            range.replaceText("<NAME3>", "тест3");
            range.replaceText("<NAME4>", "тест4");
            range.replaceText("<NAME5>", "тест5");
            OutputStream out = new FileOutputStream(filePath+fileName);
            doc.write(out);
            out.flush();
            out.close();
            System.out.println("Файл  успешно создан!");
            return fileName;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

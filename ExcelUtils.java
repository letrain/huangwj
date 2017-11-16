package com.chris.tration.util;

import com.chris.tration.entity.City;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author letrain
 * @date 2017/11/16 下午9:36
 */
public class ExcelUtils {

    public static void exportExcel(List<City> cityList, HttpServletResponse response, HttpServletRequest request){

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("城市");
        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        HSSFRow row = sheet.createRow(0);
        String[] titles = {"id","城市","code","province"};
        for(short i=0;i<titles.length;i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(titles[i]);
        }

        //写数据
        if(cityList.size()>0){
            for(short i=0;i<cityList.size();i++){
                HSSFRow row2 = sheet.createRow(i+1);

                HSSFCell cell1 = row2.createCell(0);
                City city = cityList.get(i);
                cell1.setCellStyle(style);
                cell1.setCellValue(city.getId());

                HSSFCell cell2 = row2.createCell(1);
                cell2.setCellStyle(style);
                cell2.setCellValue(city.getCityName());

                HSSFCell cell3 = row2.createCell(2);
                cell3.setCellStyle(style);
                cell3.setCellValue(city.getCode());

                HSSFCell cell4 = row2.createCell(3);
                cell4.setCellStyle(style);
                cell4.setCellValue(city.getProvinceCode());
            }
        }

        String filename = "cityInfo.xls";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        try {
            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

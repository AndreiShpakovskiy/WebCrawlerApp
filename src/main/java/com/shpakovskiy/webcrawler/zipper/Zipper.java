package com.shpakovskiy.webcrawler.zipper;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {

    private String realPath;

    public Zipper(String realPath) {
        this.realPath = realPath;
    }

    public void zip(String zipFileName, String[] paths) {
        try(FileOutputStream fos = new FileOutputStream(zipFileName)) {
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            for (String srcFile : paths) {
                File fileToZip = new File(srcFile);
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            }
            zipOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String zipCsvs(String csvTable, String csvTop10) {
        File downloadsDir = new File(realPath + System.currentTimeMillis());
        downloadsDir.mkdirs();

        stringToFile(csvTable, downloadsDir.getAbsolutePath() + "\\" +  "CsvTable.csv");
        stringToFile(csvTop10, downloadsDir.getAbsolutePath() + "\\" + "CsvTop10.csv");

        zip(downloadsDir.getAbsolutePath() + ".zip",
                new String[] { downloadsDir.getAbsolutePath() + "\\" + "CsvTable.csv",
                downloadsDir.getAbsolutePath() + "\\" + "CsvTop10.csv"});

        return downloadsDir.getName() + ".zip";
    }

    private void stringToFile(String string, String filePath) {
        File file = new File(filePath);
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(string.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

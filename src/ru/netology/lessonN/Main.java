package ru.netology.lessonN;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        GameProgress[] gps = {new GameProgress(10, 5, 4, 22),
                new GameProgress(7, 7, 5, 54),
                new GameProgress(6, 3, 1, 13)};


        File dirSavegames = new File("D://Games//savegames");
        File savezipFile = new File("D://Games//savegames//savezip.zip");

        File[] saveFiles = {new File(dirSavegames, "save1.dat"),
                new File(dirSavegames, "save2.dat"),
                new File(dirSavegames, "save3.dat")};

        for (int i = 0; i < 3; i++) {
            saveGame(saveFiles[i], gps[i]);
        }

        zipFiles(String.valueOf(savezipFile), saveFiles);

        for (File file : saveFiles) {
            if (file.delete())
                System.out.println("Файл " + file.getName() + " из каталога " + file.getParent() + " удален");
        }
    }

    public static void saveGame(File path, GameProgress gp) {
        try (FileOutputStream fos = new FileOutputStream(String.valueOf(path));
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, File... files) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (File file : files) {
                try (FileInputStream fis = new FileInputStream(String.valueOf(file))) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

package ru.netology.lessonN;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        GameProgress gp1 = new GameProgress(10, 5, 4, 22);
        GameProgress gp2 = new GameProgress(7, 7, 5, 54);
        GameProgress gp3 = new GameProgress(6, 3, 1, 13);

        File dirSavegames = new File("D://Games//savegames");
        File savezipFile = new File("D://Games//savegames//savezip.zip");

        File save1 = new File(dirSavegames, "save1.dat");
        File save2 = new File(dirSavegames, "save2.dat");
        File save3 = new File(dirSavegames, "save3.dat");

        saveGame(String.valueOf(save1), gp1);
        saveGame(String.valueOf(save2), gp2);
        saveGame(String.valueOf(save3), gp3);

        zipFiles(String.valueOf(savezipFile), save1, save2, save3);

        if (save1.delete())
            System.out.println("Файл " + save1 + " удален");
        if (save2.delete())
            System.out.println("Файл " + save2 + " удален");
        if (save3.delete())
            System.out.println("Файл " + save3 + " удален");

    }

    public static void saveGame(String path, GameProgress gp) {
        try (FileOutputStream fos = new FileOutputStream(path);
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

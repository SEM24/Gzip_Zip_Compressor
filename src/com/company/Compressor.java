package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Compressor {

    private final ICompressionStrategy strategy;

    public Compressor(ICompressionStrategy strategy) {
        this.strategy = strategy;
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Вариант архива zip/gzip:");
            String zip_gzip = scanner.nextLine();

            System.out.println("Напишите путь файла:");
            Path inFile = Paths.get(scanner.nextLine());

            Path name = inFile.getFileName();

            if (zip_gzip.equals("zip")) {
                File outFile = new File(name + ".zip");
                Compressor zipCompressor = new Compressor(data -> {
                    ZipOutputStream zipOutputStream = new ZipOutputStream(data);
                    zipOutputStream.putNextEntry(new ZipEntry(name + ".png"));
                    return zipOutputStream;
                });
                zipCompressor.compress(inFile, outFile);

            } else if (zip_gzip.equals("gzip")) {
                File outFile = new File(name + ".png.gz");
                Compressor gzipCompressor = new Compressor(GZIPOutputStream::new);
                gzipCompressor.compress(inFile, outFile);
            } else {
                System.out.println("Неизвестный формат");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compress(Path inFile, File outFile) throws IOException {
        try (OutputStream outStream = new FileOutputStream(outFile)) {
            Files.copy(inFile, strategy.compress(outStream));
        }
    }
}

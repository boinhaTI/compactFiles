package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileCompressor {

    public void comprimirArquivo(String caminhoArquivo) {
        Path caminhoFonte = Paths.get(caminhoArquivo);

        if (!Files.exists(caminhoFonte) || !Files.isRegularFile(caminhoFonte)) {
            System.out.println("Arquivo não encontrado ou caminho inválido: " + caminhoArquivo);
            return;
        }

        String nomeArquivoZip = gerarNomeArquivoZip(caminhoFonte);

        try (
                FileOutputStream fos = new FileOutputStream(nomeArquivoZip);
                ZipOutputStream zos = new ZipOutputStream(fos);
                InputStream fis = Files.newInputStream(caminhoFonte)
        ) {
            ZipEntry zipEntry = new ZipEntry(caminhoFonte.getFileName().toString());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, bytesRead);
            }

            zos.closeEntry();

            System.out.println("Arquivo compactado com sucesso: " + nomeArquivoZip);

        } catch (IOException e) {
            System.out.println("Erro ao compactar o arquivo: " + e.getMessage());
        }
    }

    public void comprimirPasta(String caminhoPasta) {
        Path pasta = Paths.get(caminhoPasta);

        if (!Files.exists(pasta) || !Files.isDirectory(pasta)) {
            System.out.println("Pasta não encontrada ou caminho inválido: " + caminhoPasta);
            return;
        }

        String nomeArquivoZip = pasta.getParent().resolve(pasta.getFileName() + "_compactado.zip").toString();

        try (
                FileOutputStream fos = new FileOutputStream(nomeArquivoZip);
                ZipOutputStream zos = new ZipOutputStream(fos)
        ) {
            Files.walk(pasta)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        String zipEntryName = pasta.relativize(path).toString().replace("\\", "/"); // Corrige caminho no Windows
                        try (InputStream fis = Files.newInputStream(path)) {
                            ZipEntry zipEntry = new ZipEntry(zipEntryName);
                            zos.putNextEntry(zipEntry);

                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = fis.read(buffer)) != -1) {
                                zos.write(buffer, 0, bytesRead);
                            }

                            zos.closeEntry();
                        } catch (IOException e) {
                            System.out.println("Erro ao adicionar arquivo: " + path + " -> " + e.getMessage());
                        }
                    });

            System.out.println("Pasta compactada com sucesso: " + nomeArquivoZip );

        } catch (IOException e) {
            System.out.println("Erro ao compactar a pasta: " + e.getMessage());
        }
    }


    private String gerarNomeArquivoZip(Path caminhoFonte) {
        String nomeOriginal = caminhoFonte.getFileName().toString();
        int indicePonto = nomeOriginal.lastIndexOf('.');
        String nomeBase = (indicePonto == -1) ? nomeOriginal : nomeOriginal.substring(0, indicePonto);
        // Usa Path.resolve para montar caminho seguro
        return caminhoFonte.getParent().resolve(nomeBase + "_compactado.zip").toString();
    }
}

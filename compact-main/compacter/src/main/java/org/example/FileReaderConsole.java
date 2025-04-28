package org.example;

import java.util.Scanner;

public class FileReaderConsole {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileCompressor compressor = new FileCompressor();

        System.out.println("Digite o caminho do arquivo (ou 'sair' para encerrar):");

        while (true) {
            System.out.print("> ");
            String caminho = scanner.nextLine();

            if (caminho.equalsIgnoreCase("sair")) {
                System.out.println("Programa finalizado.");
                break;
            }

            compressor.comprimirArquivo(caminho);
        }

        scanner.close();
    }
}
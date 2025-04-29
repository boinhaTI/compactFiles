package org.example;

import java.util.Scanner;

public class FileReaderConsole {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileCompressor compressor = new FileCompressor();

        while (true) {
            System.out.print("\nEscolha o que deseja compactar:\n01 - Apenas 01 arquivo\n02 - Pasta inteira\n03 - Sair");
            System.out.print("\nEscolha a opção: ");
            int escolha = scanner.nextInt();
            scanner.nextLine();
            switch (escolha) {
                case 1:
                    System.out.println("Escreva o caminho do arquivo");
                    System.out.print("> ");
                    String caminho = scanner.nextLine();
                    compressor.comprimirArquivo(caminho);
                    break;
                case 2:
                    System.out.println("Escreva o caminho da pasta (ou 'sair' para encerrar):");
                    System.out.print("> ");
                    String caminhoPasta = scanner.nextLine();
                    compressor.comprimirPasta(caminhoPasta);
                    break;
                case 3:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Alternativa incorreta");

            }
        }
    }
}
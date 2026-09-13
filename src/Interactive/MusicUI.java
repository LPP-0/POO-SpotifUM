package Interactive;

import DTO.*;
import album.*;
import music.*;

import controller.*;
import java.util.*;



public class MusicUI {

    public static void exibirTodasMusicas(MusicController musicController) {
        System.out.println("\nLista de todas as músicas disponíveis:");
        for (Music m : musicController.getAllMusic()) {
            System.out.printf("- ID: %s | Título: %s | Artista: %s%n", m.getId(), m.getTitle(), m.getSinger());
        }
        System.out.println("\nDigite os IDs das músicas que deseja adicionar à playlist.");
        System.out.println("Digite 'fim' para terminar.");
    }

    public static String escolherAlbum(AlbumController albumController, Scanner sc, String interprete) throws Exception{

        if (albumController.isEmpty()) {
            System.out.println(" Nenhum álbum disponível.");
            return null;
        }

        System.out.println("\nLista de álbuns disponíveis:");

        //Collection<Album> albunslist = albumController.getAllAlbums();

        List<Album> albunsDoArtista = albumController.getAllAlbums().stream()
                .filter(album -> album.getArtist().trim().equalsIgnoreCase(interprete.trim()))
                .toList();


        AlbumUI.listarAlbuns(albunsDoArtista);




        if(albunsDoArtista.isEmpty()) {
            throw new Exception("Não existe nenhum álbum do intérprete especificado '" + interprete + "'!");
        }

        String albumId;
        while (true) {
            System.out.print("Digite o ID do álbum desejado: ");
            albumId = sc.nextLine().trim();

            Album albumSelecionado = albumController.getAlbum(albumId);
            if (albumSelecionado != null && albumSelecionado.getArtist().equalsIgnoreCase(interprete)) {
                return albumId;
            }
            else {
                System.out.println("ID inválido ou artista não coincide. Tente novamente.");
            }
        }
    }


    public static MusicDTO menuAdicionarMusica(AlbumController albumController, Scanner sc) {
        Input userInput = new Input(sc);

        StringBuilder sb = new StringBuilder();

        sb.append("\n\t========== ADICIONAR MÚSICA ==========\n");
        sb.append("Insira os dados da música conforme solicitado.\n");

        System.out.print(sb);
        System.out.print("Título da música: ");
        String titulo = userInput.inputString();

        System.out.print("Intérprete: ");
        String interprete = userInput.inputString();

        System.out.print("Género musical: ");
        String genero = userInput.inputString();

        System.out.print("Duração (em segundos): ");
        int duracao = userInput.inputInt();

        System.out.print("Nome da editora: ");
        String editora = userInput.inputString();

        System.out.print("Letra da música: ");
        String letra = userInput.inputString();

        List<String> composicao = lerListaString(sc);

        System.out.print("A música é explícita? (s/n): ");
        boolean explicita = sc.nextLine().equalsIgnoreCase("s");

        String albumId = null;
        try{
            albumId = escolherAlbum(albumController, sc, interprete);
        } catch(Exception e){
            System.out.println("Não foi possivel criar a música! " + e.getMessage());
            return null;
        }


        System.out.print("A música é multimédia (tem vídeo)? (s/n): ");
        boolean isMultimedia = sc.nextLine().equalsIgnoreCase("s");

        String videoUrl = "";
        if (isMultimedia) {
            System.out.print("URL do vídeo: ");
            videoUrl = sc.nextLine();
        }
        return new MusicDTO(titulo, interprete, genero, duracao, explicita, albumId, isMultimedia, videoUrl, letra, composicao,editora);
    }

    public static List<String> lerListaString(Scanner sc) {
        List<String> composicao = new ArrayList<>();
        System.out.println("Insira a composição/melodia (uma linha por vez). Digite 'fim' para terminar:");
        while (true) {
            String linha = sc.nextLine();
            if (linha.equalsIgnoreCase("fim")) break;
            composicao.add(linha);
        }

        return composicao;
    }

}

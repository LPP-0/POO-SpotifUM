package music;


/**
 * Representa a capacidade multimédia de uma música.
 * Classes que implementam essa interface podem fornecer uma reprodução em vídeo.
 */
public interface Multimedia {
    String reproduzir();
    String getUrlVideo();
}

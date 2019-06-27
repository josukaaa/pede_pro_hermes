package br.com.senac.pede_pro_hermes.webservice;

public class Api {
    private static final String ROOT_URL ="http://www.irmy.com.br/prova/pede_pro_hermes/v1/Api.php?apicall=";
    public static final String URL_CREATE_PEDEPROHERMES= ROOT_URL + "createpede_pro_hermes";
    public static final String URL_READ_PEDEPROHERMES = ROOT_URL + "getpede_pro_hermes";
    public static final String URL_UPDATE_PEDEPROHERMES = ROOT_URL + "updatepede_pro_hermes";
    public static final String URL_DELETE_PEDEPROHERMES = ROOT_URL + "deletepede_pro_hermes&id=";
}

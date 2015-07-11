package help.home.com.br.homehelp.webservices.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import help.home.com.br.homehelp.webservices.WebServiceClient;
import help.home.com.br.homehelp.webservices.rest.dto.ChamadoDTO;
import help.home.com.br.homehelp.webservices.rest.dto.ClassificacaoDTO;
import help.home.com.br.homehelp.webservices.rest.dto.InformacaoAbrirDTO;
import help.home.com.br.homehelp.webservices.rest.dto.InformacaoClassificarDTO;
import help.home.com.br.homehelp.webservices.rest.dto.InformacaoNotificarDTO;

/**
 * Created by Andre on 02/07/2015.
 */
public class ChamadoREST extends AbstractREST{

    private static final String PATH = "chamado/";

    public String abrir(String observacao, String descricao, String prioridade, Long usuarioId, Long prestadorId, Long especialidadeId) throws Exception {
        final String PATH_ABRIR = "abrir?usuarioId="+usuarioId+"&prestadorId="+prestadorId+"&especialidadeId="+especialidadeId+"&prioridade="+prioridade;

        Log.i("URL_WS", URL_WS + PATH + PATH_ABRIR);

        InformacaoAbrirDTO info = new InformacaoAbrirDTO();
        info.setDescricao(descricao);
        info.setObservacao(observacao);

        Gson gson = new Gson();
        String infoJSON = gson.toJson(info);

        String[] resposta = new WebServiceClient().post(URL_WS + PATH + PATH_ABRIR, infoJSON);

        Log.i("infoJSON", infoJSON);

        if(resposta[0].equals("400")){
            return null;
        }else if (resposta[0].equals("200")) {
            Log.i("resposta[0]", resposta[0]);
          return resposta[0];
        }

        return null;
    }

    public List<ChamadoDTO> listarChamadosAbertos(Long usuarioId) throws Exception {
        final String PATH_ABERTOS = "aberto?usuarioId=";
        Log.i("URL_WS", URL_WS + PATH_ABERTOS + usuarioId);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ABERTOS + usuarioId);

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<ChamadoDTO> lst = new ArrayList<ChamadoDTO>();
            JsonParser parser = new JsonParser();
            JsonObject obj = null;
            JsonArray array = null;

            try{
                obj = parser.parse(resposta[1]).getAsJsonObject();
                array = obj.getAsJsonArray("chamados");
            }catch (Exception e){
                e.printStackTrace();
            }

            for (int i = 0; i < array.size(); i++) {
                lst.add(gson.fromJson(array.get(i), ChamadoDTO.class));
            }

            return lst;
        } else {
            throw new Exception(resposta[1]);
        }
    }

    public void notificar(Long chamadoId, String agendamento, String observacao) throws Exception {
        final String PATH_NOTIFICAR = "notificar?chamadoId=" +chamadoId;
        Log.i("URL_WS", URL_WS + PATH + PATH_NOTIFICAR);

        InformacaoNotificarDTO info = new InformacaoNotificarDTO();
        info.setAgendamento(agendamento);
        info.setObservacao(observacao);

        Gson gson = new Gson();
        String infoJSON = gson.toJson(info);

        String[] resposta = new WebServiceClient().post(URL_WS + PATH + PATH_NOTIFICAR, infoJSON);

       if (resposta[0].equals("200")) {
            Log.i("resposta[0]", resposta[0]);
       }

    }

    public List<ClassificacaoDTO> listarClassificacoes(Long usuarioId) throws Exception {
        final String PATH_CLASSIFICACAO = "classificacao?usuarioId=";
        Log.i("URL_WS", URL_WS + PATH_CLASSIFICACAO + usuarioId);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_CLASSIFICACAO + usuarioId);

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<ClassificacaoDTO> lst = new ArrayList<ClassificacaoDTO>();
            JsonParser parser = new JsonParser();
            JsonObject obj = null;
            JsonArray array = null;

            try{
                obj = parser.parse(resposta[1]).getAsJsonObject();
                array = obj.getAsJsonArray("classificacoes");
            }catch (Exception e){
                e.printStackTrace();
            }

            for (int i = 0; i < array.size(); i++) {
                lst.add(gson.fromJson(array.get(i), ClassificacaoDTO.class));
            }

            return lst;
        } else {
            throw new Exception(resposta[1]);
        }
    }

    public void classificar(String nota, String recomendacao, Long chamadoId) throws Exception {
        final String PATH_CLASSIFICAR = "classificar?nota="+nota+"&chamadoId="+chamadoId;
        Log.i("URL_WS", URL_WS + PATH + PATH_CLASSIFICAR);

        InformacaoClassificarDTO info = new InformacaoClassificarDTO();
        info.setRecomendacao(recomendacao);

        Gson gson = new Gson();
        String infoJSON = gson.toJson(info);

        String[] resposta = new WebServiceClient().post(URL_WS + PATH + PATH_CLASSIFICAR, infoJSON);

        if (resposta[0].equals("200")) {
            Log.i("resposta[0]", resposta[0]);
        }

    }

}

package help.home.com.br.homehelp.webservices.rest;

import android.util.Log;


import help.home.com.br.homehelp.webservices.WebServiceClient;
import help.home.com.br.homehelp.webservices.rest.dto.UsuarioEspecialidadeDTO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Andre on 02/07/2015.
 */
public class EspecialidadeREST extends AbstractREST{

    private static final String PATH = "especialidade/";

    public List<UsuarioEspecialidadeDTO> getEspecialidades(String str) throws Exception {
        String PATH_ESP = "get?especialidade=";
        Log.i("URL_WS", URL_WS + PATH + PATH_ESP + str);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ESP + str);

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<UsuarioEspecialidadeDTO> lst = new ArrayList<UsuarioEspecialidadeDTO>();
            JsonParser parser = new JsonParser();
            JsonObject obj = null;
            JsonArray array = null;

            try{
                obj = parser.parse(resposta[1]).getAsJsonObject();
                array = obj.getAsJsonArray("especialidades");
            }catch (Exception e){
                e.printStackTrace();
            }

            for (int i = 0; i < array.size(); i++) {
                lst.add(gson.fromJson(array.get(i), UsuarioEspecialidadeDTO.class));
            }

            return lst;
        } else {
            throw new Exception(resposta[1]);
        }
    }

    public List<UsuarioEspecialidadeDTO> listarPorPrestador(Long prestadorId) throws Exception {
        String PATH_ESP = "listarPorPrestador?prestadorId=";
        Log.i("URL_WS", URL_WS + PATH + PATH_ESP +prestadorId);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ESP + prestadorId);

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<UsuarioEspecialidadeDTO> lst = new ArrayList<UsuarioEspecialidadeDTO>();
            JsonParser parser = new JsonParser();
            JsonObject obj = null;
            JsonArray array = null;

            try{
                obj = parser.parse(resposta[1]).getAsJsonObject();
                array = obj.getAsJsonArray("especialidades");
            }catch (Exception e){
                e.printStackTrace();
            }

            for (int i = 0; i < array.size(); i++) {
                lst.add(gson.fromJson(array.get(i), UsuarioEspecialidadeDTO.class));
            }

            return lst;
        } else {
            throw new Exception(resposta[1]);
        }
    }

    public void excluir(Long usuarioEspecialidadeId) throws Exception {
        String PATH_ESP = "excluir?usuarioEspecialidadeId=";
        Log.i("URL_WS", URL_WS + PATH + PATH_ESP + usuarioEspecialidadeId);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ESP + usuarioEspecialidadeId);

        if (resposta[0].equals("200")) {

        } else {
            throw new Exception(resposta[1]);
        }
    }
}

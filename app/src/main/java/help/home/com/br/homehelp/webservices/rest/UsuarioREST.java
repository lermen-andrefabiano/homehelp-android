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
import help.home.com.br.homehelp.webservices.rest.dto.LoginDTO;

/**
 * Created by Andre on 02/07/2015.
 */
public class UsuarioREST extends AbstractREST{

    private static final String PATH = "usuario/";

    public LoginDTO login(String login, String senha) throws Exception {
        final String PATH_LOGIN = "login";

        Log.i("URL_WS", URL_WS + PATH + PATH_LOGIN);

        LoginDTO info = new LoginDTO();
        info.setLogin(login);
        info.setSenha(senha);

        Gson gson = new Gson();
        String infoJSON = gson.toJson(info);

        String[] resposta = new WebServiceClient().post(URL_WS + PATH + PATH_LOGIN, infoJSON);

        if (resposta[0].equals("200")) {
            Gson gsonLogin = new Gson();

            JsonParser parser = new JsonParser();
            JsonObject obj = null;

            try {
                obj = parser.parse(resposta[1]).getAsJsonObject();
                info = gsonLogin.fromJson(obj, LoginDTO.class);

                return info;
            } catch (Exception e) {
                info = null;
                e.printStackTrace();
            }
        } else {
            info = null;
            throw new Exception(resposta[1]);
        }

        return info;
    }

    public LoginDTO criar(Long userId, String nome, String email, String login, String senha, String endereco, Boolean prestaServico) throws Exception {
        final String PATH_CRIAR = "criar";

        Log.i("URL_WS", URL_WS + PATH + PATH_CRIAR);

        LoginDTO info = new LoginDTO();
        info.setId(userId);
        info.setId(userId);
        info.setNome(nome);
        info.setEmail(email);
        info.setEndereco(endereco);
        info.setLogin(login);
        info.setSenha(senha);
        info.setPrestaServico(prestaServico);

        Gson gson = new Gson();
        String infoJSON = gson.toJson(info);

        String[] resposta = new WebServiceClient().post(URL_WS + PATH + PATH_CRIAR, infoJSON);

        if (resposta[0].equals("200")) {
            Gson gsonLogin = new Gson();

            JsonParser parser = new JsonParser();
            JsonObject obj = null;

            try {
                obj = parser.parse(resposta[1]).getAsJsonObject();
                info = gsonLogin.fromJson(obj, LoginDTO.class);

                return info;
            } catch (Exception e) {
                info = null;
                e.printStackTrace();
            }
        } else {
            info = null;
            throw new Exception(resposta[1]);
        }

        return info;
    }

}

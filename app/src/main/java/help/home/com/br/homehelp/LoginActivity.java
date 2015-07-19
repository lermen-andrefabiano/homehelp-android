package help.home.com.br.homehelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import help.home.com.br.homehelp.webservices.rest.ChamadoREST;
import help.home.com.br.homehelp.webservices.rest.UsuarioREST;
import help.home.com.br.homehelp.webservices.rest.dto.LoginDTO;


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            login();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void login(){
        EditText editLogin = (EditText)findViewById(R.id.editLogin);
        EditText editSenha = (EditText)findViewById(R.id.editSenha);
        LoginDTO retorno = null;
        String login = editLogin.getText().toString();
        try {
            UsuarioREST rest = new UsuarioREST();
            retorno = rest.login(login, editSenha.getText().toString());
        }catch (Exception e){
        }

        abreMain(retorno);

    }

    public void abreMain(LoginDTO retorno){
        if(retorno!=null && retorno.getLogin()!=null){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("HomeHelpPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("key_user_id", retorno.getId().toString());
            editor.putString("key_user", retorno.getLogin());
            editor.putString("key_user_email", retorno.getEmail());
            editor.putString("key_user_nome", retorno.getNome());
            editor.putString("key_user_endereco", retorno.getEndereco());
            editor.putString("key_user_prestador", retorno.getPrestaServico().toString());
            editor.commit();

            Intent r = new Intent(this, MainActivity.class);
            startActivity(r);
        }
    }
}

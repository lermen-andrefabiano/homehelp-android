package help.home.com.br.homehelp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;


import help.home.com.br.homehelp.webservices.rest.ChamadoREST;
import help.home.com.br.homehelp.webservices.rest.EspecialidadeREST;
import help.home.com.br.homehelp.webservices.rest.dto.UsuarioEspecialidadeDTO;


public class ChamadoFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String TAG = "ChamadoFragment";

    private SearchView searchEspecialidde;

    private List<UsuarioEspecialidadeDTO> listaPrestadoresEspecialidade = null;

    private UsuarioEspecialidadeDTO usuarioEspecialidadeSel;

    // var para abertura do chamado
    private Spinner spinnerPrioridade;

    private EditText editDecricao;

    private EditText editObservacao;

    private String prioridade = "N";


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(int sectionNumber) {
        ChamadoFragment fragment = new ChamadoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ChamadoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chamado, container, false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        searchEspecialidde = (SearchView)view.findViewById(R.id.searchEspecialidde);
        searchEspecialidde.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getListaEspecialidades(newText, getView());
                return false;
            }
        });

        return view;
    }

    public void getListaEspecialidades(String query, View view){
        if(query!=null&&query.length() >= 3){
            EspecialidadeREST especialidadeREST = new EspecialidadeREST();
            try {
                listaPrestadoresEspecialidade = especialidadeREST.getEspecialidades(query);
            }catch (Exception e){
            }

            Log.i(TAG, "listaPrestadoresEspecialidade");
            if(listaPrestadoresEspecialidade!=null){
                openListaPrestadores(view);
            }
        }
    }

    private void openListaPrestadores(View view){
        Log.i(TAG, "openListaPrestadores");

        ListView listPrestadores = (ListView) view.findViewById(R.id.listPrestadores);

        ArrayAdapter<UsuarioEspecialidadeDTO> adapter = new ArrayAdapter<UsuarioEspecialidadeDTO>(getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                listaPrestadoresEspecialidade);

        listPrestadores.setAdapter(adapter);
        listPrestadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usuarioEspecialidadeSel = (UsuarioEspecialidadeDTO)parent.getItemAtPosition(position);
                Log.i(TAG, usuarioEspecialidadeSel.getEspecialidade().getId().toString());
                Log.i(TAG, usuarioEspecialidadeSel.getUsuario().getNome());
                Log.i(TAG, usuarioEspecialidadeSel.getValorCobrado().toString());
                abrirPopUpChamado();
            }
        });
    }

    private void abrirPopUpChamado(){
        LayoutInflater li = LayoutInflater.from(getActivity());
        View abrirChamadoView = li.inflate(R.layout.activity_abrir_chamado, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(abrirChamadoView);

        spinnerPrioridade = (Spinner)abrirChamadoView.findViewById(R.id.spinnerPrioridade);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.prioridade_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerPrioridade.setAdapter(adapter);
        spinnerPrioridade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);

                switch (item){
                    case "Normal":
                        prioridade = "N";
                        break;
                    case "Urgente":
                        prioridade = "U";
                        break;
                    case "Muito Urgente":
                        prioridade = "M";
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        editDecricao = (EditText) abrirChamadoView.findViewById(R.id.editDecricao);
        editObservacao = (EditText) abrirChamadoView.findViewById(R.id.editObservacao);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.label_abrir_chamado,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (!editDecricao.getText().toString().equals("")) {
                                    abrirChamado();
                                }else {
                                    Toast.makeText(getActivity(), R.string.toast_chamado_descriao, Toast.LENGTH_SHORT).show();
                                }
                        }})
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
    }

    public void abrirChamado(){
        SharedPreferences pref = getActivity().getSharedPreferences("HomeHelpPref", Context.MODE_PRIVATE);
        String user = pref.getString("key_user_id", "");

        try {
            ChamadoREST rest = new ChamadoREST();
            rest.abrir(editObservacao.getText().toString(),
                    editDecricao.getText().toString(),
                    prioridade,
                    Long.valueOf(user),
                    usuarioEspecialidadeSel.getUsuario().getId(),
                    usuarioEspecialidadeSel.getEspecialidade().getId());
        }catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(getActivity(), R.string.toast_chamado_aberto, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
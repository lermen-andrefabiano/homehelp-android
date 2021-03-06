package help.home.com.br.homehelp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import help.home.com.br.homehelp.adapter.ClassificacaoAdapter;
import help.home.com.br.homehelp.webservices.rest.ChamadoREST;
import help.home.com.br.homehelp.webservices.rest.dto.ClassificacaoDTO;

public class ClassificacaoFragment extends Fragment {

    private static final String TAG = "ClassificacaoFragment";
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private List<ClassificacaoDTO> classificacoes;

    private ClassificacaoDTO classificacaoSel;

    private EditText editRecomendacao;

    private Spinner spinnerNota;

    private String nota;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(int sectionNumber) {
        ClassificacaoFragment fragment = new ClassificacaoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ClassificacaoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_classificacao, container, false);

        listarClassificacoes(rootView);

        return rootView;
    }

    public void listarClassificacoes(View rootView){
        SharedPreferences pref = getActivity().getSharedPreferences("HomeHelpPref", Context.MODE_PRIVATE);
        String user = pref.getString("key_user_id", "");

        Log.i(TAG, user);

        try {
            ChamadoREST rest = new ChamadoREST();
            classificacoes = rest.listarClassificacoes(Long.valueOf(user));
        }catch (Exception e){
            e.printStackTrace();
        }

        if(classificacoes!=null){
            openClassificacao(rootView);
        }
    }

    public void openClassificacao(View rootView){
        Log.i(TAG, "openChamado");

        ListView listClassificacao = (ListView) rootView.findViewById(R.id.listClassificacao);

        ClassificacaoAdapter adapter = new ClassificacaoAdapter(getActivity(),
                R.layout.fragment_classificacao_item,
                classificacoes);

        listClassificacao.setAdapter(adapter);
        listClassificacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                classificacaoSel = (ClassificacaoDTO) parent.getItemAtPosition(position);
                abrirPopUpClassificacao();
            }
        });
    }

    private void abrirPopUpClassificacao(){
        LayoutInflater li = LayoutInflater.from(getActivity());
        View classificarChamadoView = li.inflate(R.layout.activity_classificar_chamado, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(classificarChamadoView);

        editRecomendacao = (EditText) classificarChamadoView.findViewById(R.id.editRecomendacao);

        spinnerNota = (Spinner) classificarChamadoView.findViewById(R.id.spinnerNota);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.nota_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerNota.setAdapter(adapter);
        spinnerNota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nota = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.label_classificar,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (!editRecomendacao.getText().toString().equals("")) {
                                    classificar();
                                }else {
                                    Toast.makeText(getActivity(), R.string.toast_classificacao_recomendacao, Toast.LENGTH_SHORT).show();
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

    public void classificar(){
        try {
            ChamadoREST rest = new ChamadoREST();
            rest.classificar(nota, editRecomendacao.getText().toString(), classificacaoSel.getChamadoId());
        }catch (Exception e){
        }
        Toast.makeText(getActivity(), R.string.toast_classificacao_aberta, Toast.LENGTH_SHORT).show();

        listarClassificacoes(getView());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
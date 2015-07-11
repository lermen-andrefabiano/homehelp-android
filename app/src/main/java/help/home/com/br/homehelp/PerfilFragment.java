package help.home.com.br.homehelp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PerfilFragment extends Fragment {

    private static final String TAG = "PerfilActivity";

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static PerfilFragment newInstance(int sectionNumber) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PerfilFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        this.openLstMeusDados(view);

        this.openLstMais(view);

        return view;
    }

    private void openLstMeusDados(View view){
        Log.i(TAG, "openLstMeusDados");

        ListView lstMeusDados = (ListView) view.findViewById(R.id.lstMeusDados);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new String[]{
                        getString(R.string.label_dados_pessoais),
                });

        lstMeusDados.setAdapter(adapter);
        lstMeusDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), MeusDadosActivity.class);
                startActivity(i);
            }
        });
    }

    private void openLstMais(View view){
        Log.i(TAG, "openLstMais");

        final ListView lstMais = (ListView) view.findViewById(R.id.lstMais);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new String[]{
                        getString(R.string.label_redefinir_senha),
                        getString(R.string.label_esqueci_senha),
                });

        lstMais.setAdapter(adapter);
        lstMais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0 :
                        Intent r = new Intent(getActivity(), RedefinirSenhaActivity.class);
                        startActivity(r);
                        break;
                    case 1 :
                        Intent l = new Intent(getActivity(), LembreteSenhaActivity.class);
                        startActivity(l);
                    break;
                }
            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

}
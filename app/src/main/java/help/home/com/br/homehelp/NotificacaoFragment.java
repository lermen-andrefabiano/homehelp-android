package help.home.com.br.homehelp;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.List;

import help.home.com.br.homehelp.adapter.NotificacaoAdapter;
import help.home.com.br.homehelp.webservices.rest.ChamadoREST;
import help.home.com.br.homehelp.webservices.rest.dto.ChamadoDTO;

public class NotificacaoFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String TAG = "NotificacaoFragment";

    private List<ChamadoDTO> chamadosAbertos;

    private ChamadoDTO chamadoSel;

    private EditText editObservacao;

    private StringBuilder agendamento = new StringBuilder("");;

    DatePicker dateAgendamento;

    TimePicker timeAgendaemnto;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(int sectionNumber) {
        NotificacaoFragment fragment = new NotificacaoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public NotificacaoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notificacao, container, false);

        listarChamados(rootView);
        return rootView;
    }

    public void listarChamados(View rootView){
        ChamadoREST rest = new ChamadoREST();
        try {
            chamadosAbertos = rest.listarChamadosAbertos(2L); //TODO remover o valor fixo - USUARIO LOGADO
        }catch (Exception e){
        }

        if(chamadosAbertos!=null){
            openChamado(rootView);
        }
    }

    public void openChamado(View rootView){
        Log.i(TAG, "openChamado");

        ListView listNotificacao = (ListView) rootView.findViewById(R.id.listNotificacao);

        NotificacaoAdapter adapter = new NotificacaoAdapter(getActivity(),
                R.layout.fragment_notificacao_item,
                chamadosAbertos);

        listNotificacao.setAdapter(adapter);
        listNotificacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chamadoSel = (ChamadoDTO)parent.getItemAtPosition(position);
                abrirPopUpNotificacao();
            }
        });
    }

    private void abrirPopUpNotificacao(){
        LayoutInflater li = LayoutInflater.from(getActivity());
        View agendarChamadoView = li.inflate(R.layout.activity_agendar_chamado, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(agendarChamadoView);

        dateAgendamento = (DatePicker) agendarChamadoView.findViewById(R.id.dateAgendamento);
        timeAgendaemnto = (TimePicker) agendarChamadoView.findViewById(R.id.timeAgendaemnto);
        editObservacao = (EditText) agendarChamadoView.findViewById(R.id.editObservacao);

        // set dialog message
        alertDialogBuilder
                .setPositiveButton(R.string.label_agendar,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                agendamento = new StringBuilder();
                                agendamento.append(dateAgendamento.getDayOfMonth());
                                agendamento.append("_");
                                agendamento.append(dateAgendamento.getMonth());
                                agendamento.append("_");
                                agendamento.append(dateAgendamento.getYear());
                                agendamento.append("_");
                                agendamento.append(timeAgendaemnto.getCurrentHour());
                                agendamento.append("_");
                                agendamento.append(timeAgendaemnto.getCurrentMinute());

                                Log.i(TAG, agendamento.toString());

                                agendar();
                            }
                        })
                .setNegativeButton(R.string.label_rejeitar,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                agendar();
                                dialog.cancel();
                            }
                        })
                .setCancelable(true);

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void agendar(){
        ChamadoREST rest = new ChamadoREST();
        try {
            rest.notificar(chamadoSel.getId(), agendamento.toString(), editObservacao.getText().toString());
        }catch (Exception e){
        }
        Toast.makeText(getActivity(), R.string.toast_agendamento_realizado, Toast.LENGTH_SHORT).show();

        listarChamados(getView());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
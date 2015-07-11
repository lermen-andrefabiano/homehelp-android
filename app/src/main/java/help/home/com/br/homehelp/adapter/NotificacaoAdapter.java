package help.home.com.br.homehelp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import help.home.com.br.homehelp.R;
import help.home.com.br.homehelp.webservices.rest.dto.ChamadoDTO;

/**
 * Created by Andre on 09/07/2015.
 */
public class NotificacaoAdapter extends ArrayAdapter<ChamadoDTO> {

    int resource;

    //Initialize adapter
    public NotificacaoAdapter(Context context, int resource, List<ChamadoDTO> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LinearLayout view;
        //Get the current alert object
        ChamadoDTO c = getItem(position);

        //Inflate the view
        if(convertView==null){
            view = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, view, true);
        }else{
            view = (LinearLayout) convertView;
        }

        if(c!=null){
            //Get the text boxes from the listitem.xml file
            TextView textPrioridade =(TextView)view.findViewById(R.id.textPrioridade);
            TextView textData =(TextView)view.findViewById(R.id.textData);
            TextView textDescricao =(TextView)view.findViewById(R.id.textDescricao);
            TextView textObservacao =(TextView)view.findViewById(R.id.textObservacao);
            TextView textEspecialidade =(TextView)view.findViewById(R.id.textEspecialidade);

            String prioridade = "";
            if(c.getPrioridade()!=null){
                switch (c.getPrioridade()){
                    case "N":
                        prioridade = getContext().getResources().getString(R.string.label_prioridade_normal);
                        break;
                    case "U":
                        prioridade = getContext().getResources().getString(R.string.label_prioridade_urgente);
                        break;
                    case "M":
                        prioridade = getContext().getResources().getString(R.string.label_prioridade_muito_urgente);
                        break;
                }
            }

            //Assign the appropriate data from our alert object above
            textPrioridade.setText(prioridade);
            textData.setText(c.getData());
            textDescricao.setText(c.getDescricao());
            textObservacao.setText(c.getObservacao());
            textEspecialidade.setText(c.getEspecialidade());

        }

        return view;
    }
}

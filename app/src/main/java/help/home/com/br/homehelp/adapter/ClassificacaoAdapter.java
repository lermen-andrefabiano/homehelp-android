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
import help.home.com.br.homehelp.webservices.rest.dto.ClassificacaoDTO;

/**
 * Created by Andre on 09/07/2015.
 */
public class ClassificacaoAdapter extends ArrayAdapter<ClassificacaoDTO> {

    int resource;

    //Initialize adapter
    public ClassificacaoAdapter(Context context, int resource, List<ClassificacaoDTO> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LinearLayout view;
        //Get the current alert object
        ClassificacaoDTO c = getItem(position);

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
        //Get the text boxes from the listitem.xml file
        TextView textDescricao =(TextView)view.findViewById(R.id.textDescricao);
        TextView textPrestador =(TextView)view.findViewById(R.id.textPrestador);
        TextView textEspecialidade =(TextView)view.findViewById(R.id.textEspecialidade);

        //Assign the appropriate data from our alert object above
        textDescricao.setText(c.getDescricao());
        textPrestador.setText(c.getPrestador());
        textEspecialidade.setText(c.getEspecialidade());

        return view;
    }
}

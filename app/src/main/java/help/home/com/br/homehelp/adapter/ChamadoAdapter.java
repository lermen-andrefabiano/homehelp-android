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
import help.home.com.br.homehelp.webservices.rest.dto.UsuarioEspecialidadeDTO;

/**
 * Created by Andre on 09/07/2015.
 */
public class ChamadoAdapter extends ArrayAdapter<UsuarioEspecialidadeDTO> {

    int resource;

    //Initialize adapter
    public ChamadoAdapter(Context context, int resource, List<UsuarioEspecialidadeDTO> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LinearLayout view;
        //Get the current alert object
        UsuarioEspecialidadeDTO u = getItem(position);

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
        TextView textValor =(TextView)view.findViewById(R.id.textValor);
        TextView textEspecialidade =(TextView)view.findViewById(R.id.textEspecialidade);
        TextView textPrestador =(TextView)view.findViewById(R.id.textPrestador);

        String valorCobrado = getContext().getString(R.string.label_valor_dinheiro) + " " + u.getValorCobrado().toString();
        textValor.setText(valorCobrado);
        textEspecialidade.setText(u.getEspecialidade().getDescricao());
        textPrestador.setText(u.getUsuario().getNome());

        return view;
    }
}

package help.home.com.br.homehelp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import help.home.com.br.homehelp.R;
import help.home.com.br.homehelp.webservices.rest.EspecialidadeREST;
import help.home.com.br.homehelp.webservices.rest.dto.UsuarioEspecialidadeDTO;

/**
 * Created by Andre on 09/07/2015.
 */
public class MeusDadosAdapter extends ArrayAdapter<UsuarioEspecialidadeDTO> {

    int resource;

    //Initialize adapter
    public MeusDadosAdapter(Context context, int resource, List<UsuarioEspecialidadeDTO> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LinearLayout view;
        //Get the current alert object
        final UsuarioEspecialidadeDTO u = getItem(position);

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
        TextView edtEspecialidade =(TextView)view.findViewById(R.id.edtEspecialidade);
        ImageView imgExcluir =(ImageView)view.findViewById(R.id.imgExcluir);

        edtEspecialidade.setText(u.getEspecialidade().getDescricao());

        imgExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EspecialidadeREST rest = new EspecialidadeREST();
                    rest.excluir(u.getId());
                }catch (Exception e){
                }
            }
        });


        return view;
    }
}

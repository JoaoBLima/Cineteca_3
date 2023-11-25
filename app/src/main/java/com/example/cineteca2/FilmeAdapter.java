package com.example.cineteca2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cineteca2.Model.Filme;

import java.util.List;

public class FilmeAdapter extends ArrayAdapter<Filme> {
    private Context context;
    public FilmeAdapter(Context context, List<Filme> filmes) {
        super(context, 0, filmes);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        Filme filme = getItem(position);

        if (filme != null) {
            TextView textView = convertView.findViewById(android.R.id.text1);
            textView.setText(filme.getTitulo() + " (" + filme.getAno() + ")");
        }

        return convertView;
    }

    public void atualizarLista(List<Filme> filmes) {
        clear();
        addAll(filmes);
        notifyDataSetChanged();
    }
    public void adicionarFilme(Filme filme) {
        add(filme);
        notifyDataSetChanged();
    }
}

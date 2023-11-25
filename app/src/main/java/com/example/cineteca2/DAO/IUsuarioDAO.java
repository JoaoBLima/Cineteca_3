package com.example.cineteca2.DAO;
import com.example.cineteca2.Model.Usuario;

public interface IUsuarioDAO {
    public boolean salvar(Usuario user) ;
    public boolean getByLogin(String login) ;
}

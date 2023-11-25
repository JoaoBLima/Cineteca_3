package com.example.cineteca2.Model;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiResponse {
    @SerializedName("ok")
    private boolean ok;
    @SerializedName("description")
    private List<FilmeDescription> descriptionList;
    public boolean isOk() {
        return ok;
    }
    public List<FilmeDescription> getDescriptionList() {
        return descriptionList;
    }
}

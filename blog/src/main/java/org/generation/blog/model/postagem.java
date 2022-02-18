package org.generation.blog.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity 
@Table(name = "postagem") // No MySQL isso vira uma tabela, o nome dela será postagem
public class Postagem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // será uma chave primária
    private long id;

    @NotNull // não pode valores nulos
    @Size(min = 5, max = 100)
    private String titulo;

    @NotNull // não pode valores nulos
    @Size(min = 5, max = 100)
    private String texto;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data = new java.sql.Date(System.currentTimeMillis());
     // toda vez que passar um dado na classe, esta linha captura a data/hora/segundo/milésimo 

    public long getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setId(long id) {
        this.id = id;
    }
}

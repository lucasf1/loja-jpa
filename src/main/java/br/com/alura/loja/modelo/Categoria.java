package br.com.alura.loja.modelo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
public class Categoria {

	@EmbeddedId
	private CategoriaId id;
	
	public Categoria(String nome) {
		this.id = new CategoriaId(nome, "XPTO");
	}	
	
	public String getNome() {
		return this.id.getNome();
	}
	
	public String getTipo() {
		return this.id.getTipo();
	}
}

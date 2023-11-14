package br.com.ProjetoLeo.ApiControleJogos.repositories;

import br.com.ProjetoLeo.ApiControleJogos.models.JogoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogoRepository extends JpaRepository<JogoModel, Long> {
}

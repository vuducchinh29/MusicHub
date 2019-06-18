package codegym.cdkteam.musichub.repository;

import codegym.cdkteam.musichub.model.TokenVerifyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenVerifyDTORepository extends JpaRepository<TokenVerifyDTO, Long> {
    TokenVerifyDTO findByUserId(long id);
}

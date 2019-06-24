package codegym.cdkteam.musichub.service.impl;

import codegym.cdkteam.musichub.model.TokenVerifyDTO;
import codegym.cdkteam.musichub.repository.TokenVerifyDTORepository;
import codegym.cdkteam.musichub.service.TokenVerifyDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenVerifyDTOServiceImpl implements TokenVerifyDTOService {
  @Autowired
  private TokenVerifyDTORepository tokenVerifyDTORepository;
  @Override
  public TokenVerifyDTO findByUserId(long id) {
    return tokenVerifyDTORepository.findByUserId(id);
  }

  @Override
  public void save(TokenVerifyDTO tokenVerifyDTO) {
    tokenVerifyDTORepository.save(tokenVerifyDTO);
  }

  @Override
  public void delete(TokenVerifyDTO tokenVerifyDTO) {
    tokenVerifyDTORepository.delete(tokenVerifyDTO);
  }
}
